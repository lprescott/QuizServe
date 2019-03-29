package edu.albany.csi418.question;

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
 * Servlet implementation class EditQuestionMC
 */
@WebServlet("/EditQuestionMC")
public class EditQuestionMC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuestionMC() {
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
	            Statement DELETE_QUESTION_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_QUESTION_SQL_Query = "DELETE Q FROM QUESTION AS Q INNER JOIN QUESTION_ANSWER AS QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER AS A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.QUESTION_ID=" + request.getParameter("QUESTION_ID")+";";
	            DELETE_QUESTION_SQL_Statement.executeUpdate(DELETE_QUESTION_SQL_Query);

	            // Clean-up environment
	            DELETE_QUESTION_SQL_Statement.close();
	            DB_Connnection.close();
	            
	            response.sendRedirect("admin/question/question_management.jsp?message_mc=Question%20Deleted%20Successfully");
	            return;
			
			} catch (Exception e) {
	            
	        	//System.out.println(e);
				response.sendRedirect("admin/question/edit_question_mc.jsp?success=false&error=Error%20Deleting%20Record");
	            return;
	        }
			
		} else if(action.equals("UPDATE")){
			
			int QUESTION_ID = Integer.parseInt(request.getParameter("QUESTION_ID"));  

			
			try {
				
				//Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");
	
	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	
	            // Execute SQL queries
	            Statement DELETE_QUESTION_SQL_Statement = DB_Connnection.createStatement();
	            String DELETE_QUESTION_SQL_Query = "DELETE Q FROM QUESTION AS Q INNER JOIN QUESTION_ANSWER AS QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER AS A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.QUESTION_ID=" + request.getParameter("QUESTION_ID")+";";
	            DELETE_QUESTION_SQL_Statement.executeUpdate(DELETE_QUESTION_SQL_Query);

	            // Clean-up environment
	            DELETE_QUESTION_SQL_Statement.close();
	            DB_Connnection.close();
	           
			
	        } catch (Exception e) {
	            
	        	//e.printStackTrace();
	            response.sendRedirect("admin/question/create_question_mc.jsp?success=false&error=Error%20Updating%20Record");
	            return;
	        }
			
			
			//Get text, category and correct answer num
			String question = request.getParameter("q_text");
	        String category = request.getParameter("q_category");
	        int correctAnswer = Integer.parseInt(request.getParameter("q_correct_answer"));
	        
	        //declare needed variables
	        int numAnswers;
	        int [] answerIDS = new int[6]; //answer ids
	        
	        //check for the number of answers given
	        if(request.getParameter("q_a5").isEmpty() & request.getParameter("q_a6").isEmpty()) {
	        	numAnswers = 4;
	        } else if(request.getParameter("q_a6").isEmpty()) {
	        	numAnswers = 5;
	        } else {
	        	numAnswers = 6;
	        }
	     
	        try {
	            //Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Open a connection
	            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	            //Insert into QUESTION Table
	            Statement ADD_QUESTION_Statement = DB_Connnection.createStatement();
	            String ADD_QUESTION_STRING = "INSERT INTO QUESTION (QUESTION_ID, TEXT, CATEGORY, IS_TRUE_FALSE, NUM_ANSWERS) VALUES (" + QUESTION_ID + ", '" + question + "', '" + category + "', 0, " + numAnswers + ")";
	            ADD_QUESTION_Statement.executeUpdate(ADD_QUESTION_STRING);
	                       
	            
	            //Variables for inserting into ANSWER Table
	            Statement GET_ANSWER_ID_Statement = DB_Connnection.createStatement();
	            ResultSet answerRS = null;
	            
	            Statement ADD_ANSWER_Statement = DB_Connnection.createStatement();
	            String ADD_ANSWER_STRING;
	            
	            
	            //Insert into ANSWER Table
	            for(int i=0; i<numAnswers; i++) {
	            	
	            	ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('" + request.getParameter("q_a" + (i+1)) + "');";
	                ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
	                
	                //Get answer ID
	                answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");
	                
	                if(answerRS.last()) {
	                	answerIDS[i] = answerRS.getInt("ANSWER_ID");
	                }
	            }
	            
	            //Variables for inserting into QUESTION_ANSWER Table
	       	 	Statement ADD_QUESTION_ANSWER_Statement = DB_Connnection.createStatement();
	       	 	String ADD_QUESTION_ANSWER_STRING;
	            
	            //Insert into QUESTION-ANSWER Table
	            for(int i=0; i<numAnswers; i++) {
	            	if((i+1) == correctAnswer) {
	                    ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + answerIDS[i] + ", 1, " + QUESTION_ID + ")";
	                    ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
	            	} else {
	                    ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + answerIDS[i] + ", 0, " + QUESTION_ID + ")";
	                    ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
	            	}
	            	
	            }
	            
	            
	            // Clean-up environment
	            ADD_QUESTION_Statement.close();
	            GET_ANSWER_ID_Statement.close();
	            ADD_ANSWER_Statement.close();
	            ADD_QUESTION_ANSWER_Statement.close();
	            
	            answerRS.close();
	            
	            DB_Connnection.close();

	            //success
	            response.sendRedirect("admin/question/question_management.jsp?message_mc=Question%20Updated%20Successfully");
	            
	        } catch (Exception e) {
	            
	        	//e.printStackTrace();
				response.sendRedirect("admin/question/edit_question_mc.jsp?success=false&error=Error%20Updating%20Record");
	            return;
	        }

		} else {
			  response.sendRedirect("admin/question/edit_question_mc.jsp?success=false&error=Unknown%20Error");
			  return;
		}
	}
}
