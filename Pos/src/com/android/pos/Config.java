package com.android.pos;

public class Config {
	
	public static String SERVER_URL = "http://192.168.0.102:8888";
	//public static String SERVER_URL = "http://20.194.21.242:8888";
	//public static String SERVER_URL = "http://192.168.1.192:8888";
	
	//public static String SERVER_URL = "http://pos-tokoku.appspot.com";
	
	public static boolean isDevelopment() {
		
		return !SERVER_URL.equals("http://pos-tokoku.appspot.com");
	}
}
