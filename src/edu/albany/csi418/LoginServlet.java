package edu.albany.csi418;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;  

import java.sql.*;

import edu.albany.csi418.LoginEnum;

/**
 * Servlet implementation class LoginServlet
 * 
 * This servlet connects to our QUIZ MySQL database, checking if the posted login information corresponds to known user/admin
 * logins and/or returns/redirects the the relevant information/page.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		//Create PrintWriter for testing purposes
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("<h1>Email: " + email + "</h1>");
		writer.println("<h1>Password: " + password + "</h1>");
		
		// Flags set to true if user or admin is found
		boolean isValidUser = false;
		boolean isValidAdmin = false;
		
		// Flag set to true if user is marked inactive
		boolean isInactiveUser = false;

		
		try {
			
			 //Load the Connector/J
			 Class.forName("com.mysql.cj.jdbc.Driver"); 

	         // Open a connection
	         Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	         // Execute SQL queries
	         Statement USER_SQL_Statement = DB_Connnection.createStatement();
	         String USER_SQL_Query = "SELECT EMAIL, PASSWORD, IS_ACTIVE FROM USERS";
	         
	         Statement ADMIN_SQL_Statement = DB_Connnection.createStatement();
	         String ADMIN_SQL_Query = "SELECT EMAIL, PASSWORD FROM ADMINISTRATOR";
	         
	         ResultSet USER_Results = USER_SQL_Statement.executeQuery(USER_SQL_Query);
	         ResultSet ADMIN_Results = ADMIN_SQL_Statement.executeQuery(ADMIN_SQL_Query);
	         
	         
	         // Extract data from result set: USER_Results
	         while(USER_Results.next()){
	        	 if(email.equals(USER_Results.getString("EMAIL")) && password.equals(USER_Results.getString("PASSWORD"))) {
	        		 if(USER_Results.getBoolean("IS_ACTIVE") == true) {
	        			 isValidUser = true;
	        		 } else {
	        			 isInactiveUser = true;
	        		 }
	        		 
	        		 break;
	        	 } 
	         }
	         
	         if(isValidUser == false && isInactiveUser == false) {
		         // Extract data from result set: ADMIN_Results
		         while(ADMIN_Results.next()){
		        	 if(email.equals(ADMIN_Results.getString("EMAIL")) && password.equals(ADMIN_Results.getString("PASSWORD"))) {
		        		 isValidAdmin = true;
		        	 }
		        	 
		        	 break;
		         } 
	         }
	         
	         // Control structure to determine type of login
	         if(isValidUser == true) {
		     	writer.println("<br><h1> User Login </h1>");

	         } else if(isInactiveUser == true) {
		     	writer.println("<br><h1> Inactive Login </h1>");

	         } else if(isValidAdmin == true) {
		     	writer.println("<br><h1> Admin Login </h1>");

	         } else {
	     		writer.println("<br><h1> Invalid Login </h1>");
	     		
	         }

	         // Clean-up environment
	         USER_Results.close();
	         ADMIN_Results.close();
	         
	         USER_SQL_Statement.close();
	         ADMIN_SQL_Statement.close();
	         
	         DB_Connnection.close();
	         
	         writer.close();
	         
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
}
