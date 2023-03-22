package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "viewApplicationsServlet", value = "/viewApplicationsServlet")
public class ViewApplicationsServlet extends HttpServlet {
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
        String title;
        String description;
        String emailEmployer;
        String company = "";
        String studentEmail = (String) request.getSession().getAttribute("studentEmail");
        String status;
        
        String salary;
        String deadline;
        String jobLocation;
        
        int id;
        
        try {
            PreparedStatement statement = connection.prepareStatement("select * from job_postings");
            ResultSet resultSet = statement.executeQuery();
            List<JobPostings> jobPostings = new ArrayList<>();
            while (resultSet.next())
            {
                title = resultSet.getString("Title");
                description = resultSet.getString("Description");
                emailEmployer = resultSet.getString("email");
                id = resultSet.getInt("id");
                status = resultSet.getString("Status");
                
                salary = resultSet.getString("salary");
                deadline = resultSet.getString("deadline");
                jobLocation = resultSet.getString("jobLocation");

                PreparedStatement statement1 = connection.prepareStatement("select * from employer_profile_information where email = ?");
                statement1.setString(1, emailEmployer);
                ResultSet resultSet1 = statement1.executeQuery();
                if(resultSet1.next()) {
                    company = resultSet1.getString(3);
                }
                PreparedStatement statement2 =connection.prepareStatement("select * from applications where studentEmail = ? AND jobPostingID = ?");
                statement2.setString(1, studentEmail);
                statement2.setInt(2, id);
                ResultSet resultSet2 = statement2.executeQuery();

                if (resultSet2.next()) {
                    status = resultSet2.getString("Status");
                    jobPostings.add(new JobPostings(id, title, description, company, status, salary, deadline, jobLocation));
                }
            }
            request.setAttribute("jobPostings", jobPostings);
            request.setAttribute("studentEmail", studentEmail);
            RequestDispatcher view = request.getRequestDispatcher("/viewJobPostings.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
