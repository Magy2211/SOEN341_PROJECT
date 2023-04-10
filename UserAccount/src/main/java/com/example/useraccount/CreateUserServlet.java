package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/*
 * The purpose of this servlet is to create a user account
 */
@WebServlet(name = "createUserServlet", value = "/createUserServlet")
public class CreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("user-type");
        String adminAuthenticationCode = request.getParameter("admin-code");
     
        try {

            //Connect to a table to checks if the email is not already in-use
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from login_information where email = '"+email+"'");
            
            if(!resultSet.next()) { //If the email doesn't exist in the table
            	
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

                if (result > 0) { //If the information was inserted successfully
                    if (userType.equals("Student")) { //If the user is a student

                        //Setting the email as an attribute to be used by other servlets
                        request.getSession().setAttribute("studentEmail", email);

                        //Redirecting the student to a page to complete the account registration
                        RequestDispatcher view = request.getRequestDispatcher("/CreatingUserProfile.html");
                        view.forward(request, response);


                    } else if (userType.equals("Employer")) { //If the user is an employer

                        //Setting the email as an attribute to be used by other servlets
                        request.getSession().setAttribute("employerEmail", email);

                        //Redirecting the employer to a page to complete the account registration
                        RequestDispatcher view = request.getRequestDispatcher("/CreatingEmployerProfile.html");
                        view.forward(request, response);
                    }
                    else if(userType.equals("Admin")) {
                    	RequestDispatcher view = request.getRequestDispatcher("/CreatingAdminProfile.html");
                    	view.forward(request,  response);
                    	request.getSession().setAttribute("adminEmail", email);
                    	response.sendRedirect("CreatingAdminProfileServlet");
                    }
          
                } else //If there was a problem inserting the information
                    out.print("<H1> Error creating the account </H1>");

            } else { //If the email is in-use

                //Redirect the user to a page to enter another email
                RequestDispatcher view = request.getRequestDispatcher("/InvalidCreatingUser.html");
                view.forward(request, response);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Close the connection with the database
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
