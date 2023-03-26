<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="EmployerHomePage.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul>
                <li><a href="viewCreatedJobPostingsServlet?interview=false">Home</a></li>
                <li><a href="viewEmployerProfileServlet">Profile</a></li>
                <li><a href="AddJobPosting.html">Add Jobs </a></li>
                <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=employer">About</a></li>
            </ul>
        </nav>
    </div>
</header>
<%--<img src="data:image/jpeg;base64,${studentInformation.getProfilePic()}" width="180" height=180/><br>--%>
<h3>Student name: ${studentInformation.getFirstName()} ${studentInformation.getLastName()}</h3>
<h3>Student field of study: ${studentInformation.getFieldOfStudy()}</h3>
<%--<h3>Profile picture: </h3>--%>
<%--<img src="data:image/jpeg;base64,${javax.xml.bind.DatatypeConverter.printBase64Binary(studentInformation.getProfilePic())}" width="240" height="300"/>
<<h3>Resume:</h3>
<iframe src="data:application/pdf;base64,${studentInformation.getResumeBase64()}" width="100%" height="500px"></iframe>
<<h3>Cover Letter:</h3>
<iframe src="data:application/pdf;base64,${studentInformation.getCoverLetterBase64()}" width="100%" height="500px"></iframe>
<<h3>Transcript:</h3>
<iframe src="data:application/pdf;base64,${studentInformation.getTranscriptBase64()}" width="100%" height="500px"></iframe>--%>

<a href="data:application/pdf;base64,${studentInformation.getResumeBase64()}" target="_blank">Resume</a><br>
<a href="data:application/pdf;base64,${studentInformation.getCoverLetterBase64()}" target="_blank">Cover Letter</a><br>
<a href="data:application/pdf;base64,${studentInformation.getTranscriptBase64()}" target="_blank">Transcript</a><br>
<%
    String status = (String) request.getAttribute("status");
    if (status.equals("Applied to")) {
%>
<form action="selectStudentForInterviewServlet?jobPostingID=${jobPostingID}&studentEmail=${studentEmail}" method="post">
    <input type="submit" value="Select for an interview">
</form>
<%}%>
</body>
</html>
