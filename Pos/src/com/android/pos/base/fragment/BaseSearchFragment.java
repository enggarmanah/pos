package com.android.pos.base.fragment;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.adapter.BaseSearchArrayAdapter;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.base.listener.BaseItemListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

public abstract class BaseSearchFragment<T> extends BaseFragment 
	implements BaseSearchArrayAdapter.ItemActionListener<T> {
	
	protected ImageButton mAddButton;
	
	protected BaseSearchArrayAdapter<T> mAdapter;
	protected BaseItemListener<T> mItemListener;

	protected ListView mSearchResultList;

	protected List<T> mItems;
	protected T mSelectedItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.app_item_search_fragment, container, false);

		if (mItems != null && mItems.size() == 0) {
			mItems.addAll(getItems(Constant.EMPTY_STRING));
		}
		
		initAdapter();
		
		return view;
	}
	
	public abstract void initAdapter();
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mAddButton = (ImageButton) getView().findViewById(R.id.addButton);
		mAddButton.setOnClickListener(getAddButtonOnClickListener());
		
		mSearchResultList = (ListView) getView().findViewById(R.id.searchResultList);

		mSearchResultList.setAdapter(mAdapter);
		mSearchResultList.setItemsCanFocus(true);
		mSearchResultList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		initWaitAfterStartTask();
	}
	
	private View.OnClickListener getAddButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mItemListener.addItem();
			}
		};
	}
	
	@Override
	public void afterStart() {
		
		if (getSelectedItemIndex() > Constant.LIST_SELECTED_ITEM_GAP) {
			mSearchResultList.setSelection(getSelectedItemIndex() + Constant.LIST_SELECTED_ITEM_GAP);
		}
	}
	
	protected abstract Long getItemId(T item);
	
	private int getSelectedItemIndex() {
		
		int index = -1;
		
		if (mSelectedItem != null && mItems != null) {
			
			index = 0;
			
			for (T item : mItems) {
				
				if (getItemId(item) == getItemId(mSelectedItem)) {
					break; 
				}
				
				index++;
			}
		}
		
		return index;
	}
	
	public void searchItem(String query) {
		
		if (!isViewInitialized()) {
			mSelectedItem = null;
			return;
		}
		
		unSelectItem();
		
		mItems.clear();
		mItems.addAll(getItems(query));
		
		mAdapter.notifyDataSetChanged();
	}
	
	public abstract List<T> getItems(String query);

	public abstract void onItemDeleted(T item);

	public void onItemSelected(T item) {

		mSelectedItem = item;
		
		mItemListener.updateItem(item);
	}
	
	public T getSelectedItem() {
		
		return mSelectedItem;
	}
	
	public void setSelectedItem(T item) {
		
		mSelectedItem = item;
	}
	
	public List<T> getItems() {
		
		return mItems;
	}
	
	public void setItems(List<T> items) {
		
		mItems = items;
	}
	
	public void unSelectItem() {
		
		mSelectedItem = null;
		
		mAdapter.unSelectItem();
		mItemListener.onItemUnselected();
	}
	
	public void refreshItems() {
		
		if (isViewInitialized()) {
			mAdapter.notifyDataSetChanged();
		}
	}
	
	public void reloadItems() {
		
		searchItem(Constant.EMPTY_STRING);
	}
}