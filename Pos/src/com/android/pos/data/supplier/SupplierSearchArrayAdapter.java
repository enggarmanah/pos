package com.android.pos.data.supplier;

import java.util.List;

import com.android.pos.base.adapter.BaseSearchArrayAdapter;
import com.android.pos.dao.Supplier;

import android.content.Context;

public class SupplierSearchArrayAdapter extends BaseSearchArrayAdapter<Supplier> {
	
	public SupplierSearchArrayAdapter(Context context, List<Supplier> suppliers, Supplier selectedSupplier, ItemActionListener<Supplier> listener) {
		super(context, suppliers, selectedSupplier, listener);
	}
	
	@Override
	public Long getItemId(Supplier supplier) {
		return supplier.getId();
	}
	
	@Override
	public String getItemName(Supplier supplier) {
		return supplier.getName();
	}
}