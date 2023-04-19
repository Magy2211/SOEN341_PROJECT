package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

/*
 * The purpose of this servlet is to get the admin profile
 * information from the database and send it to a page that
 * displays this information
 */
@WebServlet(name = "viewAdminProfileServlet", value = "/viewAdminProfileServlet")
public class ViewAdminProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    /*
     * Open database connection
     */
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    /*
     * Extract admin profile information from the database
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminEmail = (String) request.getSession().getAttribute("adminEmail"); // Get the email of current user

        String adminFirstName;
        String adminLastName;
        String adminRole;

        try {
            PreparedStatement statement = connection.prepareStatement("select * from admin_profile_information where email = ?");
            statement.setString(1, adminEmail); // Select the user with corresponding email in the database
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Get information about user
                adminFirstName = resultSet.getString(1);
                adminLastName = resultSet.getString(2);
                adminRole = resultSet.getString(3);
                // Save information in an AdminInformation object
                request.setAttribute("adminInformation", new AdminInformation(adminFirstName, adminLastName, adminEmail, adminRole));
            }

            //Redirect the admin to the page that displays their profile information
            RequestDispatcher view = request.getRequestDispatcher("/AdminHomePage.jsp"); // Go to the admin home page
            view.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
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
        	e.printStackTrace();
        }
    }
}
