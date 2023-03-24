<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<!DOCTYPE html>
<html>
<head>

	<title>Jobify</title>
	<link rel="stylesheet" type="text/css" href="viewJobPostings.css">
</head>
<body>
	<header>
		<div class="container">
			<h1>Jobify</h1>
			<nav>
				<ul>
				<li><a href="viewCreatedJobPostingsServlet?interview=false">Home</a></li>
                <li><a href="viewEmployerProfileServlet">Profile</a></li>
                <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=employer">About</a></li>
				</ul>
			</nav>
		</div>
	</header>
	
	<section id="search">
		<div class="container">
			<h2>Find your dream job today</h2>
			<form>
				<input type="text" placeholder="Search by keyword or location">
				<button type="submit">Search</button>
			</form>
		</div>
	</section>

	<section id="jobs">
		<div class="container">
			<h2>Applicant Information</h2>
	
		</div>
	</section>


	<title>Job Postings</title>
	<style>
	.job-box {
		border: 1px solid black;
		padding: 10px;
		margin-bottom: 10px;
	}
	 input[type = submit] {
            background-color: #333;
            border: none;
            text-decoration: none;
            color: #fff;;
            padding: 10px 20px;
            margin: 20px 20px;
            cursor: pointer;
         }
	</style>
</head>
<body>

		<div class="job-box">
			<h2>${jobTitle}</h2>
			<p><strong>Name:</strong> ${studentFirstName} ${studentLastName}</p>
			<p><strong>Field of study:</strong> ${studentInformation.getFieldOfStudy()}</p>
			<p><strong>Email:</strong> ${studentEmail}</p>
		</div>
		
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
     
</body>
</html>
