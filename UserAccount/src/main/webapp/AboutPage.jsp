<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>About us</title>
</head>
<body>
<header>
  <link rel="stylesheet" type="text/css" href="viewJobPostings.css">
  <div class="container">
    <h1>Jobify</h1>
    <nav>
      <ul>
        <% String account = request.getParameter("account");
          if(account.equals("student")) {
        %>
        <li><a href="viewJobPostingsServlet">Home</a></li>
        <li><a href="viewUserProfileServlet">Profile</a></li>
        <li><a href="viewApplicationsServlet">Applications</a></li>
        <li><a href="viewInterviewsServlet">Interviews</a></li>
        <li><a href="AboutPage.jsp?account=student">About</a></li>
        <%} else {%>
        <li><a href="viewCreatedJobPostingsServlet?interview=false">Home</a></li>
        <li><a href="viewEmployerProfileServlet">Profile</a></li>
        <li><a href="AddJobPosting.html">Add Jobs </a></li>
        <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
        <li><a href="AboutPage.jsp?account=employer">About</a></li>
        <%}%>
      </ul>
    </nav>
  </div>
</header>

<h2>About us</h2>
<h4>*Enter some information*</h4>
</body>
</html>
