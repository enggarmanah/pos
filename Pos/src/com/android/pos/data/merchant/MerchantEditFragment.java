package com.android.pos.data.merchant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import com.android.pos.model.PaymentTypeStatus;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    EditText mPriceLabel1Text;
    EditText mPriceLabel2Text;
    EditText mPriceLabel3Text;
    
    LinearLayout mPriceLabel1Panel;
    LinearLayout mPriceLabel2Panel;
    LinearLayout mPriceLabel3Panel;
    
    Spinner mPriceTypeCountSp;
    Spinner mDiscountTypeSp;
    Spinner mStatusSp;
    
    LinearLayout mStatusPanel;
    LinearLayout paymentTypePanel;
    LinearLayout accessRightPanel;
    LinearLayout accessRightRowPanel;
    
    List<PaymentTypeStatus> mPaymentTypeStatuses;
    List<MerchantAccess> mMerchantAccesses;
    
    List<ImageButton> mPaymentTypeButtons;
    List<ImageButton> mAccessButtons;
    
    CodeSpinnerArrayAdapter statusArrayAdapter;
    CodeSpinnerArrayAdapter typeArrayAdapter;
    CodeSpinnerArrayAdapter discountTypeArrayAdapter;
    CodeSpinnerArrayAdapter priceTypeCountArrayAdapter;
    
    private MerchantDaoService mMerchantDaoService = new MerchantDaoService();
    private MerchantAccessDaoService mMerchantAccessDaoService = new MerchantAccessDaoService();
    
    LayoutInflater mInflater;
    ViewGroup mContainer;
    
    View accessView;
    
    boolean mIsEnableInputFields;
    
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
    	mPriceLabel1Text = (EditText) view.findViewById(R.id.priceLabel1Text);
    	mPriceLabel2Text = (EditText) view.findViewById(R.id.priceLabel2Text);
    	mPriceLabel3Text = (EditText) view.findViewById(R.id.priceLabel3Text);
    	mTaxText = (EditText) view.findViewById(R.id.taxText);
    	mServiceChargeText = (EditText) view.findViewById(R.id.serviceChargeText);
    	mPriceTypeCountSp = (Spinner) view.findViewById(R.id.priceTypeCountSp);
    	mDiscountTypeSp = (Spinner) view.findViewById(R.id.discountTypeSp);
    	mStatusSp = (Spinner) view.findViewById(R.id.statusSp);
    	
    	mPriceTypeCountSp.setOnItemSelectedListener(getPriceTypeCountOnItemSelectedListener());
    	
    	mStatusPanel = (LinearLayout) view.findViewById(R.id.statusPanel);
    	
    	mPriceLabel1Panel = (LinearLayout) view.findViewById(R.id.priceLabel1Panel);
    	mPriceLabel2Panel = (LinearLayout) view.findViewById(R.id.priceLabel2Panel);
    	mPriceLabel3Panel = (LinearLayout) view.findViewById(R.id.priceLabel3Panel);
    	
    	paymentTypePanel = (LinearLayout) view.findViewById(R.id.paymentTypePanel);
    	
    	accessRightRowPanel = (LinearLayout) view.findViewById(R.id.accessRightsRowPanel);
    	accessRightPanel = (LinearLayout) view.findViewById(R.id.accessRightsPanel);
    	
    	registerRootField(mNameText);
    	registerRootField(mTypeSp);
    	registerRootField(mAddressText);
    	registerRootField(mTelephoneText);
    	registerRootField(mPeriodStartDate);
    	registerRootField(mPeriodEndDate);
    	
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
    	registerField(mPriceLabel1Text);
    	registerField(mPriceLabel2Text);
    	registerField(mPriceLabel3Text);
    	registerField(mTaxText);
    	registerField(mPriceTypeCountSp);
    	registerField(mDiscountTypeSp);
    	registerField(mServiceChargeText);
    	registerField(mStatusSp);
    	
    	enableInputFields(false);
    	
    	mandatoryFields = new ArrayList<MerchantEditFragment.FormField>();
    	
    	mPeriodStartDate.setOnClickListener(getDateFieldOnClickListener("startDatePicker"));
        mPeriodEndDate.setOnClickListener(getDateFieldOnClickListener("endDatePicker"));
		
    	linkDatePickerWithInputField("startDatePicker", mPeriodStartDate);
    	linkDatePickerWithInputField("endDatePicker", mPeriodEndDate);
    	
    	typeArrayAdapter = new CodeSpinnerArrayAdapter(mTypeSp, getActivity(), CodeUtil.getMerchantTypes());
    	mTypeSp.setAdapter(typeArrayAdapter);
    	
    	discountTypeArrayAdapter = new CodeSpinnerArrayAdapter(mDiscountTypeSp, getActivity(), CodeUtil.getDiscountTypes());
    	mDiscountTypeSp.setAdapter(discountTypeArrayAdapter);
    	
    	priceTypeCountArrayAdapter = new CodeSpinnerArrayAdapter(mPriceTypeCountSp, getActivity(), CodeUtil.getPriceTypeCount());
    	mPriceTypeCountSp.setAdapter(priceTypeCountArrayAdapter);
    	
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
    		int priceTypeCountIndex = priceTypeCountArrayAdapter.getPosition(String.valueOf(merchant.getPriceTypeCount()));
    		int discountTypeIndex = discountTypeArrayAdapter.getPosition(merchant.getDiscountType());
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
    		mPriceLabel1Text.setText(merchant.getPriceLabel1());
    		mPriceLabel2Text.setText(merchant.getPriceLabel2());
    		mPriceLabel3Text.setText(merchant.getPriceLabel3());
    		
    		mTaxText.setText(CommonUtil.formatString(merchant.getTaxPercentage()));
    		mServiceChargeText.setText(CommonUtil.formatString(merchant.getServiceChargePercentage()));
    		
    		mTypeSp.setSelection(typeIndex);
    		mPriceTypeCountSp.setSelection(priceTypeCountIndex);
    		mDiscountTypeSp.setSelection(discountTypeIndex);
    		mStatusSp.setSelection(statusIndex);
    		
    		if (!UserUtil.isRoot()) {
    			accessRightRowPanel.setVisibility(View.GONE);
    		} else {
    			accessRightRowPanel.setVisibility(View.VISIBLE);
    		}
    		
    		paymentTypePanel.removeAllViews();
    		
    		mPaymentTypeButtons = new ArrayList<ImageButton>();
    		
    		mPaymentTypeStatuses = getPaymentStatuses();
    		
    		for (final PaymentTypeStatus paymentTypeStatus : mPaymentTypeStatuses) {
        		
        		View paymentTypeView = mInflater.inflate(R.layout.data_merchant_access_list_item, mContainer, false);
        		
        		TextView nameText = (TextView) paymentTypeView.findViewById(R.id.nameText);
    			final ImageButton paymentTypeButton = (ImageButton) paymentTypeView.findViewById(R.id.checkedButton);
    			
    			paymentTypeButton.setEnabled(mIsEnableInputFields);
    			mPaymentTypeButtons.add(paymentTypeButton);
    			
    			char c = 8226;
    			
    			nameText.setText(c + " " + paymentTypeStatus.getPaymentType().getLabel());
    			
    			boolean isChecked = Constant.STATUS_YES.equals(paymentTypeStatus.getStatus());
    			
    			if (isChecked) {
					paymentTypeButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_check_black));
				} else {
					paymentTypeButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_clear_black));
				}
    			
    			paymentTypeButton.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					
    					if (!isEnableInputFields) {
    						return;
    					}
    					
    					boolean isSelected = Constant.STATUS_YES.equals(paymentTypeStatus.getStatus());
    					
    					if (!isSelected) {
    						paymentTypeButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_check_black));
    						paymentTypeStatus.setStatus(Constant.STATUS_YES);
    					} else {
    						paymentTypeButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_clear_black));
    						paymentTypeStatus.setStatus(Constant.STATUS_NO);
    					}
    				}
    			});
    			
    			paymentTypePanel.addView(paymentTypeView);
        	}
    		
    		accessRightPanel.removeAllViews();
    		
    		mMerchantAccesses = mMerchantAccessDaoService.getMerchantAccessList(merchant.getId());
    		
    		mAccessButtons = new ArrayList<ImageButton>();
    		
    		for (final MerchantAccess merchantAccess : mMerchantAccesses) {
        		
        		accessView = mInflater.inflate(R.layout.data_merchant_access_list_item, mContainer, false);
        		
        		TextView nameText = (TextView) accessView.findViewById(R.id.nameText);
    			final ImageButton checkedButton = (ImageButton) accessView.findViewById(R.id.checkedButton);
    			
    			checkedButton.setEnabled(mIsEnableInputFields);
    			mAccessButtons.add(checkedButton);
    			
    			char c = 8226;
    			
    			nameText.setText(c + " " + CodeUtil.getModuleAccessLabel(merchantAccess.getCode()));
    			
    			boolean isChecked = Constant.STATUS_YES.equals(merchantAccess.getStatus());
    			
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
    					
    					boolean isSelected = Constant.STATUS_YES.equals(merchantAccess.getStatus());
    					
    					if (!isSelected) {
    						checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_check_black));
    						merchantAccess.setStatus(Constant.STATUS_YES);
    					} else {
    						checkedButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_clear_black));
    						merchantAccess.setStatus(Constant.STATUS_NO);
    					}
    				}
    			});
    			
    			accessRightPanel.addView(accessView);
        	}
    		
    		refreshVisibleField();
    		
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
    	Float tax = CommonUtil.parseFloat(mTaxText.getText().toString());
    	Float serviceCharge = CommonUtil.parseFloat(mServiceChargeText.getText().toString());
    	Date startDate = CommonUtil.parseDate(mPeriodStartDate.getText().toString());
    	Date endDate = CommonUtil.parseDate(mPeriodEndDate.getText().toString());
    	Integer priceTypeCount = Integer.valueOf(CodeBean.getNvlCode((CodeBean) mPriceTypeCountSp.getSelectedItem()));
    	String priceLabel1 = mPriceLabel1Text.getText().toString();
    	String priceLabel2 = mPriceLabel2Text.getText().toString();
    	String priceLabel3 = mPriceLabel3Text.getText().toString();
    	String discountType = CodeBean.getNvlCode((CodeBean) mDiscountTypeSp.getSelectedItem());
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
    		mItem.setPriceTypeCount(priceTypeCount);
    		mItem.setPriceLabel1(priceLabel1);
    		mItem.setPriceLabel2(priceLabel2);
    		mItem.setPriceLabel3(priceLabel3);
    		mItem.setTaxPercentage(tax);
    		mItem.setServiceChargePercentage(serviceCharge);
    		mItem.setDiscountType(discountType);
    		mItem.setStatus(status);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    		
    		String userId = UserUtil.getUser().getUserId();
    		
    		if (mItem.getCreateBy() == null) {
    			mItem.setCreateBy(userId);
    			mItem.setCreateDate(new Date());
    		}
    		
    		mItem.setUpdateBy(userId);
    		mItem.setUpdateDate(new Date());
    		
    		String paymentType = Constant.EMPTY_STRING;
    		
    		for (PaymentTypeStatus paymentTypeStatus : mPaymentTypeStatuses) {
    			
    			if (Constant.STATUS_YES.equals(paymentTypeStatus.getStatus())) {
    				
    				paymentType += !CommonUtil.isEmpty(paymentType) ? Constant.DATA_SEPARATOR : Constant.EMPTY_STRING; 
    				paymentType += paymentTypeStatus.getPaymentType().getCode();
    			}
    		}
    		
    		mItem.setPaymentType(paymentType);
    		
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
	        
	        for (MerchantAccess merchantAccess : mMerchantAccesses) {
	        	
	        	merchantAccess.setMerchant(mItem);
	        	mMerchantAccessDaoService.addMerchantAccess(merchantAccess);
	        }
	        
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
	        mPriceLabel1Text.getText().clear();
	        mPriceLabel2Text.getText().clear();
	        mPriceLabel3Text.getText().clear();
	        mTaxText.getText().clear();
	        mServiceChargeText.getText().clear();
	        
	        mItem = null;
	        
	        isUpdated = true;
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
    		
    		if (MerchantUtil.getMerchantId() == mItem.getId()) {
    			MerchantUtil.setMerchant(mItem);
    		}
    		
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
    	
    	mIsEnableInputFields = isEnabled;
    	
    	if (mAccessButtons == null) {
    		return;
    	}
    	
    	for (ImageButton button : mPaymentTypeButtons) {
    		button.setEnabled(isEnabled);
    	}
    	
    	for (ImageButton button : mAccessButtons) {
    		button.setEnabled(isEnabled);
    	}
    }
    
    private AdapterView.OnItemSelectedListener getPriceTypeCountOnItemSelectedListener() {
    	
    	return new AdapterView.OnItemSelectedListener() {
			
        	@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
        		refreshVisibleField();
        	}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
    }
    
    private void refreshVisibleField() {
    	
    	Integer priceTypeCount = Integer.valueOf(CodeBean.getNvlCode((CodeBean) mPriceTypeCountSp.getSelectedItem()));
    	
    	mPriceLabel1Panel.setVisibility(View.GONE);
    	mPriceLabel2Panel.setVisibility(View.GONE);
    	mPriceLabel3Panel.setVisibility(View.GONE);
    	
		mandatoryFields.clear();
		mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	mandatoryFields.add(new FormField(mAddressText, R.string.field_address));
    	mandatoryFields.add(new FormField(mContactNameText, R.string.field_contact_name));
    	mandatoryFields.add(new FormField(mContactTelephoneText, R.string.field_contact_telephone));
    	mandatoryFields.add(new FormField(mLoginIdText, R.string.field_login_id));
    	mandatoryFields.add(new FormField(mPasswordText, R.string.field_password));
    	mandatoryFields.add(new FormField(mPeriodStartDate, R.string.field_period_start));
    	mandatoryFields.add(new FormField(mPeriodEndDate, R.string.field_period_end));
    	mandatoryFields.add(new FormField(mTaxText, R.string.field_tax_percentage));
    	mandatoryFields.add(new FormField(mServiceChargeText, R.string.field_service_charge_percentage));
		
    	if (priceTypeCount == 2) {
    		
    		mPriceLabel1Panel.setVisibility(View.VISIBLE);
    		mPriceLabel2Panel.setVisibility(View.VISIBLE);	
    		
    		mandatoryFields.add(new FormField(mPriceLabel1Text, R.string.field_price_label_1));
    		mandatoryFields.add(new FormField(mPriceLabel2Text, R.string.field_price_label_2));
    		
    	} else if (priceTypeCount == 3) {
    		
    		mPriceLabel1Panel.setVisibility(View.VISIBLE);
    		mPriceLabel2Panel.setVisibility(View.VISIBLE);	
    		mPriceLabel3Panel.setVisibility(View.VISIBLE);
    		
    		mandatoryFields.add(new FormField(mPriceLabel1Text, R.string.field_price_label_1));
    		mandatoryFields.add(new FormField(mPriceLabel2Text, R.string.field_price_label_2));
    		mandatoryFields.add(new FormField(mPriceLabel3Text, R.string.field_price_label_3));
    	}
    	
    	highlightMandatoryFields();
    }
    
    private List<PaymentTypeStatus> getPaymentStatuses() {
    	
    	String merchantPaymentType = mItem.getPaymentType();
    	
    	String[] merchantPaymentTypeStatus = !CommonUtil.isEmpty(merchantPaymentType) ? merchantPaymentType.split(Constant.DATA_SEPARATOR) : null;
    	HashMap<String, String> merchantPaymentTypeStatuses = new HashMap<String, String>();
    	
    	if (merchantPaymentTypeStatus != null) {
	    	
    		for (String paymentTypeStatus : merchantPaymentTypeStatus) {
	    		merchantPaymentTypeStatuses.put(paymentTypeStatus, Constant.STATUS_YES);
	    	}
    	}
    	
    	List<PaymentTypeStatus> paymentTypeStatuses = new ArrayList<PaymentTypeStatus>();
    	List<CodeBean> paymentTypes = Arrays.asList(CodeUtil.getPaymentTypes());
    	
    	for (CodeBean paymentType : paymentTypes) {
    		
    		PaymentTypeStatus paymentTypeStatus = new PaymentTypeStatus();
        	paymentTypeStatus.setPaymentType(paymentType);
        	
        	if (merchantPaymentTypeStatuses.get(paymentType.getCode()) != null) {
        		paymentTypeStatus.setStatus(Constant.STATUS_YES);
        	} else {
        		paymentTypeStatus.setStatus(Constant.STATUS_NO);
        	}
        	
        	paymentTypeStatuses.add(paymentTypeStatus);
    	}
    	
    	return paymentTypeStatuses;
    }
}