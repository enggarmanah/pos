package com.android.pos.reference.customer;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Customer;
import com.android.pos.service.CustomerDaoService;

import android.app.Activity;

public class CustomerSearchFragment extends BaseSearchFragment<Customer> {

	private CustomerDaoService mCustomerDaoService = new CustomerDaoService();

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

		return mCustomerDaoService.getCustomers(query);
	}

	public void onItemDeleted(Customer item) {

		mCustomerDaoService.deleteCustomer(item);
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}