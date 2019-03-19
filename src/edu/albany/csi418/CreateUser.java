package edu.albany.csi418;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");

             // Open a connection
             Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
             
             //Creates the query to be sent to the database
             Statement USER_SQL_Statement = DB_Connnection.createStatement();
             String USER_SQL_Query = "INSERT INTO USERS (EMAIL, PASSWORD, IS_ACTIVE)\n" + 
             		"VALUES (" + email + ", " + password + ", 1);";
             
             //executes the query
             ResultSet USER_Results = USER_SQL_Statement.executeQuery(USER_SQL_Query);
        }
        catch(Exception e) {
        	System.out.println(e);
        }
	}

}
