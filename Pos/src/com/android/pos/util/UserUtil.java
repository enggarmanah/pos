package com.android.pos.util;

import com.android.pos.dao.User;
import com.android.pos.service.UserDaoService;

public class UserUtil {
	
	private static UserDaoService mUserDaoService = new UserDaoService();
	private static User mUser;
	
	public static User getUser() {
		
		if (mUser == null) {
			mUserDaoService.getUser(Long.valueOf(1));
		}
		
		return mUser;
	}
	
	public static void setUser(User user) {
		
		mUser = user;
	}
}
