import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')

        pattern =
                '%clr(%d{yyyy-MM-dd HH:mm:ss}){magenta} ' + // Date
                        '%clr(%5p) ' + // Log level
                        '%clr([%4.5t]){green} ' + // Thread
                        '%clr(%-15.20logger{39}){cyan} %clr(:){faint} ' + // Logger
                        '%m%n%wex' // Message
    }
}

appender("dailyFileAppender", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')

        pattern =
                '%clr(%d{yyyy-MM-dd HH:mm:ss}){magenta} ' + // Date
                        '%clr(%5p) ' + // Log level
                        '%clr([%4.5t]){green} ' + // Thread
                        '%clr(%-15.20logger{39}){cyan} %clr(:){faint} ' + // Logger
                        '%m%n%wex' // Message
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${(System.getProperty('catalina.base') ?: (System.getProperty('user.home') ?: 'target'))}/logs/services-%d{yyyy-MM-dd}.zip"
    }
}


def targetDir = BuildSettings.TARGET_DIR
println ("ENV is ${Environment.getCurrent()}")
if (Environment.isDevelopmentMode() && targetDir != null) {
    root(ERROR, ['STDOUT'])
    logger("au.org.biodiversity", DEBUG, ['STDOUT'])
    logger("services3", DEBUG, ['STDOUT'])
}

if (Environment.getCurrent() == Environment.PRODUCTION && targetDir != null) {
    root(ERROR, ['dailyFileAppender'])
    logger("au.org.biodiversity", DEBUG, ['dailyFileAppender'])
    logger("services3", DEBUG, ['dailyFileAppender'])
}