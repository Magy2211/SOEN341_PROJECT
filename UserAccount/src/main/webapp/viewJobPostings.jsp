<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Job postings</title>
</head>
<body>
<form method="get" action="viewJobPostingsServlet">
    <table>
      <tr>
        <th><b>Title</b></th>
        <th><b>Organization</b></th>
      </tr>

      <%
        ArrayList<JobPostings> std = (ArrayList<JobPostings>)request.getAttribute("jobPostings");
        for(JobPostings jobPosting:std){%>
      <tr>
        <td><label><a href="viewAJobPostingServlet?title=<%=jobPosting.getTitle()%>" ><%=jobPosting.getTitle()%><br></a></label></td>
        <td><%=jobPosting.getCompany()%></td>
      </tr>
      <%}%>
  </table>
</form>
</body>
</html>
