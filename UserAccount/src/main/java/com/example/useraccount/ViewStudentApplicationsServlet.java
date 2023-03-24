package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "viewStudentApplicationsServlet", value = "/viewStudentApplicationsServlet")
public class ViewStudentApplicationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));
        String interview = request.getParameter("interview");
        String employerEmail = (String) request.getSession().getAttribute("employerEmail");
        String studentEmail;
        String studentFirstName;
        String studentLastName;
        String fieldOfStudy;

        try {
            List<StudentInformation> studentInformation = new ArrayList<>();

            ResultSet resultSet2;
            PreparedStatement statement2;

            if (interview.equals("false")){
                statement2 = connection.prepareStatement("select * from applications where jobPostingID = ?");
                statement2.setInt(1, jobPostingID);
            }
            else {
                statement2 = connection.prepareStatement("select * from applications where jobPostingID = ? AND Status = ?");
                statement2.setInt(1, jobPostingID);
                statement2.setString(2, "Selected for an interview");
            }
            resultSet2 = statement2.executeQuery();
            while(resultSet2.next()){
                studentEmail = resultSet2.getString("studentEmail");
                PreparedStatement statement1 = connection.prepareStatement("select * from profile_information where email = ?");
                statement1.setString(1, studentEmail);
                ResultSet resultSet1 = statement1.executeQuery();
                if(resultSet1.next()) {
                    studentFirstName = resultSet1.getString("firstName");
                    studentLastName = resultSet1.getString("lastName");
                    fieldOfStudy = resultSet1.getString("fieldOfStudy");

                    studentInformation.add(new StudentInformation(studentFirstName, studentLastName, studentEmail, fieldOfStudy, studentEmail, "", "", null));
                }
            }
            request.setAttribute("studentInformation", studentInformation);
            request.setAttribute("jobPostingID", jobPostingID);
            request.setAttribute("employerEmail", employerEmail);
            request.getSession().setAttribute("interview", interview);
            RequestDispatcher view = request.getRequestDispatcher("/ViewStudentApplications.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}