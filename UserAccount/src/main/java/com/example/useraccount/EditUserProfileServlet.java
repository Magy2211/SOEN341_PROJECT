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
import java.sql.*;

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
    @Override
    public void init() throws ServletException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    //This method sends the existing information in the table, so the user can view the information while editing
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

                studentInformation.setEmail(resultSet.getString(7));
            }

            //Sending attribute to another page
            request.setAttribute("studentInformation", studentInformation);

            //Redirect the student to the page to edit the profile information
            RequestDispatcher view = request.getRequestDispatcher("/EditingUserProfile.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    //This method edits the information in the table with the new information submitted by the user
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

        try {

            //Connect to the table to change the profile information of the specified user
            PreparedStatement statement = connection.prepareStatement("update profile_information set firstName=?, lastName=?, fieldOfStudy=?, resume=?, coverLetter=?, unofficialTranscript=?, profilePicture=? where email = ?");

            //Connecting to the same table and selecting the student information
            PreparedStatement statement1 = connection.prepareStatement("SELECT * from profile_information where email = ?");
            statement1.setString(1, email);
            ResultSet resultSet = statement1.executeQuery();

            if (resultSet.next()) { //If the student exists

                //Adding the updated student profile information into the table
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, fieldOfStudy);

                /*
                 * The following if statements checks if the student uploaded new
                 * files for the profile picture, resume, cover letter
                 * or transcript. If the student uploaded new files then the
                 * new files are going to be added in the table. If the student
                 * didn't upload new files then the existing files in the
                 * table will remain unchanged.
                 */
                if (resumePart != null && resumePart.getSize() > 0) { //Checks if the student uploaded a new resume

                    //Inserting resume into the table
                    InputStream inputStreamResume = resumePart.getInputStream();
                    statement.setBlob(4, inputStreamResume);

                } else { //If the student didn't upload a new resume

                    //Get the resume from the table
                    Blob resumeBlob = resultSet.getBlob(4);

                    //Re-upload the resume into the table
                    statement.setBlob(4, resumeBlob);
                }
                if (coverLetterPart != null && coverLetterPart.getSize() > 0) { //Checks if the student uploaded a new cover letter

                    //Inserting cover letter into the table
                    InputStream inputStreamLetter = coverLetterPart.getInputStream();
                    statement.setBlob(5, inputStreamLetter);

                } else { //If the student didn't upload a new cover letter

                    //Get the cover letter from the table
                    Blob coverLetterBlob = resultSet.getBlob(5);

                    //Re-upload the cover letter into the table
                    statement.setBlob(5, coverLetterBlob);
                }
                if (transcriptPart != null && transcriptPart.getSize() > 0) { //Checks if the student uploaded a new transcript

                    //Inserting transcript into the table
                    InputStream inputStreamTranscript = transcriptPart.getInputStream();
                    statement.setBlob(6, inputStreamTranscript);

                } else { //If the student didn't upload a new transcript

                    //Get the transcript from the table
                    Blob transcriptBlob = resultSet.getBlob(6);

                    //Re-upload the transcript into the table
                    statement.setBlob(6, transcriptBlob);
                }
                if (picturePart != null && picturePart.getSize() > 0) { //Checks if the student uploaded a new profile picture

                    //Inserting profile picture into the table
                    InputStream inputStreamPic = picturePart.getInputStream();
                    statement.setBlob(7, inputStreamPic);

                } else { //If the student didn't upload a new profile picture

                    //Get the profile picture from the table
                    InputStream inputStreamPic = resultSet.getBinaryStream(8);

                    //Re-upload the profile picture into the table
                    statement.setBlob(7, inputStreamPic);
                }

                statement.setString(8, email);
            }

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
