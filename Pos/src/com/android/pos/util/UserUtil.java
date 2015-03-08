package com.android.pos.util;

import com.android.pos.Constant;
import com.android.pos.dao.User;
import com.android.pos.service.UserDaoService;

public class UserUtil {
	
	private static UserDaoService mUserDaoService = new UserDaoService();
	
	private static User mUser;
	private static boolean mIsMerchant = false;
	
	public static User getUser() {
		
		if (mUser == null) {
			
			DbUtil.switchDb(MerchantUtil.getMerchantId());
			mUserDaoService = new UserDaoService();
			mUser = mUserDaoService.getUser(Long.valueOf(2));
		}
		
		return mUser;
	}
	
	public static void setUser(User user) {
		
		mUser = user;
	}
	
	public static boolean isMerchant() {
		
		return mIsMerchant;
	}
	
	public static void setMerchant(boolean isMerchant) {
		
		mIsMerchant = isMerchant;
	}
	
	public static boolean isCashier() {
		
		return Constant.USER_ROLE_CASHIER.equals(getUser().getRole());
	}
	
	public static boolean isAdmin() {
		
		return Constant.USER_ROLE_ADMIN.equals(getUser().getRole());
	}
	
	public static boolean isRoot() {
		
		if (mUser != null && Constant.ROOT.equals(mUser.getUserId())) {
			return true;
		} else {
			return false;
		}
	}
}
