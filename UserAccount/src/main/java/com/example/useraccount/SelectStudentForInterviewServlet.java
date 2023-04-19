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
 * The purpose of this servlet is to allow
 * the employer to select a student for an interview
 */
@WebServlet(name = "selectStudentForInterviewServlet", value = "/selectStudentForInterviewServlet")
public class SelectStudentForInterviewServlet extends HttpServlet {
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
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));
        String studentEmail = request.getParameter("studentEmail");

        try {

            //Connect to the table to change the application status
            PreparedStatement statement = connection.prepareStatement("UPDATE applications SET Status = ? WHERE studentEmail = ? AND jobPostingID = ?");
            statement.setString(1, "Selected for an interview");
            statement.setString(2, studentEmail);
            statement.setInt(3, jobPostingID);

            statement.executeUpdate();

            //Redirect the employer to the page that displays all the student applications for that job posting
            RequestDispatcher view = request.getRequestDispatcher("/viewStudentApplicationsServlet?interview=${interview}");
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
