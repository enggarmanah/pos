package com.android.pos.base.activity;

import java.io.Serializable;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncListener;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.ProgressDlgFragment;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.base.listener.BaseItemListener;
import com.android.pos.common.ConfirmDeleteDlgFragment;
import com.android.pos.dao.Inventory;
import com.android.pos.dao.Merchant;
import com.android.pos.util.DbUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.UserUtil;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

public abstract class BaseItemMgtActivity<S, E, T> extends BaseActivity 
	implements BaseItemListener<T>, SearchView.OnQueryTextListener, HttpAsyncListener {
	
	protected S mSearchFragment;
	protected E mEditFragment;
	protected ConfirmDeleteDlgFragment<T> mConfirmDeleteFragment;

	boolean mIsMultiplesPane = false;
	boolean mIsEnableSearch = true;

	SearchView searchView;
	
	MenuItem mSearchMenu;
	MenuItem mListMenu;
	MenuItem mSaveMenu;
	MenuItem mDiscardMenu;
	MenuItem mEditMenu;
	MenuItem mDeleteMenu;
	MenuItem mSyncMenu;

	List<T> mItems;
	T mSelectedItem;

	private static String LIST = "LIST";
	private static String SELECTED_ITEM = "SELECTED_ITEM";

	private String mSearchFragmentTag = "searchFragment";
	private String mEditFragmentTag = "editFragment";
	private String mConfirmDeleteFragmentTag = "confirmDeleteFragment";
	
	private String prevQuery = Constant.EMPTY_STRING;
	
	private boolean mIsOnEdit = false;
	
	final Context context = this;
	private HttpAsyncManager mHttpAsyncManager;
	
	private static ProgressDlgFragment mProgressDialog;
	private static Integer mProgress = 0;
	
	private static final String progressDialogTag = "progressDialogTag";

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ref_item_mgt_layout);

		DbUtil.initDb(this);

		initDrawerMenu();

		initFragments(savedInstanceState);

		initWaitAfterFragmentRemovedTask(mSearchFragmentTag, mEditFragmentTag);
		
		mConfirmDeleteFragment = (ConfirmDeleteDlgFragment<T>) getFragmentManager().findFragmentByTag(mConfirmDeleteFragmentTag);
		
		if (mConfirmDeleteFragment == null) {
			mConfirmDeleteFragment = new ConfirmDeleteDlgFragment<T>();
		}
		
		mHttpAsyncManager = new HttpAsyncManager(this);
		
		mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag(progressDialogTag);
		
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDlgFragment();
		}
	}
	
	@Override
	public void onStart() {
		
		super.onStart();

		if (getFragmentManager().findFragmentByTag(progressDialogTag) != null) {
			mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag(progressDialogTag);
		}
		
		if (mProgress == 100 && mProgressDialog.isVisible()) {
			mProgressDialog.dismiss();
		}
	}

	protected abstract S getSearchFragmentInstance();

	protected abstract E getEditFragmentInstance();

	@SuppressWarnings("unchecked")
	private void initFragments(Bundle savedInstanceState) {

		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mSearchFragment = (S) getFragmentManager().findFragmentByTag(mSearchFragmentTag);
		mEditFragment = (E) getFragmentManager().findFragmentByTag(mEditFragmentTag);

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
		
		return (getFragmentManager().findFragmentByTag(mSearchFragmentTag) != null ||
				getFragmentManager().findFragmentByTag(mEditFragmentTag) != null);  
	}
	
	private void loadFragments() {
		
		if (isFragmentHasBeenLoaded()) {
			return;
		}

		if (mIsMultiplesPane) {
			addFragment(mSearchFragment, mSearchFragmentTag);
			addFragment(mEditFragment, mEditFragmentTag);

		} else {

			if (mSelectedItem == null) {
				addFragment(mSearchFragment, mSearchFragmentTag);
				
			} else {
				addFragment(mEditFragment, mEditFragmentTag);
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

		mSearchMenu = menu.findItem(R.id.menu_item_search);
		mListMenu = menu.findItem(R.id.menu_item_list);
		mSaveMenu = menu.findItem(R.id.menu_item_save);
		mDiscardMenu = menu.findItem(R.id.menu_item_discard);
		mEditMenu = menu.findItem(R.id.menu_item_edit);
		mDeleteMenu = menu.findItem(R.id.menu_item_delete);
		mSyncMenu = menu.findItem(R.id.menu_item_sync);
		
		showNavigationMenu();
		
		searchView = (SearchView) mSearchMenu.getActionView();
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
		
		if (UserUtil.isRoot() || UserUtil.isMerchant()) {
			mSyncMenu.setVisible(!isDrawerOpen);
		} else {
			mSyncMenu.setVisible(false);
		}
		
		if (mSelectedItem instanceof Merchant && UserUtil.isRoot()) {
			mDeleteMenu.setVisible(!isDrawerOpen);
		} else {
			mDeleteMenu.setVisible(false);
		}
		
		if (mSearchMenu.isVisible()) {
			mSearchMenu.setVisible(!isDrawerOpen);
		}
		
		if (mListMenu.isVisible()) {
			mListMenu.setVisible(!isDrawerOpen);
		}
		
		if (mSelectedItem != null) {
			mEditMenu.setVisible(!isDrawerOpen);
		}
		
		if (mIsOnEdit) {
			mSaveMenu.setVisible(!isDrawerOpen);
			mDiscardMenu.setVisible(!isDrawerOpen);
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
				replaceFragment(mSearchFragment, mSearchFragmentTag);
			}

			doSearch(Constant.EMPTY_STRING);

			showMessage(R.string.msg_notification_search_action);

			return true;

		case R.id.menu_item_list:

			showAllItems();
			
			showNavigationMenu();

			return true;
			
		case R.id.menu_item_edit:
			
			if (mSelectedItem instanceof Inventory) {
				
				Inventory inventory = (Inventory) mSelectedItem;
				
				if (Constant.INVENTORY_STATUS_SALE.equals(inventory.getStatus())) {
					
					NotificationUtil.setAlertMessage(getFragmentManager(), "Data penjualan tidak dapat dirubah dari modul ini!");
					return true;
				}
			}

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
				refreshEditView();
			}
			
			return true;
			
		case R.id.menu_item_sync:
			
			mProgressDialog.show(getFragmentManager(), progressDialogTag);
			
			mHttpAsyncManager.sync(); 
			
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
		
		mConfirmDeleteFragment.show(getFragmentManager(), mConfirmDeleteFragmentTag);
		mConfirmDeleteFragment.setItemToBeDeleted(item, getItemName(item));
	}
	
	@Override
	public boolean onQueryTextChange(String query) {
		
		boolean isQuerySimilar = query.equals(prevQuery);
		
		prevQuery = query;
		
		if (mIsEnableSearch && !isQuerySimilar) {
			
			showNavigationMenu();

			if (mIsMultiplesPane) {
				doSearch(query);
			} else {
				if (!isSearchVisible()) {
					replaceFragment(mSearchFragment, mSearchFragmentTag);
				}
				doSearch(query);
			}
		}

		return true;
	}
	
	@Override
	public boolean onQueryTextSubmit(String query) {

		doSearch(query);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

		return true;
	}

	private boolean isSearchVisible() {

		if (getFragmentManager().findFragmentByTag(mSearchFragmentTag) != null) {
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
			
			replaceFragment(mSearchFragment, mSearchFragmentTag);
		}
	}

	protected abstract T updateEditFragmentItem(T item);

	protected abstract void refreshEditView();
	
	private void showNavigationMenu() {
		
		if (mSyncMenu == null) {
			return;
		}
		
		if (UserUtil.isRoot() || UserUtil.isMerchant()) {
			mSyncMenu.setVisible(true);
		} else {
			mSyncMenu.setVisible(false);
		}
		
		mSearchMenu.setVisible(true);
		mListMenu.setVisible(false);
		
		mEditMenu.setVisible(false);
		mDeleteMenu.setVisible(false);
		
		mSaveMenu.setVisible(false);
		mDiscardMenu.setVisible(false);
		
		mIsOnEdit = false;
	}
	
	private void showNavigationAndItemMenu() {
		
		enableEditFragmentInputFields(false);
		
		if (UserUtil.isRoot() || UserUtil.isMerchant()) {
			mSyncMenu.setVisible(true);
		} else {
			mSyncMenu.setVisible(false);
		}
		
		if (mIsMultiplesPane) {
			mSearchMenu.setVisible(true);
			mListMenu.setVisible(false);
		} else {
			mSearchMenu.setVisible(false);
			mListMenu.setVisible(true);
		}
		
		mEditMenu.setVisible(true);
		
		if (mSelectedItem instanceof Merchant && UserUtil.isRoot()) {
			mDeleteMenu.setVisible(true);
		} else {
			mDeleteMenu.setVisible(false);
		}
		
		mSaveMenu.setVisible(false);
		mDiscardMenu.setVisible(false);
		
		mIsOnEdit = false;
	}
	
	private void showEditMenu() {
		
		enableEditFragmentInputFields(true);
		
		mSyncMenu.setVisible(false);
		
		mSearchMenu.setVisible(false);
		mListMenu.setVisible(false);
		
		mEditMenu.setVisible(false);
		mDeleteMenu.setVisible(false);
		
		mSaveMenu.setVisible(true);
		mDiscardMenu.setVisible(true);
		
		mIsOnEdit = true;
	}

	@Override
	public void updateItem(T item) {
		
		mSelectedItem = item;
		
		showNavigationAndItemMenu();
		
		if (item instanceof Merchant && !UserUtil.isRoot()) {
			mDeleteMenu.setVisible(false);
		}
		
		if (mIsMultiplesPane) {
			updateEditFragmentItem(item);
			refreshEditView();

		} else {
			
			mIsEnableSearch = false;
			mSearchMenu.collapseActionView();
			mIsEnableSearch = true;
			
			replaceFragment(mEditFragment, mEditFragmentTag);
			updateEditFragmentItem(item);
		}
	}

	protected abstract void addEditFragmentItem(T item);

	@Override
	public void addItem() {
		
		unSelectItem();
		
		showEditMenu();
		
		mSearchMenu.collapseActionView();

		mSelectedItem = getItemInstance();

		addEditFragmentItem(mSelectedItem);

		if (mIsMultiplesPane) {
			refreshEditView();
		} else {
			replaceFragment(mEditFragment, mEditFragmentTag);
			refreshEditView();
		}
	}

	public abstract void reloadItems();

	@Override
	public void onAddCompleted() {
		
		showNavigationMenu();
		
		if (!mSearchMenu.collapseActionView()) {
			reloadItems();
		}

		if (mIsMultiplesPane) {
			getEditFragmentView().setVisibility(View.INVISIBLE);
		} else {
			replaceFragment(mSearchFragment, mSearchFragmentTag);
		}
	}
	
	@Override
	public void onDeleteCompleted() {
		
		mSelectedItem = null;
		
		showNavigationMenu();
		
		if (!mSearchMenu.collapseActionView()) {
			reloadItems();
		}

		if (mIsMultiplesPane) {
			getEditFragmentView().setVisibility(View.INVISIBLE);
		} else {
			replaceFragment(mSearchFragment, mSearchFragmentTag);
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
		
		showNavigationMenu();
		
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
		
		mSearchMenu.collapseActionView();

		reloadItems();

		if (mIsMultiplesPane) {
			refreshSearchFragmentItems();
		} else {
			if (getSearchFragmentView() == null) {
				replaceFragment(mSearchFragment, mSearchFragmentTag);
			}
		}
	}
	
	public void onSelectProduct(boolean isMandatory) {}
	
	public void onSelectSupplier(boolean isMandatory) {}
	
	public void onSelectBill(boolean isMandatory) {}
	
	@Override
	public void setSyncProgress(int progress) {
		
		mProgress = progress;
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						if (isActivityVisible()) {
							mProgressDialog.dismiss();
						}
					}
				}, 500);
			}
		}
	}
	
	@Override
	public void setSyncMessage(String message) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setMessage(message);
		}
	}
	
	@Override
	public void onTimeOut() {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "Tidak dapat terhubung ke Server!");
	}
	
	@Override
	public void onSyncError() {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "Error dalam sync data ke Server!");
	}
}