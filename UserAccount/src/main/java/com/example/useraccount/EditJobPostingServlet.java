package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "editJobPostingServlet", value = "/editJobPostingServlet")
public class EditJobPostingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int jobPostingID = (int) request.getSession().getAttribute("jobPostingID");
        String email = (String) request.getSession().getAttribute("employerEmail");
        String interview = (String) request.getSession().getAttribute("interview");
        String title = request.getParameter("positionTitle");
        String description = request.getParameter("description");

        String salary = request.getParameter("salary");
        String deadline = request.getParameter("deadline");
        String jobLocation = request.getParameter("jobLocation");

        try {
            PreparedStatement statement = connection.prepareStatement("delete from applications where jobPostingID = ?");
            statement.setInt(1, jobPostingID);
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement("UPDATE job_postings set Title = ?, Description = ?, email = ?, Status = ?, salary = ?, deadline = ?, jobLocation = ? where id = ?");
            statement1.setString(1, title);
            statement1.setString(2, description);
            statement1.setString(3, email);
            statement1.setString(4, "Open");
            statement1.setString(5, salary);
            statement1.setString(6, deadline);
            statement1.setString(7, jobLocation);
            statement1.setInt(8, jobPostingID);
            statement1.executeUpdate();

            request.setAttribute("id", jobPostingID);
            request.setAttribute("userType", "Employer");
            request.setAttribute("interview", interview);

            //RequestDispatcher view = request.getRequestDispatcher("/viewAJobPostingServlet");
            RequestDispatcher view = request.getRequestDispatcher("/viewCreatedJobPostingsServlet");
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
