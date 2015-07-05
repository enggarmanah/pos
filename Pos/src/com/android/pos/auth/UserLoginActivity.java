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
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.dao.User;
import com.android.pos.dao.UserDaoService;
import com.android.pos.data.user.UserMgtActivity;
import com.android.pos.report.transaction.TransactionActivity;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;
import com.android.pos.waitress.WaitressActivity;

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
		
		mMerchantNameTxt = (TextView) findViewById(R.id.merchantNameText);
		mMerchantAddrTxt = (TextView) findViewById(R.id.merchantAddrText);
		
		mLoginIdTxt = (EditText) findViewById(R.id.loginIdText);
		mPasswordTxt = (EditText) findViewById(R.id.passwordText);
		
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(getLoginBtnOnClickListener());
		
		mMerchantDaoService = new MerchantDaoService();
		mUserDaoService = new UserDaoService();
		
		mMerchant = mMerchantDaoService.getActiveMerchant();
		
		// when the initial sync is failed, merchant data is not yet available
		
		if (mMerchant == null) {
			
			Intent intent = new Intent(context, MerchantLoginActivity.class);
			intent.putExtra("logout", true);
			startActivity(intent);
			
			return;
		}
		
		MerchantUtil.setMerchant(mMerchant);
		
		String name = mMerchant.getName();
		String contact = mMerchant.getAddress();
		
		if (!CommonUtil.isEmpty(mMerchant.getTelephone())) {
			contact = contact + "\n" + getString(R.string.login_telephone) + " " + mMerchant.getTelephone();
		}
		
		mMerchantNameTxt.setText(name);
		mMerchantAddrTxt.setText(contact);
    }
	
	private View.OnClickListener getLoginBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				UserUtil.setMerchant(false);
				
				String loginId = mLoginIdTxt.getText().toString();
				String password = mPasswordTxt.getText().toString();
				
				Merchant merchant = mMerchantDaoService.validateMerchant(loginId, password);
				
				if (merchant != null) {
					
					User user = new User();
					
					user.setUserId(merchant.getLoginId());
					user.setName(merchant.getName());
					
					UserUtil.setUser(user);
					UserUtil.setMerchant(true);
					
					Intent intent = new Intent(context, UserMgtActivity.class);
					startActivity(intent);
					
					finish();
					
				} else {
					
					//Tracker t = CommonUtil.getTracker();
					//t.send(new HitBuilders.EventBuilder().setCategory("CAT1").setAction("ACTION1").setLabel("LABEL1").build());
					
					User user = mUserDaoService.validateUser(mMerchant.getId(), loginId, password);
					
					if (user != null) {
						
						UserUtil.setUser(user);
						
						if (Constant.USER_ROLE_CASHIER.equals(user.getRole())) {
							
							Intent intent = new Intent(context, CashierActivity.class);
							startActivity(intent);
							
						} else if (Constant.USER_ROLE_WAITRESS.equals(user.getRole())) {
							
							Intent intent = new Intent(context, WaitressActivity.class);
							startActivity(intent);
								
						} else {
							
							Intent intent = new Intent(context, TransactionActivity.class);
							startActivity(intent);
						}
						
						finish();
						
					} else {
						
						NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_user_authentication_error));
		    			
		    			mPasswordTxt.setText(Constant.EMPTY_STRING);
		    			mPasswordTxt.requestFocus();
					}
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
			
			Intent intent = new Intent(context, MerchantLoginActivity.class);
			intent.putExtra("logout", true);
			startActivity(intent);
			
			return true;

		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
	}
}
