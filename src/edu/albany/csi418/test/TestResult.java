package edu.albany.csi418.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.albany.csi418.session.LoginEnum;
import edu.albany.csi418.session.Logout;

/**
 * Servlet implementation class TestResult
 */
@SuppressWarnings("unused")
@WebServlet(asyncSupported = true, urlPatterns = { "/TestResult" })
public class TestResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//request.getRequestDispatcher("/public/result_test.jsp").forward(request, response);
		
		int userID = Integer.parseInt(request.getParameter("USERS_ID"));
		int testID = Integer.parseInt(request.getParameter("TEST_ID"));
		
		try {

            //Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

            // Execute SQL queries
            Statement TEST_SQL_Statement = DB_Connnection.createStatement();
            
            String TEST_SQL_Query = "select HEADER_TEXT, test_questions.QUESTION_ID,IS_TRUE_FALSE,TF_IS_TRUE ,question.IMAGE_NAME,TEXT,  GROUP_CONCAT(Answer SEPARATOR  '*') as answers "
            		+ "from test left join test_questions on test_questions.TEST_ID = test.test_id left join question "
            		+ "on question.question_id = test_questions.question_id left join question_answer "
            		+ "on question_answer.QUESTION_ID = question.question_id left join answer "
            		+ "on answer.answer_id = question_answer.ANSWER_ID "
            		+ "where test.test_id = " + testID +" group by text order by question.question_ID;";
            
            ResultSet TEST_Results = TEST_SQL_Statement.executeQuery(TEST_SQL_Query);
            
            
            
            Statement TEST_SQL_C_Statement = DB_Connnection.createStatement();
            
            String TEST_Correct_Answer = "select test_questions.QUESTION_ID,IS_TRUE_FALSE,TF_IS_TRUE, Answer as c_answer from test left join test_questions "
            		+ "on test_questions.TEST_ID = test.test_id left join question on question.question_id = test_questions.question_id left join question_answer "
            		+ "on question_answer.QUESTION_ID = question.question_id left join answer on answer.answer_id = question_answer.ANSWER_ID  "
            		+ "where (test.test_id= " + testID +" and question_answer.IS_CORRECT_ANSWER = 1) or (IS_TRUE_FALSE = 1) group by text order by question.question_ID;" ;
            
            ResultSet Correct_Result = TEST_SQL_C_Statement.executeQuery(TEST_Correct_Answer);
            
            Map<Integer, String> correctList = new HashMap<Integer, String>();
            
            while(Correct_Result.next()) {
            	
            	if(Correct_Result.getBoolean("IS_TRUE_FALSE")) {
            		if(Correct_Result.getBoolean("TF_IS_TRUE")) {
            			correctList.put(Correct_Result.getInt("QUESTION_ID"), "TURE");
            		}else {
            			correctList.put(Correct_Result.getInt("QUESTION_ID"), "FALSE");
            		}
            		
            	}else {
            		correctList.put(Correct_Result.getInt("QUESTION_ID"), Correct_Result.getString("c_answer"));
            	}
            }
            
            
            
            Statement USER_SQL_Statement = DB_Connnection.createStatement();
            
            String USER_SQL_Query = "select TEST_ASSIGNED, TEST_DATE,TF_CHOSEN,SCORE, QUESTION.QUESTION_ID, answer as \"user_answer\", IS_TRUE_FALSE, "
            		+ "TF_IS_TRUE from tests_taken left join results on tests_taken.TEST_TAKEN_ID = results.TEST_TAKEN_ID left join question "
            		+ "on results.question_id = question.question_id left join answer on results.answer_id = answer.answer_ID "
            		+ "left join allowed_users on tests_taken.TEST_ID = allowed_users.TEST_ID "
            		+ "where tests_taken.USERS_ID = " + userID;
            
            ResultSet USER_Results = USER_SQL_Statement.executeQuery(USER_SQL_Query);
            
            Map<Integer, String> userList = new HashMap<Integer, String>();
            Map<String, String> data_Overall = new HashMap<String, String>();
            
            while(USER_Results.next()) {
            	data_Overall.put("T_D",USER_Results.getString("TEST_DATE"));
            	data_Overall.put("T_A",USER_Results.getString("TEST_ASSIGNED"));
            	data_Overall.put("T_P",USER_Results.getString("SCORE"));
            	if(USER_Results.getBoolean("IS_TRUE_FALSE")) {
            		if(USER_Results.getInt("TF_CHOSEN") == 1){
            			userList.put(USER_Results.getInt("QUESTION_ID"), "TURE");
            		}else {
            			userList.put(USER_Results.getInt("QUESTION_ID"), "FALSE");
            		}
            		
            	}else {
            		userList.put(USER_Results.getInt("QUESTION_ID"), USER_Results.getString("user_answer"));
            	}
            }
            
            
            @SuppressWarnings("rawtypes")
			ArrayList<Map> user_test_page = new ArrayList<>();
            
            @SuppressWarnings("rawtypes")
			ArrayList<Map> test_Overall = new ArrayList<>();
            
            int totalQuestion = 0;
            int correct = 0;
            String takenTime , testName;
            
            int q_ID;
            
            while(TEST_Results.next()) {
            	data_Overall.put("H_T",TEST_Results.getString("HEADER_TEXT"));
            	totalQuestion++;
            	Map<String,String> tempHold = new HashMap<>();
            	
            	tempHold.put("Question_TEXT", TEST_Results.getString("TEXT"));
            	tempHold.put("Question_IMAGE", TEST_Results.getString("IMAGE_NAME"));
            	
            	q_ID = TEST_Results.getInt("QUESTION_ID");
            	
            	if(TEST_Results.getBoolean("IS_TRUE_FALSE")) {
            		tempHold.put("ANSWER_1", "TURE");
            		tempHold.put("ANSWER_2", "FALSE");
            		if(userList.get(q_ID) == "TURE") {
            			tempHold.put("U_ANSWER", "TURE");
            		}else {
            			tempHold.put("U_ANSWER", "FALSE");
            		}
            		
            		if(correctList.get(q_ID) == "TURE") {
            			tempHold.put("C_ANSWER", "TURE");
            		}else {
            			tempHold.put("C_ANSWER", "FALSE");
            		}	
            		
            	}else {
            		String allAnswer = TEST_Results.getString("answers");
            		String[] answerlist = allAnswer.split("\\*");
            		int i = 1;
            		for(String e : answerlist) {
            			tempHold.put(("ANSWER_"+i),e);
            			i++;
            		}
            		tempHold.put("U_ANSWER", userList.get(q_ID));
            		tempHold.put("C_ANSWER", correctList.get(q_ID));
            		
            	}
            	
            	if(tempHold.get("C_ANSWER") == tempHold.get("U_ANSWER")){
        				correct++;
        		}
            	
        		
        		user_test_page.add(tempHold);
        		
            }
            
            /*
            for(@SuppressWarnings("rawtypes") Map cell : user_test_page) {
            	System.out.println("Question Text:" + cell.get("Question_TEXT"));
            	System.out.println("Question IMG:" + cell.get("IMAGE_NAME"));
            	System.out.println("ANSWER A:" + cell.get("ANSWER_1"));
            	System.out.println("ANSWER B:" + cell.get("ANSWER_2"));
            	System.out.println("ANSWER C:" + cell.get("ANSWER_3"));
            	System.out.println("ANSWER D:" + cell.get("ANSWER_4"));
            	System.out.println("Correct ANSWER " + cell.get("C_ANSWER"));
            	System.out.println("USER ANSWER " + cell.get("U_ANSWER"));
            	System.out.println("\n\n");
            	
            }
            System.out.println("Total Question : "+totalQuestion + "correct:" + correct);
            */
            data_Overall.put("C_A", String.valueOf(correct));
            data_Overall.put("W_A",String.valueOf(totalQuestion - correct));
            
            
            test_Overall.add(data_Overall);
            
            
            
            TEST_Results.close();
            TEST_SQL_Statement.close();
            Correct_Result.close();
            TEST_SQL_C_Statement.close();
            USER_Results.close();
            USER_SQL_Statement.close();
            DB_Connnection.close();
            
            
            request.setAttribute("testPage", user_test_page);
            request.setAttribute("testoverAll",test_Overall);
            request.getRequestDispatcher("/public/result_test.jsp").forward(request, response);
            
		}catch (Exception e) {
			
			System.out.println(e);
			
			response.sendRedirect("javascript:history.back(-1)?success=false&error=Unknown%20Error");
			
		}
		
	}

}
