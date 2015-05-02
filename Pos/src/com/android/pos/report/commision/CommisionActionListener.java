package com.android.pos.report.commision;

import com.android.pos.dao.Employee;
import com.android.pos.model.CommisionMonthBean;
import com.android.pos.model.CommisionYearBean;

public interface CommisionActionListener {
	
	public void onCommisionYearSelected(CommisionYearBean transactionYear);
	
	public void onCommisionMonthSelected(CommisionMonthBean transactionMonth);
	
	public void onBackPressed();
	
	public void onEmployeeSelected(Employee employee);
}
