<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>User Home</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">

</head>
<body>
   <div class="header shadow">
      <a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
      <div class="logo-label"></div>
      <p>Logged in as ${email}.</p> 
      <form action="../Logout" method="post"><input type="submit" value="Logout?" ></form>
   </div>
   
   <div class="main-container">
	   <div class="main shadow">
         <!-- Content goes here. -->
         Hello, World!
      </div>
   </div>
   
   <div class="footer shadow">
      <p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
   </div>
</body>
</html>