package edu.albany.csi418;

import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {
	
	//Team's new GMail
	final static String teamEmail = "uaonlinequizproject@gmail.com";
	final static String teamPassword = "csi-2019";

	/**
	 * Sends a email pertaining to a newly created user w/ their info.
	 * @param userEmail
	 * @param userPassword
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void newUserMail(String userEmail, String userPassword) throws AddressException, MessagingException {
		
        // sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(teamEmail, teamPassword);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        try {
            // creates a new e-mail msg
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(teamEmail));
			msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(userEmail));
			msg.setSubject("Newly Created Account Information");
			msg.setSentDate(new Date());
			msg.setText("Dear " + userEmail + ","
				+ "\n\nYour password is: \"" + userPassword + "\""
				+ "\n\nSincerely,"
				+ "\nICSI-418 Team");

	        //Connect to smtp and send email
			Transport.send(msg);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
