package com.android.pos.reference.productGrp;

import java.util.List;

import com.android.pos.base.adapter.BaseSearchArrayAdapter;
import com.android.pos.dao.ProductGroup;

import android.content.Context;

public class ProductGrpSearchArrayAdapter extends BaseSearchArrayAdapter<ProductGroup> {
	
	public ProductGrpSearchArrayAdapter(Context context, List<ProductGroup> productGrps, ProductGroup selectedProductGroup, ItemActionListener<ProductGroup> listener) {
		super(context, productGrps, selectedProductGroup, listener);
	}
	
	@Override
	public Long getItemId(ProductGroup productGrp) {
		return productGrp.getId();
	}
	
	@Override
	public String getItemName(ProductGroup productGrp) {
		return productGrp.getName();
	}
}