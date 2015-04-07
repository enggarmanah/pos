package com.android.pos.favorite.customer;

import java.util.ArrayList;
import java.util.Date;
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
import com.android.pos.dao.Customer;
import com.android.pos.dao.CustomerDaoService;
import com.android.pos.dao.ProductGroupDaoService;
import com.android.pos.model.ProductGroupStatisticBean;
import com.android.pos.util.CommonUtil;

public class CustomerDetailFragment extends BaseFragment {
	
	private ImageButton mBackButton;
	private ImageButton mTransactionsButton;
	
	private TextView mCustomerNameText;
	
	private TextView mFirstTransactionText;
	private TextView mLastTransactionText;
	private TextView mAverageTransactionText;
	
	private TextView mTransactionCountText;
	private TextView mProductCountText;
	private TextView mTotalAmountText;
	
	private TextView mMinTransactionText;
	private TextView mMaxTransactionText;
	private TextView mAverageAmountText;
	
	protected ListView mProductGroupStatisticList;

	protected Customer mCustomer;
	protected List<ProductGroupStatisticBean> mProductGroupStatistics;
	
	private CustomerActionListener mActionListener;
	
	private CustomerDetailArrayAdapter mAdapter;
	
	private ProductGroupDaoService mProductGroupDaoService = new ProductGroupDaoService();
	private CustomerDaoService mCustomerDaoService = new CustomerDaoService();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.favorite_customer_detail_fragment, container, false);
		
		if (mProductGroupStatistics == null) {
			mProductGroupStatistics = new ArrayList<ProductGroupStatisticBean>();
		}
		
		mCustomerNameText = (TextView) view.findViewById(R.id.customerNameText);
		
		mFirstTransactionText = (TextView) view.findViewById(R.id.firstTransactionText);
		mLastTransactionText = (TextView) view.findViewById(R.id.lastTransactionText);
		mAverageTransactionText = (TextView) view.findViewById(R.id.averageTransactionText);
		
		mTransactionCountText = (TextView) view.findViewById(R.id.transactionCountText);
		mProductCountText = (TextView) view.findViewById(R.id.productCountText);
		mTotalAmountText = (TextView) view.findViewById(R.id.totalAmountText);
		
		mMinTransactionText = (TextView) view.findViewById(R.id.minTransactionText);
		mMaxTransactionText = (TextView) view.findViewById(R.id.maxTransactionText);
		mAverageAmountText = (TextView) view.findViewById(R.id.averageAmountText);
		
		mAdapter = new CustomerDetailArrayAdapter(getActivity(), mProductGroupStatistics);
		
		return view;
	}
	
	private void initViewVariables() {
		
		mProductGroupStatisticList = (ListView) getView().findViewById(R.id.productStatisticList);

		mProductGroupStatisticList.setAdapter(mAdapter);
		mProductGroupStatisticList.setItemsCanFocus(true);
		mProductGroupStatisticList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		mTransactionsButton = (ImageButton) getView().findViewById(R.id.transactionsButton);
		mTransactionsButton.setOnClickListener(getTransactionsButtonOnClickListener());
		
		boolean isMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (isMultiplesPane) {
			mBackButton.setVisibility(View.GONE);
		} else {
			mBackButton.setVisibility(View.VISIBLE);
		}
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
            mActionListener = (CustomerActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CustomerActionListener");
        }
    }
	
	public void setCustomer(Customer customer) {
		
		mCustomer = customer;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}
		
		if (mCustomer != null) {
			
			mCustomerNameText.setText(mCustomer.getName());
			
			Date firstTransaction = mCustomerDaoService.getFirstTransaction(mCustomer);
			Date lastTransaction = mCustomerDaoService.getLastTransaction(mCustomer);
			
			Long transactionsCount = mCustomerDaoService.getTransactionsCount(mCustomer);
			Long productsCount = mCustomerDaoService.getProductsCount(mCustomer);
			Long transactionAmount = mCustomerDaoService.getTransactionsAmount(mCustomer);
			
			Long minTransaction = mCustomerDaoService.getMinTransactions(mCustomer);
			Long maxTransaction = mCustomerDaoService.getMaxTransactions(mCustomer);
			Long averageAmount = transactionAmount / transactionsCount;
			
			long amonth = 60 * 60 * 24 * 30;
			
			Long transactionPeriod = (new Date().getTime() - firstTransaction.getTime()) / 1000;
			Long averageTransactionPeriod = transactionPeriod / transactionsCount;
			Long month = averageTransactionPeriod / amonth;
			Long day = averageTransactionPeriod % amonth / 30;
			
			String averageTransaction = month + "," + String.valueOf(day).substring(0, 1) + " Bulan";
			
			mFirstTransactionText.setText(CommonUtil.formatDate(firstTransaction));
			mLastTransactionText.setText(CommonUtil.formatDate(lastTransaction));
			mAverageTransactionText.setText(averageTransaction);
			
			mTransactionCountText.setText(CommonUtil.formatNumber(transactionsCount));
			mProductCountText.setText(CommonUtil.formatNumber(productsCount));
			mTotalAmountText.setText(CommonUtil.formatCurrency(transactionAmount));
			
			mMinTransactionText.setText(CommonUtil.formatCurrency(minTransaction));
			mMaxTransactionText.setText(CommonUtil.formatCurrency(maxTransaction));
			mAverageAmountText.setText(CommonUtil.formatCurrency(averageAmount));
			
			mProductGroupStatistics.clear();
			
			mProductGroupStatistics.addAll(mProductGroupDaoService.getProductGroupStatistics(mCustomer));
			
			mAdapter.notifyDataSetChanged();
			
			getView().setVisibility(View.VISIBLE);
		
		} else {
		
			getView().setVisibility(View.INVISIBLE);
			return;
		}
	}
	
	private View.OnClickListener getBackButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onBackPressed();
			}
		};
	}
	
	private View.OnClickListener getTransactionsButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.showCustomerTransactions(mCustomer);
			}
		};
	}
}