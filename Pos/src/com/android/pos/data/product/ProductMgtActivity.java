package com.android.pos.data.product;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.Product;

import android.os.Bundle;
import android.view.View;

public class ProductMgtActivity extends BaseItemMgtActivity<ProductSearchFragment, ProductEditFragment, Product> {
	
	List<Product> mProducts;
	Product mSelectedProduct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.module_product));

		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
	}
	
	@Override
	protected ProductSearchFragment getSearchFragmentInstance() {
		
		return new ProductSearchFragment();
	}

	@Override
	protected ProductEditFragment getEditFragmentInstance() {
		
		return new ProductEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<Product> products) {
		
		mSearchFragment.setItems(products);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(Product product) {
		
		mSearchFragment.setSelectedItem(product);
	}
	
	@Override
	protected void setEditFragmentItem(Product product) {
		
		mEditFragment.setItem(product);
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
	protected void enableEditFragmentInputFields(boolean isEnabled) {
		
		mEditFragment.enableInputFields(isEnabled);
	}

	@Override
	protected void doSearch(String query) {
		
		mSearchFragment.searchItem(query);
	}
	
	@Override
	protected Product getItemInstance() {
		
		return new Product();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected Product updateEditFragmentItem(Product product) {
		
		return mEditFragment.updateItem(product);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(Product product) {
	
		mEditFragment.addItem(product);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(Product product) {
		
		return product.getId();
	}
	
	@Override
	protected void refreshItem(Product product) {
		
		product.refresh();
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
	
	@Override
	public void deleteItem(Product item) {
		
		mSearchFragment.onItemDeleted(item);
	}
	
	@Override
	protected String getItemName(Product item) {
		
		return item.getName();
	}
	
	@Override
	protected List<Product> getItemsInstance() {
		
		return new ArrayList<Product>();
	}
}