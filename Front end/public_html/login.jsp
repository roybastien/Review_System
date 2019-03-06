<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<title>U-Track</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="./css/bootstrap.css" type="text/css">
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Lobster" />

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

String username = request.getParameter("username");

if( username ==  null){

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
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse navbar-ex1-collapse">
  <ul class="nav navbar-nav">
      <li><a href="logout.jsp" class="utrack">U-Track</a></li>
   </ul>
    <ul class="nav navbar-nav navbar-right">
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>
        <div class="container" align="center"  style="padding-top: 50px;">
        <form class="form-signin" align="center" method="post" action="login.jsp">
          <h2 class="form-signin-heading">Please sign in</h2>
          <label for="inputName">Username</label>
          <input type="text" name="username" id="inputName" class="form-control" placeholder="Username" required>
          <label for="inputPassword">Password</label>
          <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
          <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
        </div>
<%

} else {

  String usernameIn = request.getParameter("username");
  String passwordIn = request.getParameter("password");
  Connector connector = new Connector();
  Login login = new Login();
  String responseBack = login.verifyPassword(usernameIn, passwordIn, connector.stmt);

  if (responseBack.contains("Success") && responseBack.contains("admin")){
    session.setAttribute("username", usernameIn);
    session.setAttribute("admin", 1);
    response.sendRedirect("admin_homepage.jsp");
  }
  else if (responseBack.contains("Success") && !responseBack.contains("admin")){
    session.setAttribute("username", usernameIn);
    session.setAttribute("admin", 0);
    response.sendRedirect("user_homepage.jsp");
  }
  else if (responseBack.contains("Incorrect")){
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
      </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
          <li><a href="admin_homepage.jsp" class="utrack">U-Track</a></li>
       </ul>
        <ul class="nav navbar-nav navbar-right">
        </ul>
      </div><!-- /.navbar-collapse -->
    </nav>

            <div class="container" align="center"  style="padding-top: 50px;">
            <form class="form-signin" align="center" method="post" action="login.jsp">
              <h2 class="form-signin-heading">Your passorword was incorrect.</h2>
              <h3 class="form-signin-heading">Please try again.</h3>
              <label for="inputName">Username</label>
              <input type="text" name="username" id="inputName" class="form-control" placeholder="Username" required>
              <label for="inputPassword">Password</label>
              <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
              <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
          </form>
            </div>

    <%
  }
  else{
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
      </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
          <li><a href="logout.jsp" class="utrack">U-Track</a></li>
       </ul>
        <ul class="nav navbar-nav navbar-right">
        </ul>
      </div><!-- /.navbar-collapse -->
    </nav>
            <div class="container" align="center"  style="padding-top: 50px;">
            <form class="form-signin" align="center" method="post" action="login.jsp">
              <h2 class="form-signin-heading">Your information could not be found.</h2>
              <h3 class="form-signin-heading">Please register or try again.</h3>
              <label for="inputName">Username</label>
              <input type="text" name="username" id="inputName" class="form-control" placeholder="Username" required>
              <label for="inputPassword">Password</label>
              <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
              <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
          </form>
            </div>

    <%
  }
}

%>

<!-- jQuery -->
<script src="./js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="./js/bootstrap.min.js"></script>


</body>
</html>
