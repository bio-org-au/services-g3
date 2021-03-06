<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>NSL Services</title>

</head>

<body>

<div id="page-body" role="main" class="container">
  <h1 class="header"><st:bannerText/> NSL Services</h1>

  <div class="boxed">

    <asset:image src="${st.cardImage().toString()}" height="300px" class="left"/>

    <div class="boxed-content">
      <st:shardDescription/>

      <div>
        <p>In the menu above you can:-</p>
        <ul>
          <li>
            click <a href="${st.nameTree()}">Names (<st:nameTree/>)</a>
            to search bibliographic information on names, or
          </li>
          <li>
            click <a href="${st.primaryClassification()}">
            Taxonomy (<st:primaryClassification/>)</a> to search the <st:primaryClassification/> taxonomic data.
          </li>
        </ul>
      </div>
    </div>
  </div>

</div>

</body>
</html>
