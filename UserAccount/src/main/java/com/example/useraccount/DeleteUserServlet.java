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
import java.sql.Statement;

/*
 * The purpose of this servlet is to delete a student or employer account
 */
@WebServlet(name = "deleteUserServlet", value = "/deleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
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
        String studentEmail = request.getParameter("studentEmail");
        String employerEmail = request.getParameter("employerEmail");
        
        String email="";
        String userType="";

        try {
        	// Find user type
        	if(studentEmail == null && employerEmail != null) {
        		email = employerEmail;
        		userType = "Employer";
        	}
        	else if (employerEmail == null && studentEmail != null) {
        		email = studentEmail;
        		userType = "Student";
        	}
        		
            //Connect to a table to remove the user from the login information table
            PreparedStatement statement = connection.prepareStatement("delete from login_information where email = ?");
            statement.setString(1, email);
            statement.executeUpdate();

            //Connect to another table to delete the correct profile
            if(userType.equals("Employer")) {
            	PreparedStatement statement1 = connection.prepareStatement("delete from employer_profile_information where email = ?");
            	statement1.setString(1, email);
            	statement1.executeUpdate();
            }
            else if(userType.equals("Student")) {
            	PreparedStatement statement2 = connection.prepareStatement("delete from profile_information where email = ?");
                statement2.setString(1, email);
                statement2.executeUpdate();
            }
            
            // Redirect the administrator to its profile view
            RequestDispatcher view = request.getRequestDispatcher("/viewAdminProfileServlet");
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
