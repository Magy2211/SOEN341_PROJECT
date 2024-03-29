package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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

@WebServlet(name = "createAdminProfileServlet", value = "/createAdminProfileServlet")
@MultipartConfig
public class CreatingAdminProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    /*
     * Open database connection
     */
    @Override
    public void init() throws ServletException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /*
     * Enter admin profile information in the database upon account creation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Receive the parameters from the form in CreatingAdminProfile.html
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = (String) request.getSession().getAttribute("adminEmail");
        String adminRole = request.getParameter("adminRole");

        // Enter the information into the database
        try {
            PreparedStatement statement = connection.prepareStatement("insert into admin_profile_information values (?, ?, ?, ?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, adminRole);
            statement.setString(4, email);

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            // If successful, go to view of the newly created profile
            if (result > 0) {

                //Setting the user type as an attribute to be used by other servlets
                request.setAttribute("userType", "Admin");

                //Redirecting the admin to the page that displays the profile information
                RequestDispatcher view = request.getRequestDispatcher("/viewAdminProfileServlet");
                view.forward(request, response);

            } else // Print an error message
                out.print("<H1> Error creating the profile </H1>");

        } catch (SQLException e) {
            throw new ServletException(e);
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
        	e.printStackTrace();
        }
    }
}
