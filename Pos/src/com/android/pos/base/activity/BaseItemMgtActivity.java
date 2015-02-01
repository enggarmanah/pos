package com.android.pos.base.activity;

import java.io.Serializable;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.common.ConfirmDeleteDlgFragment;
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
	protected ConfirmDeleteDlgFragment<T> mConfirmDeleteFragment;

	boolean mIsMultiplesPane = false;
	boolean mIsEnableSearch = true;

	SearchView searchView;
	
	MenuItem mSearchItem;
	MenuItem mListItem;
	MenuItem mSaveItem;
	MenuItem mDiscardItem;
	MenuItem mEditItem;
	MenuItem mDeleteItem;

	List<T> mItems;
	T mSelectedItem;

	private static String LIST = "LIST";
	private static String SELECTED_ITEM = "SELECTED_ITEM";

	private String searchFragmentTag = "searchFragment";
	private String editFragmentTag = "editFragment";
	private String confirmDeleteFragmentTag = "confirmDeleteFragment";
	
	private String prevQuery = Constant.EMPTY_STRING;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ref_item_mgt_layout);

		DbHelper.initDb(this);

		initDrawerMenu();

		initFragments(savedInstanceState);

		initWaitAfterFragmentRemovedTask(searchFragmentTag, editFragmentTag);
		
		mConfirmDeleteFragment = new ConfirmDeleteDlgFragment<T>();
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
		
		if (mItems == null) {
			mItems = getItemsInstance();
		}
		
		setSearchFragmentItems(mItems);
		setSearchFragmentSelectedItem(mSelectedItem);

		setEditFragmentItem(mSelectedItem);
	}
	
	protected abstract List<T> getItemsInstance();
	
	protected abstract void setSearchFragmentItems(List<T> item);

	protected abstract void setSearchFragmentSelectedItem(T item);

	protected abstract void setEditFragmentItem(T item);
	
	protected abstract void enableEditFragmentInputFields(boolean isEnabled);

	@Override
	protected void afterFragmentRemoved() {

		loadFragments();
	}
	
	private boolean isFragmentHasBeenLoaded() {
		
		return (getFragmentManager().findFragmentByTag(searchFragmentTag) != null ||
				getFragmentManager().findFragmentByTag(editFragmentTag) != null);  
	}
	
	private void loadFragments() {

		/*if (!isInFront) {
			return;
		}*/
		
		if (isFragmentHasBeenLoaded()) {
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

		mSearchItem = menu.findItem(R.id.menu_item_search);
		mListItem = menu.findItem(R.id.menu_item_list);
		mSaveItem = menu.findItem(R.id.menu_item_save);
		mDiscardItem = menu.findItem(R.id.menu_item_discard);
		mEditItem = menu.findItem(R.id.menu_item_edit);
		mDeleteItem = menu.findItem(R.id.menu_item_delete);
		
		showNavigationMenu();
		
		searchView = (SearchView) mSearchItem.getActionView();
		searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.START));

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (null != searchManager) {
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		}

		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		
		if (mSelectedItem == null) {
			showNavigationMenu();
		} else {
			showNavigationAndItemMenu();
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		if (mSearchItem.isVisible()) {
			mSearchItem.setVisible(!isDrawerOpen);
		}
		
		if (mListItem.isVisible()) {
			mListItem.setVisible(!isDrawerOpen);
		}

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
			
			showNavigationMenu();

			if (getSearchFragmentView() == null) {
				replaceFragment(mSearchFragment, searchFragmentTag);
			}

			doSearch(Constant.EMPTY_STRING);

			showMessage(R.string.msg_notification_search_action);

			return true;

		case R.id.menu_item_list:

			showAllItems();
			
			showNavigationMenu();

			return true;
			
		case R.id.menu_item_edit:

			showEditMenu();

			return true;
			
		case R.id.menu_item_delete:
			
			confirmDelete(mSelectedItem);
			
			return true;
			
		case R.id.menu_item_save:
			
			saveItem();
			
			return true;
			
		case R.id.menu_item_discard:
			
			if (getItemId(mSelectedItem) == null) { 
				showNavigationMenu();
				discardItem();
				
			} else {
				showNavigationAndItemMenu();
			}
			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	protected abstract void saveItem();
	
	protected abstract void discardItem();
	
	public abstract void deleteItem(T item);
	
	protected abstract String getItemName(T item);
	
	private void confirmDelete(final T item) {
		
		mConfirmDeleteFragment.show(getFragmentManager(), confirmDeleteFragmentTag);
		mConfirmDeleteFragment.setItemToBeDeleted(item, getItemName(item));
	}
	
	public boolean onQueryTextChange(String query) {
		
		boolean isQuerySimilar = query.equals(prevQuery);
		
		prevQuery = query;
		
		if (mIsEnableSearch && !isQuerySimilar) {
			
			showNavigationMenu();

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
		
		showNavigationMenu();
		
		if (mIsMultiplesPane) {
			
			unSelectItem();
		
		} else {
			
			replaceFragment(mSearchFragment, searchFragmentTag);
		}
	}

	protected abstract T updateEditFragmentItem(T item);

	protected abstract void refreshEditView();
	
	private void showNavigationMenu() {
		
		mSearchItem.setVisible(true);
		mListItem.setVisible(false);
		
		mEditItem.setVisible(false);
		mDeleteItem.setVisible(false);
		
		mSaveItem.setVisible(false);
		mDiscardItem.setVisible(false);
	}
	
	private void showNavigationAndItemMenu() {
		
		enableEditFragmentInputFields(false);
		
		if (mIsMultiplesPane) {
			mSearchItem.setVisible(true);
			mListItem.setVisible(false);
		} else {
			mSearchItem.setVisible(false);
			mListItem.setVisible(true);
		}
		
		mEditItem.setVisible(true);
		mDeleteItem.setVisible(true);
		
		mSaveItem.setVisible(false);
		mDiscardItem.setVisible(false);
	}
	
	private void showEditMenu() {
		
		enableEditFragmentInputFields(true);
		
		mSearchItem.setVisible(false);
		mListItem.setVisible(false);
		
		mEditItem.setVisible(false);
		mDeleteItem.setVisible(false);
		
		mSaveItem.setVisible(true);
		mDiscardItem.setVisible(true);
	}

	@Override
	public void updateItem(T item) {
		
		mSelectedItem = item;
		
		showNavigationAndItemMenu();
		
		if (mIsMultiplesPane) {
			updateEditFragmentItem(item);
			refreshEditView();

		} else {
			
			mIsEnableSearch = false;
			mSearchItem.collapseActionView();
			mIsEnableSearch = true;
			
			replaceFragment(mEditFragment, editFragmentTag);
			updateEditFragmentItem(item);
		}
	}

	protected abstract void addEditFragmentItem(T item);

	@Override
	public void addItem() {
		
		showEditMenu();
		
		unSelectItem();
		
		mSearchItem.collapseActionView();

		mSelectedItem = getItemInstance();

		addEditFragmentItem(mSelectedItem);

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
		
		showNavigationMenu();
		
		if (!mSearchItem.collapseActionView()) {
			reloadItems();
		}

		if (mIsMultiplesPane) {
			getEditFragmentView().setVisibility(View.INVISIBLE);
		} else {
			replaceFragment(mSearchFragment, searchFragmentTag);
		}
	}
	
	@Override
	public void onDeleteCompleted() {
		
		mSelectedItem = null;
		
		showNavigationMenu();
		
		if (!mSearchItem.collapseActionView()) {
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
		
		showNavigationAndItemMenu();
		
		for (T item : mItems) {
			if (getItemId(item) == getItemId(mSelectedItem)) {
				refreshItem(item);
				break;
			}
		}
		
		refreshSearchFragmentItems();
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
		
		mSelectedItem = null;
		
		mSearchItem.collapseActionView();

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