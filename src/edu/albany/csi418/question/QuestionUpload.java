package edu.albany.csi418.question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
//import org.apache.commons.io.FilenameUtils;

import com.opencsv.CSVReader;

import edu.albany.csi418.FileUtils;
import edu.albany.csi418.session.LoginEnum;

/**
 * Servlet implementation class QuestionUpload
 */
@WebServlet("/QuestionUpload")
@MultipartConfig
public class QuestionUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestionUpload() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// New part object containing image, and vars
		Part filePart = request.getPart("csv_file");
		String fileName = "";
		//String fileNameNoExtension = "";
		InputStream fileContent = null;

		// Get name and content
		if (filePart != null && !(filePart.getSize() == 0)) {
			fileName = FileUtils.extractFileName(filePart);
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			//fileNameNoExtension = FilenameUtils.removeExtension(fileName);
			fileContent = filePart.getInputStream();
		}

		//Create a bew CSVReader object for parsing
		CSVReader reader = new CSVReader(new InputStreamReader(fileContent));

		try {
			// Load the Connector/J
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Open a connection
			Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

			//count variable
			int count = 1;

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				
				//Initialize variables 
				int numAnswers = 0;
				int correctAnswer = 0;
				String qText = null;
				String a1 = null;
				String a2 = null;
				String a3 = null;
				String a4 = null;
				String a5 = null;
				String a6 = null;

				//Assign variables
				qText = nextLine[1];
				a1 = nextLine[2];
				a2 = nextLine[3];
				a3 = nextLine[4];
				a4 = nextLine[5];
				a5 = nextLine[6];
				a6 = nextLine[7];

				if (a5.isEmpty() && a6.isEmpty()) {
					
					//There are 4 answers
					numAnswers = 4;
					int currentAnswerID = 0;
					int currentQuestionID = 0;
					
					//Insert Question
					Statement ADD_QUESTION_Statement = DB_Connection.createStatement();
					String ADD_QUESTION_STRING = "INSERT INTO QUESTION (TEXT, CATEGORY, IS_TRUE_FALSE, NUM_ANSWERS) VALUES ('" + qText + "', 'Question Upload', 0, " + numAnswers + ")";
					ADD_QUESTION_Statement.executeUpdate(ADD_QUESTION_STRING);
					
					// Get Question ID
					Statement GET_QUESTION_ID_Statement = DB_Connection.createStatement();
					ResultSet questionRS = GET_QUESTION_ID_Statement.executeQuery("SELECT * FROM QUESTION;");

					if (questionRS.last()) {
						currentQuestionID = questionRS.getInt("QUESTION_ID");
					}
					
					Statement ADD_ANSWER_Statement  = DB_Connection.createStatement();	
					Statement GET_ANSWER_ID_Statement  = DB_Connection.createStatement();	
					ResultSet answerRS;
					String ADD_QUESTION_ANSWER_STRING;
					Statement ADD_QUESTION_ANSWER_Statement  = DB_Connection.createStatement();	

					//ANSWER 1 ------------------------------------------------------------------------------------
					if (a1.contains("*"))
						correctAnswer = 1;
					a1 = a1.split(",\\s+")[1];
					
					//Insert Answer
					String ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a1+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 1) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 2 ------------------------------------------------------------------------------------
					if (a2.contains("*"))
						correctAnswer = 2;
					a2 = a2.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a2+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 2) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}

					//ANSWER 3 ------------------------------------------------------------------------------------
					if (a3.contains("*"))
						correctAnswer = 3;
					a3 = a3.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a3+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 3) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 4 ------------------------------------------------------------------------------------
					if (a4.contains("*"))
						correctAnswer = 4;
					a4 = a4.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a4+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 4) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}

					//Close
					ADD_QUESTION_Statement.close();
					GET_QUESTION_ID_Statement.close();
					questionRS.close();
					ADD_ANSWER_Statement.close();
					GET_ANSWER_ID_Statement.close();
					answerRS.close();
					ADD_QUESTION_ANSWER_Statement.close();	
					
				} else if (a6.isEmpty()) {
					
					//There are 5 answers
					numAnswers = 5;
					int currentAnswerID = 0;
					int currentQuestionID = 0;

					//Insert Question
					Statement ADD_QUESTION_Statement = DB_Connection.createStatement();
					String ADD_QUESTION_STRING = "INSERT INTO QUESTION (TEXT, CATEGORY, IS_TRUE_FALSE, NUM_ANSWERS) VALUES ('" + qText + "', 'Question Upload', 0, " + numAnswers + ")";
					ADD_QUESTION_Statement.executeUpdate(ADD_QUESTION_STRING);
					
					// Get Question ID
					Statement GET_QUESTION_ID_Statement = DB_Connection.createStatement();
					ResultSet questionRS = GET_QUESTION_ID_Statement.executeQuery("SELECT * FROM QUESTION;");

					if (questionRS.last()) {
						currentQuestionID = questionRS.getInt("QUESTION_ID");
					}
					
					Statement ADD_ANSWER_Statement  = DB_Connection.createStatement();	
					Statement GET_ANSWER_ID_Statement  = DB_Connection.createStatement();	
					ResultSet answerRS;
					String ADD_QUESTION_ANSWER_STRING;
					Statement ADD_QUESTION_ANSWER_Statement  = DB_Connection.createStatement();	

					//ANSWER 1 ------------------------------------------------------------------------------------
					if (a1.contains("*"))
						correctAnswer = 1;
					a1 = a1.split(",\\s+")[1];
					
					//Insert Answer
					String ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a1+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 1) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 2 ------------------------------------------------------------------------------------
					if (a2.contains("*"))
						correctAnswer = 2;
					a2 = a2.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a2+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 2) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}

					//ANSWER 3 ------------------------------------------------------------------------------------
					if (a3.contains("*"))
						correctAnswer = 3;
					a3 = a3.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a3+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 3) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 4 ------------------------------------------------------------------------------------
					if (a4.contains("*"))
						correctAnswer = 4;
					a4 = a4.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a4+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 4) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 5 ------------------------------------------------------------------------------------
					if (a5.contains("*"))
						correctAnswer = 5;
					a5 = a5.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a5+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 5) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}

					//Close
					ADD_QUESTION_Statement.close();
					GET_QUESTION_ID_Statement.close();
					questionRS.close();
					ADD_ANSWER_Statement.close();
					GET_ANSWER_ID_Statement.close();
					answerRS.close();
					ADD_QUESTION_ANSWER_Statement.close();	
					
				} else if (a5.isEmpty()) {
					// error, skip question insertion
					response.sendRedirect("admin/question/question_upload.jsp?success=false&error=Error%20Adding%20Question%20" + count);
					System.out.println("Error: Answer 5 was empty, and six was not.");
					reader.close();
					return;

				} else {
					//There are 6 anwers
					numAnswers = 6;
					int currentAnswerID = 0;
					int currentQuestionID = 0;

					//Insert Question
					Statement ADD_QUESTION_Statement = DB_Connection.createStatement();
					String ADD_QUESTION_STRING = "INSERT INTO QUESTION (TEXT, CATEGORY, IS_TRUE_FALSE, NUM_ANSWERS) VALUES ('" + qText + "', 'Question Upload', 0, " + numAnswers + ")";
					ADD_QUESTION_Statement.executeUpdate(ADD_QUESTION_STRING);
					
					// Get Question ID
					Statement GET_QUESTION_ID_Statement = DB_Connection.createStatement();
					ResultSet questionRS = GET_QUESTION_ID_Statement.executeQuery("SELECT * FROM QUESTION;");

					if (questionRS.last()) {
						currentQuestionID = questionRS.getInt("QUESTION_ID");
					}
					
					Statement ADD_ANSWER_Statement  = DB_Connection.createStatement();	
					Statement GET_ANSWER_ID_Statement  = DB_Connection.createStatement();	
					ResultSet answerRS;
					String ADD_QUESTION_ANSWER_STRING;
					Statement ADD_QUESTION_ANSWER_Statement  = DB_Connection.createStatement();	

					//ANSWER 1 ------------------------------------------------------------------------------------
					if (a1.contains("*"))
						correctAnswer = 1;
					a1 = a1.split(",\\s+")[1];
					
					//Insert Answer
					String ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a1+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 1) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 2 ------------------------------------------------------------------------------------
					if (a2.contains("*"))
						correctAnswer = 2;
					a2 = a2.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a2+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 2) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}

					//ANSWER 3 ------------------------------------------------------------------------------------
					if (a3.contains("*"))
						correctAnswer = 3;
					a3 = a3.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a3+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 3) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 4 ------------------------------------------------------------------------------------
					if (a4.contains("*"))
						correctAnswer = 4;
					a4 = a4.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a4+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 4) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 5 ------------------------------------------------------------------------------------
					if (a5.contains("*"))
						correctAnswer = 5;
					a5 = a5.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a5+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 5) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}
					
					//ANSWER 6 ------------------------------------------------------------------------------------
					if (a6.contains("*"))
						correctAnswer = 6;
					a6 = a6.split(",\\s+")[1];
					
					//Insert Answer
					ADD_ANSWER_STRING = "INSERT INTO ANSWER (ANSWER) VALUES ('"+a6+"');";
					ADD_ANSWER_Statement.executeUpdate(ADD_ANSWER_STRING);
					
					// Get answer ID
					answerRS = GET_ANSWER_ID_Statement.executeQuery("SELECT * FROM ANSWER;");

					if (answerRS.last()) {
						currentAnswerID = answerRS.getInt("ANSWER_ID");
					}
					
					//Insert Question_Answer
					if(correctAnswer == 6) {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 1, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					} else {
						ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + currentAnswerID + ", 0, " + currentQuestionID + ")";
						ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
					}

					//Close
					ADD_QUESTION_Statement.close();
					GET_QUESTION_ID_Statement.close();
					questionRS.close();
					ADD_ANSWER_Statement.close();
					GET_ANSWER_ID_Statement.close();
					answerRS.close();
					ADD_QUESTION_ANSWER_Statement.close();					}

				count++;
				
			}
			
			//Clean up
			DB_Connection.close();
			
			//Success
			response.sendRedirect("admin/question/question_upload.jsp?success=true");

			
		} catch (Exception e) {
			
			//System.out.println(e);
			response.sendRedirect("admin/question/question_upload.jsp?success=false&error=Unknown%20Error");
			return;
		}

		// Clean Up
		reader.close();
	}
}
