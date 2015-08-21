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

public class ForgotPasswordValidateActivity extends BaseAuthActivity {

	Merchant mMerchant;
	
	TextView mSecurityQuestionText;
	
	EditText mSecurityAnswerText;
	
	List<Object> mInputFields = new ArrayList<Object>();
	protected List<FormFieldBean> mMandatoryFields = new ArrayList<FormFieldBean>();
	
	Button mOkBtn;
	
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
		
		setContentView(R.layout.auth_forgot_password_validate_activity);
		
		mSecurityQuestionText = (TextView)  findViewById(R.id.securityQuestionText);
		mSecurityAnswerText = (EditText) findViewById(R.id.securityAnswerText);
		
		registerField(mSecurityAnswerText);
		
		registerMandatoryField(new FormFieldBean(mSecurityAnswerText, R.string.field_security_answer));
		
		highlightMandatoryFields();
		
		mOkBtn = (Button) findViewById(R.id.okBtn);
		mOkBtn.setOnClickListener(getOkBtnOnClickListener());
		
		mMerchant = MerchantUtil.getMerchant();
		
		String securityQuestion = mMerchant.getSecurityQuestion();
		securityQuestion += securityQuestion.contains("?") ? Constant.EMPTY_STRING : " ?";
		
		mSecurityQuestionText.setText(securityQuestion);
    }
	
	private View.OnClickListener getOkBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (isValidated()) {
					
					String securityAnswer = mSecurityAnswerText.getText().toString();
					
					Merchant merchant = MerchantUtil.getMerchant();
					
					if (merchant.getSecurityAnswer().equalsIgnoreCase(securityAnswer)) {
						
						Intent intent = new Intent(context, ForgotPasswordResetActivity.class);
						startActivity(intent);
					
					} else {
						
						NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_security_answer_dont_match));
					} 
				}
			}
		};
	}
}
