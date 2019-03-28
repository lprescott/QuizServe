<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
<meta content="text/html;" charset="UTF-8">
<title>Question Edit Page</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/create_question.css">
</head>

<body>
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img
			class="shadow" style="max-height: 60px;"
			src="${pageContext.request.contextPath}/img/graphic-seal.jpg"
			alt="SUNY Albany Seal"></a>
		<div class="logo-label"></div>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/admin/main.jsp">
			Go back </a>
		<form action="../Logout" method="post">
			<input type="submit" value="Logout?">
		</form>
	</div>
	<div>
		<table border="1" style = center>
			<tr>
				<th>ID</th>
				<th>DESCRIPTION</th>
				<th>CATEGORY</th>
				<th>Answer 1</th>
				<th>Answer 2</th>
				<th>Answer 3</th>
				<th>Answer 4</th>
				<th>Answer 5</th>
				<th>Answer 6</th>
				<th>Correct Answer</th>
			</tr>		
			<c:forEach items="${question_list}" var="que" >
		       <tr>
		       		<td>${que.QUESTION_ID}</td>
					<td>${que.TEXT}
					<c:if test ="${que.IMAGE_NAME != null}">
   						<p>${que.IMAGE_NAME}</p>
   					</c:if>
					</td>
					<td>${que.CATEGORY}</td>
					<td>${que.ANSWER_1}</td>
					<td>${que.ANSWER_2}</td>
					<td>${que.ANSWER_3}</td>
					<td>${que.ANSWER_4}</td>
					<td>${que.ANSWER_5}</td>
					<td>${que.ANSWER_6}</td>
					<td>${que.CORRECT_ANSWER}</td>
					<td><a href="http://localhost:8080/ICSI418-Group-Project/EditQuestion?id=${que.QUESTION_ID}">edit</a></td>
			   </tr>
			</c:forEach>
		</table>
	</div>
	
 
</body>
</html>
