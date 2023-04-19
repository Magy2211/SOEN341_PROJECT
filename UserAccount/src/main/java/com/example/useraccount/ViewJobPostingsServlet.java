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
 * The purpose of this servlet is to display
 * all the job postings
 */
@WebServlet(name = "viewJobPostingsServlet", value = "/viewJobPostingsServlet")
public class ViewJobPostingsServlet extends HttpServlet {
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
        String search = request.getParameter("search");

        //Creating and initialising variables
        List<JobPostings> jobPostings = new ArrayList<>();
        String employerEmail;

        try {
            PreparedStatement statement;
            if (search == null) { //If there is nothing written in the search bar

                //Connecting to the table and selecting all the job postings
                statement = connection.prepareStatement("select * from job_postings");

            } else { //If there is something written in the search bar

                search = '%' + search + '%';

                //Connecting to the table and select the rows where the title is similar to the search condition
                statement = connection.prepareStatement("select * from job_postings where Title like ?");
                statement.setString(1, search);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) { //Loop while there are job postings in the table

                //Crating a jobPosting variable
                JobPostings jobPosting = new JobPostings();

                //Getting the information from the table
                jobPosting.setTitle(resultSet.getString("Title"));
                jobPosting.setDescription(resultSet.getString("Description"));
                jobPosting.setId(resultSet.getInt("id"));
                jobPosting.setStatus(resultSet.getString("Status"));
                jobPosting.setSalary(resultSet.getString("salary"));
                jobPosting.setDeadline(resultSet.getString("deadline"));
                jobPosting.setJobLocation(resultSet.getString("jobLocation"));

                employerEmail = resultSet.getString("email");

                //Connecting to another table and getting the information of the employer who created the job posting
                PreparedStatement statement1 = connection.prepareStatement("select * from employer_profile_information where email = ?");
                statement1.setString(1, employerEmail);
                ResultSet resultSet1 = statement1.executeQuery();

                if (resultSet1.next()) { //If the employer exists

                    //Getting the company from the second
                    jobPosting.setCompany(resultSet1.getString(3));
                }

                //Connecting to a third table to check if the student applied to the job posting and get the status
                PreparedStatement statement2 = connection.prepareStatement("select * from applications where studentEmail = ? AND jobPostingID = ?");
                statement2.setString(1, studentEmail);
                statement2.setInt(2, jobPosting.getId());
                ResultSet resultSet2 = statement2.executeQuery();

                if (resultSet2.next()) { //If the student applied to the job posting

                    //Getting the status from the third table
                    jobPosting.setStatus(resultSet2.getString("Status"));
                }

                //Adding the job posting to the array of job postings
                jobPostings.add(jobPosting);
            }

            //Sending attributes to other servlets or pages
            request.setAttribute("jobPostings", jobPostings);
            request.setAttribute("studentEmail", studentEmail);

            //Redirecting the user to a page that displays all the job postings
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
