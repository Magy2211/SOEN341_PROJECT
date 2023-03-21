<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html>
<html>
<head>
	<title>Job Posting Website</title>
	<link rel="stylesheet" type="text/css" href="viewJobPostings.css">
</head>
<body>
	<header>
		<div class="container">
			<h1>Job Posting Website</h1>
			<nav>
				<ul>
					<li><a href="#">Home</a></li>
					<li><a href="#">Jobs</a></li>
					<li><a href="#">Employers</a></li>
					<li><a href="#">About</a></li>
					<li><a href="#">Contact</a></li>
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
			<h2>Featured Jobs</h2>
	
		</div>
	</section>


	<title>Job Postings</title>
	<style>
	.job-box {
		border: 1px solid black;
		padding: 10px;
		margin-bottom: 10px;
	}
	</style>
</head>
<body>

	<%
        ArrayList<JobPostings> std = (ArrayList<JobPostings>)request.getAttribute("jobPostings");
        for(JobPostings jobPosting:std){%>
		<div class="job-box">
			<h2><a href="viewAJobPostingServlet?id=<%=jobPosting.getId()%>&studentEmail=${studentEmail}" ><%=jobPosting.getTitle()%></a></h2>
			<p><strong>Company:</strong> <%=jobPosting.getCompany()%></p>
			<p><%=jobPosting.getDescription()%></p>
		</div>
	<%}%>
</body>
</html>
     
</body>
</html>

