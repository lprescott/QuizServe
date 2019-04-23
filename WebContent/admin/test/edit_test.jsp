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
<title>Edit A Test</title>
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
	<%@ include file = "../header.jsp" %>

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
									"<div id=\"success\" style=\"text-align:center; padding: 5px;\"><p>Test Successfully Updated</p></div>");
						}
					}
				%>

				<!-- Connect to DB and select all questions -->
				<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
				<sql:query dataSource="${snapshot}" var="q_result"> SELECT * FROM QUESTION ORDER BY CATEGORY;</sql:query>
				<sql:query dataSource="${snapshot}" var="t_result"> SELECT * FROM TEST WHERE TEST_ID=<%=request.getParameter("TEST_ID")%>;</sql:query>

				<!-- Form -->
				<form class="quiz-form" action="${pageContext.request.contextPath}/EditTest" method="post" enctype="multipart/form-data">

					<!-- Hidden input with ID# -->
					<input id="TEST_ID" type="hidden" name="TEST_ID" value="<%=request.getParameter("TEST_ID")%>">

					<div style="padding: 45px">

						<input class="t_input_text" id="test_title" name="test_title" type="text" placeholder="Title" value="${t_result.rows[0].TITLE}" required> <input class="t_input_text" id="test_header" name="test_header" type="text" placeholder="Header" value="${t_result.rows[0].HEADER_TEXT}" required> <input class="t_input_text" id="test_footer" name="test_footer" type="text" placeholder="Footer" value="${t_result.rows[0].FOOTER_TEXT}" required>

						<div style="text-align: center;">
							Attached image:
							<output name="image">${t_result.rows[0].IMAGE_NAME}</output>
							<input type="file" id="t_image" name="t_image" accept="image/png, image/jpeg"><br> <br> Due Date: <input type="date" id="test_due" name="test_due" value="${t_result.rows[0].TEST_DUE}">
						</div>

					</div>

					<div class="filter-box">
						<i class="fas fa-search filter-icon"></i> <input class="table-filter" type="text" id="filter1" onkeyup="filterTable('filter1', 'table1')" placeholder="Filter the below table by question text or category...">
					</div>

					<script>
						$(document).ready(function() {
							$(window).keydown(function(event) {
								if (event.keyCode == 13) {
									event.preventDefault();
									return false;
								}
							});
						});
					</script>

					<!-- Print table of all questions -->
					<table id="table1" class="table" style="width: 100%;">
						<tr>
							<th>ID</th>
							<th>Question</th>
							<th>Category</th>
							<th><input class="cb_all" type="checkbox" id="all_cb" name="all_cb" onClick="toggle(this)"></th>
						</tr>

						<c:forEach var="row" items="${q_result.rows}">
							<tr>
								<td><c:out value="${row.QUESTION_ID}" /></td>
								<td><c:out value="${row.TEXT}" /></td>
								<td><c:out value="${row.CATEGORY}" /></td>

								<sql:query dataSource="${snapshot}" var="active_result"> SELECT * FROM TEST_QUESTIONS WHERE TEST_ID=<%=request.getParameter("TEST_ID")%> AND QUESTION_ID = ${row.QUESTION_ID};</sql:query>

								<!-- question is a part of test -->
								<c:if test="${active_result.rowCount != 0}">
									<td><input class="cb" type="checkbox" id="${row.QUESTION_ID}" name="${row.QUESTION_ID}" checked></td>
								</c:if>

								<!-- question is not a part of test -->
								<c:if test="${active_result.rowCount == 0}">
									<td><input class="cb" type="checkbox" id="${row.QUESTION_ID}" name="${row.QUESTION_ID}"></td>
								</c:if>

							</tr>
						</c:forEach>

					</table>

					<div style="padding: 45px">
						<div class="padded-bottom">
							<input class="shadow-button" id="submit" type="submit" name="submit" value="UPDATE">
						</div>

						<input class="shadow-button" id="delete" onclick="return confirm('Are you sure?');" type="submit" name="submit" value="DELETE">
					</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>