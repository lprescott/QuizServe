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
 * Servlet implementation class CreateTest
 */
@WebServlet("/CreateTest")
public class CreateTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTest() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String testName = request.getParameter("test_name");
        
        try {
            //Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
 
            //Insert into TEST Table
            Statement ADD_TEST_Statement = DB_Connnection.createStatement();
            String ADD_TEST_STRING = "INSERT INTO TEST (ADMIN_ID, HEADER_TEXT) VALUES (" + request.getSession().getAttribute("id") + ", '" + testName + "')";
            ADD_TEST_Statement.executeUpdate(ADD_TEST_STRING);
            
            //Get TEST_ID
            Statement GET_TEST_ID_Statement = DB_Connnection.createStatement();
            ResultSet TEST_RS = GET_TEST_ID_Statement.executeQuery("SELECT * FROM TEST;");
            int TEST_ID = 0; 
            if(TEST_RS.last()) {
            	TEST_ID = TEST_RS.getInt("TEST_ID");
            }
            
            //Add questions to TEST_QUESTIONS Table
            Statement QUESTION_SQL_Statement = DB_Connnection.createStatement();
            String QUESTION_SQL_Query = "SELECT * FROM QUESTION";
            ResultSet QUESTION_RS = QUESTION_SQL_Statement.executeQuery(QUESTION_SQL_Query);

            Statement ADD_TEST_QUESTION_Statement = null;
            
            //Loop all questions
            while (QUESTION_RS.next()) {
            	if(request.getParameter(QUESTION_RS.getString("QUESTION_ID")) != null) {
            		
            		ADD_TEST_QUESTION_Statement = DB_Connnection.createStatement();
                    String ADD_TEST_QUESTION_STRING = "INSERT INTO TEST_QUESTIONS (TEST_ID, QUESTION_ID) VALUES (" + TEST_ID + ", " + QUESTION_RS.getString("QUESTION_ID") + ")";
                    ADD_TEST_QUESTION_Statement.executeUpdate(ADD_TEST_QUESTION_STRING);
            		
            	}
            	
            }
            
            //Clean up environment
            DB_Connnection.close();
            
            ADD_TEST_Statement.close();
            GET_TEST_ID_Statement.close();
            QUESTION_SQL_Statement.close();
            ADD_TEST_QUESTION_Statement.close();
            
            TEST_RS.close();
            QUESTION_RS.close();
            
            response.sendRedirect("admin/test/create_test.jsp?success=true");
            
        } catch (Exception e) {
            
        	//System.out.println(e);
            response.sendRedirect("admin/test/create_test.jsp?success=false&error=Unknown%20Error");
            return;
        }
    
	}

}
