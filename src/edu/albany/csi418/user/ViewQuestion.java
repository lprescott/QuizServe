package edu.albany.csi418.main;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.albany.csi418.session.LoginEnum;

/**
 * Servlet implementation class ViewQuestion
 */
@WebServlet("/ViewQuestion")
public class ViewQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewQuestion() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
            //Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());
	 
            //Execute SQL queries
            Statement Question_SQL_Statement = DB_Connnection.createStatement();
            
            //Create Map to save question-id matching
            @SuppressWarnings("rawtypes")
			List<Map> questionList =new ArrayList<Map>();
            
            String sql ="SELECT * FROM QUESTION";
            
            ResultSet ql =  Question_SQL_Statement.executeQuery(sql);
            
            
            while(ql.next()) {
            	
            	Map<String, Serializable> tempMap = new HashMap<String, Serializable>();
            	
            	String TEXT = ql.getString("TEXT");
        		
        		int QUESTION_ID = ql.getInt("QUESTION_ID");
        		
        		String CATEGORY = ql.getString("CATEGORY");
        		
        		if(ql.getString("IMAGE_NAME") != null) {
        			tempMap.put("IMAGE_NAME", ql.getString("IMAGE_NAME"));
        		}else {
        			tempMap.put("IMAGE_NAME", null);
        		}
            	
            	if(!ql.getBoolean("IS_TRUE_FALSE")) {
            		
            		String sql_al = "SELECT * FROM QUESTION_ANSWER right outer join ANSWER on QUESTION_ANSWER.ANSWER_ID = ANSWER.ANSWER_ID"
            				+ " WHERE QUESTION_ANSWER.QUESTION_ID = " + QUESTION_ID;
            		
            		Statement Question_SQL_Statement_2 = DB_Connnection.createStatement();
            		
            		ResultSet al = Question_SQL_Statement_2.executeQuery(sql_al);
            		
            		int number = 1; String key;
            		
            		while(al.next()) {
            			if(al.getBoolean("IS_CORRECT_ANSWER"))
            					tempMap.put("CORRECT_ANSWER", al.getString("ANSWER"));
            			
            			key = "ANSWER_" + number;
            			
            			tempMap.put(key,al.getString("ANSWER"));
            			
            			number++;
            		}
            		
            		
            		tempMap.put("TEXT", TEXT);
            		tempMap.put("QUESTION_ID", QUESTION_ID);
            		tempMap.put("CATEGORY",CATEGORY);
            		
            		questionList.add(tempMap);
            		
            		al.close();
          
            	}else {
            		
            		tempMap.put("TEXT", TEXT);
            		tempMap.put("QUESTION_ID", QUESTION_ID);
            	
            		if(ql.getBoolean("TF_IS_TRUE")) {
            			tempMap.put("CORRECT_ANSWER", "TURE");
            		}else {
            			tempMap.put("CORRECT_ANSWER", "FALSE");
            		}
            		tempMap.put("ANSWER_1", "TURE");
            		tempMap.put("ANSWER_2", "FALSE");
            		questionList.add(tempMap);
            	}
            }
            

            ql.close();
            Question_SQL_Statement.close();
            DB_Connnection.close();


            request.setAttribute("question_list",questionList);
            
    		request.getRequestDispatcher("admin/show_all_question.jsp").forward(request, response);
    		
            
		
		}catch (Exception e) {
	            
	        	System.out.println(e);
	            response.sendRedirect("admin/create_user.jsp?success=false&error=Unknown%20Error");
	            return;
	        }
	}

}
