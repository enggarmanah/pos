package com.android.pos.data.merchant;

import java.util.ArrayList;
import java.util.Date;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.service.MerchantDaoService;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MerchantEditFragment extends BaseEditFragment<Merchant> {
    
	EditText mNameText;
	Spinner mTypeSp;
    EditText mAddressText;
    EditText mTelephoneText;
    EditText mContactNameText;
    EditText mContactTelephoneText;
    EditText mLoginIdText;
    EditText mPasswordText;
    EditText mPeriodStartDate;
    EditText mPeriodEndDate;
    EditText mTaxText;
    EditText mServiceChargeText;
    Spinner mStatusSp;
    
    LinearLayout mStatusPanel;
    
    CodeSpinnerArrayAdapter statusArrayAdapter;
    CodeSpinnerArrayAdapter typeArrayAdapter;
    
    private MerchantDaoService mMerchantDaoService = new MerchantDaoService();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.data_merchant_fragment, container, false);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
        mNameText = (EditText) getView().findViewById(R.id.nameText);
    	mTypeSp = (Spinner) getView().findViewById(R.id.typeSp);
    	mAddressText = (EditText) getView().findViewById(R.id.addressText);
    	mTelephoneText = (EditText) getView().findViewById(R.id.telephoneText);
    	mContactNameText = (EditText) getView().findViewById(R.id.contactNameText);
    	mContactTelephoneText = (EditText) getView().findViewById(R.id.contactTelephoneText);
    	mLoginIdText = (EditText) getView().findViewById(R.id.loginIdText);
    	mPasswordText = (EditText) getView().findViewById(R.id.passwordText);
    	mPeriodStartDate = (EditText) getView().findViewById(R.id.periodStartDate);
    	mPeriodEndDate = (EditText) getView().findViewById(R.id.periodEndDate);
    	mTaxText = (EditText) getView().findViewById(R.id.taxText);
    	mServiceChargeText = (EditText) getView().findViewById(R.id.serviceChargeText);
    	mStatusSp = (Spinner) getView().findViewById(R.id.statusSp);

    	mStatusPanel = (LinearLayout) getView().findViewById(R.id.statusPanel);
    	
    	registerField(mNameText);
    	registerField(mTypeSp);
    	registerField(mAddressText);
    	registerField(mTelephoneText);
    	registerField(mContactNameText);
    	registerField(mContactTelephoneText);
    	registerField(mLoginIdText);
    	registerField(mPasswordText);
    	registerField(mPeriodStartDate);
    	registerField(mPeriodEndDate);
    	registerField(mTaxText);
    	registerField(mServiceChargeText);
    	registerField(mStatusSp);
    	
    	enableInputFields(false);
    	
    	mandatoryFields = new ArrayList<MerchantEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	mandatoryFields.add(new FormField(mAddressText, R.string.field_address));
    	//mandatoryFields.add(new FormField(mTelephoneText, R.string.field_telephone));
    	mandatoryFields.add(new FormField(mContactNameText, R.string.field_contact_name));
    	mandatoryFields.add(new FormField(mContactTelephoneText, R.string.field_contact_telephone));
    	mandatoryFields.add(new FormField(mLoginIdText, R.string.field_login_id));
    	mandatoryFields.add(new FormField(mPasswordText, R.string.field_password));
    	mandatoryFields.add(new FormField(mPeriodStartDate, R.string.field_period_start));
    	mandatoryFields.add(new FormField(mPeriodEndDate, R.string.field_period_end));
    	mandatoryFields.add(new FormField(mTaxText, R.string.field_tax_percentage));
    	mandatoryFields.add(new FormField(mServiceChargeText, R.string.field_service_charge_percentage));
    	
    	// only root can access validity period
    	
    	if (UserUtil.isRoot()) {
    		
    		mPeriodStartDate.setOnClickListener(getDateFieldOnClickListener(mPeriodStartDate, "startDatePicker"));
        	mPeriodEndDate.setOnClickListener(getDateFieldOnClickListener(mPeriodEndDate, "endDatePicker"));
		}
    	
    	linkDatePickerWithInputField("startDatePicker", mPeriodStartDate);
    	linkDatePickerWithInputField("endDatePicker", mPeriodEndDate);
    	
    	typeArrayAdapter = new CodeSpinnerArrayAdapter(mTypeSp, getActivity(), CodeUtil.getMerchantTypes());
    	mTypeSp.setAdapter(typeArrayAdapter);
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    	
    	if (UserUtil.isRoot()) {
    		mStatusPanel.setVisibility(View.VISIBLE);
		} else {
			mStatusPanel.setVisibility(View.GONE);
		}
    }
    
    @Override
    protected void updateView(Merchant merchant) {
    	
    	if (merchant != null) {
    		
    		int typeIndex = typeArrayAdapter.getPosition(merchant.getType());
    		int statusIndex = statusArrayAdapter.getPosition(merchant.getStatus());
    		
    		mNameText.setText(merchant.getName());
    		mAddressText.setText(merchant.getAddress());
    		mTelephoneText.setText(merchant.getTelephone());
    		mContactNameText.setText(merchant.getContactName());
    		mContactTelephoneText.setText(merchant.getContactTelephone());
    		mLoginIdText.setText(merchant.getLoginId());
    		mPasswordText.setText(merchant.getPassword());
    		mPeriodStartDate.setText(CommonUtil.formatDate(merchant.getPeriodStart()));
    		mPeriodEndDate.setText(CommonUtil.formatDate(merchant.getPeriodEnd()));
    		
    		mTaxText.setText(CommonUtil.formatString(merchant.getTaxPercentage()));
    		mServiceChargeText.setText(CommonUtil.formatString(merchant.getServiceChargePercentage()));
    		
    		mTypeSp.setSelection(typeIndex);
    		mStatusSp.setSelection(statusIndex);
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    }
    
    @Override
    protected void saveItem() {
    	
    	String name = mNameText.getText().toString();
    	String type = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
    	String address = mAddressText.getText().toString();
    	String telephone = mTelephoneText.getText().toString();
    	String contactName = mContactNameText.getText().toString();
    	String contactTelephone = mContactTelephoneText.getText().toString();
    	String loginId = mLoginIdText.getText().toString();
    	String password = mPasswordText.getText().toString();
    	Integer tax = CommonUtil.parseInteger(mTaxText.getText().toString());
    	Integer serviceCharge = CommonUtil.parseInteger(mServiceChargeText.getText().toString());
    	Date startDate = CommonUtil.parseDate(mPeriodStartDate.getText().toString());
    	Date endDate = CommonUtil.parseDate(mPeriodEndDate.getText().toString());
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setName(name);
    		mItem.setType(type);
    		mItem.setAddress(address);
    		mItem.setTelephone(telephone);
    		mItem.setContactName(contactName);
    		mItem.setContactTelephone(contactTelephone);
    		mItem.setLoginId(loginId);
    		mItem.setPassword(password);
    		mItem.setPeriodStart(startDate);
    		mItem.setPeriodEnd(endDate);
    		mItem.setTaxPercentage(tax);
    		mItem.setServiceChargePercentage(serviceCharge);
    		mItem.setStatus(status);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    		
    		String userId = UserUtil.getUser().getUserId();
    		
    		if (mItem.getCreateBy() == null) {
    			mItem.setCreateBy(userId);
    			mItem.setCreateDate(new Date());
    		}
    		
    		mItem.setUpdateBy(userId);
    		mItem.setUpdateDate(new Date());
    	}
    }
    
    @Override
    protected Long getItemId(Merchant merchant) {
    	
    	return merchant.getId(); 
    } 
    
    @Override
    protected boolean addItem() {
    	
    	boolean isUpdated = false;
    	
    	Merchant merchant = mMerchantDaoService.getMerchantByLoginId(mItem.getLoginId());
    	
    	// new provided login id is conflict with some other merchant
    	if (merchant != null) {
    		
    		NotificationUtil.setAlertMessage(getFragmentManager(), "Login Id konflik dengan merchant lain");
    		
    	} else {
    		
	        mMerchantDaoService.addMerchant(mItem);
	        
	        mNameText.getText().clear();
	        mAddressText.getText().clear();
	        mTelephoneText.getText().clear();
	        mContactNameText.getText().clear();
	        mContactTelephoneText.getText().clear();
	        mLoginIdText.getText().clear();
	        mPasswordText.getText().clear();
	        mPeriodStartDate.getText().clear();
	        mPeriodEndDate.getText().clear();
	        mTaxText.getText().clear();
	        mServiceChargeText.getText().clear();
	        
	        mItem = null;
    	}
    	
    	return isUpdated;
    }
    
    @Override
    protected boolean updateItem() {
    	
    	boolean isUpdated = false;
    	
    	Merchant merchant = mMerchantDaoService.getMerchantByLoginId(mItem.getLoginId());
    	
    	// new provided login id is conflict with some other merchant
    	if (merchant != null && merchant.getId() != mItem.getId()) {
    		
    		NotificationUtil.setAlertMessage(getFragmentManager(), "Login Id konflik dengan merchant lain");
    	
    	} else {
    		
    		mMerchantDaoService.updateMerchant(mItem);
    		isUpdated = true;
    	}
    	
    	return isUpdated;
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public Merchant updateItem(Merchant merchant) {

    	merchant = mMerchantDaoService.getMerchant(merchant.getId());
    	
    	this.mItem = merchant;
    	
    	return merchant;
    }
}