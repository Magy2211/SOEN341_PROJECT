<%@ page import="com.example.useraccount.StudentInformation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
  <tr>
    <th><b>Name</b></th>
    <th><b>Field of study</b></th>
  </tr>

  <%
    ArrayList<StudentInformation> std = (ArrayList<StudentInformation>)request.getAttribute("studentInformation");
    for(StudentInformation studentInformation:std){%>
  <tr>
    <td><%=studentInformation.getFirstName()%> <%=studentInformation.getLastName()%></td>
    <td><%=studentInformation.getFieldOfStudy()%></td>
    <td><label><a href="viewAStudentInformationServlet?studentEmail=<%=studentInformation.getEmail()%>&jobPostingID=${jobPostingID}">View student full information<br></a></label></td>
  </tr>
  <%}%>
</table>
</body>
</html>
