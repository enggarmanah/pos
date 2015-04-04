package com.android.pos.report.cashflow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.Bills;
import com.android.pos.dao.BillsDaoService;
import com.android.pos.dao.TransactionsDaoService;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.util.CommonUtil;

public class CashFlowDetailFragment extends BaseFragment 
	implements CashFlowDetailArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mDateText;
	
	private TextView mTotalAmountText;
	private TextView mTaxAmountText;
	private TextView mServiceChargeAmountText;
	
	private TextView mTotalBillsText;
	private TextView mTotalText;
	
	private LinearLayout mTaxPanel;
	private LinearLayout mServiceChargePanel;
	
	private ListView mBillsList;

	private CashFlowMonthBean mCashFlowMonth;
	private List<Bills> mBills;
	
	private CashFlowActionListener mActionListener;
	
	private CashFlowDetailArrayAdapter mAdapter;
	
	private BillsDaoService mBillsDaoService = new BillsDaoService();
	private TransactionsDaoService mTransactionsDaoService = new TransactionsDaoService();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_cashflow_detail_fragment, container, false);
		
		if (mBills == null) {
			mBills = new ArrayList<Bills>();
		} 
		
		mAdapter = new CashFlowDetailArrayAdapter(getActivity(), mBills, this);
		
		return view;
	}
	
	private void initViewVariables() {
		
		mBillsList = (ListView) getView().findViewById(R.id.billsList);

		mBillsList.setAdapter(mAdapter);
		mBillsList.setItemsCanFocus(true);
		mBillsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		boolean isMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (isMultiplesPane) {
			mBackButton.setVisibility(View.GONE);
		} else {
			mBackButton.setVisibility(View.VISIBLE);
		}
		
		mDateText = (TextView) getView().findViewById(R.id.dateText);
		
		mTotalAmountText = (TextView) getView().findViewById(R.id.totalAmountText);
		mTaxAmountText = (TextView) getView().findViewById(R.id.taxAmountText);
		mServiceChargeAmountText = (TextView) getView().findViewById(R.id.serviceChargeAmountText);
		
		mTaxPanel = (LinearLayout) getView().findViewById(R.id.taxPanel);
		mServiceChargePanel = (LinearLayout) getView().findViewById(R.id.serviceChargePanel);
		
		mTotalBillsText = (TextView) getView().findViewById(R.id.totalBillsText);
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
	
	private int getBillsTotalAmount(List<Bills> bills) {
		
		int totalAmount = 0;
		
		for (Bills bill : bills) {
			totalAmount += bill.getPayment();
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

		List<Long> income = mTransactionsDaoService.getMonthIncome(mCashFlowMonth.getMonth());

		Long totalAmount = income.get(0);
		Long taxAmount = income.get(1);
		Long serviceChargeAmount = income.get(2);

		mTotalAmountText.setText(CommonUtil.formatCurrency(totalAmount));
		mTaxAmountText.setText(CommonUtil.formatCurrency(taxAmount));
		mServiceChargeAmountText.setText(CommonUtil.formatCurrency(serviceChargeAmount));
		
		if (taxAmount == 0) {
			mTaxPanel.setVisibility(View.GONE);
		} else {
			mTaxPanel.setVisibility(View.VISIBLE);
		}
		
		if (serviceChargeAmount == 0) {
			mServiceChargePanel.setVisibility(View.GONE);
		} else {
			mServiceChargePanel.setVisibility(View.VISIBLE);
		}

		mBills.clear();

		List<Bills> bills = mBillsDaoService.getBills(mCashFlowMonth.getMonth(), 0);

		mBills.addAll(bills);

		mAdapter.notifyDataSetChanged();

		Integer billsTotalAmount = getBillsTotalAmount(bills);

		mTotalBillsText.setText(CommonUtil.formatCurrency(billsTotalAmount));

		Long grandTotal = totalAmount - taxAmount - serviceChargeAmount - billsTotalAmount;

		mTotalText.setText(CommonUtil.formatCurrency(grandTotal));

		if (grandTotal < 0) {
			mTotalText.setTextColor(getActivity().getResources().getColor(R.color.text_red));
		} else {
			mTotalText.setTextColor(getActivity().getResources().getColor(R.color.text_blue));
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
	
	@Override
	public void onBillSelected(Bills bill) {
		
		mActionListener.onBillSelected(bill);
	}
}