package edu.albany.csi418;

import javax.mail.MessagingException;

public class MailSend {
	
	//set up gstmp property
	private static String host = "pop.gmail.com";
    private static String port = "995";
    private static String user = "uaonlinequizproject@gmail.com";
    private static String pass = "csi-2019";
    
    //set email by submitted form information
    public static void newMail(String recipient, String defaultPW) {
    	
    	try {
            MailUtils.sendEmail(host, port, user, pass, recipient, "New Noticon",
            		defaultPW);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
