package com.android.pos.reference.employee;

import java.util.ArrayList;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.Employee;
import com.android.pos.dao.EmployeeDao;
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

public class EmployeeEditFragment extends BaseEditFragment<Employee> {
    
	EditText mNameText;
	EditText mTelephoneText;
    EditText mAddressText;
    Spinner mStatusSp;
    
    CodeSpinnerArrayAdapter statusArrayAdapter;
    
    private EmployeeDao employeeDao = DbUtil.getSession().getEmployeeDao();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.ref_employee_fragment, container, false);
    	
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
    	mAddressText = (EditText) getActivity().findViewById(R.id.addressTxt);
    	mStatusSp = (Spinner) getActivity().findViewById(R.id.statusSp);
    	
    	registerField(mNameText);
    	registerField(mTelephoneText);
    	registerField(mAddressText);
    	registerField(mStatusSp);
    	
    	enableInputFields(false);
    	
    	mandatoryFields = new ArrayList<EmployeeEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    }
    
    @Override
    protected void updateView(Employee employee) {
    	
    	if (employee != null) {
    		
    		int statusIndex = statusArrayAdapter.getPosition(employee.getStatus());
    		
    		mNameText.setText(employee.getName());
    		mTelephoneText.setText(employee.getTelephone());
    		mAddressText.setText(employee.getAddress());
    		
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
    	String address = mAddressText.getText().toString();
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setMerchantId(CommonUtil.getMerchantId());
    		
    		mItem.setName(name);
    		mItem.setTelephone(telephone);
    		mItem.setAddress(address);
    		mItem.setStatus(status);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    	}
    }
    
    @Override
    protected Long getItemId(Employee employee) {
    	
    	return employee.getId(); 
    } 
    
    @Override
    protected void addItem() {
    	
        employeeDao.insert(mItem);
        mNameText.getText().clear();
        mTelephoneText.getText().clear();
        
        mItem = null;
    }
    
    @Override
    protected void updateItem() {
    	
    	employeeDao.update(mItem);
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public Employee updateItem(Employee employee) {

    	employeeDao.detach(employee);
    	employee = employeeDao.load(employee.getId());
    	employeeDao.detach(employee);
    	
    	this.mItem = employee;
    	
    	return employee;
    }
}