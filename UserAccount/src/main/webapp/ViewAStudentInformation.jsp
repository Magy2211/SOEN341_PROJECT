<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Student name: ${studentInformation.getFirstName()} ${studentInformation.getLastName()}</h3>
<h3>Student field of study: ${studentInformation.getFieldOfStudy()}</h3>
<h3>Profile picture: </h3>
<img src="data:image/jpeg;base64,${javax.xml.bind.DatatypeConverter.printBase64Binary(studentInformation.getProfilePic())}" width="240" height="300"/>
<<h3>Resume:</h3>
<iframe src="data:application/pdf;base64,${studentInformation.getResumeBase64()}" width="100%" height="500px"></iframe>
<<h3>Cover Letter:</h3>
<iframe src="data:application/pdf;base64,${studentInformation.getCoverLetterBase64()}" width="100%" height="500px"></iframe>
<<h3>Transcript:</h3>
<iframe src="data:application/pdf;base64,${studentInformation.getTranscriptBase64()}" width="100%" height="500px"></iframe>
<%--<form action="applyForAJobPostingServlet?id=${id}&studentEmail=${studentEmail}" method="post">
  <input type="submit" value="Apply">
</form>--%>
</body>
</html>
