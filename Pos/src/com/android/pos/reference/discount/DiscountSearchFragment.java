package com.android.pos.reference.discount;

import java.util.List;

import com.android.pos.DbHelper;
import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Discount;
import com.android.pos.dao.DiscountDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;

public class DiscountSearchFragment extends BaseSearchFragment<Discount> {

	private DiscountDao discountDao = DbHelper.getSession().getDiscountDao();

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

		QueryBuilder<Discount> qb = discountDao.queryBuilder();
		qb.where(DiscountDao.Properties.Name.like("%" + query + "%")).orderAsc(DiscountDao.Properties.Name);

		Query<Discount> q = qb.build();
		List<Discount> list = q.list();
		
		mItemListener.onLoadItems(list);

		return list;
	}

	public void onItemDeleted(Discount discount) {

		Discount entity = discountDao.load(discount.getId());
		discountDao.getSession().delete(entity);
	}
}