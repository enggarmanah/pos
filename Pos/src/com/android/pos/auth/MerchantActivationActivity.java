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
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.model.FormFieldBean;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

public class MerchantActivationActivity extends BaseAuthActivity {

	Merchant mMerchant;
	
	TextView mActivationInfoText;
	TextView mResendActivationCodeText;
	
	EditText mActivationCodeText;
	
	List<Object> mInputFields = new ArrayList<Object>();
	protected List<FormFieldBean> mMandatoryFields = new ArrayList<FormFieldBean>();
	
	Button mOkBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DbUtil.initDb(this);
		CodeUtil.initCodes(this);
		
		mMerchantDaoService = new MerchantDaoService();
		
		setContentView(R.layout.auth_activation_activity);
		
		mActivationInfoText = (TextView) findViewById(R.id.activationInfoText);
		mResendActivationCodeText = (TextView) findViewById(R.id.resendActivationCodeText);
		mActivationCodeText = (EditText) findViewById(R.id.activationCodeText);
		
		registerField(mActivationCodeText);
		
		registerMandatoryField(new FormFieldBean(mActivationCodeText, R.string.merchant_activation_hint));
		
		highlightMandatoryFields();
		
		mOkBtn = (Button) findViewById(R.id.okBtn);
		mOkBtn.setOnClickListener(getOkBtnOnClickListener());
		
		mResendActivationCodeText.setOnClickListener(getResendActivationCodeTextOnClickListener());
    }
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mMerchant = MerchantUtil.getMerchant();
		mActivationInfoText.setText(getString(R.string.msg_merchant_activation_info, mMerchant.getContactEmail()));
	}
	
	private View.OnClickListener getOkBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (isValidated()) {
					
					UserUtil.setMerchant(false);
					
					mMerchant = MerchantUtil.getMerchant();
					
					String activationCode = mActivationCodeText.getText().toString();
					String correctCode = mMerchant.getRefId().substring(mMerchant.getRefId().length()-6);
					
					if (activationCode.equals(correctCode)) {
						
						mMerchant.setStatus(Constant.STATUS_ACTIVE);
						mMerchantDaoService.updateMerchant(mMerchant);
						
						Intent intent = new Intent(context, UserLoginActivity.class);
						startActivity(intent);
						
						finish();
						
					} else {
						
						NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_activation_code_incorrect));
					}
				}
			}
		};
	}
	
	private View.OnClickListener getResendActivationCodeTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, ResendActivationCodeActivity.class);
				startActivity(intent);
				
				finish();
			}
		};
	}
}
