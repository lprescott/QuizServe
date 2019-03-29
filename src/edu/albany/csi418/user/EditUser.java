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
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		String action = request.getParameter("submit");
		
		if (action.equals("DELETE")) {

			try {
	      
	            //Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	            // Execute SQL query
	            Statement DELETE_USER_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_USER_SQL_Query = "DELETE FROM USERS WHERE USERS_ID="+request.getParameter("USERS_ID")+";";
	            DELETE_USER_SQL_Statement.executeUpdate(DELETE_USER_SQL_Query);
	            
	            // Clean-up environment
	            DELETE_USER_SQL_Statement.close();
	            DB_Connnection.close();

	            response.sendRedirect("admin/user/user_management.jsp?message=User%20Deleted%20Successfully");
	            return;
			
			} catch (Exception e) {
	            
	        	//System.out.println(e);
	            response.sendRedirect("admin/user/edit_user.jsp?success=false&error=Unknown%20Error");
	            return;
	        }
	            
		} else if (action.equals("UPDATE")) {
			
			int USERS_ID = Integer.parseInt(request.getParameter("USERS_ID"));  

			String new_email = request.getParameter("email");
	        String new_password = request.getParameter("password");
	        
            //Check if active or inactive check box is selected
            int new_is_active;
            if(request.getParameter("active_cb") == null) {
            	//invalid user
            	new_is_active = 0;
            	
            } else if(request.getParameter("inactive_cb") == null){
            	//valid user
            	new_is_active = 1;
            } else {

                response.sendRedirect("admin/user/edit_user.jsp?success=false&error=Invalid%20Input");
                return;
            }
            
            try {
            	
            	//Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	            // Execute SQL query
	            Statement UPDATE_USER_SQL_Statement = DB_Connnection.createStatement();
	            String UPDATE_USER_SQL_Query = "UPDATE USERS SET EMAIL = '" + new_email + "', PASSWORD= '" + new_password + "', IS_ACTIVE = " + new_is_active + " WHERE USERS_ID = " + USERS_ID + ";";
	            UPDATE_USER_SQL_Statement.executeUpdate(UPDATE_USER_SQL_Query);
	            
	            // Clean-up environment
	            UPDATE_USER_SQL_Statement.close();
	            DB_Connnection.close();

	            response.sendRedirect("admin/user/user_management.jsp?message=User%20Updated%20Successfully");
	            return;
	            
            } catch (Exception e) {
	            
	        	//System.out.println(e);
	            response.sendRedirect("admin/user/edit_user.jsp?success=false&error=Unknown%20Error");
	            return;
	        }
			
		} else {
			
            response.sendRedirect("admin/user/edit_user.jsp?success=false&error=Unknown%20Error");
            return;
		}
	}
}
