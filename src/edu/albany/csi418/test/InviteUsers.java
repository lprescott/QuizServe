package edu.albany.csi418.test;

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

import edu.albany.csi418.session.LoginEnum;

/**
 * Servlet implementation class InviteUsers
 */
@WebServlet("/InviteUsers")
public class InviteUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InviteUsers() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int testID = Integer.parseInt(request.getParameter("TEST_ID"));
		
		try {
			// Load the Connector/J
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Open a connection
			Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(),
					LoginEnum.username.getValue(), LoginEnum.password.getValue());
			
			//Delete entries in allowed users table
            Statement ALLOWED_USERS_Statement = DB_Connection.createStatement();
            String ALLOWED_USERS_SQL_Query = "DELETE FROM ALLOWED_USERS WHERE TEST_ID = "+testID+";";
            ALLOWED_USERS_Statement.executeUpdate(ALLOWED_USERS_SQL_Query);
            
            //Search users
            Statement USER_Statement = DB_Connection.createStatement();
            String USER_SQL_Query = "SELECT * FROM USERS;";
            ResultSet USER_RS = USER_Statement.executeQuery(USER_SQL_Query);
            
            while (USER_RS.next()) {
            	if(request.getParameter(USER_RS.getString("USERS_ID")) != null) {
            		
            		//Add into allowed_users table
                    Statement ALLOWED_USERS_Statement2 = DB_Connection.createStatement();
                    String ALLOWED_USERS_SQL_Query2 = "INSERT INTO ALLOWED_USERS (USERS_ID, TEST_ID, TEST_ASSIGNED) VALUES ("+USER_RS.getString("USERS_ID")+", "+testID+", CURDATE());";
                    ALLOWED_USERS_Statement2.executeUpdate(ALLOWED_USERS_SQL_Query2);
            	}
            		
            }
            
            //success
			response.sendRedirect(
					"admin/test/invite_users.jsp?TEST_ID=" + testID + "&success=true");
			return;
            
			
		} catch (Exception e) {

			//System.out.println(e);
			response.sendRedirect(
					"admin/test/invite_users.jsp?TEST_ID=" + testID + "&success=false&error=Unknown%20Error");
			return;
		}

	}

}
