package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/*
 * The purpose of this servlet is to create a user account
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
        //String userType = request.getParameter("userType");

        try {

            //Connect to the table to input the student information
            PreparedStatement statement = connection.prepareStatement("insert into feedback values (?, ?)");

            //Inserting the student profile information into the table
            statement.setInt(1, rating);
            statement.setString(2, subject);

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            if (result > 0) { //If the information was added successfully into the table
            	
            	//Must redirect the user to the appropriate page
            	
                //Redirecting the employer to the about page
            	/*
            	if(userType.equals("student")) {
                RequestDispatcher view = request.getRequestDispatcher("/viewUserProfileServlet");
                view.forward(request, response);
            	}
            	else {
            		out.print("Account type: " + userType);
            		//RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
                    //view.forward(request, response);
            	}
            	 */
            	
            	RequestDispatcher view = request.getRequestDispatcher("/AboutPage.jsp");
                view.forward(request, response);
                
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
