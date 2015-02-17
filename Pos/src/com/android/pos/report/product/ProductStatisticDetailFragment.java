package com.android.pos.report.product;

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
import com.android.pos.dao.ProductStatistic;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.service.ProductDaoService;
import com.android.pos.util.CommonUtil;

public class ProductStatisticDetailFragment extends BaseFragment {
	
	private ImageButton mBackButton;
	
	private TextView mDateText;
	
	protected ListView mProductStatisticList;

	protected TransactionMonth mTransactionMonth;
	protected List<ProductStatistic> mProductStatistics;
	
	private ProductStatisticActionListener mActionListener;
	
	private ProductStatisticDetailArrayAdapter mAdapter;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_product_statistic_detail_fragment, container, false);
		
		if (mProductStatistics == null) {
			mProductStatistics = new ArrayList<ProductStatistic>();
		} 
		
		mAdapter = new ProductStatisticDetailArrayAdapter(getActivity(), mProductStatistics);
		
		return view;
	}
	
	private void initViewVariables() {
		
		mProductStatisticList = (ListView) getView().findViewById(R.id.productStatisticList);

		mProductStatisticList.setAdapter(mAdapter);
		mProductStatisticList.setItemsCanFocus(true);
		mProductStatisticList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		boolean isMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (isMultiplesPane) {
			mBackButton.setVisibility(View.GONE);
		} else {
			mBackButton.setVisibility(View.VISIBLE);
		}
		
		mDateText = (TextView) getView().findViewById(R.id.dateText);
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
            mActionListener = (ProductStatisticActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TransactionActionListener");
        }
    }
	
	public void setTransactionMonth(TransactionMonth transactionMonth) {
		
		mTransactionMonth = transactionMonth;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	private void updateContent() {
		
		if (mTransactionMonth != null) {
			
			mProductStatistics.clear();
			mProductStatistics.addAll(mProductDaoService.getProductStatistics(mTransactionMonth));
			mAdapter.notifyDataSetChanged();
			
			getView().setVisibility(View.VISIBLE);
		
		} else {
		
			getView().setVisibility(View.INVISIBLE);
			return;
		}
		
		mDateText.setText(CommonUtil.formatMonth(mTransactionMonth.getMonth()));
		
		mAdapter.notifyDataSetChanged();
	}
	
	private View.OnClickListener getBackButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onBackButtonClicked();
			}
		};
	}
}