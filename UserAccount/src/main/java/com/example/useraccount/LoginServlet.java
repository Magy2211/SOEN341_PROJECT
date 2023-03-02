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
        String userType = request.getParameter("user-type");

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from login_information where email = '"+email+"' AND password = '"+password+"' AND userType = '" +userType+"'");
            PrintWriter out = response.getWriter();

            if (resultSet.next()) {
                out.print("<H1>Successful Login </H1>");
            // If login is successful, go to student home page
                /*RequestDispatcher view = request.getRequestDispatcher("/StudentHomePage.jsp");
                //RequestDispatcher view = getServletContext().getRequestDispatcher("/viewUserProfileServlet");
                view.forward(request, response);
                //request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("email", email);*/
                //response.sendRedirect("ViewUserProfileServlet");
                if(userType.equals("Student")) {
                RequestDispatcher view = request.getRequestDispatcher("/StudentHomePage.jsp");
                view.forward(request, response);
                request.getSession().setAttribute("email", email);
                response.sendRedirect("ViewUserProfileServlet");
                }
                if(userType.equals("Employer")) {
                    RequestDispatcher view = request.getRequestDispatcher("/EmployerHomePage.jsp");
                    view.forward(request, response);
                    request.getSession().setAttribute("email", email);
                    //response.sendRedirect("ViewUserProfileServlet");
                    }
            }
            
            else{
                RequestDispatcher view = request.getRequestDispatcher("/InvalidLoginPage.html");
                view.forward(request, response);
                //out.print("<H1> Invalid email or password </H1>");
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
