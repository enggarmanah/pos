
package com.android.pos.report.cashflow;

import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.model.CashflowBean;

public interface CashFlowActionListener {
	
	public void onCashFlowYearSelected(CashFlowYearBean cashFlowYear);
	
	public void onCashFlowMonthSelected(CashFlowMonthBean cashFlowMonth);
	
	public void onCashFlowSelected(CashflowBean cashFlow);
	
	public void onBackPressed();
}
