package edu.albany.csi418.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;


/**
 * Servlet implementation class LoginServlet
 * 
 * This servlet connects to our QUIZ MySQL database, checking if the posted login information corresponds to known user/admin
 * logins and/or returns/redirects the the relevant information/page.
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = 0;

    	String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Flags set to true if user or admin is found
        boolean isValidUser = false;
        boolean isValidAdmin = false;

        // Flag set to true if user is marked inactive
        boolean isInactiveUser = false;


        try {
        	
        	Logout.invalidateOldSession(request);

            //Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

            // Execute SQL queries
            Statement USER_SQL_Statement = DB_Connnection.createStatement();
            String USER_SQL_Query = "SELECT USERS_ID, EMAIL, PASSWORD, IS_ACTIVE FROM USERS";

            Statement ADMIN_SQL_Statement = DB_Connnection.createStatement();
            String ADMIN_SQL_Query = "SELECT ADMIN_ID, EMAIL, PASSWORD FROM ADMINISTRATOR";

            ResultSet USER_Results = USER_SQL_Statement.executeQuery(USER_SQL_Query);
            ResultSet ADMIN_Results = ADMIN_SQL_Statement.executeQuery(ADMIN_SQL_Query);


            // Extract data from result set: USER_Results
            while (USER_Results.next()) {
                if (email.equals(USER_Results.getString("EMAIL")) && password.equals(USER_Results.getString("PASSWORD"))) {
                    if (USER_Results.getBoolean("IS_ACTIVE") == true) {
                        isValidUser = true;
                        id = USER_Results.getInt("USERS_ID");
                    } else {
                        isInactiveUser = true;
                    }

                    break;
                }
            }

            if (isValidUser == false && isInactiveUser == false) {
                // Extract data from result set: ADMIN_Results
                while (ADMIN_Results.next()) {
                    if (email.equals(ADMIN_Results.getString("EMAIL")) && password.equals(ADMIN_Results.getString("PASSWORD"))) {
                        isValidAdmin = true;
                        id = ADMIN_Results.getInt("ADMIN_ID");
                    }

                    break;
                }
            }

            // Control structure to determine type of login
            if (isValidUser == true) {

                //generate a new session
                //create jssessionid cookie
                HttpSession session = request.getSession(true);

                //Expiration in 10 minutes
                session.setMaxInactiveInterval(10 * 60);
                
                //Add session email attribute, and user-type
                session.setAttribute("email", email);
                session.setAttribute("user-type", "user");
				session.setAttribute("id", id);
                
                // Clean-up environment
                USER_Results.close();
                ADMIN_Results.close();

                USER_SQL_Statement.close();
                ADMIN_SQL_Statement.close();

                DB_Connnection.close();
                
                //redirect to user main page
                response.sendRedirect(request.getContextPath() + "/user/main.jsp");

            } else if (isInactiveUser == true) {
            	
                // Clean-up environment
                USER_Results.close();
                ADMIN_Results.close();

                USER_SQL_Statement.close();
                ADMIN_SQL_Statement.close();

                DB_Connnection.close();
            	            	
                //return to login page with error binded to request
                response.sendRedirect("login.jsp?success=false&error=User%20Account%20Disabled");

            } else if (isValidAdmin == true) {  	
          
                //generate a new session
                //create jssessionid cookie
                HttpSession session = request.getSession(true);

                //Expiration in 10 minutes
                session.setMaxInactiveInterval(10 * 60);
                
                //Add session email attribute, and user-type
                session.setAttribute("email", email);
                session.setAttribute("user-type", "admin");
				session.setAttribute("id", id);
                
                // Clean-up environment
                USER_Results.close();
                ADMIN_Results.close();

                USER_SQL_Statement.close();
                ADMIN_SQL_Statement.close();

                DB_Connnection.close();

                //redirect to admin main page
                response.sendRedirect(request.getContextPath() + "/admin/main.jsp");

            } else {
                
                // Clean-up environment
                USER_Results.close();
                ADMIN_Results.close();

                USER_SQL_Statement.close();
                ADMIN_SQL_Statement.close();

                DB_Connnection.close();
            	            	
                //return to login page with error binded to request
                response.sendRedirect("login.jsp?success=false&error=Invalid%20Login%20Credentials");
            }

        } catch (Exception e) {
            //System.out.println(e);
        	            	
            //return to login page with error binded to request
            response.sendRedirect("login.jsp?success=false&error=Unknown%20Error");
        }
    }
}