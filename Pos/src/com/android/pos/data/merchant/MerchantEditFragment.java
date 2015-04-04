package com.android.pos.data.merchant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantAccess;
import com.android.pos.dao.MerchantAccessDaoService;
import com.android.pos.dao.MerchantDaoService;
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
import android.widget.ImageButton;
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
    EditText mContactEmailText;
    EditText mLoginIdText;
    EditText mPasswordText;
    EditText mPeriodStartDate;
    EditText mPeriodEndDate;
    EditText mTaxText;
    EditText mServiceChargeText;
    EditText mPrinterLineSizeText;
    
    Spinner mPrinterRequiredSp;
    Spinner mPrinterMiniFontSp;
    Spinner mStatusSp;
    
    LinearLayout mStatusPanel;
    LinearLayout accessRightPanel;
    LinearLayout accessRightRowPanel;
    
    List<MerchantAccess> mMerchantAccesses;
    List<ImageButton> mCheckedButtons;
    
    CodeSpinnerArrayAdapter statusArrayAdapter;
    CodeSpinnerArrayAdapter typeArrayAdapter;
    CodeSpinnerArrayAdapter printerRequiredArrayAdapter;
    CodeSpinnerArrayAdapter printerMiniFontArrayAdapter;
    
    private MerchantDaoService mMerchantDaoService = new MerchantDaoService();
    private MerchantAccessDaoService mMerchantAccessDaoService = new MerchantAccessDaoService();
    
    LayoutInflater mInflater;
    ViewGroup mContainer;
    
    View accessView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	mInflater = inflater;
    	mContainer = container;
    	
    	View view = inflater.inflate(R.layout.data_merchant_fragment, container, false);
    	
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
    	mTypeSp = (Spinner) view.findViewById(R.id.typeSp);
    	mAddressText = (EditText) view.findViewById(R.id.addressText);
    	mTelephoneText = (EditText) view.findViewById(R.id.telephoneText);
    	mContactNameText = (EditText) view.findViewById(R.id.contactNameText);
    	mContactTelephoneText = (EditText) view.findViewById(R.id.contactTelephoneText);
    	mContactEmailText = (EditText) view.findViewById(R.id.contactEmailText);
    	mLoginIdText = (EditText) view.findViewById(R.id.loginIdText);
    	mPasswordText = (EditText) view.findViewById(R.id.passwordText);
    	mPeriodStartDate = (EditText) view.findViewById(R.id.periodStartDate);
    	mPeriodEndDate = (EditText) view.findViewById(R.id.periodEndDate);
    	mTaxText = (EditText) view.findViewById(R.id.taxText);
    	mServiceChargeText = (EditText) view.findViewById(R.id.serviceChargeText);
    	mPrinterRequiredSp = (Spinner) view.findViewById(R.id.printerRequiredSp);
    	mPrinterMiniFontSp = (Spinner) view.findViewById(R.id.printerMiniFontSp);
    	mPrinterLineSizeText = (EditText) view.findViewById(R.id.printerLineSizeText);
    	mStatusSp = (Spinner) view.findViewById(R.id.statusSp);

    	mStatusPanel = (LinearLayout) view.findViewById(R.id.statusPanel);
    	
    	accessRightRowPanel = (LinearLayout) view.findViewById(R.id.accessRightsRowPanel);
    	accessRightPanel = (LinearLayout) view.findViewById(R.id.accessRightsPanel);
    	
    	registerField(mNameText);
    	registerField(mTypeSp);
    	registerField(mAddressText);
    	registerField(mTelephoneText);
    	registerField(mContactNameText);
    	registerField(mContactTelephoneText);
    	registerField(mContactEmailText);
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
    	mandatoryFields.add(new FormField(mPrinterLineSizeText, R.string.field_printer_line_size));
    	
    	// only root can access validity period
    	
    	if (UserUtil.isRoot()) {
    		
    		mPeriodStartDate.setOnClickListener(getDateFieldOnClickListener(mPeriodStartDate, "startDatePicker"));
        	mPeriodEndDate.setOnClickListener(getDateFieldOnClickListener(mPeriodEndDate, "endDatePicker"));
		}
    	
    	linkDatePickerWithInputField("startDatePicker", mPeriodStartDate);
    	linkDatePickerWithInputField("endDatePicker", mPeriodEndDate);
    	
    	typeArrayAdapter = new CodeSpinnerArrayAdapter(mTypeSp, getActivity(), CodeUtil.getMerchantTypes());
    	mTypeSp.setAdapter(typeArrayAdapter);
    	
    	printerRequiredArrayAdapter = new CodeSpinnerArrayAdapter(mPrinterRequiredSp, getActivity(), CodeUtil.getStatus());
    	mPrinterRequiredSp.setAdapter(printerRequiredArrayAdapter);
    	
    	printerMiniFontArrayAdapter = new CodeSpinnerArrayAdapter(mPrinterMiniFontSp, getActivity(), CodeUtil.getBooleans());
    	mPrinterMiniFontSp.setAdapter(printerMiniFontArrayAdapter);
    	
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
    		int printerRequiredIndex = printerRequiredArrayAdapter.getPosition(merchant.getPrinterRequired());
    		int printerMiniFontIndex = printerMiniFontArrayAdapter.getPosition(merchant.getPrinterMiniFont());
    		int statusIndex = statusArrayAdapter.getPosition(merchant.getStatus());
    		
    		mNameText.setText(merchant.getName());
    		mAddressText.setText(merchant.getAddress());
    		mTelephoneText.setText(merchant.getTelephone());
    		mContactNameText.setText(merchant.getContactName());
    		mContactTelephoneText.setText(merchant.getContactTelephone());
    		mContactEmailText.setText(merchant.getContactEmail());
    		mLoginIdText.setText(merchant.getLoginId());
    		mPasswordText.setText(merchant.getPassword());
    		mPeriodStartDate.setText(CommonUtil.formatDate(merchant.getPeriodStart()));
    		mPeriodEndDate.setText(CommonUtil.formatDate(merchant.getPeriodEnd()));
    		
    		mTaxText.setText(CommonUtil.formatString(merchant.getTaxPercentage()));
    		mServiceChargeText.setText(CommonUtil.formatString(merchant.getServiceChargePercentage()));
    		mPrinterLineSizeText.setText(CommonUtil.formatString(merchant.getPrinterLineSize()));
    		
    		mTypeSp.setSelection(typeIndex);
    		mPrinterRequiredSp.setSelection(printerRequiredIndex);
    		mPrinterMiniFontSp.setSelection(printerMiniFontIndex);
    		mStatusSp.setSelection(statusIndex);
    		
    		if (!UserUtil.isRoot()) {
    			accessRightRowPanel.setVisibility(View.GONE);
    		} else {
    			accessRightRowPanel.setVisibility(View.VISIBLE);
    		}
    		
    		accessRightPanel.removeAllViews();
    		
    		mMerchantAccesses = mMerchantAccessDaoService.getMerchantAccessList(merchant.getId());
    		
    		mCheckedButtons = new ArrayList<ImageButton>();
    		
    		for (final MerchantAccess merchantAccess : mMerchantAccesses) {
        		
        		accessView = mInflater.inflate(R.layout.data_merchant_access_list_item, mContainer, false);
        		
        		TextView nameText = (TextView) accessView.findViewById(R.id.nameText);
    			final ImageButton checkedButton = (ImageButton) accessView.findViewById(R.id.checkedButton);
    			
    			checkedButton.setEnabled(false);
    			mCheckedButtons.add(checkedButton);
    			
    			nameText.setText(merchantAccess.getName());
    			
    			boolean isChecked = Constant.STATUS_YES.equals(merchantAccess.getStatus());
    			
    			if (isChecked) {
					checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.action_checked_black));
				} else {
					checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.action_cancel_black));
				}
    			
    			checkedButton.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					
    					if (!isEnableInputFields) {
    						return;
    					}
    					
    					boolean isSelected = Constant.STATUS_YES.equals(merchantAccess.getStatus());
    					
    					if (!isSelected) {
    						checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.action_checked_black));
    						merchantAccess.setStatus(Constant.STATUS_YES);
    					} else {
    						checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.action_cancel_black));
    						merchantAccess.setStatus(Constant.STATUS_NO);
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
    	String type = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
    	String address = mAddressText.getText().toString();
    	String telephone = mTelephoneText.getText().toString();
    	String contactName = mContactNameText.getText().toString();
    	String contactTelephone = mContactTelephoneText.getText().toString();
    	String contactEmail = mContactEmailText.getText().toString();
    	String loginId = mLoginIdText.getText().toString();
    	String password = mPasswordText.getText().toString();
    	Integer tax = CommonUtil.parseInteger(mTaxText.getText().toString());
    	Integer serviceCharge = CommonUtil.parseInteger(mServiceChargeText.getText().toString());
    	Date startDate = CommonUtil.parseDate(mPeriodStartDate.getText().toString());
    	Date endDate = CommonUtil.parseDate(mPeriodEndDate.getText().toString());
    	String printerRequired = CodeBean.getNvlCode((CodeBean) mPrinterRequiredSp.getSelectedItem());
    	String printerMiniFont = CodeBean.getNvlCode((CodeBean) mPrinterMiniFontSp.getSelectedItem());
    	Integer printerLineSize = CommonUtil.parseInteger(mPrinterLineSizeText.getText().toString());
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setName(name);
    		mItem.setType(type);
    		mItem.setAddress(address);
    		mItem.setTelephone(telephone);
    		mItem.setContactName(contactName);
    		mItem.setContactTelephone(contactTelephone);
    		mItem.setContactEmail(contactEmail);
    		mItem.setLoginId(loginId);
    		mItem.setPassword(password);
    		mItem.setPeriodStart(startDate);
    		mItem.setPeriodEnd(endDate);
    		mItem.setTaxPercentage(tax);
    		mItem.setServiceChargePercentage(serviceCharge);
    		mItem.setPrinterRequired(printerRequired);
    		mItem.setPrinterMiniFont(printerMiniFont);
    		mItem.setPrinterLineSize(printerLineSize);
    		mItem.setStatus(status);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    		
    		String userId = UserUtil.getUser().getUserId();
    		
    		if (mItem.getCreateBy() == null) {
    			mItem.setCreateBy(userId);
    			mItem.setCreateDate(new Date());
    		}
    		
    		mItem.setUpdateBy(userId);
    		mItem.setUpdateDate(new Date());
    		
    		for (MerchantAccess merchantAccess : mMerchantAccesses) {
    			
    			merchantAccess.setUploadStatus(Constant.STATUS_YES);
    			
        		if (merchantAccess.getCreateBy() == null) {
        			merchantAccess.setCreateBy(userId);
        			merchantAccess.setCreateDate(new Date());
        		}
        		
        		merchantAccess.setUpdateBy(userId);
        		merchantAccess.setUpdateDate(new Date());
        	}
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
	        mContactEmailText.getText().clear();
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
    		mMerchantAccessDaoService.updateMerchantAccess(mMerchantAccesses);
    		
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
    
    @Override
    public void enableInputFields(boolean isEnabled) {
    	
    	super.enableInputFields(isEnabled);
    	
    	if (mCheckedButtons == null) {
    		return;
    	}
    	
    	for (ImageButton button : mCheckedButtons) {
    		button.setEnabled(isEnabled);
    	}
    }
}