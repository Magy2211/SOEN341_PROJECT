package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * The purpose of this servlet is to add a
 * job posting to the database
 */
@WebServlet(name = "addJobPostingsServlet", value = "/addJobPostingsServlet")
public class AddJobPostingsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    @Override
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
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

            //Connecting to the table to input the job posting details
            PreparedStatement statement = connection.prepareStatement("INSERT INTO job_postings (Title, Description, email, Status, salary, deadline, jobLocation) VALUES (?, ?, ?, ?, ?, ?, ?)");

            //Inserting the job posting details into the table
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, email);
            statement.setString(4, "Open");
            statement.setString(5, salary);
            statement.setString(6, deadline);
            statement.setString(7, jobLocation);

            statement.executeUpdate();

            //Redirecting the employer to a page that displays all the job postings created
            RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Close the connection with the database
    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
