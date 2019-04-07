package edu.albany.csi418.admin;

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
 * Servlet implementation class EditAdmin
 */
@WebServlet("/EditAdmin")
public class EditAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAdmin() {
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
	            Statement DELETE_ADMIN_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_ADMIN_SQL_Query = "DELETE FROM ADMINISTRATOR WHERE ADMIN_ID="+request.getParameter("ADMIN_ID")+";";
	            DELETE_ADMIN_SQL_Statement.executeUpdate(DELETE_ADMIN_SQL_Query);
	            
	            // Clean-up environment
	            DELETE_ADMIN_SQL_Statement.close();
	            DB_Connnection.close();

	            response.sendRedirect("admin/admin_management.jsp?message=Admin%20Deleted%20Successfully");
	            return;
			
			} catch (Exception e) {
	            
	        	//System.out.println(e);
	            response.sendRedirect("admin/admin_management.jsp?success=false&error=Unknown%20Error");
	            return;
	        }
	            
		} else if (action.equals("UPDATE")) {
			
			int ADMIN_ID = Integer.parseInt(request.getParameter("ADMIN_ID"));  

			String new_email = request.getParameter("email");
	        String new_password = request.getParameter("password");
            
            try {
            	
            	//Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	            // Execute SQL query
	            Statement UPDATE_ADMIN_SQL_Statement = DB_Connnection.createStatement();
	            String UPDATE_ADMIN_SQL_Query = "UPDATE ADMINISTRATOR SET EMAIL = '" + new_email + "', PASSWORD= '" + new_password + "' WHERE ADMIN_ID = " + ADMIN_ID + ";";
	            UPDATE_ADMIN_SQL_Statement.executeUpdate(UPDATE_ADMIN_SQL_Query);
	            
	            // Clean-up environment
	            UPDATE_ADMIN_SQL_Statement.close();
	            DB_Connnection.close();

	            response.sendRedirect("admin/admin_management.jsp?message=Admin%20Updated%20Successfully");
	            return;
	            
            } catch (Exception e) {
	            
	        	//System.out.println(e);
	            response.sendRedirect("admin/admin_management.jsp?success=false&error=Unknown%20Error");
	            return;
	        }
			
		} else {
			
            response.sendRedirect("admin/admin_management.jsp?success=false&error=Unknown%20Error");
            return;
		}
	}
}
