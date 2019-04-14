<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="edu.albany.csi418.session.LoginEnum"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
<meta content="text/html;" charset="UTF-8">
<title>Question Management</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow" style="max-height: 65px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/admin/main.jsp"> Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>


	<!-- Content -->
	<div class="main-container" style="max-width: 1500px;">
		<div class="main shadow" style="padding: 0;">

			<!-- Message (if set) -->
			<%
				if (request.getParameter("message_mc") != null) {
					out.println("<div style=\"padding-top: 5px; margin: 5px; text-align: center;\" id=\"success\"><p>"
							+ request.getParameter("message_mc") + "</p></div>");
				}
			%>
			
			<div class="filter-box">
				<i class="fas fa-search filter-icon"></i>
				<input class="table-filter" type="text" id="filter1" onkeyup="filterTable('filter1', 'table1')" placeholder="Filter the below table by question text or category...">
			</div>

			<!-- Connect to DB and select all questions -->
			<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
			<sql:query dataSource="${snapshot}" var="result"> SELECT * FROM QUESTION Q INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.IS_TRUE_FALSE = 0 AND QA.IS_CORRECT_ANSWER = 1; </sql:query>

			<!-- Print table of all questions -->
			<table id="table1" class="table" style="width: 100%;">
				<tr>
					<th>ID</th>
					<th>Multiple Choice Question</th>
					<th>Category</th>
					<th>Correct Answer</th>
					<th># Answers</th>
					<th></th>
				</tr>

				<c:forEach var="row" items="${result.rows}">
					<tr>
						<td><c:out value="${row.QUESTION_ID}" /></td>
						<td><c:out value="${row.TEXT}" /></td>
						<td><c:out value="${row.CATEGORY}" /></td>
						<td><c:out value="${row.ANSWER}" /></td>
						<td><c:out value="${row.NUM_ANSWERS}" /></td>
						<td><a class="link-style" href="${pageContext.request.contextPath}/admin/question/edit_question_mc.jsp?QUESTION_ID=<c:out value="${row.QUESTION_ID}"/>">edit</a></td>
					</tr>
				</c:forEach>

				<tr>
					<td></td>
					<td><a class="link-style" href="${pageContext.request.contextPath}/admin/question/create_question_mc.jsp">create new multiple choice question</a></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- Content -->
	<div class="main-container" style="max-width: 1500px;">

			<div class="main shadow" style="padding: 0; margin-bottom: 82px !important">

			<!-- Message (if set) -->
			<%
				if (request.getParameter("message_tf") != null) {
					out.println("<div style=\"padding-top: 5px; margin: 5px; text-align: center;\" id=\"success\"><p>"
							+ request.getParameter("message_tf") + "</p></div>");
				}
			%>
			
			<div class="filter-box">
				<i class="fas fa-search filter-icon"></i>
				<input class="table-filter" type="text" id="filter2" onkeyup="filterTable('filter2', 'table2')" placeholder="Filter the below table by question text or category...">
			</div>
			
			<!-- Connect to DB and select all questions -->
			<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
			<sql:query dataSource="${snapshot}" var="result"> SELECT * from QUESTION WHERE IS_TRUE_FALSE = 1; </sql:query>

			<!-- Print table of all questions -->
			<table id="table2" class="table" style="width: 100%;">
				<tr>
					<th>ID</th>
					<th>True False Question</th>
					<th>Category</th>
					<th>True or False</th>
					<th></th>
				</tr>

				<c:forEach var="row" items="${result.rows}">
					<tr>
						<td><c:out value="${row.QUESTION_ID}" /></td>
						<td><c:out value="${row.TEXT}" /></td>
						<td><c:out value="${row.CATEGORY}" /></td>
						<td><c:out value="${row.TF_IS_TRUE}" /></td>
						<td><a class="link-style" href="${pageContext.request.contextPath}/admin/question/edit_question_tf.jsp?QUESTION_ID=<c:out value="${row.QUESTION_ID}"/>">edit</a></td>
					</tr>
				</c:forEach>

				<tr>
					<td></td>
					<td><a class="link-style" href="${pageContext.request.contextPath}/admin/question/create_question_tf.jsp">create new true false question</a></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application by <a class="link-style" href="${pageContext.request.contextPath}/about_us.jsp" >our team</a> for an ICSI 418Y/410 final project, Spring 2019.</p>
	</div>
</body>
</html>
