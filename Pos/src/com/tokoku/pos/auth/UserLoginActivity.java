package com.tokoku.pos.auth;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tokoku.pos.R;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.User;
import com.tokoku.pos.Constant;
import com.tokoku.pos.cashier.CashierActivity;
import com.tokoku.pos.dao.MerchantDaoService;
import com.tokoku.pos.dao.UserDaoService;
import com.tokoku.pos.data.user.UserMgtActivity;
import com.tokoku.pos.model.FormFieldBean;
import com.tokoku.pos.report.commission.CommissionActivity;
import com.tokoku.pos.report.transaction.TransactionActivity;
import com.tokoku.pos.util.CommonUtil;
import com.tokoku.pos.util.MerchantUtil;
import com.tokoku.pos.util.NotificationUtil;
import com.tokoku.pos.util.UserUtil;
import com.tokoku.pos.waitress.WaitressActivity;

public class UserLoginActivity extends BaseAuthActivity {

	final Context context = this;
	
	private MerchantDaoService mMerchantDaoService;
	private UserDaoService mUserDaoService;
	
	Merchant mMerchant;
	
	TextView mMerchantNameTxt;
	TextView mMerchantAddrTxt;
	
	EditText mLoginIdText;
	EditText mPasswordText;
	
	Button mLoginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.auth_user_login_activity);
		
		mMerchantNameTxt = (TextView) findViewById(R.id.merchantNameText);
		mMerchantAddrTxt = (TextView) findViewById(R.id.merchantAddrText);
		
		mLoginIdText = (EditText) findViewById(R.id.loginIdText);
		mPasswordText = (EditText) findViewById(R.id.passwordText);
		
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(getLoginBtnOnClickListener());
		
		registerField(mLoginIdText);
		registerField(mPasswordText);
		
		registerMandatoryField(new FormFieldBean(mLoginIdText, R.string.field_login_id));
		registerMandatoryField(new FormFieldBean(mPasswordText, R.string.field_password));
		
		highlightMandatoryFields();
		
		mMerchantDaoService = new MerchantDaoService();
		mUserDaoService = new UserDaoService();
		
		mMerchant = mMerchantDaoService.getActiveMerchant();
		
		// when the initial sync is failed, merchant data is not yet available
		
		if (mMerchant == null) {
			
			Intent intent = new Intent(context, MerchantLoginActivity.class);
			intent.putExtra("logout", true);
			startActivity(intent);
			
			MerchantUtil.setMerchant(null);
			finish();
			
			return;
		}
		
		MerchantUtil.setMerchant(mMerchant);
		
		if (Constant.STATUS_INACTIVE.equals(mMerchant.getStatus())) {
			
			Intent intent = new Intent(context, MerchantActivationActivity.class);
			startActivity(intent);
			
			return;
		}
	}
	
	public void onStart() {
		
		super.onStart();
		
		mMerchant = MerchantUtil.getMerchant();
		
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
				
				if (!isValidated()) {
					return;
				}
				
				UserUtil.setMerchant(false);
				
				String loginId = mLoginIdText.getText().toString();
				String password = mPasswordText.getText().toString();
				
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
					
					User user = mUserDaoService.validateUser(loginId, password);
					
					if (user != null) {
						
						UserUtil.setUser(user);
						
						if (Constant.USER_ROLE_CASHIER.equals(user.getRole())) {
							
							Intent intent = new Intent(context, CashierActivity.class);
							startActivity(intent);
							
						} else if (Constant.USER_ROLE_WAITRESS.equals(user.getRole())) {
							
							Intent intent = new Intent(context, WaitressActivity.class);
							startActivity(intent);
								
						} else if (Constant.USER_ROLE_EMPLOYEE.equals(user.getRole())) {
							
							Intent intent = new Intent(context, CommissionActivity.class);
							startActivity(intent);
								
						} else {
							
							Intent intent = new Intent(context, TransactionActivity.class);
							startActivity(intent);
						}
						
						finish();
						
					} else {
						
						NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_user_authentication_error));
		    			
		    			mPasswordText.setText(Constant.EMPTY_STRING);
		    			mPasswordText.requestFocus();
					}
				}
			}
		};
	}
	
	@Override
	public void onBackPressed() {
	}
}
