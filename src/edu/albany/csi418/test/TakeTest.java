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
 * this servlet serves accepts the submission of a test, and inserts data in a MySQL database
 * 
 * Servlet implementation class TakeTest
 */
@WebServlet("/TakeTest")
public class TakeTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TakeTest() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Get request parameters
		String action = request.getParameter("submitTest");
		int userID = Integer.parseInt(request.getParameter("USERS_ID"));
		int testID = Integer.parseInt(request.getParameter("TEST_ID"));
		
		//Check for submit button execution
		if (action != null && !action.isEmpty() && action.equals("SUBMIT")) {

			try {
				//Load the Connector/J
				Class.forName("com.mysql.cj.jdbc.Driver");

				//Open a connection
				Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

				//Delete test questions in the in_progress table from the current test
				Statement delete_in_progress = DB_Connection.createStatement();
	            String delete_in_progress_string = "DELETE FROM TEST_IN_PROGRESS WHERE TEST_ID = "+testID+" AND USERS_ID = " + userID + ";";
	            delete_in_progress.executeUpdate(delete_in_progress_string);

	            
				//Add Tests_Taken entry
				Statement tests_taken_statement = DB_Connection.createStatement();
				String tests_taken_query = "INSERT INTO TESTS_TAKEN (TEST_ID, USERS_ID, TEST_DATE) VALUES (" + testID + ", " + userID + ", CURDATE());";
				tests_taken_statement.executeUpdate(tests_taken_query);
				int testTakenID = 0;

				//Get testTakenID
				Statement tests_taken_id_statement = DB_Connection.createStatement();
				ResultSet tests_take_rs = tests_taken_id_statement.executeQuery("SELECT * FROM TESTS_TAKEN;");
				if (tests_take_rs.last()) {
					testTakenID = tests_take_rs.getInt("TEST_TAKEN_ID");
				}

				//Select test questions of the current test
				Statement test_questions_statement = DB_Connection.createStatement();
				String test_questions_query = "SELECT * FROM QUESTION Q INNER JOIN TEST_QUESTIONS TQ ON Q.QUESTION_ID = TQ.QUESTION_ID WHERE TQ.TEST_ID = " + testID + ";";
				ResultSet test_question_rs = test_questions_statement.executeQuery(test_questions_query);
				
				int numCorrect = 0; 
				int numQuestions = 0;
				int questionID;
								
				//Loop through selected questions
				while (test_question_rs.next()) {
					
					questionID = test_question_rs.getInt("QUESTION_ID");
					
					if (test_question_rs.getBoolean("IS_TRUE_FALSE") == true) {
						
						//True/False question
						boolean chosen = false;

						//Determine selected t/f answer
						if (request.getParameter(test_question_rs.getString("QUESTION_ID") + "_true") != null) {
							
							chosen = true;
						} else if (request.getParameter(test_question_rs.getString("QUESTION_ID") + "_false") != null) {
							
							chosen = false;
						}
						
						//Insert into results
						Statement result_statement = DB_Connection.createStatement();
						String result_string = "INSERT INTO RESULTS (TEST_TAKEN_ID, QUESTION_ID, TF_CHOSEN) VALUES (" + testTakenID + ", " + questionID+ ", " + chosen + ");";
						result_statement.executeUpdate(result_string);
						
						//increment correct
						if(chosen == test_question_rs.getBoolean("TF_IS_TRUE")) {
							numCorrect++;
						}
						
						//close
						result_statement.close();
						
					} else if (test_question_rs.getBoolean("IS_TRUE_FALSE") != true) {  
												
						//Multiple choice question
						int chosen = 0;
						
						//Select answers of question
						Statement question_answers_statement = DB_Connection.createStatement();
						String question_answers_query = "SELECT * FROM QUESTION Q INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID WHERE Q.QUESTION_ID = " + questionID + ";";
						ResultSet question_answers_rs = question_answers_statement.executeQuery(question_answers_query);
						
						//Loop through answers
						while (question_answers_rs.next()) {
							
							int answerID = question_answers_rs.getInt("ANSWER_ID");
							
							if(request.getParameter("answer_" + answerID) != null) {
								
								chosen = answerID;
								
								//increment correct
								Statement correct_answer_statement = DB_Connection.createStatement();
								String correct_answer_query = "SELECT * FROM QUESTION_ANSWER WHERE ANSWER_ID = " + answerID + ";";
								ResultSet correct_answer_rs = correct_answer_statement.executeQuery(correct_answer_query);
								
								if(correct_answer_rs.next()) {
									if(correct_answer_rs.getBoolean("IS_CORRECT_ANSWER") == true){
										numCorrect++;
									}
								}
								
								//close
								correct_answer_statement.close();
								correct_answer_rs.close();
							}
						}
						
						//Insert into results
						Statement result_statement = DB_Connection.createStatement();
						String result_string = "INSERT INTO RESULTS (TEST_TAKEN_ID, QUESTION_ID, ANSWER_ID) VALUES (" + testTakenID + ", " + questionID+ ", " + chosen + ");";
						result_statement.executeUpdate(result_string);
						
						//close
						question_answers_statement.close();
						question_answers_rs.close();
						result_statement.close();
						
					}
					
					//increment
					numQuestions++;

				}

				//find score, add it to db
				double score = ( numCorrect / (double) numQuestions ) * 100;
				System.out.println(score);
				
				Statement tests_taken_score_statement = DB_Connection.createStatement();
				//UPDATE table_name SET field1 = new-value1, field2 = new-value2 [WHERE Clause]
				String tests_taken_score_query = "UPDATE TESTS_TAKEN SET SCORE = " + score + " WHERE TEST_TAKEN_ID = " + testTakenID + ";";
				tests_taken_score_statement.executeUpdate(tests_taken_score_query);
				
				//close
				tests_taken_statement.close();
				tests_taken_id_statement.close();
				tests_take_rs.close();
				test_questions_statement.close();
				test_question_rs.close();
				
				//success
				response.sendRedirect("user/test/test_results.jsp?success=true&USERS_ID=" + userID + "&TEST_ID=" + testID + "&TEST_TAKEN_ID=" + testTakenID);
	            return;

				
			} catch (Exception e) {
				
				//System.out.println(e);
				response.sendRedirect("user/test/test_results.jsp?success=false");
	            return;
			}

		} else {
			
			//Save the tests progress
			int questionID = 0; //int questionChanged = 0;

			try {
					
				//Load the Connector/J
				Class.forName("com.mysql.cj.jdbc.Driver");
	
				//Open a connection
				Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	
				//Delete test questions in the in_progress table from the current test
				Statement delete_in_progress = DB_Connection.createStatement();
	            String delete_in_progress_string = "DELETE FROM TEST_IN_PROGRESS WHERE TEST_ID = "+testID+" AND USERS_ID = " + userID + ";";
	            delete_in_progress.executeUpdate(delete_in_progress_string);
				
				//Select test questions of the current test
				Statement test_questions_statement = DB_Connection.createStatement();
				String test_questions_query = "SELECT * FROM QUESTION Q INNER JOIN TEST_QUESTIONS TQ ON Q.QUESTION_ID = TQ.QUESTION_ID WHERE TQ.TEST_ID = " + testID + ";";
				ResultSet test_question_rs = test_questions_statement.executeQuery(test_questions_query);			
				
				//Loop through selected questions
				while (test_question_rs.next()) {
					
					questionID = test_question_rs.getInt("QUESTION_ID");
					
					if (test_question_rs.getBoolean("IS_TRUE_FALSE") == true) {
						
						//True/False question
						boolean chosen = false;

						//Determine selected t/f answer
						if (request.getParameter(test_question_rs.getString("QUESTION_ID") + "_true") != null) {
							
							chosen = true;
						} else if (request.getParameter(test_question_rs.getString("QUESTION_ID") + "_false") != null) {
							
							chosen = false;
						} else if((request.getParameter(test_question_rs.getString("QUESTION_ID") + "_true") == null)&& (request.getParameter(test_question_rs.getString("QUESTION_ID") + "_false") == null)) {
						   
							//continue if no selection
							continue;
						}
						
						//Insert into in progress table
						Statement in_progress_statement = DB_Connection.createStatement();
						String in_progress_string = "INSERT INTO TEST_IN_PROGRESS (TEST_ID, USERS_ID, QUESTION_ID, TF_CHOSEN) VALUES ("+testID+", "+userID+", "+questionID+", "+chosen+");";
						
						in_progress_statement.executeUpdate(in_progress_string);
						
						//close
						in_progress_statement.close();
						
					} else if (test_question_rs.getBoolean("IS_TRUE_FALSE") != true) {  
						
						//Multiple choice question
						int chosen = 0;
						
						//Select answers of question
						Statement question_answers_statement = DB_Connection.createStatement();
						String question_answers_query = "SELECT * FROM QUESTION Q INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID WHERE Q.QUESTION_ID = " + questionID + ";";
						ResultSet question_answers_rs = question_answers_statement.executeQuery(question_answers_query);
						
						//Loop through answers
						while (question_answers_rs.next()) {
							
							int answerID = question_answers_rs.getInt("ANSWER_ID");
							
							if(request.getParameter("answer_" + answerID) != null) {
								chosen = answerID;
							}
						}
						
						//continue if no selection
						if(chosen == 0) {
							continue;
						}
						
						//Insert into in progress
						Statement in_progress_statement = DB_Connection.createStatement();
						String in_progress_string = "INSERT INTO TEST_IN_PROGRESS (TEST_ID, USERS_ID, QUESTION_ID, ANSWER_ID) VALUES ("+testID+", "+userID+", "+questionID+", "+chosen+");";
						in_progress_statement.executeUpdate(in_progress_string);
						
						//close
						question_answers_statement.close();
						question_answers_rs.close();
						in_progress_statement.close();
					}
				}
				
				//scroll
				int scrollHeight = Integer.parseInt(request.getParameter("scrollHeight"));
				request.setAttribute("SCROLL", scrollHeight);

				//success
				response.sendRedirect("user/test/take_test.jsp?USERS_ID=" + userID + "&TEST_ID=" + testID + "&QUESTION_ID=" + questionID + "&SCROLL=" + scrollHeight);
	            return;
	            
			} catch (Exception e) {
				
				//System.out.println(e);
				response.sendRedirect("user/test/test_results.jsp?success=false");
	            return;
				
			}
		}

	}
}
