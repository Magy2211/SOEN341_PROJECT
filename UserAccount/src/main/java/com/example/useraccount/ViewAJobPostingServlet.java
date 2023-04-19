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
 * The purpose of this servlet is to display a specific job posting
 */
@WebServlet(name = "viewAJobPostingServlet", value = "/viewAJobPostingServlet")
public class ViewAJobPostingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        int id = Integer.parseInt(request.getParameter("id"));
        String interview = request.getParameter("interview");
        String userType = request.getParameter("userType");
        String studentEmail = (String) request.getSession().getAttribute("studentEmail");

        //Creating and initialising variables
        JobPostings jobPosting = new JobPostings();
        String emailEmployer;


        try {
            //Connecting to the table and selecting a specific row (row where the id field is equal to the variable id)
            PreparedStatement statement = connection.prepareStatement("select * from job_postings where id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //If the job posting exists

                //Getting values from the selected row
                jobPosting.setTitle(resultSet.getString("Title"));
                jobPosting.setDescription(resultSet.getString("Description"));
                jobPosting.setSalary(resultSet.getString("salary"));
                jobPosting.setDeadline(resultSet.getString("deadline"));
                jobPosting.setJobLocation(resultSet.getString("jobLocation"));
                jobPosting.setStatus(resultSet.getString("Status"));

                emailEmployer = resultSet.getString("email");

                //Connecting to another table to select the information of the employer who created the job posting
                PreparedStatement statement1 = connection.prepareStatement("select * from employer_profile_information where email = ?");
                statement1.setString(1, emailEmployer);
                ResultSet resultSet1 = statement1.executeQuery();

                if (resultSet1.next()) { //If the employer exists

                    //Getting values from the selected row
                    jobPosting.setEmployerFirstName(resultSet1.getString(1));
                    jobPosting.setEmployerLastName(resultSet1.getString(2));
                    jobPosting.setCompany(resultSet1.getString(3));
                }

                //Connecting to a third table to check if the student applied to the job posting
                if (!userType.equals("Admin")) {
                    PreparedStatement statement2 = connection.prepareStatement("select * from applications where studentEmail = ? AND jobPostingID = ?");
                    statement2.setString(1, studentEmail);
                    statement2.setInt(2, id);
                    ResultSet resultSet2 = statement2.executeQuery();

                    if (resultSet2.next()) { //If the student applied to the job posting

                        //Getting the status of the application
                        jobPosting.setStatus(resultSet2.getString("Status"));
                    }
                }
            }

            //Sending attributes to another servlet or a webpage
            request.setAttribute("jobPosting", jobPosting);
            request.getSession().setAttribute("id", id);

            if (userType.equals("Employer")) {
                request.setAttribute("interview", interview);

                //Redirecting the employer to a page that displays the job posting
                RequestDispatcher view = request.getRequestDispatcher("/ViewCreatedJobPosting.jsp");
                view.forward(request, response);
            } else if (userType.equals("Student")) {

                //Redirecting the student to a page that displays the job posting
                RequestDispatcher view = request.getRequestDispatcher("/ViewAJobPosting.jsp");
                view.forward(request, response);
            } else {
                //Redirecting the student to a page that displays the job posting
                RequestDispatcher view = request.getRequestDispatcher("/ViewAJobPostingAdmin.jsp");
                view.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String interview = request.getParameter("interview");
        String userType = request.getParameter("userType");

        request.setAttribute("id", id);
        request.setAttribute("userType", "Employer");
        request.setAttribute("interview", interview);

        doGet(request, response);
    }


    //Close the connection with the database
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
