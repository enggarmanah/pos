package com.android.pos.reference.discount;

import java.util.ArrayList;

import com.android.pos.CommonUtil;
import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.Discount;
import com.android.pos.dao.DiscountDao;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DiscountEditFragment extends BaseEditFragment<Discount> {
    
	EditText mNameText;
	EditText mPercentageText;
	
    private DiscountDao discountDao = DbHelper.getSession().getDiscountDao();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.ref_discount_fragment, container, false);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
        mNameText = (EditText) getView().findViewById(R.id.nameTxt);
        mPercentageText = (EditText) getView().findViewById(R.id.percentageTxt);	
    	
        registerField(mNameText);
        registerField(mPercentageText);
        
        mandatoryFields = new ArrayList<DiscountEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    	mandatoryFields.add(new FormField(mPercentageText, R.string.field_percentage));
    }
    
    @Override
    protected void updateView(Discount discount) {
    	
    	if (discount != null) {
    		
    		mNameText.setText(discount.getName());
    		mPercentageText.setText(CommonUtil.intToStr(discount.getPercentage()));
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    }
    
    @Override
    protected void saveItem() {
    	
    	String name = mNameText.getText().toString();
    	int percentage = CommonUtil.strToInt(mPercentageText.getText().toString());
    	
    	if (mItem != null) {
    		
    		mItem.setName(name);
    		mItem.setPercentage(percentage);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    	}
    }
    
    @Override
    protected Long getItemId(Discount discount) {
    	
    	return discount.getId(); 
    } 
    
    @Override
    protected void addItem() {
    	
        discountDao.insert(mItem);
        mNameText.getText().clear();
        
        mItem = null;
    }
    
    @Override
    protected void updateItem() {
    	
    	discountDao.update(mItem);
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public Discount updateItem(Discount discount) {

    	discountDao.detach(discount);
    	discount = discountDao.load(discount.getId());
    	discountDao.detach(discount);
    	
    	this.mItem = discount;
    	
    	return discount;
    }
}