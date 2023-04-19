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
 * The purpose of this servlet is to allow the
 * student to view all the job postings applied to
 */
@WebServlet(name = "viewApplicationsServlet", value = "/viewApplicationsServlet")
public class ViewApplicationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String studentEmail = (String) request.getSession().getAttribute("studentEmail");

        //Creating and initialising a variable
        List<JobPostings> jobPostings = new ArrayList<>();
        String emailEmployer;

        try {

            //Connecting to a table to get all the job postings
            PreparedStatement statement = connection.prepareStatement("select * from job_postings");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) { //Loop while there are job postings available

                //Creating and initialising a jobPosting variable
                JobPostings jobPosting = new JobPostings();

                //Getting the job posting details from the table
                jobPosting.setTitle(resultSet.getString("Title"));
                jobPosting.setDescription(resultSet.getString("Description"));
                jobPosting.setId(resultSet.getInt("id"));
                jobPosting.setStatus(resultSet.getString("Status"));
                jobPosting.setSalary(resultSet.getString("salary"));
                jobPosting.setDeadline(resultSet.getString("deadline"));
                jobPosting.setJobLocation(resultSet.getString("jobLocation"));

                emailEmployer = resultSet.getString("email");

                //Connecting to another table to get the information of the employer who created this job posting
                PreparedStatement statement1 = connection.prepareStatement("select * from employer_profile_information where email = ?");
                statement1.setString(1, emailEmployer);
                ResultSet resultSet1 = statement1.executeQuery();

                if (resultSet1.next()) { //If the employer exists

                    //Getting the company from the table
                    jobPosting.setCompany(resultSet1.getString(3));
                }

                //Connecting to a third table to check if the student applied to the job posting
                PreparedStatement statement2 = connection.prepareStatement("select * from applications where studentEmail = ? AND jobPostingID = ?");
                statement2.setString(1, studentEmail);
                statement2.setInt(2, jobPosting.getId());
                ResultSet resultSet2 = statement2.executeQuery();

                if (resultSet2.next()) { //If the student applied to the job posting

                    //Getting the application status
                    jobPosting.setStatus(resultSet2.getString("Status"));

                    //Adding the job posting to the array of job postings
                    jobPostings.add(jobPosting);
                }
            }

            //Sending attributes to other servlets or pages
            request.setAttribute("jobPostings", jobPostings);
            request.setAttribute("studentEmail", studentEmail);

            //Redirecting the student to the page that displays the job postings
            RequestDispatcher view = request.getRequestDispatcher("/viewJobPostings.jsp");
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
