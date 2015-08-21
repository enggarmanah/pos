package com.android.pos.auth;

import com.android.pos.dao.Merchant;

public interface ResendActivationCodeListener {
	
	public void onCodeSent(Merchant merchant);
}
