package edu.albany.csi418.question;

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
 * Servlet implementation class CreateQuestionTF
 */
@WebServlet("/EditQuestionTF")
public class EditQuestionTF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditQuestionTF() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Get text, category and correct answer num
		String question = request.getParameter("q_text");
        String category = request.getParameter("q_category");
        
        //Check if true or false check box is selected
        int tf;
        if(request.getParameter("true_cb") == null) {
        	//false
        	tf = 0;
        } else {
        	//true
        	tf = 1;
        }
        
        
		String action = request.getParameter("submit");
		
		if (action.equals("DELETE")) {
			
			try {
			      
	            //Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	            // Execute SQL query
	            Statement DELETE_QUESTION_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_QUESTION_SQL_Query = "DELETE FROM QUESTION WHERE QUESTION_ID="+request.getParameter("QUESTION_ID")+";";
	            DELETE_QUESTION_SQL_Statement.executeUpdate(DELETE_QUESTION_SQL_Query);
	            
	            // Clean-up environment
	            DELETE_QUESTION_SQL_Statement.close();
	            DB_Connnection.close();
	            
	            response.sendRedirect("admin/question/question_management.jsp?message_tf=Question%20Deleted%20Successfully");
	            return;
			
			} catch (Exception e) {
	            
	        	//System.out.println(e);
				response.sendRedirect("admin/question/edit_question_tf.jsp?success=false&error=Error%20Deleting%20Record");
	            return;
	        }
			
		} else if (action.equals("UPDATE")) {
			
			int QUESTION_ID = Integer.parseInt(request.getParameter("QUESTION_ID"));  
			
			try {
	            //Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	            //Update into QUESTION Table
	            Statement UPDATE_QUESTION_Statement = DB_Connnection.createStatement();
	            String UPDATE_QUESTION_STRING =  "UPDATE QUESTION SET TEXT = '" + question + "', CATEGORY = '" + category + "', TF_IS_TRUE = " + tf + " WHERE QUESTION_ID = " + QUESTION_ID + ";";
	            UPDATE_QUESTION_Statement.executeUpdate(UPDATE_QUESTION_STRING);

	            // Clean-up environment
	            UPDATE_QUESTION_Statement.close();
	            
	            DB_Connnection.close();
	            
	            //success
	            response.sendRedirect("admin/question/question_management.jsp?message_tf=Question%20Updated%20Successfully");
	            return;
	        
	        } catch (Exception e) {
	            
	        	//e.printStackTrace();
				response.sendRedirect("admin/question/edit_question_tf.jsp?success=false&error=Error%20Updating%20Record");
	            return;
	        }
			
		} else {
			  response.sendRedirect("admin/question/edit_question_tf.jsp?success=false&error=Unknown%20Error");
			  return;
		}
      
        
	}

}
