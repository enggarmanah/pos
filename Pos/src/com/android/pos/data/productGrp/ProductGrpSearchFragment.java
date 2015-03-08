package com.android.pos.data.productGrp;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.ProductGroup;
import com.android.pos.service.ProductGroupDaoService;

import android.app.Activity;

public class ProductGrpSearchFragment extends BaseSearchFragment<ProductGroup> {

	private ProductGroupDaoService mProductGroupDaoService = new ProductGroupDaoService();

	public void initAdapter() {
		
		mAdapter = new ProductGrpSearchArrayAdapter(getActivity(), mItems, mSelectedItem, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<ProductGroup>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<ProductGroup>");
        }
    }

	protected Long getItemId(ProductGroup item) {
		return item.getId();
	}
	
	public List<ProductGroup> getItems(String query) {

		return mProductGroupDaoService.getProductGroups(query, 0);
	}
	
	public List<ProductGroup> getNextItems(String query, int lastIndex) {

		return mProductGroupDaoService.getProductGroups(query, lastIndex);
	}

	public void onItemDeleted(ProductGroup item) {

		mProductGroupDaoService.deleteProductGroup(item);
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}