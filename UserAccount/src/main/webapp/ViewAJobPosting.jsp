<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="get" action="/viewAJobPostingServlet">
  <h3>Job Title: ${jobTitle}</h3>
  <h3>Employer First Name: ${employerFirstName}</h3>
    <h3>Employer Last Name: ${employerLastName}</h3>
  <h3>Job Description: ${jobDescription}</h3>
</form>
</body>
</html>