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
 * The purpose of this servlet is to edit the
 * student profile information
 */
@WebServlet(name = "editUserProfileServlet", value = "/editUserProfileServlet")
@MultipartConfig
public class EditUserProfileServlet extends HttpServlet {
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

            //Connect to the table to change the profile information of the specified user
            PreparedStatement statement = connection.prepareStatement("update profile_information set firstName=?, lastName=?, fieldOfStudy=?, resume=?, coverLetter=?, unofficialTranscript=?, profilePicture=? where email = ?");

            //Adding the updated student profile information into the table
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, fieldOfStudy);
            statement.setBlob(4, inputStreamResume);
            statement.setBlob(5, inputStreamLetter);
            statement.setBlob(6, inputStreamTranscript);
            statement.setBlob(7, inputStreamPic);
            statement.setString(8, email);

            int result = statement.executeUpdate();

            PrintWriter out = response.getWriter();

            if (result > 0) { //If the information was changed successfully

                out.print("<H1>Profile created</H1>");

                //Redirect the student to the page that displays the student profile information
                RequestDispatcher view = request.getRequestDispatcher("/viewUserProfileServlet");
                view.forward(request, response);

            } else //If there was a problem in changing the profile information
                out.print("<H1> Error updating student information </H1>");

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
