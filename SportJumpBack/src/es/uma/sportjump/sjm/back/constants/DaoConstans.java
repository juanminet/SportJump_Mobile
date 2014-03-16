package es.uma.sportjump.sjm.back.constants;

public class DaoConstans {
	
	//Application Server
	public static final String IP_HOST = "192.168.1.13";
	public static final String UNSECURE_PORT = "8080";
	public static final String SECURE_PORT = "8443";
	public static final String SERVER_NAME = "/SportJumpWEB";
	public static final String API_REST_PATH = "/rest";	
	
	public static final String SECURE_URL_BASE_PATH = "https://" + IP_HOST  + ":" + SECURE_PORT + SERVER_NAME + API_REST_PATH;
	public static final String UNSECURE_URL_BASE_PATH = "http://" + IP_HOST  + ":" + UNSECURE_PORT + SERVER_NAME + API_REST_PATH;

	public static final String LOGIN_PATH = "/user/login";
	public static final String USER_ATHLETE_DATA_PATH = "/user/athlete"; 
	
	//Shared Preferences
	public static final String  APPLICATION_PREFERENCE_NAME= "ApplicationPreference";
	public static final String APPLICATION_PREFERENCE_KEY_USER_LOGGED = "UserLogged";
	public static final String APPLICATION_PREFERENCE_KEY_USERNAME = null;
	public static final String APPLICATION_PREFERENCE_KEY_PASSWORD = null;
}
