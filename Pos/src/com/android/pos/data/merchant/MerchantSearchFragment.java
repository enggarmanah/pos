package com.android.pos.data.merchant;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.util.UserUtil;

import android.app.Activity;
import android.view.View;

public class MerchantSearchFragment extends BaseSearchFragment<Merchant> {

	private MerchantDaoService mMerchantDaoService = new MerchantDaoService();

	@Override
	public void onStart() {
		super.onStart();
		
		if (!UserUtil.isRoot()) {
			getAddBtn().setVisibility(View.GONE);
		}
	}
	
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

		return mMerchantDaoService.getMerchants(query, 0);
	}
	
	public List<Merchant> getNextItems(String query, int lastIndex) {

		return mMerchantDaoService.getMerchants(query, lastIndex);
	}

	public void onItemDeleted(Merchant item) {

		mMerchantDaoService.deleteMerchant(item);
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}