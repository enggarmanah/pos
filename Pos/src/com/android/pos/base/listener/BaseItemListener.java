package com.android.pos.base.listener;

import java.util.List;

public interface BaseItemListener<T> {
	
	public void displaySearch();
	
	public void updateItem(T product);
	
	public void addItem(T product);
	
	public void onAddCompleted();
	
	public void onUpdateCompleted();
	
	public void onLoadItems(List<T> items);
	
	public void onItemUnselected();
}
