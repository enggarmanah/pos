package com.android.pos.cashier;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.dao.Customer;
import com.android.pos.dao.CustomerDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
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

public class CashierCustomerDlgFragment extends DialogFragment implements CashierCustomerArrayAdapter.ItemActionListener {
	
	EditText mCustomerSearchText;
	
	ListView mCustomerListView;
	
	TextView mNoCustomerText;
	
	CashierActionListener mActionListener;
	
	CashierCustomerArrayAdapter customerArrayAdapter;
	
	List<Customer> mCustomers;
	
	private CustomerDao mCustomerDao = DbHelper.getSession().getCustomerDao();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        mCustomers = new ArrayList<Customer>();
        
        customerArrayAdapter = new CashierCustomerArrayAdapter(getActivity(), mCustomers, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_customer_fragment, container, false);

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
				// TODO Auto-generated method stub
				
				System.out.println("Search Customer : " + s.toString());
				
				mCustomers.clear();
				mCustomers.addAll(getCustomers(s.toString()));
				customerArrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		mCustomerListView = (ListView) getView().findViewById(R.id.customerListView);
		mCustomerListView.setAdapter(customerArrayAdapter);
		
		mNoCustomerText = (TextView) getView().findViewById(R.id.noCustomerText);
		mNoCustomerText.setOnClickListener(getNoCustomerTextOnClickListener());
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (CashierActionListener) activity;
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
	
	private List<Customer> getCustomers(String name) {

		QueryBuilder<Customer> qb = mCustomerDao.queryBuilder();
		qb.where(CustomerDao.Properties.Name.like("%" + name + "%")).orderAsc(CustomerDao.Properties.Name);


		Query<Customer> q = qb.build();
		List<Customer> list = q.list();
		
		return list;
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
}
