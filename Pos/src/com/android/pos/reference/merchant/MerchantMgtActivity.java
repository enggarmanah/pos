package com.android.pos.reference.merchant;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.Merchant;

import android.os.Bundle;
import android.view.View;

public class MerchantMgtActivity extends BaseItemMgtActivity<MerchantSearchFragment, MerchantEditFragment, Merchant> {
	
	List<Merchant> mMerchants;
	Merchant mSelectedMerchant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(getString(R.string.module_merchant));
		
		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
	}
	
	@Override
	protected MerchantSearchFragment getSearchFragmentInstance() {
		
		return new MerchantSearchFragment();
	}

	@Override
	protected MerchantEditFragment getEditFragmentInstance() {
		
		return new MerchantEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<Merchant> merchants) {
		
		mSearchFragment.setItems(merchants);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(Merchant merchant) {
		
		mSearchFragment.setSelectedItem(merchant);
	}
	
	@Override
	protected void setEditFragmentItem(Merchant merchant) {
		
		mEditFragment.setItem(merchant);
	}
	
	@Override
	protected View getSearchFragmentView() {
		
		return mSearchFragment.getView();
	}

	@Override
	protected View getEditFragmentView() {
		
		return mEditFragment.getView();
	}

	@Override
	protected void doSearch(String query) {
		
		mSearchFragment.searchItem(query);
	}
	
	@Override
	protected Merchant getItemInstance() {
		
		return new Merchant();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected Merchant updateEditFragmentItem(Merchant merchant) {
		
		return mEditFragment.updateItem(merchant);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(Merchant merchant) {
	
		mEditFragment.addItem(merchant);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(Merchant merchant) {
		
		return merchant.getId();
	}
	
	@Override
	protected void refreshItem(Merchant merchant) {
		
		merchant.refresh();
	}
	
	@Override
	protected void refreshSearchFragmentItems() {
		
		mSearchFragment.refreshItems();
	}
	
	@Override
	protected void saveItem() {
		
		mEditFragment.saveEditItem();
	}
	
	@Override
	protected void discardItem() {
		
		mEditFragment.discardEditItem();
	}
}