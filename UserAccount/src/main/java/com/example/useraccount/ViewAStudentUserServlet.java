package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/*
 * The purpose of this servlet is to get the information
 * of all users from the database and send this information
 * to a page that displays it
 */
@WebServlet(name = "viewAStudentUserServlet", value = "/viewAStudentUserServlet")
public class ViewAStudentUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    /*
     * Open database connection
     */
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Extract all user profile information from the database
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String studentEmail = request.getParameter("studentEmail");
    	
    	//Creating and initialing variables
        StudentInformation studentInformation = new StudentInformation();
        studentInformation.setEmail(studentEmail);
        
        try {

            //Connecting to the table to get the student information
            PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
            statement.setString(1, studentEmail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //If the student exists in the table

                //Get the information from the table
                studentInformation.setFirstName(resultSet.getString("firstName"));
                studentInformation.setLastName(resultSet.getString("lastName"));
                studentInformation.setFieldOfStudy(resultSet.getString("fieldOfStudy"));

                //Getting the profile picture
                InputStream imageData = resultSet.getBinaryStream(8);
                byte[] imageBytes = imageData.readAllBytes();
                studentInformation.setProfilePicture(Base64.getEncoder().encodeToString(imageBytes));
                
            }
            
           //Sending attributes to another page
            request.setAttribute("studentInformation", studentInformation);
           
            //Redirect the employer to the page that displays this information
            RequestDispatcher view = request.getRequestDispatcher("/ViewStudentUserProfile.jsp");
            view.forward(request, response);
       
        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /*
     * Close database connection
     */
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
