package edu.albany.csi418;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		invalidateOldSession(request);
		response.sendRedirect("login.jsp");
	}
	

    /**
     * This function gets the current http session and invalidates if it is not null.
     * @param request the current http session
     */
    public static void invalidateOldSession(HttpServletRequest request) {

        //get old session
        HttpSession oldSession = request.getSession(false);

        //invalidate
        if (oldSession != null) {
            oldSession.invalidate();
        }
    }

}
