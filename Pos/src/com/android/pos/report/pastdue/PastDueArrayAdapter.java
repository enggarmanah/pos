package com.android.pos.report.pastdue;

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

public class PastDueArrayAdapter extends ArrayAdapter<Bills> {

	private Context mContext;
	private List<Bills> mBills;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onBillSelected(Bills bill);
		
		public Bills getSelectedBill();
	}

	class ViewHolder {
		
		TextView billReferenceNoText;
		TextView remarksText;
		TextView billDateText;
		TextView billAmountText;
		TextView supplierText;
	}

	public PastDueArrayAdapter(Context context, List<Bills> bills, ItemActionListener listener) {

		super(context, R.layout.report_pastdue_list_item, bills);
		
		this.mContext = context;
		this.mBills = bills;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Bills bill = mBills.get(position);
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView billReferenceText = null;
		TextView remarksText = null;
		TextView billAmountText = null;
		TextView billDateText = null;
		TextView supplierText = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.report_pastdue_list_item, parent, false);
			
			billReferenceText = (TextView) rowView.findViewById(R.id.billReferenceNoText);
			remarksText = (TextView) rowView.findViewById(R.id.remarksText);
			billAmountText = (TextView) rowView.findViewById(R.id.billAmountText);
			billDateText = (TextView) rowView.findViewById(R.id.billDate);
			supplierText = (TextView) rowView.findViewById(R.id.supplierText);
			
			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.billReferenceNoText = billReferenceText;
			viewHolder.remarksText = remarksText;
			viewHolder.billAmountText = billAmountText;
			viewHolder.billDateText = billDateText;
			viewHolder.supplierText = supplierText;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			billReferenceText = viewHolder.billReferenceNoText;
			remarksText = viewHolder.remarksText;
			billAmountText = viewHolder.billAmountText;
			billDateText = viewHolder.billDateText;
			supplierText = viewHolder.supplierText;
		}
			
		String remarks = bill.getRemarks();
	    
	    billReferenceText.setText(bill.getBillReferenceNo());
	    
	    if (CommonUtil.isEmpty(bill.getBillReferenceNo())) {
	    	billReferenceText.setText(mContext.getString(R.string.bill_no_receipt));
		}
	    
		billDateText.setText(CommonUtil.formatDate(bill.getBillDueDate()));
		remarksText.setText(remarks);
		
		float payment = CommonUtil.getNvlFloat(bill.getPayment());
		float billAmount = CommonUtil.getNvlFloat(bill.getBillAmount());
		
		if (payment < billAmount) {
			billAmountText.setText(CommonUtil.formatCurrency(billAmount - payment));
			billAmountText.setTextColor(mContext.getResources().getColor(R.color.text_red));
		} else {
			billAmountText.setText(CommonUtil.formatCurrency(payment));
			billAmountText.setTextColor(mContext.getResources().getColor(R.color.text_medium));
		}
		
		supplierText.setText(bill.getSupplierName());
		
		if (!CommonUtil.isEmpty(bill.getSupplierName())) {
			supplierText.setVisibility(View.VISIBLE);
		} else {
			supplierText.setVisibility(View.GONE);
		}
		
		Bills selectedItem = mCallback.getSelectedBill();
		
		if (selectedItem != null && selectedItem.getId() == bill.getId()) {
			
			rowView.setBackgroundColor(mContext.getResources().getColor(R.color.list_row_selected_background));
			
		} else {
			rowView.setBackgroundColor(mContext.getResources().getColor(R.color.list_row_normal_background));
		}
		
		rowView.setOnClickListener(getItemOnClickListener(bill));

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final Bills bill) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.setSelected(true);

				mCallback.onBillSelected(bill);
			}
		};
	}
}