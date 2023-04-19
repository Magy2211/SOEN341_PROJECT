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

@WebServlet(name = "editAdminProfileServlet", value = "/editAdminProfileServlet")
public class EditAdminProfileServlet extends HttpServlet {
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
     * Get the admin profile information from the database to be visible while editing the information
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminEmail = (String) request.getSession().getAttribute("adminEmail"); // Get the email of current user

        AdminInformation adminInformation = new AdminInformation();

        try {
            PreparedStatement statement = connection.prepareStatement("select * from admin_profile_information where email = ?");

            statement.setString(1, adminEmail); // Select the user with corresponding email in the database
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Get information about user
                adminInformation.setFirstName(resultSet.getString(1));
                adminInformation.setLastName(resultSet.getString(2));
                adminInformation.setRole(resultSet.getString(3));

                // Save information in an AdminInformation object
                request.setAttribute("adminInformation", adminInformation);
            }

            //Redirect the admin to a page to edit their profile information
            RequestDispatcher view = request.getRequestDispatcher("/EditingAdminProfile.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Modify the admin profile information in the database
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("adminEmail"); // Get the current admin user email

        // Save the updated information provided by the user
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String adminRole = request.getParameter("admin-role");

        try {
            PreparedStatement statement = connection.prepareStatement("update admin_profile_information set firstName=?, lastName=?, adminRole=? where email = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, adminRole);
            statement.setString(4, email);

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            // If information modified successfully, go to profile view
            if (result > 0) {
                RequestDispatcher view = request.getRequestDispatcher("/viewAdminProfileServlet");
                view.forward(request, response);
            } else // Print out error message
                out.print("<H1> Error modifying the profile with email </H1>" + email + firstName + lastName + adminRole);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
