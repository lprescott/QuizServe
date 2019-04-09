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
		<div class="main shadow">
			<h2 style="margin: 10px;">Admin Home</h2>
			<div class="manage-container">
				<form action="${pageContext.request.contextPath}/admin/question/question_management.jsp">
					<input class="shadow-button" type="submit" value="Question Management" />
				</form>
				<form action="${pageContext.request.contextPath}/admin/user/user_management.jsp">
					<input class="shadow-button" type="submit" value="User Management" />
				</form>
				<form action="${pageContext.request.contextPath}/admin/admin_management.jsp">
					<input class="shadow-button" type="submit" value="Admin Management" />
				</form>
				<form action="${pageContext.request.contextPath}/admin/test/test_management.jsp">
					<input class="shadow-button" type="submit" value="Test Management" />
				</form>
			</div>
		</div>

		<div class="row">
			<div id="left" class="column shadow">
				<h3 style="margin: 20px;">Current Tests</h3>
				
				<div class="filter-box">
					<i class="fas fa-search filter-icon"></i>
					<input class="table-filter" type="text" id="filter1" onkeyup="filterTable('filter1', 'table1')" placeholder="Filter the below table by test name...">
				</div>
				
				<!-- Connect to DB and select all admin's tests -->
				<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
				<sql:query dataSource="${snapshot}" var="result"> SELECT TEST_ID, HEADER_TEXT, IMAGE_NAME, FOOTER_TEXT FROM TEST WHERE ADMIN_ID = ${id};</sql:query>

				<!-- Print table of admin's tests -->
				<table id="table1" class="table" style="width: 100%;">
					<tr>
						<th>ID</th>
						<th>Test Name</th>
						<th></th>
						<th></th>
					</tr>
	
					<c:forEach var="row" items="${result.rows}">
						<tr>
							<td><c:out value="${row.TEST_ID}" /></td>
							<td><c:out value="${row.TITLE}" /></td>
							<td><a class="link-style" href="${pageContext.request.contextPath}/admin/test/invite_users.jsp?TEST_ID=<c:out value="${row.TEST_ID}"/>">invite users</a></td>					
							<td><a class="link-style" href="${pageContext.request.contextPath}/admin/test/edit_test.jsp?TEST_ID=<c:out value="${row.TEST_ID}"/>">edit</a></td>
						</tr>
					</c:forEach>
	
				</table>
			
			</div>
			<div id="right" class="column shadow">
				<h3 style="margin: 20px;">Tests Taken</h3>
				
				<div class="filter-box">
					<i class="fas fa-search filter-icon"></i>
					<input class="table-filter" type="text" id="filter2" onkeyup="filterTable('filter2', 'table2')" placeholder="Filter the below table by user id or test name...">
				</div>
				
				<!-- Connect to DB and select al] admin's tests -->
				<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
				<sql:query dataSource="${snapshot}" var="result"> SELECT * FROM TEST T INNER JOIN TESTS_TAKEN TT ON T.TEST_ID = TT.TEST_ID INNER JOIN TESTS_TAKEN_RESULTS TTR ON TTR.TEST_TAKEN_ID = TT.TEST_TAKEN_ID INNER JOIN RESULTS R ON TTR.RESULTS_ID = R.RESULTS_ID WHERE T.ADMIN_ID = ${id};</sql:query>

				<!-- Print table of admin's tests -->
				<table id="table2" class="table" style="width: 100%;">
					<tr>
						<th>ID</th>
						<th>User ID</th>
						<th>Date/Time Taken</th>
						<th>Test Name</th>
						<th></th>
					</tr>
	
					<c:forEach var="row" items="${result.rows}">
						<tr>
							<td><c:out value="${row.TEST_ID}" /></td>
							<td><c:out value="${row.USERS_ID}" /></td>
							<td><c:out value="${row.TEST_DT}" /></td>
							<td><c:out value="${row.HEADER_TEXT}" /></td>
							<td><a class="link-style" href="${pageContext.request.contextPath}/admin/test/test_results.jsp?TEST_ID=<c:out value="${row.TEST_ID}"/>&?USER_ID=<c:out value="${row.USER_ID}"/>">results</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application by <a class="link-style" href="${pageContext.request.contextPath}/about_us.jsp" >our team</a> for an ICSI 418Y/410 final project, Spring 2019.</p>
	</div>
</body>

</html>
