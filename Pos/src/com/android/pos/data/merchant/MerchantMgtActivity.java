package com.android.pos.data.merchant;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.R;
import com.android.pos.async.HttpAsyncListener;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.HttpAsyncProgressDlgFragment;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.common.AlertDlgFragment;
import com.android.pos.dao.Merchant;
import com.android.pos.util.NotificationUtil;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

public class MerchantMgtActivity extends BaseItemMgtActivity<MerchantSearchFragment, MerchantEditFragment, Merchant> 
	implements HttpAsyncListener {
	
	List<Merchant> mMerchants;
	Merchant mSelectedMerchant;
	
	HttpAsyncManager mHttpAsyncManager;
	
	private HttpAsyncProgressDlgFragment mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mHttpAsyncManager = new HttpAsyncManager(this);
		
		mProgressDialog = (HttpAsyncProgressDlgFragment) getFragmentManager().findFragmentByTag("progressDialogTag");
		
		if (mProgressDialog == null) {
			mProgressDialog = new HttpAsyncProgressDlgFragment();
		}
	}
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.module_merchant));
		setSelectedMenu(getString(R.string.module_merchant));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_item_sync:
			
			mProgressDialog.show(getFragmentManager(), "progressDialogTag");
			
			mHttpAsyncManager.syncMerchants();
			
			return true;

		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected MerchantSearchFragment getSearchFragmentInstance() {
		
		return new MerchantSearchFragment();
	}

	@Override
	protected MerchantEditFragment getEditFragmentInstance() {
		
		return new MerchantEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<Merchant> merchants) {
		
		mSearchFragment.setItems(merchants);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(Merchant merchant) {
		
		mSearchFragment.setSelectedItem(merchant);
	}
	
	@Override
	protected void setEditFragmentItem(Merchant merchant) {
		
		mEditFragment.setItem(merchant);
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
	protected void enableEditFragmentInputFields(boolean isEnabled) {
		
		mEditFragment.enableInputFields(isEnabled);
	}

	@Override
	protected void doSearch(String query) {
		
		mSearchFragment.searchItem(query);
	}
	
	@Override
	protected Merchant getItemInstance() {
		
		return new Merchant();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected Merchant updateEditFragmentItem(Merchant merchant) {
		
		return mEditFragment.updateItem(merchant);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(Merchant merchant) {
	
		mEditFragment.addItem(merchant);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(Merchant merchant) {
		
		return merchant.getId();
	}
	
	@Override
	protected void refreshItem(Merchant merchant) {
		
		merchant.refresh();
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
	
	@Override
	public void deleteItem(Merchant item) {
		
		mSearchFragment.onItemDeleted(item);
	}
	
	@Override
	protected String getItemName(Merchant item) {
		
		return item.getName();
	}
	
	@Override
	protected List<Merchant> getItemsInstance() {
		
		return new ArrayList<Merchant>();
	}
	
	@Override
	public void setSyncProgress(int progress) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mProgressDialog.dismiss();
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
		
		mProgressDialog.dismiss();
		
		AlertDlgFragment alertDialogFragment = NotificationUtil.getAlertDialogInstance();
		alertDialogFragment.show(getFragmentManager(), NotificationUtil.ALERT_DIALOG_FRAGMENT_TAG);
		alertDialogFragment.setAlertMessage("Tidak dapat terhubung ke Server!");
	}
}