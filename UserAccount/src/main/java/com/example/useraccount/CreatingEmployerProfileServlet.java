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

/*
 * The purpose of this servlet is to complete the
 * employer account registration by inputting
 * the profile information into the database
 */
@WebServlet(name = "createEmployerProfileServlet", value = "/createEmployerProfileServlet")
@MultipartConfig
public class CreatingEmployerProfileServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String email = (String) request.getSession().getAttribute("employerEmail");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String company = request.getParameter("company");

        try {

            //Connect to the table to input the profile information
            PreparedStatement statement = connection.prepareStatement("insert into employer_profile_information values (?, ?, ?, ?)");

            //Inserting the employer profile information into the table
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, company);
            statement.setString(4, email);

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            if (result > 0) { //If the information was inserted successfully into the table

                //Setting the user type as an attribute to be used by other servlets
                request.getSession().setAttribute("userType", "Employer");

                //Redirect the employer to a page that displays all the job postings created by that employer
                RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
                view.forward(request, response);

            } else //If there was a problem inserting the information into the table
                out.print("<H1> Error creating the profile </H1>");

        } catch (SQLException e) {
            throw new ServletException(e);
        }

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
