package com.android.pos.transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.Transactions;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TransactionActivity extends BaseActivity 
	implements TransactionActionListener {
	
	protected TransactionTodayFragment mTransactionTodayFragment;
	protected TransactionDetailFragment mTransactionDetailFragment;
	
	
	boolean mIsMultiplesPane = false;
	
	Transactions mSelectedTransaction;
	List<Transactions> mTransactions;
	
	private static String TRANSACTIONS = "TRANSACTIONS";

	private String mTransactionTodayFragmentTag = "transactionTodayFragmentTag";
	private String mTransactionDetailFragmentTag = "transactionDetailFragmentTag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.transaction_activity);

		DbHelper.initDb(this);

		initDrawerMenu();

		initFragments(savedInstanceState);

		setTitle(getString(R.string.menu_today_transaction));

		mDrawerList.setItemChecked(Constant.MENU_TODAY_TRANSACTION_POSITION, true);
		
		initWaitAfterFragmentRemovedTask(mTransactionTodayFragmentTag);
	}
	
	@SuppressWarnings("unchecked")
	private void initFragments(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mTransactions = (List<Transactions>) savedInstanceState.getSerializable(TRANSACTIONS);
		}

		if (mTransactions == null) {
			mTransactions = new ArrayList<Transactions>();
		}

		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mTransactionTodayFragment = (TransactionTodayFragment) getFragmentManager().findFragmentByTag(mTransactionTodayFragmentTag);
		
		if (mTransactionTodayFragment == null) {
			mTransactionTodayFragment = new TransactionTodayFragment();

		} else {
			removeFragment(mTransactionTodayFragment);
		}
		
		mTransactionDetailFragment = (TransactionDetailFragment) getFragmentManager().findFragmentByTag(mTransactionDetailFragmentTag);
		
		if (mTransactionDetailFragment == null) {
			mTransactionDetailFragment = new TransactionDetailFragment();

		} else {
			removeFragment(mTransactionDetailFragment);
		}
	}

	@Override
	protected void afterFragmentRemoved() {
		
		System.out.println("afterFragmentRemoved");
		
		loadFragments();
	}
	
	private void loadFragments() {

		if (mIsMultiplesPane) {

			addFragment(mTransactionTodayFragment, mTransactionTodayFragmentTag);
			addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
			
		} else {

			if (mSelectedTransaction != null) {
				addFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
				
			} else {
				addFragment(mTransactionTodayFragment, mTransactionTodayFragmentTag);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		outState.putSerializable(TRANSACTIONS, (Serializable) mTransactions);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		//MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.cashier_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		//boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		//menu.findItem(R.id.menu_item_search).setVisible(!isDrawerOpen);
		//menu.findItem(R.id.menu_item_list).setVisible(!isDrawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTransactionSelected(Transactions transaction) {
		
		if (mIsMultiplesPane) {

			mTransactionDetailFragment.setTransaction(transaction);
			
		} else {

			replaceFragment(mTransactionDetailFragment, mTransactionDetailFragmentTag);
			mTransactionDetailFragment.setTransaction(transaction);
		}
	}
}