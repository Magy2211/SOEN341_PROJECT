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
@WebServlet(name = "viewUserFeedbackServlet", value = "/viewUserFeedbackServlet")
public class ViewUserFeedbackServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Creating and initialising variables
        List<FeedbackForm> feedbackFormList = new ArrayList<>();

        try {
        	// Connecting to the table and selecting all the feedback
            PreparedStatement statement = connection.prepareStatement("select * from feedback");
               
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) { //Loop while there are job postings in the table

                //Crating a jobPosting variable
                FeedbackForm feedbackForm = new FeedbackForm();

                //Getting the information from the table
                feedbackForm.setSubject(resultSet.getString("subject"));
                feedbackForm.setRating(resultSet.getInt("rating"));
                feedbackForm.setEmail(resultSet.getString("email"));
        
                //Adding the job posting to the array of job postings
                feedbackFormList.add(feedbackForm);
            }

            //Sending attributes to other servlets or pages
            request.setAttribute("feedbackForms", feedbackFormList);

            //Redirecting the user to a page that displays all the job postings
            RequestDispatcher view = request.getRequestDispatcher("/ViewUserFeedback.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
