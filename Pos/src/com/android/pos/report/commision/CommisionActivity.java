package com.android.pos.report.commision;

import java.io.Serializable;

import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.Employee;
import com.android.pos.model.CommisionMonthBean;
import com.android.pos.model.CommisionYearBean;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CommisionActivity extends BaseActivity 
	implements CommisionActionListener {
	
	protected CommisionListFragment mProductStatisticListFragment;
	protected CommisionDetailFragment mProductStatisticDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private CommisionYearBean mSelectedCommisionYear;
	private CommisionMonthBean mSelectedCommisionMonth;
	
	private Employee mSelectedEmployee;
	
	private static String SELECTED_TRANSACTION_YEAR = "SELECTED_TRANSACTION_YEAR";
	private static String SELECTED_TRANSACTION_MONTH = "SELECTED_TRANSACTION_MONTH";
	private static String SELECTED_EMPLOYEE = "SELECTED_EMPLOYEE";
	
	public static final String DISPLAY_TRANSACTION_ALL_YEARS = "DISPLAY_TRANSACTION_ALL_YEARS";
	public static final String DISPLAY_TRANSACTION_ON_YEAR = "DISPLAY_TRANSACTION_ON_YEAR";
	public static final String DISPLAY_TRANSACTION_ON_MONTH = "DISPLAY_TRANSACTION_ON_MONTH";
	
	private String mProductStatisticListFragmentTag = "productStatisticListFragmentTag";
	private String mProductStatisticDetailFragmentTag = "productStatisticDetailFragmentTag";
	
	private boolean mIsDisplayCommisionAllYears = false;
	private boolean mIsDisplayCommisionYear = false;
	private boolean mIsDisplayCommisionMonth = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_commision_activity);

		DbUtil.initDb(this);
		
		initDrawerMenu();
		
		initFragments();
		
		initWaitAfterFragmentRemovedTask(mProductStatisticListFragmentTag, mProductStatisticDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_report_commision));
		setSelectedMenu(getString(R.string.menu_report_commision));
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedCommisionYear = (CommisionYearBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_YEAR);
			mSelectedCommisionMonth = (CommisionMonthBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_MONTH);
			
			mSelectedEmployee = (Employee) savedInstanceState.getSerializable(SELECTED_EMPLOYEE);
			
			mIsDisplayCommisionAllYears = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ALL_YEARS);
			mIsDisplayCommisionYear = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ON_YEAR);
			mIsDisplayCommisionMonth = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ON_MONTH);
		
		} else {
			
			mIsDisplayCommisionMonth = true;
			
			mSelectedCommisionMonth = new CommisionMonthBean();
			mSelectedCommisionMonth.setMonth(CommonUtil.getCurrentMonth());
			
			mSelectedCommisionYear = new CommisionYearBean();
			mSelectedCommisionYear.setYear(CommonUtil.getCurrentYear());
		} 
	}
	
	private void initFragments() {
		
		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mProductStatisticListFragment = (CommisionListFragment) getFragmentManager().findFragmentByTag(mProductStatisticListFragmentTag);
		
		if (mProductStatisticListFragment == null) {
			mProductStatisticListFragment = new CommisionListFragment();

		} else {
			removeFragment(mProductStatisticListFragment);
		}
		
		mProductStatisticDetailFragment = (CommisionDetailFragment) getFragmentManager().findFragmentByTag(mProductStatisticDetailFragmentTag);
		
		if (mProductStatisticDetailFragment == null) {
			mProductStatisticDetailFragment = new CommisionDetailFragment();

		} else {
			removeFragment(mProductStatisticDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_TRANSACTION_YEAR, (Serializable) mSelectedCommisionYear);
		outState.putSerializable(SELECTED_TRANSACTION_MONTH, (Serializable) mSelectedCommisionMonth);
		
		outState.putSerializable(SELECTED_EMPLOYEE, (Serializable) mSelectedEmployee);
		
		outState.putSerializable(DISPLAY_TRANSACTION_ALL_YEARS, (Serializable) mIsDisplayCommisionAllYears);
		outState.putSerializable(DISPLAY_TRANSACTION_ON_YEAR, (Serializable) mIsDisplayCommisionYear);
		outState.putSerializable(DISPLAY_TRANSACTION_ON_MONTH, (Serializable) mIsDisplayCommisionMonth);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mProductStatisticListFragment.setSelectedCommisionYear(mSelectedCommisionYear);
		mProductStatisticListFragment.setSelectedCommisionMonth(mSelectedCommisionMonth);
		
		mProductStatisticDetailFragment.setCommisionMonth(mSelectedCommisionMonth);
		mProductStatisticDetailFragment.setEmployee(mSelectedEmployee);
		
		if (mIsMultiplesPane) {

			addFragment(mProductStatisticListFragment, mProductStatisticListFragmentTag);
			addFragment(mProductStatisticDetailFragment, mProductStatisticDetailFragmentTag);
			
		} else {

			if (mSelectedCommisionMonth != null) {
				
				addFragment(mProductStatisticDetailFragment, mProductStatisticDetailFragmentTag);
				
			} else {
				
				addFragment(mProductStatisticListFragment, mProductStatisticListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		synchronized (CommonUtil.LOCK) {
		
			switch (item.getItemId()) {
	
				default:
					return super.onOptionsItemSelected(item);
			}
		}
	}
	
	@Override
	public void onCommisionYearSelected(CommisionYearBean commisionYear) {
		
		mSelectedCommisionYear = commisionYear;
		
		resetDisplayStatus();
		mIsDisplayCommisionYear = true;
		
		mProductStatisticListFragment.setSelectedCommisionYear(commisionYear);
	}
	
	@Override
	public void onCommisionMonthSelected(CommisionMonthBean commisionMonth) {
		
		mSelectedCommisionMonth = commisionMonth;
		
		resetDisplayStatus();
		mIsDisplayCommisionMonth = true;
		
		if (mIsMultiplesPane) {
			
			mProductStatisticListFragment.setSelectedCommisionMonth(commisionMonth);
			mProductStatisticDetailFragment.setCommisionMonth(commisionMonth);
			
		} else {

			replaceFragment(mProductStatisticDetailFragment, mProductStatisticDetailFragmentTag);
			mProductStatisticDetailFragment.setCommisionMonth(commisionMonth);
		}
	}
	
	@Override
	public void onEmployeeSelected(Employee employee) {
		
		mSelectedEmployee = employee;
	}
	
	@Override
	public void onBackPressed() {
		
		synchronized (CommonUtil.LOCK) {
			onBackToParent();
		}
	}
	
	private void onBackToParent() {
		
		if (mSelectedEmployee != null) {
			
			mSelectedEmployee = null;
			mProductStatisticDetailFragment.setCommisionMonth(mSelectedCommisionMonth);
			
			return;
		}
		
		setDisplayStatusToParent();
		
		mProductStatisticListFragment.setSelectedCommisionYear(mSelectedCommisionYear);
		mProductStatisticListFragment.setSelectedCommisionMonth(mSelectedCommisionMonth);
		
		mProductStatisticDetailFragment.setCommisionMonth(mSelectedCommisionMonth);
		
		if (mIsMultiplesPane) {
			
			initFragment();
			
		} else {
			
			replaceFragment(mProductStatisticListFragment, mProductStatisticListFragmentTag);
			initFragment();
		}
	}
	
	private void initFragment() {
		
		if (mIsDisplayCommisionAllYears) {
			
			mProductStatisticListFragment.displayCommisionAllYears();
			
		} else if (mIsDisplayCommisionYear) {
			
			mProductStatisticListFragment.displayCommisionOnYear(mSelectedCommisionYear);
		}
	}
	
	private void resetDisplayStatus() {
		
		mIsDisplayCommisionAllYears = false;
		mIsDisplayCommisionYear = false;
		mIsDisplayCommisionMonth = false;
	}
	
	private void setDisplayStatusToParent() {
		
		if (mIsDisplayCommisionYear) {
			
			mIsDisplayCommisionYear = false;
			mSelectedCommisionYear = null;
			
			mIsDisplayCommisionAllYears = true;
			
		} else if (mIsDisplayCommisionMonth) {
			
			mIsDisplayCommisionMonth = false;
			mSelectedCommisionMonth = null;
			
			if (mIsMultiplesPane) {
				mIsDisplayCommisionAllYears = true;
			} else {
				mIsDisplayCommisionYear = true;
			}
		}
	}
	
	@Override
	protected void onAsyncTaskCompleted() {
		
		super.onAsyncTaskCompleted();
		
		mProductStatisticListFragment.updateContent();
		mProductStatisticDetailFragment.updateContent();
	}
}