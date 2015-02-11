package com.android.pos.auth;

import com.android.pos.dao.Merchant;

public interface LoginListener {
	
	public void onMerchantValidated(Merchant merchant);
}
