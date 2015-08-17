package com.android.pos.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.pos.Config;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.ProgressDlgFragment;
import com.android.pos.cashier.CashierActivity;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.dao.User;
import com.android.pos.data.merchant.MerchantMgtActivity;
import com.android.pos.data.user.UserMgtActivity;
import com.android.pos.model.FormFieldBean;
import com.android.pos.report.transaction.TransactionActivity;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

public class MerchantLoginActivity extends BaseAuthActivity implements LoginListener {

	Button mLoginBtn;
	EditText mLoginIdText;
	EditText mPasswordText;
	
	TextView mSignUpText;
	TextView mForgotPasswordText;
	
	Merchant mMerchant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.auth_merchant_login_activity);
		
		DbUtil.initDb(this);
		
		CodeUtil.initCodes(this);
		
		mMerchantDaoService = new MerchantDaoService();
		
		mHttpAsyncManager = new HttpAsyncManager(context);
		
		mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag("progressDialogTag");
		
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDlgFragment();
		}
		
		mLoginIdText = (EditText) findViewById(R.id.loginIdText);
		mPasswordText = (EditText) findViewById(R.id.passwordText);
		
		mSignUpText = (TextView) findViewById(R.id.signUpText);
		mSignUpText.setOnClickListener(getSignUpTextOnClickListener());
		
		mForgotPasswordText = (TextView) findViewById(R.id.forgotPasswordText);
		mForgotPasswordText.setOnClickListener(getForgotPasswordTextOnClickListener());
				
		registerField(mLoginIdText);
		registerField(mPasswordText);
		
		registerMandatoryField(new FormFieldBean(mLoginIdText, R.string.field_login_id));
		registerMandatoryField(new FormFieldBean(mPasswordText, R.string.field_password));
		
		highlightMandatoryFields();
		
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(getLoginBtnOnClickListener());
		
		isMerchantAuthenticated();
    }
	
	@Override
	protected void onNewIntent(Intent intent) {
		
		super.onNewIntent(intent);
		
		boolean isLogout = intent.getBooleanExtra("logout", false);
		
		if (isLogout) {
			
			DbUtil.switchDb(this, null);
			
			MerchantUtil.recreateDao();
			
			mMerchantDaoService = new MerchantDaoService();
			
			mLoginIdText.setText(Constant.EMPTY_STRING);
			mPasswordText.setText(Constant.EMPTY_STRING);
			
			if (MerchantUtil.getMerchant() != null) {
				mMerchantDaoService.logoutMerchant(MerchantUtil.getMerchant());
			}
			
			MerchantUtil.setMerchant(null);
		
		} else {
		
			DbUtil.initDb(this);
			
			if (!Config.isDebug() && UserUtil.getUser() != null) {
				
				if (UserUtil.isRoot()) {
					Intent newIntent = new Intent(context, MerchantMgtActivity.class);
					startActivity(newIntent);
					
				} else if (UserUtil.isMerchant()) {
					Intent newIntent = new Intent(context, UserMgtActivity.class);
					startActivity(newIntent);
					
				} else if (UserUtil.isAdmin()) {
					Intent newIntent = new Intent(context, TransactionActivity.class);
					startActivity(newIntent);
					
				} else if (UserUtil.isCashier()) {
					Intent newIntent = new Intent(context, CashierActivity.class);
					startActivity(newIntent);
					
				} else if (UserUtil.isWaitress()) {
					Intent newIntent = new Intent(context, CashierActivity.class);
					startActivity(newIntent);
				}
				
			} else if (!Config.isDebug() && MerchantUtil.getMerchant() != null) {
				
				Intent newIntent = new Intent(context, UserLoginActivity.class);
				startActivity(newIntent);
			}
			
			mMerchantDaoService = new MerchantDaoService();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_item_exit:
			
			finish();
			System.exit(0);
			
			return true;

		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
	
	private boolean isMerchantAuthenticated() {
		
		mMerchant = mMerchantDaoService.getActiveMerchant();
		
		if (mMerchant != null) {
			
			DbUtil.switchDb(this, mMerchant.getId());
			
			MerchantUtil.setMerchant(mMerchant);
			Intent intent = new Intent(context, UserLoginActivity.class);
			startActivity(intent);
			
			return true;
		
		} else {
			
			return false;
		}
	}
	
	private View.OnClickListener getLoginBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (!isValidated()) {
					return;
				}
				
				String loginId = mLoginIdText.getText().toString();
				String password = mPasswordText.getText().toString();
				
				if (Constant.ROOT.equals(loginId)) {
					
					if (mProgressDialog.isAdded()) {
						return;
					}
					
					mProgressDialog.show(getFragmentManager(), "progressDialogTag");
						
					mHttpAsyncManager = new HttpAsyncManager(context);
					mHttpAsyncManager.validateUser(loginId, password);
					
				} else {
				
					mMerchant = mMerchantDaoService.getMerchantByLoginId(loginId);
					
					if (mMerchant != null) {
						
						DbUtil.switchDb(getApplicationContext(), mMerchant.getId());
						
						mMerchantDaoService = new MerchantDaoService();
						mMerchant = mMerchantDaoService.validateMerchant(loginId, password);
						
						if (mMerchant != null) {
							
							DbUtil.switchDb(getApplicationContext(), null);
							mMerchantDaoService = new MerchantDaoService();
							
							mMerchant.setIsLogin(true);
							mMerchantDaoService.updateMerchant(mMerchant);
							
							DbUtil.switchDb(getApplicationContext(), mMerchant.getId());
							
							MerchantUtil.setMerchant(mMerchant);
							Intent intent = new Intent(context, UserLoginActivity.class);
							startActivity(intent);
							
						} else {
							
							if (mMerchantDaoService.isEmptyDb()) {
								
								DbUtil.switchDb(getApplicationContext(), null);
								mMerchantDaoService = new MerchantDaoService();
								
								MerchantUtil.recreateDao();
								
								if (mProgressDialog.isAdded()) {
									return;
								}
								
								mProgressDialog.show(getFragmentManager(), "progressDialogTag");
								
								mHttpAsyncManager = new HttpAsyncManager(context);
								mHttpAsyncManager.validateMerchant(loginId, password);
							
							} else {
								mPasswordText.setText(Constant.EMPTY_STRING);
								showFailedAuthenticationMessage();
							}
						}
						
					} else {
						
						if (mProgressDialog.isAdded()) {
							return;
						}
						
						mProgressDialog.show(getFragmentManager(), "progressDialogTag");
						
						mHttpAsyncManager = new HttpAsyncManager(context);
						mHttpAsyncManager.validateMerchant(loginId, password);
					}
				}
			}
		};
	}
		
	private View.OnClickListener getSignUpTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, RegistrationActivity.class);
				startActivity(intent);
			}
		};
	}
	
	private View.OnClickListener getForgotPasswordTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, ForgotPasswordActivity.class);
				startActivity(intent);
			}
		};
	}
	
	@Override
	public void onMerchantValidated(Merchant merchant) {
		
		if (merchant != null) {		
			
			mMerchant = merchant;
			
			MerchantUtil.setMerchant(mMerchant);
			
			mMerchant.setIsLogin(true);
			
			if (mMerchantDaoService.getMerchant(mMerchant.getId()) != null) {
				mMerchantDaoService.updateMerchant(mMerchant);
			} else {
				mMerchantDaoService.addMerchant(mMerchant);
			}
			
			DbUtil.switchDb(getApplicationContext(), mMerchant.getId());
		
			mHttpAsyncManager = new HttpAsyncManager(context);
			mHttpAsyncManager.syncMerchant();
			
		} else {
			
			mProgressDialog.dismiss();
			showFailedAuthenticationMessage();
		}
	}
	
	@Override
	public void onUserValidated(User user) {
		
		if (user != null) {		
			
			user = new User();
			
			user.setUserId(Constant.ROOT);
			user.setName(Constant.ROOT);
			
			UserUtil.setMerchant(false);
			UserUtil.setUser(user);
			
			DbUtil.switchDb(this, null);
			
			MerchantUtil.recreateDao();
			
			if (MerchantUtil.getMerchant() != null) {
				
				mMerchantDaoService = new MerchantDaoService();
				mMerchantDaoService.logoutMerchant(MerchantUtil.getMerchant());
				MerchantUtil.setMerchant(null);
			}
			
			mHttpAsyncManager = new HttpAsyncManager(context);
			mHttpAsyncManager.syncMerchants();
			
		} else {
			
			mProgressDialog.dismiss();
			showFailedAuthenticationMessage();
		}
	}
	
	@Override
	public void onMerchantsUpdated() {
		
		if (UserUtil.isRoot()) {
			
			Intent intent = new Intent(context, MerchantMgtActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	protected void onSyncCompleted() {
		
		// case when the login is root
		if (UserUtil.isRoot()) {
			return;
		}
		
		mMerchantDaoService = new MerchantDaoService();
		mMerchant = mMerchantDaoService.getMerchant(mMerchant.getId());
		mMerchant.setIsLogin(true);
		
		mMerchantDaoService.updateMerchant(mMerchant);
		
		Intent intent = new Intent(context, UserLoginActivity.class);
		startActivity(intent);
	}
	
	private void showFailedAuthenticationMessage() {
		
		NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_merchant_authentication_error));
	}
	
	@Override
	public void onBackPressed() {
	}
}
