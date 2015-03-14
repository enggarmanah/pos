package com.android.pos.report.product;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.dao.Transactions;
import com.android.pos.model.TransactionDayBean;
import com.android.pos.model.TransactionMonthBean;
import com.android.pos.model.TransactionYearBean;
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
	
	private List<TransactionYearBean> mTransactionYears;
	private List<TransactionMonthBean> mTransactionMonths;
	private List<TransactionDayBean> mTransactionDays;
	private List<Transactions> mTransactions;
	
	private TransactionYearBean mSelectedTransactionYear;
	private TransactionMonthBean mSelectedTransactionMonth;
	
	private String mSelectedProductInfo = Constant.PRODUCT_REVENUE;
	
	private ProductStatisticYearArrayAdapter mTransactionYearAdapter;
	private ProductStatisticMonthArrayAdapter mTransactionMonthAdapter;
	
	private ProductStatisticActionListener mActionListener;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
	private String mStatus;
	
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
			mTransactionYears = new ArrayList<TransactionYearBean>();
		}
		
		if (mTransactionMonths == null) {
			mTransactionMonths = new ArrayList<TransactionMonthBean>();
		}
		
		if (mTransactionDays == null) {
			mTransactionDays = new ArrayList<TransactionDayBean>();
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
	
	public void setSelectedProductInfo(String productInfo) {
		
		mSelectedProductInfo = productInfo;
		updateContent();
	}
	
	private void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}
		
		if (ProductStatisticActivity.DISPLAY_TRANSACTION_ALL_YEARS.equals(mStatus)) {
			displayTransactionAllYears();
			
		} else if (ProductStatisticActivity.DISPLAY_TRANSACTION_ON_YEAR.equals(mStatus)) {
			displayTransactionOnYear(mSelectedTransactionYear);
			
		} else if (ProductStatisticActivity.DISPLAY_TRANSACTION_ON_MONTH.equals(mStatus)) {
			displayTransactionOnMonth(mSelectedTransactionMonth);
		}
	}
	
	public void setSelectedTransactionYear(TransactionYearBean transactionYear) {
		
		mSelectedTransactionYear = transactionYear;
		
		if (transactionYear == null) {
			return;
		}
		
		mStatus = ProductStatisticActivity.DISPLAY_TRANSACTION_ON_YEAR;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void setSelectedTransactionMonth(TransactionMonthBean transactionMonth) {
		
		mSelectedTransactionMonth = transactionMonth;
		
		if (transactionMonth == null) {
			return;
		}
		
		mStatus = ProductStatisticActivity.DISPLAY_TRANSACTION_ON_MONTH;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void refreshTransactionYear() {
		
		displayTransactionOnYear(mSelectedTransactionYear);
	} 
	
	private int getTransactionYearsTotalAmount(List<TransactionYearBean> transactionYears) {
		
		int totalAmount = 0;
		
		for (TransactionYearBean transactionYear : transactionYears) {
			totalAmount += transactionYear.getAmount();
		}
		
		return totalAmount;
	}
	
	private int getTransactionMonthsTotalAmount(List<TransactionMonthBean> transactionMonths) {
		
		int totalAmount = 0;
		
		for (TransactionMonthBean transactionMonth : transactionMonths) {
			totalAmount += transactionMonth.getAmount();
		}
		
		return totalAmount;
	}
	
	public void displayTransactionAllYears() {
		
		mStatus = ProductStatisticActivity.DISPLAY_TRANSACTION_ALL_YEARS;
		
		mTransactionYears.clear();
		
		if (Constant.PRODUCT_QUANTITY.equals(mSelectedProductInfo)) {
			mTransactionYears.addAll(mProductDaoService.getTransactionYearsQuantity());
			
		} else if (Constant.PRODUCT_REVENUE.equals(mSelectedProductInfo)) {
			mTransactionYears.addAll(mProductDaoService.getTransactionYearsRevenue());
			
		} else if (Constant.PRODUCT_PROFIT.equals(mSelectedProductInfo)) {
			mTransactionYears.addAll(mProductDaoService.getTransactionYearsProfit());
		}
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(false);
		
		mNavigationTitle.setText(getString(R.string.transaction_total));
		
		if (Constant.PRODUCT_QUANTITY.equals(mSelectedProductInfo)) {
			mNavText.setText("Total   " + CommonUtil.formatNumber(getTransactionYearsTotalAmount(mTransactionYears)));
		} else {
			mNavText.setText(CommonUtil.formatCurrency(getTransactionYearsTotalAmount(mTransactionYears)));
		}
		
		mTransactionList.setAdapter(mTransactionYearAdapter);
	}
	
	public void displayTransactionOnYear(TransactionYearBean transactionYear) {
		
		mStatus = ProductStatisticActivity.DISPLAY_TRANSACTION_ON_YEAR;
		
		mTransactionMonths.clear();
		
		if (Constant.PRODUCT_QUANTITY.equals(mSelectedProductInfo)) {
			mTransactionMonths.addAll(mProductDaoService.getTransactionMonthsQuantity(transactionYear));
			
		} else if (Constant.PRODUCT_REVENUE.equals(mSelectedProductInfo)) {
			mTransactionMonths.addAll(mProductDaoService.getTransactionMonthsRevenue(transactionYear));
		
		} else if (Constant.PRODUCT_PROFIT.equals(mSelectedProductInfo)) {
			mTransactionMonths.addAll(mProductDaoService.getTransactionMonthsProfit(transactionYear));
		}
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(true);
		
		mNavigationTitle.setText("Tahun " + CommonUtil.formatYear(transactionYear.getYear()));
		
		if (Constant.PRODUCT_QUANTITY.equals(mSelectedProductInfo)) {
			mNavText.setText("Total   " + CommonUtil.formatNumber(getTransactionMonthsTotalAmount(mTransactionMonths)));
		} else {
			mNavText.setText(CommonUtil.formatCurrency(getTransactionMonthsTotalAmount(mTransactionMonths)));
		}
		
		mTransactionList.setAdapter(mTransactionMonthAdapter);
	}
	
	public void displayTransactionOnMonth(TransactionMonthBean transactionMonth) {
		
		mStatus = ProductStatisticActivity.DISPLAY_TRANSACTION_ON_MONTH;
		
		mTransactionMonthAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onTransactionYearSelected(TransactionYearBean transactionYear) {
		
		mActionListener.onTransactionYearSelected(transactionYear);
	}
	
	@Override
	public TransactionYearBean getSelectedTransactionYear() {
		
		return mSelectedTransactionYear;
	}
	
	@Override
	public String getSelectedProductInfo() {
		
		return mSelectedProductInfo;
	}
	
	@Override
	public void onTransactionMonthSelected(TransactionMonthBean transactionMonth) {
		
		mActionListener.onTransactionMonthSelected(transactionMonth);
	}
	
	@Override
	public TransactionMonthBean getSelectedTransactionMonth() {
		
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