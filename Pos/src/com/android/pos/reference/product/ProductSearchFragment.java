package com.android.pos.reference.product;

import java.util.List;

import com.android.pos.DbHelper;
import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;

public class ProductSearchFragment extends BaseSearchFragment<Product> {

	private ProductDao productDao = DbHelper.getSession().getProductDao();

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

		QueryBuilder<Product> qb = productDao.queryBuilder();
		qb.where(ProductDao.Properties.Name.like("%" + query + "%")).orderAsc(ProductDao.Properties.Name);

		Query<Product> q = qb.build();
		List<Product> list = q.list();
		
		mItemListener.onLoadItems(list);

		return list;
	}

	public void onItemDeleted(Product product) {

		productDao.load(product.getId()).delete();
	}
}