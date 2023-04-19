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
 * the employer to view the information of the
 * student application to a job posting
 */
@WebServlet(name = "viewAStudentInformationServlet", value = "/viewAStudentInformationServlet")
public class ViewAStudentInformationServlet extends HttpServlet {
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

        //Getting parameters sent from other servlet/pages
        String studentEmail = request.getParameter("studentEmail");
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));

        //Creating and initialing variables
        StudentInformation studentInformation = new StudentInformation();
        studentInformation.setEmail(studentEmail);
        String status = "";

        try {

            //Connecting to the table to get the student information
            PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
            statement.setString(1, studentEmail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //If the student exists in the table

                //Get the information from the table
                studentInformation.setFirstName(resultSet.getString("firstName"));
                studentInformation.setLastName(resultSet.getString("lastName"));
                studentInformation.setFieldOfStudy(resultSet.getString("fieldOfStudy"));

                //Getting the profile picture
                InputStream imageData = resultSet.getBinaryStream(8);
                byte[] imageBytes = imageData.readAllBytes();
                studentInformation.setProfilePicture(Base64.getEncoder().encodeToString(imageBytes));

                //Connecting to another table to get the document the student choose for this application
                PreparedStatement statement1 = connection.prepareStatement("select * from applications where jobPostingID = ? AND studentEmail = ?");
                statement1.setInt(1, jobPostingID);
                statement1.setString(2, studentEmail);
                ResultSet resultSet1 = statement1.executeQuery();

                if (resultSet1.next()) { //If the student application exists in the table

                    //Get the application status from the table
                    status = resultSet1.getString("Status");

                    //read resume as a PDF file
                    Blob resumeBlob = resultSet1.getBlob(5);
                    byte[] resumeData = resumeBlob.getBytes(1, (int) resumeBlob.length());
                    studentInformation.setResumeBase64(Base64.getEncoder().encodeToString(resumeData));

                    // read cover letter as a PDF file
                    Blob coverLetterBlob = resultSet1.getBlob(6);
                    byte[] coverLetterData = coverLetterBlob.getBytes(1, (int) coverLetterBlob.length());
                    studentInformation.setCoverLetterBase64(Base64.getEncoder().encodeToString(coverLetterData));

                    // read transcript as a PDF file
                    Blob transcriptBlob = resultSet1.getBlob(7);
                    byte[] transcriptData = transcriptBlob.getBytes(1, (int) transcriptBlob.length());
                    studentInformation.setTranscriptBase64(Base64.getEncoder().encodeToString(transcriptData));
                }
            }

            //Sending attributes to another page
            request.setAttribute("studentInformation", studentInformation);
            request.setAttribute("jobPostingID", jobPostingID);
            request.setAttribute("status", status);

            //Redirect the employer to the page that displays this information
            RequestDispatcher view = request.getRequestDispatcher("/ViewAStudentInformation.jsp");
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
