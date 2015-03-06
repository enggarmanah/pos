package com.android.pos.report.transaction;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.TransactionYear;
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
	implements TransactionArrayAdapter.ItemActionListener, 
		TransactionDayArrayAdapter.ItemActionListener, 
		TransactionMonthArrayAdapter.ItemActionListener,
		TransactionYearArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mNavigationTitle;
	private TextView mNavText;
	
	private ListView mTransactionList;
	
	private List<TransactionYear> mTransactionYears;
	private List<TransactionMonth> mTransactionMonths;
	private List<TransactionDay> mTransactionDays;
	private List<Transactions> mTransactions;
	
	private TransactionYear mSelectedTransactionYear;
	private TransactionMonth mSelectedTransactionMonth;
	private TransactionDay mSelectedTransactionDay;
	private Transactions mSelectedTransaction;
	
	private TransactionYearArrayAdapter mTransactionYearAdapter;
	private TransactionMonthArrayAdapter mTransactionMonthAdapter;
	private TransactionDayArrayAdapter mTransactionDayAdapter;
	private TransactionArrayAdapter mTransactionAdapter;
	
	private TransactionActionListener mActionListener;
	
	private TransactionsDaoService mTransactionDaoService;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mTransactionDaoService = new TransactionsDaoService();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_transaction_list_fragment, container, false);
		
		if (mTransactionYears == null) {
			mTransactionYears = new ArrayList<TransactionYear>();
		}
		
		if (mTransactionMonths == null) {
			mTransactionMonths = new ArrayList<TransactionMonth>();
		}
		
		if (mTransactionDays == null) {
			mTransactionDays = new ArrayList<TransactionDay>();
		}
		
		if (mTransactions == null) {
			mTransactions = new ArrayList<Transactions>();
		}
		
		mTransactionYearAdapter = new TransactionYearArrayAdapter(getActivity(), mTransactionYears, this);
		mTransactionMonthAdapter = new TransactionMonthArrayAdapter(getActivity(), mTransactionMonths, this);
		mTransactionDayAdapter = new TransactionDayArrayAdapter(getActivity(), mTransactionDays, this);
		mTransactionAdapter = new TransactionArrayAdapter(getActivity(), mTransactions, this);
		
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
		
		mTransactionList.setItemsCanFocus(true);
		mTransactionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		if (mSelectedTransaction != null) {
			onTransactionSelected(mSelectedTransaction);
			
		} else if (mSelectedTransactionDay != null) {
			onTransactionDaySelected(mSelectedTransactionDay);
			
		} else if (mSelectedTransactionMonth != null) {
			onTransactionMonthSelected(mSelectedTransactionMonth);
			
		} else if (mSelectedTransactionYear != null) {
			onTransactionYearSelected(mSelectedTransactionYear);
			
		} else {
			displayTransactionAllYears();
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
	
	public void setSelectedTransactionYear(TransactionYear transactionYear) {
		
		mSelectedTransactionYear = transactionYear;
	}
	
	public void setSelectedTransactionMonth(TransactionMonth transactionMonth) {
		
		mSelectedTransactionMonth = transactionMonth;
	}
	
	public void setSelectedTransactionDay(TransactionDay transactionDay) {
		
		mSelectedTransactionDay = transactionDay;
	}
	
	public void setSelectedTransaction(Transactions transaction) {
		
		mSelectedTransaction = transaction;
	}
	
	private int getTransactionYearsTotalAmount(List<TransactionYear> transactionYears) {
		
		int totalAmount = 0;
		
		for (TransactionYear transactionYear : transactionYears) {
			totalAmount += transactionYear.getAmount();
		}
		
		return totalAmount;
	}
	
	private int getTransactionMonthsTotalAmount(List<TransactionMonth> transactionMonths) {
		
		int totalAmount = 0;
		
		for (TransactionMonth transactionMonth : transactionMonths) {
			totalAmount += transactionMonth.getAmount();
		}
		
		return totalAmount;
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
	
	public void displayTransactionAllYears() {
		
		mSelectedTransaction = null;
		mSelectedTransactionDay = null;
		mSelectedTransactionMonth = null;
		mSelectedTransactionYear = null;
		
		mTransactionYears.clear();
		mTransactionYears.addAll(mTransactionDaoService.getTransactionYears());
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(false);
		
		mNavigationTitle.setText(getString(R.string.transaction_total));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionYearsTotalAmount(mTransactionYears)));
		
		mTransactionList.setAdapter(mTransactionYearAdapter);
	}
	
	public void displayTransactionOnYear(TransactionYear transactionYear) {
		
		mSelectedTransaction = null;
		mSelectedTransactionDay = null;
		mSelectedTransactionMonth = null;
		
		mTransactionMonths.clear();
		mTransactionMonths.addAll(mTransactionDaoService.getTransactionMonths(transactionYear));
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(true);
		
		mNavigationTitle.setText("Tahun " + CommonUtil.formatYear(transactionYear.getYear()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionMonthsTotalAmount(mTransactionMonths)));
		
		mTransactionList.setAdapter(mTransactionMonthAdapter);
	}
	
	public void displayTransactionOnMonth(TransactionMonth transactionMonth) {
		
		mSelectedTransaction = null;
		mSelectedTransactionDay = null;
		
		mTransactionDays.clear();
		mTransactionDays.addAll(mTransactionDaoService.getTransactionDays(transactionMonth));
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(true);
		
		mNavigationTitle.setText(CommonUtil.formatMonth(transactionMonth.getMonth()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionDaysTotalAmount(mTransactionDays)));
		
		mTransactionList.setAdapter(mTransactionDayAdapter);
	}
	
	public void displayTransactionOnDay(TransactionDay transactionDay) {
		
		mSelectedTransaction = null;
		
		if (!isViewInitialized()) {
			return;
		}
		
		onTransactionDaySelected(transactionDay);
	}
	
	@Override
	public void onTransactionSelected(Transactions transaction) {
		
		mSelectedTransaction = transaction;
		mActionListener.onTransactionSelected(transaction);
		mTransactionAdapter.notifyDataSetChanged();
	}
	
	@Override
	public Transactions getSelectedTransaction() {
		
		return mSelectedTransaction;
	}
	
	@Override
	public void onTransactionYearSelected(TransactionYear transactionYear) {
		
		setBackButtonVisible(true);
		
		mSelectedTransactionYear = transactionYear;
		mSelectedTransactionMonth = null;
		
		mActionListener.onTransactionYearSelected(transactionYear);
		
		mTransactionMonths.clear();
		mTransactionMonths.addAll(mTransactionDaoService.getTransactionMonths(transactionYear));
		
		mNavigationTitle.setText("Tahun " + CommonUtil.formatYear(transactionYear.getYear()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionMonthsTotalAmount(mTransactionMonths)));
		
		mTransactionList.setAdapter(mTransactionMonthAdapter);
	}
	
	@Override
	public TransactionYear getSelectedTransactionYear() {
		
		return mSelectedTransactionYear;
	}
	
	@Override
	public void onTransactionMonthSelected(TransactionMonth transactionMonth) {
		
		setBackButtonVisible(true);
		
		mSelectedTransactionMonth = transactionMonth;
		mSelectedTransactionDay = null;
		
		mActionListener.onTransactionMonthSelected(transactionMonth);
		
		mTransactionDays.clear();
		mTransactionDays.addAll(mTransactionDaoService.getTransactionDays(transactionMonth));
		
		mNavigationTitle.setText(CommonUtil.formatMonth(transactionMonth.getMonth()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionDaysTotalAmount(mTransactionDays)));
		
		mTransactionList.setAdapter(mTransactionDayAdapter);
	}
	
	@Override
	public TransactionMonth getSelectedTransactionMonth() {
		
		return mSelectedTransactionMonth;
	}
	
	@Override
	public void onTransactionDaySelected(TransactionDay transactionDay) {
		
		setBackButtonVisible(true);
		
		mSelectedTransactionDay = transactionDay;
		mSelectedTransaction = null;
		
		mActionListener.onTransactionDaySelected(transactionDay);
		
		mTransactions.clear();
		mTransactions.addAll(mTransactionDaoService.getTransactions(transactionDay.getDate()));
		
		mNavigationTitle.setText(CommonUtil.formatDayDate(transactionDay.getDate()));
		mNavText.setText(CommonUtil.formatCurrency(getTransactionsTotalAmount(mTransactions)));
		
		mTransactionList.setAdapter(mTransactionAdapter);
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