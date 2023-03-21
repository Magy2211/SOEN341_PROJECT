package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "applyForAJobPostingServlet", value = "/applyForAJobPostingServlet")
public class ApplyForAJobPostingServlet extends HttpServlet {

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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int jobPostingID = Integer.parseInt(request.getParameter("id"));
        String studentEmail = request.getParameter("studentEmail");
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO applications (jobPostingID, studentEmail, Status) VALUES (?, ?, ?)");
            statement.setInt(1, jobPostingID);
            statement.setString(2, studentEmail);
            statement.setString(3, "Applied to");
            int result = statement.executeUpdate();
            PrintWriter out = response.getWriter();
            if (result > 0) {
                request.getSession().setAttribute("email", studentEmail);
                request.getSession().setAttribute("userType", "Student");
                RequestDispatcher view = request.getRequestDispatcher("/viewUserProfileServlet");
                view.forward(request, response);
            } else
                out.print("<H1> Error applying for the job </H1>");
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
