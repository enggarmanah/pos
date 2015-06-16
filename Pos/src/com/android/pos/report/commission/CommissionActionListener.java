package com.android.pos.report.commission;

import com.android.pos.dao.Employee;
import com.android.pos.model.CommisionMonthBean;
import com.android.pos.model.CommisionYearBean;

public interface CommissionActionListener {
	
	public void onCommisionYearSelected(CommisionYearBean transactionYear);
	
	public void onCommisionMonthSelected(CommisionMonthBean transactionMonth);
	
	public void onBackPressed();
	
	public void onEmployeeSelected(Employee employee);
}
