package com.android.pos.transaction;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pos.CodeUtil;
import com.android.pos.CommonUtil;
import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.TransactionItemDao;
import com.android.pos.dao.Transactions;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class TransactionDetailFragment extends BaseFragment implements TransactionDetailArrayAdapter.ItemActionListener {
	
	private LinearLayout mCustomerPanel;
	
	private TextView mDateText;
	private TextView mTransactionNoText;	
	private TextView mCashierText;
	private TextView mCustomerText;
	
	private TextView mDiscountLabelText;
	private TextView mDiscountText;
	private TextView mTaxText;
	private TextView mServiceChargeText;
	private TextView mTotalOrderText;
	private TextView mPaymentTypeText;
	
	protected ListView mTransactionItemList;

	protected Transactions mTransaction;
	protected List<TransactionItem> mTransactionItems;
	
	//private TransactionActionListener mActionListener;
	
	private TransactionDetailArrayAdapter mAdapter;
	
	private TransactionItemDao transactionItemDao = DbHelper.getSession().getTransactionItemDao();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.transaction_detail_fragment, container, false);
		
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
	}
	
	public void onStart() {
		super.onStart();
		
		initViewVariables();
		updateContent();
	}
	
	/*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (TransactionActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CashierActionListener");
        }
    }*/
	
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
			mCustomerPanel.setVisibility(View.INVISIBLE);
		}
		
		int totalOrder = 0;
		
		for (TransactionItem transItem : mTransactionItems) {
			totalOrder += transItem.getQuantity() * transItem.getPrice();
		}
		
		int discount = 0;
		
		if (!CommonUtil.isEmpty(mTransaction.getDiscountName())) {
			String label = mTransaction.getDiscountName() + " " + CommonUtil.intToStr(mTransaction.getDiscountPercentage()) + "%";
			mDiscountLabelText.setText(label);
			discount = mTransaction.getDiscountPercentage() * totalOrder / 100;
			mDiscountText.setText(CommonUtil.formatCurrency(discount));
			
		} else {
			mDiscountLabelText.setText("Tanpa Diskon");
			mDiscountText.setText(Constant.EMPTY_STRING);
		}
		
		totalOrder = totalOrder - discount;
		
		int tax = (int) Math.round(0.1 * totalOrder);
		int serviceCharge = (int) Math.round(0.05 * totalOrder);
		
		mTaxText.setText(CommonUtil.formatCurrency(tax));
		mServiceChargeText.setText(CommonUtil.formatCurrency(serviceCharge));
		
		int totalPayable = totalOrder + tax + serviceCharge;
		
		mPaymentTypeText.setText(CodeUtil.getPaymentTypeLabel(mTransaction.getPaymentType()));
		mTotalOrderText.setText(CommonUtil.formatCurrency(totalPayable));
		
		mAdapter.notifyDataSetChanged();
	}
	
	private List<TransactionItem> getTransactionItems(Transactions transaction) {

		QueryBuilder<TransactionItem> qb = transactionItemDao.queryBuilder();
		qb.where(TransactionItemDao.Properties.TransactionId.eq(transaction.getId())).orderAsc(TransactionItemDao.Properties.Id);

		Query<TransactionItem> q = qb.build();
		List<TransactionItem> list = q.list();
		
		return list;
	}
}