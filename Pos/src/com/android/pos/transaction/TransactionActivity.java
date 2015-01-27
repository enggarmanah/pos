package com.android.pos.transaction;

import java.io.Serializable;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.TransactionSummary;
import com.android.pos.dao.Transactions;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TransactionActivity extends BaseActivity 
	implements TransactionActionListener {
	
	protected TransactionSummaryFragment mTransactionSummaryFragment;
	protected TransactionDetailFragment mTransactionDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private TransactionSummary mSelectedTransactionSummary;
	private Transactions mSelectedTransaction;
	
	private static String SELECTED_TRANSACTION_SUMMARY = "SELECTED_TRANSACTION_SUMMARY";
	private static String SELECTED_TRANSACTION = "SELECTED_TRANSACTION";
	
	private String mTransactionSummaryFragmentTag = "transactionSummaryFragmentTag";
	private String mTransactionDetailFragmentTag = "transactionDetailFragmentTag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.transaction_activity);

		DbHelper.initDb(this);

		initDrawerMenu();

		initFragments(savedInstanceState);

		setTitle(getString(R.string.menu_transaction));

		mDrawerList.setItemChecked(Constant.MENU_TRANSACTION_POSITION, true);
		
		initWaitAfterFragmentRemovedTask(mTransactionSummaryFragmentTag, mTransactionDetailFragmentTag);
	}
	
	private void initFragments(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedTransactionSummary = (TransactionSummary) savedInstanceState.getSerializable(SELECTED_TRANSACTION_SUMMARY);
			mSelectedTransaction = (Transactions) savedInstanceState.getSerializable(SELECTED_TRANSACTION);
		}

		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mTransactionSummaryFragment = (TransactionSummaryFragment) getFragmentManager().findFragmentByTag(mTransactionSummaryFragmentTag);
		
		if (mTransactionSummaryFragment == null) {
			mTransactionSummaryFragment = new TransactionSummaryFragment();

		} else {
			removeFragment(mTransactionSummaryFragment);
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

		outState.putSerializable(SELECTED_TRANSACTION_SUMMARY, (Serializable) mSelectedTransactionSummary);
		outState.putSerializable(SELECTED_TRANSACTION, (Serializable) mSelectedTransaction);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mTransactionSummaryFragment.setSelectedTransactionSummary(mSelectedTransactionSummary);
		mTransactionSummaryFragment.setSelectedTransaction(mSelectedTransaction);

		if (mIsMultiplesPane) {

			addFragment(mTransactionSummaryFragment, mTransactionSummaryFragmentTag);
			addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
			
			mTransactionSummaryFragment.setSelectedTransactionSummary(mSelectedTransactionSummary);
			mTransactionDetailFragment.setTransaction(mSelectedTransaction);
			
		} else {

			if (mSelectedTransaction != null) {
				
				addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
				mTransactionDetailFragment.setTransaction(mSelectedTransaction);
				
			} else {
				
				addFragment(mTransactionSummaryFragment, mTransactionSummaryFragmentTag);
				mTransactionSummaryFragment.setSelectedTransactionSummary(mSelectedTransactionSummary);
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
	public void onTransactionSummarySelected(TransactionSummary transactionSummary) {
		
		mSelectedTransactionSummary = transactionSummary;
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
			mSelectedTransactionSummary = null;
			
			mTransactionSummaryFragment.displayTransactionSummary();
			mTransactionDetailFragment.setTransaction(null);
			
		} else {
			
			if (mSelectedTransaction != null) {
				mSelectedTransaction = null;
			} else {
				mSelectedTransactionSummary = null;
			}
			
			replaceFragment(mTransactionSummaryFragment, mTransactionSummaryFragmentTag);
			
			if (mSelectedTransactionSummary != null) {
				mTransactionSummaryFragment.displayTransactionToday();
			} else {
				mTransactionSummaryFragment.displayTransactionSummary();
			}
		}
	}
}