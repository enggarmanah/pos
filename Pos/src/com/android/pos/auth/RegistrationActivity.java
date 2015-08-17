package com.android.pos.auth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.ProgressDlgFragment;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantAccess;
import com.android.pos.dao.MerchantAccessDaoService;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.model.FormFieldBean;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

public class RegistrationActivity extends BaseAuthActivity implements RegistrationListener {

	Merchant mMerchant;
	
	TextView mInfoText;
	
	EditText mNameText;
	Spinner mTypeSp;
	EditText mAddressText;
	EditText mTelephoneText;
	EditText mEmailText;
	EditText mLoginIdText;
	EditText mPasswordText;
	EditText mPasswordConfirmText;
	EditText mSecurityQuestionText;
	EditText mSecurityAnswerText;
	
	List<Object> mInputFields = new ArrayList<Object>();
	protected List<FormFieldBean> mMandatoryFields = new ArrayList<FormFieldBean>();
	
	Button mOkBtn;
	
	CodeSpinnerArrayAdapter typeArrayAdapter;
	
	boolean mIsRegistrationSuccessful = false;
	
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
		
		setContentView(R.layout.auth_registration_activity);
		
		mInfoText = (TextView) findViewById(R.id.infoText);
		mTypeSp = (Spinner) findViewById(R.id.typeSp);
		mNameText = (EditText) findViewById(R.id.nameText);
		mAddressText = (EditText) findViewById(R.id.addressText);
		mTelephoneText = (EditText) findViewById(R.id.telephoneText);
		mEmailText = (EditText) findViewById(R.id.emailText);
		mLoginIdText = (EditText) findViewById(R.id.loginIdText);
		mPasswordText = (EditText) findViewById(R.id.passwordText);
		mPasswordConfirmText = (EditText) findViewById(R.id.passwordConfirmText);
		mSecurityQuestionText = (EditText) findViewById(R.id.securityQuestionText);
		mSecurityAnswerText = (EditText) findViewById(R.id.securityAnswerText);
		
		mInfoText.setVisibility(View.GONE);
		
		registerField(mNameText);
		registerField(mAddressText);
		registerField(mTelephoneText);
		registerField(mEmailText);
		registerField(mLoginIdText);
		registerField(mPasswordText);
		registerField(mPasswordConfirmText);
		registerField(mSecurityQuestionText);
		registerField(mSecurityAnswerText);
		
		registerMandatoryField(new FormFieldBean(mNameText, R.string.field_name));
		registerMandatoryField(new FormFieldBean(mAddressText, R.string.field_address));
		registerMandatoryField(new FormFieldBean(mTelephoneText, R.string.field_telephone));
		registerMandatoryField(new FormFieldBean(mEmailText, R.string.field_email));
		registerMandatoryField(new FormFieldBean(mLoginIdText, R.string.field_login_id));
		registerMandatoryField(new FormFieldBean(mPasswordText, R.string.field_password));
		registerMandatoryField(new FormFieldBean(mPasswordConfirmText, R.string.password_confirm));
		registerMandatoryField(new FormFieldBean(mSecurityQuestionText, R.string.security_question));
		registerMandatoryField(new FormFieldBean(mSecurityAnswerText, R.string.security_answer));
		
		highlightMandatoryFields();
		
		mOkBtn = (Button) findViewById(R.id.loginBtn);
		mOkBtn.setOnClickListener(getOkBtnOnClickListener());
		
		typeArrayAdapter = new CodeSpinnerArrayAdapter(mTypeSp, this, CodeUtil.getMerchantTypes(), 
				R.layout.register_spinner_items, 
				R.layout.register_spinner_items_selected, 
				R.layout.register_spinner_selected_item);
		
