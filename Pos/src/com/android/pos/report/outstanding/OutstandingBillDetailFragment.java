package com.android.pos.report.outstanding;

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
import com.android.pos.dao.Bills;
import com.android.pos.dao.Inventory;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.util.CommonUtil;

public class OutstandingBillDetailFragment extends BaseFragment {
	
	private ImageButton mBackButton;
	
	private TextView mReferenceNoText;
	private TextView mDateText;
	
	private TextView mSupplierText;
	private TextView mDueDateText;
	private TextView mTotalAmountText;
	private TextView mPaymentAmountText;
	private TextView mOutstandingAmountText;
	private TextView mRemarksText;
	
	private TextView mListTitleText;
	
	private ListView mInventoryListView;

	private Bills mBill;
	private List<Inventory> mInventories;
	
	private OutstandingBillActionListener mActionListener;
	
	private OutstandingBillDetailArrayAdapter mAdapter;
	
	private InventoryDaoService mInventoryDaoService = new InventoryDaoService();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_pastdue_detail_fragment, container, false);
		
		if (mInventories == null) {
			mInventories = new ArrayList<Inventory>();
		} 
		
		mAdapter = new OutstandingBillDetailArrayAdapter(getActivity(), mInventories);
		
		initViewVariables(view);
		
		return view;
	}
	
	private void initViewVariables(View view) {
		
		mInventoryListView = (ListView) view.findViewById(R.id.inventoryList);

		mInventoryListView.setAdapter(mAdapter);
		mInventoryListView.setItemsCanFocus(true);
		mInventoryListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mBackButton = (ImageButton) view.findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		boolean isMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (isMultiplesPane) {
			mBackButton.setVisibility(View.GONE);
		} else {
			mBackButton.setVisibility(View.VISIBLE);
		}
		
		mReferenceNoText = (TextView) view.findViewById(R.id.referenceNoText);
		mDateText = (TextView) view.findViewById(R.id.dateText);
		
		mSupplierText = (TextView) view.findViewById(R.id.supplierText);
		mDueDateText = (TextView) view.findViewById(R.id.dueDateText);
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
            mActionListener = (OutstandingBillActionListener) activity;
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

		Float totalAmount = mBill.getBillAmount();
		Float paymentAmount = mBill.getPayment();
		Float outstandingAmount = totalAmount - paymentAmount;
		
		mSupplierText.setText(mBill.getSupplierName());
		mDueDateText.setText(CommonUtil.formatDate(mBill.getBillDueDate()));
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