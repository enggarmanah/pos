package com.android.pos.report.cashflow;

import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;

public interface CashFlowActionListener {
	
	public void onCashFlowYearSelected(CashFlowYearBean cashFlowYear);
	
	public void onCashFlowMonthSelected(CashFlowMonthBean cashFlowMonth);
	
	public void onBackButtonClicked();
}