    	mTypeSp.setAdapter(typeArrayAdapter);
    }
	
	private View.OnClickListener getOkBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (!mIsRegistrationSuccessful) {
				
					if (isValidated()) {
						
						mProgressDialog.show(getFragmentManager(), mProgressDialogTag);
					
						UserUtil.setMerchant(false);
						
						String type = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
						
						String name = mNameText.getText().toString();
						String address = mAddressText.getText().toString();
						String telephone = mTelephoneText.getText().toString();
						String email = mEmailText.getText().toString();
						String loginId = mLoginIdText.getText().toString();
						String password = mPasswordText.getText().toString();
						String securityQuestion = mSecurityQuestionText.getText().toString();
						String securityAnswer = mSecurityAnswerText.getText().toString();
						
						Merchant merchant = new Merchant();
						
						merchant.setType(type);
						merchant.setName(name);
						merchant.setAddress(address);
						merchant.setTelephone(telephone);
						merchant.setContactEmail(email);
						merchant.setLoginId(loginId);
						merchant.setPassword(password);
						merchant.setSecurityQuestion(securityQuestion);
						merchant.setSecurityAnswer(securityAnswer);
						
						// default value
						
						merchant.setRefId(CommonUtil.generateRefId());
						
						Calendar start = Calendar.getInstance();
						Calendar end = Calendar.getInstance();
						end.add(Calendar.MONTH, 3);
						
						merchant.setPrinterRequired(Constant.STATUS_INACTIVE);
						merchant.setPrinterMiniFont(Constant.STATUS_YES);
						merchant.setPrinterLineSize(32);
						
						merchant.setPeriodStart(start.getTime());
						merchant.setPeriodEnd(end.getTime());
						
						merchant.setPaymentType(Constant.PAYMENT_TYPE_CASH);
						merchant.setPriceTypeCount(1);
						merchant.setDiscountType(Constant.DISCOUNT_TYPE_PERCENTAGE);
						
						merchant.setTaxPercentage(Float.valueOf(0));
						merchant.setServiceChargePercentage(Float.valueOf(0));
						
						merchant.setStatus(Constant.STATUS_ACTIVE);
						
						merchant.setCreateBy(Constant.SYSTEM);
						merchant.setCreateDate(new Date());
						merchant.setUpdateBy(Constant.SYSTEM);
						merchant.setUpdateDate(new Date());
						
						mHttpAsyncManager = new HttpAsyncManager(context);
						mHttpAsyncManager.registerMerchant(merchant);
					}
					
				} else {
					
					Intent intent = new Intent(context, UserLoginActivity.class);
					startActivity(intent);
				} 
			}
		};
	}
	
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
	public void onMerchantRegistered(Merchant merchant) {
		
		mIsRegistrationSuccessful = true;
		
		mProgressDialog.dismiss();
		
		if (merchant != null) {		
			
			mMerchant = merchant;
			
			MerchantUtil.setMerchant(mMerchant);
			
			mMerchant.setIsLogin(true);
			
			mMerchantDaoService.addMerchant(mMerchant);
			
			DbUtil.switchDb(getApplicationContext(), mMerchant.getId());
			
			mMerchantDaoService = new MerchantDaoService();
			mMerchantAccessDaoService = new MerchantAccessDaoService();
			
			mMerchantDaoService.addMerchant(mMerchant);
			
			List<MerchantAccess> mMerchantAccesses = mMerchantAccessDaoService.getMerchantAccessList(merchant.getId());
			
			for (MerchantAccess merchantAccess : mMerchantAccesses) {
	        	
	        	merchantAccess.setMerchant(mMerchant);
	        	merchantAccess.setUploadStatus(Constant.STATUS_YES);
	        	merchantAccess.setStatus(Constant.STATUS_YES);
	        	
	        	mMerchantAccessDaoService.addMerchantAccess(merchantAccess);
	        }
			
			mInfoText.setText(getString(R.string.msg_merchant_registration_success));
			mInfoText.setVisibility(View.VISIBLE);
			
			mTypeSp.setVisibility(View.GONE);
			mNameText.setVisibility(View.GONE);
			mAddressText.setVisibility(View.GONE);
			mTelephoneText.setVisibility(View.GONE);
			mEmailText.setVisibility(View.GONE);
			mLoginIdText.setVisibility(View.GONE);
			mPasswordText.setVisibility(View.GONE);
			mPasswordConfirmText.setVisibility(View.GONE);
			mSecurityQuestionText.setVisibility(View.GONE);
			mSecurityAnswerText.setVisibility(View.GONE);
		}
	}
}
