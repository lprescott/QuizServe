<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="edu.albany.csi418.session.LoginEnum"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
<meta content="text/html;" charset="UTF-8">
<title>Upload a CSV of Questions</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/question.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
</head>

<body>
	<!-- Navbar -->
	<%@ include file = "header.jsp" %>

	<!-- Content -->
	<div class="main-container" style="max-width: 500px;">
		<div class="main shadow">
			<div class="form-container-question" style="padding: 30px;">

				<!-- Error Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("false")) {
							out.println("<div id=\"error\" style=\"text-align:center; padding: 5px; margin: 5px;\"><p>" + request.getParameter("error") + "</p></div>");
						}
					}
				%>

				<!-- Success Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("true")) {
							out.println("<div id=\"success\" style=\"text-align:center; padding: 5px; margin: 5px;\"><p>File Successfully Uploaded</p></div>");
						}
					}
				%>

				<h2 style="margin: 10px;">Upload File (.csv)</h2>
				<br>
				<form action="${pageContext.request.contextPath}/FileUpload" method="post" enctype="multipart/form-data">

					<input class="q_input_text" id="q_test" name="q_test" type="text" placeholder="Test Name (Leave Empty for No Test!)"> <br>
					<input required class="q_input_text" id="q_category" name="q_category" type="text" placeholder="Question Category"> <br>
					<input required id="csv_file" name="csv_file" type="file" accept=".csv" /><br> 
					<br> <input class="shadow-button" id="submit" type="submit" value="UPLOAD">
				</form>
			</div>
		</div>
	</div>

</body>

</html>
