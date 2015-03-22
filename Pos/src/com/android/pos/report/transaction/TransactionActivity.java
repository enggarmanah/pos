package com.android.pos.report.transaction;

import java.io.Serializable;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.Transactions;
import com.android.pos.model.TransactionDayBean;
import com.android.pos.model.TransactionMonthBean;
import com.android.pos.model.TransactionYearBean;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.PrintUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TransactionActivity extends BaseActivity 
	implements TransactionActionListener {
	
	protected TransactionListFragment mTransactionListFragment;
	protected TransactionDetailFragment mTransactionDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private TransactionYearBean mSelectedTransactionYear;
	private TransactionMonthBean mSelectedTransactionMonth;
	private TransactionDayBean mSelectedTransactionDay;
	private Transactions mSelectedTransaction;
	
	private static String SELECTED_TRANSACTION_YEAR = "SELECTED_TRANSACTION_YEAR";
	private static String SELECTED_TRANSACTION_MONTH = "SELECTED_TRANSACTION_MONTH";
	private static String SELECTED_TRANSACTION_DAY = "SELECTED_TRANSACTION_DAY";
	private static String SELECTED_TRANSACTION = "SELECTED_TRANSACTION";
	
	private String mTransactionListFragmentTag = "transactionListFragmentTag";
	private String mTransactionDetailFragmentTag = "transactionDetailFragmentTag";
	
	private boolean mIsDisplayTransactionAllYears = false;
	private boolean mIsDisplayTransactionYear = false;
	private boolean mIsDisplayTransactionMonth = false;
	private boolean mIsDisplayTransactionDay = false;
	private boolean mIsDisplayTransaction = false;
	
	private MenuItem mPrintItem;
	private MenuItem mUpItem;
	private MenuItem mSyncItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_transaction_activity);

		DbUtil.initDb(this);

		initDrawerMenu();
		
		initFragments();
		
		initWaitAfterFragmentRemovedTask(mTransactionListFragmentTag, mTransactionDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.menu_transaction));
		setSelectedMenu(getString(R.string.menu_transaction));
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedTransactionYear = (TransactionYearBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_YEAR);
			mSelectedTransactionMonth = (TransactionMonthBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_MONTH);
			mSelectedTransactionDay = (TransactionDayBean) savedInstanceState.getSerializable(SELECTED_TRANSACTION_DAY);
			mSelectedTransaction = (Transactions) savedInstanceState.getSerializable(SELECTED_TRANSACTION);
			
			if (mSelectedTransaction != null) {
				mIsDisplayTransaction = true;
			} else if (mSelectedTransactionDay != null) {
				mIsDisplayTransactionDay = true;
			} else if (mSelectedTransactionMonth != null) {
				mIsDisplayTransactionMonth = true;
			} else if (mSelectedTransactionYear != null) {
				mIsDisplayTransactionYear = true;
			} else {
				mIsDisplayTransactionAllYears = true;
			}
			
		} else {
			
			mIsDisplayTransactionMonth = true;
			
			mSelectedTransactionMonth = new TransactionMonthBean();
			mSelectedTransactionMonth.setMonth(CommonUtil.getCurrentMonth());
				
			mSelectedTransactionYear = new TransactionYearBean();
			mSelectedTransactionYear.setYear(CommonUtil.getCurrentYear());
		} 
	}
	
	private void initFragments() {
		
		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mTransactionListFragment = (TransactionListFragment) getFragmentManager().findFragmentByTag(mTransactionListFragmentTag);
		
		if (mTransactionListFragment == null) {
			mTransactionListFragment = new TransactionListFragment();

		} else {
			removeFragment(mTransactionListFragment);
		}
		
		mTransactionDetailFragment = (TransactionDetailFragment) getFragmentManager().findFragmentByTag(mTransactionDetailFragmentTag);
		
		if (mTransactionDetailFragment == null) {
			mTransactionDetailFragment = new TransactionDetailFragment();

		} else {
			removeFragment(mTransactionDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_TRANSACTION_YEAR, (Serializable) mSelectedTransactionYear);
		outState.putSerializable(SELECTED_TRANSACTION_MONTH, (Serializable) mSelectedTransactionMonth);
		outState.putSerializable(SELECTED_TRANSACTION_DAY, (Serializable) mSelectedTransactionDay);
		outState.putSerializable(SELECTED_TRANSACTION, (Serializable) mSelectedTransaction);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mTransactionListFragment.setSelectedTransactionYear(mSelectedTransactionYear);
		mTransactionListFragment.setSelectedTransactionMonth(mSelectedTransactionMonth);
		mTransactionListFragment.setSelectedTransactionDay(mSelectedTransactionDay);
		mTransactionListFragment.setSelectedTransaction(mSelectedTransaction);

		if (mIsMultiplesPane) {

			addFragment(mTransactionListFragment, mTransactionListFragmentTag);
			addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
			
			mTransactionDetailFragment.setTransaction(mSelectedTransaction);
			
		} else {

			if (mSelectedTransaction != null) {
				
				addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
				mTransactionDetailFragment.setTransaction(mSelectedTransaction);
				
			} else {
				
				addFragment(mTransactionListFragment, mTransactionListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.report_transaction_menu, menu);
		
		mPrintItem = menu.findItem(R.id.menu_item_print);
		mUpItem = menu.findItem(R.id.menu_item_up);
		mSyncItem = menu.findItem(R.id.menu_item_sync);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		initMenus();
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			
			case R.id.menu_item_print:
				
				if (PrintUtil.isPrinterConnected()) {
					try {
						PrintUtil.print(mSelectedTransaction);
					} catch (Exception e) {
						showMessage(Constant.MESSAGE_PRINTER_CANT_PRINT);
					}
				} else {
					NotificationUtil.setAlertMessage(getFragmentManager(), Constant.MESSAGE_PRINTER_CONNECT_PRINTER_FROM_CASHIER);
				}
				
				return true;
		
			case R.id.menu_item_up:
				
				onBackToParent();
				
				return true;
				
			case R.id.menu_item_sync:
				
				mProgressDialog.show(getFragmentManager(), "progressDialogTag");
				
				if (mHttpAsyncManager == null) {
					mHttpAsyncManager = new HttpAsyncManager(this); 
				}
				
				mHttpAsyncManager.sync(); 
				
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void hideAllMenus() {
		
		mPrintItem.setVisible(false);
		mUpItem.setVisible(false);
		mSyncItem.setVisible(false);
	}
	
	@Override
	public void onTransactionYearSelected(TransactionYearBean transactionYear) {
		
		mSelectedTransactionYear = transactionYear;
		
		resetDisplayStatus();
		mIsDisplayTransactionYear = true;
		
		initMenus();
	}
	
	@Override
	public void onTransactionMonthSelected(TransactionMonthBean transactionMonth) {
		
		mSelectedTransactionMonth = transactionMonth;
		
		resetDisplayStatus();
		mIsDisplayTransactionMonth = true;
		
		initMenus();
	}
	
	@Override
	public void onTransactionDaySelected(TransactionDayBean transactionDay) {
		
		mSelectedTransactionDay = transactionDay;
		
		resetDisplayStatus();
		mIsDisplayTransactionDay = true;
		
		initMenus();
	}
	
	@Override
	public void onTransactionSelected(Transactions transaction) {
		
		mSelectedTransaction = transaction;
		
		resetDisplayStatus();
		mIsDisplayTransaction = true;
		
		if (mIsMultiplesPane) {

			mTransactionDetailFragment.setTransaction(transaction);
			
		} else {

			replaceFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
			mTransactionDetailFragment.setTransaction(transaction);
		}
		
		initMenus();
	}
	
	@Override
	public void onBackButtonClicked() {
		
		synchronized (CommonUtil.LOCK) {
			onBackToParent();
		}
	}
	
	private void onBackToParent() {
		
		setDisplayStatusToParent();
		
		if (mIsMultiplesPane) {
			
			initFragment();
			
			mTransactionDetailFragment.setTransaction(null);
			
		} else {
			
			replaceFragment(mTransactionListFragment, mTransactionListFragmentTag);
			
			initFragment();
		}
		
		initMenus();
	}
	
	private void initFragment() {
		
		if (mIsDisplayTransactionAllYears) {
			mTransactionListFragment.displayTransactionAllYears();
			
		} else if (mIsDisplayTransactionYear) {
			mTransactionListFragment.displayTransactionOnYear(mSelectedTransactionYear);
			
		} else if (mIsDisplayTransactionMonth) {
			mTransactionListFragment.displayTransactionOnMonth(mSelectedTransactionMonth);
			
		} else if (mIsDisplayTransactionDay) {
			mTransactionListFragment.displayTransactionOnDay(mSelectedTransactionDay);
		
		} else if (mIsDisplayTransaction) {
			mUpItem.setVisible(true);
		}
	}
	
	private void initMenus() {
		
		if (mUpItem == null) {
			return;
		}
		
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		if (isDrawerOpen) {
			
			hideAllMenus();
		
		} else {
			
			hideAllMenus();
			
			mSyncItem.setVisible(true);
			
			if (mIsDisplayTransactionYear || mIsDisplayTransactionMonth || mIsDisplayTransactionDay) {
				mUpItem.setVisible(true);
				
			} else if (mIsDisplayTransaction) {
				mUpItem.setVisible(true);
				mPrintItem.setVisible(true);
			}
		}
	}
	
	private void resetDisplayStatus() {
		
		mIsDisplayTransactionAllYears = false;
		mIsDisplayTransactionYear = false;
		mIsDisplayTransactionMonth = false;
		mIsDisplayTransactionDay = false;
		mIsDisplayTransaction = false;
	}
	
	private void setDisplayStatusToParent() {
		
		if (mIsDisplayTransactionYear) {
			
			mIsDisplayTransactionYear = false;
			mSelectedTransactionYear = null;
			
			mIsDisplayTransactionAllYears = true;
			
		} else if (mIsDisplayTransactionMonth) {
			
			mIsDisplayTransactionMonth = false;
			mSelectedTransactionMonth = null;
			
			mIsDisplayTransactionYear = true;
			
		} else if (mIsDisplayTransactionDay) {
			
			mIsDisplayTransactionDay = false;
			mSelectedTransactionDay = null;
			
			mIsDisplayTransactionMonth = true;
		
		} else if (mIsDisplayTransaction) {
					
			mIsDisplayTransaction = false;
			mSelectedTransaction = null;
			
			if (mIsMultiplesPane) {
				
				mIsDisplayTransactionDay = false;
				mSelectedTransactionDay = null;
				
				mIsDisplayTransactionMonth = true;
				
				System.out.println("month : " + mSelectedTransactionMonth);
			} else {
				mIsDisplayTransactionDay = true;
			}
		}
	}
}