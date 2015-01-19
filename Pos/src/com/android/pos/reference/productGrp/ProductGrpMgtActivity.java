package com.android.pos.reference.productGrp;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.ProductGroup;

import android.os.Bundle;
import android.view.View;

public class ProductGrpMgtActivity extends BaseItemMgtActivity<ProductGrpSearchFragment, ProductGrpEditFragment, ProductGroup> {
	
	List<ProductGroup> mProductGroups;
	ProductGroup mSelectedProductGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(getString(R.string.module_product_group));
		
		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
	}
	
	@Override
	protected ProductGrpSearchFragment getSearchFragmentInstance() {
		
		return new ProductGrpSearchFragment();
	}

	@Override
	protected ProductGrpEditFragment getEditFragmentInstance() {
		
		return new ProductGrpEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<ProductGroup> productGroups) {
		
		mSearchFragment.setItems(productGroups);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(ProductGroup productGroup) {
		
		mSearchFragment.setSelectedItem(productGroup);
	}
	
	@Override
	protected void setEditFragmentItem(ProductGroup productGroup) {
		
		mEditFragment.setItem(productGroup);
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
	protected ProductGroup getItemInstance() {
		
		return new ProductGroup();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected ProductGroup updateEditFragmentItem(ProductGroup productGroup) {
		
		return mEditFragment.updateItem(productGroup);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(ProductGroup productGroup) {
	
		mEditFragment.addItem(productGroup);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(ProductGroup productGroup) {
		
		return productGroup.getId();
	}
	
	@Override
	protected void refreshItem(ProductGroup productGroup) {
		
		productGroup.refresh();
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