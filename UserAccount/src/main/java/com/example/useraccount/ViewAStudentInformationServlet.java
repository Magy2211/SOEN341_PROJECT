package com.example.useraccount;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Base64;

@WebServlet(name = "viewAStudentInformationServlet", value = "/viewAStudentInformationServlet")
public class ViewAStudentInformationServlet extends HttpServlet {
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentEmail = request.getParameter("studentEmail");
        String employerEmail = request.getParameter("employerEmail");
        String studentFirstName;
        String studentLastName;
        String fieldOfStudy;
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));

        try {
            PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
            statement.setString(1, studentEmail);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                studentFirstName = resultSet.getString("firstName");
                studentLastName = resultSet.getString("lastName");
                fieldOfStudy = resultSet.getString("fieldOfStudy");

                Blob resumeBlob = resultSet.getBlob(4);
                byte[] resumeData = resumeBlob.getBytes(1, (int)resumeBlob.length());
                String resumeBase64 = Base64.getEncoder().encodeToString(resumeData);

                // read cover letter as a PDF file
                Blob coverLetterBlob = resultSet.getBlob(5);
                byte[] coverLetterData = coverLetterBlob.getBytes(1, (int)coverLetterBlob.length());
                String coverLetterBase64 = Base64.getEncoder().encodeToString(coverLetterData);

                // read transcript as a PDF file
                Blob transcriptBlob = resultSet.getBlob(6);
                byte[] transcriptData = transcriptBlob.getBytes(1, (int)transcriptBlob.length());
                String transcriptBase64 = Base64.getEncoder().encodeToString(transcriptData);

                InputStream imageData = resultSet.getBinaryStream(8);
                byte[] imageBytes = imageData.readAllBytes();
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                BufferedImage img = ImageIO.read(bis);
                request.setAttribute("studentInformation", new StudentInformation(studentFirstName, studentLastName, studentEmail, fieldOfStudy, resumeBase64, coverLetterBase64, transcriptBase64, img));
            }
            request.setAttribute("jobPostingID", jobPostingID);
            request.setAttribute("employerEmail", employerEmail);
            request.setAttribute("studentEmail", studentEmail);
            RequestDispatcher view = request.getRequestDispatcher("/ViewAStudentInformation.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
