package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "addJobPostingsServlet", value = "/addJobPostingsServlet")
public class AddJobPostingsServlet extends HttpServlet {

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
        String email = (String) request.getSession().getAttribute("email");
        String title = request.getParameter("positionTitle");
        String description = request.getParameter("description");
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("insert into job_postings values ('" + title + "', '" + description + "', '" + email + "')");
            PrintWriter out = response.getWriter();

                if (result > 0) {
                    request.getSession().setAttribute("email", email);
                    request.getSession().setAttribute("userType", "Employer");
                    RequestDispatcher view = request.getRequestDispatcher("/viewUserProfileServlet");
                    view.forward(request, response);
                } else
                    out.print("<H1> Error creating job posting </H1>");
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
