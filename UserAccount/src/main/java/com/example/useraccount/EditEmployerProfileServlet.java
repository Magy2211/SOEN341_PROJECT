package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "editEmployerProfileServlet", value = "/editEmployerProfileServlet")
public class EditEmployerProfileServlet extends HttpServlet {
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
        String email = (String) request.getSession().getAttribute("employerEmail");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String company = request.getParameter("company");

        try {
            PreparedStatement statement = connection.prepareStatement("update employer_profile_information set firstName=?, lastName=?, company=? where email = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, company);
            statement.setString(4, email);

            PrintWriter out = response.getWriter();
            int result = statement.executeUpdate();
            if (result > 0) {
                out.print("<H1>Profile created</H1>");
                //Go back to student home page
                //RequestDispatcher view = request.getRequestDispatcher("/EmployerHomePage.jsp");
                //request.getSession().setAttribute("email", email);
                RequestDispatcher view = request.getRequestDispatcher("/viewEmployerProfileServlet");
                view.forward(request, response);
                //response.sendRedirect("/viewEmployerProfileServlet");
            }
            else
                out.print("<H1> Error creating the profile </H1>");

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
