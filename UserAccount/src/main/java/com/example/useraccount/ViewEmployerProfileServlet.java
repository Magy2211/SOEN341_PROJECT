package com.example.useraccount;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Base64;

@WebServlet(name = "viewEmployerProfileServlet", value = "/viewEmployerProfileServlet")
public class ViewEmployerProfileServlet extends HttpServlet {

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
