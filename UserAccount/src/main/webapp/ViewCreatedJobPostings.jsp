<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Job postings</title>
</head>
<body>
<form method="get" action="viewCreatedJobPostingsServlet">
  <table>
    <tr>
      <th><b>Title</b></th>
    </tr>
    
    <%
      ArrayList<JobPostings> std = (ArrayList<JobPostings>)request.getAttribute("jobPostings");
      for(JobPostings jobPosting:std){%>
    <tr>
      <td><label><a href="viewStudentApplicationsServlet?jobPostingID=<%=jobPosting.getId()%>&employerEmail=${employerEmail}" ><%=jobPosting.getTitle()%><br></a></label></td>
    </tr>
    <%}%>
    
  </table>
</form>
</body>
</html>
