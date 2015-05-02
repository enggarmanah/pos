package com.android.pos.report.commision;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.model.CommisionMonthBean;
import com.android.pos.model.CommisionYearBean;
import com.android.pos.util.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CommisionListFragment extends BaseFragment 
	implements CommisionMonthArrayAdapter.ItemActionListener,
		CommisionYearArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mNavigationTitle;
	private TextView mNavText;
	
	private ListView mCommisionList;
	
	private List<CommisionYearBean> mCommisionYears;
	private List<CommisionMonthBean> mCommisionMonths;
	
	private CommisionYearBean mSelectedCommisionYear;
	private CommisionMonthBean mSelectedCommisionMonth;
	
	private CommisionYearArrayAdapter mCommisionYearAdapter;
	private CommisionMonthArrayAdapter mCommisionMonthAdapter;
	
	private CommisionActionListener mActionListener;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
	private String mStatus;
	
	public CommisionListFragment() {
		
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
		
		mCommisionYearAdapter = new CommisionYearArrayAdapter(getActivity(), mCommisionYears, this);
		mCommisionMonthAdapter = new CommisionMonthArrayAdapter(getActivity(), mCommisionMonths, this);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		mNavigationTitle = (TextView) getView().findViewById(R.id.navigationTitle);
		mNavText = (TextView) getView().findViewById(R.id.navText);
		
		mCommisionList = (ListView) getActivity().findViewById(R.id.transactionList);
		
		mCommisionList.setItemsCanFocus(true);
		mCommisionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		if (mSelectedCommisionMonth != null) {
			
			displayCommisionOnYear(mSelectedCommisionYear);
			displayCommisionOnMonth(mSelectedCommisionMonth);
		}
		if (mSelectedCommisionYear != null) {
			
			displayCommisionOnYear(mSelectedCommisionYear);
			
		} else {
			displayCommisionAllYears();
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (CommisionActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CommisionActionListener");
        }
    }
	
	private void initList() {
		
		if (mCommisionYears == null) {
			mCommisionYears = new ArrayList<CommisionYearBean>();
		}
		
		if (mCommisionMonths == null) {
			mCommisionMonths = new ArrayList<CommisionMonthBean>();
		}
	}
	
	public void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}
		
		if (CommisionActivity.DISPLAY_TRANSACTION_ALL_YEARS.equals(mStatus)) {
			displayCommisionAllYears();
			
		} else if (CommisionActivity.DISPLAY_TRANSACTION_ON_YEAR.equals(mStatus)) {
			displayCommisionOnYear(mSelectedCommisionYear);
			
		} else if (CommisionActivity.DISPLAY_TRANSACTION_ON_MONTH.equals(mStatus)) {
			displayCommisionOnMonth(mSelectedCommisionMonth);
		}
	}
	
	public void setSelectedCommisionYear(CommisionYearBean transactionYear) {
		
		mSelectedCommisionYear = transactionYear;
		
		if (transactionYear == null) {
			return;
		}
		
		mStatus = CommisionActivity.DISPLAY_TRANSACTION_ON_YEAR;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void setSelectedCommisionMonth(CommisionMonthBean transactionMonth) {
		
		mSelectedCommisionMonth = transactionMonth;
		
		if (transactionMonth == null) {
			return;
		}
		
		mStatus = CommisionActivity.DISPLAY_TRANSACTION_ON_MONTH;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void refreshCommisionYear() {
		
		displayCommisionOnYear(mSelectedCommisionYear);
	} 
	
	private int getCommisionYearsTotalAmount(List<CommisionYearBean> transactionYears) {
		
		int totalAmount = 0;
		
		for (CommisionYearBean transactionYear : transactionYears) {
			totalAmount += transactionYear.getAmount();
		}
		
		return totalAmount;
	}
	
	private int getCommisionMonthsTotalAmount(List<CommisionMonthBean> transactionMonths) {
		
		int totalAmount = 0;
		
		for (CommisionMonthBean transactionMonth : transactionMonths) {
			totalAmount += transactionMonth.getAmount();
		}
		
		return totalAmount;
	}
	
	public void displayCommisionAllYears() {
		
		mStatus = CommisionActivity.DISPLAY_TRANSACTION_ALL_YEARS;
		
		mCommisionYears.clear();
		
		mCommisionYears.addAll(mProductDaoService.getCommisionYears());
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(false);
		
		mNavigationTitle.setText(getString(R.string.transaction_total));
		
		mNavText.setText(CommonUtil.formatCurrency(getCommisionYearsTotalAmount(mCommisionYears)));
		
		mCommisionList.setAdapter(mCommisionYearAdapter);
	}
	
	public void displayCommisionOnYear(CommisionYearBean transactionYear) {
		
		mStatus = CommisionActivity.DISPLAY_TRANSACTION_ON_YEAR;
		
		mCommisionMonths.clear();
		
		mCommisionMonths.addAll(mProductDaoService.getCommisionMonths(transactionYear));
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(true);
		
		mNavigationTitle.setText("Tahun " + CommonUtil.formatYear(transactionYear.getYear()));
		
		mNavText.setText(CommonUtil.formatCurrency(getCommisionMonthsTotalAmount(mCommisionMonths)));
		
		mCommisionList.setAdapter(mCommisionMonthAdapter);
	}
	
	public void displayCommisionOnMonth(CommisionMonthBean transactionMonth) {
		
		mStatus = CommisionActivity.DISPLAY_TRANSACTION_ON_MONTH;
		
		mCommisionList.setAdapter(mCommisionMonthAdapter);
		mCommisionMonthAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onCommisionYearSelected(CommisionYearBean transactionYear) {
		
		mActionListener.onCommisionYearSelected(transactionYear);
	}
	
	@Override
	public CommisionYearBean getSelectedCommisionYear() {
		
		return mSelectedCommisionYear;
	}
	
	@Override
	public void onCommisionMonthSelected(CommisionMonthBean transactionMonth) {
		
		mActionListener.onCommisionMonthSelected(transactionMonth);
	}
	
	@Override
	public CommisionMonthBean getSelectedCommisionMonth() {
		
		return mSelectedCommisionMonth;
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