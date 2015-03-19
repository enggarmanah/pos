package com.android.pos.inventory;

import java.util.ArrayList;
import java.util.Date;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Bills;
import com.android.pos.dao.Inventory;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDaoService;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class InventoryEditFragment extends BaseEditFragment<Inventory> {
    
	Spinner mStatusSp;
	EditText mProductNameText;
	EditText mProductCostPriceText;
	EditText mQuantityText;
    EditText mBillsReferenceNoText;
    EditText mSupplierNameText;
    EditText mDeliveryDate;
    EditText mRemarksText;
    
    LinearLayout mSupplierPanel;
    LinearLayout mRemarksPanel;
    	
    CodeSpinnerArrayAdapter statusArrayAdapter;
    
    private InventoryDaoService mInventoryDaoService = new InventoryDaoService();
    private ProductDaoService mProductDaoService = new ProductDaoService();
    
    BaseItemListener<Inventory> mInventoryItemListener;
    
    String mStatus;
    
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
        
    	mStatusSp = (Spinner) getView().findViewById(R.id.statusSp);
    	mProductNameText = (EditText) getView().findViewById(R.id.productNameText);
    	mProductCostPriceText = (EditText) getView().findViewById(R.id.productCostPriceText);
    	mQuantityText = (EditText) getView().findViewById(R.id.quantityText);
    	mBillsReferenceNoText = (EditText) getView().findViewById(R.id.billsReferenceNoText);
    	mSupplierNameText = (EditText) getView().findViewById(R.id.supplierNameText);
    	mDeliveryDate = (EditText) getView().findViewById(R.id.deliveryDate);
    	mRemarksText = (EditText) getView().findViewById(R.id.remarksText);
    	
    	mSupplierPanel = (LinearLayout) getView().findViewById(R.id.supplierPanel);
        mRemarksPanel = (LinearLayout) getView().findViewById(R.id.remarksPanel);
    	
    	registerField(mStatusSp);
    	registerField(mProductNameText);
    	registerField(mProductCostPriceText);
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
    	
    	mBillsReferenceNoText.setFocusable(false);
    	mBillsReferenceNoText.setOnClickListener(getBillOnClickListener());
    	
    	mSupplierNameText.setFocusable(false);
    	mSupplierNameText.setOnClickListener(getSupplierOnClickListener());
    }
    
    @Override
    protected void updateView(Inventory inventory) {
    	
    	if (inventory != null) {
    		
    		int statusIndex = statusArrayAdapter.getPosition(inventory.getStatus());
    		mStatusSp.setSelection(statusIndex);
    		
    		mProductNameText.setText(inventory.getProductName());
    		mProductCostPriceText.setText(CommonUtil.formatNumber(inventory.getProductCostPrice()));
    		mQuantityText.setText(CommonUtil.formatNumber(inventory.getQuantityStr()));
    		mBillsReferenceNoText.setText(inventory.getBillReferenceNo());
    		mSupplierNameText.setText(inventory.getSupplierName());
    		mDeliveryDate.setText(CommonUtil.formatDate(inventory.getDeliveryDate()));
    		mRemarksText.setText(inventory.getRemarks());
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    	
        mStatus = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
        refreshVisibleField();
    }
    
    @Override
    protected void saveItem() {
    	
    	String productName = mProductNameText.getText().toString();
    	Integer productCostPrice = CommonUtil.parseNumber(mProductCostPriceText.getText().toString());
    	Integer quantity = CommonUtil.parseNumber(mQuantityText.getText().toString());
    	String billReferenceNo = mBillsReferenceNoText.getText().toString();
    	String supplierName = mSupplierNameText.getText().toString();
    	Date deliveryDate = CommonUtil.parseDate(mDeliveryDate.getText().toString());
    	String remarks = mRemarksText.getText().toString();
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setMerchantId(MerchantUtil.getMerchantId());
    		
    		mItem.setProductName(productName);
    		mItem.setProductCostPrice(productCostPrice);
    		mItem.setQuantityStr(CommonUtil.formatString(quantity));
    		
    		if (quantity != null) {
    			
    			if (Constant.INVENTORY_STATUS_PURCHASE.equals(status) ||
    				Constant.INVENTORY_STATUS_REPLACEMENT.equals(status) ||
    				Constant.INVENTORY_STATUS_REFUND.equals(status) ||
    				Constant.INVENTORY_STATUS_INITIAL_STOCK.equals(status)) {
    				
    				mItem.setQuantity(quantity);
    			} else {
    				mItem.setQuantity(-quantity);
    			}
    			
    		} else {
    			
    			mItem.setQuantity(0);
    		}
     		
    		mItem.setBillReferenceNo(billReferenceNo);
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
    	
    	// update product cost price
    	
    	if (mItem.getProductCostPrice() != null || mItem.getProductCostPrice() == 0) {
    	
	    	Product product = mProductDaoService.getProduct(mItem.getProductId());
	    	product.setCostPrice(mItem.getProductCostPrice());
	    	mProductDaoService.updateProduct(product);
    	}
    	
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
    			mItem.setProductCostPrice(product.getCostPrice());
    			
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
    
    public void setBill(Bills bill) {
		
    	if (mItem != null) {
    		
    		if (bill != null) {
    			
    			mItem.setBillId(bill.getId());
    			mItem.setBillReferenceNo(bill.getBillReferenceNo());
    			mItem.setSupplierId(bill.getSupplierId());
    			mItem.setSupplierName(bill.getSupplierName());
    			mItem.setDeliveryDate(bill.getDeliveryDate());
    			
    		} else {
    			
    			mItem.setBills(null);
    			mItem.setBillReferenceNo(Constant.EMPTY_STRING);
    			mItem.setSupplier(null);
    			mItem.setSupplierName(Constant.EMPTY_STRING);
    			mItem.setDeliveryDate(null);
    		}
    		
    		updateView(mItem);
    	}
	}
    
    private void refreshVisibleField() {
    	
    	mSupplierPanel.setVisibility(View.VISIBLE);
		mRemarksPanel.setVisibility(View.VISIBLE);
		
		if (Constant.INVENTORY_STATUS_SALE.equals(mStatus)) {
			
			mSupplierPanel.setVisibility(View.GONE);
			mRemarksPanel.setVisibility(View.GONE);
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
    
    public View.OnClickListener getBillOnClickListener() {
    	
    	return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mBillsReferenceNoText.isEnabled()) {
					
					if (isEnableInputFields) {
						
						saveItem();
						
						boolean isMandatory = true;
						mInventoryItemListener.onSelectBill(isMandatory);
					}
				}
			}
		};
    }
}