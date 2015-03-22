package com.android.pos.cashier;

import java.util.List;

import com.android.pos.R;
import com.android.pos.dao.TransactionItem;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.UserUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CashierOrderArrayAdapter extends ArrayAdapter<TransactionItem> {

	private Context context;
	private List<TransactionItem> transactionItems;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onItemSelected(TransactionItem item);
	}

	class ViewHolder {
		TextView quantityText;
		TextView nameText;
		TextView picText;
		TextView priceText;
		TextView totalPriceText;
	}

	public CashierOrderArrayAdapter(Context context, List<TransactionItem> transactionItems, ItemActionListener listener) {

		super(context, R.layout.cashier_order_list_item, transactionItems);
		
		this.context = context;
		this.transactionItems = transactionItems;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final TransactionItem transactionItem = transactionItems.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView productQuantity = null;
		TextView productName = null;
		TextView productInfo = null;
		TextView productPrice = null;
		TextView totalProductPrice = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.cashier_order_list_item, parent, false);
			
			productQuantity = (TextView) rowView.findViewById(R.id.quantityText);
			productName = (TextView) rowView.findViewById(R.id.nameText);
			productInfo = (TextView) rowView.findViewById(R.id.infoText);
			productPrice = (TextView) rowView.findViewById(R.id.priceText);
			totalProductPrice = (TextView) rowView.findViewById(R.id.totalPriceText);
			
			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.quantityText = productQuantity;
			viewHolder.nameText = productName;
			viewHolder.picText = productInfo;
			viewHolder.priceText = productPrice;
			viewHolder.totalPriceText = totalProductPrice;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			productQuantity = viewHolder.quantityText;
			productName = viewHolder.nameText;
			productInfo = viewHolder.picText;
			productPrice = viewHolder.priceText;
			totalProductPrice = viewHolder.totalPriceText;
		}
		
		productQuantity.setText(String.valueOf(transactionItem.getQuantity()));
		productName.setText(transactionItem.getProductName());
		
		if (transactionItem.getEmployeeId() != 0) {
			
			productInfo.setText(transactionItem.getEmployee().getName());
			productInfo.setVisibility(View.VISIBLE);
			
		} else {
			
			productInfo.setVisibility(View.GONE);
		}
		
		if (!CommonUtil.isEmpty(transactionItem.getRemarks())) {
			
			productInfo.setText(transactionItem.getRemarks());
			productInfo.setVisibility(View.VISIBLE);
			
		} else {
			
			productInfo.setVisibility(View.GONE);
		}
		
		productPrice.setText(CommonUtil.formatCurrency(transactionItem.getPrice()));
		totalProductPrice.setText(CommonUtil.formatCurrency(transactionItem.getQuantity() * transactionItem.getPrice()));
		
		if (UserUtil.isWaitress()) {
			productPrice.setVisibility(View.GONE);
			totalProductPrice.setVisibility(View.GONE);
		} else {
			productPrice.setVisibility(View.VISIBLE);
			totalProductPrice.setVisibility(View.VISIBLE);
		}

		rowView.setOnClickListener(getItemOnClickListener(transactionItem));

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final TransactionItem item) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.setSelected(true);

				mCallback.onItemSelected(item);
			}
		};
	}
}