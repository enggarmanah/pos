package com.android.pos.report.pastdue;

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

public class PastDueActivity extends BaseActivity 
	implements PastDueActionListener {
	
	protected PastDueListFragment mPastDueListFragment;
	protected PastDueDetailFragment mPastDueDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private Bills mSelectedBill;
	
	private String mPastDueListFragmentTag = "pastDueListFragmentTag";
	private String mPastDueDetailFragmentTag = "pastDueDetailFragmentTag";
	
	private final String SELECTED_BILL = "SELECTED_BILL";
	
	private MenuItem mMenuList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_transaction_activity);

		initDrawerMenu();
		
		initFragments();
		
		initWaitAfterFragmentRemovedTask(mPastDueListFragmentTag, mPastDueDetailFragmentTag);
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

		mPastDueListFragment = (PastDueListFragment) getFragmentManager().findFragmentByTag(mPastDueListFragmentTag);
		
		if (mPastDueListFragment == null) {
			mPastDueListFragment = new PastDueListFragment();

		} else {
			removeFragment(mPastDueListFragment);
		}
		
		mPastDueDetailFragment = (PastDueDetailFragment) getFragmentManager().findFragmentByTag(mPastDueDetailFragmentTag);
		
		if (mPastDueDetailFragment == null) {
			mPastDueDetailFragment = new PastDueDetailFragment();

		} else {
			removeFragment(mPastDueDetailFragment);
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
		
		mPastDueListFragment.setSelectedBill(mSelectedBill);
		mPastDueDetailFragment.setPastDueBill(mSelectedBill);
		
		if (mIsMultiplesPane) {

			addFragment(mPastDueListFragment, mPastDueListFragmentTag);
			addFragment(mPastDueDetailFragment, mPastDueDetailFragmentTag);
			
		} else {

			if (mSelectedBill != null) {
				
				addFragment(mPastDueDetailFragment, mPastDueDetailFragmentTag);
				
			} else {
				
				addFragment(mPastDueListFragment, mPastDueListFragmentTag);
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
			
			mPastDueListFragment.setSelectedBill(mSelectedBill);
			mPastDueDetailFragment.setPastDueBill(mSelectedBill);
			
		} else {

			replaceFragment(mPastDueDetailFragment, mPastDueDetailFragmentTag);
			mPastDueDetailFragment.setPastDueBill(mSelectedBill);
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
		
		mPastDueListFragment.setSelectedBill(mSelectedBill);
		mPastDueDetailFragment.setPastDueBill(mSelectedBill);
		
		if (mIsMultiplesPane) {
			
			initFragment();
			
		} else {
			
			replaceFragment(mPastDueListFragment, mPastDueListFragmentTag);
			initFragment();
		}
	}
	
	private void initFragment() {
		
		mPastDueListFragment.displayPastDueBills();
	}
	
	@Override
	protected void onAsyncTaskCompleted() {
		
		super.onAsyncTaskCompleted();
		
		mPastDueListFragment.updateContent();
		mPastDueDetailFragment.updateContent();
	}
}