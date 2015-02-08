package com.android.pos.reference.product;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Product;
import com.android.pos.service.ProductDaoService;

import android.app.Activity;

public class ProductSearchFragment extends BaseSearchFragment<Product> {

	private ProductDaoService mProductDaoService = new ProductDaoService();

	public void initAdapter() {
		
		mAdapter = new ProductSearchArrayAdapter(getActivity(), mItems, mSelectedItem, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<Product>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<Product>");
        }
    }

	protected Long getItemId(Product item) {
		return item.getId();
	}
	
	public List<Product> getItems(String query) {

		return mProductDaoService.getProducts(query);
	}

	public void onItemDeleted(Product item) {
		
		mProductDaoService.deleteProduct(item);
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}