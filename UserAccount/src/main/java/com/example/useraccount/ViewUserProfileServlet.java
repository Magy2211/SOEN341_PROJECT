package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Base64;

/*
 * The purpose of this servlet is to allow
 * the student to view their profile information
 */
@WebServlet(name = "viewUserProfileServlet", value = "/viewUserProfileServlet")
public class ViewUserProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    //Establishing a connection with the database
    @Override
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameter sent from other servlet
        String email = (String) request.getSession().getAttribute("studentEmail");

        //Create and initialise a student information variable
        StudentInformation studentInformation = new StudentInformation();

        try {

            //Connect to a table and selecting the student information
            PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //Checks if the student exists in the table

                //Get the information from the table
                studentInformation.setFirstName(resultSet.getString(1));
                studentInformation.setLastName(resultSet.getString(2));
                studentInformation.setFieldOfStudy(resultSet.getString(3));

                // read resume as a PDF file
                Blob resumeBlob = resultSet.getBlob(4);
                byte[] resumeData = resumeBlob.getBytes(1, (int) resumeBlob.length());
                studentInformation.setResumeBase64(Base64.getEncoder().encodeToString(resumeData));

                // read cover letter as a PDF file
                Blob coverLetterBlob = resultSet.getBlob(5);
                byte[] coverLetterData = coverLetterBlob.getBytes(1, (int) coverLetterBlob.length());
                studentInformation.setCoverLetterBase64(Base64.getEncoder().encodeToString(coverLetterData));

                // read transcript as a PDF file
                Blob transcriptBlob = resultSet.getBlob(6);
                byte[] transcriptData = transcriptBlob.getBytes(1, (int) transcriptBlob.length());
                studentInformation.setTranscriptBase64(Base64.getEncoder().encodeToString(transcriptData));

                studentInformation.setEmail(resultSet.getString(7));

                //get the profile picture
                InputStream imageData = resultSet.getBinaryStream(8);
                byte[] imageBytes = imageData.readAllBytes();
                studentInformation.setProfilePicture(Base64.getEncoder().encodeToString(imageBytes));
            }

            //Sending attribute to another page
            request.setAttribute("studentInformation", studentInformation);

            //Redirect the student to the page that displays this information
            RequestDispatcher view = request.getRequestDispatcher("/StudentHomePage.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }
}