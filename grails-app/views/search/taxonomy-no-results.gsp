<%--
  User: pmcneil
  Date: 16/09/14
--%>

<%@ page import="au.org.biodiversity.nsl.NameRank; au.org.biodiversity.nsl.NameType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main">
  <title>
    <st:pageTitle/>
    <g:if test="${params.product}">
      ${params.product}
    </g:if>
    <g:else>Search</g:else>
    <g:if test="${query?.name}">- ${query.name}?</g:if>
  </title>
</head>

<body>
<g:set var="panelClass"
       value="${st.panelClass(product: params.product)}"/>

<div class="${params.product}">

  <g:render template="/search/taxonSearchTabs"/>

  <div>
    <div class="results-header ${panelClass}">
      <strong>No results found</strong>

      <div class="text text-info">
        <g:message code="product.search.tip.${params.product}" default=""/>
      </div>
    </div>

    <div class="panel-body">
      <div class="results">
        <h2>No results found <g:if test="${query.name}">for &quot;${query.name}&quot;</g:if>.</h2>
        <p>       Suggestions:</p>
        <ul>
          <li>Check the spelling of your search string.</li>
          <li>Use one or more wildcards (%) for a broader search.</li>
          <li>Change the type of name(s) included in the search.</li>
        </ul>
      </div>
    </div>
  </div>

</div>
</body>
</html>
