package com.android.pos.order;

import java.io.Serializable;
import java.util.HashMap;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.cashier.CashierActivity;
import com.android.pos.cashier.CashierProductCountDlgFragment;
import com.android.pos.dao.Employee;
import com.android.pos.dao.OrderItem;
import com.android.pos.dao.OrderItemDaoService;
import com.android.pos.dao.Orders;
import com.android.pos.dao.Product;
import com.android.pos.util.DbUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OrderActivity extends BaseActivity 
	implements OrderActionListener, CashierProductCountDlgFragment.ProductActionListener {
	
	protected OrderListFragment mOrderListFragment;
	protected OrderDetailFragment mOrderDetailFragment;
	private CashierProductCountDlgFragment mProductCountDlgFragment;
	
	private String mOrderListFragmentTag = "OrderListFragmentTag";
	private String mOrderDetailFragmentTag = "OrderDetailFragmentTag";
	private String mProductCountDlgFragmentTag = "productCountDlgFragmentTag";
	
	private static final String SELECTED_ORDER_REFERENCE = "SELECTED_ORDER_REFERENCE";
	private static final String SELECTED_ORDER_ITEM = "SELECTED_ORDER_ITEM";
	
	boolean mIsMultiplesPane = false;
	
	private String mSelectedOrderReference;
	private OrderItem mSelectedOrderItem;
	
	private MenuItem mMenuPayment;
	
	@SuppressLint("UseSparseArrays")
	public HashMap<Long, Boolean> mSelectedOrders = new HashMap<Long, Boolean>();
	
	public boolean mIsOrderSelected = false;
	
	OrderItemDaoService mOrderItemDaoService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_transaction_activity);

		DbUtil.initDb(this);

		initDrawerMenu();
		
		initFragments();
		
		mOrderItemDaoService = new OrderItemDaoService();
		
		initWaitAfterFragmentRemovedTask(mOrderListFragmentTag, mOrderDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_order));
		setSelectedMenu(getString(R.string.menu_order));
		
		mIsOrderSelected = false;
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedOrderReference = (String) savedInstanceState.getSerializable(SELECTED_ORDER_REFERENCE);
			mSelectedOrderItem = (OrderItem) savedInstanceState.getSerializable(SELECTED_ORDER_ITEM);
		}
	}
	
	private void initFragments() {
		
		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (mProductCountDlgFragment == null) {
			mProductCountDlgFragment = new CashierProductCountDlgFragment();
		}

		mOrderListFragment = (OrderListFragment) getFragmentManager().findFragmentByTag(mOrderListFragmentTag);
		
		if (mOrderListFragment == null) {
			mOrderListFragment = new OrderListFragment();

		} else {
			removeFragment(mOrderListFragment);
		}
		
		mOrderDetailFragment = (OrderDetailFragment) getFragmentManager().findFragmentByTag(mOrderDetailFragmentTag);
		
		if (mOrderDetailFragment == null) {
			mOrderDetailFragment = new OrderDetailFragment();

		} else {
			removeFragment(mOrderDetailFragment);
		}
		
		mOrderDetailFragment.setSelectedOrders(mSelectedOrders);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_ORDER_REFERENCE, (Serializable) mSelectedOrderReference);
		outState.putSerializable(SELECTED_ORDER_ITEM, (Serializable) mSelectedOrderItem);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mOrderListFragment.setSelectedOrderReference(mSelectedOrderReference);
		mOrderDetailFragment.displayOrders(mSelectedOrderReference);
		
		if (mIsMultiplesPane) {

			addFragment(mOrderListFragment, mOrderListFragmentTag);
			addFragment(mOrderDetailFragment, mOrderDetailFragmentTag);
			
		} else {

			if (mSelectedOrderReference != null) {
				
				addFragment(mOrderDetailFragment, mOrderDetailFragmentTag);
				
			} else {
				
				addFragment(mOrderListFragment, mOrderListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.order_menu, menu);
		
		mMenuPayment = menu.findItem(R.id.menu_payment);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		displayMenus();
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	private void displayMenus() {
		
		mMenuPayment.setVisible(false);
		
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		if (!isDrawerOpen) {
		
			if (mSelectedOrderReference != null) {
				mMenuPayment.setVisible(true);
			}	
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
	
			case R.id.menu_payment:
				
				// make payment
				
				displayMenus();
				
				mIsOrderSelected = true;
				
				Intent intent = new Intent(getApplicationContext(), CashierActivity.class);
				intent.putExtra(Constant.SELECTED_ORDERS_FOR_PAYMENT, mSelectedOrders);
				startActivity(intent);
				
				return true;
					
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onOrderReferenceSelected(String orderReference) {
		
		mSelectedOrderReference = orderReference;
		
		displayMenus();
		
		mOrderDetailFragment.displayOrders(orderReference);
		
		if (mIsMultiplesPane) {
			
			mOrderListFragment.setSelectedOrderReference(orderReference);
			
		} else {
			
			replaceFragment(mOrderDetailFragment, mOrderDetailFragmentTag);
		}
	}
	
	@Override
	public void onBackButtonClicked() {
		
		mSelectedOrderReference = null;
		
		displayMenus();
		
		if (!mIsMultiplesPane) {
			
			mOrderListFragment.setSelectedOrderReference(mSelectedOrderReference);
			replaceFragment(mOrderListFragment, mOrderListFragmentTag);
		}
	}
	
	@Override
	public void onOrderItemSelected(OrderItem orderItem) {
		
		mSelectedOrderItem = orderItem;
		
		mProductCountDlgFragment.show(getFragmentManager(), mProductCountDlgFragmentTag);
		mProductCountDlgFragment.setProduct(orderItem.getProduct(), orderItem.getQuantity(), orderItem.getRemarks());
	}
	
	@Override
	public void onProductQuantitySelected(Product product, Employee personInCharge, int quantity, String remarks) {
		
		mSelectedOrderItem.setQuantity(quantity);
		mSelectedOrderItem.setRemarks(remarks);
		mOrderItemDaoService.updateOrderItem(mSelectedOrderItem);
		mOrderDetailFragment.displayOrders(mSelectedOrderReference);
	}
	
	@Override
	public void onAddNewOrder(Orders order) {
		
		Intent intent = new Intent(getApplicationContext(), CashierActivity.class);
		intent.putExtra(Constant.SELECTED_ORDERS_FOR_NEW_ITEM, order);
		startActivity(intent);
	}
}