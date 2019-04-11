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
	<title>User Home</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/user/main.jsp"><img class="shadow"
				style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg"
				alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>		
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow">
			<h2 style="margin: 10px;">User Home</h2>
			<div class="manage-container">
				<form action="${pageContext.request.contextPath}/user/update_account.jsp">
					<input class="shadow-button" type="submit" value="Update Your Account" />
				</form>
			</div>
		</div>

		<div class="row">
			<div id="left" class="column shadow">
				<h3 style="margin: 20px;">Assigned Tests</h3>
				
				<div class="filter-box">
					<i class="fas fa-search filter-icon"></i>
					<input class="table-filter" type="text" id="filter1" onkeyup="filterTable('filter1', 'table1')" placeholder="Filter the below table by test name...">
				</div>
				
				<!-- Connect to DB and select all admin's tests -->
				<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
				<sql:query dataSource="${snapshot}" var="result"> SELECT t.TITLE, AU.TEST_ASSIGNED,T.TEST_DUE, t.test_id  FROM TEST T INNER JOIN ALLOWED_USERS AU ON T.TEST_ID = AU.TEST_ID left join tests_taken on T.test_id = tests_taken.test_id WHERE AU.USERS_ID = ${id} and TEST_TAKEN_ID is null;</sql:query>

				<!-- Print table of user's tests -->
				<table id="table1" class="table" style="width: 100%;">
					<tr>
						<th>Test Name</th>
						<th>Assigned On</th>
						<th>Due Date</th>
						<th></th>
					</tr>
	
					<c:forEach var="row" items="${result.rows}">
						<tr>
							<td><c:out value="${row.TITLE}" /></td>
							<td><c:out value="${row.TEST_ASSIGNED}" /></td>		
							<td><c:out value="${row.TEST_DUE}" /></td>												
							<td><a class="link-style" href="${pageContext.request.contextPath}/user/test/take_test.jsp?USERS_ID=${id}&TEST_ID=<c:out value="${row.TEST_ID}"/>">take test</a></td>					
						</tr>
					</c:forEach>
	
				</table>

			</div>
			<div id="right" class="column shadow">
				<h3 style="margin: 20px;">Assigned Tests</h3>
				
				<div class="filter-box">
					<i class="fas fa-search filter-icon"></i>
					<input class="table-filter" type="text" id="filter1" onkeyup="filterTable('filter1', 'table1')" placeholder="Filter the below table by test name...">
				</div>
				
				<!-- Connect to DB and select all admin's tests -->
				<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
				<sql:query dataSource="${snapshot}" var="result"> SELECT * FROM TEST T INNER JOIN tests_taken AU ON T.TEST_ID = AU.TEST_ID WHERE AU.USERS_ID = ${id};</sql:query>

				<!-- Print table of user's tests -->
				<table id="table1" class="table" style="width: 100%;">
					<tr>
						<th>Test Name</th>
						<th>Taken Date</th>
						<th>Score</th>
						<th></th>
					</tr>
	
					<c:forEach var="row" items="${result.rows}">
						<tr>
							<td><c:out value="${row.TITLE}" /></td>
							<td><c:out value="${row.TEST_DATE}" /></td>		
							<td><c:out value="${row.SCORE}" /></td>												
							<td><a class="link-style" href="../TestResult?USERS_ID=${id}&TEST_ID=<c:out value="${row.TEST_ID}"/>">test details</a></td>					
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
