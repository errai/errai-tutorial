<!DOCTYPE html>
<%
   String contextPath = getServletContext().getContextPath();
%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Errai - Tutorial</title>
<meta name="description" content="">
<meta name="author" content="">

<script src="components/platform/platform.js"></script>
<link rel="import" href="<%=contextPath%>/components/paper-input/paper-input.html">
<link rel="import" href="<%=contextPath%>/components/paper-button/paper-button.html">
<link rel="import" href="<%=contextPath%>/components/core-icons/image-icons.html">

<link href="<%=contextPath%>/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=contextPath%>/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

<link href="<%=contextPath%>/css/application.css" rel="stylesheet">
<link href="<%=contextPath%>/css/polymer-styling.css" rel="stylesheet" shim-shadowdom>

<link href='http://fonts.googleapis.com/css?family=Gudea:400,700' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Inconsolata' rel='stylesheet' type='text/css'>

<link rel="shortcut icon" href="<%=contextPath%>/favicon.ico">
<link rel="apple-touch-icon" href="<%=contextPath%>/favicon.ico">

<link rel="apple-touch-icon" sizes="72x72" href="<%=contextPath%>/images/apple-touch-icon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114" href="<%=contextPath%>/images/apple-touch-icon-114x114.png">

<script type="text/javascript" language="javascript" src="<%=contextPath%>/app/app.nocache.js"></script>
</head>

<body>
  <div id="rootPanel"></div>

  <iframe src="javascript:''" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>

  <script src="<%=contextPath%>/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
