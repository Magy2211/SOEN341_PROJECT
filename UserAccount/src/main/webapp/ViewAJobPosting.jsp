<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h3>Job Title: ${jobTitle}</h3>
  <h3>Employer First Name: ${employerFirstName}</h3>
    <h3>Employer Last Name: ${employerLastName}</h3>
    <h3>Job location: ${jobLocation}</h3>
    <h3>Salary: ${salary}</h3>
    <h3>Deadline to apply: ${deadline}</h3>
  <h3>Job Description: ${jobDescription}</h3>
    <form action="applyForAJobPostingServlet?id=${id}&studentEmail=${studentEmail}" method="post">
        <input type="submit" value="Apply">
    </form>
</body>
</html>