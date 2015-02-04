package com.android.pos.user;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.User;
import com.android.pos.dao.UserDao;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;

public class UserSearchFragment extends BaseSearchFragment<User> {

	private UserDao userDao = DbUtil.getSession().getUserDao();

	public void initAdapter() {
		
		mAdapter = new UserSearchArrayAdapter(getActivity(), mItems, mSelectedItem, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mItemListener = (BaseItemListener<User>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseItemListener<User>");
        }
    }

	protected Long getItemId(User item) {
		return item.getId();
	}
	
	public List<User> getItems(String query) {

		QueryBuilder<User> qb = userDao.queryBuilder();
		qb.where(UserDao.Properties.Name.like("%" + query + "%")).orderAsc(UserDao.Properties.Name);

		Query<User> q = qb.build();
		List<User> list = q.list();

		return list;
	}

	public void onItemDeleted(User item) {

		userDao.load(item.getId()).delete();
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}