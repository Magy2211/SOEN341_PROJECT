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
import java.sql.*;


/*
 * The purpose of this servlet is to allow the student
 * to apply to a job posting
 */
@WebServlet(name = "applyForAJobPostingServlet", value = "/applyForAJobPostingServlet")
@MultipartConfig
public class ApplyForAJobPostingServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting parameters sent from other servlet/pages
        int jobPostingID = (Integer) request.getSession().getAttribute("id");
        String studentEmail = (String) request.getSession().getAttribute("studentEmail");
        String[] defaultInformation = request.getParameterValues("default");

        //Creating and initialing variables
        boolean defaultResume = false;
        boolean defaultCoverLetter = false;
        boolean defaultTranscript = false;
        InputStream inputStreamResume = null;
        InputStream inputStreamLetter = null;
        InputStream inputStreamTranscript = null;

        /*
         * This loop checks the user choice of using
         * the default resume, cover letter and/or transcript
         * and set the values of the defaultResume, defaultCoverLetter
         * and defaultTranscript to true if the user wants to use the
         * default documents
         *
         * The switch statements checks if the box was ticked
         */
        for (String s : defaultInformation) {
            switch (s) {
                case "defaultResume":
                    defaultResume = true;
                    break;
                case "defaultCoverLetter":
                    defaultCoverLetter = true;
                    break;
                case "defaultTranscript":
                    defaultTranscript = true;
                    break;
            }
        }

        try {

            //Connects to the table to get the student information
            PreparedStatement statement1 = connection.prepareStatement("SELECT * from profile_information where email = ?");
            statement1.setString(1, studentEmail);
            ResultSet resultSet = statement1.executeQuery();

            if (resultSet.next()) { //Check that the student exists

                /*
                 * The following if-statements checks if the student selected the
                 * default document or upload a new document
                 * and get the default document from the table if the
                 * student is going to use the default document or getting
                 * the new document that the student uploaded
                 */

                if (!defaultResume) { //Not using the default document

                    //Getting the new document that the student uploaded
                    Part resumePart = request.getPart("resume");
                    inputStreamResume = resumePart.getInputStream();

                } else { //Using the default document

                    //Getting the document from the table
                    inputStreamResume = resultSet.getBinaryStream("resume");
                }
                if (!defaultCoverLetter) { //Not using the default document

                    //Getting the new document that the student uploaded
                    Part coverLetterPart = request.getPart("cover-letter");
                    inputStreamLetter = coverLetterPart.getInputStream();

                } else {//Using the default document

                    //Getting the document from the table
                    inputStreamLetter = resultSet.getBinaryStream("coverLetter");
                }
                if (!defaultTranscript) { //Not using the default document

                    //Getting the new document that the student uploaded
                    Part transcriptPart = request.getPart("transcript");
                    inputStreamTranscript = transcriptPart.getInputStream();
                } else { //Using the default document

                    //Getting the document from the table
                    inputStreamTranscript = resultSet.getBinaryStream("unofficialTranscript");
                }
            }

            //Connect to another table to input the student application information
            PreparedStatement statement = connection.prepareStatement("INSERT INTO applications (jobPostingID, studentEmail, Status, resume, coverLetter, transcript) VALUES (?, ?, ?, ?, ?, ?)");

            //Inserting the student application details into the table
            statement.setInt(1, jobPostingID);
            statement.setString(2, studentEmail);
            statement.setString(3, "Applied to");
            statement.setBlob(4, inputStreamResume);
            statement.setBlob(5, inputStreamLetter);
            statement.setBlob(6, inputStreamTranscript);

            statement.executeUpdate();

            //Redirecting the student to the page that displays all the job postings
            RequestDispatcher view = request.getRequestDispatcher("/viewJobPostingsServlet");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
