package com.android.pos.reference.employee;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.Employee;

import android.os.Bundle;
import android.view.View;

public class EmployeeMgtActivity extends BaseItemMgtActivity<EmployeeSearchFragment, EmployeeEditFragment, Employee> {
	
	List<Employee> mEmployees;
	Employee mSelectedEmployee;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(getString(R.string.module_employee));
		
		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
	}
	
	@Override
	protected EmployeeSearchFragment getSearchFragmentInstance() {
		
		return new EmployeeSearchFragment();
	}

	@Override
	protected EmployeeEditFragment getEditFragmentInstance() {
		
		return new EmployeeEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<Employee> employees) {
		
		mSearchFragment.setItems(employees);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(Employee employee) {
		
		mSearchFragment.setSelectedItem(employee);
	}
	
	@Override
	protected void setEditFragmentItem(Employee employee) {
		
		mEditFragment.setItem(employee);
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
	protected Employee getItemInstance() {
		
		return new Employee();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected Employee updateEditFragmentItem(Employee employee) {
		
		return mEditFragment.updateItem(employee);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(Employee employee) {
	
		mEditFragment.addItem(employee);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(Employee employee) {
		
		return employee.getId();
	}
	
	@Override
	protected void refreshItem(Employee employee) {
		
		employee.refresh();
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