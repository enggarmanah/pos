package com.android.pos.bills;

import java.util.List;

import com.android.pos.R;
import com.android.pos.base.adapter.BaseSearchArrayAdapter;
import com.android.pos.dao.Bills;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BillsSearchArrayAdapter extends BaseSearchArrayAdapter<Bills> {
	
	class ViewHolder {
		TextView itemText;
		TextView remarksText;
		TextView billDateText;
		TextView billAmountText;
		TextView supplierText;
	}
	
	public BillsSearchArrayAdapter(Context context, List<Bills> inventories, Bills selectedBills, ItemActionListener<Bills> listener) {
		super(context, R.layout.bills_list_item, inventories, selectedBills, listener);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Bills item = items.get(position);
		
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
			
		String remarks = item.getRemarks();
	    
	    nameText.setText(getItemName(item));
	    
	    if (CommonUtil.isEmpty(item.getBillReferenceNo())) {
	    	nameText.setText("Tanpa Nota");
		}
	    
		billDateText.setText(CommonUtil.formatDate(item.getBillDate()));
		remarksText.setText(remarks);
		
		int payment = CommonUtil.getNvl(item.getPayment());
		int billAmount = CommonUtil.getNvl(item.getBillAmount());
		
		if (payment < billAmount) {
			billAmountText.setText(CommonUtil.formatCurrency(billAmount - payment));
			billAmountText.setTextColor(context.getResources().getColor(R.color.text_red));
		} else {
			billAmountText.setText(CommonUtil.formatCurrency(payment));
			billAmountText.setTextColor(context.getResources().getColor(R.color.text_blue));
		}
		
		supplierText.setText(item.getSupplierName());
		
		if (!CommonUtil.isEmpty(item.getSupplierName())) {
			supplierText.setVisibility(View.VISIBLE);
		} else {
			supplierText.setVisibility(View.GONE);
		}
		
		if (selectedItem != null && getItemId(selectedItem) == getItemId(item)) {
			
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_selected_background));
			selectedView = rowView;
			
		} else {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));
		}
		
		rowView.setOnClickListener(getItemOnClickListener(item, nameText));

		return rowView;
	}
	
	@Override
	public Long getItemId(Bills bills) {
		return bills.getId();
	}
	
	@Override
	public String getItemName(Bills bills) {
		return bills.getBillReferenceNo();
	}
}