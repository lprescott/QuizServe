<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
 
 
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Hello, World!

	<sql:setDataSource var = "snapshot" driver = "com.mysql.cj.jdbc.Driver" url = "jdbc:mysql://localhost/Henry" user = "eclipse"  password = "csi2019"/>
         
	<sql:query dataSource="${snapshot}" var="result"> SELECT * from AUTHOR; </sql:query>
	
	 <table border = "1">
         <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
         </tr>
         
         <c:forEach var = "row" items = "${result.rows}">
            <tr>
               <td><c:out value = "${row.AUTHOR_NUM}"/></td>
               <td><c:out value = "${row.AUTHOR_FIRST}"/></td>
               <td><c:out value = "${row.AUTHOR_LAST}"/></td>
            </tr>
         </c:forEach>
      </table>
         
</body>
</html>