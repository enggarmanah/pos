package com.android.pos.transaction;

import java.util.List;

import com.android.pos.CommonUtil;
import com.android.pos.R;
import com.android.pos.dao.TransactionSummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TransactionSummaryArrayAdapter extends ArrayAdapter<TransactionSummary> {

	private Context context;
	private List<TransactionSummary> transactionSummaries;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onTransactionSummarySelected(TransactionSummary item);
		
		public TransactionSummary getSelectedTransactionSummary();
	}

	class ViewHolder {
		TextView transactionDateText;
		TextView totalAmountText;
	}

	public TransactionSummaryArrayAdapter(Context context, List<TransactionSummary> transactions, ItemActionListener listener) {

		super(context, R.layout.transaction_summary_list_item, transactions);
		
		this.context = context;
		this.transactionSummaries = transactions;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final TransactionSummary transaction = transactionSummaries.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView transDate = null;
		TextView totalAmount = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.transaction_summary_list_item, parent, false);
			
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
		
		transDate.setText(CommonUtil.formatDate(transaction.getDate()));
		totalAmount.setText(CommonUtil.formatCurrency(transaction.getAmount()));

		rowView.setOnClickListener(getItemOnClickListener(transaction, transDate));
		
		TransactionSummary selectedTransactionSummary = mCallback.getSelectedTransactionSummary();
		
		if (selectedTransactionSummary != null && selectedTransactionSummary.getDate() == transaction.getDate()) {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_selected_background));
		} else {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));
		}

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final TransactionSummary item, final TextView itemNameView) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.setSelected(true);

				mCallback.onTransactionSummarySelected(item);
			}
		};
	}
}