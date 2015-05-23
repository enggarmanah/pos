package com.android.pos.base.adapter;

import java.util.List;

import com.android.pos.Config;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.UserUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppMenuArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<String> menus;
	private ItemActionListener mCallback;

	public interface ItemActionListener {

		public String getSelectedMenu();
	}

	class ViewHolder {
		
		ImageView menuImage;
		TextView menuText;
		TextView countText;
	}

	public AppMenuArrayAdapter(Context context, List<String> menus, ItemActionListener listener) {

		super(context, R.layout.app_menu_item, menus);
		
		this.context = context;
		this.menus = menus;
		this.mCallback = listener;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final String menu = menus.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		
		ImageView menuImage = null;
		TextView menuText = null;
		TextView countText = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.app_menu_item, parent, false);
			
			menuImage = (ImageView) rowView.findViewById(R.id.menuImage);
			menuText = (TextView) rowView.findViewById(R.id.menuText);
			countText = (TextView) rowView.findViewById(R.id.countText);
			
			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.menuImage = menuImage;
			viewHolder.menuText = menuText;
			viewHolder.countText = countText;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			menuImage = viewHolder.menuImage;
			menuText = viewHolder.menuText;
			countText = viewHolder.countText;
		}
		
		boolean isSubMenu = false;
		
		if (Constant.MENU_USER.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_account_circle_black));
		} else if (Constant.MENU_CASHIER.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home_black));
		} else if (Constant.MENU_WAITRESS.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home_black));
		} else if (Constant.MENU_ORDER.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_content_paste_black));
		} else if (Constant.MENU_REPORT.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_assessment_black));
		} else if (Constant.MENU_FAVORITE.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_outline_black));
		} else if (Constant.MENU_DATA.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_view_list_black));
		} else if (Constant.MENU_SYNC.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cached_black));
		} else if (Constant.MENU_DATA_MANAGEMENT.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_turned_in_not_black));
		} else if (Constant.MENU_PRINTER.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_print_black));
		} else if (Constant.MENU_EXIT.equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_exit_to_app_black));
		} else {
			menuImage.setImageDrawable(null);
			isSubMenu = true;
		}
		
		if (isSubMenu) {
			menuText.setTextColor(context.getResources().getColor(R.color.text_medium));
		} else {
			menuText.setTextColor(context.getResources().getColor(R.color.text_dark));
		}
		
		if (Constant.MENU_USER.equals(menu)) {
			
			if (UserUtil.getUser() != null) {
				menuText.setText(UserUtil.getUser().getName());
			}
		} else {
			menuText.setText(menu);
		}
		
		if (Constant.MENU_REPORT.equals(menu)) {
			
			countText.setVisibility(View.VISIBLE);
			
			if (!Config.isMenuReportExpanded()) {
				
				Integer count = MerchantUtil.getBelowStockLimitProductCount() + MerchantUtil.getPastDueBillsCount();
				
				if (count > 99) {
					countText.setText("++");
				} else if (count > 0) {
					countText.setText(CommonUtil.formatNumber(count));
				} else {
					countText.setVisibility(View.GONE);
				}
			} else {
				countText.setVisibility(View.GONE);
			}
			
		} else if (Constant.MENU_REPORT_INVENTORY.equals(menu) ||
			Constant.MENU_REPORT_CASHFLOW.equals(menu)) {
			
			countText.setVisibility(View.VISIBLE);
			
			Integer count = 0;
			
			if (Constant.MENU_REPORT_INVENTORY.equals(menu)) {
				count = MerchantUtil.getBelowStockLimitProductCount();
			} else if (Constant.MENU_REPORT_CASHFLOW.equals(menu)) {
				count = MerchantUtil.getPastDueBillsCount();
			}
			
			if (count > 99) {
				countText.setText("++");
			} else if (count > 0) {
				countText.setText(CommonUtil.formatNumber(count));
			} else {
				countText.setVisibility(View.GONE);
			}
			
		} else {
			countText.setVisibility(View.GONE);
		}
		
		String selectedMenu = mCallback.getSelectedMenu();
		
		if (selectedMenu != null && selectedMenu.equals(menu)) {
			
			menuText.setTextColor(context.getResources().getColor(R.color.text_dark));
			rowView.setBackgroundColor(context.getResources().getColor(R.color.menu_selected));
			
		} else {
			
			if (isSubMenu) {
				menuText.setTextColor(context.getResources().getColor(R.color.text_darkmed));
				rowView.setBackgroundColor(context.getResources().getColor(R.color.menu_sub));
			} else {
				menuText.setTextColor(context.getResources().getColor(R.color.text_dark));
				rowView.setBackgroundColor(context.getResources().getColor(R.color.menu_normal));
			}
		}

		return rowView;
	}
}