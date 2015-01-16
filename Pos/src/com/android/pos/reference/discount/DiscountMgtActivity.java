package com.android.pos.reference.discount;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.Discount;
import com.android.pos.dao.DiscountDao;

import android.os.Bundle;
import android.view.View;

public class DiscountMgtActivity extends BaseItemMgtActivity<DiscountSearchFragment, DiscountEditFragment, Discount> {
	
	List<Discount> mDiscounts;
	Discount mSelectedDiscount;
	
	DiscountDao discountDao = DbHelper.getSession().getDiscountDao();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(getString(R.string.module_product_group));
		
		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
	}
	
	@Override
	protected DiscountSearchFragment getSearchFragmentInstance() {
		
		return new DiscountSearchFragment();
	}

	@Override
	protected DiscountEditFragment getEditFragmentInstance() {
		
		return new DiscountEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<Discount> discounts) {
		
		mSearchFragment.setItems(discounts);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(Discount discount) {
		
		mSearchFragment.setSelectedItem(discount);
	}
	
	@Override
	protected void setEditFragmentItem(Discount discount) {
		
		mEditFragment.setItem(discount);
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
	protected Discount getItemInstance() {
		
		return new Discount();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected Discount updateEditFragmentItem(Discount discount) {
		
		return mEditFragment.updateItem(discount);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(Discount discount) {
	
		mEditFragment.addItem(discount);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(Discount discount) {
		
		return discount.getId();
	}
	
	@Override
	protected void refreshItem(Discount discount) {
		
		discountDao.getSession().refresh(discount);
	}
	
	@Override
	protected void refreshSearchFragmentItems() {
		
		mSearchFragment.refreshItems();
	}
}