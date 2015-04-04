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
import com.android.pos.dao.Inventory;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.util.CommonUtil;

public class CashFlowBillDetailFragment extends BaseFragment {
	
	private ImageButton mBackButton;
	
	private LinearLayout mSupplierAndDatePanel;
	private LinearLayout mSupplierPanel;
	private LinearLayout mDueDatePanel;
	private LinearLayout mPaymentDatePanel;
	
	private TextView mReferenceNoText;
	private TextView mDateText;
	
	private TextView mSupplierText;
	private TextView mDueDateText;
	private TextView mPaymentDateText;
	
	private TextView mTotalAmountText;
	private TextView mPaymentAmountText;
	private TextView mOutstandingAmountText;
	
	private TextView mRemarksText;
	
	private TextView mListTitleText;
	
	private ListView mInventoryListView;

	private Bills mBill;
	private List<Inventory> mInventories;
	
	private CashFlowActionListener mActionListener;
	
	private CashFlowBillDetailArrayAdapter mAdapter;
	
	private InventoryDaoService mInventoryDaoService = new InventoryDaoService();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_cashflow_bill_detail_fragment, container, false);
		
		if (mInventories == null) {
			mInventories = new ArrayList<Inventory>();
		} 
		
		mAdapter = new CashFlowBillDetailArrayAdapter(getActivity(), mInventories);
		
		initViewVariables(view);
		
		return view;
	}
	
	private void initViewVariables(View view) {
		
		mInventoryListView = (ListView) view.findViewById(R.id.inventoryList);

		mInventoryListView.setAdapter(mAdapter);
		mInventoryListView.setItemsCanFocus(true);
		mInventoryListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mSupplierAndDatePanel = (LinearLayout) view.findViewById(R.id.supplierAndDatePanel);
		mSupplierPanel = (LinearLayout) view.findViewById(R.id.supplierPanel);
		mDueDatePanel = (LinearLayout) view.findViewById(R.id.dueDatePanel);
		mPaymentDatePanel = (LinearLayout) view.findViewById(R.id.paymentDatePanel);
		
		mBackButton = (ImageButton) view.findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		mReferenceNoText = (TextView) view.findViewById(R.id.referenceNoText);
		mDateText = (TextView) view.findViewById(R.id.dateText);
		
		mSupplierText = (TextView) view.findViewById(R.id.supplierText);
		mDueDateText = (TextView) view.findViewById(R.id.dueDateText);
		mPaymentDateText = (TextView) view.findViewById(R.id.paymentDateText);
		mTotalAmountText = (TextView) view.findViewById(R.id.totalAmountText);
		mPaymentAmountText = (TextView) view.findViewById(R.id.paymentAmountText);
		mOutstandingAmountText = (TextView) view.findViewById(R.id.outstandingAmountText);
		mRemarksText = (TextView) view.findViewById(R.id.remarksText);
		
		mListTitleText = (TextView) view.findViewById(R.id.listTitleText);
	}
	
	public void onStart() {
		super.onStart();
		
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
	
	public void setBill(Bills bill) {
		
		mBill = bill;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}

		if (mBill == null) {

			getView().setVisibility(View.INVISIBLE);
			return;
		}

		mReferenceNoText.setText(mBill.getBillReferenceNo());
		mDateText.setText(CommonUtil.formatDate(mBill.getBillDate()));

		Integer totalAmount = mBill.getBillAmount();
		Integer paymentAmount = mBill.getPayment();
		Integer outstandingAmount = totalAmount - paymentAmount;
		
		mSupplierText.setText(mBill.getSupplierName());
		mDueDateText.setText(CommonUtil.formatDate(mBill.getBillDueDate()));
		mPaymentDateText.setText(CommonUtil.formatDate(mBill.getPaymentDate()));
		mTotalAmountText.setText(CommonUtil.formatCurrency(totalAmount));
		mPaymentAmountText.setText(CommonUtil.formatCurrency(paymentAmount));
		mOutstandingAmountText.setText(CommonUtil.formatCurrency(outstandingAmount));
		mRemarksText.setText(mBill.getRemarks());
		
		mInventories.clear();

		List<Inventory> inventories = mInventoryDaoService.getInventories(mBill);
		
		if (inventories.size() > 0) {
			
			mListTitleText.setVisibility(View.VISIBLE);
			mInventories.addAll(inventories);
			
		} else {
			mListTitleText.setVisibility(View.GONE);
		}
		
		mAdapter.notifyDataSetChanged();
		
		if (CommonUtil.isEmpty(mBill.getSupplierName())) {
			mSupplierPanel.setVisibility(View.GONE);
		} else {
			mSupplierPanel.setVisibility(View.VISIBLE);
		}
		
		if (mBill.getBillDueDate() == null) {
			mDueDatePanel.setVisibility(View.GONE);
		} else {
			mDueDatePanel.setVisibility(View.VISIBLE);
		}
		
		if (mBill.getPaymentDate() == null) {
			mPaymentDatePanel.setVisibility(View.GONE);
		} else {
			mPaymentDatePanel.setVisibility(View.VISIBLE);
		}
		
		if (CommonUtil.isEmpty(mBill.getSupplierName()) && mBill.getBillDueDate() == null && mBill.getPaymentDate() == null) {
			mSupplierAndDatePanel.setVisibility(View.GONE);
		} else {
			mSupplierAndDatePanel.setVisibility(View.VISIBLE);
		}
		
		getView().setVisibility(View.VISIBLE);
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