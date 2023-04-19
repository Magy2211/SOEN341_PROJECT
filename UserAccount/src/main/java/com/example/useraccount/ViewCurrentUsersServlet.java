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
 * The purpose of this servlet is to get the information
 * of all users from the database and send this information
 * to a page that displays it
 */
@WebServlet(name = "viewCurrentUsersServlet", value = "/viewCurrentUsersServlet")
public class ViewCurrentUsersServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    /*
     * Open database connection
     */
    @Override
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName;
        String lastName;
        String email;

        String fieldOfStudy;
        String company;

        try {

            // Get information for student profiles
            PreparedStatement statement = connection.prepareStatement("select * from profile_information");
            ResultSet resultSet = statement.executeQuery();
            // Create a list of student information
            List<StudentInformation> students = new ArrayList<>();

            // Extract the information from the database
            while (resultSet.next()) {
                firstName = resultSet.getString("firstName");
                lastName = resultSet.getString("lastName");
                email = resultSet.getString("email");

                fieldOfStudy = resultSet.getString("fieldOfStudy");

                // Add student information to the list
                students.add(new StudentInformation(firstName, lastName, email, fieldOfStudy));
            }
            request.setAttribute("students", students); // Send to view

            // Get information for employer profiles

            PreparedStatement statement1 = connection.prepareStatement("select * from employer_profile_information");
            ResultSet resultSet1 = statement1.executeQuery();
            // Create a list of employer information
            List<EmployerInformation> employers = new ArrayList<>();

            // Extract the information from the database
            while (resultSet1.next()) {
                firstName = resultSet1.getString("firstName");
                lastName = resultSet1.getString("lastName");
                email = resultSet1.getString("email");

                company = resultSet1.getString("company");

                // Add employer information to the list
                employers.add(new EmployerInformation(firstName, lastName, company, email));
            }
            request.setAttribute("employers", employers); // Send to view

            // Send to view of the current users in the system
            RequestDispatcher view = request.getRequestDispatcher("/ViewCurrentUsers.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /*
     * Close database connection
     */
    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
