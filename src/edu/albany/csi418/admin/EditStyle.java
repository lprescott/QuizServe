package edu.albany.csi418.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.File;
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
 * Servlet implementation class EditStyle
 */
@WebServlet("/EditStyle")
@MultipartConfig
public class EditStyle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditStyle() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// New part object containing image, and vars
		Part filePart = request.getPart("headerimg");
		String fileName = "";
		String fileExt = "";
		InputStream fileContent = null;

		// Get name and content
		if (filePart != null && !(filePart.getSize() == 0)) {
			fileName = FileUtils.extractFileName(filePart);
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			fileExt = fileName.substring(fileName.lastIndexOf("."));
			fileContent = filePart.getInputStream();
		}

		// Get text, category and correct answer num
		String header = request.getParameter("header");
		String footer = request.getParameter("footer");
		
		try {
			// Load the Connector/J
			Class.forName("com.mysql.cj.jdbc.Driver");

			// If a file was attached
			if (filePart != null && !(filePart.getSize() == 0)) {

				String appPath = request.getServletContext().getRealPath("");
				String savePath = appPath + "headerimg";

				// creates the save directory if it does not exists
				File fileSaveDir = new File(savePath);
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdir();
				}

				// Get new path
				File fileToSave = new File(savePath + File.separator + fileName);
				Integer x = 0;
				String temp = fileName.substring(0, fileName.lastIndexOf("."));

				// Adds a number if the same image exists
				while (fileToSave.exists()) {

					x++;
					String tempFileName = temp + x.toString() + fileExt;
					fileToSave = new File(savePath + File.separator + tempFileName);
				}
				if (x != 0) {

					fileName = temp + x.toString() + fileExt;
				}
				Files.copy(fileContent, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

				// clean up
				fileSaveDir.delete();
				// fileToSave.delete();
				filePart.delete();
				fileContent.close();
			}

			// Open a connection
			Connection DB_Connection = DriverManager.getConnection(LoginEnum.hostname.getValue(), LoginEnum.username.getValue(), LoginEnum.password.getValue());

			Statement ADMIN_Statement;
			// Insert into ADMIN Table
			
				ADMIN_Statement = DB_Connection.createStatement();
				String ADMIN_STRING = "UPDATE ADMINISTRATOR SET HEADER = '"+ header + "', FOOTER = '"+ footer + "', FILENAME = '" + fileName + "' WHERE ADMIN_ID = '" + request.getSession().getAttribute("id") + "';";
				ADMIN_Statement.executeUpdate(ADMIN_STRING);
			
			ADMIN_Statement.close();
			DB_Connection.close();

			// success
			response.sendRedirect("admin/WebsiteStyle.jsp?success=true");

		} catch (SQLException s) {
			System.out.println(s);
			response.sendRedirect("admin/WebsiteStyle.jsp?success=false&error=SQL%20Exception");
			return;

		} catch (Exception e) {

			 System.out.println(e);
			// e.printStackTrace();
			response.sendRedirect("admin/WebsiteStyle.jsp?success=false&error=Unknown%20Error");
			return;
		}
	}
}