package com.android.pos.cashier;

import java.util.List;

import com.android.pos.R;
import com.android.pos.dao.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CashierCustomerArrayAdapter extends ArrayAdapter<Customer> {

	private Context context;
	private List<Customer> customers;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onCustomerSelected(Customer item);
	}

	class ViewHolder {
		TextView itemText;
	}

	public CashierCustomerArrayAdapter(Context context, List<Customer> customers, ItemActionListener listener) {

		super(context, R.layout.cashier_customer_list_item, customers);
		
		this.context = context;
		this.customers = customers;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Customer customer = customers.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		TextView prdGroupName = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.cashier_customer_list_item, parent, false);
			prdGroupName = (TextView) rowView.findViewById(R.id.nameTxt);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemText = prdGroupName;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			prdGroupName = viewHolder.itemText;
		}
		
		prdGroupName.setText(customer.getName());

		rowView.setOnClickListener(getItemOnClickListener(customer, prdGroupName));

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