package com.android.pos.reference.product;

import java.util.List;

import com.android.pos.base.adapter.BaseSearchArrayAdapter;
import com.android.pos.dao.Product;

import android.content.Context;

public class ProductSearchArrayAdapter extends BaseSearchArrayAdapter<Product> {
	
	public ProductSearchArrayAdapter(Context context, List<Product> products, Product selectedProduct, ItemActionListener<Product> listener) {
		super(context, products, selectedProduct, listener);
	}
	
	@Override
	public Long getItemId(Product product) {
		return product.getId();
	}
	
	@Override
	public String getItemName(Product product) {
		return product.getName();
	}
}