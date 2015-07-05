package com.android.pos.report.cashflow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.CashflowDaoService;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashflowBean;
import com.android.pos.util.CommonUtil;

public class CashFlowDetailFragment extends BaseFragment {
	
	private ImageButton mBackButton;
	
	private TextView mDateText;
	private TextView mTotalText;
	
	private ListView mCashflowList;

	private CashFlowMonthBean mCashFlowMonth;
	private List<CashflowBean> mCashflows;
	
	private CashFlowActionListener mActionListener;
	
	private CashFlowDetailArrayAdapter mAdapter;
	
	private CashflowDaoService mCashflowDaoService = new CashflowDaoService();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_cashflow_detail_fragment, container, false);
		
		if (mCashflows == null) {
			mCashflows = new ArrayList<CashflowBean>();
		} 
		
		mAdapter = new CashFlowDetailArrayAdapter(getActivity(), mCashflows);
		
		return view;
	}
	
	private void initViewVariables() {
		
		mCashflowList = (ListView) getView().findViewById(R.id.billsList);

		mCashflowList.setAdapter(mAdapter);
		mCashflowList.setItemsCanFocus(true);
		mCashflowList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		boolean isMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (isMultiplesPane) {
			mBackButton.setVisibility(View.GONE);
		} else {
			mBackButton.setVisibility(View.VISIBLE);
		}
		
		mDateText = (TextView) getView().findViewById(R.id.dateText);
		mTotalText = (TextView) getView().findViewById(R.id.totalText);
	}
	
	public void onStart() {
		super.onStart();
		
		initViewVariables();
		updateContent();
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
	
	public void setCashFlowMonth(CashFlowMonthBean cashFlowMonth) {
		
		mCashFlowMonth = cashFlowMonth;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	private int getCashflowsTotalAmount(List<CashflowBean> cashflows) {
		
		int totalAmount = 0;
		
		for (CashflowBean cashflow : cashflows) {
			totalAmount += cashflow.getCash_amount();
		}
		
		return totalAmount;
	}
	
	public void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}

		if (mCashFlowMonth == null) {

			getView().setVisibility(View.INVISIBLE);
			return;
		}

		mDateText.setText(CommonUtil.formatMonth(mCashFlowMonth.getMonth()));

		mCashflows.clear();

		List<CashflowBean> cashflows = mCashflowDaoService.getCashFlowDays(mCashFlowMonth);

		mCashflows.addAll(cashflows);

		mAdapter.notifyDataSetChanged();

		Integer cashflowsTotalAmount = getCashflowsTotalAmount(cashflows);

		mTotalText.setText(CommonUtil.formatCurrency(cashflowsTotalAmount));

		if (cashflowsTotalAmount < 0) {
			mTotalText.setTextColor(getActivity().getResources().getColor(R.color.text_red));
		} else {
			mTotalText.setTextColor(getActivity().getResources().getColor(R.color.section_header_text));
		}

		getView().setVisibility(View.VISIBLE);

		mAdapter.notifyDataSetChanged();
	}
	
	private View.OnClickListener getBackButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onBackPressed();
			}
		};
	}
}