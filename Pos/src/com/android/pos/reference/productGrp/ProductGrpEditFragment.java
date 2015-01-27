package com.android.pos.reference.productGrp;

import java.util.ArrayList;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseEditFragment;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.ProductGroupDao;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ProductGrpEditFragment extends BaseEditFragment<ProductGroup> {
    
	EditText mNameText;
    
    private ProductGroupDao productGroupDao = DbHelper.getSession().getProductGroupDao();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.ref_product_grp_fragment, container, false);
    	
    	return view;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void initViewReference() {
        
    	mNameText = (EditText) getActivity().findViewById(R.id.nameTxt);
    	
    	registerField(mNameText);
    	
    	mandatoryFields = new ArrayList<ProductGrpEditFragment.FormField>();
    	mandatoryFields.add(new FormField(mNameText, R.string.field_name));
    }
    
    @Override
    protected void updateView(ProductGroup productGroup) {
    	
    	if (productGroup != null) {
    		
    		mNameText.setText(productGroup.getName());
    		
    		showView();
    		
    	} else {
    		
    		hideView();
    	}
    }
    
    @Override
    protected void saveItem() {
    	
    	String name = mNameText.getText().toString();
    	
    	if (mItem != null) {
    		
    		mItem.setName(name);
    		
    		mItem.setUploadStatus(Constant.STATUS_YES);
    	}
    }
    
    @Override
    protected Long getItemId(ProductGroup productGroup) {
    	
    	return productGroup.getId(); 
    } 
    
    @Override
    protected void addItem() {
    	
        productGroupDao.insert(mItem);
        mNameText.getText().clear();
        
        mItem = null;
    }
    
    @Override
    protected void updateItem() {
    	
    	productGroupDao.update(mItem);
    }
    
    @Override
    protected TextView getFirstField() {
    	
    	return mNameText;
    }
    
    @Override
    public ProductGroup updateItem(ProductGroup productGroup) {

    	productGroupDao.detach(productGroup);
    	productGroup = productGroupDao.load(productGroup.getId());
    	productGroupDao.detach(productGroup);
    	
    	this.mItem = productGroup;
    	
    	return productGroup;
    }
}