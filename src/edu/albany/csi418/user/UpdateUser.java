package edu.albany.csi418.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.albany.csi418.session.LoginEnum;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int USERS_ID = Integer.parseInt(request.getParameter("USERS_ID"));  

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password_confirmation = request.getParameter("password-confirm");

        //Check if passwords match	
        if (!password.equals(password_confirmation)) {
            response.sendRedirect("user/update_account.jsp?success=false&error=Passwords%20Do%20Not%20Match");
            return;
        }
        
        try {
        	
        	//Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

            // Execute SQL query
            Statement UPDATE_USER_SQL_Statement = DB_Connection.createStatement();
            String UPDATE_USER_SQL_Query = "UPDATE USERS SET EMAIL = '" + email + "', PASSWORD= '" + password + "' WHERE USERS_ID = " + USERS_ID + ";";
            UPDATE_USER_SQL_Statement.executeUpdate(UPDATE_USER_SQL_Query);
            
            // Clean-up environment
            UPDATE_USER_SQL_Statement.close();
            DB_Connection.close();

            response.sendRedirect("user/update_account.jsp?success=true");
            return;
            
        } catch (Exception e) {
            
        	//System.out.println(e);
            response.sendRedirect("user/update_account.jsp?success=false&error=Unknown%20Error");
            
            return;
        }
	}

}
