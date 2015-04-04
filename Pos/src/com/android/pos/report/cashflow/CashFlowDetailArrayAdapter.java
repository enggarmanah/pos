package com.android.pos.report.cashflow;

import java.util.List;

import com.android.pos.R;
import com.android.pos.dao.Bills;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CashFlowDetailArrayAdapter extends ArrayAdapter<Bills> {

	private Context context;
	private List<Bills> bills;
	private ItemActionListener mCallback;
	
	public interface ItemActionListener {

		public void onBillSelected(Bills bill);
	}
	
	class ViewHolder {
		
		TextView itemText;
		TextView remarksText;
		TextView billDateText;
		TextView billAmountText;
		TextView supplierText;
	}

	public CashFlowDetailArrayAdapter(Context context, List<Bills> productStatistics, ItemActionListener listener) {

		super(context, R.layout.bills_list_item, productStatistics);
		
		this.context = context;
		this.bills = productStatistics;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Bills bill = bills.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView nameText = null;
		TextView remarksText = null;
		TextView billAmountText = null;
		TextView billDateText = null;
		TextView supplierText = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.bills_list_item, parent, false);
			
			nameText = (TextView) rowView.findViewById(R.id.nameText);
			remarksText = (TextView) rowView.findViewById(R.id.remarksText);
			billAmountText = (TextView) rowView.findViewById(R.id.billAmountText);
			billDateText = (TextView) rowView.findViewById(R.id.billDate);
			supplierText = (TextView) rowView.findViewById(R.id.supplierText);
			
			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.itemText = nameText;
			viewHolder.remarksText = remarksText;
			viewHolder.billAmountText = billAmountText;
			viewHolder.billDateText = billDateText;
			viewHolder.supplierText = supplierText;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			nameText = viewHolder.itemText;
			remarksText = viewHolder.remarksText;
			billAmountText = viewHolder.billAmountText;
			billDateText = viewHolder.billDateText;
			supplierText = viewHolder.supplierText;
		}
			
		String remarks = bill.getRemarks();
	    String billReferenceNo = bill.getBillReferenceNo();
		
		
		if (CommonUtil.isEmpty(billReferenceNo)) {
	    	billReferenceNo = "Tanpa Nota";
		}
		
	    nameText.setText(billReferenceNo);
	    billDateText.setText(CommonUtil.formatDate(bill.getBillDate()));
		remarksText.setText(remarks);
		
		int payment = CommonUtil.getNvl(bill.getPayment());
		int billAmount = CommonUtil.getNvl(bill.getBillAmount());
		
		if (payment < billAmount) {
			billAmountText.setText(CommonUtil.formatCurrency(billAmount - payment));
			billAmountText.setTextColor(context.getResources().getColor(R.color.text_red));
		} else {
			billAmountText.setText(CommonUtil.formatCurrency(payment));
			billAmountText.setTextColor(context.getResources().getColor(R.color.text_medium));
		}
		
		if (!CommonUtil.isEmpty(bill.getSupplierName())) {
			supplierText.setText(CommonUtil.formatNumber(bill.getSupplierName()));
			supplierText.setVisibility(View.VISIBLE);
		} else {
			supplierText.setVisibility(View.GONE);
		}
		
		rowView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mCallback.onBillSelected(bill);
			}
		});
		
		return rowView;
	}
}