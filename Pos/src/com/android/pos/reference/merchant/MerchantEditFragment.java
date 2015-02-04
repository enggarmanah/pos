package com.android.pos.reference.merchant;

import java.util.ArrayList;
import java.util.Date;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDao;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MerchantEditFragment extends BaseEditFragment<Merchant> {
    
	EditText mNameText;
	Spinner mTypeSp;
    EditText mAddressText;
    EditText mContactNameText;
    EditText mContactTelephoneText;
    EditText mLoginIdText;
    EditText mPasswordText;
    EditText mPeriodStartDate;
    EditText mPeriodEndDate;
    EditText mTaxText;
    EditText mServiceChargeText;
    Spinner mStatusSp;
    
    CodeSpinnerArrayAdapter statusArrayAdapter;
    CodeSpinnerArrayAdapter typeArrayAdapter;
    
    private MerchantDao merchantDao = DbUtil.getSession().getMerchantDao();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.ref_merchant_fragment, container, false);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
        mNameText = (EditText) getActivity().findViewById(R.id.nameText);
    	mTypeSp = (Spinner) getActivity().findViewById(R.id.typeSp);
    	mAddressText = (EditText) getActivity().findViewById(R.id.addressText);
    	mContactNameText = (EditText) getActivity().findViewById(R.id.contactNameText);
    	mContactTelephoneText = (EditText) getActivity().findViewById(R.id.contactTelephoneText);
    	mLoginIdText = (EditText) getActivity().findViewById(R.id.loginIdText);
    	mPasswordText = (EditText) getActivity().findViewById(R.id.passwordText);
    	mPeriodStartDate = (EditText) getActivity().findViewById(R.id.periodStartDate);
    	mPeriodEndDate = (EditText) getActivity().findViewById(R.id.periodEndDate);
    	mTaxText = (EditText) getActivity().findViewById(R.id.taxText);
    	mServiceChargeText = (EditText) getActivity().findViewById(R.id.serviceChargeText);
    	mStatusSp = (Spinner) getActivity().findViewById(R.id.statusSp);
    	
    	registerField(mNameText);
    	registerField(mTypeSp);
    	registerField(mAddressText);
    	registerField(mContactNameText);
    	registerField(mContactTelephoneText);
    	registerField(mLoginIdText);
    	registerField(mPasswordText);
    	registerField(mPeriodStartDate);
    	registerField(mPeriodEndDate);
    	registerField(mTaxText);
    	registerField(mServiceChargeText);
    	registerField(mStatusSp);
    	
    	mandatoryFields = new ArrayList<MerchantEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	mandatoryFields.add(new FormField(mAddressText, R.string.field_address));
    	mandatoryFields.add(new FormField(mContactNameText, R.string.field_contact_name));
    	mandatoryFields.add(new FormField(mContactTelephoneText, R.string.field_contact_telephone));
    	mandatoryFields.add(new FormField(mLoginIdText, R.string.field_login_id));
    	mandatoryFields.add(new FormField(mPasswordText, R.string.field_password));
    	mandatoryFields.add(new FormField(mPeriodStartDate, R.string.field_period_start));
    	mandatoryFields.add(new FormField(mPeriodEndDate, R.string.field_period_end));
    	
    	mPeriodStartDate.setOnClickListener(getDateFieldOnClickListener(mPeriodStartDate, "startDatePicker"));
    	mPeriodEndDate.setOnClickListener(getDateFieldOnClickListener(mPeriodEndDate, "endDatePicker"));
    	
    	linkDatePickerWithInputField("startDatePicker", mPeriodStartDate);
    	linkDatePickerWithInputField("endDatePicker", mPeriodEndDate);
    	
    	typeArrayAdapter = new CodeSpinnerArrayAdapter(mTypeSp, getActivity(), CodeUtil.getMerchantTypes());
    	mTypeSp.setAdapter(typeArrayAdapter);
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    }
    
    @Override
    protected void updateView(Merchant merchant) {
    	
    	if (merchant != null) {
    		
    		int typeIndex = typeArrayAdapter.getPosition(merchant.getType());
    		int statusIndex = statusArrayAdapter.getPosition(merchant.getStatus());
    		
    		mNameText.setText(merchant.getName());
    		mAddressText.setText(merchant.getAddress());
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
    	}
    }
    
    @Override
    protected Long getItemId(Merchant merchant) {
    	
    	return merchant.getId(); 
    } 
    
    @Override
    protected void addItem() {
    	
        merchantDao.insert(mItem);
        mNameText.getText().clear();
        mAddressText.getText().clear();
        
        mItem = null;
    }
    
    @Override
    protected void updateItem() {
    	
    	merchantDao.update(mItem);
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public Merchant updateItem(Merchant merchant) {

    	merchantDao.detach(merchant);
    	merchant = merchantDao.load(merchant.getId());
    	merchantDao.detach(merchant);
    	
    	this.mItem = merchant;
    	
    	return merchant;
    }
}