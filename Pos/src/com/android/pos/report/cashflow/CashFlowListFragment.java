package com.android.pos.report.cashflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.BillsDaoService;
import com.android.pos.dao.TransactionsDaoService;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.util.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CashFlowListFragment extends BaseFragment 
	implements CashFlowMonthArrayAdapter.ItemActionListener,
		CashFlowYearArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mNavigationTitle;
	private TextView mNavText;
	
	private ListView mCashFlowList;
	
	private List<CashFlowYearBean> mCashFlowYears;
	private List<CashFlowMonthBean> mCashFlowMonths;
	
	private CashFlowYearBean mSelectedCashFlowYear;
	private CashFlowMonthBean mSelectedCashFlowMonth;
	
	private CashFlowYearArrayAdapter mCashFlowYearAdapter;
	private CashFlowMonthArrayAdapter mCashFlowMonthAdapter;
	
	private CashFlowActionListener mActionListener;
	
	private BillsDaoService mBillsDaoService = new BillsDaoService();
	private TransactionsDaoService mTransactionsDaoService = new TransactionsDaoService();
	
	private String mStatus;
	
	public CashFlowListFragment() {
		
		initList();
	}
	
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
		
		mCashFlowYearAdapter = new CashFlowYearArrayAdapter(getActivity(), mCashFlowYears, this);
		mCashFlowMonthAdapter = new CashFlowMonthArrayAdapter(getActivity(), mCashFlowMonths, this);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		mNavigationTitle = (TextView) getView().findViewById(R.id.navigationTitle);
		mNavText = (TextView) getView().findViewById(R.id.navText);
		
		mCashFlowList = (ListView) getActivity().findViewById(R.id.transactionList);
		
		mCashFlowList.setItemsCanFocus(true);
		mCashFlowList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		if (mSelectedCashFlowYear != null) {
			onCashFlowYearSelected(mSelectedCashFlowYear);		
		} else {
			displayCashFlowAllYears();
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (CashFlowActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CashFlowActionListener");
        }
    }
	
	private void initList() {
		
		if (mCashFlowYears == null) {
			mCashFlowYears = new ArrayList<CashFlowYearBean>();
		}
		
		if (mCashFlowMonths == null) {
			mCashFlowMonths = new ArrayList<CashFlowMonthBean>();
		}
	}
	
	public void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}
		
		if (CashFlowActivity.DISPLAY_TRANSACTION_ALL_YEARS.equals(mStatus)) {
			displayCashFlowAllYears();
			
		} else if (CashFlowActivity.DISPLAY_TRANSACTION_ON_YEAR.equals(mStatus)) {
			displayCashFlowOnYear(mSelectedCashFlowYear);
			
		} else if (CashFlowActivity.DISPLAY_TRANSACTION_ON_MONTH.equals(mStatus)) {
			displayCashFlowOnMonth(mSelectedCashFlowMonth);
		}
	}
	
	public void setSelectedCashFlowYear(CashFlowYearBean cashFlowYear) {
		
		mSelectedCashFlowYear = cashFlowYear;
		
		if (cashFlowYear == null) {
			return;
		}
		
		mStatus = CashFlowActivity.DISPLAY_TRANSACTION_ON_YEAR;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void setSelectedCashFlowMonth(CashFlowMonthBean cashFlowMonth) {
		
		mSelectedCashFlowMonth = cashFlowMonth;
		
		if (cashFlowMonth == null) {
			return;
		}
		
		mStatus = CashFlowActivity.DISPLAY_TRANSACTION_ON_MONTH;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void refreshCashFlowYear() {
		
		displayCashFlowOnYear(mSelectedCashFlowYear);
	} 
	
	private int getCashFlowYearsTotalAmount(List<CashFlowYearBean> cashFlowYears) {
		
		int totalAmount = 0;
		
		for (CashFlowYearBean cashFlowYear : cashFlowYears) {
			totalAmount += cashFlowYear.getAmount();
		}
		
		return totalAmount;
	}
	
	private int getCashFlowMonthsTotalAmount(List<CashFlowMonthBean> cashFlowMonths) {
		
		int totalAmount = 0;
		
		for (CashFlowMonthBean cashFlowMonth : cashFlowMonths) {
			totalAmount += cashFlowMonth.getAmount();
		}
		
		return totalAmount;
	}
	
	public List<CashFlowYearBean> combineYearIncomeAndExpense(List<CashFlowYearBean> incomeYears, List<CashFlowYearBean> expenseYears) {
		
		HashMap<Date, Long> cashFlowMap = new HashMap<Date, Long>(); 
		
		for (CashFlowYearBean incomeYear : incomeYears) {
			
			cashFlowMap.put(incomeYear.getYear(), incomeYear.getAmount());
		}
		
		for (CashFlowYearBean expenseYear : expenseYears) {
			
			Long amount = cashFlowMap.get(expenseYear.getYear());
			
			if (amount != null) {
				amount = amount - expenseYear.getAmount();
			} else {
				amount = - expenseYear.getAmount();
			}
			
			cashFlowMap.put(expenseYear.getYear(), amount);
		}
		
		Set<Date> incomeYear = cashFlowMap.keySet();
		Date[] cashFlowYears = incomeYear.toArray(new Date[incomeYear.size()]);
		
		Arrays.sort(cashFlowYears);
		
		List<CashFlowYearBean> cashFlowYearList = new ArrayList<CashFlowYearBean>();
		
		for (Date year : cashFlowYears) {
			
			Long amount = cashFlowMap.get(year);
			
			CashFlowYearBean cashFlowYear = new CashFlowYearBean();
			cashFlowYear.setYear(year);
			cashFlowYear.setAmount(amount);
			
			cashFlowYearList.add(cashFlowYear);
		}
		
		return cashFlowYearList;
	}
	
	public List<CashFlowMonthBean> combineMonthIncomeAndExpense(List<CashFlowMonthBean> incomeMonths, List<CashFlowMonthBean> expenseMonths) {
		
		HashMap<Date, Long> cashFlowMap = new HashMap<Date, Long>(); 
		
		for (CashFlowMonthBean incomeMonth : incomeMonths) {
			
			cashFlowMap.put(incomeMonth.getMonth(), incomeMonth.getAmount());
		}
		
		for (CashFlowMonthBean expenseMonth : expenseMonths) {
			
			Long amount = cashFlowMap.get(expenseMonth.getMonth());
			
			if (amount != null) {
				amount = amount - expenseMonth.getAmount();
			} else {
				amount = - expenseMonth.getAmount();
			}
			
			cashFlowMap.put(expenseMonth.getMonth(), amount);
		}
		
		Set<Date> incomeYear = cashFlowMap.keySet();
		Date[] cashFlowYears = incomeYear.toArray(new Date[incomeYear.size()]);
		
		Arrays.sort(cashFlowYears);
		
		List<CashFlowMonthBean> cashFlowMonthList = new ArrayList<CashFlowMonthBean>();
		
		for (Date month : cashFlowYears) {
			
			Long amount = cashFlowMap.get(month);
			
			CashFlowMonthBean cashFlowMonth = new CashFlowMonthBean();
			cashFlowMonth.setMonth(month);
			cashFlowMonth.setAmount(amount);
			
			cashFlowMonthList.add(cashFlowMonth);
		}
		
		return cashFlowMonthList;
	}
	
	public void displayCashFlowAllYears() {
		
		mStatus = CashFlowActivity.DISPLAY_TRANSACTION_ALL_YEARS;
		
		mCashFlowYears.clear();
		
		List<CashFlowYearBean> cashFlowYears = combineYearIncomeAndExpense(mTransactionsDaoService.getCashFlowYears(), mBillsDaoService.getBillYears());
		
		mCashFlowYears.addAll(cashFlowYears);
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(false);
		
		mNavigationTitle.setText(getString(R.string.transaction_total));
		
		mNavText.setText(CommonUtil.formatCurrency(getCashFlowYearsTotalAmount(mCashFlowYears)));
		
		mCashFlowList.setAdapter(mCashFlowYearAdapter);
	}
	
	public void displayCashFlowOnYear(CashFlowYearBean cashFlowYear) {
		
		mStatus = CashFlowActivity.DISPLAY_TRANSACTION_ON_YEAR;
		
		mCashFlowMonths.clear();
		
		List<CashFlowMonthBean> cashFlowMonths = combineMonthIncomeAndExpense(mTransactionsDaoService.getCashFlowMonths(cashFlowYear), mBillsDaoService.getBillMonths(cashFlowYear));
		
		mCashFlowMonths.addAll(cashFlowMonths);
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(true);
		
		mNavigationTitle.setText(getString(R.string.report_year, CommonUtil.formatYear(cashFlowYear.getYear())));
		
		mNavText.setText(CommonUtil.formatCurrency(getCashFlowMonthsTotalAmount(mCashFlowMonths)));
		
		mCashFlowList.setAdapter(mCashFlowMonthAdapter);
	}
	
	public void displayCashFlowOnMonth(CashFlowMonthBean cashFlowMonth) {
		
		mStatus = CashFlowActivity.DISPLAY_TRANSACTION_ON_MONTH;
		
		mCashFlowMonthAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onCashFlowYearSelected(CashFlowYearBean cashFlowYear) {
		
		mActionListener.onCashFlowYearSelected(cashFlowYear);
	}
	
	@Override
	public CashFlowYearBean getSelectedCashFlowYear() {
		
		return mSelectedCashFlowYear;
	}
	
	@Override
	public void onCashFlowMonthSelected(CashFlowMonthBean cashFlowMonth) {
		
		mActionListener.onCashFlowMonthSelected(cashFlowMonth);
	}
	
	@Override
	public CashFlowMonthBean getSelectedCashFlowMonth() {
		
		return mSelectedCashFlowMonth;
	}
	
	private View.OnClickListener getBackButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onBackPressed();
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