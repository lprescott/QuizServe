<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Admin Home</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
   <div class="header">
      <a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
      <div class="logo-label">  </div>
      <p>Logged in as ${email}. </p> 
      <a id="link" href="${pageContext.request.contextPath}/admin/create_user.jsp"> Create a user </a>
      <form action="../Logout" method="post"><input type="submit" value="Logout?" ></form>
   </div>
   
   
   
   <div class="footer">
      <p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
   </div>
</body>
</html>