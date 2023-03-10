package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {
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
            ResultSet resultSet = statement.executeQuery("select * from login_information where email = '"+email+"' AND password = '"+password+"'");

            if (resultSet.next()) {

                String userType = resultSet.getString(3);
                //if(userType.equals("Student")) {

                    request.getSession().setAttribute("email", email);
                    request.getSession().setAttribute("userType", userType);
                    RequestDispatcher view = request.getRequestDispatcher("/viewUserProfileServlet");
                    view.forward(request, response);
                    //response.sendRedirect("/viewUserProfileServlet");
                //}
                /*if(userType.equals("Employer")) {

                    request.getSession().setAttribute("email", email);
                    RequestDispatcher view = request.getRequestDispatcher("/viewEmployerProfileServlet");
                    view.forward(request, response);
                   // response.sendRedirect("/viewEmployerProfileServlet");

                    }*/
            }
            
            else{
                RequestDispatcher view = request.getRequestDispatcher("/InvalidLoginPage.html");
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
