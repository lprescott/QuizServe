<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow" style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>		
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout?">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow">
			<h2 style="margin: 10px;">Admin Home</h2>
			<div class="manage-container">
				<form action="${pageContext.request.contextPath}/admin/question/question_management.jsp">
					<input class="shadow-button" type="submit" value="Question Management" />
				</form>
				<form action="${pageContext.request.contextPath}/admin/user/user_management.jsp">
					<input class="shadow-button" type="submit" value="User Management" />
				</form>
				<form action="${pageContext.request.contextPath}/admin/test/test_management.jsp">
					<input class="shadow-button" type="submit" value="Test Management" />
				</form>
			</div>
		</div>

		<div class="row">
			<div id="left" class="column shadow">
				<h3 style="margin: 10px;">Current Tests</h3>

			</div>
			<div id="right" class="column shadow">
				<h3 style="margin: 10px;">Tests Taken</h3>

			</div>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
	</div>
</body>

</html>
