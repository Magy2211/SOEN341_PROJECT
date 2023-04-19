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
 * The purpose of this servlet is to delete
 * a job posting. The job posting and the student applications
 * for that job posting should be removed.
 */
@WebServlet(name = "removeJobPostingServlet", value = "/removeJobPostingServlet")
public class RemoveJobPostingServlet extends HttpServlet {

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
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));
        String userType = request.getParameter("userType");

        try {

            //Connect to a table to remove the student application for that posting
            PreparedStatement statement = connection.prepareStatement("delete from applications where jobPostingID = ?");
            statement.setInt(1, jobPostingID);
            statement.executeUpdate();

            //Connect to another table to remove the job posting 
            PreparedStatement statement1 = connection.prepareStatement("delete from job_postings where id = ?");
            statement1.setInt(1, jobPostingID);
            statement1.executeUpdate();

            if (userType.equals("Admin")) {
                //Redirect the admin to the job postings list
                RequestDispatcher view = request.getRequestDispatcher("/viewJobPostingsAdminServlet");
                view.forward(request, response);
            } else {
                //Redirect the employer to a page that displays all the job postings created by that employer
                RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
                view.forward(request, response);
            }

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
