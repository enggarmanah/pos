package com.android.pos.reference.productGrp;

import java.util.List;

import com.android.pos.DbHelper;
import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.ProductGroupDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;

public class ProductGrpSearchFragment extends BaseSearchFragment<ProductGroup> {

	private ProductGroupDao productGroupDao = DbHelper.getSession().getProductGroupDao();

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

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.where(ProductGroupDao.Properties.Name.like("%" + query + "%")).orderAsc(ProductGroupDao.Properties.Name);

		Query<ProductGroup> q = qb.build();
		List<ProductGroup> list = q.list();
		
		mItemListener.onLoadItems(list);

		return list;
	}

	public void onItemDeleted(ProductGroup productGroup) {

		productGroupDao.load(productGroup.getId()).delete();
	}
}