package com.android.pos.reference.discount;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Discount;
import com.android.pos.service.DiscountDaoService;

import android.app.Activity;

public class DiscountSearchFragment extends BaseSearchFragment<Discount> {

	private DiscountDaoService mDiscountDaoService = new DiscountDaoService();

	public void initAdapter() {
		
		mAdapter = new DiscountSearchArrayAdapter(getActivity(), mItems, mSelectedItem, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<Discount>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<Discount>");
        }
    }

	protected Long getItemId(Discount item) {
		return item.getId();
	}
	
	public List<Discount> getItems(String query) {

		return mDiscountDaoService.getDiscounts(query);
	}

	public void onItemDeleted(Discount item) {

		mDiscountDaoService.deleteDiscount(item);
		
		mItems.remove(mSelectedItem);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}