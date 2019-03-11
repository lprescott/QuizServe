package edu.albany.csi418;

/*
 * This enumeration contains some commonly referenced values when connecting to the 
 * relavent MySQL database. This also allows for ease of change when pushing to the cloud
 * if need be.
 */
public enum DatabaseLogin {
	JDBC_HOST("jdbc:mysql://localhost:3306/QUIZ"),
	USERNAME("eclipse"),
	PASSWORD("csi2019");
	
	private final String value;
	
	private DatabaseLogin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
