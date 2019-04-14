package edu.albany.csi418.question;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import edu.albany.csi418.session.LoginEnum;


/**
 * Servlet implementation class EditQuestionMC
 */
@WebServlet("/EditQuestionMC")
@MultipartConfig
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
	            Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	
	            // Execute SQL queries
	            Statement DELETE_QUESTION_SQL_Statement = DB_Connection.createStatement();
	            String DELETE_QUESTION_SQL_Query = "DELETE Q FROM QUESTION AS Q INNER JOIN QUESTION_ANSWER AS QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER AS A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.QUESTION_ID=" + request.getParameter("QUESTION_ID")+";";
	            DELETE_QUESTION_SQL_Statement.executeUpdate(DELETE_QUESTION_SQL_Query);

	            // Clean-up environment
	            DELETE_QUESTION_SQL_Statement.close();
	            DB_Connection.close();
	            
	            response.sendRedirect("admin/question/question_management.jsp?message_mc=Question%20Deleted%20Successfully");
	            return;
			
			} catch (Exception e) {
	            
	        	//System.out.println(e);
				response.sendRedirect("admin/question/edit_question_mc.jsp?success=false&error=Error%20Deleting%20Record");
	            return;
	        }
			
		} else if(action.equals("UPDATE")){
			
			//New part object containing image, and vars
			Part filePart = request.getPart("q_image");
			String fileName = "";
			InputStream fileContent = null;
			
			//Get name and content
			if (filePart != null && !(filePart.getSize() == 0)) {
				fileName = extractFileName(filePart);
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				fileContent = filePart.getInputStream();
			}
			
			int QUESTION_ID = Integer.parseInt(request.getParameter("QUESTION_ID"));  

			try {
				
				//Load the Connector/J
	            Class.forName("com.mysql.cj.jdbc.Driver");
	
	            // Open a connection
	            Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	
	            // Execute SQL queries
	            Statement DELETE_QUESTION_SQL_Statement = DB_Connection.createStatement();
	            String DELETE_QUESTION_SQL_Query = "DELETE Q FROM QUESTION AS Q INNER JOIN QUESTION_ANSWER AS QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER AS A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.QUESTION_ID=" + request.getParameter("QUESTION_ID")+";";
	            DELETE_QUESTION_SQL_Statement.executeUpdate(DELETE_QUESTION_SQL_Query);

	            // Clean-up environment
	            DELETE_QUESTION_SQL_Statement.close();
	            DB_Connection.close();
	           
			
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
	            
	          //If a file was attached
	            if (filePart != null && !(filePart.getSize() == 0)) {
	            	
	            	String appPath = request.getServletContext().getRealPath("");
	            	String savePath = appPath + "uploads";
	            	            	
	            	// creates the save directory if it does not exists
	            	File fileSaveDir = new File(savePath);
	            	if (!fileSaveDir.exists()) {
	        	        fileSaveDir.mkdir();
	            	}

	            	//Get new path
	            	File fileToSave = new File(savePath + File.separator + fileName);
	            	Integer x = 0;
	            	String temp = fileName.substring(0, fileName.lastIndexOf("."));
	            	String ext = fileName.substring(fileName.lastIndexOf("."));
	            	
	            	//Adds a number if the same image exists
	            	while (fileToSave.exists()) {
	            		
	            		x++;
	            		String tempFileName = temp + x.toString() + ext;
	            		fileToSave = new File(savePath + File.separator + tempFileName);
	            	} if (x != 0) {
	            		
	            		fileName = temp + x.toString() + ext;
	            	}
	            	
	            	//Save file
	            	Files.copy(fileContent, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            	
	            	//clean up
	            	fileSaveDir.delete();
	            	//fileToSave.delete();
	            	filePart.delete();
	            	fileContent.close();
	            }

	            // Open a connection
	            Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

	            //Insert into QUESTION Table
	            Statement ADD_QUESTION_Statement = DB_Connection.createStatement();
	            String ADD_QUESTION_STRING = "INSERT INTO QUESTION (QUESTION_ID, TEXT, CATEGORY, IS_TRUE_FALSE, NUM_ANSWERS, IMAGE_NAME) VALUES (" + QUESTION_ID + ", '" + question + "', '" + category + "', 0, " + numAnswers + ",'" + fileName + "')";
	            ADD_QUESTION_Statement.executeUpdate(ADD_QUESTION_STRING);
	                       
	            
	            //Variables for inserting into ANSWER Table
	            Statement GET_ANSWER_ID_Statement = DB_Connection.createStatement();
	            ResultSet answerRS = null;
	            
	            Statement ADD_ANSWER_Statement = DB_Connection.createStatement();
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
	       	 	Statement ADD_QUESTION_ANSWER_Statement = DB_Connection.createStatement();
	       	 	String ADD_QUESTION_ANSWER_STRING;
	            
	            //Insert into QUESTION-ANSWER Table
	            for(int i=0; i<numAnswers; i++) {
	            	if (correctAnswer > numAnswers)
	            	{
	            		response.sendRedirect("admin/question/create_question_mc.jsp?success=false&error=Error:%20Not%20Enough%20Answers");
	            		return;
	            	}
	            	else {
	            	if((i+1) == correctAnswer) {
	                    ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + answerIDS[i] + ", 1, " + QUESTION_ID + ")";
	                    ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
	            	} else {
	                    ADD_QUESTION_ANSWER_STRING = "INSERT INTO QUESTION_ANSWER (ANSWER_ID, IS_CORRECT_ANSWER, QUESTION_ID) VALUES (" + answerIDS[i] + ", 0, " + QUESTION_ID + ")";
	                    ADD_QUESTION_ANSWER_Statement.executeUpdate(ADD_QUESTION_ANSWER_STRING);
	            	}
	            	}
	            	
	            }
	            
	            
	            // Clean-up environment
	            ADD_QUESTION_Statement.close();
	            GET_ANSWER_ID_Statement.close();
	            ADD_ANSWER_Statement.close();
	            ADD_QUESTION_ANSWER_Statement.close();
	            
	            answerRS.close();
	            
	            DB_Connection.close();

	            //success
	            response.sendRedirect("admin/question/question_management.jsp?message_mc=Question%20Updated%20Successfully");
	            
	        } catch(SQLException s) {
	        	
	        	 response.sendRedirect("admin/question/edit_question_mc.jsp?success=false&error=SQL%20Exception");
	             return;
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
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
