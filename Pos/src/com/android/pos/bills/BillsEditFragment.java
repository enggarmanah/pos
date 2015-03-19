package com.android.pos.bills;

import java.util.ArrayList;
import java.util.Date;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Bills;
import com.android.pos.dao.BillsDaoService;
import com.android.pos.dao.Supplier;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.UserUtil;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class BillsEditFragment extends BaseEditFragment<Bills> {
    
	Spinner mTypeSp;
    EditText mSupplierNameText;
    EditText mBillReferenceNoText;
    EditText mBillDate;
    EditText mBillDueDate;
    EditText mBillAmountText;
    Spinner mStatusSp;
    EditText mPaymentDate;
    EditText mPaymentText;
    EditText mDeliveryDate;
    EditText mRemarksText;
    
    LinearLayout mSupplierPanel;
    LinearLayout mBillReferenceNoPanel;
    LinearLayout mBillDatePanel;
    LinearLayout mBillDueDatePanel;
    LinearLayout mBillAmountPanel;
    LinearLayout mBillStatusPanel;
    LinearLayout mPaymentDatePanel;
    LinearLayout mPaymentAmountPanel;
    LinearLayout mDeliveryDatePanel;
    
    CodeSpinnerArrayAdapter typeArrayAdapter;
    CodeSpinnerArrayAdapter statusArrayAdapter;
    
    private BillsDaoService mBillsDaoService = new BillsDaoService();
    
    BaseItemListener<Bills> mBillsItemListener;
    
    String mBillType;
    String mBillStatus;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.data_bills_fragment, container, false);
    	
    	return view;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
        	mBillsItemListener = (BaseItemListener<Bills>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<T>");
        }
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
    	mTypeSp = (Spinner) getView().findViewById(R.id.typeSp);
    	mStatusSp = (Spinner) getView().findViewById(R.id.statusSp);
    	
    	mSupplierNameText = (EditText) getView().findViewById(R.id.supplierNameText);
    	mBillReferenceNoText = (EditText) getView().findViewById(R.id.billsReferenceNoText);
    	mBillDate = (EditText) getView().findViewById(R.id.billsDate);
    	mBillDueDate = (EditText) getView().findViewById(R.id.billsDueDate);
    	mBillAmountText = (EditText) getView().findViewById(R.id.billsAmountText);
    	mPaymentDate = (EditText) getView().findViewById(R.id.paymentDate);
    	mPaymentText = (EditText) getView().findViewById(R.id.paymentText);
    	mDeliveryDate = (EditText) getView().findViewById(R.id.deliveryDate);
    	mRemarksText = (EditText) getView().findViewById(R.id.remarksText);
    	
    	registerField(mTypeSp);
    	registerField(mSupplierNameText);
    	registerField(mBillReferenceNoText);
    	registerField(mBillDate);
    	registerField(mBillDueDate);
    	registerField(mBillAmountText);
    	registerField(mStatusSp);
    	registerField(mPaymentDate);
    	registerField(mPaymentText);
    	registerField(mDeliveryDate);
    	registerField(mRemarksText);
    	
    	enableInputFields(false);
    	
    	mandatoryFields = new ArrayList<BillsEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mBillDate, R.string.field_bills_date));
    	mandatoryFields.add(new FormField(mBillAmountText, R.string.field_total));
    	mandatoryFields.add(new FormField(mRemarksText, R.string.field_remarks));
    	
    	mBillAmountText.setOnFocusChangeListener(getCurrencyFieldOnFocusChangeListener(mBillAmountText));
    	mPaymentText.setOnFocusChangeListener(getCurrencyFieldOnFocusChangeListener(mPaymentText));
    	
    	typeArrayAdapter = new CodeSpinnerArrayAdapter(mTypeSp, getActivity(), CodeUtil.getBillTypes());
    	mTypeSp.setAdapter(typeArrayAdapter);
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getBillStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    	
    	mBillDate.setOnClickListener(getDateFieldOnClickListener(mBillDate, "billsDatePicker"));
    	mBillDueDate.setOnClickListener(getDateFieldOnClickListener(mBillDueDate, "billsDueDatePicker"));
    	mPaymentDate.setOnClickListener(getDateFieldOnClickListener(mPaymentDate, "paymentDatePicker"));
    	mDeliveryDate.setOnClickListener(getDateFieldOnClickListener(mDeliveryDate, "deliveryDatePicker"));
    	
    	linkDatePickerWithInputField("billsDatePicker", mBillDate);
    	linkDatePickerWithInputField("billsDueDatePicker", mBillDueDate);
    	linkDatePickerWithInputField("paymentDatePicker", mPaymentDate);
    	linkDatePickerWithInputField("deliveryDatePicker", mDeliveryDate);
    	
    	mSupplierNameText.setFocusable(false);
    	mSupplierNameText.setOnClickListener(getSupplierOnClickListener());
    	
    	mSupplierPanel = (LinearLayout) getView().findViewById(R.id.supplierPanel);
        mBillReferenceNoPanel = (LinearLayout) getView().findViewById(R.id.billReferenceNoPanel);
        mBillDatePanel = (LinearLayout) getView().findViewById(R.id.billDatePanel);
        mBillDueDatePanel = (LinearLayout) getView().findViewById(R.id.billDueDatePanel);
        mBillAmountPanel = (LinearLayout) getView().findViewById(R.id.billAmountPanel);
        mBillStatusPanel = (LinearLayout) getView().findViewById(R.id.billStatusPanel);
        mPaymentDatePanel = (LinearLayout) getView().findViewById(R.id.paymentDatePanel);
        mPaymentAmountPanel = (LinearLayout) getView().findViewById(R.id.paymentAmountPanel);
        mDeliveryDatePanel = (LinearLayout) getView().findViewById(R.id.deliveryDatePanel);
        
        mTypeSp.setOnItemSelectedListener(getTypeOnItemSelectedListener());
        mStatusSp.setOnItemSelectedListener(getStatusOnItemSelectedListener());
    }
    
    @Override
    protected void updateView(Bills bills) {
    	
    	if (bills != null) {
    		
    		int typeIndex = typeArrayAdapter.getPosition(bills.getBillType());
    		mTypeSp.setSelection(typeIndex);
    		
    		int statusIndex = statusArrayAdapter.getPosition(bills.getStatus());
    		mStatusSp.setSelection(statusIndex);
    		
    		mSupplierNameText.setText(bills.getSupplierName());
    		mBillReferenceNoText.setText(bills.getBillReferenceNo());
    		mBillDate.setText(CommonUtil.formatDate(bills.getBillDate()));
    		mBillDueDate.setText(CommonUtil.formatDate(bills.getBillDueDate()));
    		mBillAmountText.setText(CommonUtil.formatCurrency(bills.getBillAmount()));
    		mPaymentDate.setText(CommonUtil.formatDate(bills.getPaymentDate()));
    		mPaymentText.setText(CommonUtil.formatCurrency(bills.getPayment()));
    		mDeliveryDate.setText(CommonUtil.formatDate(bills.getDeliveryDate()));
    		mRemarksText.setText(bills.getRemarks());
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    	
    	mBillType = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
        mBillStatus = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
        
        refreshVisibleField();
    }
    
    @Override
    protected void saveItem() {
    	
    	String type = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
    	String supplierName = mSupplierNameText.getText().toString();
    	String billReferenceNo = mBillReferenceNoText.getText().toString();
    	Date billDate = CommonUtil.parseDate(mBillDate.getText().toString());
    	Date billDueDate = CommonUtil.parseDate(mBillDueDate.getText().toString());
    	Integer billAmount = CommonUtil.parseCurrency(mBillAmountText.getText().toString());
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	Date paymentDate = CommonUtil.parseDate(mPaymentDate.getText().toString());
    	Integer payment = CommonUtil.parseCurrency(mPaymentText.getText().toString());
    	Date deliveryDate = CommonUtil.parseDate(mDeliveryDate.getText().toString());
    	String remarks = mRemarksText.getText().toString();
    	
    	if (mItem != null) {
    		
    		mItem.setMerchantId(MerchantUtil.getMerchantId());
    		
    		mItem.setBillType(type);
    		mItem.setSupplierName(supplierName);
    		mItem.setBillReferenceNo(billReferenceNo);
    		mItem.setBillDate(billDate);
    		mItem.setBillDueDate(billDueDate);
    		mItem.setBillAmount(billAmount);
    		mItem.setStatus(status);
    		mItem.setPaymentDate(paymentDate);
    		mItem.setPayment(payment);
    		mItem.setDeliveryDate(deliveryDate);
    		mItem.setRemarks(remarks);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    		
    		String loginId = UserUtil.getUser().getUserId();
    		
    		if (mItem.getCreateBy() == null) {
    			mItem.setCreateBy(loginId);
    			mItem.setCreateDate(new Date());
    		}
    		
    		mItem.setUpdateBy(loginId);
    		mItem.setUpdateDate(new Date());
    	}
    }
    
    @Override
    protected Long getItemId(Bills bills) {
    	
    	return bills.getId(); 
    } 
    
    @Override
    protected boolean addItem() {
    	
        mBillsDaoService.addBills(mItem);
        
        mSupplierNameText.getText().clear();
        mBillReferenceNoText.getText().clear();
        mBillDate.getText().clear();
        mBillDueDate.getText().clear();
        mBillAmountText.getText().clear();
        mPaymentDate.getText().clear();
        mPaymentText.getText().clear();
        mDeliveryDate.getText().clear();
        mRemarksText.getText().clear();
        
        mItem = null;
        
        return true;
    }
    
    @Override
    protected boolean updateItem() {
    	
    	int payment = CommonUtil.getNvl(mItem.getPayment());
		int billAmount = CommonUtil.getNvl(mItem.getBillAmount());
		
		mItem.setPayment(payment);
		mItem.setBillAmount(billAmount);
    	
    	mBillsDaoService.updateBills(mItem);
    	
    	return true;
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return null;
    }
    
    @Override
    public Bills updateItem(Bills bills) {

    	bills = mBillsDaoService.getBills(bills.getId());
    	
    	this.mItem = bills;
    	
    	return bills;
    }
    
    public void setSupplier(Supplier supplier) {
		
    	if (mItem != null) {
    		
    		if (supplier != null) {
    			
    			mItem.setSupplierId(supplier.getId());
    			mItem.setSupplierName(supplier.getName());
    			
    		} else {
    			
    			mItem.setSupplier(null);
    			mItem.setSupplierName(Constant.EMPTY_STRING);	
    		}
    		
    		updateView(mItem);
    	}
	}
    
    @Override
    protected boolean isValidated() {
    	
    	String type = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
    	
    	if (Constant.BILL_TYPE_EXPENSE_WITHOUT_RECEIPT.equals(type)) {
    		mBillDate.setText(mPaymentDate.getText().toString());
    		mBillAmountText.setText(mPaymentText.getText().toString());
    	}
    	
    	return super.isValidated();
    }
    
    private void refreshVisibleField() {
    	
    	mSupplierPanel.setVisibility(View.VISIBLE);
		mBillReferenceNoPanel.setVisibility(View.VISIBLE);
		mBillDatePanel.setVisibility(View.VISIBLE);
		mBillDueDatePanel.setVisibility(View.VISIBLE);
		mBillAmountPanel.setVisibility(View.VISIBLE);
		mBillStatusPanel.setVisibility(View.VISIBLE);
		mDeliveryDatePanel.setVisibility(View.VISIBLE);
		mPaymentDatePanel.setVisibility(View.VISIBLE);
		mPaymentAmountPanel.setVisibility(View.VISIBLE);
    	
    	if (Constant.BILL_TYPE_EXPENSE_WITHOUT_RECEIPT.equals(mBillType)) {
    		
    		mSupplierPanel.setVisibility(View.GONE);
    		mBillReferenceNoPanel.setVisibility(View.GONE);
    		mBillDatePanel.setVisibility(View.GONE);
    		mBillDueDatePanel.setVisibility(View.GONE);
    		mBillAmountPanel.setVisibility(View.GONE);
    		mBillStatusPanel.setVisibility(View.GONE);
    		mDeliveryDatePanel.setVisibility(View.GONE);
    	
    	} else if (Constant.BILL_TYPE_EXPENSE_WITH_RECEIPT.equals(mBillType)) {
    		
    		mSupplierPanel.setVisibility(View.GONE);
    		mDeliveryDatePanel.setVisibility(View.GONE);
    	}
    	
    	if (Constant.BILL_STATUS_PAID.equals(mBillStatus)) {
    		
    		mBillDueDatePanel.setVisibility(View.GONE);
    	
    	} else if (Constant.BILL_STATUS_UNPAID.equals(mBillStatus)) {
    		
    		mPaymentDatePanel.setVisibility(View.GONE);
    		mPaymentAmountPanel.setVisibility(View.GONE);
    	} 
    }
    
    private View.OnClickListener getSupplierOnClickListener() {
    	
    	return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mSupplierNameText.isEnabled()) {
					
					if (isEnableInputFields) {
						
						saveItem();
						
						boolean isMandatory = true;
						mBillsItemListener.onSelectSupplier(isMandatory);
					}
				}
			}
		};
    }
    
    private AdapterView.OnItemSelectedListener getTypeOnItemSelectedListener() {
    	
    	return new AdapterView.OnItemSelectedListener() {
			
        	@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
		    	mBillType = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
		    	
		    	if (Constant.BILL_TYPE_EXPENSE_WITHOUT_RECEIPT.equals(mBillType)) {
		    		
		    		mBillStatus = Constant.BILL_STATUS_PAID;
		    		
		    		int statusIndex = statusArrayAdapter.getPosition(mBillStatus);
		    		mStatusSp.setSelection(statusIndex);
		    	}
		    	
		    	refreshVisibleField();
		    }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
    }
    
    private AdapterView.OnItemSelectedListener getStatusOnItemSelectedListener() {
    	
    	return new AdapterView.OnItemSelectedListener() {
			
        	@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
        		mBillStatus = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
		    	
		    	refreshVisibleField();
        		
		    	if (Constant.BILL_STATUS_PAID.equals(mBillStatus)) {
		    		
		    		mRemarksText.requestFocus();
		    		
		    		mPaymentDate.setText(mBillDate.getText());
		    		mPaymentText.setText(mBillAmountText.getText().toString());
		    	}		    	
		    }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
    }
}