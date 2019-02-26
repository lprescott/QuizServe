<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>


<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- Forward, Hello World! -->
	Hello World!
	<br /> 
	
	<h1> Using JSP, think "Java code in HTML"</h1>
	<!-- Part 1, Printing some data from the database -->
	<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver"
		url="jdbc:mysql://localhost/Henry" user="eclipse" password="csi2019" />
	<sql:query dataSource="${snapshot}" var="result"> SELECT * from AUTHOR; </sql:query>

	<table border="1">
		<tr>
			<th>ID</th>
			<th>First Name</th>
			<th>Last Name</th>
		</tr>

		<c:forEach var="row" items="${result.rows}">
			<tr>
				<td><c:out value="${row.AUTHOR_NUM}" /></td>
				<td><c:out value="${row.AUTHOR_FIRST}" /></td>
				<td><c:out value="${row.AUTHOR_LAST}" /></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	
	<h1> Using Servlets, think "HTML in Java code"</h1>
	<h2> POST example</h2>
	<!-- Part 2, Calling a simple java file (servlet) - POST -->
	<form action="helloServletPOST" method="post">
	    Enter your name: <input type="text" name="yourName" size="20">
	    <input type="submit" value="Call Servlet" />
	</form>
	<br />
	
	<h2> GET example</h2>
	<!-- Part 3, Going to a a simple java file (servlet) - GET -->
	<a href="/ICSI418-Group-Project/helloServletGET">What time is it?</a>
	
</body>
</html>