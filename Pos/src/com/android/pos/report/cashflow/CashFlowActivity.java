package com.android.pos.report.cashflow;

import java.io.Serializable;

import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CashFlowActivity extends BaseActivity 
	implements CashFlowActionListener {
	
	protected CashFlowListFragment mCashFlowListFragment;
	protected CashFlowDetailFragment mCashFlowDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private CashFlowYearBean mSelectedCashFlowYear;
	private CashFlowMonthBean mSelectedCashFlowMonth;
	
	private static String SELECTED_TRANSACTION_YEAR = "SELECTED_TRANSACTION_YEAR";
	private static String SELECTED_TRANSACTION_MONTH = "SELECTED_TRANSACTION_MONTH";
	
	public static final String DISPLAY_TRANSACTION_ALL_YEARS = "DISPLAY_TRANSACTION_ALL_YEARS";
	public static final String DISPLAY_TRANSACTION_ON_YEAR = "DISPLAY_TRANSACTION_ON_YEAR";
	public static final String DISPLAY_TRANSACTION_ON_MONTH = "DISPLAY_TRANSACTION_ON_MONTH";
	
	private String mCashFlowListFragmentTag = "cashFlowListFragmentTag";
	private String mCashFlowDetailFragmentTag = "cashFlowDetailFragmentTag";
	
	private boolean mIsDisplayCashFlowAllYears = false;
	private boolean mIsDisplayCashFlowYear = false;
	private boolean mIsDisplayCashFlowMonth = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_transaction_activity);

		DbUtil.initDb(this);

		initDrawerMenu();
		
		initFragments();
		
		initWaitAfterFragmentRemovedTask(mCashFlowListFragmentTag, mCashFlowDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_report_cashflow));
		setSelectedMenu(getString(R.string.menu_report_cashflow));
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedCashFlowYear = (CashFlowYearBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_YEAR);
			mSelectedCashFlowMonth = (CashFlowMonthBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_MONTH);
			
			mIsDisplayCashFlowAllYears = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ALL_YEARS);
			mIsDisplayCashFlowYear = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ON_YEAR);
			mIsDisplayCashFlowMonth = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ON_MONTH);
		
		} else {
			
			mSelectedCashFlowMonth = new CashFlowMonthBean();
			mSelectedCashFlowMonth.setMonth(CommonUtil.getCurrentMonth());
			
			mSelectedCashFlowYear = new CashFlowYearBean();
			mSelectedCashFlowYear.setYear(CommonUtil.getCurrentYear());
		} 
	}
	
	private void initFragments() {
		
		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mCashFlowListFragment = (CashFlowListFragment) getFragmentManager().findFragmentByTag(mCashFlowListFragmentTag);
		
		if (mCashFlowListFragment == null) {
			mCashFlowListFragment = new CashFlowListFragment();

		} else {
			removeFragment(mCashFlowListFragment);
		}
		
		mCashFlowDetailFragment = (CashFlowDetailFragment) getFragmentManager().findFragmentByTag(mCashFlowDetailFragmentTag);
		
		if (mCashFlowDetailFragment == null) {
			mCashFlowDetailFragment = new CashFlowDetailFragment();

		} else {
			removeFragment(mCashFlowDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_TRANSACTION_YEAR, (Serializable) mSelectedCashFlowYear);
		outState.putSerializable(SELECTED_TRANSACTION_MONTH, (Serializable) mSelectedCashFlowMonth);
		
		outState.putSerializable(DISPLAY_TRANSACTION_ALL_YEARS, (Serializable) mIsDisplayCashFlowAllYears);
		outState.putSerializable(DISPLAY_TRANSACTION_ON_YEAR, (Serializable) mIsDisplayCashFlowYear);
		outState.putSerializable(DISPLAY_TRANSACTION_ON_MONTH, (Serializable) mIsDisplayCashFlowMonth);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mCashFlowListFragment.setSelectedCashFlowYear(mSelectedCashFlowYear);
		mCashFlowListFragment.setSelectedCashFlowMonth(mSelectedCashFlowMonth);
		
		mCashFlowDetailFragment.setCashFlowMonth(mSelectedCashFlowMonth);
		
		if (mIsMultiplesPane) {

			addFragment(mCashFlowListFragment, mCashFlowListFragmentTag);
			addFragment(mCashFlowDetailFragment, mCashFlowDetailFragmentTag);
			
		} else {

			if (mSelectedCashFlowMonth != null) {
				
				addFragment(mCashFlowDetailFragment, mCashFlowDetailFragmentTag);
				
			} else {
				
				addFragment(mCashFlowListFragment, mCashFlowListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		hideSelectedMenu();
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	private void hideSelectedMenu() {
		
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		if (!isDrawerOpen) {
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		synchronized (CommonUtil.LOCK) {
		
			switch (item.getItemId()) {
	
				case R.id.menu_revenue:
					
					refreshParentView();
					hideSelectedMenu();
					
					return true;
				
				case R.id.menu_profit:
					
					refreshParentView();
					hideSelectedMenu();
					
					return true;
				
				case R.id.menu_quantity:
					
					refreshParentView();
					hideSelectedMenu();
					
					return true;
					
				default:
					return super.onOptionsItemSelected(item);
			}
		}
	}
	
	@Override
	public void onCashFlowYearSelected(CashFlowYearBean cashFlowYear) {
		
		mSelectedCashFlowYear = cashFlowYear;
		
		resetDisplayStatus();
		mIsDisplayCashFlowYear = true;
		
		mCashFlowListFragment.setSelectedCashFlowYear(cashFlowYear);
	}
	
	@Override
	public void onCashFlowMonthSelected(CashFlowMonthBean cashFlowMonth) {
		
		mSelectedCashFlowMonth = cashFlowMonth;
		
		resetDisplayStatus();
		mIsDisplayCashFlowMonth = true;
		
		if (mIsMultiplesPane) {
			
			mCashFlowListFragment.setSelectedCashFlowMonth(cashFlowMonth);
			mCashFlowDetailFragment.setCashFlowMonth(cashFlowMonth);
			
		} else {

			replaceFragment(mCashFlowDetailFragment, mCashFlowDetailFragmentTag);
			mCashFlowDetailFragment.setCashFlowMonth(cashFlowMonth);
		}
	}
	
	private void refreshParentView() {
		
		if (mIsMultiplesPane && mIsDisplayCashFlowMonth) {
			
			mCashFlowListFragment.setSelectedCashFlowYear(mSelectedCashFlowYear);
		}
	}
	
	@Override
	public void onBackButtonClicked() {
		
		synchronized (CommonUtil.LOCK) {
			onBackToParent();
		}
	}
	
	private void onBackToParent() {
		
		setDisplayStatusToParent();
		
		mCashFlowListFragment.setSelectedCashFlowYear(mSelectedCashFlowYear);
		mCashFlowListFragment.setSelectedCashFlowMonth(mSelectedCashFlowMonth);
		
		mCashFlowDetailFragment.setCashFlowMonth(mSelectedCashFlowMonth);
		
		if (mIsMultiplesPane) {
			
			initFragment();
			
		} else {
			
			replaceFragment(mCashFlowListFragment, mCashFlowListFragmentTag);
			initFragment();
		}
	}
	
	private void initFragment() {
		
		if (mIsDisplayCashFlowAllYears) {
			
			mCashFlowListFragment.displayCashFlowAllYears();
			
		} else if (mIsDisplayCashFlowYear) {
			
			mCashFlowListFragment.displayCashFlowOnYear(mSelectedCashFlowYear);
		}
	}
	
	private void resetDisplayStatus() {
		
		mIsDisplayCashFlowAllYears = false;
		mIsDisplayCashFlowYear = false;
		mIsDisplayCashFlowMonth = false;
	}
	
	private void setDisplayStatusToParent() {
		
		if (mIsDisplayCashFlowYear) {
			
			mIsDisplayCashFlowYear = false;
			mSelectedCashFlowYear = null;
			
			mIsDisplayCashFlowAllYears = true;
			
		} else if (mIsDisplayCashFlowMonth) {
			
			mIsDisplayCashFlowMonth = false;
			mSelectedCashFlowMonth = null;
			
			if (mIsMultiplesPane) {
				mIsDisplayCashFlowAllYears = true;
			} else {
				mIsDisplayCashFlowYear = true;
			}
		}
	}
}