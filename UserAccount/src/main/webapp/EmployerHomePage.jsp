
<!DOCTYPE html>
<html lang="en">
  <html>
<head>
	<title>Job Posting Website</title>
	<link rel="stylesheet" type="text/css" href="EmployerHomePage.css">
</head>
<body>
	<header>
		<div class="container">
			<h1>Job Posting Website</h1>
			<nav>
				<ul>
					<li><a href="#">Home</a></li>
					<li><a href="#">Profile</a></li>
					<li><a href="#">Interviews</a></li>
					<li><a href="#">About</a></li>
					
				</ul>
			</nav>
		</div>
	</header>
	
<head>   
  
      <link rel="stylesheet" type="text/css" href="EmployerHomePage.css">
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
      <form method="get" action="viewEmployerProfileServlet">
      
        <h3 style="color:black;">Welcome </h3>
            <link rel="stylesheet" type="text/css" href="index.css">

               
    <div class="form-group">
<label>First Name: ${firstName}</label>
    <label>Last Name: ${lastName}</label>
    <label>Email: ${email}</label>
    <label>Company: ${company}</label>
       <label><a style="color:blue;" href="EditingEmployerProfile.html">Edit Profile Information<br></a></label>
        <label><a style="color:blue;" href="AddJobPosting.html">Add a job posting<br></a></label>
        <label><a style="color:blue;" href="viewCreatedJobPostingsServlet?employerEmail=${email}">View job postings created<br></a></label>
    			</div>
		</form> 

      </body>
</html>
