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
<title>Update Style</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/test.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/checkBox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.0.min.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
</head>

<body>
	<!-- Navbar -->
	<%@ include file = "header.jsp" %>
	<!-- Content -->
	<div class="main-container" style="max-width: 1500px;">
		<div class="main shadow" style="padding: 0; margin-bottom: 82px !important;">

			<div class="form-container-test">

				<!-- Error Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("false")) {
							out.println("<div id=\"error\" style=\"text-align:center; padding: 5px;\"><p>"
									+ request.getParameter("error") + "</p></div>");
						}
					}
				%>

				<!-- Success Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("true")) {
							out.println(
									"<div id=\"success\" style=\"text-align:center; padding: 5px;\"><p>Style Successfully Updated</p></div>");
						}
					}
				%>

				<!-- Form -->
				<form class="quiz-form" action="${pageContext.request.contextPath}/EditStyle" method="post" enctype="multipart/form-data">

					<div style="padding: 45px">

						<input class="t_input_text" id="header" name="header" type="text" placeholder="Header" required> <input class="t_input_text" id="footer" name="footer" type="text" placeholder="Footer" required>

						<div style="text-align: center;">
							Attached image: <input type="file" id="headerimg" name="headerimg" accept="image/png, image/jpeg">
						</div>

					</div>


					<div style="padding: 45px">
						<input class="shadow-button" id="submit" type="submit" value="UPDATE STYLE">
					</div>

				</form>
			</div>
		</div>
	</div>

</body>

</html>