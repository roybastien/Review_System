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
String POIname = request.getParameter("POIname");

if (session.getAttribute("username") == null){
  response.sendRedirect("logout.jsp");
}

else if ((Integer)session.getAttribute("admin") == 0){
  response.sendRedirect("user_homepage.jsp");
}

else if(POIname == null){
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
      <h2 align="center"><%=session.getAttribute("username")%>, please enter the point of interest information below:</h2>
      <p></p>
      <form class="form-signin" align="center" method="post" action="newPOI.jsp">
        <label for="POIname">Point of Interest Name</label>
        <input type="text" name="POIname" id="POIname" class="form-control" placeholder="Starbucks" required>
        <label for="street">Street Address</label>
        <input type="text" name="street" id="street" class="form-control" placeholder="123 Ash Lane" required>
        <label for="city">City</label>
        <input type="text" name="city" id="city" class="form-control" placeholder="Salt Lake City" required>
        <label for="state">State</label>
        <input type="text" name="state" id="state" class="form-control" placeholder="Utah" required>
        <label for="phone">Phone Number</label>
        <input type="text" name="phone" id="phone" class="form-control" placeholder="555-555-5555" required>
          <label for="url">Point of Interest URL</label>
          <input type="text" name="url" id="url" class="form-control" placeholder="www.starbucks.com" required>
          <label for="year">Year Established</label>
          <input type="text" name="year" id="year" class="form-control" placeholder="1974" required>
          <label for="hours">Hours</label>
          <input type="text" name="hours" id="city" class="form-control" placeholder="6am - 12pm" required>
          <label for="price">Estimated Price per Person</label>
          <input type="text" name="price" id="price" class="form-control" placeholder="7" required>
          <label for="catagory">Catagory</label>
          <input type="text" name="catagory" id="catagory" class="form-control" placeholder="Coffee" required>
          <div class="form-group">
              <label for="keywords">Keywords (comma seperated please):</label>
              <textarea class="form-control" name="keywords" rows="2" id="keywords"></textarea>
          </div>
        <p></p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
      </form>
    </div>
  </div>

  <%
}

else if (POIname != null){
  String poiNameIn = request.getParameter("POIname");
  String streetIn = request.getParameter("street");
  String cityIn = request.getParameter("city");
  String stateIn = request.getParameter("state");
  String phoneIn = request.getParameter("phone");
  String urlIn = request.getParameter("url");
  String yearIn = request.getParameter("year");
  String hoursIn = request.getParameter("hours");
  String priceIn = request.getParameter("price");
  String catagoryIn = request.getParameter("catagory");
  String keywordsIn = request.getParameter("keywords");
  Connector connector = new Connector();
  String usr = (String)session.getAttribute("username");
  Boolean admn;
  if ((Integer)session.getAttribute("admin") == 0){
    admn = false;
  }
  else{
    admn = true;
  }

  FunctionSet fs = new FunctionSet(usr, admn, connector.stmt);
  String responseBack = fs.enterPOIJSP(poiNameIn, streetIn, cityIn, stateIn, phoneIn, urlIn, yearIn,
                hoursIn, priceIn, catagoryIn, keywordsIn);
  String success = "";
  if(responseBack.contains("Success")){
    success = "Success, information entered!";
  }
  else{
    success = "Sorry, your information could not be entered.";
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
      <div class="col-sm-3">
      </div>
      <div class="col-sm-6">
        <div class="text-center">
        <h2>
          <%=success%>
        </h2>
        <div class="text-center">
        <a href="admin_homepage.jsp" class="btn btn-md btn-info" align="center" role="button">Return Home</a>
      </div>
    </div>
      </div>
      <div class="col-sm-3">
      </div>
    </div>
  </div>


  <%
}

%>


<!-- jQuery -->
<script src="./js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="./js/bootstrap.min.js"></script>


</body>
</html>
