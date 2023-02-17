package com.example.useraccount;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "createUserServlet", value = "/createUserServlet")
public class CreateUserServlet extends HttpServlet {
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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users where email = '"+email+"'");
            if(!resultSet.next()) {
                int result = statement.executeUpdate("insert into users values ('" + firstName + "', '" + lastName + "', '" + email + "', '" + password + "')");

                PrintWriter out = response.getWriter();
                if (result > 0)
                    out.print("<H1>Account created</H1>");
                else
                    out.print("<H1> Error creating the account </H1>");
            }
            else {
                RequestDispatcher view = request.getRequestDispatcher("/InvalidCreatingUser.html");
                view.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
