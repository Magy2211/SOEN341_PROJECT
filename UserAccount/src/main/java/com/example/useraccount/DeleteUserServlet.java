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
 * The purpose of this servlet is to delete a student or employer account
 */
@WebServlet(name = "deleteUserServlet", value = "/deleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
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

            if(userType.equals("Employer")) {

                //Connect to a table to get the job posting ID of all the job postings created by that employer
                PreparedStatement statement = connection.prepareStatement("select * from job_postings where email = ?");
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) { //Loop while there are job postings created by that employer

                    //Connect to a table to delete all the student applications for a specific job posting created by that employer
                    PreparedStatement statement1 = connection.prepareStatement("delete from applications where jobPostingID = ?");
                    statement1.setInt(1, resultSet.getInt("jobPostingID"));
                    statement1.executeUpdate();
                }

                //Connect to a table to delete the employer's job posting
                PreparedStatement statement2 = connection.prepareStatement("delete from job_postings where email = ?");
                statement2.setString(1, email);
                statement2.executeUpdate();

                //Connect to a table to delete the student profile
            	PreparedStatement statement3 = connection.prepareStatement("delete from employer_profile_information where email = ?");
            	statement3.setString(1, email);
            	statement3.executeUpdate();
            }
            else if(userType.equals("Student")) {

                //Connect to a table to delete all the student applications
                PreparedStatement statement = connection.prepareStatement("delete from applications where studentEmail = ?");
                statement.setString(1, email);
                statement.executeUpdate();

                //Connect to a table to remove the employer profile information
            	PreparedStatement statement1 = connection.prepareStatement("delete from profile_information where email = ?");
                statement1.setString(1, email);
                statement1.executeUpdate();
            }

            //Connect to a table to remove the user from the login information table
            PreparedStatement statement4 = connection.prepareStatement("delete from login_information where email = ?");
            statement4.setString(1, email);
            statement4.executeUpdate();
            
            // Redirect the administrator to its profile view
            RequestDispatcher view = request.getRequestDispatcher("/viewAdminProfileServlet");
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
