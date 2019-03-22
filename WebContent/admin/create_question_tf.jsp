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
<title>Create A Question</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/create_question.css">
<script src="${pageContext.request.contextPath}/js/checkBox.js"></script>
</head>
</head>
<body>

	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img
			class="shadow" style="max-height: 60px;"
			src="${pageContext.request.contextPath}/img/graphic-seal.jpg"
			alt="SUNY Albany Seal"></a>
		<div class="logo-label"></div>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/admin/main.jsp">
			Go back </a>
		<form action="../Logout" method="post">
			<input type="submit" value="Logout?">
		</form>
	</div>

	<div class="main-container">
		<div class="main shadow">
			<!-- Content goes here. -->
			<div class="form-container-question">
				<form class="login-form" action="../CreateQuestion" method="post">

					<textarea class="q_input_text" id="q_text" name="q_text" rows="10"
						cols="30" placeholder="Question text goes here..." required></textarea>

					<div class="padded-bottom">
						Answer: <input class="cb" type="checkbox" id="true_cb"
							name="true_cb" onchange="checkBoxUpdate(this)"> <label
							for="true">True</label> <input class="cb" type="checkbox"
							id="false_cb" name="false_cb" onchange="checkBoxUpdate(this)">
						<label for="false">False</label>

						<script>
							
						</script>
					</div>

					<div class="padded-bottom">
						Attached image: <input type="file" id="q_image" name="q_image"
							accept="image/png, image/jpeg"> <br>
					</div>

					<input id="submit" type="submit" value="CREATE QUESTION">
				</form>
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("false")) {
							out.println("<div id=\"error\"><p>" + request.getParameter("error") + "</p></div>");
						}
					}
				%>
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("true")) {
							out.println("<div id=\"success\"><p>Successfully Added Question</p></div>");
						}
					}
				%>
			</div>
		</div>
	</div>


	<div class="footer shadow">
		<p>A quiz application for the ICSI 418Y final project, Spring
			2019.</p>
	</div>

</body>
</html>