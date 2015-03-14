package com.android.pos.cashier;

import com.android.pos.R;
import com.android.pos.dao.Discount;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.NotificationUtil;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CashierDiscountAmountDlgFragment extends DialogFragment {
	
	TextView mDiscountText;
	TextView mAmountText;
	
	LinearLayout mNumberBtnPanel;
	
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
	
	Button mActionCBtn;
	ImageButton mActionXBtn;
	
	Button mOkBtn;
	Button mCancelBtn;
	
	Discount mDiscount;
	int mAmount;
	
	CashierActionListener mActionListener;
	
	private static String DISCOUNT = "DISCOUNT";
	private static String AMOUNT = "AMOUNT";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        if (savedInstanceState != null) {
        	
        	mDiscount = (Discount) savedInstanceState.get(DISCOUNT);
        	mAmount = (Integer) savedInstanceState.get(AMOUNT);
        }
        
        setCancelable(false);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		mAmount = Integer.valueOf(mAmountText.getText().toString());
		
		outState.putSerializable(DISCOUNT, mDiscount);
		outState.putSerializable(AMOUNT, mAmount);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_discount_amount_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mDiscountText = (TextView) getView().findViewById(R.id.discountText);
		mAmountText = (TextView) getView().findViewById(R.id.amountText);
		
		mNumberBtnPanel = (LinearLayout) getView().findViewById(R.id.numberBtnPanel);
		
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
		
		mActionCBtn = (Button) getView().findViewById(R.id.actionCBtn);
		mActionXBtn = (ImageButton) getView().findViewById(R.id.actionXBtn);
		
		mOkBtn = (Button) getView().findViewById(R.id.okBtn);
		mCancelBtn = (Button) getView().findViewById(R.id.cancelBtn);
		
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
		
		mActionCBtn.setOnClickListener(getClearBtnOnClickListener());
		mActionXBtn.setOnClickListener(getDeleteBtnOnClickListener());
		
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
            throw new ClassCastException(activity.toString()
                    + " must implement CashierActionListener");
        }
    }
	
	private void refreshDisplay() {
		
		if (getView() == null) {
			return;
		}
		
		mDiscountText.setText(mDiscount.getName());
		mAmountText.setText(CommonUtil.formatNumber(mAmount));
	}
		
	private View.OnClickListener getNumberBtnOnClickListener(final String numberText) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int payment = CommonUtil.parseCurrency(mAmountText.getText().toString());
				String number = String.valueOf(payment);
				
				if (number.equals("0")) {
					mAmountText.setText(numberText);
				} else {
					number = CommonUtil.formatNumber(number + numberText);
					mAmountText.setText(number);
				}
			}
		};
	}
	
	private View.OnClickListener getClearBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mAmountText.setText("0");
			}
		};
	}
	
	private View.OnClickListener getDeleteBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int payment = CommonUtil.parseCurrency(mAmountText.getText().toString());
				String number = String.valueOf(payment);
				
				if (number.length() == 1) {
					mAmountText.setText("0");
				} else {
					number = CommonUtil.formatNumber(number.substring(0, number.length()-1));
					mAmountText.setText(number);
				}
			}
		};
	}
	
	private View.OnClickListener getOkBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				mAmount = CommonUtil.parseCurrency(mAmountText.getText().toString());
				
				if (mAmount == 0) {
					
					NotificationUtil.setAlertMessage(getFragmentManager(), "Nominal diskon tidak boleh kosong.");
	    			
	    			return;
				}
	    			
				mDiscount.setAmount(mAmount);
				
				mActionListener.onDiscountSelected(mDiscount);
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
	
	public void setDiscount(Discount discount) {
		
		this.mDiscount = discount;
		
		refreshDisplay();
	}
}
