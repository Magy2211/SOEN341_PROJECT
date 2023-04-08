package com.example.useraccount;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "createUserServlet", value = "/createUserServlet")
public class CreateUserServlet extends HttpServlet {
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("user-type");
        String adminAuthenticationCode = request.getParameter("admin-code");
     
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from login_information where email = '"+email+"'");
            
            if(!resultSet.next()) {
            	
            	int result;
            	
            	// For student and employer user type, save their login information in the database directly
            	if(userType.equals("Student") || userType.equals("Employer")) {
            		result = statement.executeUpdate("insert into login_information values ('" + email + "', '" + password + "', '" + userType + "')");
            	}
            	// For admin user type, verify that the authentication code has been entered correctly
            	// before saving login information into the database
            	else if (userType.equals("Admin") && adminAuthenticationCode != null && adminAuthenticationCode.equals(AdminInformation.AUTHENTICATION_CODE)){
            		result = statement.executeUpdate("insert into login_information values ('" + email + "', '" + password + "', '" + userType + "')");
            	}
            	else
            		result = -1; // Error
                
                PrintWriter out = response.getWriter();
                
                if (result > 0) {
                	// Redirect each user type the appropriate profile creation page
                	if(userType.equals("Student")) {
                    RequestDispatcher view = request.getRequestDispatcher("/CreatingUserProfile.html");
                    view.forward(request, response);
                    request.getSession().setAttribute("studentEmail", email);
                    response.sendRedirect("CreatingUserProfileServlet");
                	}
                    else if(userType.equals("Employer")) {
                        RequestDispatcher view = request.getRequestDispatcher("/CreatingEmployerProfile.html");
                        view.forward(request, response);
                        request.getSession().setAttribute("employerEmail", email);
                        response.sendRedirect("CreatingEmployerProfileServlet");
                    }
                    else if(userType.equals("Admin")) {
                    	RequestDispatcher view = request.getRequestDispatcher("/CreatingAdminProfile.html");
                    	view.forward(request,  response);
                    	request.getSession().setAttribute("adminEmail", email);
                    	response.sendRedirect("CreatingAdminProfileServlet");
                    }
                }
                else
                    out.print("<H1> Error creating the account </H1>");
            }
            else {
                RequestDispatcher view = request.getRequestDispatcher("/InvalidCreatingUser.html");
                view.forward(request, response);

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
