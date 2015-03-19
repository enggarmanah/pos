package com.android.pos.base.adapter;

import java.util.List;

import com.android.pos.R;
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
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.app_menu_item, parent, false);
			
			menuImage = (ImageView) rowView.findViewById(R.id.menuImage);
			menuText = (TextView) rowView.findViewById(R.id.menuText);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.menuImage = menuImage;
			viewHolder.menuText = menuText;

			rowView.setTag(viewHolder);

		} else {

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			menuImage = viewHolder.menuImage;
			menuText = viewHolder.menuText;
		}
		
		if ("User".equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.action_user));
		} else if ("Kasir".equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.action_home_black));
		} else if ("Laporan".equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.action_folder));
		} else if ("Data".equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.action_folder));
		} else if ("Keluar".equals(menu)) {
			menuImage.setImageDrawable(context.getResources().getDrawable(R.drawable.action_exit_black));
		}
		
		if ("User".equals(menu)) {
			menuText.setText(UserUtil.getUser().getName());
		} else {
			menuText.setText(menu);
		}
		
		String selectedMenu = mCallback.getSelectedMenu();
		
		if (selectedMenu != null && selectedMenu.equals(menu)) {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.menu_selected));
		} else {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.menu_normal));
		}

		return rowView;
	}
}