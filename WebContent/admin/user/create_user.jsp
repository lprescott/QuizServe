<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta content="text/html;" charset="UTF-8">
<title>Create A User</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<script src="${pageContext.request.contextPath}/js/checkBox.js"></script>
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow" style="max-height: 65px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/admin/user/user_management.jsp"> Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>

	<!-- Content -->
	<div class="login-main">
		<div class="form-container shadow">

			<!-- Error Message (if set) -->
			<%
				if (request.getParameter("success") != null) {
					if (request.getParameter("success").equals("false")) {
						out.println("<div style=\"text-align:center; padding-bottom:15px; margin: 5px;\" id=\"error\"><p>" + request.getParameter("error") + "</p></div>");
					}
				}
			%>

			<!-- Success Message (if set) -->
			<%
				if (request.getParameter("success") != null) {
					if (request.getParameter("success").equals("true")) {
						out.println("<div id=\"success\" style=\"text-align:center; padding-bottom:15px; margin: 5px;\">User Successfully Created</div>");
					}
				}
			%>

			<!-- Form -->
			<form class="quiz-form" action="${pageContext.request.contextPath}/CreateUser" method="post">
				<input id="email" name="email" type="email" placeholder="email" required> <input id="password" name="password" type="password" placeholder="password" required> <input id="password-confirm" name="password-confirm" type="password" placeholder="confirm password" required>

				<div class="padded-bottom">
					<input class="cb" type="checkbox" id="active_cb" name="active_cb" value="active_cb" onchange="checkBoxUpdate(this, 'cb')" checked> <label for="active_cb">Active</label> <input class="cb" type="checkbox" id="inactive_cb" name="inactive_cb" value="inactive_cb" onchange="checkBoxUpdate(this, 'cb')"> <label for="inactive_cb">Inactive</label>
				</div>

				<input class="shadow-button" id="submit" type="submit" value="CREATE">
			</form>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>
			A quiz application by <a class="link-style" href="${pageContext.request.contextPath}/about_us.jsp">us</a> for an ICSI 418Y/410 final project, Spring 2019.
		</p>
	</div>
</body>

</html>