package com.android.pos.user;

import java.util.List;

import com.android.pos.base.adapter.BaseSearchArrayAdapter;
import com.android.pos.dao.User;

import android.content.Context;

public class UserSearchArrayAdapter extends BaseSearchArrayAdapter<User> {
	
	public UserSearchArrayAdapter(Context context, List<User> users, User selectedUser, ItemActionListener<User> listener) {
		super(context, users, selectedUser, listener);
	}
	
	@Override
	public Long getItemId(User user) {
		return user.getId();
	}
	
	@Override
	public String getItemName(User user) {
		return user.getName();
	}
}