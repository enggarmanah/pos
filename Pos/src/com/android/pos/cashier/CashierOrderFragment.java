package com.android.pos.cashier;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.TransactionItem;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;

public class CashierOrderFragment extends BaseFragment implements CashierOrderArrayAdapter.ItemActionListener {
	
	private TextView mDiscountLabelText;
	private TextView mDiscountText;
	private TextView mTaxText;
	private TextView mServiceChargeText;
	private TextView mTotalOrderText;
	
	private LinearLayout mDiscountPanel;
	private LinearLayout mTaxPanel;
	private LinearLayout mServiceChargePanel;
	
	private TextView mPaymentText;
	private TextView mCancelText;
	
	protected ListView mTransactionItemList;

	protected List<TransactionItem> mTransactionItems;
	protected ProductGroup mSelectedTransactionItem;
	
	private CashierActionListener mActionListener;
	
	private CashierOrderArrayAdapter mAdapter;
	
	private Discount mDiscount;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_order_fragment, container, false);
		
		mAdapter = new CashierOrderArrayAdapter(getActivity(), mTransactionItems, this);
		
		return view;
	}
	
	private void initViewVariables() {
		
		mTransactionItemList = (ListView) getView().findViewById(R.id.transactionItemList);

		mTransactionItemList.setAdapter(mAdapter);
		mTransactionItemList.setItemsCanFocus(true);
		mTransactionItemList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mDiscountPanel = (LinearLayout) getView().findViewById(R.id.discountPanel);
		mDiscountPanel.setOnClickListener(getDiscountPanelOnClickListener());
		
		mDiscountLabelText = (TextView) getView().findViewById(R.id.discountLabelText);
		mDiscountLabelText.setText("Tanpa Diskon");
		
		mDiscountText = (TextView) getView().findViewById(R.id.discountText);
		mDiscountText.setText(Constant.EMPTY_STRING);
		
		mTaxText = (TextView) getView().findViewById(R.id.taxText);
		mTaxText.setText(CommonUtil.formatCurrencyUnsigned(0));
		
		mServiceChargeText = (TextView) getView().findViewById(R.id.serviceChargeText);
		mServiceChargeText.setText(CommonUtil.formatCurrencyUnsigned(0));
		
		mTotalOrderText = (TextView) getView().findViewById(R.id.totalOrderText);
		mTotalOrderText.setText(CommonUtil.formatCurrency(0));
		
		mPaymentText = (TextView) getView().findViewById(R.id.paymentText);
		mPaymentText.setOnClickListener(getPaymentBtnOnClickListener());
		
		mCancelText = (TextView) getView().findViewById(R.id.cancelText);
		mCancelText.setOnClickListener(getCancelBtnOnClickListener());
		
		mTaxPanel = (LinearLayout) getView().findViewById(R.id.taxPanel);
		mServiceChargePanel = (LinearLayout) getView().findViewById(R.id.serviceChargePanel);
	}
	
	public void onStart() {
		super.onStart();
		
		initViewVariables();
		updatePayableInfo();
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
	public void onItemSelected(TransactionItem transactionItem) {
		
		mActionListener.onProductSelected(transactionItem.getProduct(), transactionItem.getQuantity());
	}
	
	public void setTransactionItems(List<TransactionItem> transactionItems) {
		
		mTransactionItems = transactionItems;
	}
	
	public void setDiscount(Discount discount) {
		
		mDiscount = discount;
		
		if (isViewInitialized()) {
			updatePayableInfo();
		}
	}
	
	public void addTransactionItem(TransactionItem item) {
		
		boolean isExist = false;
		
		for (TransactionItem transItem : mTransactionItems) {
			
			if (transItem.getProductId() == item.getProductId()) {
				if (item.getQuantity() == 0) {
					mTransactionItems.remove(transItem);
				} else {
					transItem.setQuantity(item.getQuantity());
				}
				isExist = true;
				break;
			}
		}
		
		if (!isExist) {
			mTransactionItems.add(item);
		}
		
		if (!isViewInitialized()) {
			return;
		}
		
		updatePayableInfo();
	}
	
	private void updatePayableInfo() {
		
		int totalOrder = 0;
		
		for (TransactionItem transItem : mTransactionItems) {
			totalOrder += transItem.getQuantity() * transItem.getPrice();
		}
		
		int discount = 0;
		
		if (mDiscount != null) {
			String label = mDiscount.getName() + " " + CommonUtil.intToStr(mDiscount.getPercentage()) + "%";
			mDiscountLabelText.setText(label);
			discount = mDiscount.getPercentage() * totalOrder / 100;
			mDiscountText.setText(CommonUtil.formatCurrency(discount));
			
		} else {
			mDiscountLabelText.setText("Tanpa Diskon");
			mDiscountText.setText(Constant.EMPTY_STRING);
		}
		
		totalOrder = totalOrder - discount;
		
		Merchant merchant = MerchantUtil.getMerchant();
		
		int taxPercentage = CommonUtil.getNvl(merchant.getTaxPercentage());
		int serviceChargePercentage = CommonUtil.getNvl(merchant.getServiceChargePercentage());
		
		int tax = (int) Math.round(totalOrder * taxPercentage / 100);
		int serviceCharge = (int) Math.round(totalOrder * serviceChargePercentage / 100);
		
		mTaxText.setText(CommonUtil.formatCurrency(tax));
		mServiceChargeText.setText(CommonUtil.formatCurrency(serviceCharge));
		
		mTaxPanel.setVisibility(View.VISIBLE);
		if (taxPercentage == 0) {
			mTaxPanel.setVisibility(View.GONE);
		}
		
		mServiceChargePanel.setVisibility(View.VISIBLE);
		if (serviceChargePercentage == 0) {
			mServiceChargePanel.setVisibility(View.GONE);
		}
		
		int totalPayable = totalOrder + tax + serviceCharge;
		
		mTotalOrderText.setText(CommonUtil.formatCurrency(totalPayable));
		
		mAdapter.notifyDataSetChanged();
	}
	
	private View.OnClickListener getDiscountPanelOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onSelectDiscount();
			}
		};
	}
	
	private View.OnClickListener getPaymentBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onPaymentRequested(CommonUtil.parseCurrency(mTotalOrderText.getText().toString()));
			}
		};
	}
	
	private View.OnClickListener getCancelBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onClearTransaction();
			}
		};
	}
}