package com.android.pos.report.pastdue;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.Bills;
import com.android.pos.dao.BillsDaoService;
import com.android.pos.util.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PastDueListFragment extends BaseFragment 
	implements PastDueArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mNavigationTitle;
	private TextView mNavText;
	
	private ListView mCashFlowList;
	
	private List<Bills> mPastDueBills;
	
	private Bills mSelectedBill;
	
	private PastDueArrayAdapter mPastDueArrayAdapter;
	
	private PastDueActionListener mActionListener;
	
	private BillsDaoService mBillsDaoService = new BillsDaoService();
	
	public PastDueListFragment() {
		
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
		
		mPastDueArrayAdapter = new PastDueArrayAdapter(getActivity(), mPastDueBills, this);
		
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
		
		displayPastDueBills();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (PastDueActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CashFlowActionListener");
        }
    }
	
	private void initList() {
		
		if (mPastDueBills == null) {
			mPastDueBills = new ArrayList<Bills>();
		}
	}
	
	public void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}
		
		displayPastDueBills();
	}
	
	public void setSelectedBill(Bills bill) {
		
		mSelectedBill = bill;
		
		if (bill == null) {
			return;
		}
		
		if (isViewInitialized()) {
			
			mPastDueArrayAdapter.notifyDataSetChanged();
		}
	}
	
	public void displayPastDueBills() {
		
		mPastDueBills.clear();
		
		List<Bills> bills = mBillsDaoService.getPastDueBills();
		
		mPastDueBills.addAll(bills);
		
		if (!isViewInitialized()) {
			return;
		}
		
		setBackButtonVisible(false);
		
		Integer totalBills = 0;
		
		for (Bills bill : bills) {
			totalBills += (bill.getBillAmount() - bill.getPayment());
		}
		
		mNavigationTitle.setText(getString(R.string.report_bill_pastdue));
		
		mNavText.setText(CommonUtil.formatCurrency(totalBills));
		
		mCashFlowList.setAdapter(mPastDueArrayAdapter);
	}
	
	@Override
	public void onBillSelected(Bills bill) {
		
		mActionListener.onBillSelected(bill);
	}
	
	@Override
	public Bills getSelectedBill() {
		
		return mSelectedBill;
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