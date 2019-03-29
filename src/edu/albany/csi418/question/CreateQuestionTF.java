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
@WebServlet("/CreateQuestionTF")
public class CreateQuestionTF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionTF() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
        
        try {
            //Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

            //Insert into QUESTION Table
            Statement ADD_QUESTION_Statement = DB_Connnection.createStatement();
            String ADD_QUESTION_STRING = "INSERT INTO QUESTION (TEXT, CATEGORY, IS_TRUE_FALSE, TF_IS_TRUE) VALUES ('" + question + "', '" + category + "', 1, " + tf + ")";
            ADD_QUESTION_Statement.executeUpdate(ADD_QUESTION_STRING);

            // Clean-up environment
            ADD_QUESTION_Statement.close();
            
            DB_Connnection.close();
            
            //success
            response.sendRedirect("admin/question/create_question_tf.jsp?success=true");
        
        } catch (Exception e) {
            
        	//e.printStackTrace();
            response.sendRedirect("admin/question/create_question_tf.jsp?success=false&error=Unknown%20Error");
            return;
        }
        
	}

}
