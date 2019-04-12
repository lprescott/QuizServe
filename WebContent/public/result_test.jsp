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
<title>Test Result</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/testresult.css">

</head>
<body>
<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow" style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>	
		<form action="javascript:history.back(-1);" method="post">
			<input type="submit" value="Go back">
		</form>	
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>
	 
<!-- Constent -->
	<div class = "main-container">
		<div class = "basic-chart">
			<h1>Test Result</h1>
		</div>
		<div class = "result-table">
			<table class = "tableD">
			<thead>
				<tr>
					<th>Test Name</th>
					<th>Signed Date</th>
					<th>Taken Date</th>
					<th>Score</th>
					<th>C</th>
					<th>F</th>
				</tr>
				<tr>
				<c:forEach items = "${testoverAll}" var="data">
					<td>${data.H_T}</td>
					<td>${data.T_A}</td>
					<td>${data.T_D}</td>
					<td>${data.T_P}</td>
					<td>${data.C_A}</td>
					<td>${data.W_A}</td>
				</c:forEach>
				</tr>
				</table>
		</div>
		<div class = "contact">
			<a href="">Have a question of this test?...</a>
		</div>
		<div  class = "show-testdeteils">
			<h2 style="font-family:monospace; text-align:center; font-size:35px">Original Test Page</h2>
			<% int title = 1; %>
			<c:forEach items="${testPage}" var="question">
				<div class = "question-cell">
				<div style="float:left;clear:left;height:15px;width:20px"></div>
					<p><%out.print("Q"+title+":"); %>${question.Question_TEXT}</p>
					<form name=myform>
					<%title++; %>
						<c:choose>
							<c:when test="${question.ANSWER_1 == question.U_ANSWER}">
								<div style="float:left;clear:left;height:15px;width:20px"></div>
								<input type="radio" name=myradio checked disabled>A: ${question.ANSWER_1}<p></p>
							</c:when>
							<c:otherwise>
								<div style="float:left;clear:left;height:15px;width:20px"></div>
								<input type="radio" name=myradio disabled>A: ${question.ANSWER_1}<p></p>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${question.ANSWER_2 == question.U_ANSWER}">
								<div style="float:left;clear:left;height:15px;width:20px"></div>
								<input type="radio" name=myradio checked disabled>B: ${question.ANSWER_2}<p></p>
							</c:when>
							<c:otherwise>
								<div style="float:left;clear:left;height:15px;width:20px"></div>
								<input type="radio" name=myradio disabled>B: ${question.ANSWER_2}<p></p>
							</c:otherwise>
						</c:choose>
						<c:if test="${not empty question.ANSWER_3}">
							<c:choose>
								<c:when test="${question.ANSWER_3 == question.U_ANSWER}">
									<div style="float:left;clear:left;height:15px;width:20px"></div>
									<input type="radio" name=myradio checked disabled>C: ${question.ANSWER_3}<p></p>
								</c:when>
								<c:otherwise>
									<div style="float:left;clear:left;height:15px;width:20px"></div>
									<input type="radio" name=myradio disabled>C:${question.ANSWER_3}<p></p>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${question.ANSWER_4 == question.U_ANSWER}">
									<div style="float:left;clear:left;height:15px;width:20px"></div>
									<input type="radio" name=myradio checked disabled>D: ${question.ANSWER_4}<p></p>
									</c:when>
								<c:otherwise>
									<div style="float:left;clear:left;height:15px;width:20px"></div>
									<input type="radio" name=myradio disabled>D: ${question.ANSWER_4}<p></p>
								</c:otherwise>
							</c:choose>
						</c:if>
					</form>
					<div style="float:left;clear:left;height:15px;width:20px"></div>
					<p>Correct Answer: ${question.C_ANSWER}</p>
				</div>
			</c:forEach>
		</div>
	</div>
</body>

</html>