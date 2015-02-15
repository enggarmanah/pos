package com.android.pos.report.product;

import java.io.Serializable;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.TransactionYear;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ProductStatisticActivity extends BaseActivity 
	implements ProductStatisticActionListener {
	
	protected ProductStatisticListFragment mProductStatisticListFragment;
	protected ProductStatisticDetailFragment mProductStatisticDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private TransactionYear mSelectedTransactionYear;
	private TransactionMonth mSelectedTransactionMonth;
	
	private static String SELECTED_TRANSACTION_YEAR = "SELECTED_TRANSACTION_YEAR";
	private static String SELECTED_TRANSACTION_MONTH = "SELECTED_TRANSACTION_MONTH";
	
	private String mProductStatisticListFragmentTag = "productStatisticListFragmentTag";
	private String mProductStatisticDetailFragmentTag = "productStatisticDetailFragmentTag";
	
	private boolean mIsDisplayTransactionAllYears = false;
	private boolean mIsDisplayTransactionYear = false;
	private boolean mIsDisplayTransactionMonth = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.report_transaction_activity);

		DbUtil.initDb(this);

		initDrawerMenu();
		
		initInstanceState(savedInstanceState);
		
		initFragments();
		
		initWaitAfterFragmentRemovedTask(mProductStatisticListFragmentTag, mProductStatisticDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_statistic_product));

		mDrawerList.setItemChecked(Constant.MENU_STATISTIC_PRODUCT_POSITION, true);
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedTransactionYear = (TransactionYear) savedInstanceState.getSerializable(SELECTED_TRANSACTION_YEAR);
			mSelectedTransactionMonth = (TransactionMonth) savedInstanceState.getSerializable(SELECTED_TRANSACTION_MONTH);
		
		} else {
			
			mSelectedTransactionMonth = new TransactionMonth();
			mSelectedTransactionMonth.setMonth(CommonUtil.getCurrentMonth());
			
			mSelectedTransactionYear = new TransactionYear();
			mSelectedTransactionYear.setYear(CommonUtil.getCurrentYear());
		} 
	}
	
	private void initFragments() {
		
		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mProductStatisticListFragment = (ProductStatisticListFragment) getFragmentManager().findFragmentByTag(mProductStatisticListFragmentTag);
		
		if (mProductStatisticListFragment == null) {
			mProductStatisticListFragment = new ProductStatisticListFragment();

		} else {
			removeFragment(mProductStatisticListFragment);
		}
		
		mProductStatisticDetailFragment = (ProductStatisticDetailFragment) getFragmentManager().findFragmentByTag(mProductStatisticDetailFragmentTag);
		
		if (mProductStatisticDetailFragment == null) {
			mProductStatisticDetailFragment = new ProductStatisticDetailFragment();

		} else {
			removeFragment(mProductStatisticDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_TRANSACTION_YEAR, (Serializable) mSelectedTransactionYear);
		outState.putSerializable(SELECTED_TRANSACTION_MONTH, (Serializable) mSelectedTransactionMonth);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mProductStatisticListFragment.setSelectedTransactionYear(mSelectedTransactionYear);
		mProductStatisticListFragment.setSelectedTransactionMonth(mSelectedTransactionMonth);

		if (mIsMultiplesPane) {

			addFragment(mProductStatisticListFragment, mProductStatisticListFragmentTag);
			addFragment(mProductStatisticDetailFragment, mProductStatisticDetailFragmentTag);
			
			mProductStatisticDetailFragment.setTransactionMonth(mSelectedTransactionMonth);
			
		} else {

			if (mSelectedTransactionMonth != null) {
				
				addFragment(mProductStatisticDetailFragment, mProductStatisticDetailFragmentTag);
				mProductStatisticDetailFragment.setTransactionMonth(mSelectedTransactionMonth);
				
			} else {
				
				addFragment(mProductStatisticListFragment, mProductStatisticListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.transaction_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		menu.findItem(R.id.menu_item_list).setVisible(!isDrawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

			case R.id.menu_item_list:
				
				onBackToParent();

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onTransactionYearSelected(TransactionYear transactionYear) {
		
		mSelectedTransactionYear = transactionYear;
		
		resetDisplayStatus();
		mIsDisplayTransactionYear = true;
	}
	
	@Override
	public void onTransactionMonthSelected(TransactionMonth transactionMonth) {
		
		mSelectedTransactionMonth = transactionMonth;
		
		resetDisplayStatus();
		mIsDisplayTransactionMonth = true;
		
		if (mIsMultiplesPane) {

			mProductStatisticDetailFragment.setTransactionMonth(transactionMonth);
			
		} else {

			replaceFragment(mProductStatisticDetailFragment, mProductStatisticDetailFragmentTag);
			mProductStatisticDetailFragment.setTransactionMonth(transactionMonth);
		}
	}
	
	@Override
	public void onBackButtonClicked() {
		
		onBackToParent();
	}
	
	private void onBackToParent() {
		
		setDisplayStatusToParent();
		
		if (mIsMultiplesPane) {
			
			initFragment();
			
			mProductStatisticDetailFragment.setTransactionMonth(null);
			
		} else {
			
			mProductStatisticListFragment.setSelectedTransactionMonth(null);
			replaceFragment(mProductStatisticListFragment, mProductStatisticListFragmentTag);
			
			initFragment();
		}
	}
	
	private void initFragment() {
		
		if (mIsDisplayTransactionAllYears) {
			
			mProductStatisticListFragment.displayTransactionAllYears();
			
		} else if (mIsDisplayTransactionYear) {
			
			mProductStatisticListFragment.displayTransactionOnYear(mSelectedTransactionYear);
			
		}
	}
	
	private void resetDisplayStatus() {
		
		mIsDisplayTransactionAllYears = false;
		mIsDisplayTransactionYear = false;
		mIsDisplayTransactionMonth = false;
	}
	
	private void setDisplayStatusToParent() {
		
		if (mIsDisplayTransactionYear) {
			
			mIsDisplayTransactionYear = false;
			mSelectedTransactionYear = null;
			
			mIsDisplayTransactionAllYears = true;
			
		} else if (mIsDisplayTransactionMonth) {
			
			mIsDisplayTransactionMonth = false;
			mSelectedTransactionMonth = null;
			
			if (mIsMultiplesPane) {
				mIsDisplayTransactionAllYears = true;
			} else {
				mIsDisplayTransactionYear = true;
			}
		}
	}
}