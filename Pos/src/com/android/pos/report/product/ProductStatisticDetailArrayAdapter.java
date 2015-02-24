package com.android.pos.report.product;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.ProductStatistic;
import com.android.pos.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductStatisticDetailArrayAdapter extends ArrayAdapter<ProductStatistic> {

	private Context context;
	private List<ProductStatistic> productStatistics;
	private ItemActionListener mCallback;
	
	public interface ItemActionListener {

		public String getProductInfo();
	}

	class ViewHolder {
		TextView productNameText;
		TextView saleCountText;
	}

	public ProductStatisticDetailArrayAdapter(Context context, List<ProductStatistic> productStatistics, ItemActionListener listener) {

		super(context, R.layout.report_product_statistic_list_item, productStatistics);
		
		this.context = context;
		this.productStatistics = productStatistics;
		mCallback = (ItemActionListener) listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ProductStatistic productStatistic = productStatistics.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView productName = null;
		TextView saleCount = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.report_product_statistic_list_item, parent, false);
			
			productName = (TextView) rowView.findViewById(R.id.productNameText);
			saleCount = (TextView) rowView.findViewById(R.id.saleCountText);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.productNameText = productName;
			viewHolder.saleCountText = saleCount;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			productName = viewHolder.productNameText;
			saleCount = viewHolder.saleCountText;
		}
		
		productName.setText(productStatistic.getProduct_name());
		
		if (Constant.PRODUCT_QUANTITY.equals(mCallback.getProductInfo())) {
			saleCount.setText(CommonUtil.formatCurrencyUnsigned(productStatistic.getValue()));
		} else {
			saleCount.setText(CommonUtil.formatCurrency(productStatistic.getValue()));
		}

		rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));

		return rowView;
	}
}