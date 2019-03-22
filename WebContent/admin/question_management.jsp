<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<head>
	<meta content="text/html;" charset="UTF-8">
	<title>Question FrountPage</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/questioncenter.css">
</head>
</head>
<body>

	<div class="header">
      <a class="logo" href="${pageContext.request.contextPath}/login.jsp">
      <img style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
      <p>Authorized User: ${email}. </p> 
      <form action="main" method="post"><input type="submit" value="Back to Main" ></form>
    </div>
    
    
    <!-- Two Main Options -->
    <div class = "overview">
    	<div class = "creation" align="center" style="padding-top: 120px;">
    		<input class = "button_a"  type="button" value="Create New Question" onclick="open_create_form()"/>
    	</div>
    	<div class = "review" align="center" style="padding-top: 40px;">
    	
    			<!-- Currently Goes back, need change to Question View Page -->
    		<input class = "button_a" type="button"  value="View Existed Questions" onclick= "window.location.href='main.jsp'"/>
    		
    	</div>	
    </div>
    
 
	<div class =create_form  id = form_c
		style="display:none;">
		<div class = create_form_container>
		
			<!-- Currently Stay same page, need change to Insert New Question part and get response -->
			<form  action="question_management.jsp" method="post">
			
		        <input id="question" name="question_describe" type="text" placeholder="Descreption for Question" required>
		        <input id="answer1" name="answer_a" type="text" placeholder="answer1" required>
		        <input id="answer2" name="answer_b" type="text" placeholder="answer2" required>
		        <input id="answer3" name="answer_c" type="text" placeholder="answer3" >
		        <input id="answer4" name="answer_d" type="text" placeholder="answer4" >
		        <input id="answer5" name="answer_e" type="text" placeholder="answer5" >
		        <input id="answer6" name="answer_f" type="text" placeholder="answer6" >
		        <p> Available img File <input type="file" name="uploadFile"></p>
		        <br/><br/>
		        <a href="javascript:closeLogin();"><input id="submit" type="submit" value="Submit"></a>
		        <input  type="button" id = "cancel"  value="Cancal" onclick="close_create_form()"/>
	         </form>
         </div>
	</div>   
    
    <script>
    	function open_create_form(){
    	   document.getElementById("form_c").style.display="";
    	}
    	function close_create_form(){
    	   document.getElementById("form_c").style.display="none";
    	}
    </script>


</body>
</html>