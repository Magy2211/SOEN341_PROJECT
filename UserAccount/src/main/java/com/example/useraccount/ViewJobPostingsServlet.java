package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "viewJobPostingsServlet", value = "/viewJobPostingsServlet")
public class ViewJobPostingsServlet extends HttpServlet {
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
        try {
            PreparedStatement statement = connection.prepareStatement("select * from job_postings");
            ResultSet resultSet = statement.executeQuery();
            List<JobPostings> jobPostings = new ArrayList<>();
            while (resultSet.next())
            {
                title = resultSet.getString("Title");
                description = resultSet.getString("Description");
                emailEmployer = resultSet.getString("email");

                PreparedStatement statement1 = connection.prepareStatement("select * from employer_profile_information where email = ?");
                statement1.setString(1, emailEmployer);
                ResultSet resultSet1 = statement1.executeQuery();
                if(resultSet1.next()) {
                    company = resultSet1.getString(3);
                }
                jobPostings.add(new JobPostings(title, description, company));
            }
            request.setAttribute("jobPostings", jobPostings);
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
