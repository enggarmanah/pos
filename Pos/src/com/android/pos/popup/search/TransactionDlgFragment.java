package com.android.pos.popup.search;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionsDaoService;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TransactionDlgFragment extends BaseSearchDlgFragment<Transactions> implements TransactionArrayAdapter.ItemActionListener {
	
	TextView mCancelBtn;
	EditText mTransactionsSearchText;
	ListView mTransactionsListView;
	TextView mNoTransactionsText;
	
	TransactionSelectionListener mActionListener;
	
	TransactionArrayAdapter transactionArrayAdapter;
	
	boolean mIsMandatory = false;
	
	private TransactionsDaoService mTransactionsDaoService = new TransactionsDaoService();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        mItems = new ArrayList<Transactions>();
        
        transactionArrayAdapter = new TransactionArrayAdapter(getActivity(), mItems, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.popup_transaction_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mTransactionsSearchText = (EditText) getView().findViewById(R.id.transactionSearchText);
		mTransactionsSearchText.setText(Constant.EMPTY_STRING);
		mTransactionsSearchText.requestFocus();
		
		mTransactionsSearchText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				mQuery = s.toString();
				
				mItems.clear();
				mItems.addAll(mTransactionsDaoService.getUnpaidTransactions(mQuery, 0));
				transactionArrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		mTransactionsListView = (ListView) getView().findViewById(R.id.transactionListView);
		mTransactionsListView.setAdapter(transactionArrayAdapter);
		
		mNoTransactionsText = (TextView) getView().findViewById(R.id.noTransactionText);
		mNoTransactionsText.setOnClickListener(getNoTransactionsTextOnClickListener());
		
		mCancelBtn = (TextView) getView().findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());
		
		if (mIsMandatory) {
			mNoTransactionsText.setVisibility(View.GONE);
		} else {
			mNoTransactionsText.setVisibility(View.VISIBLE);
		}
		
		mItems.clear();
		mItems.addAll(mTransactionsDaoService.getUnpaidTransactions(mQuery, 0));
		transactionArrayAdapter.notifyDataSetChanged();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (TransactionSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TransactionSelectionListener");
        }
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	public void setMandatory(boolean isMandatory) {
		
		mIsMandatory = isMandatory;
	}
	
	@Override
	public void onTransactionsSelected(Transactions transaction) {
		
		dismiss();
		mActionListener.onTransactionSelected(transaction);
	}
	
	private View.OnClickListener getNoTransactionsTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onTransactionSelected(null);
				dismiss();
			}
		};
	}
	
	private View.OnClickListener getCancelBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dismiss();
			}
		};
	}
	
	@Override
	protected List<Transactions> getItems(String query) {
		
		return mTransactionsDaoService.getUnpaidTransactions(mQuery, 0);
	}
	
	@Override
	protected List<Transactions> getNextItems(String query, int lastIndex) {
		
		return mTransactionsDaoService.getUnpaidTransactions(mQuery, lastIndex);
	}
	
	@Override
	protected void refreshList() {
		
		transactionArrayAdapter.notifyDataSetChanged();
	}
}
