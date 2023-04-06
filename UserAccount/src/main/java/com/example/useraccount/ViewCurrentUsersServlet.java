package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "viewCurrentUsersServlet", value = "/viewCurrentUsersServlet")
public class ViewCurrentUsersServlet extends HttpServlet {

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
        //String adminEmail = (String) request.getSession().getAttribute("adminEmail");
        //String interview = request.getParameter("interview");
        //int id;
        String firstName;
        String lastName = "";
        String email = "";
        
        String fieldOfStudy;
        String company;

        try {
        	//Get student profiles
            PreparedStatement statement = connection.prepareStatement("select * from profile_information");
            ResultSet resultSet = statement.executeQuery();
            List<StudentInformation> students = new ArrayList<>();
            
            while (resultSet.next())
            {
                firstName = resultSet.getString("firstName");
                lastName = resultSet.getString("lastName");
                email = resultSet.getString("email");
                
                fieldOfStudy = resultSet.getString("fieldOfStudy");

                students.add(new StudentInformation(firstName, lastName, email, fieldOfStudy));
            }
            request.setAttribute("students", students);
            
            //Get employer profiles
            PreparedStatement statement1 = connection.prepareStatement("select * from employer_profile_information");
            ResultSet resultSet1 = statement1.executeQuery();
            List<EmployerInformation> employers = new ArrayList<>();
            
            while (resultSet1.next())
            {
                firstName = resultSet1.getString("firstName");
                lastName = resultSet1.getString("lastName");
                email = resultSet1.getString("email");
                
                company = resultSet1.getString("company");

                employers.add(new EmployerInformation(firstName, lastName, company, email));
            }
            request.setAttribute("employers", employers);
            
            
            RequestDispatcher view = request.getRequestDispatcher("/ViewCurrentUsers.jsp");
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
