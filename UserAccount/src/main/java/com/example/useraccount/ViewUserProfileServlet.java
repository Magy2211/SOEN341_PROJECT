package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "viewUserProfileServlet", value = "/viewUserProfileServlet")
public class ViewUserProfileServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        try {
            //Statement statement = connection.createStatement();
            //ResultSet resultSet = statement.executeQuery("select * from profile_information where email '"+email+"' ");
            PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            RequestDispatcher view = request.getRequestDispatcher("/StudentHomePage.jsp");
            view.forward(request, response);
            if(resultSet.next()){
                request.getSession().setAttribute("firstName", resultSet.getString(1));
                request.getSession().setAttribute("lastName", resultSet.getString(2));
                request.getSession().setAttribute("engineeringField", resultSet.getString(3));
                request.getSession().setAttribute("resume", resultSet.getBlob(4));
                request.getSession().setAttribute("coverLetter", resultSet.getBlob(5));
                request.getSession().setAttribute("transcript", resultSet.getBlob(6));
                request.getSession().setAttribute("email", resultSet.getString(7));
                request.getSession().setAttribute("profilePic", resultSet.getBlob(8));
            }
            response.sendRedirect("/StudentHomePage.jsp");
            //response.sendRedirect("StudentHomePage.jsp");
            //request.getRequestDispatcher("/StudentHomePage.jsp").forward(request, response);
            /*PrintWriter out = response.getWriter();
            out.print("<h3>First Name:" + resultSet.getString(1) +"</h3>\n" +
                    "    <h3>Last Name:" + resultSet.getString(2) +"</h3>\n" +
                    "    <h3>Field of study:" + resultSet.getString(3) +"</h3>\n" +
                    "    <h3>First Name:" + resultSet.getString(1) +"</h3>\n" +
                    "    <h3>First Name:" + resultSet.getString(1) +"</h3>\n" +
                    "    <h3>First Name:" + resultSet.getString(1) +"</h3>\n" +
                    "    <h3>First Name:" + resultSet.getString(1) +"</h3>\n" +
                    "    <h3>First Name:" + resultSet.getString(1) +"</h3>");*/
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
