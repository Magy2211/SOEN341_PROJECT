package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "editUserProfileServlet", value = "/editUserProfileServlet")
@MultipartConfig
public class EditUserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        Part picturePart = request.getPart("profile-pic");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String fieldOfStudy = request.getParameter("engineering-field");
        Part resumePart = request.getPart("resume");
        Part coverLetterPart = request.getPart("cover-letter");
        Part transcriptPart = request.getPart("transcript");

        InputStream inputStreamPic = picturePart.getInputStream();
        InputStream inputStreamResume = resumePart.getInputStream();
        InputStream inputStreamLetter = coverLetterPart.getInputStream();
        InputStream inputStreamTranscript = transcriptPart.getInputStream();

        try {
            /*Statement statement = connection.createStatement();
            int result = statement.executeUpdate("insert into profile_information values ('" + firstName + "', '" + lastName + "', '" + fieldOfStudy + "', '" + inputStreamResume + "','" + inputStreamLetter + "', '" + inputStreamTranscript + "', '" + email + "', '" + inputStreamPic + "')");

            */
            PreparedStatement statement = connection.prepareStatement("update profile_information set firstName=?, lastName=?, fieldOfStudy=?, resume=?, coverLetter=?, unofficialTranscript=?/*, profilePicture=?*/ where email = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, fieldOfStudy);
            statement.setBlob(4, inputStreamResume);
            statement.setBlob(5, inputStreamLetter);
            statement.setBlob(6, inputStreamTranscript);
            statement.setString(7, email);
            //statement.setBlob(8, inputStreamPic);

            PrintWriter out = response.getWriter();
            int result = statement.executeUpdate();
            if (result > 0) {
                out.print("<H1>Profile created</H1>");
                //Go back to student home page
                RequestDispatcher view = request.getRequestDispatcher("/StudentHomePage.jsp");
                view.forward(request, response);
                request.getSession().setAttribute("email", email);
                response.sendRedirect("ViewUserProfileServlet");
            }
            else
                out.print("<H1> Error creating the profile </H1>");

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
