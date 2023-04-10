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
 * The purpose of this servlet is to log in to the website
 * by verifying the account email and password
 * and redirecting the user to their respective pages
 * depending on the user type (Student, Employer, or Admin)
 */
@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            //Connecting with the table to check user credentials
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from login_information where email = '" + email + "' AND password = '" + password + "'");

            if (resultSet.next()) { //Check if the user exists

                //Get the user type from the table
                String userType = resultSet.getString(3);

                if (userType.equals("Student")) {

                    //Setting the attribute to be used by other servlets
                    request.getSession().setAttribute("studentEmail", email);

                    //Redirecting the student to the student to their home page
                    RequestDispatcher view = request.getRequestDispatcher("/viewJobPostingsServlet");
                    view.forward(request, response);
                } else if (userType.equals("Employer")) {

                    //Setting the email to be used by other servlets
                    request.getSession().setAttribute("employerEmail", email);

                    //Redirecting the employer to their home page
                    RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
                    view.forward(request, response);
                }
            } else { //If the login information is incorrect

                //Redirect the user to a page to re-enter their login information
                RequestDispatcher view = request.getRequestDispatcher("/InvalidLoginPage.html");
                view.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //CLose the connection with the database
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
