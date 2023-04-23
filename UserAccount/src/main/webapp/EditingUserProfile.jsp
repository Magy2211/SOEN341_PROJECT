<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Edit student profile</title>
    <link rel="stylesheet" type="text/css" href="EditingUserProfile.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul>
                <li><a href="viewCreatedJobPostingsServlet?interview=false">Home</a></li>
                <li><a href="viewEmployerProfileServlet">Profile</a></li>
                <li><a href="AddJobPosting.html">Add Jobs </a></li>
                <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=employer">About</a></li>

            </ul>
        </nav>
    </div>
</header>


<head>
    <title>Edit student profile</title>
    <link rel="stylesheet" type="text/css" href="EditingUserProfile.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>


</head>


<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form action="editUserProfileServlet" method="post" enctype="multipart/form-data">


    <h3 style="color:orange;">Edit User Profile</h3>


    <div class="container">
        <br/>
        <form method="post" action="editUserProfileServlet">
            <table>
                <div class="form-group">
                    <label for="profile-pic">Profile Picture:</label>
                    <input type="file" id="profile-pic" name="profile-pic">
                </div>
                <div class="form-group">
                    <label for="first-name">First Name:</label>
                    <input type="text" id="first-name" name="first-name" value="${studentInformation.getFirstName()}"
                           required>
                </div>
                <div class="form-group">
                    <label for="last-name">Last Name:</label>
                    <input type="text" id="last-name" name="last-name" value="${studentInformation.getLastName()}"
                           required>
                </div>
                <div class="form-group">
                    <select id="engineering-field" name="engineering-field" required>
                        <option value="" selected disabled>Select Engineering Field</option>
                        <option value="aerospace-engineering" ${studentInformation.getFieldOfStudy().equals("aerospace-engineering") ? "selected" : ""}>
                            Aerospace Engineering
                        </option>
                        <option value="building-engineering" ${studentInformation.getFieldOfStudy().equals("building-engineering") ? "selected" : ""}>
                            Building Engineering
                        </option>
                        <option value="computer-engineering" ${studentInformation.getFieldOfStudy().equals("computer-engineering") ? "selected" : ""}>
                            Computer Engineering
                        </option>
                        <option value="civil-engineering" ${studentInformation.getFieldOfStudy().equals("civil-engineering") ? "selected" : ""}>
                            Civil Engineering
                        </option>
                        <option value="electrical-engineering" ${studentInformation.getFieldOfStudy().equals("electrical-engineering") ? "selected" : ""}>
                            Electrical Engineering
                        </option>
                        <option value="industrial-engineering" ${studentInformation.getFieldOfStudy().equals("industrial-engineering") ? "selected" : ""}>
                            Industrial Engineering
                        </option>
                        <option value="mechanical-engineering" ${studentInformation.getFieldOfStudy().equals("mechanical-engineering") ? "selected" : ""}>
                            Mechanical Engineering
                        </option>
                        <option value="software-engineering" ${studentInformation.getFieldOfStudy().equals("software-engineering") ? "selected" : ""}>
                            Software Engineering
                        </option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="resume">Resume:</label>
                    <input type="file" id="resume" name="resume">
                </div>
                <div class="form-group">
                    <label for="cover-letter">Cover Letter:</label>
                    <input type="file" id="cover-letter" name="cover-letter">
                </div>
                <div class="form-group">
                    <label for="transcript">Unofficial Transcript:</label>
                    <input type="file" id="transcript" name="transcript">
                </div>
                <button type="submit" class="submit-btn">Save</button>

    </div>
    <script src="script.js"></script>
</form>


</div>


</body>
</html>
