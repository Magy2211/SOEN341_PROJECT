
<!DOCTYPE html>
<html lang="en">
<head>   
  
      <link rel="stylesheet" type="text/css" href="StudentHomePage.css">
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
<form action="createUserProfileServlet" method="post" enctype="multipart/form-data">


<!--  <h3 style="color:#FF77FF;">*User Profile*</h3>
 -->         
     <h3 style="color:orange;">*User Profile*</h3>   
        
<div class="container">
<br/>
<form method="get" action="viewUserProfileServlet">
    <div class="form-group">
<h1>First Name: ${firstName}</h1>
    <h1>Last Name: ${lastName}</h1>
    	<h1>Email: ${email}</h1>
        <h1>Profile picture: </h1>
        <img src="data:image/jpeg;base64,${javax.xml.bind.DatatypeConverter.printBase64Binary(profilePic)}" width="240" height="300"/>
  <h1>Resume:</h1>
 <a href="data:application/pdf;base64,${coverLetter}" target="_blank"> Resume </a>
      <h1>Cover Letter:</h1>
			 <a href="data:application/pdf;base64,${coverLetter}" target="_blank"> Cover Letter </a>
 
  <h1>Transcript:</h1>
        <a href="data:application/pdf;base64,${transcript}" target="_blank"> Transcript </a>
        
   <h1>Engineering field of study: ${engineeringField}</h1>
    <label>
       <label><a href="EditingUserProfile.html">Edit Profile Information<br></a></label>

    </div>
</form>  
</div>                   
</body>
</html>