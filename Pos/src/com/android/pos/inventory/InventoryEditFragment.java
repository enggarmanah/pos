package com.android.pos.inventory;

import java.util.ArrayList;
import java.util.Date;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Inventory;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Product;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class InventoryEditFragment extends BaseEditFragment<Inventory> {
    
	Spinner mStatusSp;
	EditText mProductNameText;
	EditText mQuantityText;
    EditText mBillsReferenceNoText;
    EditText mSupplierNameText;
    EditText mDeliveryDate;
    EditText mRemarksText;

    CodeSpinnerArrayAdapter statusArrayAdapter;
    
    private InventoryDaoService mInventoryDaoService = new InventoryDaoService();
    
    BaseItemListener<Inventory> mInventoryItemListener;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.data_inventory_fragment, container, false);
    	
    	return view;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
        	mInventoryItemListener = (BaseItemListener<Inventory>) activity;
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
        
    	mStatusSp = (Spinner) getActivity().findViewById(R.id.statusSp);
    	mProductNameText = (EditText) getActivity().findViewById(R.id.productNameTxt);
    	mQuantityText = (EditText) getActivity().findViewById(R.id.quantityTxt);
    	mBillsReferenceNoText = (EditText) getActivity().findViewById(R.id.billsReferenceNoTxt);
    	mSupplierNameText = (EditText) getActivity().findViewById(R.id.supplierNameTxt);
    	mDeliveryDate = (EditText) getActivity().findViewById(R.id.deliveryDate);
    	mRemarksText = (EditText) getActivity().findViewById(R.id.remarksTxt);
    	
    	registerField(mStatusSp);
    	registerField(mQuantityText);
    	registerField(mBillsReferenceNoText);
    	registerField(mSupplierNameText);
    	registerField(mDeliveryDate);
    	registerField(mRemarksText);
    	
    	enableInputFields(false);
    	
    	mandatoryFields = new ArrayList<InventoryEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mProductNameText, R.string.field_product));
    	mandatoryFields.add(new FormField(mQuantityText, R.string.field_quantity));
    	mandatoryFields.add(new FormField(mBillsReferenceNoText, R.string.field_bills_reference_no));
    	mandatoryFields.add(new FormField(mDeliveryDate, R.string.field_delivery_date));
    	
    	mQuantityText.setOnFocusChangeListener(getNumberFieldOnFocusChangeListener(mQuantityText));
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getInventoryStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    	
    	mDeliveryDate.setOnClickListener(getDateFieldOnClickListener(mDeliveryDate, "deliveryDatePicker"));
    	
    	linkDatePickerWithInputField("deliveryDatePicker", mDeliveryDate);
    	
    	mProductNameText.setFocusable(false);
    	mProductNameText.setOnClickListener(getProductOnClickListener());
    	
    	mSupplierNameText.setFocusable(false);
    	mSupplierNameText.setOnClickListener(getSupplierOnClickListener());
    }
    
    @Override
    protected void updateView(Inventory inventory) {
    	
    	if (inventory != null) {
    		
    		int statusIndex = statusArrayAdapter.getPosition(inventory.getStatus());
    		mStatusSp.setSelection(statusIndex);
    		
    		mProductNameText.setText(inventory.getProductName());
    		mQuantityText.setText(CommonUtil.formatCurrencyUnsigned(inventory.getQuantityStr()));
    		mBillsReferenceNoText.setText(inventory.getBillsReferenceNo());
    		mSupplierNameText.setText(inventory.getSupplierName());
    		mDeliveryDate.setText(CommonUtil.formatDate(inventory.getDeliveryDate()));
    		mRemarksText.setText(inventory.getRemarks());
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    }
    
    @Override
    protected void saveItem() {
    	
    	String productName = mProductNameText.getText().toString();
    	String quantity = CommonUtil.parseCurrencyAsString(mQuantityText.getText().toString());
    	String billsReferenceNo = mBillsReferenceNoText.getText().toString();
    	String supplierName = mSupplierNameText.getText().toString();
    	Date deliveryDate = CommonUtil.parseDate(mDeliveryDate.getText().toString());
    	String remarks = mRemarksText.getText().toString();
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setMerchantId(MerchantUtil.getMerchantId());
    		
    		mItem.setProductName(productName);
    		mItem.setQuantityStr(quantity);
    		
    		if (!CommonUtil.isEmpty(quantity)) {
    			
    			if (Constant.INVENTORY_STATUS_PURCHASE.equals(status) ||
    				Constant.INVENTORY_STATUS_PURCHASE.equals(status)) {
    				
    				mItem.setQuantity(CommonUtil.parseInteger(quantity));
    			} else {
    				mItem.setQuantity(-CommonUtil.parseInteger(quantity));
    			}
    		}
     		
    		mItem.setBillsReferenceNo(billsReferenceNo);
    		mItem.setSupplierName(supplierName);
    		mItem.setDeliveryDate(deliveryDate);
    		mItem.setRemarks(remarks);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    		mItem.setStatus(status);
    		
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
    protected Long getItemId(Inventory inventory) {
    	
    	return inventory.getId(); 
    } 
    
    @Override
    protected boolean addItem() {
    	
        mInventoryDaoService.addInventory(mItem);
        
        mProductNameText.getText().clear();
        mQuantityText.getText().clear();
        mBillsReferenceNoText.getText().clear();
        mSupplierNameText.getText().clear();
        mDeliveryDate.getText().clear();
        mRemarksText.getText().clear();
        
        mItem = null;
        
        return true;
    }
    
    @Override
    protected boolean updateItem() {
    	
    	mInventoryDaoService.updateInventory(mItem);
    	
    	return true;
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return null;
    }
    
    @Override
    public Inventory updateItem(Inventory inventory) {

    	inventory = mInventoryDaoService.getInventory(inventory.getId());
    	
    	this.mItem = inventory;
    	
    	return inventory;
    }
    
    public void setProduct(Product product) {
		
    	if (mItem != null) {
    		
    		if (product != null) {
    			
    			mItem.setProductId(product.getId());
    			mItem.setProductName(product.getName());
    			
    		} else {
    			
    			mItem.setProduct(null);
    			mItem.setProductName(Constant.EMPTY_STRING);	
    		}
    		
    		updateView(mItem);
    	}
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
    
    public View.OnClickListener getProductOnClickListener() {
    	
    	return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mProductNameText.isEnabled()) {
					
					if (isEnableInputFields) {
						
						saveItem();
						
						boolean isMandatory = true;
						mInventoryItemListener.onSelectProduct(isMandatory);
					}
				}
			}
		};
    }
    
    public View.OnClickListener getSupplierOnClickListener() {
    	
    	return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mSupplierNameText.isEnabled()) {
					
					if (isEnableInputFields) {
						
						saveItem();
						
						boolean isMandatory = true;
						mInventoryItemListener.onSelectSupplier(isMandatory);
					}
				}
			}
		};
    }
}