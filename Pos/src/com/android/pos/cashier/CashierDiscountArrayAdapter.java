package com.android.pos.cashier;

import java.util.List;

import com.android.pos.R;
import com.android.pos.dao.Discount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CashierDiscountArrayAdapter extends ArrayAdapter<Discount> {

	private Context context;
	private List<Discount> discounts;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onDiscountSelected(Discount item);
	}

	class ViewHolder {
		TextView itemText;
	}

	public CashierDiscountArrayAdapter(Context context, List<Discount> discounts, ItemActionListener listener) {

		super(context, R.layout.cashier_discount_list_item, discounts);
		
		this.context = context;
		this.discounts = discounts;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Discount discount = discounts.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		TextView prdGroupName = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.cashier_discount_list_item, parent, false);
			prdGroupName = (TextView) rowView.findViewById(R.id.nameTxt);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemText = prdGroupName;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			prdGroupName = viewHolder.itemText;
		}
		
		prdGroupName.setText(discount.getName());

		rowView.setOnClickListener(getItemOnClickListener(discount, prdGroupName));

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final Discount discount, final TextView itemNameView) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.setSelected(true);

				mCallback.onDiscountSelected(discount);
			}
		};
	}
}