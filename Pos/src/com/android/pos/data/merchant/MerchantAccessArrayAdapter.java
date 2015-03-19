package com.android.pos.data.merchant;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.dao.MerchantAccess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class MerchantAccessArrayAdapter extends ArrayAdapter<MerchantAccess> {

	private Context context;
	private List<MerchantAccess> merchantAccesses;

	class ViewHolder {
		TextView menuText;
		ImageButton checkedButton;
	}

	public MerchantAccessArrayAdapter(Context context, List<MerchantAccess> merchantAccesses) {

		super(context, R.layout.data_merchant_access_list_item, merchantAccesses);
		
		this.context = context;
		this.merchantAccesses = merchantAccesses;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final MerchantAccess merchantAccess = merchantAccesses.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		TextView menuText = null;
		ImageButton checkedButton = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.data_merchant_access_list_item, parent, false);
			
			menuText = (TextView) rowView.findViewById(R.id.menuText);
			checkedButton = (ImageButton) rowView.findViewById(R.id.checkedButton);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.menuText = menuText;
			viewHolder.checkedButton = checkedButton;
			
			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			menuText = viewHolder.menuText;
			checkedButton = viewHolder.checkedButton;
		}
		
		boolean isSelected = Constant.STATUS_YES.equals(merchantAccess.getStatus()); 
		
		if (isSelected) {
			checkedButton.setImageDrawable(context.getResources().getDrawable(R.drawable.action_checked_black));
		} else {
			checkedButton.setImageDrawable(context.getResources().getDrawable(R.drawable.action_cancel_black));
		}
		
		final ImageButton button = checkedButton;
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				boolean isSelected = Constant.STATUS_YES.equals(merchantAccess.getStatus()); 
				
				if (isSelected) {
					button.setImageDrawable(context.getResources().getDrawable(R.drawable.action_checked_black));
					merchantAccess.setStatus(Constant.STATUS_YES);
				} else {
					button.setImageDrawable(context.getResources().getDrawable(R.drawable.action_cancel_black));
					merchantAccess.setStatus(Constant.STATUS_NO);
				}
			}
		});

		return rowView;
	}
}