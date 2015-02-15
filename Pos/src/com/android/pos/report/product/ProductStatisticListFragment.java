package com.android.pos.report.product;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.TransactionYear;
import com.android.pos.dao.Transactions;
import com.android.pos.service.ProductDaoService;
import com.android.pos.util.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ProductStatisticListFragment extends BaseFragment 
	implements ProductStatisticMonthArrayAdapter.ItemActionListener,
		ProductStatisticYearArrayAdapter.ItemActionListener {
	
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
	
	private ProductStatisticYearArrayAdapter mTransactionYearAdapter;
	private ProductStatisticMonthArrayAdapter mTransactionMonthAdapter;
	
	private ProductStatisticActionListener mActionListener;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
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
		
		mTransactionYearAdapter = new ProductStatisticYearArrayAdapter(getActivity(), mTransactionYears, this);
		mTransactionMonthAdapter = new ProductStatisticMonthArrayAdapter(getActivity(), mTransactionMonths, this);
		
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
		
		if (mSelectedTransactionYear != null) {
			onTransactionYearSelected(mSelectedTransactionYear);
			
		} else {
			displayTransactionAllYears();
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (ProductStatisticActionListener) activity;
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
	
	public void displayTransactionAllYears() {
		
		mTransactionYears.clear();
		mTransactionYears.addAll(mProductDaoService.getTransactionYears());
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(false);
		
		mNavigationTitle.setText(getString(R.string.transaction_total));
		mNavText.setText("Total   " + CommonUtil.formatCurrencyUnsigned(getTransactionYearsTotalAmount(mTransactionYears)));
		
		mTransactionList.setAdapter(mTransactionYearAdapter);
	}
	
	public void displayTransactionOnYear(TransactionYear transactionYear) {
		
		mTransactionMonths.clear();
		mTransactionMonths.addAll(mProductDaoService.getTransactionMonths(transactionYear));
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(true);
		
		mNavigationTitle.setText("Tahun " + CommonUtil.formatYear(transactionYear.getYear()));
		mNavText.setText("Total   " + CommonUtil.formatCurrencyUnsigned(getTransactionMonthsTotalAmount(mTransactionMonths)));
		
		mTransactionList.setAdapter(mTransactionMonthAdapter);
	}
	
	@Override
	public void onTransactionYearSelected(TransactionYear transactionYear) {
		
		setBackButtonVisible(true);
		
		mSelectedTransactionYear = transactionYear;
		
		mActionListener.onTransactionYearSelected(transactionYear);
		
		mTransactionMonths.clear();
		mTransactionMonths.addAll(mProductDaoService.getTransactionMonths(transactionYear));
		
		mNavigationTitle.setText("Tahun " + CommonUtil.formatYear(transactionYear.getYear()));
		mNavText.setText("Total   " + CommonUtil.formatCurrencyUnsigned(getTransactionMonthsTotalAmount(mTransactionMonths)));
		
		mTransactionList.setAdapter(mTransactionMonthAdapter);
	}
	
	@Override
	public TransactionYear getSelectedTransactionYear() {
		
		return mSelectedTransactionYear;
	}
	
	@Override
	public void onTransactionMonthSelected(TransactionMonth transactionMonth) {
		
		mSelectedTransactionMonth = transactionMonth;
		mActionListener.onTransactionMonthSelected(transactionMonth);
		mTransactionMonthAdapter.notifyDataSetChanged();
	}
	
	@Override
	public TransactionMonth getSelectedTransactionMonth() {
		
		return mSelectedTransactionMonth;
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