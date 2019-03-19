<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
   <head>
      <meta content="text/html;" charset="UTF-8">
      <title>Login</title>
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
   </head>
   <body>
        <div class="header">
	      <a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
	      <div class="logo-label">  </div>
	      <form action="Logout" method="post"><input style="text-decoration: none !important;" type="submit" value="Clear Session" ></form>
	   </div>
   
      <div class="login-main">
         <div class="form-container">
            <form class="login-form" action="Login" method="post">
               <input id="email" name="email" type="email" placeholder="email" required>
               <input id="password" name="password" type="password" placeholder="password" required>
               <input id="submit" type="submit" value="LOGIN">
            </form>
            <c:if test="${not empty error}">
	           <div id=error>
	               <p>${error}</p>
	           </div>
         	</c:if>
         </div>
      </div>
      <div class="footer">
         <p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
      </div>
   </body>
</html>