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
<title>Invite User(s)</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/question.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/test.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.0.min.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">

</head>

<body>
	<!-- Navbar -->
	<%@ include file = "../header.jsp" %>
	<!-- Content -->
	<div class="main-container" style="max-width: 1000px;">
		<div class="main shadow" style="padding: 0;">

			<!-- Error Message (if set) -->
			<%
				if (request.getParameter("success") != null) {
					if (request.getParameter("success").equals("false")) {
						out.println("<div id=\"error\" style=\"text-align:center; padding: 5px;margin: 5px;\"><p>" + request.getParameter("error") + "</p></div>");
					}
				}
			%>

			<!-- Success Message (if set) -->
			<%
				if (request.getParameter("success") != null) {
					if (request.getParameter("success").equals("true")) {
						out.println("<div id=\"success\" style=\"text-align:center; padding: 5px;margin: 5px;\"><p>Invitations Successfully Updated</p></div>");
					}
				}
			%>

			<div class="filter-box">
				<i class="fas fa-search filter-icon"></i> <input class="table-filter" type="text" id="filter" onkeyup="filterTable('filter', 'table')" placeholder="Filter the below table by user email or active status...">
			</div>

			<!-- Connect to DB and select all users -->
			<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
			<sql:query dataSource="${snapshot}" var="result"> SELECT * FROM USERS;</sql:query>

			<div class="form-container-test">
				<form class="quiz-form" action="${pageContext.request.contextPath}/InviteUsers" method="post">

					<!-- Hidden input with ID# -->
					<input id="TEST_ID" type="hidden" name="TEST_ID" value="<%=request.getParameter("TEST_ID")%>">

					<!-- Print table of all users -->
					<table id="table" class="table" style="width: 100%;">
						<tr>
							<th>User ID</th>
							<th>Email</th>
							<th>Active</th>
							<th></th>
						</tr>

						<c:forEach var="row" items="${result.rows}">
							<tr>
								<td><c:out value="${row.USERS_ID}" /></td>
								<td><c:out value="${row.EMAIL}" /></td>
								<td><c:out value="${row.IS_ACTIVE}" /></td>

								<!-- Connect to DB and select all user already invited -->
								<sql:setDataSource var="snapshot2" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
								<sql:query dataSource="${snapshot2}" var="result2"> SELECT * FROM USERS U INNER JOIN ALLOWED_USERS AU ON U.USERS_ID = AU.USERS_ID WHERE AU.TEST_ID = <%=request.getParameter("TEST_ID")%> AND U.USERS_ID = ${row.USERS_ID};</sql:query>

								<!-- user is active and invited -->
								<c:if test="${row.IS_ACTIVE == true and not empty result2.rows[0]}">
									<td><input class="cb" type="checkbox" id="${row.USERS_ID}" name="${row.USERS_ID}" checked></td>
								</c:if>

								<!-- user is active and not invited -->
								<c:if test="${row.IS_ACTIVE == true and empty result2.rows[0]}">
									<td><input class="cb" type="checkbox" id="${row.USERS_ID}" name="${row.USERS_ID}"></td>
								</c:if>

								<!-- user is inactive -->
								<c:if test="${row.IS_ACTIVE == false}">
									<td><input class="cb" type="checkbox" id="${row.USERS_ID}" name="${row.USERS_ID}" disabled></td>
								</c:if>

							</tr>
						</c:forEach>
					</table>

					<div style="padding: 45px">
						<input class="shadow-button" id="submit" type="submit" value="Update Invitations">
					</div>

				</form>
			</div>
		</div>
	</div>
	
</body>

</html>