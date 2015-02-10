package com.android.pos.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.android.pos.R;
import com.android.pos.cashier.CashierActivity;
import com.android.pos.reference.discount.DiscountMgtActivity;
import com.android.pos.sync.SyncProgressDlgFragment;
import com.android.pos.sync.SyncListener;
import com.android.pos.sync.SyncManager;
import com.android.pos.util.DbUtil;

public class MerchantLoginActivity extends Activity implements SyncListener {

	final Context context = this;
	private SyncManager syncManager;
	
	private SyncProgressDlgFragment mProgressDialog;
	
	Button mLoginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.auth_merchant_login_activity);
		
		DbUtil.initDb(this);
		
		syncManager = new SyncManager(this);
		
		mProgressDialog = (SyncProgressDlgFragment) getFragmentManager().findFragmentByTag("progressDialogTag");
		
		if (mProgressDialog == null) {
			mProgressDialog = new SyncProgressDlgFragment();
		}
		
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, CashierActivity.class);
				startActivity(intent);
			}
		});
    }
	
	public void setSyncProgress(int progress) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mProgressDialog.dismiss();
					}
				}, 500);
			}
		}
	}
	
	public void setSyncMessage(String message) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setMessage(message);
		}
	}
}
