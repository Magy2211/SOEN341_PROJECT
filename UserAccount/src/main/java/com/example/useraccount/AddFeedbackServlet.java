package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * The purpose of this servlet is to add a feedback
 */
@WebServlet(name = "addFeedbackServlet", value = "/addFeedbackServlet")
public class AddFeedbackServlet extends HttpServlet {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String subject = request.getParameter("subject");
        int rating = Integer.parseInt(request.getParameter("rate"));
        String userType = request.getParameter("userType");
        String studentEmail = (String) request.getSession().getAttribute("studentEmail");
        String employerEmail = (String) request.getSession().getAttribute("employerEmail");

        try {

            //Connect to the table to input the student information
            PreparedStatement statement = connection.prepareStatement("insert into feedback values (?, ?, ?)");

            //Inserting the student profile information into the table
            statement.setInt(1, rating);
            statement.setString(2, subject);
            if (userType.equals("student")) {
                statement.setString(3, studentEmail);
            } else {
                statement.setString(3, employerEmail);
            }

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            if (result > 0) { //If the information was added successfully into the table

                //Must redirect the user to the appropriate page

                //Redirecting the employer to the about page
                if (userType.equals("student")) {
                    RequestDispatcher view = request.getRequestDispatcher("/viewJobPostingsServlet");
                    view.forward(request, response);
                } else {
                    RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet?interview=false");
                    view.forward(request, response);
                }

            } else //If there was a problem inserting the information into the table
                out.print("<H1> Error saving feedback </H1>");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
