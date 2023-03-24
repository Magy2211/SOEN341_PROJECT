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

@WebServlet(name = "viewUserProfileServlet", value = "/viewUserProfileServlet")
public class ViewUserProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root1234");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("studentEmail");
        try {
            PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                request.getSession().setAttribute("firstName", resultSet.getString(1));
                request.getSession().setAttribute("lastName", resultSet.getString(2));
                request.getSession().setAttribute("engineeringField", resultSet.getString(3));

                // read resume as a PDF file
                Blob resumeBlob = resultSet.getBlob(4);
                byte[] resumeData = resumeBlob.getBytes(1, (int)resumeBlob.length());
                String resumeBase64 = Base64.getEncoder().encodeToString(resumeData);
                request.getSession().setAttribute("resume", resumeBase64);

                // read cover letter as a PDF file
                Blob coverLetterBlob = resultSet.getBlob(5);
                byte[] coverLetterData = coverLetterBlob.getBytes(1, (int)coverLetterBlob.length());
                String coverLetterBase64 = Base64.getEncoder().encodeToString(coverLetterData);
                request.getSession().setAttribute("coverLetter", coverLetterBase64);

                // read transcript as a PDF file
                Blob transcriptBlob = resultSet.getBlob(6);
                byte[] transcriptData = transcriptBlob.getBytes(1, (int)transcriptBlob.length());
                String transcriptBase64 = Base64.getEncoder().encodeToString(transcriptData);
                request.getSession().setAttribute("transcript", transcriptBase64);

                request.getSession().setAttribute("email", resultSet.getString(7));

                InputStream imageData = resultSet.getBinaryStream(8);
                byte[] imageBytes = imageData.readAllBytes();
                /*ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                BufferedImage img = ImageIO.read(bis);
                request.setAttribute("profilePic", img);*/
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                request.getSession().setAttribute("profilePic", base64Image);

            }
            RequestDispatcher view = request.getRequestDispatcher("/StudentHomePage.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
