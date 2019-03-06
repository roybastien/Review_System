<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<title>U-Track</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/bootstrap.css" type="text/css">
<%-- <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet'  type='text/css'> --%>
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Lobster" />
</head>


<script LANGUAGE="javascript">


</script>


</head>
<style>
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
.navbar-custom {
  background-color: #CC0000;
}

.navbar .navbar-nav {
    display: inline-block;
    float: none;
}

.navbar .navbar-collapse {
    text-align: center;
}

.navbar .navbar-collapse .utrack {
  color: black;
  font-size: 85px;
  font-family: Lobster;
}


</style>
<body style="background-color: white">
<%

if (session.getAttribute("username") == null){
  response.sendRedirect("logout.jsp");
}
else if ((Integer)session.getAttribute("admin") == 0){
  response.sendRedirect("user_homepage.jsp");
}
%>
<nav class="navbar navbar-default navbar-custom" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="admin_homepage.jsp" style="color: black;">Home</a>
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse navbar-ex1-collapse">
  <ul class="nav navbar-nav">
      <li><a href="admin_homepage.jsp" class="utrack">U-Track</a></li>
   </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="logout.jsp" style="color: black;">Logout</a></li>
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>

  <div class="container">
    <div class="row">
      <div class="col-sm-4">
        One of three columns
      </div>
      <div class="col-sm-4">
        One of three columns
      </div>
      <div class="col-sm-4">
        One of three columns
      </div>
    </div>
  </div>




<!-- jQuery -->
<script src="./js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="./js/bootstrap.min.js"></script>


</body>
</html>
