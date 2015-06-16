package com.android.pos.cashier;

import android.content.Context;
import android.widget.Spinner;

import com.android.pos.R;
import com.android.pos.base.adapter.BaseSpinnerArrayAdapter;
import com.android.pos.model.PriceBean;
import com.android.pos.util.CommonUtil;

public class CashierProductCountPriceSpinnerArrayAdapter extends BaseSpinnerArrayAdapter<PriceBean> {

	Spinner spinner;

	public CashierProductCountPriceSpinnerArrayAdapter(Spinner spinner, Context context, PriceBean[] options) {
		super(context, options, R.layout.cashier_spinner_items, R.layout.cashier_spinner_items_selected,
				R.layout.cashier_spinner_selected_item);
		
		this.spinner = spinner;
	}
	
	@Override
	protected String getSelectedValue() {

		if (spinner.getSelectedItem() != null) {
			return String.valueOf(((PriceBean) spinner.getSelectedItem()).getValue());
		} else {
			return null;
		}
	}

	@Override
	protected String getLabel1(PriceBean price) {

		return price.getType();
	}
	
	@Override
	protected String getLabel2(PriceBean price) {

		return CommonUtil.formatCurrency(price.getValue());
	}

	@Override
	protected String getValue(PriceBean price) {

		return CommonUtil.formatCurrency(price.getValue());
	}
}
