<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow"
				style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg"
				alt="SUNY Albany Seal"></a>
		<p>Logged in as ${email}.</p>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout?">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow">
			<h2>Admin Home</h2>
			<p>Here you have CRUD access to users, questions, and tests.</p>
			<ul>
				<li>Question management <a href="${pageContext.request.contextPath}/admin/question/question_management.jsp">here</a>.

				<li>User management <a href="${pageContext.request.contextPath}/admin/user/user_management.jsp">here</a>.

				</li>
				<li>Tests
					<ul>
						<li>Create a new test, <a href="">here</a>.
						</li>
						<li>View all tests, <a href="${pageContext.request.contextPath}/admin/test/test_list.jsp">here</a>.
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	
	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application for the ICSI 418Y final project, Spring
			2019.</p>
	</div>
</body>

</html>
