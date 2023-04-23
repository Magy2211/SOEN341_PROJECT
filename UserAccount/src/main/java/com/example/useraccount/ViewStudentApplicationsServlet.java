package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * The purpose of this servlet is to display the information
 * of all the students who applied to the job posting
 */
@WebServlet(name = "viewStudentApplicationsServlet", value = "/viewStudentApplicationsServlet")
public class ViewStudentApplicationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    @Override
    public void init() throws ServletException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));
        String interview = request.getParameter("interview");
        String employerEmail = (String) request.getSession().getAttribute("employerEmail");

        //Creating and initialising a student information array
        List<StudentInformation> studentInformation = new ArrayList<>();

        try {
            ResultSet resultSet2;
            PreparedStatement statement2;

            if (interview.equals("false")) { //If the student is not selected for an interview

                //Connect to the table to get the information of all the students applied to a specific job posting
                statement2 = connection.prepareStatement("select * from applications where jobPostingID = ?");
                statement2.setInt(1, jobPostingID);

            } else { //If the student is selected for an interview

                //Connect to the table to get the information of all the students applied to a specific job posting
                statement2 = connection.prepareStatement("select * from applications where jobPostingID = ? AND Status = ?");
                statement2.setInt(1, jobPostingID);
                statement2.setString(2, "Selected for an interview");
            }
            resultSet2 = statement2.executeQuery();

            while (resultSet2.next()) { //Loop while there are student applications for a specific job posting

                //Creating and initialising a StudentInformation variable to hold the student information
                StudentInformation studentInformation1 = new StudentInformation();

                //Getting information from the table
                studentInformation1.setEmail(resultSet2.getString("studentEmail"));

                //Connecting to another table to get the student information
                PreparedStatement statement1 = connection.prepareStatement("select * from profile_information where email = ?");
                statement1.setString(1, studentInformation1.getEmail());
                ResultSet resultSet1 = statement1.executeQuery();

                if (resultSet1.next()) { //If the student exists

                    //Getting the student information from the table
                    studentInformation1.setFirstName(resultSet1.getString("firstName"));
                    studentInformation1.setLastName(resultSet1.getString("lastName"));
                    studentInformation1.setFieldOfStudy(resultSet1.getString("fieldOfStudy"));

                    //Add the studentInformation1 variable to the array
                    studentInformation.add(studentInformation1);
                }
            }

            //Sending the attributes to another servlet or pages
            request.setAttribute("studentInformation", studentInformation);
            request.setAttribute("jobPostingID", jobPostingID);
            request.setAttribute("employerEmail", employerEmail);
            request.getSession().setAttribute("interview", interview);

            //Redirecting the employer to a page that displays this information
            RequestDispatcher view = request.getRequestDispatcher("/ViewStudentApplications.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //Close the connection with the database
    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
}