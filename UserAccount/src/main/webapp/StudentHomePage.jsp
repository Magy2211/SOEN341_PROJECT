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
<form method="get" action="viewUserProfileServlet">
    <div class="form-group">
<h3>First Name: ${firstName}</h3>
    <h3>Last Name: ${lastName}</h3>
    <h3>Email: ${email}</h3>
        <h3>Engineering field of study: ${engineeringField}</h3>
        <h3>Profile picture: </h3>
        <img src="data:image/jpeg;base64,${javax.xml.bind.DatatypeConverter.printBase64Binary(profilePic)}" width="240" height="300"/>
        <<h3>Resume:</h3>
        <iframe src="data:application/pdf;base64,${resume}" width="100%" height="500px"></iframe>
        <<h3>Cover Letter:</h3>
        <iframe src="data:application/pdf;base64,${coverLetter}" width="100%" height="500px"></iframe>
        <<h3>Transcript:</h3>
        <iframe src="data:application/pdf;base64,${transcript}" width="100%" height="500px"></iframe>
    <label><a href="EditingUserProfile.html">Edit Profile Information<br></a></label>
        <label><a href="viewJobPostingsServlet?studentEmail=${email}">View job postings<br></a></label>
    </div>
</form>
</div>
</body>
</html>
