package com.android.pos.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.pos.CommonUtil;
import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.TransactionSummary;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionsDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class TransactionSummaryFragment extends BaseFragment 
	implements TransactionTodayArrayAdapter.ItemActionListener, TransactionSummaryArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mNavigationTitle;
	private TextView mNavText;
	
	private ListView mTransactionList;
	
	private List<TransactionSummary> mTransactionSummaries;
	private List<Transactions> mTransactions;
	
	private TransactionSummary mSelectedTransactionSummary;
	private Transactions mSelectedTransaction;
	
	private TransactionSummaryArrayAdapter mTransactionSummaryAdapter;
	private TransactionTodayArrayAdapter mTransactionTodayAdapter;
	
	private TransactionActionListener mActionListener;
	
	private TransactionsDao transactionDao = DbHelper.getSession().getTransactionsDao();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.transaction_summary_fragment, container, false);
		
		if (mTransactionSummaries == null) {
			mTransactionSummaries = new ArrayList<TransactionSummary>();
		}
		
		if (mTransactions == null) {
			mTransactions = new ArrayList<Transactions>();
		}
		
		mTransactionSummaryAdapter = new TransactionSummaryArrayAdapter(getActivity(), mTransactionSummaries, this);
		mTransactionTodayAdapter = new TransactionTodayArrayAdapter(getActivity(), mTransactions, this);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		mNavigationTitle = (TextView) getView().findViewById(R.id.navigationTitle);
		mNavText = (TextView) getView().findViewById(R.id.navText);
		
		mTransactionList = (ListView) getActivity().findViewById(R.id.transactionList);
		
		if (mSelectedTransactionSummary != null) {
			mTransactionList.setAdapter(mTransactionTodayAdapter);
		} else {
			mTransactionList.setAdapter(mTransactionSummaryAdapter);
		}
		
		mTransactionList.setItemsCanFocus(true);
		mTransactionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		if (mSelectedTransactionSummary != null) {
			onTransactionSummarySelected(mSelectedTransactionSummary);
		} else {
			displayTransactionSummary();
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (TransactionActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TransactionActionListener");
        }
    }
	
	public void setSelectedTransactionSummary(TransactionSummary transactionSummary) {
		
		mSelectedTransactionSummary = transactionSummary;
	}
	
	public void setSelectedTransaction(Transactions transaction) {
		
		mSelectedTransaction = transaction;
	}
	
	public List<Transactions> getTransactions(Date transactionDate) {
		
		Date startDate = transactionDate;
		Date endDate = new Date(startDate.getTime() + 24 * 60 * 60 * 1000 - 1);
		
		QueryBuilder<Transactions> qb = transactionDao.queryBuilder();
		qb.where(TransactionsDao.Properties.TransactionDate.between(startDate, endDate)).orderAsc(TransactionsDao.Properties.TransactionDate);

		Query<Transactions> q = qb.build();
		List<Transactions> list = q.list();
		
		return list;
	}
	
	public List<TransactionSummary> getTransactionSummary() {
		
		ArrayList<TransactionSummary> transactionSummaries = new ArrayList<TransactionSummary>();
		
		SQLiteDatabase db = DbHelper.getDb();
		Cursor cursor = db.rawQuery("SELECT strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount) total_amount "
				+ " FROM transactions "
				+ " GROUP BY strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime')", null);
		
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "dd-MM-yyyy");
			Long amount = cursor.getLong(1);
			TransactionSummary summary = new TransactionSummary();
			summary.setDate(date);
			summary.setAmount(amount);
			transactionSummaries.add(summary);
		}
		
		return transactionSummaries;
	}
	
	private int getTransactionsTotalAmount(List<Transactions> transactions) {
		
		int totalAmount = 0;
		
		for (Transactions transaction : transactions) {
			totalAmount += transaction.getTotalAmount();
		}
		
		return totalAmount;
	}
	
	public void displayTransactionToday() {
		
		mSelectedTransaction = null;
		
		if (!isViewInitialized()) {
			return;
		}
		
		onTransactionSummarySelected(mSelectedTransactionSummary);
	}
	
	public void displayTransactionSummary() {
		
		mSelectedTransaction = null;
		mSelectedTransactionSummary = null;
		
		mTransactionSummaries.clear();
		mTransactionSummaries.addAll(getTransactionSummary());
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(false);
		
		mNavigationTitle.setText(getString(R.string.transaction_recap));
		mNavText.setText(Constant.EMPTY_STRING);
		
		mTransactionList.setAdapter(mTransactionSummaryAdapter);
	}
	
	@Override
	public void onTransactionSelected(Transactions transaction) {
		
		mSelectedTransaction = transaction;
		mActionListener.onTransactionSelected(transaction);
		mTransactionTodayAdapter.notifyDataSetChanged();
	}
	
	@Override
	public Transactions getSelectedTransaction() {
		
		return mSelectedTransaction;
	}
	
	@Override
	public void onTransactionSummarySelected(TransactionSummary transactionSummary) {
		
		setBackButtonVisible(true);
		
		mSelectedTransactionSummary = transactionSummary;
		mActionListener.onTransactionSummarySelected(transactionSummary);
		
		mTransactions.clear();
		mTransactions.addAll(getTransactions(transactionSummary.getDate()));
		
		mNavigationTitle.setText(CommonUtil.formatDayDate(transactionSummary.getDate()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionsTotalAmount(mTransactions)));
		
		mTransactionList.setAdapter(mTransactionTodayAdapter);
	}
	
	@Override
	public TransactionSummary getSelectedTransactionSummary() {
		
		return mSelectedTransactionSummary;
	}
	
	private View.OnClickListener getBackButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onBackButtonClicked();
			}
		};
	}
	
	private void setBackButtonVisible(boolean isVisible) {
		
		if (isVisible) {
			mBackButton.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
		} else {
			mBackButton.getLayoutParams().width = 0;
		}
	}
}