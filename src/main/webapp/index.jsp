<!DOCTYPE html>
<%
   String contextPath = getServletContext().getContextPath();
%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Java Regular Expression Tester | OCPsoft</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le styles -->
<link href="<%=contextPath%>/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<link href="<%=contextPath%>/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
<link href="<%=contextPath%>/css/application.css" rel="stylesheet">

<!-- Google Fonts -->
<link href='http://fonts.googleapis.com/css?family=Gudea:400,700'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Inconsolata'
	rel='stylesheet' type='text/css'>

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="<%=contextPath%>/favicon.ico">
<link rel="apple-touch-icon" href="<%=contextPath%>/favicon.ico">

<link rel="apple-touch-icon" sizes="72x72"
	href="<%=contextPath%>/images/apple-touch-icon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="<%=contextPath%>/images/apple-touch-icon-114x114.png">

<script type="text/javascript" language="javascript"
	src="<%=contextPath%>/app/app.nocache.js"></script>
</head>

<body>

	<div id="rootPanel"></div>

	<iframe src="javascript:''" id="__gwt_historyFrame"
		style="width: 0; height: 0; border: 0"></iframe>

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<%=contextPath%>/js/jquery.min.js"></script>
	<script src="<%=contextPath%>/js/jquery-expandable.js"></script>
	<script src="<%=contextPath%>/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
