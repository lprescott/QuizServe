package edu.albany.csi418;

import javax.servlet.http.Part;

public class FileUtils {
	
	/**
	 * this function extract the file name, given a part object
	 * @param part the object to be parsed
	 * @return
	 */
	public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
