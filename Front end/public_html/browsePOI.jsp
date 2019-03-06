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
String drop1 = request.getParameter("attributeDrop1");
String submitted = request.getParameter("submitted");

if (session.getAttribute("username") == null){
  response.sendRedirect("logout.jsp");
}


else if(drop1 == null && submitted == null){
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
      <h2 align="center"><%=session.getAttribute("username")%>, you can browse by the attributes below.</h2>
      <p></p>
      <form class="form-signin" align="center" method="get" action="browsePOI.jsp">
        <%-- <label for="attributeDrop">How useful did you find this review?</label> --%>
        <select class="form-control" name="attributeDrop1" id="attributeDrop1">
          <option value="0">None</option>
          <option value="1">Price Range</option>
          <option value="2">City</option>
          <option value="3">State</option>
          <option value="4">POI Name keyword</option>
          <option value="5">Catagory</option>
        </select>
        <p></p>
        <select class="form-control" name="attributeDrop2" id="attributeDrop2">
          <option value="0">None</option>
          <option value="1">Price Range</option>
          <option value="2">City</option>
          <option value="3">State</option>
          <option value="4">POI Name keyword</option>
          <option value="5">Catagory</option>
        </select>
        <p></p>
        <select class="form-control" name="attributeDrop3" id="attributeDrop3">
          <option value="0">None</option>
          <option value="1">Price Range</option>
          <option value="2">City</option>
          <option value="3">State</option>
          <option value="4">POI Name keyword</option>
          <option value="5">Catagory</option>
        </select>
        <p></p>
        <select class="form-control" name="attributeDrop4" id="attributeDrop4">
          <option value="0">None</option>
          <option value="1">Price Range</option>
          <option value="2">City</option>
          <option value="3">State</option>
          <option value="4">POI Name keyword</option>
          <option value="5">Catagory</option>
        </select>
        <p></p>
        <select class="form-control" name="attributeDrop5" id="attributeDrop5">
          <option value="0">None</option>
          <option value="1">Price Range</option>
          <option value="2">City</option>
          <option value="3">State</option>
          <option value="4">POI Name keyword</option>
          <option value="5">Catagory</option>
        </select>
        <p></p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
      </form>
    </div>
  </div>

  <%
}

else if (drop1 != null && submitted == null){
  String usr = (String)session.getAttribute("username");
  String drop1In = request.getParameter("attributeDrop1");
  String drop2In = request.getParameter("attributeDrop2");
  String drop3In = request.getParameter("attributeDrop3");
  String drop4In = request.getParameter("attributeDrop4");
  String drop5In = request.getParameter("attributeDrop5");
  String choices = "";
  if (!drop1In.equals("0") && !choices.contains(drop1In)){
    choices += (drop1In+"#");
  }
  if (!drop2In.equals("0") && !choices.contains(drop2In)){
    choices += (drop2In+"#");
  }
  if (!drop3In.equals("0") && !choices.contains(drop3In)){
    choices += (drop3In+"#");
  }
  if (!drop4In.equals("0") && !choices.contains(drop4In)){
    choices += (drop4In+"#");
  }
  if (!drop5In.equals("0") && !choices.contains(drop5In)){
    choices += (drop5In+"#");
  }
  String success = choices;

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

        <form class="form-signin" align="center" method="get" action="browsePOI.jsp">
          <input type="hidden" name="submitted" value="submitted">

            <%
            if(success.contains("1")){
              %>
              <label for="minPrice">Enter a minimum price.</label>
              <input type="text" name="minPrice" id="minPrice" class="form-control" placeholder="2.00" required>
              <p></p>
              <label for="maxPrice">Enter a maximum price.</label>
              <input type="text" name="maxPrice" id="maxPrice" class="form-control" placeholder="25.00" required>
              <p></p>
              <%
            }
            if(success.contains("2")){
              %>
              <label for="city">Enter a city.</label>
              <input type="text" name="city" id="city" class="form-control" placeholder="Salt Lake City" required>
              <p></p>
              <%
            }
            if(success.contains("3")){
              %>
              <label for="state">Enter a state.</label>
              <input type="text" name="state" id="state" class="form-control" placeholder="Utah" required>
              <p></p>
              <%
            }
            if(success.contains("4")){
              %>
              <label for="keyword">Enter a POI name keyword.</label>
              <input type="text" name="keyword" id="keyword" class="form-control" placeholder="Ruths" required>
              <p></p>
              <%
            }
            if(success.contains("5")){
              %>
              <label for="catagory">Enter a POI name keyword.</label>
              <input type="text" name="catagory" id="catagory" class="form-control" placeholder="breakfast" required>
              <p></p>
              <%
            }
            %>
            <label for="sort">How would you like the results sorted?</label>
            <select class="form-control" name="sort" id="sort">
              <option value="1">Price</option>
              <option value="2">Average Score of the Feedbacks</option>
              <option value="3">Average Score of the Trusted Feedbacks</option>
            </select>
            <p></p>
          <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form>
    </div>
      </div>
      <div class="col-sm-3">
      </div>
    </div>
  </div>


  <%

}
else if (submitted != null){
  String success = "";
  String urlString = request.getQueryString();
    String usr = (String)session.getAttribute("username");
  Boolean admn;
  if ((Integer)session.getAttribute("admin") == 0){
    admn = false;
  }
  else{
  admn = true;
  }
  Connector connector = new Connector();
  FunctionSet fs = new FunctionSet(usr, admn, connector.stmt);

  String text = "";
  String responseBack = fs.browsePOIWeb(urlString);
  String[] resultsArr = responseBack.split("#");
  if(responseBack.contains("Success")){
    success = "Your search results are below";
  }
  else{
    success = "Sorry, your information could not be entered.\n" + responseBack;
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
        <table class="table table-striped">
          <tbody>
            <%
            if (resultsArr.length == 1){
              %>
                <tr>
                  <td align="center">No results matched your query.</td>
                </tr>
                <%
            }
            else{
              for(int j = 1; j < resultsArr.length; j++){
              %>
                <tr>
                  <td align="center"><%=resultsArr[j]%></td>
                </tr>
                <%
              }
            }

            %>
          </tbody>
        </table>
        <a href="browsePOI.jsp" class="btn btn-lg btn-primary btn-block" role="button">Search Again</a>
        <a href="admin_homepage.jsp" class="btn btn-lg btn-primary btn-block" role="button">Go Home</a>
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
