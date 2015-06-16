package com.android.pos.waitress;

import com.android.pos.R;
import com.android.pos.cashier.CashierActivity;

public class WaitressActivity extends CashierActivity {
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_waitress));
		setSelectedMenu(getString(R.string.menu_waitress));
	}
}
