package com.android.pos.user;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.User;

import android.os.Bundle;
import android.view.View;

public class UserMgtActivity extends BaseItemMgtActivity<UserSearchFragment, UserEditFragment, User> {
	
	List<User> mUsers;
	User mSelectedUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(getString(R.string.menu_user_management));
		
		mDrawerList.setItemChecked(Constant.MENU_USER_MANAGEMENT_POSITION, true);
	}
	
	@Override
	protected UserSearchFragment getSearchFragmentInstance() {
		
		return new UserSearchFragment();
	}

	@Override
	protected UserEditFragment getEditFragmentInstance() {
		
		return new UserEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<User> users) {
		
		mSearchFragment.setItems(users);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(User user) {
		
		mSearchFragment.setSelectedItem(user);
	}
	
	@Override
	protected void setEditFragmentItem(User user) {
		
		mEditFragment.setItem(user);
	}
	
	@Override
	protected View getSearchFragmentView() {
		
		return mSearchFragment.getView();
	}

	@Override
	protected View getEditFragmentView() {
		
		return mEditFragment.getView();
	}

	@Override
	protected void doSearch(String query) {
		
		mSearchFragment.searchItem(query);
	}
	
	@Override
	protected User getItemInstance() {
		
		return new User();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected User updateEditFragmentItem(User user) {
		
		return mEditFragment.updateItem(user);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(User user) {
	
		mEditFragment.addItem(user);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(User user) {
		
		return user.getId();
	}
	
	@Override
	protected void refreshItem(User user) {
		
		user.refresh();
	}
	
	@Override
	protected void refreshSearchFragmentItems() {
		
		mSearchFragment.refreshItems();
	}
	
	@Override
	protected void saveItem() {
		
		mEditFragment.saveEditItem();
	}
	
	@Override
	protected void discardItem() {
		
		mEditFragment.discardEditItem();
	}
}