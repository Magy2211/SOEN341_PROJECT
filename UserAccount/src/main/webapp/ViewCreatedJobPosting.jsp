<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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


<title>Job Posting</title>
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
    <h2>${jobPosting.getTitle()}</h2>
    <p><strong>Employer:</strong> ${jobPosting.getEmployerFirstName()} ${jobPosting.getEmployerLastName()}</p>
    <p><strong>Company:</strong> ${jobPosting.getCompany()}</p>
    <p><strong>Location:</strong> ${jobPosting.getJobLocation()} </p>
    <p><strong>Salary:</strong> ${jobPosting.getSalary()} </p>
    <p><strong>Deadline to apply:</strong> ${jobPosting.getDeadline()} </p>
    <p>${jobPosting.getDescription()} </p>
</div>

<form action="viewStudentApplicationsServlet?jobPostingID=${id}&interview=${interview}" method="post">
    <input type="submit" value="View student applications">
</form>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    request.getSession().setAttribute("jobPostingID", id);

    String interview = request.getParameter("interview");
    request.getSession().setAttribute("interview", interview);
%>
<form action="EditJobPosting.html" method="post">
    <input type="submit" value="Edit">
</form>
<form action="removeJobPostingServlet?jobPostingID=${id}" method="post">
    <input type="submit" value="Remove">
</form>
</body>

</html>

</body>
</html>

