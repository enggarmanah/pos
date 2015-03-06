package com.android.pos.util;

import com.android.pos.dao.Merchant;
import com.android.pos.service.MerchantDaoService;

public class MerchantUtil {
	
	private static MerchantDaoService merchantDaoService = new MerchantDaoService();
	private static Merchant mMerchant;
	
	public static Merchant getMerchant() {
		
		if (mMerchant == null) {
			mMerchant = merchantDaoService.getMerchant(Long.valueOf(1));
		}
		
		return mMerchant;
	}
	
	public static Long getMerchantId() {
		
		return getMerchant().getId();
	}
	
	public static void setMerchant(Merchant merchant) {
		
		mMerchant = merchant;
	}
}
