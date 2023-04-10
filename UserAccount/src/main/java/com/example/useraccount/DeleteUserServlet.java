package com.example.useraccount;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * The purpose of this servlet is to delete a student account
 */
@WebServlet(name = "deleteUserServlet", value = "/deleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
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

            //Connect to the table and delete the student login information
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("delete from login_information where email='" + email + "' AND password = '" + password + "'");

            PrintWriter out = response.getWriter();

            if (result > 0) { //If the information was deleted successfully from the table
                out.print("<H1>Account deleted</H1");
            } else { //If there was a problem in deleting the account from the table
                out.print("<H1>Account not found in the database.</H1>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
