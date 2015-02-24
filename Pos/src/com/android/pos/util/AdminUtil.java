package com.android.pos.util;

public class AdminUtil {
	
	private static boolean mIsAdmin = false;
	
	public static boolean isAdmin() {
		
		return mIsAdmin;
	}
	
	public static void setAdmin(boolean isAdmin) {
		
		mIsAdmin = isAdmin;
	}
}
