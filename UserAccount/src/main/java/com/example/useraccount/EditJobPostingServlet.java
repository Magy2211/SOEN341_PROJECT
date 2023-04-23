package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

/*
 * The purpose of this servlet is to edit the
 * job posting. When editing a job postings all
 * the student application for this job posting
 * should be removed
 */
@WebServlet(name = "editJobPostingServlet", value = "/editJobPostingServlet")
public class EditJobPostingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    int jobPostingID;
    String interview;
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

    //This method sends the existing information in the table, so the user can view the information while editing
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Getting parameters sent from other servlet/pages
        jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));
        interview = request.getParameter("interview");

        //Creating and initialising a job posting object
        JobPostings jobPosting = new JobPostings();

        try {

            //Connecting to the table and selecting a specific row (row where the id field is equal to the variable id)
            PreparedStatement statement = connection.prepareStatement("select * from job_postings where id = ?");
            statement.setInt(1, jobPostingID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //If the job posting exists

                //Getting values from the selected row
                jobPosting.setTitle(resultSet.getString("Title"));
                jobPosting.setDescription(resultSet.getString("Description"));
                jobPosting.setSalary(resultSet.getString("salary"));
                jobPosting.setDeadline(resultSet.getString("deadline"));
                jobPosting.setJobLocation(resultSet.getString("jobLocation"));
                jobPosting.setStatus(resultSet.getString("Status"));
            }

            //Sending attributes to another servlet or a webpage
            request.setAttribute("jobPosting", jobPosting);

            //Redirecting the employer to a page to edit the job posting
            RequestDispatcher view = request.getRequestDispatcher("/EditJobPosting.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    //This method edits the information in the table with the new information submitted by the user
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String email = (String) request.getSession().getAttribute("employerEmail");
        String title = request.getParameter("positionTitle");
        String description = request.getParameter("description");
        String salary = request.getParameter("salary");
        String deadline = request.getParameter("deadline");
        String jobLocation = request.getParameter("jobLocation");

        try {

            //Connect to a table to remove all the student applications for this job posting
            PreparedStatement statement = connection.prepareStatement("delete from applications where jobPostingID = ?");
            statement.setInt(1, jobPostingID);
            statement.executeUpdate();

            //Connect to another table to change the job posting information
            PreparedStatement statement1 = connection.prepareStatement("UPDATE job_postings set Title = ?, Description = ?, email = ?, Status = ?, salary = ?, deadline = ?, jobLocation = ? where id = ?");

            //Adding the updated job posting details into the table
            statement1.setString(1, title);
            statement1.setString(2, description);
            statement1.setString(3, email);
            statement1.setString(4, "Open");
            statement1.setString(5, salary);
            statement1.setString(6, deadline);
            statement1.setString(7, jobLocation);
            statement1.setInt(8, jobPostingID);

            statement1.executeUpdate();

            //Setting attributes to be used by another servlet
            request.setAttribute("id", jobPostingID);
            request.setAttribute("userType", "Employer");
            request.setAttribute("interview", interview);

            //Redirect the employer to the page that displays all the job postings created by that employer
            RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
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