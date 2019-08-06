package au.org.biodiversity.nsl.api

import au.org.biodiversity.nsl.Author
import au.org.biodiversity.nsl.AuthorService
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authz.annotation.RequiresRoles
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.OK

class AuthorController implements WithTarget {

    static responseFormats = [
            index      : ['html'],
            deduplicate: ['json', 'xml', 'html']
    ]

    static allowedMethods = [
            deduplicate: 'DELETE'
    ]

    AuthorService authorService
    def jsonRendererService

    def index() {}


    @RequiresRoles('admin')
    deduplicate(long id, long target, String user) {
        Author targetAuthor = Author.get(target)
        Author duplicate = Author.get(id)
        withTargets(["Target author": targetAuthor, "Duplicate author": duplicate]) { ResultObject result ->
            if (!user) {
                user = SecurityUtils.subject.principal.toString()
            }
            if (duplicate.id == targetAuthor.id) {
                result.error("Duplicate and Target author are the same.")
                result.status = HttpStatus.BAD_REQUEST
                result.ok = false
                return
            }
            try {
                Map dedupeResults = authorService.deduplicate(duplicate, targetAuthor, user)
                if (dedupeResults) {
                    //rename success to OK for consistency
                    result.ok = dedupeResults.remove('success')
                    result << dedupeResults
                } else { //probably only happens with a Mock in testing
                    result.ok = false
                    result.error = "Author deduplication failed: No results from service."
                }
                if (result.ok) {
                    result.status = OK
                } else {
                    result.status = INTERNAL_SERVER_ERROR
                }
            } catch (e) {
                e.printStackTrace()
                result.error "Could not deduplicate: $e.message"
                result.status = INTERNAL_SERVER_ERROR
                result.ok = false
            }
        }
    }
}
