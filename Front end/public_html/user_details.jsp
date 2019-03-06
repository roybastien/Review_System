<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<title>U-Track</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="./css/bootstrap.css" type="text/css">


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
String fullname = request.getParameter("name");

if( username ==  null){

  response.sendRedirect("index.html");

} else if (username != null && fullname == null){

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
          <h2 class="form-signin-heading" align="center">Please enter your details.</h2>
          <form class="form-signin" align="center" method="post" action="user_details.jsp">
            <input type=hidden name="username" value="<%=username%>">
            <label for="inputName">Full Name</label>
            <input type="text" name="name" id="inputName" class="form-control" placeholder="John Doe" required>
            <label for="adminDrop">Administrator</label>
            <select class="form-control" name="admin" id="adminDrop">
                    <option value=0>Not Administrator</option>
                    <option value=1>Administrator</option>
            </select>
            <label for="street">Street Address</label>
            <input type="text" name="street" id="street" class="form-control" placeholder="123 Ash Lane" required>
            <label for="city">City</label>
            <input type="text" name="city" id="city" class="form-control" placeholder="Salt Lake City" required>
            <label for="state">State</label>
            <input type="text" name="state" id="state" class="form-control" placeholder="Utah" required>
            <label for="phone">State</label>
            <input type="text" name="phone" id="phone" class="form-control" placeholder="555-555-5555" required>
            <p></p>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
        </div>
<%

} else {
  String usernameIn = request.getParameter("username");
  String nameIn = request.getParameter("name");
  String adminIn = request.getParameter("admin");
  String streetIn = request.getParameter("street");
  String cityIn = request.getParameter("city");
  String stateIn = request.getParameter("state");
  String phoneIn = request.getParameter("phone");
  Connector connector = new Connector();
  Register register = new Register();
  String responseBack = register.setUserInfo(usernameIn, nameIn, adminIn, streetIn, cityIn, stateIn, phoneIn, connector.stmt);

  if(responseBack.contains("Success")){
    session.setAttribute("username", usernameIn);

    if (adminIn.equals("1")){
      session.setAttribute("admin", 1);
      response.sendRedirect("admin_homepage.jsp");
    }
    else{
      session.setAttribute("admin", 0);
      response.sendRedirect("user_homepage.jsp");
    }

  }
  else {
    out.println(responseBack);
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
          <div class="container" align="center"  style="padding-top: 75px;">
          <form class="form-signin" align="center" method="post" action="register.jsp">
            <h2 class="form-signin-heading">Your information could not be entered.</h2>
            <h3 class="form-signin-heading">Please try again.</h3>
            <input type=hidden name="username" value="<%=username%>">
            <label for="inputName">Full Name</label>
            <input type="text" name="name" id="inputName" class="form-control" placeholder="John Doe" required>
              <label for="adminDrop">Administrator</label>
            <select class="form-control" name="admin" id="adminDrop">
                    <option value=0>Not Administrator</option>
                    <option value=1>Administrator</option>
            </select>
            <label for="street">Street Address</label>
            <input type="text" name="street" id="street" class="form-control" placeholder="123 Ash Lane" required>
            <label for="city">City</label>
            <input type="text" name="city" id="city" class="form-control" placeholder="Salt Lake City" required>
            <label for="state">State</label>
            <input type="text" name="state" id="state" class="form-control" placeholder="Utah" required>
            <label for="phone">State</label>
            <input type="text" name="phone" id="phone" class="form-control" placeholder="555-555-5555" required>
            <p></p>
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
