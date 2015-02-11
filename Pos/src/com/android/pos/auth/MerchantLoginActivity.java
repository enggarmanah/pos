package com.android.pos.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.pos.R;
import com.android.pos.common.AlertDlgFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.http.HttpAsyncListener;
import com.android.pos.http.HttpAsyncManager;
import com.android.pos.http.HttpAsyncProgressDlgFragment;
import com.android.pos.service.MerchantDaoService;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;

public class MerchantLoginActivity extends Activity implements HttpAsyncListener, LoginListener {

	final Context context = this;
	private HttpAsyncManager mHttpAsyncManager;
	
	private HttpAsyncProgressDlgFragment mProgressDialog;
	private MerchantDaoService mMerchantDaoService;
	
	Button mLoginBtn;
	EditText mLoginIdTxt;
	EditText mPasswordTxt;
	
	Merchant mMerchant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.auth_merchant_login_activity);
		
		DbUtil.initDb(this);
		
		mMerchantDaoService = new MerchantDaoService();
		
		if (isMerchantAuthenticated()) {
			return;
		}
		
		mHttpAsyncManager = new HttpAsyncManager(this);
		
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
		
		Merchant merchant = mMerchantDaoService.getActiveMerchant();
		
		if (merchant != null) {
			
			MerchantUtil.setMerchant(merchant);
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
				
				Merchant merchant = mMerchantDaoService.getMerchantByLoginId(loginId);
				
				if (merchant != null) {
				
					merchant = mMerchantDaoService.validateMerchant(loginId, password);
					
					if (merchant != null) {
						
						MerchantUtil.setMerchant(merchant);
						Intent intent = new Intent(context, UserLoginActivity.class);
						startActivity(intent);
					
					} else {
						
						showFailedAuthenticationMessage();
					}
					
				} else {
					
					mProgressDialog.show(getFragmentManager(), "progressDialogTag");
					mHttpAsyncManager.validateMerchant(loginId, password);
				}
			}
		};
	}
	
	@Override
	public void onMerchantValidated(Merchant merchant) {
		
		if (merchant != null) {		
			
			mMerchant = merchant;
			
			MerchantUtil.setMerchant(merchant);
			mHttpAsyncManager.sync();
			
		} else {
			
			mProgressDialog.dismiss();
			showFailedAuthenticationMessage();
		}
	}
	
	@Override
	public void setSyncProgress(int progress) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						mProgressDialog.dismiss();
						
						mMerchant = mMerchantDaoService.getMerchant(mMerchant.getId());
						mMerchant.setIsLogin(true);
						
						mMerchantDaoService.updateMerchant(mMerchant);
						
						Intent intent = new Intent(context, UserLoginActivity.class);
						startActivity(intent);
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
	
	private void showFailedAuthenticationMessage() {
		
		AlertDlgFragment alertDialogFragment = NotificationUtil.getAlertDialogInstance();
		alertDialogFragment.show(getFragmentManager(), NotificationUtil.ALERT_DIALOG_FRAGMENT_TAG);
		alertDialogFragment.setAlertMessage("ID Merchant & password anda salah!");
	}
}
