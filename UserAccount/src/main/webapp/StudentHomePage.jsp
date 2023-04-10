<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title> Jobify </title>
    <link rel="stylesheet" type="text/css" href="EmployerHomePage.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul>
                <li><a href="viewJobPostingsServlet">Home</a></li>
                <li><a href="viewUserProfileServlet">Profile</a></li>
                <li><a href="viewApplicationsServlet">Applications</a></li>
                <li><a href="viewInterviewsServlet">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=student">About</a></li>

            </ul>
        </nav>
    </div>
</header>

<head>

    <link rel="stylesheet" type="text/css" href="StudentHomePage.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>

</head>

<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form action="createUserProfileServlet" method="post" enctype="multipart/form-data">


    <!--  <h3 style="color:#FF77FF;">*User Profile*</h3>
     -->
    <h3 style="color:orange;"> User Profile </h3>

    <div class="container">
        <br/>
        <form method="get" action="viewUserProfileServlet">
            <div class="form-group">
                <img src="data:image/jpeg;base64,${studentInformation.getProfilePicture()}" width="180" height=180/><br>
                <h4>First Name: ${studentInformation.getFirstName()}</h4>
                <h4>Last Name: ${studentInformation.getLastName()}</h4>
                <h4>Email: ${studentInformation.getEmail()}</h4>
                <h4>Engineering field of study: ${studentInformation.getFieldOfStudy()}</h4>
                <a style="color:blue;" href="data:application/pdf;base64,${studentInformation.getResumeBase64()}" target="_blank">Resume</a><br>
                <a style="color:blue;" href="data:application/pdf;base64,${studentInformation.getCoverLetterBase64()}" target="_blank">Cover Letter</a><br>
                <a style="color:blue;" href="data:application/pdf;base64,${studentInformation.getTranscriptBase64()}" target="_blank">Transcript</a><br>
                <button><a style="color:white;" href="EditingUserProfile.html">Edit Profile Information<br></a></button>
            </div>
        </form>
    </div>
</form>
</body>
</html>