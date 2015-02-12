package com.android.pos.transaction;

import java.io.Serializable;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.Transactions;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TransactionActivity extends BaseActivity 
	implements TransactionActionListener {
	
	protected TransactionListFragment mTransactionListFragment;
	protected TransactionDetailFragment mTransactionDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private TransactionMonth mSelectedTransactionMonth;
	private TransactionDay mSelectedTransactionDay;
	private Transactions mSelectedTransaction;
	
	private static String SELECTED_TRANSACTION_DAY = "SELECTED_TRANSACTION_DAY";
	private static String SELECTED_TRANSACTION = "SELECTED_TRANSACTION";
	
	private String mTransactionListFragmentTag = "transactionListFragmentTag";
	private String mTransactionDetailFragmentTag = "transactionDetailFragmentTag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.transaction_activity);

		DbUtil.initDb(this);

		initDrawerMenu();

		initFragments(savedInstanceState);

		setTitle(getString(R.string.menu_transaction));

		mDrawerList.setItemChecked(Constant.MENU_TRANSACTION_POSITION, true);
		
		initWaitAfterFragmentRemovedTask(mTransactionListFragmentTag, mTransactionDetailFragmentTag);
		
		if (mSelectedTransactionMonth == null) {
			
			mSelectedTransactionMonth = new TransactionMonth();
			mSelectedTransactionMonth.setMonth(CommonUtil.getCurrentMonth());
		}
	}
	
	private void initFragments(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedTransactionDay = (TransactionDay) savedInstanceState.getSerializable(SELECTED_TRANSACTION_DAY);
			mSelectedTransaction = (Transactions) savedInstanceState.getSerializable(SELECTED_TRANSACTION);
		}

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

		outState.putSerializable(SELECTED_TRANSACTION_DAY, (Serializable) mSelectedTransactionDay);
		outState.putSerializable(SELECTED_TRANSACTION, (Serializable) mSelectedTransaction);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mTransactionListFragment.setSelectedTransactionSummary(mSelectedTransactionDay);
		mTransactionListFragment.setSelectedTransaction(mSelectedTransaction);

		if (mIsMultiplesPane) {

			addFragment(mTransactionListFragment, mTransactionListFragmentTag);
			addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
			
			mTransactionListFragment.setSelectedTransactionSummary(mSelectedTransactionDay);
			mTransactionDetailFragment.setTransaction(mSelectedTransaction);
			
		} else {

			if (mSelectedTransaction != null) {
				
				addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
				mTransactionDetailFragment.setTransaction(mSelectedTransaction);
				
			} else {
				
				addFragment(mTransactionListFragment, mTransactionListFragmentTag);
				mTransactionListFragment.setSelectedTransactionSummary(mSelectedTransactionDay);
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
				
				onMenuListSelected();

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onTransactionSummarySelected(TransactionDay transactionSummary) {
		
		mSelectedTransactionDay = transactionSummary;
	}
	
	@Override
	public void onTransactionSelected(Transactions transaction) {
		
		mSelectedTransaction = transaction;
		
		if (mIsMultiplesPane) {

			mTransactionDetailFragment.setTransaction(transaction);
			
		} else {

			replaceFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
			mTransactionDetailFragment.setTransaction(transaction);
		}
	}
	
	@Override
	public void onBackButtonClicked() {
		
		onMenuListSelected();
	}
	
	private void onMenuListSelected() {
		
		if (mIsMultiplesPane) {
			
			mSelectedTransaction = null;
			mSelectedTransactionDay = null;
			
			mTransactionListFragment.displayTransactionByMonth(mSelectedTransactionMonth);
			mTransactionDetailFragment.setTransaction(null);
			
		} else {
			
			if (mSelectedTransaction != null) {
				mSelectedTransaction = null;
			} else {
				mSelectedTransactionDay = null;
			}
			
			replaceFragment(mTransactionListFragment, mTransactionListFragmentTag);
			
			if (mSelectedTransactionDay != null) {
				mTransactionListFragment.displayTransactionToday();
			} else {
				mTransactionListFragment.displayTransactionByMonth(mSelectedTransactionMonth);
			}
		}
	}
}