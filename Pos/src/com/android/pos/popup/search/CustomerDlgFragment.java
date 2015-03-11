package com.android.pos.popup.search;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Customer;
import com.android.pos.dao.CustomerDaoService;

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

public class CustomerDlgFragment extends DialogFragment implements CustomerArrayAdapter.ItemActionListener {
	
	TextView mCancelBtn;
	EditText mCustomerSearchText;
	ListView mCustomerListView;
	TextView mNoCustomerText;
	
	CustomerSelectionListener mActionListener;
	
	CustomerArrayAdapter customerArrayAdapter;
	
	List<Customer> mCustomers;
	
	private CustomerDaoService mCustomerDaoService = new CustomerDaoService();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        mCustomers = new ArrayList<Customer>();
        
        customerArrayAdapter = new CustomerArrayAdapter(getActivity(), mCustomers, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.popup_customer_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mCustomerSearchText = (EditText) getView().findViewById(R.id.customerSearchText);
		mCustomerSearchText.setText(Constant.EMPTY_STRING);
		
		mCustomerSearchText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				mCustomers.clear();
				mCustomers.addAll(mCustomerDaoService.getCustomers(s.toString(), 0));
				customerArrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		mCustomerListView = (ListView) getView().findViewById(R.id.customerListView);
		mCustomerListView.setAdapter(customerArrayAdapter);
		
		mNoCustomerText = (TextView) getView().findViewById(R.id.noCustomerText);
		mNoCustomerText.setOnClickListener(getNoCustomerTextOnClickListener());
		
		mCancelBtn = (TextView) getView().findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (CustomerSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CashierActionListener");
        }
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onCustomerSelected(Customer customer) {
		
		dismiss();
		mActionListener.onCustomerSelected(customer);
	}
	
	private View.OnClickListener getNoCustomerTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onCustomerSelected(null);
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
