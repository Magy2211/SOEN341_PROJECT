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
        String studentFirstName = "";
        String studentLastName = "";
        String fieldOfStudy;
        int jobPostingID = Integer.parseInt(request.getParameter("jobPostingID"));
        String status = "";
        String resumeBase64 = "";
        String coverLetterBase64 = "";
        String transcriptBase64 = "";
        String base64Image = "";

        try {
            PreparedStatement statement = connection.prepareStatement("select * from profile_information where email = ?");
            statement.setString(1, studentEmail);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                studentFirstName = resultSet.getString("firstName");
                studentLastName = resultSet.getString("lastName");
                fieldOfStudy = resultSet.getString("fieldOfStudy");

                InputStream imageData = resultSet.getBinaryStream(8);
                byte[] imageBytes = imageData.readAllBytes();
                base64Image = Base64.getEncoder().encodeToString(imageBytes);

                PreparedStatement statement1 = connection.prepareStatement("select * from applications where jobPostingID = ? AND studentEmail = ?");
                statement1.setInt(1, jobPostingID);
                statement1.setString(2, studentEmail);
                ResultSet resultSet1 = statement1.executeQuery();
                if (resultSet1.next()){
                    status = resultSet1.getString("Status");

                    Blob resumeBlob = resultSet1.getBlob(5);
                    byte[] resumeData = resumeBlob.getBytes(1, (int)resumeBlob.length());
                    resumeBase64 = Base64.getEncoder().encodeToString(resumeData);

                    // read cover letter as a PDF file
                    Blob coverLetterBlob = resultSet1.getBlob(6);
                    byte[] coverLetterData = coverLetterBlob.getBytes(1, (int)coverLetterBlob.length());
                    coverLetterBase64 = Base64.getEncoder().encodeToString(coverLetterData);

                    // read transcript as a PDF file
                    Blob transcriptBlob = resultSet1.getBlob(7);
                    byte[] transcriptData = transcriptBlob.getBytes(1, (int)transcriptBlob.length());
                    transcriptBase64 = Base64.getEncoder().encodeToString(transcriptData);
                }

                request.setAttribute("studentInformation", new StudentInformation(studentFirstName, studentLastName, studentEmail, fieldOfStudy, resumeBase64, coverLetterBase64, transcriptBase64, base64Image));
            }
            request.setAttribute("studentFirstName", studentFirstName);
            request.setAttribute("studentLastName", studentLastName);
            request.setAttribute("jobPostingID", jobPostingID);
            request.setAttribute("studentEmail", studentEmail);
            request.setAttribute("status", status);
            RequestDispatcher view = request.getRequestDispatcher("/ViewAStudentInformation.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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
