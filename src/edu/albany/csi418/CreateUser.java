package edu.albany.csi418;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUser
 * 
 * This Servlet Will connect to our DataBase QUIZ and create a new User specified by an admin on the create_user.jsp page.
 * It will then email the user their information. 
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String host;
    	private String port;
   	private String user;
    	private String pass;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
        super();
    }
	public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password_confirmation = request.getParameter("password-confirm");
	String recipient = request.getParameter("email");
	//email subject and description
        String subject = "New Account";
        String content = ("A new account was created for you on a test-taking website."
        		+ " Your Username is "+ email + "and your password is " + password);
         String resultMessage = "";
        //Check if passwords match
        if(!password.equals(password_confirmation)) {
        	response.sendRedirect("admin/create_user.jsp?success=false&error=Passwords%20Do%20Not%20Match");
            return;
        }
        
        try {
        	 //Load the Connector/J
             Class.forName("com.mysql.cj.jdbc.Driver");

             // Open a connection
             Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
             
             // Execute SQL queries
             Statement USER_SQL_Statement = DB_Connnection.createStatement();
             String USER_SQL_Query = "SELECT EMAIL FROM USERS";
             
             Statement ADMIN_SQL_Statement = DB_Connnection.createStatement();
             String ADMIN_SQL_Query = "SELECT EMAIL FROM ADMINISTRATOR";
             
             ResultSet USER_Results = USER_SQL_Statement.executeQuery(USER_SQL_Query);
             ResultSet ADMIN_Results = ADMIN_SQL_Statement.executeQuery(ADMIN_SQL_Query);
             
             //Check if user exists with given email
             while (USER_Results.next()) {
            	 if (email.equals(USER_Results.getString("EMAIL"))) {
            		 
                     // Clean-up environment
                     USER_Results.close();
                     ADMIN_Results.close();

                     USER_SQL_Statement.close();
                     ADMIN_SQL_Statement.close();

                     DB_Connnection.close();
                     
                     response.sendRedirect("admin/create_user.jsp?success=false&error=Existing%20User%20Email");
                     return;
            	 }
             }
             
             //Check if admin exists with given email
             while (ADMIN_Results.next()) {
            	 if (email.equals(ADMIN_Results.getString("EMAIL"))) {
                     
                     // Clean-up environment
                     USER_Results.close();
                     ADMIN_Results.close();

                     USER_SQL_Statement.close();
                     ADMIN_SQL_Statement.close();

                     DB_Connnection.close();
                     
                     response.sendRedirect("admin/create_user.jsp?success=false&error=Existing%20Admin%20Email");
                     return;
            	 }
             }
             
             //Insert into DB
             Statement ADD_USER_Statement = DB_Connnection.createStatement();
             String ADD_USER_STRING = "INSERT INTO USERS (EMAIL, PASSWORD, IS_ACTIVE) VALUES ('" + email + "', '" + password + "', 1)";
             ADD_USER_Statement.executeUpdate(ADD_USER_STRING);
             
             //Email User Information
             EmailUtility.sendEmail(host, port, user, pass, recipient, subject,
	                    content);
	            resultMessage = "The e-mail was sent successfully";
             // Clean-up environment
             USER_Results.close();
             ADMIN_Results.close();

             USER_SQL_Statement.close();
             ADMIN_SQL_Statement.close();
             ADD_USER_Statement.close();

             DB_Connnection.close();
        
             response.sendRedirect("admin/create_user.jsp?success=true");
        }
        catch(Exception e) {
        	System.out.println(e);
		resultMessage = "There was an error: " + e.getMessage();
        } 
	}
}
