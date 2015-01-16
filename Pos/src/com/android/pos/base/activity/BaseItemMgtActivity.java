package com.android.pos.base.activity;

import java.io.Serializable;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.base.listener.BaseItemListener;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

public abstract class BaseItemMgtActivity<S, E, T> extends BaseActivity implements BaseItemListener<T>, SearchView.OnQueryTextListener {

	protected S mSearchFragment;
	protected E mEditFragment;

	boolean mIsMultiplesPane = false;
	boolean mIsEnableSearch = true;

	SearchView searchView;
	MenuItem searchItem;

	List<T> mItems;
	T mSelectedItem;

	private static String LIST = "LIST";
	private static String SELECTED_ITEM = "SELECTED_ITEM";

	private String searchFragmentTag = "searchFragment";
	private String editFragmentTag = "editFragment";
	
	private String prevQuery = Constant.EMPTY_STRING;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ref_item_mgt_layout);

		DbHelper.initDb(this);

		initDrawerMenu();

		initFragments(savedInstanceState);

		initWaitAfterFragmentRemovedTask(searchFragmentTag, editFragmentTag);
	}

	protected abstract S getSearchFragmentInstance();

	protected abstract E getEditFragmentInstance();

	@SuppressWarnings("unchecked")
	private void initFragments(Bundle savedInstanceState) {

		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mSearchFragment = (S) getFragmentManager().findFragmentByTag(searchFragmentTag);
		mEditFragment = (E) getFragmentManager().findFragmentByTag(editFragmentTag);

		if (mSearchFragment == null) {
			mSearchFragment = getSearchFragmentInstance();

		} else {
			removeFragment(mSearchFragment);
		}

		if (mEditFragment == null) {
			mEditFragment = getEditFragmentInstance();

		} else {
			removeFragment(mEditFragment);
		}

		if (savedInstanceState != null) {

			mItems = (List<T>) savedInstanceState.getSerializable(LIST);
			mSelectedItem = (T) savedInstanceState.getSerializable(SELECTED_ITEM);
		}

		setSearchFragmentItems(mItems);
		setSearchFragmentSelectedItem(mSelectedItem);

		setEditFragmentItem(mSelectedItem);
	}

	protected abstract void setSearchFragmentItems(List<T> item);

	protected abstract void setSearchFragmentSelectedItem(T item);

	protected abstract void setEditFragmentItem(T item);

	@Override
	protected void afterFragmentRemoved() {

		loadFragments();
	}

	private void loadFragments() {

		if (!isInFront) {
			return;
		}

		if (mIsMultiplesPane) {
			addFragment(mSearchFragment, searchFragmentTag);
			addFragment(mEditFragment, editFragmentTag);

		} else {

			if (mSelectedItem == null) {
				addFragment(mSearchFragment, searchFragmentTag);
			} else {
				addFragment(mEditFragment, editFragmentTag);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		outState.putSerializable(LIST, (Serializable) mItems);
		outState.putSerializable(SELECTED_ITEM, (Serializable) mSelectedItem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.item_mgt_menu, menu);

		searchItem = menu.findItem(R.id.menu_item_search);

		searchView = (SearchView) searchItem.getActionView();
		searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.START));

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (null != searchManager) {
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		}

		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		menu.findItem(R.id.menu_item_search).setVisible(!isDrawerOpen);
		menu.findItem(R.id.menu_item_list).setVisible(!isDrawerOpen);
		menu.findItem(R.id.menu_item_new).setVisible(!isDrawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	protected abstract View getSearchFragmentView();

	protected abstract View getEditFragmentView();

	protected abstract void doSearch(String query);

	protected abstract T getItemInstance();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_item_search:

			if (getSearchFragmentView() == null) {
				replaceFragment(mSearchFragment, searchFragmentTag);
			}

			doSearch(Constant.EMPTY_STRING);

			showMessage(R.string.msg_notification_search_action);

			return true;

		case R.id.menu_item_new:

			addItem(getItemInstance());

			return true;

		case R.id.menu_item_list:

			showAllItems();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean onQueryTextChange(String query) {
		
		boolean isQuerySimilar = query.equals(prevQuery);
		
		prevQuery = query;
		
		if (mIsEnableSearch && !isQuerySimilar) {

			if (mIsMultiplesPane) {
				doSearch(query);
			} else {
				if (!isSearchVisible()) {
					replaceFragment(mSearchFragment, searchFragmentTag);
				}
				doSearch(query);
			}
		}

		return true;
	}

	public boolean onQueryTextSubmit(String query) {

		doSearch(query);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

		return true;
	}

	private boolean isSearchVisible() {

		if (getFragmentManager().findFragmentByTag(searchFragmentTag) != null) {
			return true;
		} else {
			return false;
		}
	}

	protected abstract void unSelectItem();

	@Override
	public void displaySearch() {

		if (mIsMultiplesPane) {
			unSelectItem();
		} else {
			replaceFragment(mSearchFragment, searchFragmentTag);
		}
	}

	protected abstract T updateEditFragmentItem(T item);

	protected abstract void refreshEditView();

	@Override
	public void updateItem(T item) {

		mIsEnableSearch = false;
		searchItem.collapseActionView();
		mIsEnableSearch = true;

		if (mIsMultiplesPane) {
			mSelectedItem = updateEditFragmentItem(item);
			refreshEditView();

		} else {
			replaceFragment(mEditFragment, editFragmentTag);
			mSelectedItem = updateEditFragmentItem(item);
		}
	}

	protected abstract void addEditFragmentItem(T item);

	@Override
	public void addItem(T item) {
		
		unSelectItem();
		
		searchItem.collapseActionView();

		mSelectedItem = item;

		addEditFragmentItem(item);

		if (mIsMultiplesPane) {
			refreshEditView();
		} else {
			replaceFragment(mEditFragment, editFragmentTag);
			refreshEditView();
		}
	}

	public abstract void reloadItems();

	@Override
	public void onAddCompleted() {

		if (!searchItem.collapseActionView()) {
			reloadItems();
		}

		if (mIsMultiplesPane) {
			getEditFragmentView().setVisibility(View.INVISIBLE);
		} else {
			replaceFragment(mSearchFragment, searchFragmentTag);
		}
	}

	protected abstract Long getItemId(T item);

	protected abstract void refreshItem(T item);

	protected abstract void refreshSearchFragmentItems();

	@Override
	public void onUpdateCompleted() {

		for (T item : mItems) {
			if (getItemId(item) == getItemId(mSelectedItem)) {
				refreshItem(item);
				break;
			}
		}

		if (mIsMultiplesPane) {
			refreshSearchFragmentItems();
		} else {
			replaceFragment(mSearchFragment, searchFragmentTag);
			refreshSearchFragmentItems();
		}
	}

	@Override
	public void onLoadItems(List<T> items) {

		mItems = items;
	}

	@Override
	public void onItemUnselected() {

		mSelectedItem = null;

		if (mIsMultiplesPane) {

			setEditFragmentItem(null);

			if (getEditFragmentView() != null) {
				getEditFragmentView().setVisibility(View.INVISIBLE);
			}

		} else {
			setSearchFragmentSelectedItem(mSelectedItem);
			refreshSearchFragmentItems();
		}
	}

	private void showAllItems() {

		searchItem.collapseActionView();

		reloadItems();

		if (mIsMultiplesPane) {
			refreshSearchFragmentItems();
		} else {
			if (getSearchFragmentView() == null) {
				replaceFragment(mSearchFragment, searchFragmentTag);
			}
		}
	}
}