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
<title>View User Test Results</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
<script src="${pageContext.request.contextPath}/js/checkBox.js"></script>
</head>

<body>
	<sql:setDataSource var="snapshot1" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
	<sql:query dataSource="${snapshot1}" var="result1"> SELECT * FROM TEST T INNER JOIN ADMINISTRATOR A ON T.ADMIN_ID = A.ADMIN_ID WHERE TEST_ID = <%=request.getParameter("TEST_ID")%>; </sql:query>
	<sql:query dataSource="${snapshot1}" var="result4"> SELECT * FROM USERS U INNER JOIN TESTS_TAKEN TT ON U.USERS_ID = TT.USERS_ID WHERE TEST_TAKEN_ID = <%=request.getParameter("TEST_TAKEN_ID")%>; </sql:query>
	<sql:query dataSource="${snapshot1}" var="result5"> SELECT * FROM ALLOWED_USERS WHERE USERS_ID = ${id} AND TEST_ID = <%=request.getParameter("TEST_ID")%>; </sql:query>

	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/user/main.jsp"><img class="shadow" style="max-height: 65px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">${result1.rows[0].HEADER_TEXT}</p>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/user/main.jsp"> Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow" style="padding: 0;">
			<h2 style="margin: 10px; padding: 20px;">${result1.rows[0].TITLE}</h2>

			<table style="width: 100%;">
				<tr>
					<th>Administrator</th>
					<th>Assigned Date</th>
					<th>User</th>
					<th>Taken Date</th>
					<th>Score</th>
				</tr>

				<tr>
					<th>${result1.rows[0].EMAIL}</th>
					<th>${result5.rows[0].TEST_ASSIGNED}</th>
					<th>${result4.rows[0].EMAIL}</th>
					<th>${result4.rows[0].TEST_DATE}</th>
					<th>${result4.rows[0].SCORE}</th>
				</tr>
			</table>

		</div>
	</div>

	<sql:query dataSource="${snapshot1}" var="result2"> SELECT * FROM TEST T INNER JOIN TEST_QUESTIONS TQ ON T.TEST_ID = TQ.TEST_ID INNER JOIN QUESTION Q ON TQ.QUESTION_ID = Q.QUESTION_ID WHERE T.TEST_ID = <%=request.getParameter("TEST_ID")%> ORDER BY RAND(${id}); </sql:query>

	<form id="test-form" class="quiz-form" action="${pageContext.request.contextPath}/TakeTest" method="post">

		<!-- Hidden inputs with ID #s -->
		<input id="TEST_ID" type="hidden" name="TEST_ID" value="<%=request.getParameter("TEST_ID")%>"> <input id="USERS_ID" type="hidden" name="USERS_ID" value="<%=request.getParameter("USERS_ID")%>">

		<div class="main-container">
			<div class="main shadow" style="margin-bottom: 82px !important">

				<c:set var="count" value="1" scope="page" />
				<c:forEach var="row" items="${result2.rows}">

					<sql:query dataSource="${snapshot1}" var="result6"> SELECT * FROM RESULTS WHERE TEST_TAKEN_ID = <%=request.getParameter("TEST_TAKEN_ID")%> AND QUESTION_ID = ${row.QUESTION_ID}; </sql:query>

					<c:if test="${not empty row.IMAGE_NAME}">
						<!-- Image -->
						<div class="image-container">
							<img src="${pageContext.request.contextPath}/uploads/${row.IMAGE_NAME}" alt="Q-${row.QUESTION_ID} Image">
						</div>
					</c:if>
						
					<h4>${count}.&nbsp;${row.TEXT}</h4>
					<!-- question is mc -->
					<c:if test="${row.IS_TRUE_FALSE == false}">
						<sql:query dataSource="${snapshot1}" var="result3"> SELECT * FROM QUESTION Q INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.QUESTION_ID = ${row.QUESTION_ID} ORDER BY RAND(${id});</sql:query>
						<c:forEach var="row2" items="${result3.rows}">
							<c:if test="${row2.ANSWER_ID == result6.rows[0].ANSWER_ID}">
								<!-- chosen answer -->
								<input disabled class="${row.QUESTION_ID}" type="checkbox" id="answer_${row2.ANSWER_ID}" name="answer_${row2.ANSWER_ID}" onchange="checkBoxUpdate(this, '${row2.QUESTION_ID}');" checked>
								<label for="${row2.ANSWER_ID}">${row2.ANSWER}</label>
							</c:if>
							<c:if test="${!(row2.ANSWER_ID == result6.rows[0].ANSWER_ID)}">
								<!-- not chosen answer -->
								<input disabled class="${row.QUESTION_ID}" type="checkbox" id="answer_${row2.ANSWER_ID}" name="answer_${row2.ANSWER_ID}" onchange="checkBoxUpdate(this, '${row2.QUESTION_ID}');">
								<label for="${row2.ANSWER_ID}">${row2.ANSWER}</label>
							</c:if>
							<br>
						</c:forEach>
						<br>
						
						<sql:query dataSource="${snapshot1}" var="result7"> SELECT * FROM TEST_QUESTIONS TQ INNER JOIN QUESTION Q ON TQ.QUESTION_ID = Q.QUESTION_ID INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID WHERE TQ.TEST_ID = <%=request.getParameter("TEST_ID")%> AND Q.QUESTION_ID = ${row.QUESTION_ID} AND QA.ANSWER_ID = ${result6.rows[0].ANSWER_ID}; </sql:query>
						<sql:query dataSource="${snapshot1}" var="result8"> SELECT * FROM TEST_QUESTIONS TQ INNER JOIN QUESTION Q ON TQ.QUESTION_ID = Q.QUESTION_ID INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER A ON QA.ANSWER_ID = A.ANSWER_ID WHERE TQ.TEST_ID = <%=request.getParameter("TEST_ID")%> AND Q.QUESTION_ID = ${row.QUESTION_ID} AND QA.IS_CORRECT_ANSWER = 1; </sql:query>
						
						<c:if test="${result7.rows[0].IS_CORRECT_ANSWER == true}">
							<!-- correct -->
							<div><i style="color: green;" class="fas fa-check"></i>&nbsp;Correct </div>
						</c:if>
						
						<c:if test="${!(result7.rows[0].IS_CORRECT_ANSWER == true)}">
							<!-- incorrect -->
							<div><i style="color: red;"class="fas fa-times"></i>&nbsp;Incorrect, the correct answer is: '${result8.rows[0].ANSWER}'.</div> 
						</c:if>
					</c:if>
					<!-- question is t/f -->
					<c:if test="${row.IS_TRUE_FALSE == true}">

						<c:if test="${result6.rows[0].TF_CHOSEN == true}">
							<!--  user chose true -->
							<input checked disabled class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_true" name="${row.QUESTION_ID}_true" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}');">
							<label for="${row.QUESTION_ID}_true">True</label>
							<br>
							<input disabled class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_false" name="${row.QUESTION_ID}_false" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}');">
							<label for="${row.QUESTION_ID}_false">False</label>
						</c:if>

						<c:if test="${result6.rows[0].TF_CHOSEN == false}">
							<!--  user chose false -->
							<input disabled class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_true" name="${row.QUESTION_ID}_true" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}');">
							<label for="${row.QUESTION_ID}_true">True</label>
							<br>
							<input checked disabled class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_false" name="${row.QUESTION_ID}_false" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}');">
							<label for="${row.QUESTION_ID}_false">False</label>
						</c:if>
						<br>
						<br>
						<sql:query dataSource="${snapshot1}" var="result9"> SELECT * FROM QUESTION Q WHERE QUESTION_ID = ${row.QUESTION_ID};</sql:query>
						
						<c:if test="${(result6.rows[0].TF_CHOSEN == true) and (result9.rows[0].TF_IS_TRUE == true)}">
							<!-- correct -->
							<div><i style="color: green;" class="fas fa-check"></i>&nbsp;Correct </div>
						</c:if>
						
						<c:if test="${(result6.rows[0].TF_CHOSEN == false) and (result9.rows[0].TF_IS_TRUE == false)}">
							<!-- correct -->
							<div><i style="color: green;" class="fas fa-check"></i>&nbsp;Correct </div>
						</c:if>
						
						<c:if test="${(result6.rows[0].TF_CHOSEN == true) and (result9.rows[0].TF_IS_TRUE == false)}">
							<!-- incorrect -->
							<div><i style="color: red;"class="fas fa-times"></i>&nbsp;Incorrect, the correct answer is false. </div> 
						</c:if>
						
						<c:if test="${(result6.rows[0].TF_CHOSEN == false) and (result9.rows[0].TF_IS_TRUE == true)}">
							<!-- incorrect -->
							<div><i style="color: red;"class="fas fa-times"></i>&nbsp;Incorrect, the correct answer is true. </div> 
						</c:if>
					</c:if>		

					<div class="clearfix">&nbsp;</div>

					<br>
					<hr style="margin-left: -20px; margin-right: -20px;">
					<div style="padding-bottom: 20px;"></div>
						
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
			</div>
		</div>
	</form>

	<!-- Footer -->
	<div class="footer shadow">
		<p>${result1.rows[0].FOOTER_TEXT}</p>
	</div>
</body>

</html>