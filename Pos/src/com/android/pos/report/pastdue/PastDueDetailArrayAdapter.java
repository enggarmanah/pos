package com.android.pos.report.pastdue;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.Inventory;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PastDueDetailArrayAdapter extends ArrayAdapter<Inventory> {

	private Context context;
	private List<Inventory> mInventories;
	
	class ViewHolder {
		ImageView flowImage;
		TextView remarksText;
		TextView productText;
		TextView amountText;
		TextView supplierText;
	}

	public PastDueDetailArrayAdapter(Context context, List<Inventory> productStatistics) {

		super(context, R.layout.report_pastdue_detail_list_item, productStatistics);
		
		this.context = context;
		this.mInventories = productStatistics;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Inventory inventory = mInventories.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		ImageView flowImage = null;
		TextView productText = null;
		TextView remarksText = null;
		TextView amountText = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.report_pastdue_detail_list_item, parent, false);
			
			flowImage = (ImageView) rowView.findViewById(R.id.flowImage);
			productText = (TextView) rowView.findViewById(R.id.productText);
			remarksText = (TextView) rowView.findViewById(R.id.remarksText);
			amountText = (TextView) rowView.findViewById(R.id.amountText);
			
			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.flowImage = flowImage;
			viewHolder.remarksText = remarksText;
			viewHolder.amountText = amountText;
			viewHolder.productText = productText;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			flowImage = viewHolder.flowImage;
			remarksText = viewHolder.remarksText;
			amountText = viewHolder.amountText;
			productText = viewHolder.productText;
		}
			
		int padding = flowImage.getPaddingLeft();
		
		flowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_back_black));
		flowImage.setBackground(context.getResources().getDrawable(R.drawable.bg_flow_out));
		
		if (Constant.INVENTORY_STATUS_PURCHASE.equals(inventory.getStatus()) ||
			Constant.INVENTORY_STATUS_REPLACEMENT.equals(inventory.getStatus())) {
			
			flowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_forward_black));
			flowImage.setBackground(context.getResources().getDrawable(R.drawable.bg_flow_in));
		}
		
	    flowImage.setPadding(padding, padding, padding, padding);
		
	    productText.setText(inventory.getProductName());
	    remarksText.setText(CommonUtil.formatNumber(inventory.getQuantity()) + "  x  " + CommonUtil.formatCurrency(inventory.getProductCostPrice()));
		amountText.setText(CommonUtil.formatCurrency((inventory.getQuantity() * inventory.getProductCostPrice())));
		
		return rowView;
	}
}