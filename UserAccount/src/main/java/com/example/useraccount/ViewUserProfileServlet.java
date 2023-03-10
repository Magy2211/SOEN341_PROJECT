package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

@WebServlet(name = "viewUserProfileServlet", value = "/viewUserProfileServlet")
public class ViewUserProfileServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        String userType = (String) request.getSession().getAttribute("userType");
        if (userType.equals("Student")){
            try {
                PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    request.getSession().setAttribute("firstName", resultSet.getString(1));
                    request.getSession().setAttribute("lastName", resultSet.getString(2));
                    request.getSession().setAttribute("engineeringField", resultSet.getString(3));
                    //request.getSession().setAttribute("resume", resultSet.getBlob(4));
                    Blob resumeBlob = resultSet.getBlob(4);
                    byte[] resumeData = resumeBlob.getBytes(1, (int)resumeBlob.length());
                    String resumeBase64 = Base64.getEncoder().encodeToString(resumeData);
                    request.getSession().setAttribute("resume", resumeBase64);
                    Blob coverLetterBlob = resultSet.getBlob(5);
                    byte[] coverLetterData = coverLetterBlob.getBytes(1, (int)coverLetterBlob.length());
                    String coverLetterBase64 = Base64.getEncoder().encodeToString(coverLetterData);
                    request.getSession().setAttribute("coverLetter", coverLetterBase64);
                    // read transcript as a PDF file
                    Blob transcriptBlob = resultSet.getBlob(6);
                    byte[] transcriptData = transcriptBlob.getBytes(1, (int)transcriptBlob.length());
                    String transcriptBase64 = Base64.getEncoder().encodeToString(transcriptData);
                    request.getSession().setAttribute("transcript", transcriptBase64);

                    //request.getSession().setAttribute("coverLetter", resultSet.getBlob(5));
                    //request.getSession().setAttribute("transcript", resultSet.getBlob(6));
                    request.getSession().setAttribute("email", resultSet.getString(7));
                    //request.getSession().setAttribute("profilePic", resultSet.getBlob(8));
                    InputStream imageData = resultSet.getBinaryStream(8);


                    byte[] imageBytes = imageData.readAllBytes();
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                    BufferedImage img = ImageIO.read(bis);
                    request.setAttribute("profilePic", img);
                }
                RequestDispatcher view = request.getRequestDispatcher("/StudentHomePage.jsp");
                view.forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if (userType.equals("Employer")) {
            try {
                PreparedStatement statement = connection.prepareStatement("select * from employer_profile_information where email = ?");
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    request.getSession().setAttribute("firstName", resultSet.getString(1));
                    request.getSession().setAttribute("lastName", resultSet.getString(2));
                    request.getSession().setAttribute("company", resultSet.getString(3));
                    request.getSession().setAttribute("email", resultSet.getString(4));
                    Blob resumeBlob = resultSet.getBlob(5);
                    byte[] resumeData = resumeBlob.getBytes(1, (int)resumeBlob.length());
                    String resumeBase64 = Base64.getEncoder().encodeToString(resumeData);
                    request.getSession().setAttribute("jobPosting", resumeBase64);
                }
                RequestDispatcher view = request.getRequestDispatcher("/EmployerHomePage.jsp");
                view.forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
