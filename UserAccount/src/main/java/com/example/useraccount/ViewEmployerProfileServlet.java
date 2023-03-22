package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "viewEmployerProfileServlet", value = "/viewEmployerProfileServlet")
public class ViewEmployerProfileServlet extends HttpServlet {

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employerEmail = (String) request.getSession().getAttribute("employerEmail");
        String employerFirstName;
        String employerLastName;
        String company;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from employer_profile_information where email = ?");
            statement.setString(1, employerEmail);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                employerFirstName = resultSet.getString(1);
                employerLastName = resultSet.getString(2);
                company = resultSet.getString(3);
                request.setAttribute("employerInformation", new EmployerInformation(employerFirstName, employerLastName, company, employerEmail));
            }
            RequestDispatcher view = request.getRequestDispatcher("/EmployerHomePage.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
