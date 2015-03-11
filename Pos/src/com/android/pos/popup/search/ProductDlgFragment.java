package com.android.pos.popup.search;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDaoService;

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

public class ProductDlgFragment extends DialogFragment implements ProductArrayAdapter.ItemActionListener {
	
	TextView mCancelBtn;
	EditText mProductSearchText;
	ListView mProductListView;
	TextView mNoProductText;
	
	ProductSelectionListener mActionListener;
	
	ProductArrayAdapter productArrayAdapter;
	
	List<Product> mProducts;

	boolean mIsMandatory = false;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        mProducts = new ArrayList<Product>();
        
        productArrayAdapter = new ProductArrayAdapter(getActivity(), mProducts, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.popup_product_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mProductSearchText = (EditText) getView().findViewById(R.id.productSearchText);
		mProductSearchText.setText(Constant.EMPTY_STRING);
		
		mProductSearchText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				mProducts.clear();
				mProducts.addAll(mProductDaoService.getProducts(s.toString(), 0));
				productArrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		mProductListView = (ListView) getView().findViewById(R.id.productListView);
		mProductListView.setAdapter(productArrayAdapter);
		
		mNoProductText = (TextView) getView().findViewById(R.id.noProductText);
		mNoProductText.setOnClickListener(getNoProductTextOnClickListener());
		
		mCancelBtn = (TextView) getView().findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());
		
		if (mIsMandatory) {
			mNoProductText.setVisibility(View.GONE);
		} else {
			mNoProductText.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (ProductSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ProductSelectionListener");
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
	public void onProductSelected(Product product) {
		
		dismiss();
		mActionListener.onProductSelected(product);
	}
	
	private View.OnClickListener getNoProductTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onProductSelected(null);
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
