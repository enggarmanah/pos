package com.android.pos.data.cashflow;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.R;
import com.android.pos.base.activity.BaseItemMgtActivity;
import com.android.pos.dao.Bills;
import com.android.pos.dao.Cashflow;
import com.android.pos.dao.Transactions;
import com.android.pos.popup.search.BillDlgFragment;
import com.android.pos.popup.search.BillSelectionListener;
import com.android.pos.popup.search.TransactionDlgFragment;
import com.android.pos.popup.search.TransactionSelectionListener;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;

import android.os.Bundle;
import android.view.View;

public class CashflowMgtActivity extends BaseItemMgtActivity<CashflowSearchFragment, CashflowEditFragment, Cashflow> 
	implements TransactionSelectionListener, BillSelectionListener {
	
	List<Cashflow> mInventories;
	Cashflow mSelectedCashflow;
	
	private TransactionDlgFragment mTransactionDlgFragment;
	private BillDlgFragment mBillDlgFragment;
	
	private static String mTransactionDlgFragmentTag = "mTransactionDlgFragmentTag";
	private static String mBillDlgFragmentTag = "mBillDlgFragmentTag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTransactionDlgFragment = (TransactionDlgFragment) getFragmentManager().findFragmentByTag(mTransactionDlgFragmentTag);
		
		if (mTransactionDlgFragment == null) {
			mTransactionDlgFragment = new TransactionDlgFragment();
		}
		
		mBillDlgFragment = (BillDlgFragment) getFragmentManager().findFragmentByTag(mBillDlgFragmentTag);
		
		if (mBillDlgFragment == null) {
			mBillDlgFragment = new BillDlgFragment();
		}
	}
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.menu_cashflow));
		setSelectedMenu(getString(R.string.menu_cashflow));
	}
	
	@Override
	protected CashflowSearchFragment getSearchFragmentInstance() {
		
		return new CashflowSearchFragment();
	}

	@Override
	protected CashflowEditFragment getEditFragmentInstance() {
		
		return new CashflowEditFragment();
	}
	
	@Override
	protected void setSearchFragmentItems(List<Cashflow> inventories) {
		
		mSearchFragment.setItems(inventories);
	}
	
	@Override
	protected void setSearchFragmentSelectedItem(Cashflow cashflow) {
		
		mSearchFragment.setSelectedItem(cashflow);
	}
	
	@Override
	protected void setEditFragmentItem(Cashflow cashflow) {
		
		mEditFragment.setItem(cashflow);
	}
	
	@Override
	protected View getSearchFragmentView() {
		
		return mSearchFragment.getView();
	}

	@Override
	protected View getEditFragmentView() {
		
		return mEditFragment.getView();
	}
	
	@Override
	protected void enableEditFragmentInputFields(boolean isEnabled) {
		
		mEditFragment.enableInputFields(isEnabled);
	}

	@Override
	protected void doSearch(String query) {
		
		mSearchFragment.searchItem(query);
	}
	
	@Override
	protected Cashflow getItemInstance() {
		
		return new Cashflow();
	}
	
	@Override
	protected void unSelectItem() {
		
		mSearchFragment.unSelectItem();
	}
	
	@Override
	protected Cashflow updateEditFragmentItem(Cashflow cashflow) {
		
		return mEditFragment.updateItem(cashflow);
	}

	@Override
	protected void refreshEditView() {
		
		mEditFragment.refreshView();
	}
	
	@Override
	protected void addEditFragmentItem(Cashflow cashflow) {
	
		mEditFragment.addItem(cashflow);
	}
	
	@Override
	public void reloadItems() {
		
		mSearchFragment.reloadItems();
	}
	
	@Override
	protected Long getItemId(Cashflow cashflow) {
		
		return cashflow.getId();
	}
	
	@Override
	protected void refreshItem(Cashflow cashflow) {
		
		cashflow.refresh();
	}
	
	@Override
	protected void refreshSearchFragmentItems() {
		
		mSearchFragment.refreshItems();
	}
	
	@Override
	protected void saveItem() {
		
		mEditFragment.saveEditItem();
	}
	
	@Override
	protected void discardItem() {
		
		mEditFragment.discardEditItem();
	}
	
	@Override
	public void deleteItem(Cashflow item) {
		
		mSearchFragment.onItemDeleted(item);
	}
	
	@Override
	protected String getItemName(Cashflow item) {
		
		return CodeUtil.getCashflowTypeLabel(item.getType()) + " " + CommonUtil.formatDate(item.getCashDate()) + " " + CommonUtil.formatCurrency(item.getCashAmount());
	}
	
	@Override
	protected List<Cashflow> getItemsInstance() {
		
		return new ArrayList<Cashflow>();
	}
	
	public void onSelectTransaction(boolean isMandatory) {
		
		if (mTransactionDlgFragment.isAdded()) {
			return;
		}
		
		mTransactionDlgFragment.setMandatory(isMandatory);
		mTransactionDlgFragment.show(getFragmentManager(), mTransactionDlgFragmentTag);
	}
	
	public void onTransactionSelected(Transactions transaction) {
		
		mEditFragment.setTransaction(transaction);
	}
	
	public void onSelectBill(boolean isMandatory) {
		
		if (mBillDlgFragment.isAdded()) {
			return;
		}
		
		mBillDlgFragment.setMandatory(isMandatory);
		mBillDlgFragment.show(getFragmentManager(), mBillDlgFragmentTag);
	}
	
	public void onBillSelected(Bills bill) {
		
		mEditFragment.setBill(bill);
	}
}