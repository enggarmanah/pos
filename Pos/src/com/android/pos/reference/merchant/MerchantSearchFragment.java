package com.android.pos.reference.merchant;

import java.util.List;

import com.android.pos.DbHelper;
import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;

public class MerchantSearchFragment extends BaseSearchFragment<Merchant> {

	private MerchantDao merchantDao = DbHelper.getSession().getMerchantDao();

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

		QueryBuilder<Merchant> qb = merchantDao.queryBuilder();
		qb.where(MerchantDao.Properties.Name.like("%" + query + "%")).orderAsc(MerchantDao.Properties.Name);

		Query<Merchant> q = qb.build();
		List<Merchant> list = q.list();
		
		mItemListener.onLoadItems(list);

		return list;
	}

	public void onItemDeleted(Merchant merchant) {

		merchantDao.load(merchant.getId()).delete();
	}
}