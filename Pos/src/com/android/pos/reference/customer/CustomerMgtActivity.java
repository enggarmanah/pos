package com.android.pos.reference.customer;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.Customer;

import android.os.Bundle;
import android.view.View;

public class CustomerMgtActivity extends BaseItemMgtActivity<CustomerSearchFragment, CustomerEditFragment, Customer> {
	
	List<Customer> mCustomers;
	Customer mSelectedCustomer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(getString(R.string.module_customer));
		
		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
	}
	
	@Override
	protected CustomerSearchFragment getSearchFragmentInstance() {
		
		return new CustomerSearchFragment();
	}

	@Override
	protected CustomerEditFragment getEditFragmentInstance() {
		
		return new CustomerEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<Customer> customers) {
		
		mSearchFragment.setItems(customers);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(Customer customer) {
		
		mSearchFragment.setSelectedItem(customer);
	}
	
	@Override
	protected void setEditFragmentItem(Customer customer) {
		
		mEditFragment.setItem(customer);
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
	protected Customer getItemInstance() {
		
		return new Customer();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected Customer updateEditFragmentItem(Customer customer) {
		
		return mEditFragment.updateItem(customer);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(Customer customer) {
	
		mEditFragment.addItem(customer);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(Customer customer) {
		
		return customer.getId();
	}
	
	@Override
	protected void refreshItem(Customer customer) {
		
		customer.refresh();
	}
	
	@Override
	protected void refreshSearchFragmentItems() {
		
		mSearchFragment.refreshItems();
	}
}