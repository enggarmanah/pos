package com.tokoku.pos.data.merchant;

import java.util.List;

import com.android.pos.dao.Merchant;
import com.tokoku.pos.base.adapter.BaseSearchArrayAdapter;

import android.content.Context;

public class MerchantSearchArrayAdapter extends BaseSearchArrayAdapter<Merchant> {
	
	public MerchantSearchArrayAdapter(Context context, List<Merchant> merchants, Merchant selectedMerchant, ItemActionListener<Merchant> listener) {
		super(context, merchants, selectedMerchant, listener);
	}
	
	@Override
	public Long getItemId(Merchant merchant) {
		return merchant.getId();
	}
	
	@Override
	public String getItemName(Merchant merchant) {
		return merchant.getName();
	}
}