package com.android.pos.transaction;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.Transactions;
import com.android.pos.service.TransactionsDaoService;
import com.android.pos.util.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class TransactionListFragment extends BaseFragment 
	implements TransactionDayArrayAdapter.ItemActionListener, TransactionMonthArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mNavigationTitle;
	private TextView mNavText;
	
	private ListView mTransactionList;
	
	private List<TransactionDay> mTransactionDays;
	private List<Transactions> mTransactions;
	
	private TransactionMonth mSelectedTransactionMonth;
	private TransactionDay mSelectedTransactionDay;
	private Transactions mSelectedTransaction;
	
	private TransactionMonthArrayAdapter mTransactionMonthAdapter;
	private TransactionDayArrayAdapter mTransactionDayAdapter;
	
	private TransactionActionListener mActionListener;
	
	private TransactionsDaoService mTransactionDaoService = new TransactionsDaoService();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mSelectedTransactionMonth = new TransactionMonth();
		mSelectedTransactionMonth.setMonth(CommonUtil.getCurrentMonth());
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.transaction_summary_fragment, container, false);
		
		if (mTransactionDays == null) {
			mTransactionDays = new ArrayList<TransactionDay>();
		}
		
		if (mTransactions == null) {
			mTransactions = new ArrayList<Transactions>();
		}
		
		mTransactionMonthAdapter = new TransactionMonthArrayAdapter(getActivity(), mTransactionDays, this);
		mTransactionDayAdapter = new TransactionDayArrayAdapter(getActivity(), mTransactions, this);
		
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
		
		if (mSelectedTransactionDay != null) {
			mTransactionList.setAdapter(mTransactionDayAdapter);
		} else {
			mTransactionList.setAdapter(mTransactionMonthAdapter);
		}
		
		mTransactionList.setItemsCanFocus(true);
		mTransactionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		if (mSelectedTransactionDay != null) {
			onTransactionDaySelected(mSelectedTransactionDay);
		} else {
			displayTransactionByMonth(mSelectedTransactionMonth);
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
	
	public void setSelectedTransactionSummary(TransactionDay transactionSummary) {
		
		mSelectedTransactionDay = transactionSummary;
	}
	
	public void setSelectedTransaction(Transactions transaction) {
		
		mSelectedTransaction = transaction;
	}
	
	private int getTransactionDaysTotalAmount(List<TransactionDay> transactionDays) {
		
		int totalAmount = 0;
		
		for (TransactionDay transactionDay : transactionDays) {
			totalAmount += transactionDay.getAmount();
		}
		
		return totalAmount;
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
		
		onTransactionDaySelected(mSelectedTransactionDay);
	}
	
	public void displayTransactionByMonth(TransactionMonth transactionMonth) {
		
		mSelectedTransaction = null;
		mSelectedTransactionDay = null;
		
		mTransactionDays.clear();
		mTransactionDays.addAll(mTransactionDaoService.getTransactionSummary(transactionMonth));
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(true);
		
		mNavigationTitle.setText(CommonUtil.formatMonthDate(transactionMonth.getMonth()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionDaysTotalAmount(mTransactionDays)));
		
		mTransactionList.setAdapter(mTransactionMonthAdapter);
	}
	
	@Override
	public void onTransactionSelected(Transactions transaction) {
		
		mSelectedTransaction = transaction;
		mActionListener.onTransactionSelected(transaction);
		mTransactionDayAdapter.notifyDataSetChanged();
	}
	
	@Override
	public Transactions getSelectedTransaction() {
		
		return mSelectedTransaction;
	}
	
	@Override
	public void onTransactionDaySelected(TransactionDay transactionDay) {
		
		setBackButtonVisible(true);
		
		mSelectedTransactionDay = transactionDay;
		mActionListener.onTransactionDaySelected(transactionDay);
		
		mTransactions.clear();
		mTransactions.addAll(mTransactionDaoService.getTransactions(transactionDay.getDate()));
		
		mNavigationTitle.setText(CommonUtil.formatDayDate(transactionDay.getDate()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionsTotalAmount(mTransactions)));
		
		mTransactionList.setAdapter(mTransactionDayAdapter);
	}
	
	@Override
	public TransactionDay getSelectedTransactionDay() {
		
		return mSelectedTransactionDay;
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
			mBackButton.setVisibility(View.VISIBLE);
		} else {
			mBackButton.setVisibility(View.GONE);
		}
	}
}