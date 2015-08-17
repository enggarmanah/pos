package com.android.pos.auth;

import com.android.pos.dao.Merchant;

public interface RegistrationListener {
	
	public void onMerchantRegistered(Merchant merchant);
}
