package com.android.pos.data.user;

import java.util.List;

import com.android.pos.base.fragment.BaseSearchFragment;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.dao.User;
import com.android.pos.dao.UserDaoService;

import android.app.Activity;

public class UserSearchFragment extends BaseSearchFragment<User> {

	private UserDaoService mUserDaoService = new UserDaoService();

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

		return mUserDaoService.getUsers(query, 0);
	}
	
	public List<User> getNextItems(String query, int lastIndex) {

		return mUserDaoService.getUsers(query, lastIndex);
	}

	public void onItemDeleted(User item) {

		mUserDaoService.deleteUser(item);
		
		mItems.remove(item);
		mAdapter.notifyDataSetChanged();
		
		mSelectedItem = null;
		mItemListener.onDeleteCompleted();
	}
}