package com.android.pos.util;

public class AdminUtil {
	
	private static boolean mIsAdmin = false;
	private static boolean mIsRoot = false;
	
	public static boolean isAdmin() {
		
		return mIsAdmin;
	}
	
	public static void setAdmin(boolean isAdmin) {
		
		mIsAdmin = isAdmin;
	}

	public static boolean isRoot() {
		return mIsRoot;
	}

	public static void setRoot(boolean isRoot) {
		mIsRoot = isRoot;
	}
}
