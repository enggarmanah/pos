package com.android.pos.cashier;

import java.io.Serializable;
import java.util.Date;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseDialogFragment;
import com.android.pos.dao.Orders;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.UserUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CashierOrderSummaryDlgFragment extends BaseDialogFragment {

	LinearLayout mCustomerPanel;
	
	TextView mOrderReferenceLabel;

	TextView mOrderTypeText;
	TextView mOrderReferenceText;

	Button mOkBtn;
	Button mCancelBtn;
	
	String mOrderReference;
	String mOrderType;
	int mTotalItem;

	CashierActionListener mActionListener;

	private static String ORDER_REFERENCE = "ORDER_REFERENCE";
	private static String ORDER_TYPE = "ORDER_TYPE";
	private static String TOTAL_ITEM = "TOTAL_ITEM";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);

		setCancelable(false);

		if (savedInstanceState != null) {
			mOrderReference = (String) savedInstanceState.getSerializable(ORDER_REFERENCE);
			mOrderType = (String) savedInstanceState.getSerializable(ORDER_TYPE);
			mTotalItem = (Integer) savedInstanceState.getSerializable(TOTAL_ITEM);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.cashier_order_summary_dlg_fragment, container, false);

		return view;
	}

	@Override
	public void onStart() {

		super.onStart();

		mCustomerPanel = (LinearLayout) getView().findViewById(R.id.customerPanel);
		
		mOrderReferenceLabel = (TextView) getView().findViewById(R.id.orderReferenceLabel);
		mOrderReferenceText = (TextView) getView().findViewById(R.id.orderReferenceText);
		mOrderTypeText = (TextView) getView().findViewById(R.id.orderTypeText);
		mOkBtn = (Button) getView().findViewById(R.id.okBtn);
		mCancelBtn = (Button) getView().findViewById(R.id.cancelBtn);

		mOkBtn.setOnClickListener(getOkBtnOnClickListener());
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());

		refreshDisplay();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mActionListener = (CashierActionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement CashierActionListener");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		outState.putSerializable(ORDER_REFERENCE, (Serializable) mOrderReference);
		outState.putSerializable(ORDER_TYPE, (Serializable) mTotalItem);
		outState.putSerializable(TOTAL_ITEM, (Serializable) mOrderType);
	}

	private void refreshDisplay() {

		if (getView() == null) {
			return;
		}
		
		if (Constant.ORDER_TYPE_DINE_IN.equals(mOrderType)) {
			mOrderReferenceLabel.setText(getString(R.string.reservation_no));
		} else {
			mOrderReferenceLabel.setText(getString(R.string.customer));
		}

		mOrderReferenceText.setText(mOrderReference);
		mOrderTypeText.setText(CodeUtil.getOrderTypeLabel(mOrderType));
	}

	private View.OnClickListener getOkBtnOnClickListener() {

		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Orders order = getOrder();
				
				mActionListener.onOrderConfirmed(order);
				
				if (!UserUtil.isWaitress()) {
					
					mActionListener.onPrintOrder(order);
					mActionListener.onClearTransaction();
				}
					
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

	public void setOrderInfo(String orderReference, String orderType) {

		mOrderReference = orderReference;
		mOrderType = orderType;
		
		refreshDisplay();
	}

	private Orders getOrder() {

		Orders order = new Orders();
		
		order.setMerchantId(MerchantUtil.getMerchantId());
		
		order.setOrderNo(CommonUtil.getTransactionNo());
		order.setOrderDate(new Date());
		order.setOrderType(mOrderType);
		
		if (Constant.ORDER_TYPE_DINE_IN.equals(mOrderType)) {
			order.setOrderReference(mOrderReference);
		} else {
			order.setOrderReference(mOrderReference);
			order.setCustomerName(mOrderReference);
		}
		
		order.setStatus(Constant.STATUS_ACTIVE);
		
		return order;
	}
}
