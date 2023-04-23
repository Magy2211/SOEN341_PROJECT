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
 * The purpose of this servlet is to get the information
 * of all users from the database and send this information
 * to a page that displays it
 */
@WebServlet(name = "viewAnEmployerUserServlet", value = "/viewAnEmployerUserServlet")
public class ViewAnEmployerUserServlet extends HttpServlet {

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
     * Extract enployer information from the database
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employerEmail = request.getParameter("employerEmail");

        //Creating and initialing variables
        EmployerInformation employerInformation = new EmployerInformation();
        employerInformation.setEmail(employerEmail);

        try {

            //Connecting to the table to get the student information
            PreparedStatement statement = connection.prepareStatement("select * from employer_profile_information where email = ?");
            statement.setString(1, employerEmail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //If the student exists in the table

                //Get the information from the table
                employerInformation.setFirstName(resultSet.getString("firstName"));
                employerInformation.setLastName(resultSet.getString("lastName"));
                employerInformation.setCompany(resultSet.getString("company"));

            }

            //Sending attributes to another page
            request.setAttribute("employerInformation", employerInformation);

            //Redirect the employer to the page that displays this information
            RequestDispatcher view = request.getRequestDispatcher("/ViewEmployerUserProfile.jsp");
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
