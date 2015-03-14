package com.android.pos.popup.search;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Bills;
import com.android.pos.dao.BillsDaoService;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class BillDlgFragment extends DialogFragment implements BillArrayAdapter.ItemActionListener {
	
	TextView mCancelBtn;
	EditText mBillsSearchText;
	ListView mBillsListView;
	TextView mNoBillsText;
	
	BillSelectionListener mActionListener;
	
	BillArrayAdapter billArrayAdapter;
	
	List<Bills> mBills;

	boolean mIsMandatory = false;
	
	private BillsDaoService mBillsDaoService = new BillsDaoService();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        mBills = new ArrayList<Bills>();
        
        billArrayAdapter = new BillArrayAdapter(getActivity(), mBills, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.popup_bill_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mBillsSearchText = (EditText) getView().findViewById(R.id.billSearchText);
		mBillsSearchText.setText(Constant.EMPTY_STRING);
		
		mBillsSearchText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				mBills.clear();
				mBills.addAll(mBillsDaoService.getProductPurchaseBills(s.toString(), 0));
				billArrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		mBillsListView = (ListView) getView().findViewById(R.id.billListView);
		mBillsListView.setAdapter(billArrayAdapter);
		
		mNoBillsText = (TextView) getView().findViewById(R.id.noBillText);
		mNoBillsText.setOnClickListener(getNoBillsTextOnClickListener());
		
		mCancelBtn = (TextView) getView().findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());
		
		if (mIsMandatory) {
			mNoBillsText.setVisibility(View.GONE);
		} else {
			mNoBillsText.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (BillSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BillSelectionListener");
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
	public void onBillsSelected(Bills bill) {
		
		dismiss();
		mActionListener.onBillSelected(bill);
	}
	
	private View.OnClickListener getNoBillsTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onBillSelected(null);
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
}
