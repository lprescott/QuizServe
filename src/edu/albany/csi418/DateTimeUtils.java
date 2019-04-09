package edu.albany.csi418;

public class DateTimeUtils {
	// '2019-04-08 22:53:36.0' => '04/08/2019 22:53'
	public static String formatDateTime(String input) {
		String year, month, day, hour, minute;

		String[] tokens = input.split("[^\\d]");
		year = tokens[0];
		month = tokens[1];
		day = tokens[2];
		hour = tokens[3];
		minute = tokens[4];

		return month + "/" + day + "/" + year + " " + hour + ":" + minute;
	}
}
