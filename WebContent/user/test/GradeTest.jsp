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
<title>GradeTest</title>
</head>
<body>
	<form action="TakeTest.jsp" method="post">
		<!-- Connect to DB and select all questions -->
		<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
		<sql:query dataSource="${snapshot}" var="result"> SELECT * FROM (QUESTION Q
															INNER JOIN 
															TEST_QUESTION T
															ON Q.QUESTION_ID = T.QUESTION_ID)
															WHERE TEST_ID=<%=request.getParameter("TEST_ID")%>;</sql:query>
		
		<c:forEach var="row" items="${result.rows}">
			<c:out value="${row.TEXT}" />
			<sql:query dataSource="${snapshot}" var="answers">SELECT * 
			                                                  FROM (ANSWER A
			                                                  INNER JOIN
			                                                  QUESTION_ANSWER Q
			                                                  ON A.ANSWER_ID = Q.ANSWER_ID)
			                                                  WHERE QUESTION_ID = ${row.QUESTION_ID};</sql:query>
						                                                  
			<c:forEach var="ans" items="${answers.rows}">
				<%
					
				%>
			</c:forEach>
						
			<BR>
						
		</c:forEach>
	</form>
</body>
</html>