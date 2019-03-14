package edu.albany.csi418;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

import edu.albany.csi418.LoginEnum;
import edu.albany.csi418.Logout;


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
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Flags set to true if user or admin is found
        boolean isValidUser = false;
        boolean isValidAdmin = false;

        // Flag set to true if user is marked inactive
        boolean isInactiveUser = false;


        try {

            //Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connnection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

            // Execute SQL queries
            Statement USER_SQL_Statement = DB_Connnection.createStatement();
            String USER_SQL_Query = "SELECT EMAIL, PASSWORD, IS_ACTIVE FROM USERS";

            Statement ADMIN_SQL_Statement = DB_Connnection.createStatement();
            String ADMIN_SQL_Query = "SELECT EMAIL, PASSWORD FROM ADMINISTRATOR";

            ResultSet USER_Results = USER_SQL_Statement.executeQuery(USER_SQL_Query);
            ResultSet ADMIN_Results = ADMIN_SQL_Statement.executeQuery(ADMIN_SQL_Query);


            // Extract data from result set: USER_Results
            while (USER_Results.next()) {
                if (email.equals(USER_Results.getString("EMAIL")) && password.equals(USER_Results.getString("PASSWORD"))) {
                    if (USER_Results.getBoolean("IS_ACTIVE") == true) {
                        isValidUser = true;
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
                    }

                    break;
                }
            }

            // Control structure to determine type of login
            if (isValidUser == true) {

                Logout.invalidateOldSession(request);

                //generate a new session
                //create jssessionid cookie
                HttpSession session = request.getSession(true);

                //Expiration in 10 minutes
                session.setMaxInactiveInterval(10 * 60);
                
                //Add session email attribute, and user-type
                session.setAttribute("email", email);
                session.setAttribute("user-type", "user");
                
                //redirect to user main page
                request.getRequestDispatcher("/user/main.jsp").forward(request, response);

            } else if (isInactiveUser == true) {
            	
                //return to login page with error binded to request
                request.setAttribute("error", "User Account Disabled");
                request.getRequestDispatcher("login.jsp").forward(request, response);

            } else if (isValidAdmin == true) {  	
          
            	Logout.invalidateOldSession(request);

                //generate a new session
                //create jssessionid cookie
                HttpSession session = request.getSession(true);

                //Expiration in 10 minutes
                session.setMaxInactiveInterval(10 * 60);
                
                //Add session email attribute, and user-type
                session.setAttribute("email", email);
                session.setAttribute("user-type", "admin");

                //redirect to admin main page
                request.getRequestDispatcher("/admin/main.jsp").forward(request, response);

            } else {
            	
                //return to login page with error binded to request
                request.setAttribute("error", "Invalid Login Credentials");
                request.getRequestDispatcher("/user/main.jsp").forward(request, response);
            }

            // Clean-up environment
            USER_Results.close();
            ADMIN_Results.close();

            USER_SQL_Statement.close();
            ADMIN_SQL_Statement.close();

            DB_Connnection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}