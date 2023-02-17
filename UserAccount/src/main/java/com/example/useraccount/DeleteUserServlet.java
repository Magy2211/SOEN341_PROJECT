package com.example.useraccount;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "deleteUserServlet", value = "/deleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("delete from users where email='"+email+"' AND password = '"+password+"'");
            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.print("<H1>Account deleted</H1");
            } else {
                out.print("<H1>Account not found in the database.</H1>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
