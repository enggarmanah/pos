package com.android.pos.data.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductGroup;
import com.android.pos.service.ProductDaoService;
import com.android.pos.service.ProductGroupDaoService;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.UserUtil;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProductEditFragment extends BaseEditFragment<Product> {
    
	EditText mNameText;
	Spinner mTypeSp;
	Spinner mProductGrpSp;
    EditText mPriceText;
    EditText mCostPriceText;
    Spinner mPicRequiredSp;
    EditText mCommisionText;
    EditText mPromoPriceText;
    EditText mPromoStartDate;
    EditText mPromoEndDate;
    Spinner mStatusSp;
    
    ProductGrpSpinnerArrayAdapter productGrpArrayAdapter;
    CodeSpinnerArrayAdapter statusArrayAdapter;
    CodeSpinnerArrayAdapter typeArrayAdapter;
    CodeSpinnerArrayAdapter picRequiredArrayAdapter;
    
    private ProductDaoService mProductDaoService = new ProductDaoService();
    private ProductGroupDaoService mProductGroupDaoService = new ProductGroupDaoService();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.data_product_fragment, container, false);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
        mNameText = (EditText) getView().findViewById(R.id.nameTxt);
    	mTypeSp = (Spinner) getView().findViewById(R.id.typeSp);
    	mProductGrpSp = (Spinner) getView().findViewById(R.id.productGrpSp);
    	mPriceText = (EditText) getView().findViewById(R.id.priceTxt);
    	mCostPriceText = (EditText) getView().findViewById(R.id.costPriceTxt);
    	mPicRequiredSp = (Spinner) getView().findViewById(R.id.picRequiredSp);
    	mCommisionText = (EditText) getView().findViewById(R.id.commisionTxt);
    	mPromoPriceText = (EditText) getView().findViewById(R.id.promoPriceTxt);
    	mPromoStartDate = (EditText) getView().findViewById(R.id.promoStartDate);
    	mPromoEndDate = (EditText) getView().findViewById(R.id.promoEndDate);
    	mStatusSp = (Spinner) getView().findViewById(R.id.statusSp);
    	
    	registerField(mNameText);
    	registerField(mTypeSp);
    	registerField(mProductGrpSp);
    	registerField(mPriceText);
    	registerField(mCostPriceText);
    	registerField(mPicRequiredSp);
    	registerField(mCommisionText);
    	registerField(mPromoPriceText);
    	registerField(mPromoStartDate);
    	registerField(mPromoEndDate);
    	registerField(mStatusSp);
    	
    	enableInputFields(false);
    	
    	mandatoryFields = new ArrayList<ProductEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	mandatoryFields.add(new FormField(mPriceText, R.string.field_price));
    	
    	mPriceText.setOnFocusChangeListener(getNumberFieldOnFocusChangeListener(mPriceText));
    	mCostPriceText.setOnFocusChangeListener(getNumberFieldOnFocusChangeListener(mCostPriceText));
    	mCommisionText.setOnFocusChangeListener(getNumberFieldOnFocusChangeListener(mCommisionText));
    	mPromoPriceText.setOnFocusChangeListener(getNumberFieldOnFocusChangeListener(mPromoPriceText));
    	
    	mPromoStartDate.setOnClickListener(getDateFieldOnClickListener(mPromoStartDate, "startDatePicker"));
    	mPromoEndDate.setOnClickListener(getDateFieldOnClickListener(mPromoEndDate, "endDatePicker"));
    	
    	linkDatePickerWithInputField("startDatePicker", mPromoStartDate);
    	linkDatePickerWithInputField("endDatePicker", mPromoEndDate);
    	
    	typeArrayAdapter = new CodeSpinnerArrayAdapter(mTypeSp, getActivity(), CodeUtil.getProductTypes());
    	mTypeSp.setAdapter(typeArrayAdapter);
    	
    	productGrpArrayAdapter = new ProductGrpSpinnerArrayAdapter(mProductGrpSp, getActivity(), getProductGroups());
    	mProductGrpSp.setAdapter(productGrpArrayAdapter);
    	
    	picRequiredArrayAdapter = new CodeSpinnerArrayAdapter(mPicRequiredSp, getActivity(), CodeUtil.getBooleans());
    	mPicRequiredSp.setAdapter(picRequiredArrayAdapter);
    	
    	statusArrayAdapter = new CodeSpinnerArrayAdapter(mStatusSp, getActivity(), CodeUtil.getProductStatus());
    	mStatusSp.setAdapter(statusArrayAdapter);
    }
    
    @Override
    protected void updateView(Product product) {
    	
    	if (product != null) {
    		
    		int typeIndex = typeArrayAdapter.getPosition(product.getType());
    		int productGrpIndex = productGrpArrayAdapter.getPosition(String.valueOf(product.getProductGroupId()));
    		int statusIndex = statusArrayAdapter.getPosition(product.getStatus());
    		int picRequiredIndex = picRequiredArrayAdapter.getPosition(product.getPicRequired());
    		
    		mNameText.setText(product.getName());
    		mPriceText.setText(CommonUtil.formatCurrency(product.getPrice()));
    		mCostPriceText.setText(CommonUtil.formatCurrency(product.getCostPrice()));
    		mCommisionText.setText(CommonUtil.formatCurrency(product.getCommision()));
    		mPromoPriceText.setText(CommonUtil.formatCurrency(product.getPromoPrice()));
    		mPromoStartDate.setText(CommonUtil.formatDate(product.getPromoStart()));
    		mPromoEndDate.setText(CommonUtil.formatDate(product.getPromoEnd()));
    		
    		mTypeSp.setSelection(typeIndex);
    		mProductGrpSp.setSelection(productGrpIndex);
    		mStatusSp.setSelection(statusIndex);
    		mPicRequiredSp.setSelection(picRequiredIndex);
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    }
    
    @Override
    protected void saveItem() {
    	
    	String name = mNameText.getText().toString();
    	String type = CodeBean.getNvlCode((CodeBean) mTypeSp.getSelectedItem());
    	Long prodGrpId = CommonUtil.getNvlId((ProductGroup) mProductGrpSp.getSelectedItem());
    	Integer price = CommonUtil.parseCurrency(mPriceText.getText().toString());
    	Integer costPrice = CommonUtil.parseCurrency(mCostPriceText.getText().toString());
    	String picRequired = CodeBean.getNvlCode((CodeBean) mPicRequiredSp.getSelectedItem());
    	Integer commision = CommonUtil.parseCurrency(mCommisionText.getText().toString());
    	Integer promoPrice = CommonUtil.parseCurrency(mPromoPriceText.getText().toString());
    	Date startDate = CommonUtil.parseDate(mPromoStartDate.getText().toString());
    	Date endDate = CommonUtil.parseDate(mPromoEndDate.getText().toString());
    	String status = CodeBean.getNvlCode((CodeBean) mStatusSp.getSelectedItem());
    	
    	if (mItem != null) {
    		
    		mItem.setMerchantId(CommonUtil.getMerchantId());
    		
    		mItem.setName(name);
    		mItem.setType(type);
    		mItem.setProductGroupId(prodGrpId);
    		mItem.setPrice(price);
    		mItem.setCostPrice(costPrice);
    		mItem.setPicRequired(picRequired);
    		mItem.setCommision(commision);
    		mItem.setPromoPrice(promoPrice);
    		mItem.setPromoStart(startDate);
    		mItem.setPromoEnd(endDate);
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
    protected Long getItemId(Product product) {
    	
    	return product.getId(); 
    } 
    
    @Override
    protected void addItem() {
    	
        mProductDaoService.addProduct(mItem);
        
        mNameText.getText().clear();
        mPriceText.getText().clear();
        mCostPriceText.getText().clear();
        mCommisionText.getText().clear();
        mPromoPriceText.getText().clear();
        mPromoStartDate.getText().clear();
        mPromoEndDate.getText().clear();
        
        mItem = null;
    }
    
    @Override
    protected void updateItem() {
    	
    	mProductDaoService.updateProduct(mItem);
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public Product updateItem(Product product) {

    	product = mProductDaoService.getProduct(product.getId());
    	
    	this.mItem = product;
    	
    	return product;
    }
    
    private ProductGroup[] getProductGroups() {

		List<ProductGroup> list = mProductGroupDaoService.getProductGroups();

		return list.toArray(new ProductGroup[list.size()]);
	}
}