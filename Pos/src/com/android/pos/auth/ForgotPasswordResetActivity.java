package com.android.pos.auth;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.ProgressDlgFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantAccessDaoService;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.model.FormFieldBean;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

public class ForgotPasswordResetActivity extends BaseAuthActivity implements ForgotPasswordListener {

	Merchant mMerchant;
	
	TextView mInfoText;
	
	EditText mPasswordText;
	EditText mPasswordConfirmText;
	
	List<Object> mInputFields = new ArrayList<Object>();
	protected List<FormFieldBean> mMandatoryFields = new ArrayList<FormFieldBean>();
	
	Button mOkBtn;
	
	boolean mIsResetSuccessful = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DbUtil.initDb(this);
		CodeUtil.initCodes(this);
		
		mMerchantDaoService = new MerchantDaoService();
		mMerchantAccessDaoService = new MerchantAccessDaoService();
		
		mHttpAsyncManager = new HttpAsyncManager(context);
		
		mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag(mProgressDialogTag);
		
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDlgFragment();
		}
		
		setContentView(R.layout.auth_forgot_password_reset_activity);
		
		mInfoText = (TextView)  findViewById(R.id.infoText);
		mPasswordText = (EditText) findViewById(R.id.passwordText);
		mPasswordConfirmText = (EditText) findViewById(R.id.passwordConfirmText);
		
		registerField(mPasswordText);
		registerField(mPasswordConfirmText);
		
		registerMandatoryField(new FormFieldBean(mPasswordText, R.string.password));
		registerMandatoryField(new FormFieldBean(mPasswordConfirmText, R.string.password_confirm));
		
		highlightMandatoryFields();
		
		mOkBtn = (Button) findViewById(R.id.okBtn);
		mOkBtn.setOnClickListener(getOkBtnOnClickListener());
		
		mMerchant = MerchantUtil.getMerchant();
		
		String securityQuestion = mMerchant.getSecurityAnswer();
		securityQuestion += securityQuestion.contains("?") ? Constant.EMPTY_STRING : " ?";
		
		mInfoText.setText(mMerchant.getName());
    }
	
	private View.OnClickListener getOkBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (!mIsResetSuccessful) {
					
					if (isValidated()) {
						
						mProgressDialog.show(getFragmentManager(), mProgressDialogTag);
					
						UserUtil.setMerchant(false);
						
						String loginId = MerchantUtil.getMerchant().getLoginId();
						String password = mPasswordText.getText().toString();
						
						Merchant merchant = mMerchantDaoService.getMerchantByLoginId(loginId);
						
						if (merchant != null) {
							
							DbUtil.switchDb(getApplicationContext(), mMerchant.getId());
							
							mMerchantDaoService = new MerchantDaoService();
							mMerchantAccessDaoService = new MerchantAccessDaoService();
							
							merchant = mMerchantDaoService.getMerchantByLoginId(loginId);
							
							merchant.setPassword(password);
							mMerchantDaoService.updateMerchant(merchant);
							
							DbUtil.switchDb(getApplicationContext(), null);
						}
						
						mHttpAsyncManager = new HttpAsyncManager(context);
						mHttpAsyncManager.resetPassword(loginId, password);
					}
				
				} else {
					
					Intent intent = new Intent(context, MerchantLoginActivity.class);
					startActivity(intent);
					
					finish();
				}
			}
		};
	}
	
	@Override
	protected boolean isValidated() {
    	
    	if (super.isValidated()) {
		
			if (!mPasswordText.getText().toString().equals(mPasswordConfirmText.getText().toString())) {
	    		
	    		NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_password_dont_match));
	    		mPasswordText.requestFocus();
	    		return false;
	    		
	    	} else {
	    		return true;
	    	}
			
    	} else {
    		return false;
    	}
    }
	
	@Override
	public void onMerchantRetrieved(Merchant merchant) {
		
		if (merchant != null) {		
			
			mIsResetSuccessful = true;
			
			mInfoText.setText(getString(R.string.msg_reset_password_success));
			
			mPasswordText.setVisibility(View.GONE);
			mPasswordConfirmText.setVisibility(View.GONE);
		}
		
		mProgressDialog.dismiss();
	}
}
