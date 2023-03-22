package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "viewCreatedJobPostingsServlet", value = "/viewCreatedJobPostingsServlet")
public class ViewCreatedJobPostingsServlet extends HttpServlet {

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employerEmail = (String) request.getSession().getAttribute("employerEmail");
        String interview = request.getParameter("interview");
        int id;
        String title;
        String description = "";
        String company = "";

        try {
            PreparedStatement statement = connection.prepareStatement("select * from job_postings where email = ?");
            statement.setString(1, employerEmail);
            ResultSet resultSet = statement.executeQuery();
            List<JobPostings> jobPostings = new ArrayList<>();
            while (resultSet.next())
            {
                title = resultSet.getString("Title");
                id = resultSet.getInt("id");

                jobPostings.add(new JobPostings(id, title, description, company, ""));
            }
            request.setAttribute("jobPostings", jobPostings);
            request.setAttribute("interview", interview);
            RequestDispatcher view = request.getRequestDispatcher("/ViewCreatedJobPostings.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
