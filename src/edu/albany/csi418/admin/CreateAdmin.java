package edu.albany.csi418.admin;

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

import edu.albany.csi418.MailUtils;
import edu.albany.csi418.session.LoginEnum;

/**
 * Servlet implementation class CreateAdmin
 * 
 */
@WebServlet("/CreateAdmin")
public class CreateAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAdmin() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password_confirmation = request.getParameter("password-confirm");

        //Check if passwords match	
        if (!password.equals(password_confirmation)) {
            response.sendRedirect("admin/create_admin.jsp?success=false&error=Passwords%20Do%20Not%20Match");
            return;
        }

        try {
            //Load the Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

            // Execute SQL queries
            Statement USER_SQL_Statement = DB_Connection.createStatement();
            String USER_SQL_Query = "SELECT EMAIL FROM USERS";

            Statement ADMIN_SQL_Statement = DB_Connection.createStatement();
            String ADMIN_SQL_Query = "SELECT EMAIL FROM ADMINISTRATOR";

            ResultSet USER_Results = USER_SQL_Statement.executeQuery(USER_SQL_Query);
            ResultSet ADMIN_Results = ADMIN_SQL_Statement.executeQuery(ADMIN_SQL_Query);

            //Check if user exists with given email
            while (USER_Results.next()) {
                if (email.equals(USER_Results.getString("EMAIL"))) {

                    // Clean-up environment
                    USER_Results.close();
                    ADMIN_Results.close();

                    USER_SQL_Statement.close();
                    ADMIN_SQL_Statement.close();

                    DB_Connection.close();

                    response.sendRedirect("admin/create_admin.jsp?success=false&error=Existing%20User%20Email");
                    return;
                }
            }

            //Check if admin exists with given email
            while (ADMIN_Results.next()) {
                if (email.equals(ADMIN_Results.getString("EMAIL"))) {

                    // Clean-up environment
                    USER_Results.close();
                    ADMIN_Results.close();

                    USER_SQL_Statement.close();
                    ADMIN_SQL_Statement.close();

                    DB_Connection.close();

                    response.sendRedirect("admin/create_admin.jsp?success=false&error=Existing%20Admin%20Email");
                    return;
                }
            }

            //Insert into DB
            Statement ADD_ADMIN_Statement = DB_Connection.createStatement();
            String ADD_ADMIN_STRING = "INSERT INTO ADMINISTRATOR (EMAIL, PASSWORD) VALUES ('" + email + "', '" + password + "')";
            ADD_ADMIN_Statement.executeUpdate(ADD_ADMIN_STRING);

            //Email User Information
            MailUtils.newUserMail(email, password, 1);

            // Clean-up environment
            USER_Results.close();
            ADMIN_Results.close();

            USER_SQL_Statement.close();
            ADMIN_SQL_Statement.close();
            ADD_ADMIN_Statement.close();

            DB_Connection.close();

            response.sendRedirect("admin/create_admin.jsp?success=true");
        } catch (Exception e) {
            
        	//System.out.println(e);
            response.sendRedirect("admin/create_admin.jsp?success=false&error=Unknown%20Error");
            return;
        }
    }
}
