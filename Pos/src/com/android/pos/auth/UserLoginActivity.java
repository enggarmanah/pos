package com.android.pos.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.cashier.CashierActivity;
import com.android.pos.common.AlertDlgFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.User;
import com.android.pos.reference.DataMgtActivity;
import com.android.pos.service.MerchantDaoService;
import com.android.pos.service.UserDaoService;
import com.android.pos.util.DbUtil;
import com.android.pos.util.NotificationUtil;

public class UserLoginActivity extends Activity {

	final Context context = this;
	
	private MerchantDaoService mMerchantDaoService;
	private UserDaoService mUserDaoService;
	
	Merchant mMerchant;
	
	TextView mMerchantNameTxt;
	TextView mMerchantAddrTxt;
	
	EditText mLoginIdTxt;
	EditText mPasswordTxt;
	
	Button mLoginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.auth_user_login_activity);
		
		DbUtil.initDb(this);
		
		mMerchantNameTxt = (TextView) findViewById(R.id.merchantNameTxt);
		mMerchantAddrTxt = (TextView) findViewById(R.id.merchantAddrTxt);
		
		mLoginIdTxt = (EditText) findViewById(R.id.loginIdTxt);
		mPasswordTxt = (EditText) findViewById(R.id.passwordTxt);
		
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(getLoginBtnOnClickListener());
		
		mMerchantDaoService = new MerchantDaoService();
		mUserDaoService = new UserDaoService();
		
		mMerchant = mMerchantDaoService.getActiveMerchant();
		
		mMerchantNameTxt.setText(mMerchant.getName());
		mMerchantAddrTxt.setText(mMerchant.getAddress());
    }
	
	private View.OnClickListener getLoginBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String loginId = mLoginIdTxt.getText().toString();
				String password = mPasswordTxt.getText().toString();
				
				User user = mUserDaoService.validateUser(mMerchant.getId(), loginId, password);
				
				if (user != null) {
					
					if (Constant.USER_ROLE_CASHIER.equals(user.getRole())) {
						
						Intent intent = new Intent(context, CashierActivity.class);
						startActivity(intent);
						
					}else {
						
						Intent intent = new Intent(context, DataMgtActivity.class);
						startActivity(intent);
					}
				} else {
					
					AlertDlgFragment alertDialogFragment = NotificationUtil.getAlertDialogInstance();
	    			alertDialogFragment.show(getFragmentManager(), NotificationUtil.ALERT_DIALOG_FRAGMENT_TAG);
	    			alertDialogFragment.setAlertMessage("ID Merchant & password salah!");
				} 
			}
		};
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.user_login_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_item_exit:
			
			mMerchantDaoService.logoutMerchant(mMerchant);
			
			Intent intent = new Intent(context, MerchantLoginActivity.class);
			startActivity(intent);
			
			return true;

		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
}
