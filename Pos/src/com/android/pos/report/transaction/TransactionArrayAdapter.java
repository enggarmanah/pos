package com.android.pos.report.transaction;

import java.util.List;

import com.android.pos.R;
import com.android.pos.dao.Transactions;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TransactionArrayAdapter extends ArrayAdapter<Transactions> {

	private Context context;
	private List<Transactions> transactions;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onTransactionSelected(Transactions item);
		
		public Transactions getSelectedTransaction();
	}

	class ViewHolder {
		TextView transactionDateText;
		TextView totalAmountText;
	}

	public TransactionArrayAdapter(Context context, List<Transactions> transactions, ItemActionListener listener) {

		super(context, R.layout.report_transaction_list_item, transactions);
		
		this.context = context;
		this.transactions = transactions;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final Transactions transaction = transactions.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView transDate = null;
		TextView totalAmount = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.report_transaction_list_item, parent, false);
			
			transDate = (TextView) rowView.findViewById(R.id.transactionDateText);
			totalAmount = (TextView) rowView.findViewById(R.id.totalAmountText);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.transactionDateText = transDate;
			viewHolder.totalAmountText = totalAmount;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			transDate = viewHolder.transactionDateText;
			totalAmount = viewHolder.totalAmountText;
		}
		
		transDate.setText(CommonUtil.formatDateTime(transaction.getTransactionDate()));
		totalAmount.setText(CommonUtil.formatCurrency(transaction.getTotalAmount()));

		rowView.setOnClickListener(getItemOnClickListener(transaction, transDate));
		
		Transactions selectedTransaction = mCallback.getSelectedTransaction();
		
		if (selectedTransaction != null && selectedTransaction.getId() == transaction.getId()) {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_selected_background));
		} else {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));
		}

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final Transactions item, final TextView itemNameView) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.setSelected(true);

				mCallback.onTransactionSelected(item);
			}
		};
	}
}