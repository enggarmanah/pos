package com.android.pos.report.commision;

import java.util.List;

import com.android.pos.R;
import com.android.pos.model.CommisionYearBean;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommisionYearArrayAdapter extends ArrayAdapter<CommisionYearBean> {

	private Context context;
	private List<CommisionYearBean> commisionYears;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public void onCommisionYearSelected(CommisionYearBean item);
		
		public CommisionYearBean getSelectedCommisionYear();
	}

	class ViewHolder {
		TextView commisionDateText;
		TextView commisionAmountText;
	}

	public CommisionYearArrayAdapter(Context context, List<CommisionYearBean> commisionYears, ItemActionListener listener) {

		super(context, R.layout.report_commision_list_item, commisionYears);
		
		this.context = context;
		this.commisionYears = commisionYears;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final CommisionYearBean commisionYear = commisionYears.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView commisionDate = null;
		TextView commisionAmount = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.report_commision_list_item, parent, false);
			
			commisionDate = (TextView) rowView.findViewById(R.id.commisionDateText);
			commisionAmount = (TextView) rowView.findViewById(R.id.commisionAmountText);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.commisionDateText = commisionDate;
			viewHolder.commisionAmountText = commisionAmount;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			commisionDate = viewHolder.commisionDateText;
			commisionAmount = viewHolder.commisionAmountText;
		}
		
		commisionDate.setText(CommonUtil.formatYear(commisionYear.getYear()));
		commisionAmount.setText(CommonUtil.formatCurrency(commisionYear.getAmount()));
		
		rowView.setOnClickListener(getItemOnClickListener(commisionYear, commisionDate));
		
		CommisionYearBean selectedCommisionYear = mCallback.getSelectedCommisionYear();
		
		if (selectedCommisionYear != null && selectedCommisionYear.getYear() == commisionYear.getYear()) {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_selected_background));
		} else {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));
		}

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final CommisionYearBean item, final TextView itemNameView) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.setSelected(true);

				mCallback.onCommisionYearSelected(item);
			}
		};
	}
}