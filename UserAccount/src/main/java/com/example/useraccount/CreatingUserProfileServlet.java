package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * The purpose of this servlet is to complete
 * the student account registration by entering the
 * student profile information into the database
 */
@WebServlet(name = "createUserProfileServlet", value = "/createUserProfileServlet")
@MultipartConfig
public class CreatingUserProfileServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        String email = (String) request.getSession().getAttribute("studentEmail");
        Part picturePart = request.getPart("profile-pic");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String fieldOfStudy = request.getParameter("engineering-field");
        Part resumePart = request.getPart("resume");
        Part coverLetterPart = request.getPart("cover-letter");
        Part transcriptPart = request.getPart("transcript");

        //Creating and initialing variables that holds the documents
        InputStream inputStreamPic = picturePart.getInputStream();
        InputStream inputStreamResume = resumePart.getInputStream();
        InputStream inputStreamLetter = coverLetterPart.getInputStream();
        InputStream inputStreamTranscript = transcriptPart.getInputStream();

        try {

            //Connect to the table to input the student information
            PreparedStatement statement = connection.prepareStatement("insert into profile_information values (?, ?, ?, ?, ?, ?, ?, ?)");

            //Inserting the student profile information into the table
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, fieldOfStudy);
            statement.setBlob(4, inputStreamResume);
            statement.setBlob(5, inputStreamLetter);
            statement.setBlob(6, inputStreamTranscript);
            statement.setString(7, email);
            statement.setBlob(8, inputStreamPic);

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            if (result > 0) { //If the information was added successfully into the table

                //Setting the user type as an attribute to be used by other servlets
                request.getSession().setAttribute("userType", "Student");

                //Redirecting the student to a page that displays all the job postings
                RequestDispatcher view = request.getRequestDispatcher("/viewJobPostingsServlet");
                view.forward(request, response);

            } else //If there was a problem inserting the information into the table
                out.print("<H1> Error creating the profile </H1>");

        } catch (SQLException e) {
            throw new ServletException(e);
        }

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
