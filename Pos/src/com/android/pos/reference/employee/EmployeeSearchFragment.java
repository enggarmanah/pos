package com.android.pos.reference.employee;

import java.util.List;

import com.android.pos.DbHelper;
import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Employee;
import com.android.pos.dao.EmployeeDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;

public class EmployeeSearchFragment extends BaseSearchFragment<Employee> {

	private EmployeeDao employeeDao = DbHelper.getSession().getEmployeeDao();

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

		QueryBuilder<Employee> qb = employeeDao.queryBuilder();
		qb.where(EmployeeDao.Properties.Name.like("%" + query + "%")).orderAsc(EmployeeDao.Properties.Name);

		Query<Employee> q = qb.build();
		List<Employee> list = q.list();
		
		mItemListener.onLoadItems(list);

		return list;
	}

	public void onItemDeleted(Employee employee) {

		employeeDao.load(employee.getId()).delete();
	}
}