package com.android.pos;

import com.android.pos.dao.User;
import com.android.pos.dao.UserDao;

public class UserUtil {
	
	private static UserDao userDao = DbHelper.getSession().getUserDao();
	
	public static User getUser() {
		
		return userDao.load(Long.valueOf(1));
	}
	
	public static int getTaxPercentage() {
		
		return 10;
	}
	
	public static int getServiceChargePercentage() {
		
		return 5;
	}
}
