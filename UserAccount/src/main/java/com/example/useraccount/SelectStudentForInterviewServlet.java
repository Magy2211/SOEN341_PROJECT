package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "selectStudentForInterviewServlet", value = "/selectStudentForInterviewServlet")
public class SelectStudentForInterviewServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));
        String studentEmail = request.getParameter("studentEmail");
        String employerEmail = request.getParameter("employerEmail");
        //String status;

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE applications SET Status = ? WHERE studentEmail = ? AND jobPostingID = ?");
            statement.setString(1, "Selected for an interview");
            statement.setString(2, studentEmail);
            statement.setInt(3, jobPostingID);

            int rowsAffected = statement.executeUpdate();

            request.getSession().setAttribute("email", employerEmail);
            request.getSession().setAttribute("userType", "Employer");
            RequestDispatcher view = request.getRequestDispatcher("/viewUserProfileServlet");
            view.forward(request, response);

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
