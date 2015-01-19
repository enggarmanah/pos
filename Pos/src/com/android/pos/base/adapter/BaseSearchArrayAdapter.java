package com.android.pos.base.adapter;

import java.util.List;

import com.android.pos.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class BaseSearchArrayAdapter<T> extends ArrayAdapter<T> {

	private Context context;
	private List<T> items;
	private T selectedItem;
	private ItemActionListener<T> mCallback;

	private View selectedView;

	public interface ItemActionListener<T> {

		public void onItemDeleted(T item);

		public void onItemSelected(T item);
	}

	class ViewHolder {
		TextView itemText;
	}

	public BaseSearchArrayAdapter(Context context, List<T> items, T selectedItem, ItemActionListener<T> listener) {

		super(context, R.layout.app_list_item, items);
		
		this.context = context;
		this.items = items;
		this.selectedItem = selectedItem;
		this.mCallback = listener;
	}
	
	public abstract Long getItemId(T item);
	
	public abstract String getItemName(T item);
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final T item = items.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		TextView itemName = null;
		
		if (rowView == null) {

			rowView = inflater.inflate(R.layout.app_list_item, parent, false);
			itemName = (TextView) rowView.findViewById(R.id.nameTxt);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemText = itemName;

			rowView.setTag(viewHolder);

		} else {

			@SuppressWarnings("unchecked")
			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			itemName = viewHolder.itemText;
		}
		
		itemName.setText(getItemName(item));

		if (selectedItem != null && getItemId(selectedItem) == getItemId(item)) {
			
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_selected_background));
			itemName.setTextColor(context.getResources().getColor(R.color.list_row_selected_text));
			selectedView = rowView;
			
		} else {
			rowView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));
			itemName.setTextColor(context.getResources().getColor(R.color.list_row_normal_text));
		}
		
		//ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.deleteBtn);
		
		rowView.setOnClickListener(getItemOnClickListener(item, itemName));
		//deleteBtn.setOnClickListener(getDeleteBtnClickListener(item));

		return rowView;
	}
	
	private View.OnClickListener getItemOnClickListener(final T item, final TextView itemNameView) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				selectedItem = item;
				v.setSelected(true);

				if (selectedView != null) {

					TextView selectedText = (TextView) selectedView.findViewById(R.id.nameTxt);

					selectedView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));
					selectedText.setTextColor(context.getResources().getColor(R.color.list_row_normal_text));
				}

				selectedView = v;

				selectedView.setBackgroundColor(context.getResources().getColor(R.color.list_row_selected_background));
				itemNameView.setTextColor(context.getResources().getColor(R.color.list_row_selected_text));

				mCallback.onItemSelected(item);
			}
		};
	}
	
	private View.OnClickListener getDeleteBtnClickListener(final T item) {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlertDialog diaBox = confirmDelete(item);
				diaBox.show();
			}
		};
	}
	
	private String getString(int resourceId) {
		return context.getResources().getString(resourceId);
	}
	
	private AlertDialog confirmDelete(final T item) {

		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)

				.setTitle(getString(R.string.confirmation)).setMessage("Hapus " + getItemName(item) + " ?").setIcon(R.drawable.action_delete)

				.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {

						mCallback.onItemDeleted(item);
						items.remove(item);
						notifyDataSetChanged();
						dialog.dismiss();
					}

				})

				.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

					}
				}).create();

		return myQuittingDialogBox;
	}

	public void unSelectItem() {

		selectedItem = null;
		
		if (selectedView != null) {

			TextView selectedText = (TextView) selectedView.findViewById(R.id.nameTxt);

			selectedView.setBackgroundColor(context.getResources().getColor(R.color.list_row_normal_background));
			selectedText.setTextColor(context.getResources().getColor(R.color.list_row_normal_text));
		}
	}
}