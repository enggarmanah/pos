package com.android.pos.report.outstanding;

import java.io.Serializable;

import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.Bills;
import com.android.pos.report.cashflow.CashFlowActivity;
import com.android.pos.util.CommonUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OutstandingBillActivity extends BaseActivity 
	implements OutstandingBillActionListener {
	
	protected OutstandingBillListFragment mOutstandingListFragment;
	protected OutstandingBillDetailFragment mOutstandingDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private Bills mSelectedBill;
	
	private String mOutstandingListFragmentTag = "outstandingListFragmentTag";
	private String mOutstandingDetailFragmentTag = "outstandingDetailFragmentTag";
	
	private final String SELECTED_BILL = "SELECTED_BILL";
	
	private MenuItem mMenuList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_transaction_activity);

		initDrawerMenu();
		
		initFragments();
		
		initWaitAfterFragmentRemovedTask(mOutstandingListFragmentTag, mOutstandingDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_report_cashflow));
		setSelectedMenu(getString(R.string.menu_report_cashflow));
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedBill = (Bills) savedInstanceState.getSerializable(SELECTED_BILL);
		}
	}
	
	private void initFragments() {
		
		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mOutstandingListFragment = (OutstandingBillListFragment) getFragmentManager().findFragmentByTag(mOutstandingListFragmentTag);
		
		if (mOutstandingListFragment == null) {
			mOutstandingListFragment = new OutstandingBillListFragment();

		} else {
			removeFragment(mOutstandingListFragment);
		}
		
		mOutstandingDetailFragment = (OutstandingBillDetailFragment) getFragmentManager().findFragmentByTag(mOutstandingDetailFragmentTag);
		
		if (mOutstandingDetailFragment == null) {
			mOutstandingDetailFragment = new OutstandingBillDetailFragment();

		} else {
			removeFragment(mOutstandingDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_BILL, (Serializable) mSelectedBill);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mOutstandingListFragment.setSelectedBill(mSelectedBill);
		mOutstandingDetailFragment.setBill(mSelectedBill);
		
		if (mIsMultiplesPane) {

			addFragment(mOutstandingListFragment, mOutstandingListFragmentTag);
			addFragment(mOutstandingDetailFragment, mOutstandingDetailFragmentTag);
			
		} else {

			if (mSelectedBill != null) {
				
				addFragment(mOutstandingDetailFragment, mOutstandingDetailFragmentTag);
				
			} else {
				
				addFragment(mOutstandingListFragment, mOutstandingListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.report_pastdue_menu, menu);
		
		mMenuList = menu.findItem(R.id.menu_item_list);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		mMenuList.setVisible(!isDrawerOpen);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		synchronized (CommonUtil.LOCK) {
		
			switch (item.getItemId()) {
	
				case R.id.menu_item_list:
					
					Intent intent = new Intent(this, CashFlowActivity.class);
					startActivity(intent);
					
					return true;
					
				default:
					return super.onOptionsItemSelected(item);
			}
		}
	}
	
	@Override
	public void onBillSelected(Bills bill) {
		
		mSelectedBill = bill;
		
		if (mIsMultiplesPane) {
			
			mOutstandingListFragment.setSelectedBill(mSelectedBill);
			mOutstandingDetailFragment.setBill(mSelectedBill);
			
		} else {

			replaceFragment(mOutstandingDetailFragment, mOutstandingDetailFragmentTag);
			mOutstandingDetailFragment.setBill(mSelectedBill);
		}
	}
	
	@Override
	public void onBackPressed() {
		
		synchronized (CommonUtil.LOCK) {
			onBackToParent();
		}
	}
	
	private void onBackToParent() {
		
		mSelectedBill = null;
		
		mOutstandingListFragment.setSelectedBill(mSelectedBill);
		mOutstandingDetailFragment.setBill(mSelectedBill);
		
		if (mIsMultiplesPane) {
			
			initFragment();
			
		} else {
			
			replaceFragment(mOutstandingListFragment, mOutstandingListFragmentTag);
			initFragment();
		}
	}
	
	private void initFragment() {
		
		mOutstandingListFragment.displayOutstandingBills();
	}
	
	@Override
	protected void onAsyncTaskCompleted() {
		
		super.onAsyncTaskCompleted();
		
		mOutstandingListFragment.updateContent();
		mOutstandingDetailFragment.updateContent();
	}
}