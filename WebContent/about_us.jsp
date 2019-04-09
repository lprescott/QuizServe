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
<title>Admin Home</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow" style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="row">
			<div id="left" class="column shadow">
				<h3 style="margin: 20px;">Who are we?</h3>
				<ul>
					<li style="padding-bottom: 5px;">Luke Prescott</li>
					<li style="padding-bottom: 5px;">Sean Loucks</li>
					<li style="padding-bottom: 5px;">Jack Holden</li>
					<li style="padding-bottom: 5px;">Max Moore</li>
					<li style="padding-bottom: 5px;">Chin Wa Cheung</li>
					<li style="padding-bottom: 5px;">Will Dahl</li>
					<li style="padding-bottom: 5px;">Gary Passarelli</li>
				</ul>

			</div>
			<div id="right" class="column shadow">
				<h3 style="margin: 20px;">About the application:</h3>
				<ul>
					<li style="padding-bottom: 5px;"><a class="link-style" href="https://github.com/lprescott/ICSI418-Group-Project">GitHub</a></li>
					<li style="padding-bottom: 5px;"><a class="link-style" href="https://trello.com/b/pfH92DPN/icsi-418-group-project-sprint-1-%F0%9F%9A%80-03-07-19-03-21-19">Trello Board</a></li>
					<li style="padding-bottom: 5px;"><a class="link-style" href="https://drive.google.com/file/d/1XlezssqVcUBls8oMyGCVEdJGmtN95HkN/view?usp=sharing">ER Diagram</a></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application by <a class="link-style" href="${pageContext.request.contextPath}/about_us.jsp" >us</a> for an ICSI 418Y/410 final project, Spring 2019.</p>
	</div>
</body>

</html>
