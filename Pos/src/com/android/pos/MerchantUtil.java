package com.android.pos;

import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDao;

public class MerchantUtil {
	
	private static MerchantDao merchantDao = DbHelper.getSession().getMerchantDao();
	
	public static Merchant getMerchant() {
		
		return merchantDao.load(Long.valueOf(1));
	}
}
