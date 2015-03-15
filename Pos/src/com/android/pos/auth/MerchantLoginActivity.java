package com.android.pos.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncListener;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.HttpAsyncProgressDlgFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.dao.User;
import com.android.pos.data.merchant.MerchantMgtActivity;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

public class MerchantLoginActivity extends Activity implements HttpAsyncListener, LoginListener {

	final Context context = this;
	private HttpAsyncManager mHttpAsyncManager;
	
	private static HttpAsyncProgressDlgFragment mProgressDialog;
	private MerchantDaoService mMerchantDaoService;
	
	Button mLoginBtn;
	EditText mLoginIdTxt;
	EditText mPasswordTxt;
	
	Merchant mMerchant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.auth_merchant_login_activity);
		
		Intent intent = getIntent();
		boolean isLogout = intent.getBooleanExtra("logout", false);
		
		if (isLogout) {
			
			DbUtil.switchDb(null);
			
			mMerchantDaoService = new MerchantDaoService();
			mMerchantDaoService.logoutMerchant(MerchantUtil.getMerchant());
		
		} else {
		
			DbUtil.initDb(this);
			
			mMerchantDaoService = new MerchantDaoService();
		}
		
		if (isMerchantAuthenticated()) {
			return;
		}
		
		mHttpAsyncManager = new HttpAsyncManager(context);
		
		mProgressDialog = (HttpAsyncProgressDlgFragment) getFragmentManager().findFragmentByTag("progressDialogTag");
		
		if (mProgressDialog == null) {
			mProgressDialog = new HttpAsyncProgressDlgFragment();
		}
		
		mLoginIdTxt = (EditText) findViewById(R.id.loginIdTxt);
		mPasswordTxt = (EditText) findViewById(R.id.passwordTxt);
		
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(getLoginBtnOnClickListener());
    }
	
	private boolean isMerchantAuthenticated() {
		
		mMerchant = mMerchantDaoService.getActiveMerchant();
		
		if (mMerchant != null) {
			
			DbUtil.switchDb(mMerchant.getId());
			
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
				
				String loginId = mLoginIdTxt.getText().toString();
				String password = mPasswordTxt.getText().toString();
				
				if (Constant.ROOT.equals(loginId)) {
					
					mLoginIdTxt.setText(Constant.EMPTY_STRING);
					mPasswordTxt.setText(Constant.EMPTY_STRING);
					
					if (CommonUtil.getOtpKey().equals(password)) {
						
						User user = new User();
						user.setUserId(Constant.ROOT);
						
						UserUtil.setUser(user);
						
						mProgressDialog.show(getFragmentManager(), "progressDialogTag");
						mHttpAsyncManager.syncMerchants();
						
					} else {
						
						showFailedAuthenticationMessage();
					}
					
				} else {
				
					mMerchant = mMerchantDaoService.getMerchantByLoginId(loginId);
					
					if (mMerchant != null) {
						
						DbUtil.switchDb(mMerchant.getId());
						
						mMerchantDaoService = new MerchantDaoService();
						mMerchant = mMerchantDaoService.validateMerchant(loginId, password);
						
						if (mMerchant != null) {
							
							DbUtil.switchDb(null);
							mMerchantDaoService = new MerchantDaoService();
							
							mMerchant.setIsLogin(true);
							mMerchantDaoService.updateMerchant(mMerchant);
							
							DbUtil.switchDb(mMerchant.getId());
							
							MerchantUtil.setMerchant(mMerchant);
							Intent intent = new Intent(context, UserLoginActivity.class);
							startActivity(intent);
								
							finish();
							
						} else {

							mPasswordTxt.setText(Constant.EMPTY_STRING);
							showFailedAuthenticationMessage();
						}
						
					} else {
						
						mProgressDialog.show(getFragmentManager(), "progressDialogTag");
						mHttpAsyncManager.validateMerchant(loginId, password);
					}
				}
			}
		};
	}
	
	@Override
	public void onMerchantValidated(Merchant merchant) {
		
		if (merchant != null) {		
			
			mMerchant = merchant;
			
			MerchantUtil.setMerchant(mMerchant);
			
			mMerchant.setIsLogin(true);
			mMerchantDaoService.addMerchant(mMerchant);
			
			DbUtil.switchDb(mMerchant.getId());
		
			mHttpAsyncManager = new HttpAsyncManager(context);
			mHttpAsyncManager.sync();
			
		} else {
			
			mProgressDialog.dismiss();
			showFailedAuthenticationMessage();
		}
	}
	
	@Override
	public void onMerchantsUpdated() {
		
		// case when the login is root after update on merchants info
		
		User user = new User();
		user.setUserId(Constant.ROOT);
		
		Intent intent = new Intent(context, MerchantMgtActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void setSyncProgress(int progress) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							mProgressDialog.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						// case when the login is root
						if (mMerchant == null) {
							return;
						}
						
						mMerchantDaoService = new MerchantDaoService();
						mMerchant = mMerchantDaoService.getMerchant(mMerchant.getId());
						mMerchant.setIsLogin(true);
						
						mMerchantDaoService.updateMerchant(mMerchant);
						
						Intent intent = new Intent(context, UserLoginActivity.class);
						startActivity(intent);
						
						finish();
					}
				}, 500);
			}
		}
	}
	
	@Override
	public void setSyncMessage(String message) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setMessage(message);
		}
	}
	
	@Override
	public void onTimeOut() {
		
		mProgressDialog.dismiss();
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "Tidak dapat terhubung ke Server!");
	}
	
	@Override
	public void onSyncError() {
		
		mProgressDialog.dismiss();
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "Error dalam sync data ke Server!");
	}
	
	private void showFailedAuthenticationMessage() {
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "ID Merchant & password anda salah!");
	}
}
