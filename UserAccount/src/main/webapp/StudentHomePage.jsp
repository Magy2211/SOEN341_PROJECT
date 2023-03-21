<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>

      <link rel="stylesheet" type="text/css" href="StudentHomePage.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>


</head>
<body>
    <div class="background">
        <div class="shape"></div>
        <div class="shape"></div>
    </div>
<form action="createUserProfileServlet" method="post" enctype="multipart/form-data">


<!--  <h3 style="color:#FF77FF;">*User Profile*</h3>
 -->
     <h3 style="color:orange;">*User Profile*</h3>

<div class="container">
<br/>
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
</form>
</body>
</html>
