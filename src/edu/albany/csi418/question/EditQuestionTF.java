package edu.albany.csi418.question;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import edu.albany.csi418.session.LoginEnum;
import edu.albany.csi418.FileUtils;

/**
 * Servlet implementation class CreateQuestionTF
 */
@WebServlet("/EditQuestionTF")
@MultipartConfig
public class EditQuestionTF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditQuestionTF() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get text, category and correct answer num
		String question = request.getParameter("q_text");
		String category = request.getParameter("q_category");

		// Check if true or false check box is selected
		int tf;
		if (request.getParameter("true_cb") == null) {
			// false
			tf = 0;
		} else {
			// true
			tf = 1;
		}

		String action = request.getParameter("submit");

		if (action.equals("DELETE")) {

			try {

				// Load the Connector/J
				Class.forName("com.mysql.cj.jdbc.Driver");

				// Open a connection
				Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

				// Execute SQL query
				Statement DELETE_QUESTION_SQL_Statement = DB_Connection.createStatement();
				String DELETE_QUESTION_SQL_Query = "DELETE FROM QUESTION WHERE QUESTION_ID=" + request.getParameter("QUESTION_ID") + ";";
				DELETE_QUESTION_SQL_Statement.executeUpdate(DELETE_QUESTION_SQL_Query);

				// Clean-up environment
				DELETE_QUESTION_SQL_Statement.close();
				DB_Connection.close();

				response.sendRedirect("admin/question/question_management.jsp?message_tf=Question%20Deleted%20Successfully");
				return;

			} catch (Exception e) {

				// System.out.println(e);
				response.sendRedirect("admin/question/edit_question_tf.jsp?success=false&error=Error%20Deleting%20Record");
				return;
			}

		} else if (action.equals("UPDATE")) {

			// New part object containing image, and vars
			Part filePart = request.getPart("q_image");
			String fileName = "";
			InputStream fileContent = null;

			// Get name and content
			if (filePart != null && !(filePart.getSize() == 0)) {
				fileName = FileUtils.extractFileName(filePart);
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				fileContent = filePart.getInputStream();
			}

			int QUESTION_ID = Integer.parseInt(request.getParameter("QUESTION_ID"));

			try {
				// Load the Connector/J
				Class.forName("com.mysql.cj.jdbc.Driver");

				// If a file was attached
				if (filePart != null && !(filePart.getSize() == 0)) {

					String appPath = request.getServletContext().getRealPath("");
					String savePath = appPath + "uploads";

					// creates the save directory if it does not exists
					File fileSaveDir = new File(savePath);
					if (!fileSaveDir.exists()) {
						fileSaveDir.mkdir();
					}

					// Get new path
					File fileToSave = new File(savePath + File.separator + fileName);
					Integer x = 0;
					String temp = fileName.substring(0, fileName.lastIndexOf("."));
					String ext = fileName.substring(fileName.lastIndexOf("."));

					// Adds a number if the same image exists
					while (fileToSave.exists()) {

						x++;
						String tempFileName = temp + x.toString() + ext;
						fileToSave = new File(savePath + File.separator + tempFileName);
					}
					if (x != 0) {

						fileName = temp + x.toString() + ext;
					}

					// Save file
					Files.copy(fileContent, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

					// clean up
					fileSaveDir.delete();
					// fileToSave.delete();
					filePart.delete();
					fileContent.close();
				}

				// Open a connection
				Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

				Statement UPDATE_QUESTION_Statement;
				// Update into QUESTION Table
				if (fileName.equals("")) {
					
					UPDATE_QUESTION_Statement = DB_Connection.createStatement();
					String UPDATE_QUESTION_STRING = "UPDATE QUESTION SET TEXT = '" + question + "', CATEGORY = '" + category + "', TF_IS_TRUE = " + tf + " WHERE QUESTION_ID = " + QUESTION_ID + ";";
					UPDATE_QUESTION_Statement.executeUpdate(UPDATE_QUESTION_STRING);
				} else {
					
					UPDATE_QUESTION_Statement = DB_Connection.createStatement();
					String UPDATE_QUESTION_STRING = "UPDATE QUESTION SET TEXT = '" + question + "', CATEGORY = '" + category + "', TF_IS_TRUE = " + tf + ", IMAGE_NAME = '" + fileName + "' WHERE QUESTION_ID = " + QUESTION_ID + ";";
					UPDATE_QUESTION_Statement.executeUpdate(UPDATE_QUESTION_STRING);
				}
				// Clean-up environment
				UPDATE_QUESTION_Statement.close();

				DB_Connection.close();

				// success
				response.sendRedirect("admin/question/question_management.jsp?message_tf=Question%20Updated%20Successfully");
				return;

			} catch (SQLException s) {

				response.sendRedirect("admin/question/edit_question_tf.jsp?success=false&error=SQL%20Exception");
				return;

			} catch (Exception e) {

				// e.printStackTrace();
				response.sendRedirect("admin/question/edit_question_tf.jsp?success=false&error=Error%20Updating%20Record");
				return;
			}

		} else {
			response.sendRedirect("admin/question/edit_question_tf.jsp?success=false&error=Unknown%20Error");
			return;
		}

	}

}
