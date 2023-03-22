<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8"%>
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
					<li><a href="viewUserProfileServlet">Home</a></li>
					<li><a href="viewJobPostingsServlet">Jobs</a></li>
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
			<p><strong>Company:</strong> ${company}</p>
			<p><strong>Status:</strong>${status}</p>
			<p><strong>Location:</strong> ${jobLocation} </p>
			<p><strong>Salary:</strong> ${salary} </p>
			<p><strong>Deadline to apply:</strong> ${deadline} </p>
			<p>${description} </p>
		</div>
		
		 <form action="applyForAJobPostingServlet?id=${id}&studentEmail=${studentEmail}" method="post">
        <input type="submit" value="Apply">
    </form>
</body>
</html>
     
</body>
</html>

