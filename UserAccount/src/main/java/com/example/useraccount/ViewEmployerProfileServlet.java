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
 * The purpose of this servlet is to display the employer
 * profile information
 */
@WebServlet(name = "viewEmployerProfileServlet", value = "/viewEmployerProfileServlet")
public class ViewEmployerProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    @Override
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String employerEmail = (String) request.getSession().getAttribute("employerEmail");

        //Create and initialise an employer information variable
        EmployerInformation employerInformation = new EmployerInformation();

        try {

            //Connect to a table to select the employer information
            PreparedStatement statement = connection.prepareStatement("select * from employer_profile_information where email = ?");
            statement.setString(1, employerEmail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //If the employer exists

                //Get the information from the table
                employerInformation.setFirstName(resultSet.getString(1));
                employerInformation.setLastName(resultSet.getString(2));
                employerInformation.setCompany(resultSet.getString(3));

                employerInformation.setEmail(employerEmail);
            }

            //Sending attributes to other servlets or pages
            request.setAttribute("employerInformation", employerInformation);

            //Redirect the employer to the page that displays this information
            RequestDispatcher view = request.getRequestDispatcher("/EmployerHomePage.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //Close the connection with the database
    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}