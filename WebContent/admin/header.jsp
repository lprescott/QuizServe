<sql:setDataSource var="snapshot1" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
	<sql:query dataSource="${snapshot1}" var="currAdmin"> SELECT * FROM ADMINISTRATOR WHERE ADMIN_ID = <%=request.getSession().getAttribute("id")%>; </sql:query>
<div class="header shadow">
		<c:if test="${not empty currAdmin.rows[0].FILENAME}">
			<a class="logo-uploaded" href="${pageContext.request.contextPath}/admin/main.jsp"> <img class="shadow" style="max-height: 70px;" src="${pageContext.request.contextPath}/headerimg/${currAdmin.rows[0].FILENAME}" alt="T-${currAdmin.rows[0].FILENAME}">
			</a>
		</c:if>

		<c:if test="${empty currAdmin.rows[0].FILENAME}">
			<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"> <img class="shadow" style="max-height: 65px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal">
			</a>
		</c:if>
		<c:if test="${not empty currAdmin.rows[0].HEADER}"> 
		<p style="float: left;">${currAdmin.rows[0].HEADER}</p>
		</c:if>
		<c:if test="${empty currAdmin.rows[0].HEADER}"> 
		<p style="float: left;">University at Albany, SUNY</p>
		</c:if>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/admin/main.jsp"> Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>
	<div class="footer shadow">
		<c:if test="${not empty currAdmin.rows[0].FOOTER}"> 
		<p style="float: left;">${currAdmin.rows[0].FOOTER}</p>
		</c:if>
		<c:if test="${empty currAdmin.rows[0].FOOTER}"> 
		<p style="float: left;">A quiz application by <a class="link-style" href="${pageContext.request.contextPath}/about_us.jsp">our team</a> for an ICSI 418Y/410 final project, Spring 2019.</p>
		</c:if>
	</div>