package edu.albany.csi418.session;

/*
 * This enumeration contains some commonly referenced values when connecting to the 
 * relevant MySQL database. This also allows for ease of change when pushing to the cloud
 * if need be.
 */
public enum LoginEnum {
	hostname("jdbc:mysql://mysql-quiz-instance1.cppjgualbsxk.us-east-1.rds.amazonaws.com:3306/QUIZ?&serverTimezone=US/Eastern"),
	username("root"),
	password("icsi2019");
	
	private final String value;
	
	private LoginEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
