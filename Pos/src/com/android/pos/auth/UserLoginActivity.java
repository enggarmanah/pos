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
import com.android.pos.report.transaction.TransactionActivity;
import com.android.pos.service.MerchantDaoService;
import com.android.pos.service.UserDaoService;
import com.android.pos.user.UserMgtActivity;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

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
		
		String name = mMerchant.getName();
		String contact = mMerchant.getAddress();
		
		if (!CommonUtil.isEmpty(mMerchant.getTelephone())) {
			contact = contact + "\nTelp. " + mMerchant.getTelephone();
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
					
					mLoginIdTxt.setText(Constant.EMPTY_STRING);
					mPasswordTxt.setText(Constant.EMPTY_STRING);
					
					UserUtil.setMerchant(true);
					
					Intent intent = new Intent(context, UserMgtActivity.class);
					startActivity(intent);
					
				} else {
					
					User user = mUserDaoService.validateUser(mMerchant.getId(), loginId, password);
					
					if (user != null) {
						
						mLoginIdTxt.setText(Constant.EMPTY_STRING);
						mPasswordTxt.setText(Constant.EMPTY_STRING);
						
						UserUtil.setUser(user);
						
						if (Constant.USER_ROLE_CASHIER.equals(user.getRole())) {
							
							Intent intent = new Intent(context, CashierActivity.class);
							startActivity(intent);
							
						}else {
							
							Intent intent = new Intent(context, TransactionActivity.class);
							startActivity(intent);
						}
					} else {
						
						AlertDlgFragment alertDialogFragment = NotificationUtil.getAlertDialogInstance();
		    			alertDialogFragment.show(getFragmentManager(), NotificationUtil.ALERT_DIALOG_FRAGMENT_TAG);
		    			alertDialogFragment.setAlertMessage("ID Pengguna & password salah!");
		    			
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
			
			mMerchantDaoService.logoutMerchant(mMerchant);
			
			Intent intent = new Intent(context, MerchantLoginActivity.class);
			startActivity(intent);
			
			return true;

		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
}
