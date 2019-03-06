<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<title>U-Track</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/bootstrap.css" type="text/css">
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Lobster" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
</head>


<script LANGUAGE="javascript">
$(function() {
  $( "#datepicker" ).datepicker();
});

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

else if (request.getParameter("POIname") == null){

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
          <%=session.getAttribute("username")%>, where did you visit?
        </h2>
        <div class="text-center">
          <form class="form-signin" align="center" method="post" action="recordVisit.jsp">
            <input type="hidden" name="confirm" value="notConfirmed">
            <label for="POIname">Point of Interest Name</label>
            <input type="text" name="POIname" id="POIname" class="form-control" placeholder="Starbucks" required>
            <p></p>
            <label for="datepicker">Date</label>
            <input type="text" id="datepicker" name="date" class="form-control" placeholder="04/22/2016" required>
            <p></p>
            <label for="numberofheads">How many people were in your party?</label>
            <input type="text" name="numberofheads" id="numberofheads" class="form-control" placeholder="5" required>
            <p></p>
            <label for="avg_cost">How much did you spend in total?</label>
            <input type="text" name="avg_cost" id="avg_cost" class="form-control" placeholder="5.75" required>
            <p></p>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
          </form>
      </div>
    </div>
      </div>
      <div class="col-sm-3">
      </div>
    </div>
  </div>

  <%
}

else if (request.getParameter("confirm").equals("notConfirmed")){
  //out.println("notConfirmed");
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
            Please confirm the information below.
          </h2>
          <div class="text-center">
            <form class="form-signin" align="center" method="post" action="recordVisit.jsp">
              <input type="hidden" name="confirm" value="confirmed">
              <label for="POIname">Point of Interest Name</label>
              <input type="text" name="POIname" id="POIname" class="form-control" value=<%=request.getParameter("POIname")%> required>
              <p></p>
              <label for="datepicker">Date</label>
              <input type="text" id="datepicker" name="date" class="form-control" value=<%=request.getParameter("date")%> required>
              <p></p>
              <label for="numberofheads">How many people were in your party?</label>
              <input type="text" name="numberofheads" id="numberofheads" class="form-control" value=<%=request.getParameter("numberofheads")%> required>
              <p></p>
              <label for="avg_cost">How much did you spend in total?</label>
              <input type="text" name="avg_cost" id="avg_cost" class="form-control" value=<%=request.getParameter("avg_cost")%> required>
              <p></p>
              <a href="admin_homepage.jsp" class="btn btn-lg btn-danger btn-block" role="button">Cancel</a>
              <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            </form>
        </div>
      </div>
        </div>
        <div class="col-sm-3">
        </div>
      </div>
    </div>

    <%

}

else if (request.getParameter("confirm").equals("confirmed")){
  //out.println("confirmed");
  String POInameIn = request.getParameter("POIname");
  String dateIn = request.getParameter("date");
  String dateFormatted = dateIn.substring(6,10) + "-" + dateIn.substring(0,2) + "-" + dateIn.substring(3,5);
  String numberofheadsIn = request.getParameter("numberofheads");
  String avg_costIn = request.getParameter("avg_cost");
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
  String responseBack = fs.recordSimpleVisit(usr, POInameIn, dateFormatted, numberofheadsIn, avg_costIn);

  if (responseBack.contains("Thanks")){
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
              Success!!
            </h2>
            <p></p>
            <a href="admin_homepage.jsp" class="btn btn-lg btn-Success btn-block" role="button">Go Home</a>

          </div>
        </div>
          </div>
          <div class="col-sm-3">
          </div>
        </div>
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
              Your request could not be processed.
            </h2>
            <h2><%=responseBack%></h2>
            <a href="admin_homepage.jsp" class="btn btn-lg btn-danger btn-block" role="button">Go Home</a>
          </div>
        </div>
          </div>
          <div class="col-sm-3">
          </div>
        </div>


      <%
  }
}
else{
  out.println("Shouldn't get here.");
}

%>




<!-- jQuery -->
<!--<script src="./js/jquery.js"></script>-->

<!-- Bootstrap Core JavaScript -->
<script src="./js/bootstrap.min.js"></script>


</body>
</html>
