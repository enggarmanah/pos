package com.android.pos.report.commision;

import java.util.List;

import com.android.pos.R;
import com.android.pos.model.EmployeeCommisionBean;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommisionDetailEmployeeArrayAdapter extends ArrayAdapter<EmployeeCommisionBean> {

	private Context context;
	private List<EmployeeCommisionBean> employeeCommisions;
	
	class ViewHolder {
		
		TextView dateText;
		TextView productNameText;
		TextView commisionText;
	}

	public CommisionDetailEmployeeArrayAdapter(Context context, List<EmployeeCommisionBean> employeeCommisions) {

		super(context, R.layout.report_commision_detail_employee_list_item, employeeCommisions);
		
		this.context = context;
		this.employeeCommisions = employeeCommisions;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		EmployeeCommisionBean employeeCommision = employeeCommisions.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView dateText = null;
		TextView productNameText = null;
		TextView commisionText = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.report_commision_detail_employee_list_item, parent, false);
			
			dateText = (TextView) rowView.findViewById(R.id.dateText);
			productNameText = (TextView) rowView.findViewById(R.id.productNameText);
			commisionText = (TextView) rowView.findViewById(R.id.commisionText);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.dateText = dateText;
			viewHolder.productNameText = productNameText;
			viewHolder.commisionText = commisionText;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			dateText = viewHolder.dateText;
			productNameText = viewHolder.productNameText;
			commisionText = viewHolder.commisionText;
		}
		
		dateText.setText(CommonUtil.formatDateTime(employeeCommision.getTransaction_date()));
		productNameText.setText(employeeCommision.getProduct_name());
		commisionText.setText(CommonUtil.formatCurrency(employeeCommision.getCommision()));
		
		return rowView;
	}
}