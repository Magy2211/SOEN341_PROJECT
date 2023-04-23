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

/*
 * The purpose of this servlet is to
 * display all the job postings created
 * by that employer
 */
@WebServlet(name = "viewCreatedJobPostingsServlet", value = "/viewCreatedJobPostingsServlet")
public class ViewCreatedJobPostingsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    @Override
    public void init() throws ServletException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String employerEmail = (String) request.getSession().getAttribute("employerEmail");
        String interview = request.getParameter("interview");

        //Creating and initialing an array of job postings
        List<JobPostings> jobPostings = new ArrayList<>();

        try {

            //Connect to a table and select all the job postings created by that employer
            PreparedStatement statement = connection.prepareStatement("select * from job_postings where email = ?");
            statement.setString(1, employerEmail);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) { //Loop for all the job postings that employer created

                //Create and initialise a job posting variable
                JobPostings jobPosting = new JobPostings();

                //Get the job posting information from the table
                jobPosting.setTitle(resultSet.getString("Title"));
                jobPosting.setId(resultSet.getInt("id"));
                jobPosting.setSalary(resultSet.getString("salary"));
                jobPosting.setDeadline(resultSet.getString("deadline"));
                jobPosting.setJobLocation(resultSet.getString("jobLocation"));

                //Add the job posting to the array of job postings
                jobPostings.add(jobPosting);
            }

            //Sending attributes to other servlets or pages
            request.setAttribute("jobPostings", jobPostings);
            request.setAttribute("interview", interview);

            //Redirect the employer that displays all the job postings created by that employer
            RequestDispatcher view = request.getRequestDispatcher("/ViewCreatedJobPostings.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //Close the connection with the database
    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
