package com.android.pos.popup.search;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Supplier;
import com.android.pos.dao.SupplierDaoService;

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

public class SupplierDlgFragment extends DialogFragment implements SupplierArrayAdapter.ItemActionListener {
	
	TextView mCancelBtn;
	EditText mSupplierSearchText;
	ListView mSupplierListView;
	TextView mNoSupplierText;
	
	SupplierSelectionListener mActionListener;
	
	SupplierArrayAdapter supplierArrayAdapter;
	
	List<Supplier> mSuppliers;

	boolean mIsMandatory = false;
	
	private SupplierDaoService mSupplierDaoService = new SupplierDaoService();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        mSuppliers = new ArrayList<Supplier>();
        
        supplierArrayAdapter = new SupplierArrayAdapter(getActivity(), mSuppliers, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.popup_supplier_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mSupplierSearchText = (EditText) getView().findViewById(R.id.supplierSearchText);
		mSupplierSearchText.setText(Constant.EMPTY_STRING);
		
		mSupplierSearchText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				mSuppliers.clear();
				mSuppliers.addAll(mSupplierDaoService.getSuppliers(s.toString(), 0));
				supplierArrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		mSupplierListView = (ListView) getView().findViewById(R.id.supplierListView);
		mSupplierListView.setAdapter(supplierArrayAdapter);
		
		mNoSupplierText = (TextView) getView().findViewById(R.id.noSupplierText);
		mNoSupplierText.setOnClickListener(getNoSupplierTextOnClickListener());
		
		mCancelBtn = (TextView) getView().findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());
		
		if (mIsMandatory) {
			mNoSupplierText.setVisibility(View.GONE);
		} else {
			mNoSupplierText.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (SupplierSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SupplierSelectionListener");
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
	public void onSupplierSelected(Supplier supplier) {
		
		dismiss();
		mActionListener.onSupplierSelected(supplier);
	}
	
	private View.OnClickListener getNoSupplierTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onSupplierSelected(null);
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
