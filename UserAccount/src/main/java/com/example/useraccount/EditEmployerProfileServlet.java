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

/*
 * The purpose of this servlet is to edit
 * the employer profile information
 */
@WebServlet(name = "editEmployerProfileServlet", value = "/editEmployerProfileServlet")
public class EditEmployerProfileServlet extends HttpServlet {
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
        String email = (String) request.getSession().getAttribute("employerEmail");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String company = request.getParameter("company");

        try {

            //Connect to the table to change the information
            PreparedStatement statement = connection.prepareStatement("update employer_profile_information set firstName=?, lastName=?, company=? where email = ?");

            //Adding the updated employer profile information into the table
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, company);
            statement.setString(4, email);

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            if (result > 0) { //If the information was changed successfully

                out.print("<H1>Profile created</H1>");

                //Redirect the user to a page that display the profile information
                RequestDispatcher view = request.getRequestDispatcher("/viewEmployerProfileServlet");
                view.forward(request, response);

            } else //If there was a problem in changing the profile information
                out.print("<H1> Error creating the profile </H1>");

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
