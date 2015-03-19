package com.android.pos.popup.search;

import java.util.List;

import com.android.pos.R;
import com.android.pos.dao.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomerArrayAdapter extends ArrayAdapter<Customer> {

	private Context context;
	private List<Customer> customers;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onCustomerSelected(Customer item);
	}

	class ViewHolder {
		TextView itemText;
	}

	public CustomerArrayAdapter(Context context, List<Customer> customers, ItemActionListener listener) {

		super(context, R.layout.popup_customer_list_item, customers);
		
		this.context = context;
		this.customers = customers;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Customer customer = customers.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		TextView customerName = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.popup_customer_list_item, parent, false);
			customerName = (TextView) rowView.findViewById(R.id.nameText);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemText = customerName;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			customerName = viewHolder.itemText;
		}
		
		customerName.setText(customer.getName());

		rowView.setOnClickListener(getItemOnClickListener(customer, customerName));

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final Customer customer, final TextView itemNameView) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.setSelected(true);

				mCallback.onCustomerSelected(customer);
			}
		};
	}
}