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
 * Servlet implementation class EditTest
 */
@WebServlet("/EditTest")
public class EditTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTest() {
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
	
	            // Execute SQL queries
	            Statement DELETE_TEST_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_TEST_SQL_Query = "DELETE FROM TEST WHERE TEST_ID="+request.getParameter("TEST_ID")+";";
	            DELETE_TEST_SQL_Statement.executeUpdate(DELETE_TEST_SQL_Query);
	            
	            Statement DELETE_TEST_QUESTIONS_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_TEST_QUESTIONS_SQL_Query = "DELETE TQ FROM TEST_QUESTIONS AS TQ WHERE TEST_ID ="+request.getParameter("TEST_ID")+";";
	            DELETE_TEST_QUESTIONS_SQL_Statement.executeUpdate(DELETE_TEST_QUESTIONS_SQL_Query);

	            // Clean-up environment
	            DELETE_TEST_SQL_Statement.close();
	            DELETE_TEST_QUESTIONS_SQL_Statement.close();
	            DB_Connnection.close();
	            
	            response.sendRedirect("admin/test/test_management.jsp?message=Test%20Deleted%20Successfully");
	            return;
			
			} catch (Exception e) {
	            
	        	//System.out.println(e);
				response.sendRedirect("admin/test/edit_test.jsp?success=false&error=Error%20Deleting%20Test");
	            return;
	        }
			
		} else if(action.equals("UPDATE")){
			
	        String testTitle = request.getParameter("test_title");
	        String testHeader = request.getParameter("test_header");
	        String testFooter = request.getParameter("test_footer");
	        String testDue = request.getParameter("test_due");

	        
			try {
	            //Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");
	
	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	
	            if(testDue != null) {
	            	Statement UPDATE_TEST_SQL_Statement = DB_Connnection.createStatement();
		            String UPDATE_TEST_SQL_Query = "UPDATE TEST SET TITLE = '" + testTitle + "', HEADER_TEXT = '" + testHeader + "', FOOTER_TEXT = '" + testFooter + "', TEST_DUE = '" + testDue + "' WHERE TEST_ID = " + request.getParameter("TEST_ID") + ";";
		            UPDATE_TEST_SQL_Statement.executeUpdate(UPDATE_TEST_SQL_Query);
		            UPDATE_TEST_SQL_Statement.close();

	            } else {
	            	// Execute SQL queries
		            Statement UPDATE_TEST_SQL_Statement = DB_Connnection.createStatement();
		            String UPDATE_TEST_SQL_Query = "UPDATE TEST SET TITLE = '" + testTitle + "', HEADER_TEXT = '" + testHeader + "', FOOTER_TEXT = '" + testFooter + "' WHERE TEST_ID = " + request.getParameter("TEST_ID") + ";";
		            UPDATE_TEST_SQL_Statement.executeUpdate(UPDATE_TEST_SQL_Query);
		            UPDATE_TEST_SQL_Statement.close();
	            }
	           
	            Statement DELETE_TEST_QUESTIONS_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_TEST_QUESTIONS_SQL_Query = "DELETE TQ FROM TEST_QUESTIONS AS TQ WHERE TEST_ID ="+request.getParameter("TEST_ID")+";";
	            DELETE_TEST_QUESTIONS_SQL_Statement.executeUpdate(DELETE_TEST_QUESTIONS_SQL_Query);

	            //Add questions to TEST_QUESTIONS Table
	            Statement QUESTION_SQL_Statement = DB_Connnection.createStatement();
	            String QUESTION_SQL_Query = "SELECT * FROM QUESTION";
	            ResultSet QUESTION_RS = QUESTION_SQL_Statement.executeQuery(QUESTION_SQL_Query);

	            Statement ADD_TEST_QUESTION_Statement = null;
	            //Loop all questions
	            while (QUESTION_RS.next()) {
	            	if(request.getParameter(QUESTION_RS.getString("QUESTION_ID")) != null) {
	            		
	            		ADD_TEST_QUESTION_Statement = DB_Connnection.createStatement();
	                    String ADD_TEST_QUESTION_STRING = "INSERT INTO TEST_QUESTIONS (TEST_ID, QUESTION_ID) VALUES (" + request.getParameter("TEST_ID") + ", " + QUESTION_RS.getString("QUESTION_ID") + ")";
	                    ADD_TEST_QUESTION_Statement.executeUpdate(ADD_TEST_QUESTION_STRING);
	            		
	            	}
	            	
	            }
	            
	            // Clean-up environment
	            DELETE_TEST_QUESTIONS_SQL_Statement.close();
	            QUESTION_SQL_Statement.close();
	            ADD_TEST_QUESTION_Statement.close();
	            
	            QUESTION_RS.close();
	            
	            DB_Connnection.close();
	            
	            response.sendRedirect("admin/test/test_management.jsp?message=Test%20Updated%20Successfully");
	            return;
			
			} catch (Exception e) {
	            
	        	//System.out.println(e);
				response.sendRedirect("admin/test/edit_test.jsp?success=false&error=Error%20Updating%20Test");
	            return;
	        }
			
		} else {
			  response.sendRedirect("admin/test/edit_test.jsp?success=false&error=Unknown%20Error");
			  return;
		}
	}

}
