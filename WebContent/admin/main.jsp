<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta content="text/html;" charset="UTF-8">
	<title>Admin Home</title>
  	  <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>  
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
   <div class="header shadow">
      <a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img class="shadow" style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
      <div class="logo-label">  </div>
      <p>Logged in as ${email}. </p> 
      <a id="link" href="${pageContext.request.contextPath}/admin/create_user.jsp"> Create a user </a>
      <form action="../Logout" method="post"><input type="submit" value="Logout?" ></form>
   </div>

   <div class="main-container">
	   <div class="main shadow">
         <!-- Content goes here. -->
         <h2>Admin Home</h2>
		<p>Here you have CRUD access to users, questions, and tests.</p>
		
		<ul>
		    <li>Questions
		        <ul>
		            <li>Create a new question, <a href="">here</a>.</li>
		            <li>View all questions, <a href="">here</a>.</li>
		        </ul>
		    </li>
		    <li>Users
		        <ul>
		            <li>Create a new user, <a href="${pageContext.request.contextPath}/admin/create_user.jsp">here</a>.</li>
		            <li>View all users, <a href="">here</a>.</li>
		        </ul>
		    </li>
		    <li>Tests
		        <ul>
		            <li>Create a new test, <a href="">here</a>.</li>
		            <li>View all tests, <a href="">here</a>.</li>
		        </ul>
		    </li>
		</ul>
		
      </div>
   </div>
   
   <div class="footer shadow">
      <p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
   </div>
</body>
</html>