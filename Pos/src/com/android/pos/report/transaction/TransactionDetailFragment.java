package com.android.pos.report.transaction;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.TransactionItemDao;
import com.android.pos.dao.Transactions;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class TransactionDetailFragment extends BaseFragment implements TransactionDetailArrayAdapter.ItemActionListener {
	
	private LinearLayout mCustomerPanel;
	
	private ImageButton mBackButton;
	
	private TextView mDateText;
	private TextView mTransactionNoText;	
	private TextView mCashierText;
	private TextView mCustomerText;
	
	private TextView mTaxBorder;
	private TextView mServiceChargeBorder;
	
	private LinearLayout mTaxPanel;
	private LinearLayout mServiceChargePanel;
	
	private TextView mDiscountLabelText;
	private TextView mDiscountText;
	private TextView mTaxText;
	private TextView mServiceChargeText;
	private TextView mTotalOrderText;
	private TextView mPaymentTypeText;
	
	protected ListView mTransactionItemList;

	protected Transactions mTransaction;
	protected List<TransactionItem> mTransactionItems;
	
	private TransactionActionListener mActionListener;
	
	private TransactionDetailArrayAdapter mAdapter;
	
	private TransactionItemDao transactionItemDao = DbUtil.getSession().getTransactionItemDao();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_transaction_detail_fragment, container, false);
		
		if (mTransactionItems == null) {
			mTransactionItems = new ArrayList<TransactionItem>();
		} 
		
		mAdapter = new TransactionDetailArrayAdapter(getActivity(), mTransactionItems, this);
		
		return view;
	}
	
	private void initViewVariables() {
		
		mTransactionItemList = (ListView) getView().findViewById(R.id.transactionItemList);

		mTransactionItemList.setAdapter(mAdapter);
		mTransactionItemList.setItemsCanFocus(true);
		mTransactionItemList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mCustomerPanel = (LinearLayout) getView().findViewById(R.id.customerPanel);
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		boolean isMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (isMultiplesPane) {
			mBackButton.setVisibility(View.GONE);
		} else {
			mBackButton.setVisibility(View.VISIBLE);
		}
		
		mDateText = (TextView) getView().findViewById(R.id.dateText);
		mTransactionNoText = (TextView) getView().findViewById(R.id.transactionNoText);	
		mCashierText = (TextView) getView().findViewById(R.id.cashierText);
		mCustomerText = (TextView) getView().findViewById(R.id.customerText);	
		
		mDiscountLabelText = (TextView) getView().findViewById(R.id.discountLabelText);
		mDiscountLabelText.setText("Tanpa Diskon");
		
		mDiscountText = (TextView) getView().findViewById(R.id.discountText);
		mDiscountText.setText(Constant.EMPTY_STRING);
		
		mTaxText = (TextView) getView().findViewById(R.id.taxText);
		mTaxText.setText(CommonUtil.formatCurrencyUnsigned(0));
		
		mServiceChargeText = (TextView) getView().findViewById(R.id.serviceChargeText);
		mServiceChargeText.setText(CommonUtil.formatCurrencyUnsigned(0));
		
		mPaymentTypeText = (TextView) getView().findViewById(R.id.paymentTypeText);
		
		mTotalOrderText = (TextView) getView().findViewById(R.id.totalOrderText);
		mTotalOrderText.setText(CommonUtil.formatCurrency(0));
		
		mTaxPanel = (LinearLayout) getView().findViewById(R.id.taxPanel);
		mServiceChargePanel = (LinearLayout) getView().findViewById(R.id.serviceChargePanel);
		
		mTaxBorder = (TextView) getView().findViewById(R.id.taxBorder);
		mServiceChargeBorder = (TextView) getView().findViewById(R.id.serviceChargeBorder);
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
            mActionListener = (TransactionActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TransactionActionListener");
        }
    }
	
	@Override
	public void onItemSelected(TransactionItem transactionItem) {
	}
	
	public void setTransaction(Transactions transaction) {
		
		mTransaction = transaction;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	private void updateContent() {
		
		if (mTransaction != null) {
			
			mTransactionItems.clear();
			mTransactionItems.addAll(getTransactionItems(mTransaction));
			mAdapter.notifyDataSetChanged();
			
			getView().setVisibility(View.VISIBLE);
		
		} else {
		
			getView().setVisibility(View.INVISIBLE);
			return;
		}
		
		mDateText.setText(CommonUtil.formatDayDateTime(mTransaction.getTransactionDate()));
		mTransactionNoText.setText(mTransaction.getTransactionNo());
		mCashierText.setText(mTransaction.getCashierName());
		
		if (!CommonUtil.isEmpty(mTransaction.getCustomerName())) {
			mCustomerText.setText(mTransaction.getCustomerName());
			mCustomerPanel.setVisibility(View.VISIBLE);
		} else {
			mCustomerPanel.setVisibility(View.GONE);
		}
		
		if (!CommonUtil.isEmpty(mTransaction.getDiscountName())) {
			String label = mTransaction.getDiscountName() + " " + CommonUtil.intToStr(mTransaction.getDiscountPercentage()) + "%";
			mDiscountLabelText.setText(label);
			mDiscountText.setText(CommonUtil.formatCurrency(mTransaction.getDiscountAmount()));
			
		} else {
			mDiscountLabelText.setText("Tanpa Diskon");
			mDiscountText.setText(Constant.EMPTY_STRING);
		}
		
		int tax = mTransaction.getTaxAmount();
		int serviceCharge = mTransaction.getServiceChargeAmount();
		
		mTaxText.setText(CommonUtil.formatCurrency(tax));
		mServiceChargeText.setText(CommonUtil.formatCurrency(serviceCharge));
		
		mTaxBorder.setVisibility(View.VISIBLE);
		mTaxPanel.setVisibility(View.VISIBLE);
		
		if (tax == 0) {
			mTaxBorder.setVisibility(View.GONE);
			mTaxPanel.setVisibility(View.GONE);
		}
		
		mServiceChargeBorder.setVisibility(View.VISIBLE);
		mServiceChargePanel.setVisibility(View.VISIBLE);
		
		if (serviceCharge == 0) {
			mServiceChargeBorder.setVisibility(View.GONE);
			mServiceChargePanel.setVisibility(View.GONE);
		}
		
		mPaymentTypeText.setText(CodeUtil.getPaymentTypeLabel(mTransaction.getPaymentType()));
		mTotalOrderText.setText(CommonUtil.formatCurrency(mTransaction.getTotalAmount()));
		
		mAdapter.notifyDataSetChanged();
	}
	
	private List<TransactionItem> getTransactionItems(Transactions transaction) {

		QueryBuilder<TransactionItem> qb = transactionItemDao.queryBuilder();
		qb.where(TransactionItemDao.Properties.TransactionId.eq(transaction.getId())).orderAsc(TransactionItemDao.Properties.Id);

		Query<TransactionItem> q = qb.build();
		List<TransactionItem> list = q.list();
		
		return list;
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