package edu.albany.csi418.main;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.albany.csi418.session.LoginEnum;

/**
 * Servlet implementation class EditQuestion
 */
@WebServlet("/EditQuestion")
public class EditQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String id = request.getParameter("id");
			
			
			//Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	 
            //Execute SQL queries
            Statement Question_SQL_Statement = DB_Connnection.createStatement();
            
            //Create Map to save question-id matching
            
            Map<String, Serializable> question = new HashMap<String, Serializable>();
            
            String sql ="SELECT * FROM QUESTION WHERE QUESTION.QUESTION_ID = " + id;
            
            ResultSet tq =  Question_SQL_Statement.executeQuery(sql);
            
            
            while(tq.next()){    
		        
		
				question.put("TEXT", tq.getString("TEXT"));
				question.put("QUESTION_ID", tq.getInt("QUESTION_ID"));
				question.put("CATEGORY",tq.getString("CATEGORY"));
		        
		        if(tq.getString("IMAGE_NAME") != null) {
		        	question.put("IMAGE_NAME", tq.getString("IMAGE_NAME"));
				}else {
					question.put("IMAGE_NAME", null);
				}
		    	
		    	if(!tq.getBoolean("IS_TRUE_FALSE")) {
		    		
		    		String sql_al = "SELECT * FROM QUESTION_ANSWER right outer join ANSWER on QUESTION_ANSWER.ANSWER_ID = ANSWER.ANSWER_ID"
		    				+ " WHERE QUESTION_ANSWER.QUESTION_ID = " + tq.getInt("QUESTION_ID");
		    		
		    		Statement Question_SQL_Statement_2 = DB_Connnection.createStatement();
		    		
		    		ResultSet al = Question_SQL_Statement_2.executeQuery(sql_al);
		    		
		    		int number = 1; String key;
		    		
		    		while(al.next()) {
		    			
		    			key = "ANSWER_" + number;
		    			
		    			question.put(key,al.getString("ANSWER"));
		    			
		    			number++;
		    		}
		    		
		    		al.close();
		    		
		    		
		    		request.setAttribute("teq", question);
		    		request.getRequestDispatcher("admin/edit_question_mc.jsp").forward(request, response);
		    		
		    	}else {
		    		
		    		request.setAttribute("teq", question);
		    		request.getRequestDispatcher("admin/edit_question_tf.jsp").forward(request, response);
		    	}
            }
        	
        	
		}catch (Exception e) {
            
        	System.out.println(e);
            response.sendRedirect("admin/create_user.jsp?success=false&error=Unknown%20Error");
            return;
        }
		
	}

}
