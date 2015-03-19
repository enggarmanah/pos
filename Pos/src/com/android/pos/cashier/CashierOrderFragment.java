package com.android.pos.cashier;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.common.ConfirmListener;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.Orders;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.TransactionItem;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.ConfirmationUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;

public class CashierOrderFragment extends BaseFragment implements CashierOrderArrayAdapter.ItemActionListener {
	
	private TextView mHeaderText;
	
	private TextView mDiscountLabelText;
	private TextView mDiscountText;
	private TextView mTaxText;
	private TextView mServiceChargeText;
	private TextView mTotalBillText;
	
	private TextView mTaxBorder;
	private TextView mServiceChargeBorder;
	
	private LinearLayout mDiscountPanel;
	private LinearLayout mTaxPanel;
	private LinearLayout mServiceChargePanel;
	
	private TextView mPaymentText;
	private TextView mOrderNewItemText;
	private TextView mOrderText;
	private TextView mCancelText;
	
	private FrameLayout mPaymentPanel;
	private FrameLayout mOrderNewItemPanel;
	private FrameLayout mOrderPanel;
	
	private TextView mOrderDivider;
	
	protected ListView mTransactionItemList;

	protected List<TransactionItem> mTransactionItems;
	protected ProductGroup mSelectedTransactionItem;
	
	private CashierActionListener mActionListener;
	
	private CashierOrderArrayAdapter mAdapter;
	
	private Discount mDiscount;
	private int mTotalItem = 0;
	
	private String mCashierState = Constant.CASHIER_STATE_CASHIER;
	
