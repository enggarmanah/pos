package com.android.pos.data.product;

import com.android.pos.base.adapter.BaseSpinnerArrayAdapter;
import com.android.pos.dao.ProductGroup;

import android.content.Context;
import android.widget.Spinner;

public class ProductGrpSpinnerArrayAdapter extends BaseSpinnerArrayAdapter<ProductGroup>{
	
	Spinner spinner;
	
	public ProductGrpSpinnerArrayAdapter(Spinner spinner, Context context, ProductGroup[] options) {
		super(context, options);
		this.spinner = spinner;
	}
	
	@Override
	protected String getSelectedValue() {
		
		if (spinner.getSelectedItem() != null) {
			return String.valueOf(((ProductGroup) spinner.getSelectedItem()).getId());
		} else {
			return null;
		}
	}
	
	@Override
	protected String getLabel(ProductGroup productGroup) {
		
		return productGroup.getName();
	}
	
	@Override
	protected String getValue(ProductGroup productGroup) {
		
		return String.valueOf(productGroup.getId());
	}
}
