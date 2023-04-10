package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "editAdminProfileServlet", value = "/editAdminProfileServlet")
public class EditAdminProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    
    /*
     * Open database connection
     */
    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    /*
     * Modify the admin profile information in the database
     */
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
            
            PrintWriter out = response.getWriter();
            int result = statement.executeUpdate();
            // If information modified successfully, go to profile view
            if (result > 0) {
                RequestDispatcher view = request.getRequestDispatcher("/viewAdminProfileServlet");
                view.forward(request, response);
            }
            else // Print out error message
                out.print("<H1> Error modifying the profile with email </H1>" + email + firstName + lastName + adminRole);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
