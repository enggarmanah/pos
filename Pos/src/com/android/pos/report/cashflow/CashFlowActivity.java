package com.android.pos.report.cashflow;

import java.io.Serializable;

import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.Bills;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.report.outstanding.OutstandingBillActivity;
import com.android.pos.report.pastdue.PastDueActivity;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CashFlowActivity extends BaseActivity 
	implements CashFlowActionListener {
	
	protected CashFlowListFragment mCashFlowListFragment;
	protected CashFlowDetailFragment mCashFlowDetailFragment;
	protected CashFlowBillDetailFragment mCashFlowBillDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private MenuItem mAlertMenu;
	private MenuItem mWarningMenu;
	
	private TextView mAlertMenuText;
	private ImageButton mAlertMenuBtn;
	
	private TextView mWarningMenuText;
	private ImageButton mWarningMenuBtn;
	
	private CashFlowYearBean mSelectedCashFlowYear;
	private CashFlowMonthBean mSelectedCashFlowMonth;
	
	private Bills mSelectedBill;
	
	private static String SELECTED_TRANSACTION_YEAR = "SELECTED_TRANSACTION_YEAR";
	private static String SELECTED_TRANSACTION_MONTH = "SELECTED_TRANSACTION_MONTH";
	private static String SELECTED_BILL = "SELECTED_BILL";
	
	public static final String DISPLAY_TRANSACTION_ALL_YEARS = "DISPLAY_TRANSACTION_ALL_YEARS";
	public static final String DISPLAY_TRANSACTION_ON_YEAR = "DISPLAY_TRANSACTION_ON_YEAR";
	public static final String DISPLAY_TRANSACTION_ON_MONTH = "DISPLAY_TRANSACTION_ON_MONTH";
	
	private String mCashFlowListFragmentTag = "cashFlowListFragmentTag";
	private String mCashFlowDetailFragmentTag = "cashFlowDetailFragmentTag";
	private String mCashFlowBillDetailFragmentTag = "cashFlowBillDetailFragmentTag";
	
	private Integer mPastDueBillsCount = 0;
	private Integer mOutstandingBillsCount = 0;
	
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
		
		initWaitAfterFragmentRemovedTask(mCashFlowListFragmentTag, mCashFlowDetailFragmentTag, mCashFlowBillDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_report_cashflow));
		setSelectedMenu(getString(R.string.menu_report_cashflow));
		
		updatePastDueBillsCount();
		updateOutstandingBillsCount();
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedCashFlowYear = (CashFlowYearBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_YEAR);
			mSelectedCashFlowMonth = (CashFlowMonthBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_MONTH);
			
			mSelectedBill = (Bills) savedInstanceState.getSerializable(SELECTED_BILL);
			
			mIsDisplayCashFlowAllYears = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ALL_YEARS);
			mIsDisplayCashFlowYear = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ON_YEAR);
			mIsDisplayCashFlowMonth = (Boolean) savedInstanceState.getSerializable(DISPLAY_TRANSACTION_ON_MONTH);
		
		} else {
			
			mIsDisplayCashFlowMonth = true;
			
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
		
		mCashFlowBillDetailFragment = (CashFlowBillDetailFragment) getFragmentManager().findFragmentByTag(mCashFlowBillDetailFragmentTag);
		
		if (mCashFlowBillDetailFragment == null) {
			mCashFlowBillDetailFragment = new CashFlowBillDetailFragment();

		} else {
			removeFragment(mCashFlowBillDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_TRANSACTION_YEAR, (Serializable) mSelectedCashFlowYear);
		outState.putSerializable(SELECTED_TRANSACTION_MONTH, (Serializable) mSelectedCashFlowMonth);
		
		outState.putSerializable(SELECTED_BILL, (Serializable) mSelectedBill);
		
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
		
		mCashFlowBillDetailFragment.setBill(mSelectedBill);
		
		if (mIsMultiplesPane) {

			addFragment(mCashFlowListFragment, mCashFlowListFragmentTag);
			
			if (mSelectedBill != null) {
				
				addFragment(mCashFlowBillDetailFragment, mCashFlowBillDetailFragmentTag);
				
			} else {
				
				addFragment(mCashFlowDetailFragment, mCashFlowDetailFragmentTag);
			}			
		} else {

			if (mSelectedBill != null) {
				
				addFragment(mCashFlowBillDetailFragment, mCashFlowBillDetailFragmentTag);
				
			} else if (mSelectedCashFlowMonth != null) {
				
				addFragment(mCashFlowDetailFragment, mCashFlowDetailFragmentTag);
				
			} else {
				
				addFragment(mCashFlowListFragment, mCashFlowListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.report_cashflow_menu, menu);
		
		mAlertMenu = menu.findItem(R.id.menu_item_alert);
		
		mAlertMenuText = (TextView) mAlertMenu.getActionView().findViewById(R.id.menu_item_alert_text);
		mAlertMenuBtn = (ImageButton) mAlertMenu.getActionView().findViewById(R.id.menu_item_alert_icon);
		
		String alertText = "0";
		
		if (mPastDueBillsCount > 99) {
			alertText = "++";
		} else if (mPastDueBillsCount > 0) {
			alertText = CommonUtil.formatNumber(mPastDueBillsCount);
		}
		
		mAlertMenuText.setText(alertText);
		mAlertMenuBtn.setOnClickListener(getMenuAlertOnClickListener());
		
		mAlertMenu.setVisible(false);
		
		if (mPastDueBillsCount != 0) {
			
			mAlertMenu.setVisible(true);
		}
		
		mWarningMenu = menu.findItem(R.id.menu_item_warning);
		
		mWarningMenuText = (TextView) mWarningMenu.getActionView().findViewById(R.id.menu_item_warning_text);
		mWarningMenuBtn = (ImageButton) mWarningMenu.getActionView().findViewById(R.id.menu_item_warning_icon);
		
		String warningText = "0";
		
		if (mOutstandingBillsCount > 99) {
			warningText = "++";
		} else if (mOutstandingBillsCount > 0) {
			warningText = CommonUtil.formatNumber(mOutstandingBillsCount);
		}
		
		mWarningMenuText.setText(warningText);
		mWarningMenuBtn.setOnClickListener(getMenuWarningOnClickListener());
		
		mWarningMenu.setVisible(false);
		
		if (mOutstandingBillsCount != 0) {
			
			mAlertMenu.setVisible(true);
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		hideSelectedMenu();
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	private View.OnClickListener getMenuAlertOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), PastDueActivity.class);
				startActivity(intent);
			}
		};
	}
	
	private View.OnClickListener getMenuWarningOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), OutstandingBillActivity.class);
				startActivity(intent);
			}
		};
	}
	
	private void updatePastDueBillsCount() {
		
		MerchantUtil.refreshPastDueBillsCount();
		
		mPastDueBillsCount = MerchantUtil.getPastDueBillsCount();
		
		if (mAlertMenuText != null) {
			mAlertMenuText.setText(CommonUtil.formatNumber(mPastDueBillsCount));
		}
		
		if (mPastDueBillsCount != 0) {
			
			if (mAlertMenu != null) {
				mAlertMenu.setVisible(true);
			}
		}
	}
	
	private void updateOutstandingBillsCount() {
		
		MerchantUtil.refreshOutstandingBillsCount();
		
		mOutstandingBillsCount = MerchantUtil.getOutstandingBillsCount();
		
		if (mWarningMenuText != null) {
			mWarningMenuText.setText(CommonUtil.formatNumber(mOutstandingBillsCount));
		}
		
		if (mOutstandingBillsCount != 0) {
			
			if (mWarningMenu != null) {
				mWarningMenu.setVisible(true);
			}
		}
	}
	
	private void hideSelectedMenu() {
		
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		if (mPastDueBillsCount != 0) {
			mAlertMenu.setVisible(!isDrawerOpen);
		}
		
		if (mOutstandingBillsCount != 0) {
			mWarningMenu.setVisible(!isDrawerOpen);
		}
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
	
	@Override
	public void onBillSelected(Bills bill) {
		
		mSelectedBill = bill;
		
		resetDisplayStatus();
		mIsDisplayCashFlowMonth = true;
		
		if (mIsMultiplesPane) {
			
			mCashFlowBillDetailFragment.setBill(mSelectedBill);
			
			removeFragment(mCashFlowListFragment);
			removeFragment(mCashFlowDetailFragment);
			
			initWaitAfterFragmentRemovedTask(mCashFlowListFragmentTag, mCashFlowDetailFragmentTag);
			
		} else {
			
			replaceFragment(mCashFlowBillDetailFragment, mCashFlowBillDetailFragmentTag);
			mCashFlowBillDetailFragment.setBill(mSelectedBill);
		}
	}
	
	@Override
	public void onBackPressed() {
		
		synchronized (CommonUtil.LOCK) {
			onBackToParent();
		}
	}
	
	private void onBackToParent() {
		
		if (mIsMultiplesPane) {
			
			if (mSelectedBill != null) {
				
				mSelectedBill = null;
				
				mCashFlowBillDetailFragment.setBill(mSelectedBill);
				
				removeFragment(mCashFlowListFragment);
				removeFragment(mCashFlowBillDetailFragment);
				
				initWaitAfterFragmentRemovedTask(mCashFlowListFragmentTag, mCashFlowBillDetailFragmentTag);
			
			} else {
				
				setDisplayStatusToParent();
				
				mCashFlowListFragment.setSelectedCashFlowYear(mSelectedCashFlowYear);
				mCashFlowListFragment.setSelectedCashFlowMonth(mSelectedCashFlowMonth);
				
				mCashFlowDetailFragment.setCashFlowMonth(mSelectedCashFlowMonth);
			}
			
			initFragment();
			
		} else {
			
			if (mSelectedBill != null) {
				
				mSelectedBill = null;
				replaceFragment(mCashFlowDetailFragment, mCashFlowDetailFragmentTag);
			
			} else {
				
				setDisplayStatusToParent();
				
				mCashFlowListFragment.setSelectedCashFlowYear(mSelectedCashFlowYear);
				mCashFlowListFragment.setSelectedCashFlowMonth(mSelectedCashFlowMonth);
				
				mCashFlowDetailFragment.setCashFlowMonth(mSelectedCashFlowMonth);
				
				replaceFragment(mCashFlowListFragment, mCashFlowListFragmentTag);
				
				initFragment();
			}
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
	
	@Override
	protected void onAsyncTaskCompleted() {
		
		super.onAsyncTaskCompleted();
		
		mCashFlowListFragment.updateContent();
		mCashFlowDetailFragment.updateContent();
	}
}