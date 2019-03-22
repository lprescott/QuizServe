<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
	<meta content="text/html;" charset="UTF-8">
	<title>Create a user</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
   <div class="header shadow">
      <a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
      <div class="logo-label">  </div>
      <p>Logged in as ${email}. </p> 
      <a id="link" href="${pageContext.request.contextPath}/admin/main.jsp"> Go back </a>
      <form action="../Logout" method="post"><input type="submit" value="Logout?" ></form>
   </div>
   
    <div class="login-main">
         <div class="form-container shadow">
            <form class="login-form" action="../CreateUser" method="post">
               <input id="email" name="email" type="email" placeholder="email" required>
               <input id="password" name="password" type="password" placeholder="password" required>
               <input id="password" name="password-confirm" type="password" placeholder="confirm password" required>
               <input id="submit" type="submit" value="CREATE">
            </form>
            <%	
            if(request.getParameter("success") != null) {
            	 if (request.getParameter("success").equals("false")) {
 			        out.println("<div id=\"error\"><p>" + request.getParameter("error") + "</p></div>");
 			    }
            }
			%>
			<%
			if(request.getParameter("success") != null) {
			    if (request.getParameter("success").equals("true")) {
			    	out.println("<div id=\"success\"><p>Successfully Added User</p></div>");
			    }
			}
			%>
         </div>
      </div>
      <div class="footer shadow">
         <p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
      </div>
   </body>
</html>