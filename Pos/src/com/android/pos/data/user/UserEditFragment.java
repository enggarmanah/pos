package com.android.pos.data.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.User;
import com.android.pos.dao.UserAccess;
import com.android.pos.dao.UserAccessDaoService;
import com.android.pos.dao.UserDaoService;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.UserUtil;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class UserEditFragment extends BaseEditFragment<User> {
    
	EditText mNameText;
	EditText mUserIdText;
    EditText mPasswordText;
    Spinner mRoleSp;
    Spinner mStatusSp;
    
    LinearLayout accessRightPanel;
    
    List<UserAccess> mUserAccesses;
    List<ImageButton> mCheckedButtons;
    
    CodeSpinnerArrayAdapter roleArrayAdapter;
    CodeSpinnerArrayAdapter statusArrayAdapter;
    
    private UserDaoService mUserDaoService = new UserDaoService();
    private UserAccessDaoService mUserAccessDaoService = new UserAccessDaoService();
    
    LayoutInflater mInflater;
    ViewGroup mContainer;
    
    View accessView;
    
    boolean mIsEnableInputFields;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	mInflater = inflater;
    	mContainer = container;
    	
    	View view = inflater.inflate(R.layout.data_user_fragment, container, false);
    	
    	initViewReference(view);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference(View view) {
        
    	mNameText = (EditText) view.findViewById(R.id.nameText);
    	mUserIdText = (EditText) view.findViewById(R.id.userIdText);
    	mPasswordText = (EditText) view.findViewById(R.id.passwordText);
    	mRoleSp = (Spinner) view.findViewById(R.id.roleSp);
    	mStatusSp = (Spinner) view.findViewById(R.id.statusSp);
    	
    	accessRightPanel = (LinearLayout) view.findViewById(R.id.accessRightsPanel);
    	
    	registerField(mNameText);
    	registerField(mUserIdText);
    	registerField(mPasswordText);
    	registerField(mRoleSp);
    	registerField(mStatusSp);
    	
    	enableInputFields(false);
    	
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
    		
    		accessRightPanel.removeAllViews();
    		
    		mUserAccesses = mUserAccessDaoService.getUserAccessList(user.getId());
    		
    		mCheckedButtons = new ArrayList<ImageButton>();
    		
    		for (final UserAccess userAccess : mUserAccesses) {
        		
        		accessView = mInflater.inflate(R.layout.data_user_access_list_item, mContainer, false);
        		
        		TextView nameText = (TextView) accessView.findViewById(R.id.nameText);
    			final ImageButton checkedButton = (ImageButton) accessView.findViewById(R.id.checkedButton);
    			
    			checkedButton.setEnabled(mIsEnableInputFields);
    			mCheckedButtons.add(checkedButton);
    			
    			char c = 8226;
    			
    			nameText.setText(c + " " + CodeUtil.getModuleAccessLabel(userAccess.getCode()));
    			
    			boolean isChecked = Constant.STATUS_YES.equals(userAccess.getStatus());
    			
    			if (isChecked) {
					checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_check_black));
				} else {
					checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_clear_black));
				}
    			
    			checkedButton.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					
    					if (!isEnableInputFields) {
    						return;
    					}
    					
    					boolean isSelected = Constant.STATUS_YES.equals(userAccess.getStatus());
    					
    					if (!isSelected) {
    						checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_check_black));
    						userAccess.setStatus(Constant.STATUS_YES);
    					} else {
    						checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_clear_black));
    						userAccess.setStatus(Constant.STATUS_NO);
    					}
    				}
    			});
    			
    			accessRightPanel.addView(accessView);
        	}
    		
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
    		
    		mItem.setMerchantId(MerchantUtil.getMerchantId());
    		
    		mItem.setName(name);
    		mItem.setUserId(userId);
    		mItem.setPassword(password);
    		mItem.setRole(role);
    		mItem.setStatus(status);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    		
    		String loginId = null;
    		
    		if (UserUtil.isMerchant()) {
    			loginId = MerchantUtil.getMerchant().getLoginId();
    		} else {
    			loginId = UserUtil.getUser().getUserId();
    		} 
    		
    		if (mItem.getCreateBy() == null) {
    			mItem.setCreateBy(loginId);
    			mItem.setCreateDate(new Date());
    		}
    		
    		mItem.setUpdateBy(loginId);
    		mItem.setUpdateDate(new Date());
    		
    		for (UserAccess userAccess : mUserAccesses) {
    			
    			userAccess.setUploadStatus(Constant.STATUS_YES);
        		
        		if (userAccess.getCreateBy() == null) {
        			userAccess.setCreateBy(loginId);
        			userAccess.setCreateDate(new Date());
        		}
        		
        		userAccess.setUpdateBy(loginId);
        		userAccess.setUpdateDate(new Date());
        	}
    	}
    }
    
    @Override
    protected Long getItemId(User user) {
    	
    	return user.getId(); 
    } 
    
    @Override
    protected boolean addItem() {
    	
        mUserDaoService.addUser(mItem);
        
        for (UserAccess userAccess : mUserAccesses) {
        	
        	userAccess.setUser(mItem);
        	mUserAccessDaoService.addUserAccess(userAccess);
        }
        
        mNameText.getText().clear();
        mUserIdText.getText().clear();
        
        mItem = null;
        
        return true;
    }
    
    @Override
    protected boolean updateItem() {
    	
    	mUserDaoService.updateUser(mItem);
		mUserAccessDaoService.updateUserAccess(mUserAccesses);
    	
    	return true;
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public User updateItem(User user) {

    	user = mUserDaoService.getUser(user.getId());
    	
    	this.mItem = user;
    	
    	return user;
    }
    
    @Override
    public void enableInputFields(boolean isEnabled) {
    	
    	super.enableInputFields(isEnabled);
    	
    	mIsEnableInputFields = isEnabled;
    	
    	if (mCheckedButtons == null) {
    		return;
    	}
    	
    	for (ImageButton button : mCheckedButtons) {
    		button.setEnabled(isEnabled);
    	}
    }
}