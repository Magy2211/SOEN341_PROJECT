package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "applyForAJobPostingServlet", value = "/applyForAJobPostingServlet")
@MultipartConfig
public class ApplyForAJobPostingServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //int jobPostingID = Integer.parseInt(request.getParameter("id"));
        int jobPostingID = (Integer) request.getSession().getAttribute("id");
        String studentEmail = (String) request.getSession().getAttribute("studentEmail");
        String[] defaultInformation = request.getParameterValues("default");
        boolean defaultResume = false;
        boolean defaultCoverLetter = false;
        boolean defaultTranscript = false;
        InputStream inputStreamResume = null;
        InputStream inputStreamLetter = null;
        InputStream inputStreamTranscript = null;
        
        for (int i = 0; i < defaultInformation.length; i++){
            if (defaultInformation[i].equals("defaultResume")){
                defaultResume = true;
            } else if (defaultInformation[i].equals("defaultCoverLetter")) {
                defaultCoverLetter = true;
            } else if (defaultInformation[i].equals("defaultTranscript")){
                defaultTranscript = true;
            }
        }
        try {
            PreparedStatement statement1 = connection.prepareStatement("SELECT * from profile_information where email = ?");
            statement1.setString(1, studentEmail);
            ResultSet resultSet = statement1.executeQuery();
            if(resultSet.next()) {
                if (!defaultResume) {
                    Part resumePart = request.getPart("resume");
                    inputStreamResume = resumePart.getInputStream();
                }
                else {
                    inputStreamResume = resultSet.getBinaryStream("resume");
                }
                if (!defaultCoverLetter) {
                    Part coverLetterPart = request.getPart("cover-letter");
                    inputStreamLetter = coverLetterPart.getInputStream();
                }
                else {
                    inputStreamLetter = resultSet.getBinaryStream("coverLetter");
                }
                if (!defaultTranscript) {
                    Part transcriptPart = request.getPart("transcript");
                    inputStreamTranscript = transcriptPart.getInputStream();
                }
                else {
                    inputStreamTranscript = resultSet.getBinaryStream("unofficialTranscript");
                }
            }
            
            PreparedStatement statement = connection.prepareStatement("INSERT INTO applications (jobPostingID, studentEmail, Status, resume, coverLetter, transcript) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, jobPostingID);
            statement.setString(2, studentEmail);
            statement.setString(3, "Applied to");
            statement.setBlob(4, inputStreamResume);
            statement.setBlob(5, inputStreamLetter);
            statement.setBlob(6, inputStreamTranscript);
            int result = statement.executeUpdate();
            PrintWriter out = response.getWriter();
            if (result > 0) {
                RequestDispatcher view = request.getRequestDispatcher("/viewJobPostingsServlet");
                view.forward(request, response);
            } else
                out.print("<H1> Error applying for the job </H1>");
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
