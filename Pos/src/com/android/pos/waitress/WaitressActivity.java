package com.android.pos.waitress;

import com.android.pos.Constant;
import com.android.pos.cashier.CashierActivity;

public class WaitressActivity extends CashierActivity {
	
	@Override
	public void onStart() {
		
		super.onStart();

		setSelectedMenu(Constant.MENU_WAITRESS);
	}
}
