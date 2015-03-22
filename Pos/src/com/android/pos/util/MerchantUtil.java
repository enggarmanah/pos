package com.android.pos.util;

import com.android.pos.Config;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;

public class MerchantUtil {
	
	private static MerchantDaoService merchantDaoService = new MerchantDaoService();
	private static Merchant mMerchant;
	
	public static Merchant getMerchant() {
		
		if (mMerchant == null && Config.isDevelopment() && !UserUtil.isRoot()) {
			mMerchant = merchantDaoService.getMerchant(Long.valueOf(1));
		}
		
		return mMerchant;
	}
	
	public static Long getMerchantId() {
		
		return getMerchant() != null ? getMerchant().getId() : null;
	}
	
	public static void setMerchant(Merchant merchant) {
		
		mMerchant = merchant;
	}
}
