package com.android.pos.base.listener;

public interface BaseItemListener<T> {
	
	public void displaySearch();
	
	public void addItem();
	
	public void updateItem(T item);
	
	public void deleteItem(T item);
	
	public void onAddCompleted();
	
	public void onUpdateCompleted();
	
	public void onItemUnselected();
	
	public void onDeleteCompleted();
	
	public void onSelectProduct(boolean isMandatory);
	
	public void onSelectSupplier(boolean isMandatory);
	
	public void onSelectBill(boolean isMandatory);
}
