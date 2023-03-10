<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="CreatingUserProfile.css">
</head>
<body>
<div class="container">
<div class="header">
    <h1><%= "Home Page" %>
</div>
</h1>
<form method="post" action="viewUserProfileServlet">
    <div class="form-group">
<h3>First Name: ${firstName}</h3>
    <h3>Last Name: ${lastName}</h3>
    <h3>Email: ${email}</h3>
    <h3>Profile picture: ${profilePic}</h3>
    <h3>Resume: ${resume}</h3>
    <h3>Cover Letter: ${coverLetter}</h3>
    <h3>Transcript: ${transcript}</h3>
    <h3>Engineering field of study: ${engineeringField}</h3>
    <label><a href="EditingUserProfile.html">Edit Profile Information<br></a></label>
    </div>
</form>
</div>
</body>
</html>
