package com.android.pos.printer;

import com.android.pos.CodeBean;
import com.android.pos.R;
import com.android.pos.base.adapter.CodeSpinnerArrayAdapter;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PrinterSelectActivity extends Activity {

	EditText mPrinterLineSizeText;

	Spinner mPrinterRequiredSp;
	Spinner mPrinterMiniFontSp;

	CodeSpinnerArrayAdapter printerRequiredArrayAdapter;
	CodeSpinnerArrayAdapter printerMiniFontArrayAdapter;
	
	Button mOkBtn;
	Button mCancelBtn;
	
	private MerchantDaoService mMerchantDaoService = new MerchantDaoService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setTheme(android.R.style.Theme_Holo_Light_Dialog);

		setContentView(R.layout.printer_select_activity);
	}

	@Override
	public void onStart() {

		super.onStart();

		mPrinterLineSizeText = (EditText) findViewById(R.id.printerLineSizeText);

		mPrinterRequiredSp = (Spinner) findViewById(R.id.printerRequiredSp);
		mPrinterMiniFontSp = (Spinner) findViewById(R.id.printerMiniFontSp);
		
		mOkBtn = (Button) findViewById(R.id.okBtn);
		mCancelBtn = (Button) findViewById(R.id.cancelBtn);

		printerRequiredArrayAdapter = new CodeSpinnerArrayAdapter(mPrinterRequiredSp, this, CodeUtil.getStatus());
		mPrinterRequiredSp.setAdapter(printerRequiredArrayAdapter);

		printerMiniFontArrayAdapter = new CodeSpinnerArrayAdapter(mPrinterMiniFontSp, this, CodeUtil.getBooleans());
		mPrinterMiniFontSp.setAdapter(printerMiniFontArrayAdapter);

		Merchant merchant = MerchantUtil.getMerchant();

		int printerRequiredIndex = printerRequiredArrayAdapter.getPosition(merchant.getPrinterRequired());
		int printerMiniFontIndex = printerMiniFontArrayAdapter.getPosition(merchant.getPrinterMiniFont());

		mPrinterLineSizeText.setText(CommonUtil.formatString(merchant.getPrinterLineSize()));

		mPrinterRequiredSp.setSelection(printerRequiredIndex);
		mPrinterMiniFontSp.setSelection(printerMiniFontIndex);

		mPrinterRequiredSp.requestFocus();
		
		mOkBtn.setOnClickListener(getOkBtnOnClickListener());
		mCancelBtn.setOnClickListener(getCancelBtnOnClickListener());
	}

	private View.OnClickListener getOkBtnOnClickListener() {

		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Merchant merchant = mMerchantDaoService.getMerchant(MerchantUtil.getMerchantId());
				
				String printerRequired = CodeBean.getNvlCode((CodeBean) mPrinterRequiredSp.getSelectedItem());
		    	String printerMiniFont = CodeBean.getNvlCode((CodeBean) mPrinterMiniFontSp.getSelectedItem());
		    	Integer printerLineSize = CommonUtil.parseInteger(mPrinterLineSizeText.getText().toString());
				
		    	merchant.setPrinterRequired(printerRequired);
		    	merchant.setPrinterMiniFont(printerMiniFont);
				merchant.setPrinterLineSize(printerLineSize);
				
				mMerchantDaoService.updateMerchant(merchant);
				
				MerchantUtil.setMerchant(merchant);
				
				finish();
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
