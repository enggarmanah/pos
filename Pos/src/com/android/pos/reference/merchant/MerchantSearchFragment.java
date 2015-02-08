package com.android.pos.reference.merchant;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Merchant;
import com.android.pos.service.MerchantDaoService;

import android.app.Activity;

public class MerchantSearchFragment extends BaseSearchFragment<Merchant> {

	private MerchantDaoService mMerchantDaoService = new MerchantDaoService();

	public void initAdapter() {
		
		mAdapter = new MerchantSearchArrayAdapter(getActivity(), mItems, mSelectedItem, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<Merchant>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MerchantEditFragment.MerchantActionListener");
        }
    }

	protected Long getItemId(Merchant item) {
		return item.getId();
	}
	
	public List<Merchant> getItems(String query) {

		return mMerchantDaoService.getMerchants(query);
	}

	public void onItemDeleted(Merchant item) {

		mMerchantDaoService.deleteMerchant(item);
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}