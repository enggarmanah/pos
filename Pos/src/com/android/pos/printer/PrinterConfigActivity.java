package com.android.pos.printer;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BasePopUpActivity;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.model.FormFieldBean;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.PrintUtil;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PrinterConfigActivity extends BasePopUpActivity {

	EditText mPrinterLineSizeText;

	Spinner mPrinterRequiredSp;
	Spinner mPrinterFontSizeSp;

	CodeSpinnerArrayAdapter printerRequiredArrayAdapter;
	CodeSpinnerArrayAdapter printerFontSizeArrayAdapter;
	
	Button mOkBtn;
	Button mCancelBtn;
	
	private MerchantDaoService mMerchantDaoService = new MerchantDaoService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setTheme(android.R.style.Theme_Holo_Light_Dialog);
		
		setContentView(R.layout.printer_select_activity);
		
		setFinishOnTouchOutside(false);
	}

	@Override
	public void onStart() {

		super.onStart();

		mPrinterLineSizeText = (EditText) findViewById(R.id.printerLineSizeText);

		mPrinterRequiredSp = (Spinner) findViewById(R.id.printerRequiredSp);
		mPrinterFontSizeSp = (Spinner) findViewById(R.id.printerFontSizeSp);
		
		registerField(mPrinterLineSizeText);
		registerMandatoryField(new FormFieldBean(mPrinterLineSizeText, R.string.field_printer_line_size));
		
		mOkBtn = (Button) findViewById(R.id.okBtn);
		mCancelBtn = (Button) findViewById(R.id.cancelBtn);

		printerRequiredArrayAdapter = new CodeSpinnerArrayAdapter(mPrinterRequiredSp, this, CodeUtil.getStatus());
		mPrinterRequiredSp.setAdapter(printerRequiredArrayAdapter);

		printerFontSizeArrayAdapter = new CodeSpinnerArrayAdapter(mPrinterFontSizeSp, this, CodeUtil.getFontSizes());
		mPrinterFontSizeSp.setAdapter(printerFontSizeArrayAdapter);

		Merchant merchant = MerchantUtil.getMerchant();

		int printerRequiredIndex = printerRequiredArrayAdapter.getPosition(merchant.getPrinterRequired());
		int printerMiniFontIndex = printerFontSizeArrayAdapter.getPosition(merchant.getPrinterMiniFont());

		mPrinterLineSizeText.setText(CommonUtil.formatString(merchant.getPrinterLineSize()));

		mPrinterRequiredSp.setSelection(printerRequiredIndex);
		mPrinterFontSizeSp.setSelection(printerMiniFontIndex);

		mPrinterRequiredSp.requestFocus();
		
		mOkBtn.setOnClickListener(getOkBtnOnClickListener());
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());
	}
	
	private View.OnClickListener getOkBtnOnClickListener() {

		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (isValidated()) {
				
					Merchant merchant = mMerchantDaoService.getMerchant(MerchantUtil.getMerchantId());
					
					boolean isPrinterActiveBefore = Constant.STATUS_ACTIVE.equals(merchant.getPrinterRequired());
					
					String printerRequired = CodeBean.getNvlCode((CodeBean) mPrinterRequiredSp.getSelectedItem());
			    	String printerMiniFont = CodeBean.getNvlCode((CodeBean) mPrinterFontSizeSp.getSelectedItem());
			    	Integer printerLineSize = CommonUtil.parseInteger(mPrinterLineSizeText.getText().toString());
					
			    	merchant.setPrinterRequired(printerRequired);
			    	merchant.setPrinterMiniFont(printerMiniFont);
					merchant.setPrinterLineSize(printerLineSize);
					
					mMerchantDaoService.updateMerchant(merchant);
					
					MerchantUtil.setMerchant(merchant);
					
					boolean isPrinterActiveNow = Constant.STATUS_ACTIVE.equals(merchant.getPrinterRequired());
					
					if (!isPrinterActiveBefore && isPrinterActiveNow) {
						
						if (PrintUtil.isBluetoothEnabled()) {
							PrintUtil.selectBluetoothPrinter();
						} else {
							PrintUtil.initBluetooth(null);
						}
						
					} else if (isPrinterActiveBefore && !isPrinterActiveNow) {
						PrintUtil.disablePrinterOptions();
					}
					
					finish();
				}
			}
		};
	}

	private View.OnClickListener getCancelBtnOnClickListener() {

		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		};
	}
}
