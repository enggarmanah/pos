package com.android.pos.data.employee;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Employee;
import com.android.pos.service.EmployeeDaoService;

import android.app.Activity;

public class EmployeeSearchFragment extends BaseSearchFragment<Employee> {

	private EmployeeDaoService mEmployeeDaoService = new EmployeeDaoService();

	public void initAdapter() {
		
		mAdapter = new EmployeeSearchArrayAdapter(getActivity(), mItems, mSelectedItem, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<Employee>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<Employee>");
        }
    }

	protected Long getItemId(Employee item) {
		return item.getId();
	}
	
	public List<Employee> getItems(String query) {

		return mEmployeeDaoService.getEmployees(query);
	}

	public void onItemDeleted(Employee item) {

		mEmployeeDaoService.deleteEmployee(item);
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}