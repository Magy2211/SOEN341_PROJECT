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
<form method="get" action="viewEmployerProfileServlet">
    <div class="form-group">
<h3>First Name: ${firstName}</h3>
    <h3>Last Name: ${lastName}</h3>
    <h3>Email: ${email}</h3>
    <h3>Company: ${company}</h3>
    <label><a href="EditingEmployerProfile.html">Edit Profile Information<br></a></label>
        <label><a href="AddJobPosting.html">Add a job posting<br></a></label>
    </div>
</form>
</div>
</body>
</html>
