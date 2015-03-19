package com.android.pos.report.inventory;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Inventory;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InventoryReportDetailArrayAdapter extends ArrayAdapter<Inventory> {

	private Context context;
	private List<Inventory> mInventories;
	
	class ViewHolder {
		ImageView flowImage;
		TextView remarksText;
		TextView deliveryDateText;
		TextView quantityText;
		TextView supplierText;
	}

	public InventoryReportDetailArrayAdapter(Context context, List<Inventory> productStatistics) {

		super(context, R.layout.report_inventory_detail_list_item, productStatistics);
		
		this.context = context;
		this.mInventories = productStatistics;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Inventory inventory = mInventories.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		ImageView flowImage = null;
		TextView remarksText = null;
		TextView quantityText = null;
		TextView deliveryDateText = null;
		TextView supplierText = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.report_inventory_detail_list_item, parent, false);
			
			flowImage = (ImageView) rowView.findViewById(R.id.flowImage);
			remarksText = (TextView) rowView.findViewById(R.id.remarksText);
			quantityText = (TextView) rowView.findViewById(R.id.quantityText);
			deliveryDateText = (TextView) rowView.findViewById(R.id.deliveryDate);
			supplierText = (TextView) rowView.findViewById(R.id.supplierText);
			
			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.flowImage = flowImage;
			viewHolder.remarksText = remarksText;
			viewHolder.quantityText = quantityText;
			viewHolder.deliveryDateText = deliveryDateText;
			viewHolder.supplierText = supplierText;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			flowImage = viewHolder.flowImage;
			remarksText = viewHolder.remarksText;
			quantityText = viewHolder.quantityText;
			deliveryDateText = viewHolder.deliveryDateText;
			supplierText = viewHolder.supplierText;
		}
			
		int padding = flowImage.getPaddingLeft();
		
		flowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.action_back));
		flowImage.setBackground(context.getResources().getDrawable(R.drawable.bg_flow_out));
		
		if (Constant.INVENTORY_STATUS_PURCHASE.equals(inventory.getStatus()) ||
			Constant.INVENTORY_STATUS_REPLACEMENT.equals(inventory.getStatus())) {
			
			flowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.action_forward));
			flowImage.setBackground(context.getResources().getDrawable(R.drawable.bg_flow_in));
		}
		
	    flowImage.setPadding(padding, padding, padding, padding);
		
	    String remarks = CommonUtil.isEmpty(inventory.getRemarks()) ? CodeUtil.getInvetoriStatusLabel(inventory.getStatus()) : CodeUtil.getInvetoriStatusLabel(inventory.getStatus()) + ". " + inventory.getRemarks(); 
	    
	    quantityText.setText(CommonUtil.formatNumber(inventory.getQuantity()));
		deliveryDateText.setText(CommonUtil.formatDate(inventory.getDeliveryDate()));
		remarksText.setText(remarks);
		
		if (!CommonUtil.isEmpty(inventory.getSupplierName())) {
			supplierText.setText(inventory.getSupplierName());
			supplierText.setVisibility(View.VISIBLE);
		} else {
			supplierText.setVisibility(View.GONE);
		}
		
		return rowView;
	}
}