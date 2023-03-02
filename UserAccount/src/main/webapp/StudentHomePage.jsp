<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="viewUserProfileServlet">
<h3>First Name: ${firstName}</h3>
    <h3>Last Name: ${lastName}</h3>
    <h3>Email: ${email}</h3>
    <h3>Profile picture: ${profilePic}</h3>
    <h3>Resume: ${resume}</h3>
    <h3>Cover Letter: ${coverLetter}</h3>
    <h3>Transcript: ${transcript}</h3>
    <h3>Engineering field of study: ${engineeringField}</h3>
</form>
</body>
</html>