	private Orders mSelectedOrder;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_order_fragment, container, false);
		
		mAdapter = new CashierOrderArrayAdapter(getActivity(), mTransactionItems, this);
		
		initViewVariables(view);
		
		return view;
	}
	
	private void initViewVariables(View view) {
		
		mHeaderText = (TextView) view.findViewById(R.id.headerText);
		mHeaderText.setText("Daftar Penjualan");
		
		mTransactionItemList = (ListView) view.findViewById(R.id.transactionItemList);

		mTransactionItemList.setAdapter(mAdapter);
		mTransactionItemList.setItemsCanFocus(true);
		mTransactionItemList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mDiscountPanel = (LinearLayout) view.findViewById(R.id.discountPanel);
		mDiscountPanel.setOnClickListener(getDiscountPanelOnClickListener());
		
		mDiscountLabelText = (TextView) view.findViewById(R.id.discountLabelText);
		mDiscountLabelText.setText("Tanpa Diskon");
		
		mDiscountText = (TextView) view.findViewById(R.id.discountText);
		mDiscountText.setText(Constant.EMPTY_STRING);
		
		mTaxText = (TextView) view.findViewById(R.id.taxText);
		mTaxText.setText(CommonUtil.formatNumber(0));
		
		mServiceChargeText = (TextView) view.findViewById(R.id.serviceChargeText);
		mServiceChargeText.setText(CommonUtil.formatNumber(0));
		
		mTotalBillText = (TextView) view.findViewById(R.id.totalOrderText);
		mTotalBillText.setText(CommonUtil.formatCurrency(0));
		
		mPaymentText = (TextView) view.findViewById(R.id.paymentText);
		mPaymentText.setOnClickListener(getPaymentBtnOnClickListener());
		
		mOrderNewItemText = (TextView) view.findViewById(R.id.orderNewItemText);
		mOrderNewItemText.setOnClickListener(getOrderNewItemBtnOnClickListener());
		
		mOrderText = (TextView) view.findViewById(R.id.orderText);
		mOrderText.setOnClickListener(getOrderBtnOnClickListener());
		
		mCancelText = (TextView) view.findViewById(R.id.cancelText);
		mCancelText.setOnClickListener(getCancelBtnOnClickListener());
		
		mTaxPanel = (LinearLayout) view.findViewById(R.id.taxPanel);
		mServiceChargePanel = (LinearLayout) view.findViewById(R.id.serviceChargePanel);
		
		mTaxBorder = (TextView) view.findViewById(R.id.taxBorder);
		mServiceChargeBorder = (TextView) view.findViewById(R.id.serviceChargeBorder);
		
		mPaymentPanel = (FrameLayout) view.findViewById(R.id.paymentPanel);
		mOrderNewItemPanel = (FrameLayout) view.findViewById(R.id.orderNewItemPanel);
		mOrderPanel = (FrameLayout) view.findViewById(R.id.orderPanel);
		mOrderDivider = (TextView) view.findViewById(R.id.orderDivider);
	}
	
	public void onStart() {
		super.onStart();
		
		updatePayableInfo();
		initHeader();
		initButtons();
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
		
		mActionListener.onProductSelected(transactionItem.getProduct(), transactionItem.getQuantity(), transactionItem.getRemarks());
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
					transItem.setRemarks(item.getRemarks());
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
	
	public void addTransactionItem(TransactionItem item, boolean isMergeQuantity) {
		
		boolean isExist = false;
		
		for (TransactionItem transItem : mTransactionItems) {
			
			if (transItem.getProductId() == item.getProductId()) {
				if (item.getQuantity() == 0) {
					mTransactionItems.remove(transItem);
				} else {
					if (isMergeQuantity) {
						transItem.setQuantity(transItem.getQuantity() + item.getQuantity());
					} else {
						transItem.setQuantity(item.getQuantity());
					}
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
		
		if (!isViewInitialized()) {
			return;
		}
		
		int totalBill = 0;
		mTotalItem = 0;
		
		for (TransactionItem transItem : mTransactionItems) {
			totalBill += transItem.getQuantity() * transItem.getPrice();
			mTotalItem += transItem.getQuantity();
		}
		
		int discount = 0;
		
		if (mDiscount != null) {
			
			String label = mDiscount.getName();
			
			if (mDiscount.getPercentage() != 0) {
				label = label + " " + CommonUtil.intToStr(mDiscount.getPercentage()) + "%";
			}
			
			mDiscountLabelText.setText(label);
			
			if (mDiscount.getPercentage() != 0) {
				discount = mDiscount.getPercentage() * totalBill / 100;
			} else {
				discount = mDiscount.getAmount();
			}
			
			mDiscountText.setText("- " + CommonUtil.formatCurrency(discount));
			
		} else {
			mDiscountLabelText.setText("Tanpa Diskon");
			mDiscountText.setText(Constant.EMPTY_STRING);
		}
		
		totalBill = totalBill - discount;
		
		Merchant merchant = MerchantUtil.getMerchant();
		
		int taxPercentage = CommonUtil.getNvl(merchant.getTaxPercentage());
		int serviceChargePercentage = CommonUtil.getNvl(merchant.getServiceChargePercentage());
		
		int tax = (int) Math.round(totalBill * taxPercentage / 100);
		int serviceCharge = (int) Math.round(totalBill * serviceChargePercentage / 100);
		
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
		
		int totalPayable = totalBill + tax + serviceCharge;
		
		mTotalBillText.setText(CommonUtil.formatCurrency(totalPayable));
		
		mAdapter.notifyDataSetChanged();
	}
	
	private void initHeader() {
		
		String header = null;
		
		if (mSelectedOrder != null) {
			
			if (Constant.ORDER_TYPE_DINE_IN.equals(mSelectedOrder.getOrderType())) {
				
				header = "Tambahan No. Meja : " + mSelectedOrder.getOrderReference();
			
			} else {
				
				header = "Tambahan Pelanggan : " + mSelectedOrder.getOrderReference();
			}
			
		} else {
			
			header = "Daftar Penjualan";
		}
		
		mHeaderText.setText(header);
	}
	
	private void initButtons() {
		
		if (Constant.MERCHANT_TYPE_RESTO.equals(MerchantUtil.getMerchant().getType()) ||
			Constant.MERCHANT_TYPE_BEAUTY_N_SPA.equals(MerchantUtil.getMerchant().getType())) {
			
			hidePaymentButton();
			hideOrderNewItemButton();
			hideOrderButton();
			
			if (Constant.CASHIER_STATE_CASHIER.equals(mCashierState)) {
				
				showPaymentButton();
				showOrderButton();
			
			} else if (Constant.CASHIER_STATE_ORDER_PAYMENT.equals(mCashierState)) {
				
				showPaymentButton();
				
			} else if (Constant.CASHIER_STATE_ORDER_NEW_ITEM.equals(mCashierState)) {
				
				showOrderNewItemButton();
			}
			
		} else {
			hideOrderNewItemButton();
			hideOrderButton();
		}
	}
	
	private void hidePaymentButton() {
		
		mPaymentPanel.setVisibility(View.GONE);
	}
	
	private void showPaymentButton() {
		
		mPaymentPanel.setVisibility(View.VISIBLE);
	}
	
	private void hideOrderNewItemButton() {
		
		mOrderNewItemPanel.setVisibility(View.GONE);
	}
	
	private void showOrderNewItemButton() {
		
		mOrderNewItemPanel.setVisibility(View.VISIBLE);
	}
	
	private void hideOrderButton() {
		
		mOrderPanel.setVisibility(View.GONE);
		mOrderDivider.setVisibility(View.GONE);
	}
	
	private void showOrderButton() {
		
		mOrderPanel.setVisibility(View.VISIBLE);
		mOrderDivider.setVisibility(View.VISIBLE);
	}
	
	public void setCashierState(String state) {
		
		mCashierState = state;
		
		if (isViewInitialized()) {
			initButtons();
		}
	}
	
	public void setSelectedOrders(Orders order) {
		
		mSelectedOrder = order;
		
		if (isViewInitialized()) {
			initHeader();
		}
	}
	
	private View.OnClickListener getDiscountPanelOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onSelectDiscount();
			}
		};
	}
	
	private Integer getTotalQuantity(List<TransactionItem> transactionItems) {
		
		Integer total = 0;
		
		for (TransactionItem transactionItem : transactionItems) {
			total = total + transactionItem.getQuantity();
		}
		
		return total;
	}
	
	private View.OnClickListener getPaymentBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mTransactionItems != null && getTotalQuantity(mTransactionItems) == 0) {
					NotificationUtil.setAlertMessage(getFragmentManager(), "Daftar item penjualan masih kosong");
				} else {
					mActionListener.onPaymentRequested(CommonUtil.parseCurrency(mTotalBillText.getText().toString()));
				}
			}
		};
	}
	
	private View.OnClickListener getOrderNewItemBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mTransactionItems != null && mTransactionItems.size() == 0) {
					NotificationUtil.setAlertMessage(getFragmentManager(), "Daftar item pesanan masih kosong");
				} else {
					mActionListener.onOrderInfoProvided(mSelectedOrder.getOrderReference(), mSelectedOrder.getOrderType());
				}
			}
		};
	}
	
	private View.OnClickListener getOrderBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mTransactionItems != null && mTransactionItems.size() == 0) {
					NotificationUtil.setAlertMessage(getFragmentManager(), "Daftar item pesanan masih kosong");
				} else {
					mActionListener.onOrderRequested(mTotalItem);
				}
			}
		};
	}
	
	private View.OnClickListener getCancelBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ConfirmationUtil.confirmTask(getFragmentManager(), (ConfirmListener) getActivity(), ConfirmationUtil.CANCEL_TRANSACTION, "Batalkan transaksi ?");
			}
		};
	}
}