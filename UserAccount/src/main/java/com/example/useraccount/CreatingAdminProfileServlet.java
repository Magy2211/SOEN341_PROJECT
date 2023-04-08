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
    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    /*
     * Enter admin profile information in the database upon account creation
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Receive the parameters from the form in CreatingAdminProfile.html
    	String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
    	String email = (String) request.getSession().getAttribute("adminEmail");
        String role = request.getParameter("role");
        
        // Enter the information into the database
        try {
            PreparedStatement statement = connection.prepareStatement("insert into admin_profile_information values (?, ?, ?, ?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, role);
            
            // If successful, go to view of the newly created profile
            PrintWriter out = response.getWriter();
            int result = statement.executeUpdate();
            if (result > 0) {
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("userType", "Admin");
                RequestDispatcher view = request.getRequestDispatcher("/viewAdminProfileServlet");
                view.forward(request, response);
            }
            
            else // Print an error message
                out.print("<H1> Error creating the profile </H1>");

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
