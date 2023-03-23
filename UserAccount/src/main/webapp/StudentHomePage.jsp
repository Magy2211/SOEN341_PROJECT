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
     <h4 style="color:orange;">User Profile</h4>

<div class="container">
<br/>
<form method="get" action="viewUserProfileServlet">
    <div class="form-group">
        <img src="data:image/jpeg;base64,${profilePic}" width="180" height=180/><br>
<h3>First Name: ${firstName}</h3>
    <h3>Last Name: ${lastName}</h3>
    <h3>Email: ${studentEmail}</h3>
        <h3>Engineering field of study: ${engineeringField}</h3>
       <%--<h3>Profile Picture </h3>
        <img src="https://www.champlainsaintlambert.ca/wp-content/uploads/2022/09/Untitled-design-60.png"  width="240" height="300" />

        <h3>Resume:</h3>

       <object data="https://writing.colostate.edu/guides/documents/resume/functionalsample.pdf" width="800"  height="500">
        </object>
        <h3>Cover Letter:</h3>
       <object data="https://www.kenan-flagler.unc.edu/wp-content/uploads/2019/12/cover-letter-samples.pdf"  width="800"  height="500">
        </object>
        <h3>Transcript:</h3>
        <object data="https://www.bath.ac.uk/publications/student-transcript-examples/attachments/Example-of-a-transcript.pdf"  width="800"  height="500">
        </object>--%>

        <%--<h3>Profile picture: </h3>--%>
        <%--<img src="data:image/jpeg;base64,${javax.xml.bind.DatatypeConverter.printBase64Binary(profilePic)}" width="240" height="300"/>--%>

        <%--<<h3>Resume:</h3>--%>
        <%--<iframe src="data:application/pdf;base64,${resume}" width="800" height="500"></iframe>--%>
        <a href="data:application/pdf;base64,${resume}" target="_blank">Resume</a><br>

        <%--<<h3>Cover Letter:</h3>--%>
        <%--<iframe src="data:application/pdf;base64,${coverLetter}" width="800" height="500"></iframe>--%>
        <a href="data:application/pdf;base64,${coverLetter}" target="_blank">Cover Letter</a><br>

        <%--<<h3>Transcript:</h3>--%>
        <%--<iframe src="data:application/pdf;base64,${transcript}" width="800" height="500"></iframe>--%>
        <a href="data:application/pdf;base64,${transcript}" target="_blank">Transcript</a><br>

    <label><a href="EditingUserProfile.html">Edit Profile Information<br></a></label>
        <label><a href="viewJobPostingsServlet">View job postings<br></a></label>
        <label><a href="viewApplicationsServlet?">Applications<br></a></label>
        <label><a href="viewInterviewsServlet?">Interviews<br></a></label>
    </div>
</form>
</div>
</form>
</body>
</html>
