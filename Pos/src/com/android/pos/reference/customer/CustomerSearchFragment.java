package com.android.pos.reference.customer;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Customer;
import com.android.pos.dao.CustomerDao;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;

public class CustomerSearchFragment extends BaseSearchFragment<Customer> {

	private CustomerDao customerDao = DbUtil.getSession().getCustomerDao();

	public void initAdapter() {
		
		mAdapter = new CustomerSearchArrayAdapter(getActivity(), mItems, mSelectedItem, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<Customer>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<Customer>");
        }
    }

	protected Long getItemId(Customer item) {
		return item.getId();
	}
	
	public List<Customer> getItems(String query) {

		QueryBuilder<Customer> qb = customerDao.queryBuilder();
		qb.where(CustomerDao.Properties.Name.like("%" + query + "%")).orderAsc(CustomerDao.Properties.Name);

		Query<Customer> q = qb.build();
		List<Customer> list = q.list();

		return list;
	}

	public void onItemDeleted(Customer item) {

		customerDao.load(item.getId()).delete();
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}