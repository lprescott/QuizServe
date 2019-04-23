<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
	<meta content="text/html;" charset="UTF-8">
	<title>Create A Question</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/question.css">
</head>

<body>
	<!-- Navbar -->
	<%@ include file = "../header.jsp" %>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow">
			<div class="form-container-question">
				<!-- Error Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("false")) {
							out.println("<div style=\"padding-bottom:15px; margin: 5px\" id=\"error\"><p>" + request.getParameter("error") + "</p></div>");
						}
					}
				%>
				
				<!-- Success Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("true")) {
							out.println("<div style=\"padding-bottom:15px; margin: 5px\" id=\"success\">Question Successfully Created</div>");
						}
					}
				%>
				
				<!-- Form -->
				<form class="quiz-form" action="${pageContext.request.contextPath}/CreateQuestionMC" method="post" enctype="multipart/form-data">
					<textarea class="q_input_text" id="q_text" name="q_text" rows="10" cols="30"
						placeholder="Question text goes here..." required></textarea>
					<input class="q_input_text" id="q_category" name="q_category" type="text" placeholder="Category">
					<input class="q_input_text" id="q_a1" name="q_a1" type="text" placeholder="Answer #1" required>
					<input class="q_input_text" id="q_a2" name="q_a2" type="text" placeholder="Answer #2" required>
					<input class="q_input_text" id="q_a3" name="q_a3" type="text" placeholder="Answer #3" required>
					<input class="q_input_text" id="q_a4" name="q_a4" type="text" placeholder="Answer #4" required>
					<input class="q_input_text" id="q_a5" name="q_a5" type="text" placeholder="Answer #5"> <input
						class="q_input_text" id="q_a6" name="q_a6" type="text" placeholder="Answer #6">
					<div class="padded-bottom">
						Correct Answer #: <input id="q_correct_answer" type="number" name="q_correct_answer" min="1"
							max="6" required>
					</div>
					<div class="padded-bottom">
						Attached image: <input type="file" id="q_image" name="q_image" accept="image/png, image/jpeg">
					</div>
					<input class="shadow-button" id="submit" type="submit" value="CREATE QUESTION">
				</form>
			</div>
		</div>
	</div>

</body>

</html>