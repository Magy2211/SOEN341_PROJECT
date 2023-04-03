package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "viewAdminProfileServlet", value = "/viewAdminProfileServlet")
public class ViewAdminProfileServlet extends HttpServlet {

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
        String adminEmail = (String) request.getSession().getAttribute("adminEmail");
        String adminFirstName;
        String adminLastName;
        String role;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from admin_profile_information where email = ?");
            statement.setString(1, adminEmail);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                adminFirstName = resultSet.getString(1);
                adminLastName = resultSet.getString(2);
                role = resultSet.getString(4);
                request.setAttribute("adminInformation", new AdminInformation(adminFirstName, adminLastName, adminEmail, role));
            }
            RequestDispatcher view = request.getRequestDispatcher("/AdminHomePage.jsp");
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
