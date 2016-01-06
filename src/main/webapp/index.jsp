<!DOCTYPE html>
<%
   String contextPath = getServletContext().getContextPath();
%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="A simple Errai/GWT CRUD Application.">

  <title>Errai Tutorial</title>

  <!-- Bootstrap Core CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom CSS -->
  <link href="css/shop-homepage.css" rel="stylesheet">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js does not work if you view the page via file:// -->
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->

  <link href="<%=contextPath%>/css/application.css" rel="stylesheet">
  <script type="text/javascript">
    erraiJaxRsApplicationRoot = "rest";
  </script>
  <script type="text/javascript" src="<%=contextPath%>/app/app.nocache.js"></script>

</head>

<body>
  <div id="rootPanel" class="container"></div>
  <div class="container">
    <hr>
    <!-- Footer -->
    <footer>
      <div class="row">
        <div class="col-lg-12">
          <p>Copyright &copy; Your Website 2014</p>
        </div>
      </div>
    </footer>
  </div>
  <!-- /.container -->

  <iframe src="javascript:''" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
  <script src="js/jquery.js"></script>
  <script src="<%=contextPath%>/js/bootstrap.min.js"></script>
</body>
</html>
