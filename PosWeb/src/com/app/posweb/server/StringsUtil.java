package com.app.posweb.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StringsUtil {
	
private static Properties strings;
private static Properties strings_id;
    
	static {
		
		loadProperties();
	}

	public static void loadProperties()  {
		
		InputStream input1 = null;
		InputStream input2 = null;
		
		try {
			
			input1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("string.properties");
			
			strings = new Properties();
			strings.load(input1);
			
			input2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("string-id.properties");
			
			strings_id = new Properties();
			strings_id.load(input2);

		} catch (IOException ex) {
			ex.printStackTrace();
		
		} finally {
			
			if (input1 != null) {
				try {
					input1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (input2 != null) {
				try {
					input2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getString(String lang, String key) {
		
		if ("id".equals(lang)) {
			return strings_id.getProperty(key);
		} else {
			return strings.getProperty(key);
		}
	}
}
