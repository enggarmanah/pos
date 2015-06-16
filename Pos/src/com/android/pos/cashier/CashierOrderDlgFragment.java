package com.android.pos.cashier;

import java.io.Serializable;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.NotificationUtil;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CashierOrderDlgFragment extends DialogFragment {
	
	TextView mTotalItemText;
	Spinner mOrderTypeSp;
	EditText mCustomerText;
	TextView mReservationNoText;
	
	LinearLayout mReservationNoPanel;
	LinearLayout mReservationInputPanel;
	
	Button number0Btn;
	Button number1Btn;
	Button number2Btn;
	Button number3Btn;
	Button number4Btn;
	Button number5Btn;
	Button number6Btn;
	Button number7Btn;
	Button number8Btn;
	Button number9Btn;
	
	Button actionCBtn;
	ImageButton actionXBtn;
	
	Button okBtn;
	Button cancelBtn;
	
	float mTotalItem;
	String mOrderType;
	String mCustomerName;
	int mReservationNo;
	
	CashierActionListener mActionListener;
	
	CodeSpinnerArrayAdapter orderTypeArrayAdapter;
	
	private static String TOTAL_ITEM = "TOTAL_ITEM";
	private static String ORDER_TYPE = "ORDER_TYPE";
	private static String CUSTOMER = "CUSTOMER";
	private static String RESERVATION_NO = "RESERVATION_NO";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        if (savedInstanceState != null) {
        	
			mTotalItem = (Float) savedInstanceState.getSerializable(TOTAL_ITEM);
			mOrderType = (String) savedInstanceState.getSerializable(ORDER_TYPE);
			mCustomerName = (String) savedInstanceState.getSerializable(CUSTOMER);
			mReservationNo = (Integer) savedInstanceState.getSerializable(RESERVATION_NO);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_order_dlg_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mTotalItemText = (TextView) getView().findViewById(R.id.totalItemText);
		mOrderTypeSp = (Spinner) getView().findViewById(R.id.orderTypeSp);
		mCustomerText = (EditText) getView().findViewById(R.id.customerText);
		mReservationNoText = (TextView) getView().findViewById(R.id.reservationNoText);
		
		mReservationNoPanel = (LinearLayout) getView().findViewById(R.id.reservationNoPanel);
		mReservationInputPanel = (LinearLayout) getView().findViewById(R.id.reservationInputPanel);
		
		mOrderTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
		    	mOrderType = CodeBean.getNvlCode((CodeBean) mOrderTypeSp.getSelectedItem());
		    	refreshDisplay();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		number0Btn = (Button) getView().findViewById(R.id.number0Btn);
		number1Btn = (Button) getView().findViewById(R.id.number1Btn);
		number2Btn = (Button) getView().findViewById(R.id.number2Btn);
		number3Btn = (Button) getView().findViewById(R.id.number3Btn);
		number4Btn = (Button) getView().findViewById(R.id.number4Btn);
		number5Btn = (Button) getView().findViewById(R.id.number5Btn);
		number6Btn = (Button) getView().findViewById(R.id.number6Btn);
		number7Btn = (Button) getView().findViewById(R.id.number7Btn);
		number8Btn = (Button) getView().findViewById(R.id.number8Btn);
		number9Btn = (Button) getView().findViewById(R.id.number9Btn);
		
		actionCBtn = (Button) getView().findViewById(R.id.actionCBtn);
		actionXBtn = (ImageButton) getView().findViewById(R.id.actionXBtn);
		
		okBtn = (Button) getView().findViewById(R.id.okBtn);
		cancelBtn = (Button) getView().findViewById(R.id.cancelBtn);
		
		number0Btn.setOnClickListener(getNumberBtnOnClickListener("0"));
		number1Btn.setOnClickListener(getNumberBtnOnClickListener("1"));
		number2Btn.setOnClickListener(getNumberBtnOnClickListener("2"));
		number3Btn.setOnClickListener(getNumberBtnOnClickListener("3"));
		number4Btn.setOnClickListener(getNumberBtnOnClickListener("4"));
		number5Btn.setOnClickListener(getNumberBtnOnClickListener("5"));
		number6Btn.setOnClickListener(getNumberBtnOnClickListener("6"));
		number7Btn.setOnClickListener(getNumberBtnOnClickListener("7"));
		number8Btn.setOnClickListener(getNumberBtnOnClickListener("8"));
		number9Btn.setOnClickListener(getNumberBtnOnClickListener("9"));
		
		actionCBtn.setOnClickListener(getClearBtnOnClickListener());
		actionXBtn.setOnClickListener(getDeleteBtnOnClickListener());
		
		okBtn.setOnClickListener(getOkBtnOnClickListener());
		cancelBtn.setOnClickListener(getCancelBtnOnClickListener());
		
		orderTypeArrayAdapter = new CodeSpinnerArrayAdapter(mOrderTypeSp, getActivity(), CodeUtil.getOrderTypes(),
											R.layout.cashier_spinner_items, 
											R.layout.cashier_spinner_items_selected, 
											R.layout.cashier_spinner_selected_item);
		
		mOrderTypeSp.setAdapter(orderTypeArrayAdapter);
		
		refreshDisplay();
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
		
		saveDataFromView();
		
		outState.putSerializable(TOTAL_ITEM, (Serializable) mTotalItem);
		outState.putSerializable(ORDER_TYPE, (Serializable) mOrderType);
		outState.putSerializable(CUSTOMER, (Serializable) mCustomerName);
		outState.putSerializable(RESERVATION_NO, (Serializable) mReservationNo);
	}
	
	private void saveDataFromView() {
		
		mTotalItem = CommonUtil.parseIntNumber(mTotalItemText.getText().toString());
    	mOrderType = CodeBean.getNvlCode((CodeBean) mOrderTypeSp.getSelectedItem());
    	mReservationNo = CommonUtil.parseIntCurrency(mReservationNoText.getText().toString());
    	mCustomerName = mCustomerText.getText().toString();
	}
	
	private void refreshDisplay() {
		
		if (getView() == null) {
			return;
		}
		
		int orderTypeIndex = orderTypeArrayAdapter.getPosition(mOrderType);
		
		mTotalItemText.setText(CommonUtil.formatNumber(mTotalItem));
		mOrderTypeSp.setSelection(orderTypeIndex);
		mCustomerText.setText(mCustomerName);
		mReservationNoText.setText(CommonUtil.formatNumber(mReservationNo));
		
		if (mOrderType == null) {
			mOrderType = Constant.ORDER_TYPE_DINE_IN;
		}
		
		if (Constant.ORDER_TYPE_DINE_IN.equals(mOrderType)) {
			
			mCustomerText.setVisibility(View.GONE);
			mReservationNoPanel.setVisibility(View.VISIBLE);
			mReservationInputPanel.setVisibility(View.VISIBLE);
			
		} else {
			
			mCustomerText.setVisibility(View.VISIBLE);
			mReservationNoPanel.setVisibility(View.GONE);
			mReservationInputPanel.setVisibility(View.GONE);
			
			mCustomerText.requestFocus();
		}
	}
	
	private View.OnClickListener getNumberBtnOnClickListener(final String numberText) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int payment = CommonUtil.parseIntCurrency(mReservationNoText.getText().toString());
				String number = String.valueOf(payment);
				
				if (number.equals("0")) {
					mReservationNoText.setText(numberText);
				} else {
					number = CommonUtil.formatNumber(number + numberText);
					mReservationNoText.setText(number);
				}
			}
		};
	}
	
	private View.OnClickListener getClearBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mReservationNoText.setText("0");
			}
		};
	}
	
	private View.OnClickListener getDeleteBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int payment = CommonUtil.parseIntCurrency(mReservationNoText.getText().toString());
				String number = String.valueOf(payment);
				
				if (number.length() == 1) {
					mReservationNoText.setText("0");
				} else {
					number = CommonUtil.formatNumber(number.substring(0, number.length()-1));
					mReservationNoText.setText(number);
				}
			}
		};
	}
	
	private View.OnClickListener getOkBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				saveDataFromView();
				
				String orderReference = Constant.EMPTY_STRING;
				
				if (Constant.ORDER_TYPE_DINE_IN.equals(mOrderType)) {
					orderReference = String.valueOf(mReservationNo);
				} else {
					orderReference = mCustomerName;
				}
				
				if (Constant.ORDER_TYPE_DINE_IN.equals(mOrderType) && "0".equals(orderReference)) {
				
					NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_please_choose_table_no));
	    			
	    			return;
	    			
				} else if (Constant.ORDER_TYPE_TAKEWAY.equals(mOrderType) && CommonUtil.isEmpty(orderReference)) {
				
					NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.alert_customer_name_empty));
	    			
	    			return;
				}
				
				mActionListener.onOrderInfoProvided(orderReference, mOrderType);
				
				mReservationNo = 0;
				
				dismiss();
			}
		};
	}
	
	private View.OnClickListener getCancelBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mReservationNo = 0;
				
				dismiss();
			}
		};
	}
	
	public void setTotalOrder(int totalOrder) {
		
		mTotalItem = totalOrder;
		
		refreshDisplay();
	}
}
