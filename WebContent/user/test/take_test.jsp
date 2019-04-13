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
<title>Test A Test</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<script src="${pageContext.request.contextPath}/js/checkBox.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.4.0.min.js"></script>
<link rel="stylesheet" type="text/css"  href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
</head>

<!-- This function scrolls to a y value supplied by a url attribute. -->
<script>
	window.onload = function() {
		setScroll();
	}

	function setScroll() {
		window.scrollTo({
			top : <%=request.getParameter("SCROLL")%>,
			left : 0,
			behavior : 'auto'
		});
	}
</script>

<body onload="setScroll()">
	<sql:setDataSource var="snapshot1" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
	<sql:query dataSource="${snapshot1}" var="result1"> SELECT * FROM TEST T INNER JOIN ADMINISTRATOR A ON T.ADMIN_ID = A.ADMIN_ID WHERE TEST_ID = <%=request.getParameter("TEST_ID")%>; </sql:query>

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
		<div class="main shadow">
			<h2 style="margin: 10px;">${result1.rows[0].TITLE}</h2>
			<h3 style="margin: 10px;">Administered by ${result1.rows[0].EMAIL}</h3>
			<h3 style="margin: 10px;">Due by ${result1.rows[0].TEST_DUE}</h3>
			
		</div>
	</div>

	<sql:query dataSource="${snapshot1}" var="result2"> SELECT * FROM TEST T INNER JOIN TEST_QUESTIONS TQ ON T.TEST_ID = TQ.TEST_ID INNER JOIN QUESTION Q ON TQ.QUESTION_ID = Q.QUESTION_ID WHERE T.TEST_ID = <%=request.getParameter("TEST_ID")%> ORDER BY RAND(${id}); </sql:query>

	<form id="test-form" class="quiz-form" action="${pageContext.request.contextPath}/TakeTest" method="post">

		<!-- Hidden inputs with ID #s and scroll height-->
		<input id="TEST_ID" type="hidden" name="TEST_ID" value="<%=request.getParameter("TEST_ID")%>"> <input id="USERS_ID" type="hidden" name="USERS_ID" value="<%=request.getParameter("USERS_ID")%>"> <input id="scrollHeight" type="hidden" name="scrollHeight">

		<!-- This script assigns the scroll height to the hidden scrollHeight input. -->
		<script>
			document.getElementById('scrollHeight').value = 0;
			window.addEventListener("scroll", function(event) {
				document.getElementById('scrollHeight').value = Math
						.round(this.scrollY);
			});

			function subForm(questionID) {
				$.ajax({
					url : '/ICSI418-Group-Project/TakeTest',
					type : 'post',
					data : $('#test-form').serialize(),
					success : function() {
						setTimeout( function() {
							$("#question-label-" + questionID).css("visibility", "visible");
							$("#question-label-" + questionID).css("opacity", "1");
						}, 100);
						setTimeout( function() {
							$("#question-label-" + questionID).css("visibility", "hidden");
							$("#question-label-" + questionID).css("opacity", "0");
						}, 5000);
					}
				});
			}
		</script>

		<div class="main-container">
			<div class="main shadow" style="margin-bottom: 82px !important">

				<c:set var="count" value="1" scope="page" />
				<c:forEach var="row" items="${result2.rows}">

					<sql:query dataSource="${snapshot1}" var="result4"> SELECT * FROM TEST_IN_PROGRESS WHERE TEST_ID = ${row.TEST_ID} AND USERS_ID = <%=request.getParameter("USERS_ID")%> AND QUESTION_ID = ${row.QUESTION_ID}; </sql:query>

					<h4>${count}.&nbsp;${row.TEXT}</h4>
					<!-- question is mc -->
					<c:if test="${row.IS_TRUE_FALSE == false}">
						<sql:query dataSource="${snapshot1}" var="result3"> SELECT * FROM QUESTION Q INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.QUESTION_ID = ${row.QUESTION_ID} ORDER BY RAND(${id});</sql:query>
						<c:forEach var="row2" items="${result3.rows}">

							<c:if test="${row2.ANSWER_ID == result4.rows[0].ANSWER_ID}">
								<input checked class="${row.QUESTION_ID}" type="checkbox" id="answer_${row2.ANSWER_ID}" name="answer_${row2.ANSWER_ID}" onchange="checkBoxUpdate(this, '${row2.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							</c:if>
							<c:if test="${row2.ANSWER_ID != result4.rows[0].ANSWER_ID}">
								<input class="${row.QUESTION_ID}" type="checkbox" id="answer_${row2.ANSWER_ID}" name="answer_${row2.ANSWER_ID}" onchange="checkBoxUpdate(this, '${row2.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							</c:if>

							<label for="${row2.ANSWER_ID}">${row2.ANSWER}</label>
							<br>
						</c:forEach>

					</c:if>
					<!-- question is t/f -->
					<c:if test="${row.IS_TRUE_FALSE == true}">
						<c:if test="${result4.rows[0].TF_CHOSEN == true}">
							<input checked class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_true" name="${row.QUESTION_ID}_true" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							<label for="${row.QUESTION_ID}_true">True</label>
							<br>
							<input class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_false" name="${row.QUESTION_ID}_false" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							<label for="${row.QUESTION_ID}_false">False</label>
						</c:if>
						<c:if test="${result4.rows[0].TF_CHOSEN == false}">
							<input class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_true" name="${row.QUESTION_ID}_true" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							<label for="${row.QUESTION_ID}_true">True</label>
							<br>
							<input checked class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_false" name="${row.QUESTION_ID}_false" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							<label for="${row.QUESTION_ID}_false">False</label>
						</c:if>
						<c:if test="${empty result4.rows[0].TF_CHOSEN}">
							<input class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_true" name="${row.QUESTION_ID}_true" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							<label for="${row.QUESTION_ID}_true">True</label>
							<br>
							<input class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_false" name="${row.QUESTION_ID}_false" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}'); subForm(${row.QUESTION_ID});">
							<label for="${row.QUESTION_ID}_false">False</label>
						</c:if>
						<br>

					</c:if>

					<div id="question-label-${row.QUESTION_ID}" style="transition: visibility 0.5s, opacity 0.5s linear;  opacity: 0; visibility: hidden;">
						<!-- saved -->
						<div style="float: right;">
							<i style="color: green;" class="fas fa-save"></i>&nbsp;Answer Saved
						</div>
					</div>

					<br>
					<hr style="margin-left: -20px; margin-right: -20px;">

					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
				<div style="padding-top: 30px">
					<input onClick="return validateCheckboxes();" class="shadow-button submit-button" name="submitTest" id="submitTest" type="submit" value="SUBMIT">
				</div>
			</div>
		</div>
	</form>

	<!-- Footer -->
	<div class="footer shadow">
		<p>${result1.rows[0].FOOTER_TEXT}</p>
	</div>
</body>

</html>