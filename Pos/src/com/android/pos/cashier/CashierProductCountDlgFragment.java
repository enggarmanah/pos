package com.android.pos.cashier;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Employee;
import com.android.pos.dao.EmployeeDaoService;
import com.android.pos.dao.Product;
import com.android.pos.util.CommonUtil;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CashierProductCountDlgFragment extends DialogFragment {
	
	public interface ProductActionListener {
		
		public void onProductQuantitySelected(Product product, Employee personInCharge, int quantity, String remarks);
	}
	
	TextView mProductText;
	Spinner mPersonInChargeSp;
	TextView mQuantityText;
	
	LinearLayout mNumberBtnPanel;
	
	TextView mRemarksBtn;
	TextView mRemarksDivider;
	EditText mRemarksText;
	
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
	
	Product mProduct;
	String mRemarks;
	int mQuantity;
	
	ProductActionListener mActionListener;
	
	CashierProductCountPicSpinnerArrayAdapter mPicArrayAdapter;
	
	private static String PRODUCT = "PRODUCT";
	private static String QUANTITY = "QUANTITY";
	
	private EmployeeDaoService mEmployeeDaoService = new EmployeeDaoService();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        if (savedInstanceState != null) {
        	
        	mProduct = (Product) savedInstanceState.get(PRODUCT);
        	mQuantity = (Integer) savedInstanceState.get(QUANTITY);
        }
        
        setCancelable(false);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		mQuantity = Integer.valueOf(mQuantityText.getText().toString());
		
		outState.putSerializable(PRODUCT, mProduct);
		outState.putSerializable(QUANTITY, mQuantity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_product_count_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mProductText = (TextView) getView().findViewById(R.id.productText);
		mPersonInChargeSp = (Spinner) getView().findViewById(R.id.personInChargeSp);
		mQuantityText = (TextView) getView().findViewById(R.id.countText);
		
		mRemarksBtn = (TextView) getView().findViewById(R.id.remarksBtn);
		mRemarksDivider = (TextView) getView().findViewById(R.id.remarksDivider);
		mRemarksText = (EditText) getView().findViewById(R.id.remarksText);
		
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
		
		mRemarksBtn.setOnClickListener(getRemarksBtnOnClickListener());
		
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
		
		mPicArrayAdapter = new CashierProductCountPicSpinnerArrayAdapter(mPersonInChargeSp, getActivity(), getPersonInCharge());

		mPersonInChargeSp.setAdapter(mPicArrayAdapter);
		
		refreshDisplay();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (ProductActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CashierActionListener");
        }
    }
	
	private void refreshDisplay() {
		
		if (getView() == null) {
			return;
		}
		
		if (Constant.PRODUCT_TYPE_SERVICE.equals(mProduct.getType())) {
			
			mQuantity = 1;
			
			mPersonInChargeSp.setVisibility(View.VISIBLE);
			mNumberBtnPanel.setVisibility(View.GONE);
		
		} else {
			
			mPersonInChargeSp.setVisibility(View.GONE);
			mNumberBtnPanel.setVisibility(View.VISIBLE);
		}
		
		mProductText.setText(mProduct.getName());
		mRemarksText.setText(mRemarks);
		mQuantityText.setText(String.valueOf(mQuantity));
		
		displayRemarks(!CommonUtil.isEmpty(mRemarks));
	}
	
	private Employee[] getPersonInCharge() {

		List<Employee> list = mEmployeeDaoService.getEmployees(Constant.EMPTY_STRING, 0);
		
		return list.toArray(new Employee[list.size()]);
	}
	
	private View.OnClickListener getNumberBtnOnClickListener(final String numberText) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String number = mQuantityText.getText().toString();
				
				if (number.equals("0")) {
					mQuantityText.setText(numberText);
				} else {
					mQuantityText.setText(number + numberText);
				}
			}
		};
	}
	
	private View.OnClickListener getClearBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mQuantityText.setText("0");
			}
		};
	}
	
	private View.OnClickListener getDeleteBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String number = mQuantityText.getText().toString(); 
				
				if (number.length() == 1) {
					mQuantityText.setText("0");
				} else {
					mQuantityText.setText(number.substring(0, number.length()-1));
				}
			}
		};
	}
	
	private View.OnClickListener getOkBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Employee personInCharge = null;
				
				if (Constant.STATUS_YES.equals(mProduct.getPicRequired())) {
					personInCharge = (Employee) mPersonInChargeSp.getSelectedItem();
				}
				
				mQuantity = Integer.valueOf(mQuantityText.getText().toString());
				mActionListener.onProductQuantitySelected(mProduct, personInCharge, mQuantity, mRemarksText.getText().toString());
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
	
	private View.OnClickListener getRemarksBtnOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mRemarksText.getVisibility() == View.VISIBLE) {
					
					mRemarksText.setText(Constant.EMPTY_STRING);
					displayRemarks(false);
					
				} else {
					
					displayRemarks(true);
				}
			}
		};
	}
	
	public void setProduct(Product product, int quantity, String remarks) {
		
		this.mProduct = product;
		this.mRemarks = remarks;
		this.mQuantity = quantity;
		
		refreshDisplay();
	}
	
	public void displayRemarks(boolean isDisplayed) {
		
		if (isDisplayed) {
			
			mRemarksDivider.setVisibility(View.VISIBLE);
			mRemarksText.setVisibility(View.VISIBLE);
			mRemarksText.requestFocus();
			
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(mRemarksText, InputMethodManager.SHOW_IMPLICIT);
			
		} else {
			
			mRemarksDivider.setVisibility(View.GONE);
			mRemarksText.setVisibility(View.GONE);
		}
	}
}
