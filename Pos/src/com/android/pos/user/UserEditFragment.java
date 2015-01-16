package com.android.pos.user;

import java.util.ArrayList;

import com.android.pos.CodeBean;
import com.android.pos.CommonUtil;
import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.CodeUtil;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.User;
import com.android.pos.dao.UserDao;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class UserEditFragment extends BaseEditFragment<User> {
    
	EditText mNameText;
	EditText mUserIdText;
    EditText mPasswordText;
    Spinner mRoleSp;
    Spinner mStatusSp;
    
    Button mOkButton;
    Button mCancelButton;
    
    CodeSpinnerArrayAdapter roleArrayAdapter;
    CodeSpinnerArrayAdapter statusArrayAdapter;
    
    private UserDao userDao = DbHelper.getSession().getUserDao();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.ref_user_fragment, container, false);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
        mOkButton = (Button) getActivity().findViewById(R.id.okBtn);
        mOkButton.setOnClickListener(getOkBtnClickListener());
        
        mCancelButton = (Button) getActivity().findViewById(R.id.cancelBtn);
        mCancelButton.setOnClickListener(getCancelBtnClickListener());
        
    	mNameText = (EditText) getActivity().findViewById(R.id.nameTxt);
    	mUserIdText = (EditText) getActivity().findViewById(R.id.userIdTxt);
    	mPasswordText = (EditText) getActivity().findViewById(R.id.passwordTxt);
    	mRoleSp = (Spinner) getActivity().findViewById(R.id.roleSp);
    	mStatusSp = (Spinner) getActivity().findViewById(R.id.statusSp);
    	
    	mandatoryFields = new ArrayList<UserEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	mandatoryFields.add(new FormField(mUserIdText, R.string.field_price));
    	mandatoryFields.add(new FormField(mPasswordText, R.string.field_password));
    	
    	roleArrayAdapter = new CodeSpinnerArrayAdapter(mRoleSp, getActivity(), CodeUtil.getRoles());
    	mRoleSp.setAdapter(roleArrayAdapter);
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    }
    
    @Override
    protected void updateView(User user) {
    	
    	if (user != null) {
    		
    		int roleIndex = roleArrayAdapter.getPosition(user.getRole());
    		int statusIndex = statusArrayAdapter.getPosition(user.getStatus());
    		
    		mNameText.setText(user.getName());
    		mUserIdText.setText(user.getUserId());
    		mPasswordText.setText(user.getPassword());
    		
    		mRoleSp.setSelection(roleIndex);
    		mStatusSp.setSelection(statusIndex);
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    }
    
    @Override
    protected void saveItem() {
    	
    	String name = mNameText.getText().toString();
    	String userId = mUserIdText.getText().toString();
    	String password = mPasswordText.getText().toString();
    	String role = CodeBean.getNvlCode((CodeBean) mRoleSp.getSelectedItem());
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setMerchantId(CommonUtil.getMerchantId());
    		
    		mItem.setName(name);
    		mItem.setUserId(userId);
    		mItem.setPassword(password);
    		mItem.setRole(role);
    		mItem.setStatus(status);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    	}
    }
    
    @Override
    protected Long getItemId(User user) {
    	
    	return user.getId(); 
    } 
    
    @Override
    protected void addItem() {
    	
        userDao.insert(mItem);
        mNameText.getText().clear();
        mUserIdText.getText().clear();
        
        mItem = null;
    }
    
    @Override
    protected void updateItem() {
    	
    	userDao.update(mItem);
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public User updateItem(User user) {

    	userDao.detach(user);
    	user = userDao.load(user.getId());
    	userDao.detach(user);
    	
    	this.mItem = user;
    	
    	return user;
    }
}