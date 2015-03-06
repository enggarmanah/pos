package com.android.pos.report.transaction;

import java.io.Serializable;

import com.android.pos.R;
import com.android.pos.async.HttpAsyncListener;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.HttpAsyncProgressDlgFragment;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.TransactionYear;
import com.android.pos.dao.Transactions;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.PrintUtil;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TransactionActivity extends BaseActivity 
	implements TransactionActionListener, HttpAsyncListener {
	
	protected TransactionListFragment mTransactionListFragment;
	protected TransactionDetailFragment mTransactionDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private TransactionYear mSelectedTransactionYear;
	private TransactionMonth mSelectedTransactionMonth;
	private TransactionDay mSelectedTransactionDay;
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
	
	private static HttpAsyncProgressDlgFragment mProgressDialog;

	private HttpAsyncManager mHttpAsyncManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_transaction_activity);

		DbUtil.initDb(this);

		initDrawerMenu();
		
		initFragments();
		
		mHttpAsyncManager = new HttpAsyncManager(this);
		
		initWaitAfterFragmentRemovedTask(mTransactionListFragmentTag, mTransactionDetailFragmentTag);
		
		mProgressDialog = (HttpAsyncProgressDlgFragment) getFragmentManager().findFragmentByTag("progressDialogTag");
		
		if (mProgressDialog == null) {
			mProgressDialog = new HttpAsyncProgressDlgFragment();
		}
	}
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.menu_transaction));
		setSelectedMenu(getString(R.string.menu_transaction));
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedTransactionYear = (TransactionYear) savedInstanceState.getSerializable(SELECTED_TRANSACTION_YEAR);
			mSelectedTransactionMonth = (TransactionMonth) savedInstanceState.getSerializable(SELECTED_TRANSACTION_MONTH);
			mSelectedTransactionDay = (TransactionDay) savedInstanceState.getSerializable(SELECTED_TRANSACTION_DAY);
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
			
			mSelectedTransactionMonth = new TransactionMonth();
			mSelectedTransactionMonth.setMonth(CommonUtil.getCurrentMonth());
				
			mSelectedTransactionYear = new TransactionYear();
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
				
				PrintUtil.print(mSelectedTransaction);
				
				return true;
		
			case R.id.menu_item_up:
				
				onBackToParent();
				
				return true;
				
			case R.id.menu_item_sync:
				
				mProgressDialog.show(getFragmentManager(), "progressDialogTag");
				
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
	public void onTransactionYearSelected(TransactionYear transactionYear) {
		
		mSelectedTransactionYear = transactionYear;
		
		resetDisplayStatus();
		mIsDisplayTransactionYear = true;
		
		initMenus();
	}
	
	@Override
	public void onTransactionMonthSelected(TransactionMonth transactionMonth) {
		
		mSelectedTransactionMonth = transactionMonth;
		
		resetDisplayStatus();
		mIsDisplayTransactionMonth = true;
		
		initMenus();
	}
	
	@Override
	public void onTransactionDaySelected(TransactionDay transactionDay) {
		
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
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "Tidak dapat terhubung ke Server!");
	}
}