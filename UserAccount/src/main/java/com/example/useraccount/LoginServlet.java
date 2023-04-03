package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
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
                if(userType.equals("Student")) {
                    request.getSession().setAttribute("studentEmail", email);
                    //RequestDispatcher view = request.getRequestDispatcher("/viewUserProfileServlet");
                    RequestDispatcher view = request.getRequestDispatcher("/viewJobPostingsServlet");
                    view.forward(request, response);
                }
                else if(userType.equals("Employer")) {
                    request.getSession().setAttribute("employerEmail", email);
                    //RequestDispatcher view = request.getRequestDispatcher("/viewEmployerProfileServlet");
                    RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
                    view.forward(request, response);
                }
                
                else if(userType.equals("Admin")) {
                	request.getSession().setAttribute("adminEmail", email);
                    //RequestDispatcher view = request.getRequestDispatcher("/viewEmployerProfileServlet");
                    RequestDispatcher view = request.getRequestDispatcher("/viewAdminProfileServlet");
                    view.forward(request, response);
                }
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
