<!DOCTYPE html>
<html lang="en">
<head>
    <title>Creating account</title>
    <!-- add the white screen in the middle  -->

    <link rel="stylesheet" type="text/css" href="Index.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>


</head>


<body>
<!-- To add the shapes which are the two circles that surround the white screen that is in the middle of the website page -->

<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form method="post" action="createUserServlet">


    <h3 style="color:black;">Account Registration</h3>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#user-type").change(function () {
                if ($(this).val() == "Admin") {
                    $("#admin-code").removeAttr("disabled");
                    $("#admin-code").focus();
                } else {
                    $("#admin-code").attr("disabled", "disabled");
                }
            });
        });
    </script>

    <div class="container">
        <br/>
        <form method="post" action="createUserServlet">
            <table>
                <tr>
                </tr>
                <tr>
                    <!-- let the user select "user type" -->
                    <!-- creating options as dropdown menu  -->
                    <div class="form-group">
                        <label for="user-type">Select user type:</label>
                        <select id="user-type" name="user-type" required>
                            <option value="" selected disabled>Select User Type</option>
                            <option value="Student">Student</option>
                            <option value="Employer">Employer</option>
                            <option value="Admin">Admin</option>
                        </select>
                    </div>
                <tr>
                    <!-- let user fill out the email, password and AAC for their account registration -->
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
                <div class="form-group">
                    <td>AAC:</td>
                    <td><input name="admin-code" id=admin-code disabled="disabled"/></td>
                </div>
                <tr>
                    <td/>
                    <button type="submit" class="submit-btn">Create Account</button>

            </table>
        </form>
    </div>
</body>
</html>
