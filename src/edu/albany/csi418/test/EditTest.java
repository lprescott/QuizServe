package edu.albany.csi418.test;

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

import edu.albany.csi418.FileUtils;
import edu.albany.csi418.session.LoginEnum;

/**
 * Servlet implementation class EditTest
 */
@WebServlet("/EditTest")
@MultipartConfig
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
	            Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	
	            // Execute SQL queries
	            Statement DELETE_TEST_SQL_Statement = DB_Connection.createStatement();
	            String DELETE_TEST_SQL_Query = "DELETE FROM TEST WHERE TEST_ID="+request.getParameter("TEST_ID")+";";
	            DELETE_TEST_SQL_Statement.executeUpdate(DELETE_TEST_SQL_Query);
	            
	            Statement DELETE_TEST_QUESTIONS_SQL_Statement = DB_Connection.createStatement();
	            String DELETE_TEST_QUESTIONS_SQL_Query = "DELETE TQ FROM TEST_QUESTIONS AS TQ WHERE TEST_ID ="+request.getParameter("TEST_ID")+";";
	            DELETE_TEST_QUESTIONS_SQL_Statement.executeUpdate(DELETE_TEST_QUESTIONS_SQL_Query);

	            // Clean-up environment
	            DELETE_TEST_SQL_Statement.close();
	            DELETE_TEST_QUESTIONS_SQL_Statement.close();
	            DB_Connection.close();
	            
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

	        //New part object containing image, and vars
			Part filePart = request.getPart("t_image");
			String fileName = "";
			InputStream fileContent = null;
			
			//Get name and content
			if (filePart != null && !(filePart.getSize() == 0)) {
				fileName = FileUtils.extractFileName(filePart);
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				fileContent = filePart.getInputStream();
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
	
	            if(testDue != null) {
	            	Statement UPDATE_TEST_SQL_Statement = DB_Connection.createStatement();
		            String UPDATE_TEST_SQL_Query = "UPDATE TEST SET TITLE = '" + testTitle + "', HEADER_TEXT = '" + testHeader + "', FOOTER_TEXT = '" + testFooter + "', TEST_DUE = '" + testDue + "' WHERE TEST_ID = " + request.getParameter("TEST_ID") + ";";
		            UPDATE_TEST_SQL_Statement.executeUpdate(UPDATE_TEST_SQL_Query);
		            UPDATE_TEST_SQL_Statement.close();

	            } else {
	            	// Execute SQL queries
		            Statement UPDATE_TEST_SQL_Statement = DB_Connection.createStatement();
		            String UPDATE_TEST_SQL_Query = "UPDATE TEST SET TITLE = '" + testTitle + "', HEADER_TEXT = '" + testHeader + "', FOOTER_TEXT = '" + testFooter + "' WHERE TEST_ID = " + request.getParameter("TEST_ID") + ";";
		            UPDATE_TEST_SQL_Statement.executeUpdate(UPDATE_TEST_SQL_Query);
		            UPDATE_TEST_SQL_Statement.close();
	            }
	           
	            Statement DELETE_TEST_QUESTIONS_SQL_Statement = DB_Connection.createStatement();
	            String DELETE_TEST_QUESTIONS_SQL_Query = "DELETE TQ FROM TEST_QUESTIONS AS TQ WHERE TEST_ID ="+request.getParameter("TEST_ID")+";";
	            DELETE_TEST_QUESTIONS_SQL_Statement.executeUpdate(DELETE_TEST_QUESTIONS_SQL_Query);


	            if(!fileName.equals("")) {
	            	/*
	            	 * UPDATE table_name
					 * SET column1 = value1, column2 = value2, ...
					 * WHERE condition;
	            	 */
	            	
	            	Statement ADD_IMAGE_Statement = DB_Connection.createStatement();
	                String ADD_IMAGE_STRING = "UPDATE TEST SET IMAGE_NAME = '" + fileName + "' WHERE TEST_ID = " + request.getParameter("TEST_ID") + ";";
	                ADD_IMAGE_Statement.executeUpdate(ADD_IMAGE_STRING);
	                ADD_IMAGE_Statement.close();
	            }
	            
	            //Add questions to TEST_QUESTIONS Table
	            Statement QUESTION_SQL_Statement = DB_Connection.createStatement();
	            String QUESTION_SQL_Query = "SELECT * FROM QUESTION";
	            ResultSet QUESTION_RS = QUESTION_SQL_Statement.executeQuery(QUESTION_SQL_Query);

	            Statement ADD_TEST_QUESTION_Statement = null;
	            //Loop all questions
	            while (QUESTION_RS.next()) {
	            	if(request.getParameter(QUESTION_RS.getString("QUESTION_ID")) != null) {
	            		
	            		ADD_TEST_QUESTION_Statement = DB_Connection.createStatement();
	                    String ADD_TEST_QUESTION_STRING = "INSERT INTO TEST_QUESTIONS (TEST_ID, QUESTION_ID) VALUES (" + request.getParameter("TEST_ID") + ", " + QUESTION_RS.getString("QUESTION_ID") + ")";
	                    ADD_TEST_QUESTION_Statement.executeUpdate(ADD_TEST_QUESTION_STRING);
	            		
	            	}
	            	
	            }
	            
	            // Clean-up environment
	            DELETE_TEST_QUESTIONS_SQL_Statement.close();
	            QUESTION_SQL_Statement.close();
	            ADD_TEST_QUESTION_Statement.close();
	            
	            QUESTION_RS.close();
	            
	            DB_Connection.close();
	            
	            response.sendRedirect("admin/test/test_management.jsp?message=Test%20Updated%20Successfully");
	            return;
			
			} catch(SQLException s) {
	        	
	        	 response.sendRedirect("admin/test/edit_test.jsp?success=false&error=SQL%20Exception");
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
