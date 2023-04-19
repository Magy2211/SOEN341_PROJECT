<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Creating account</title>

    <link rel="stylesheet" type="text/css" href="Index.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>

    <script>
        function on_change(select) {
            var adminCodeInput = document.getElementById("admin-code");
            if (select.value == "Admin") {
                adminCodeInput.style.display = "block";
            } else {
                adminCodeInput.style.display = "none";
            }
        }
    </script>

</head>


<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form method="post" action="createUserServlet">


    <h3 style="color:black;">Account Registration</h3>


    <div class="container">
        <br/>
        <form method="post" action="createUserServlet">
            <table>
                <tr>
                </tr>
                <tr>
                    <div class="form-group">
                        <label for="user-type">Select user type:</label>
                        <select id="user-type" name="user-type" required onchange='on_change(this)'>
                            <option value="" selected disabled>Select User Type</option>
                            <option value="Student">Student</option>
                            <option value="Employer">Employer</option>
                            <option value="Admin">Admin</option>
                        </select>
                    </div>
                <tr>
                    <div class="form-group">
                        <td>Email:</td>
                        <td><input name="email"/></td>
                    </div>
                </tr>
                <tr>
                    <div class="form-group">
                        <td>Password:</td>
                        <td><input name="password" type="password"/></td>
                    </div>
                </tr>
                <tr id="admin-code" style="display:none">
                    <div class="form-group">
                        <td>AAC:</td>
                        <td><input name="admin-code"/></td>
                    </div>
                </tr>
                <tr>
                    <td/>
                    <button type="submit" class="submit-btn">Create Account</button>
            </table>
        </form>
    </div>
</body>
</html>