package com.android.pos.data.customer;

import java.util.ArrayList;
import java.util.Date;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.Customer;
import com.android.pos.service.CustomerDaoService;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.UserUtil;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CustomerEditFragment extends BaseEditFragment<Customer> {
    
	EditText mNameText;
	EditText mTelephoneText;
	EditText mEmailText;
	Spinner mEmailStatusSp;
    EditText mAddressText;
    Spinner mStatusSp;
    
    CodeSpinnerArrayAdapter emailStatusArrayAdapter;
    CodeSpinnerArrayAdapter statusArrayAdapter;
    
    private CustomerDaoService mCustomerDaoService = new CustomerDaoService();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.data_customer_fragment, container, false);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
        mNameText = (EditText) getActivity().findViewById(R.id.nameTxt);
    	mTelephoneText = (EditText) getActivity().findViewById(R.id.telephoneTxt);
    	mEmailText = (EditText) getActivity().findViewById(R.id.emailTxt);
    	mAddressText = (EditText) getActivity().findViewById(R.id.addressTxt);
    	mEmailStatusSp = (Spinner) getActivity().findViewById(R.id.emailStatusSp);
    	mStatusSp = (Spinner) getActivity().findViewById(R.id.statusSp);
    	
    	registerField(mNameText);
    	registerField(mTelephoneText);
    	registerField(mEmailText);
    	registerField(mAddressText);
    	registerField(mEmailStatusSp);
    	registerField(mStatusSp);
    	
    	enableInputFields(false);
    	
    	mandatoryFields = new ArrayList<CustomerEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	
    	emailStatusArrayAdapter = new CodeSpinnerArrayAdapter(mEmailStatusSp, getActivity(), CodeUtil.getEmailStatus());
    	mEmailStatusSp.setAdapter(emailStatusArrayAdapter);
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    }
    
    @Override
    protected void updateView(Customer customer) {
    	
    	if (customer != null) {
    		
    		int emailStatusIndex = emailStatusArrayAdapter.getPosition(customer.getEmailStatus());
    		int statusIndex = statusArrayAdapter.getPosition(customer.getStatus());
    		
    		mNameText.setText(customer.getName());
    		mTelephoneText.setText(customer.getTelephone());
    		mEmailText.setText(customer.getEmail());
    		mAddressText.setText(customer.getAddress());
    		
    		mEmailStatusSp.setSelection(emailStatusIndex);
    		mStatusSp.setSelection(statusIndex);
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    }
    
    @Override
    protected void saveItem() {
    	
    	String name = mNameText.getText().toString();
    	String telephone = mTelephoneText.getText().toString();
    	String email = mEmailText.getText().toString();
    	String emailStatus = CodeBean.getNvlCode((CodeBean) mEmailStatusSp.getSelectedItem());
    	String address = mAddressText.getText().toString();
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setMerchantId(MerchantUtil.getMerchantId());
    		
    		mItem.setName(name);
    		mItem.setTelephone(telephone);
    		mItem.setEmail(email);
    		mItem.setEmailStatus(emailStatus);
    		mItem.setAddress(address);
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
    protected Long getItemId(Customer customer) {
    	
    	return customer.getId(); 
    } 
    
    @Override
    protected void addItem() {
    	
        mCustomerDaoService.addCustomer(mItem);
        
        mNameText.getText().clear();
        mTelephoneText.getText().clear();
        
        mItem = null;
    }
    
    @Override
    protected void updateItem() {
    	
    	mCustomerDaoService.updateCustomer(mItem);
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public Customer updateItem(Customer customer) {

    	customer = mCustomerDaoService.getCustomer(customer.getId());
    	
    	this.mItem = customer;
    	
    	return customer;
    }
}