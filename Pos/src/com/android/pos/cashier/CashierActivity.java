package com.android.pos.cashier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.common.ConfirmListener;
import com.android.pos.dao.Customer;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Employee;
import com.android.pos.dao.Inventory;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.dao.OrderItem;
import com.android.pos.dao.OrderItemDaoService;
import com.android.pos.dao.Orders;
import com.android.pos.dao.OrdersDaoService;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.TransactionItemDaoService;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionsDaoService;
import com.android.pos.popup.search.CustomerDlgFragment;
import com.android.pos.popup.search.CustomerSelectionListener;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.ConfirmationUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.PrintUtil;
import com.android.pos.util.UserUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class CashierActivity extends BaseActivity 
	implements CashierActionListener, SearchView.OnQueryTextListener, ConfirmListener,
		CashierProductCountDlgFragment.ProductActionListener, CustomerSelectionListener {
	
	LinearLayout messagePanel;
	TextView messageText;

	private CashierProductSearchFragment mProductSearchFragment;
	private CashierOrderFragment mOrderFragment;
	private CashierOrderDlgFragment mOrderDlgFragment;
	private CashierProductCountDlgFragment mProductCountDlgFragment;
	private CashierPaymentDlgFragment mPaymentDlgFragment;
	private CashierOrderSummaryDlgFragment mOrderSummaryDlgFragment;
	private CashierPaymentSummaryDlgFragment mPaymentSummaryDlgFragment;
	private CashierDiscountDlgFragment mDiscountDlgFragment;
	private CashierDiscountAmountDlgFragment mDiscountAmountDlgFragment;
	private CustomerDlgFragment mCustomerDlgFragment;
	
	boolean mIsMultiplesPane = false;
	boolean mIsEnableSearch = true;
	
	SearchView mSearchView;
	
	MenuItem mSearchItem;
	MenuItem mListItem;
	MenuItem mSelectPrinterItem;
	
	Product mSelectedProduct;
	List<TransactionItem> mTransactionItems;
	String state;
	
	HashMap<Long, Boolean> mSelectedOrders;
	Orders mSelectedOrder;
	
	Discount mDiscount;

	private static final String TRANSACTION_ITEMS = "TRANSACTION_ITEMS";
	private static final String DISCOUNT = "DISCOUNT";
	private static final String STATE = "STATE";
	private static final String SELECTED_ORDER = "SELECTED_ORDER";
	
	//private static String ORDER = "ORDER";
	//private static String ORDER_ITEM = "ORDER_ITEMS";

	private String mProductSearchFragmentTag = "productSearchFragmentTag";
	private String mProductCountDlgFragmentTag = "productCountDlgFragmentTag";
	private String mOrderFragmentTag = "orderFragment";
	private String mPaymentDlgFragmentTag = "paymentDlgFragmentTag";
	private String mOrderDlgFragmentTag = "orderDlgFragmentTag";
	private String mPaymentSummaryDlgFragmentTag = "paymentSummaryDlgFragmentTag";
	private String mOrderSummaryDlgFragmentTag = "orderSummaryDlgFragmentTag";
	private String mDiscountDlgFragmentTag = "discountDlgFragmentTag";
	private String mDiscountAmountDlgFragmentTag = "discountAmountDlgFragmentTag";
	private String mCustomerDlgFragmentTag = "customerDlgFragmentTag";

	private String prevQuery = Constant.EMPTY_STRING;
	
	private TransactionsDaoService mTransactionDaoService;
	private TransactionItemDaoService mTransactionItemDaoService;
	private ProductDaoService mProductDaoService;
	private InventoryDaoService mInventoryDaoService;
	
	private OrdersDaoService mOrderDaoService;
	private OrderItemDaoService mOrderItemDaoService;
	
	private static boolean isTryToConnect = false;
	
	private Orders mOrder;
	private List<OrderItem> mOrderItems;
	
	private String mOrderType;
	private String mOrderReference;
	
	private String mState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cashier_activity);
		
		mState = Constant.CASHIER_STATE_CASHIER;

		DbUtil.initDb(this);
		
		mTransactionDaoService = new TransactionsDaoService();
		mTransactionItemDaoService = new TransactionItemDaoService();
		mProductDaoService = new ProductDaoService();
		mInventoryDaoService = new InventoryDaoService();
		
		mOrderDaoService = new OrdersDaoService();
		mOrderItemDaoService = new OrderItemDaoService();
		
		initDrawerMenu();

		initFragments(savedInstanceState);
		
		initWaitAfterFragmentRemovedTask(mProductSearchFragmentTag, mOrderFragmentTag);
		
		messagePanel = (LinearLayout) findViewById(R.id.messagePanel);
		messageText = (TextView) findViewById(R.id.messageText);
		
		messagePanel.setVisibility(View.GONE);
		
		messageText.setOnClickListener(getMessageTextOnClickListener());
		
		if (!isTryToConnect) {
			isTryToConnect = true;
			connectToMerchantPrinter();
		}
		
		loadOrders();
	}
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.menu_cashier));
		setSelectedMenu(getString(R.string.menu_cashier));
		
		if (!PrintUtil.isPrinterConnected()) {
			setMessage(Constant.MESSAGE_PRINTER_PLEASE_CHECK_PRINTER);
		}
		
		mOrderFragment.setCashierState(mState);
	}
	
	private void connectToMerchantPrinter() {
		
		Merchant merchant = MerchantUtil.getMerchant();
		String printerType = merchant.getPrinterType();
		String printerAddress = merchant.getPrinterAddress();
		
		if (PrintUtil.initBluetooth(this)) {
			
			if (!CommonUtil.isEmpty(printerAddress)) {
				
				setMessage(Constant.MESSAGE_PRINTER_CONNECTED_TO + printerAddress);
				connectToPrinter(printerType, printerAddress);
			
			} else {
				
				setMessage(Constant.MESSAGE_PRINTER_PLEASE_CHECK_PRINTER);
				PrintUtil.selectBluetoothPrinter();
			}
		}
	}
	
	public void setMessage(String message) {
		
		messagePanel.setVisibility(View.VISIBLE);
		messageText.setText(message);
	}
	
	public void clearMessage() {
		
		messageText.setText(Constant.EMPTY_STRING);
		messagePanel.setVisibility(View.GONE);
	}

	@SuppressWarnings("unchecked")
	private void initFragments(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			
			mTransactionItems = (List<TransactionItem>) savedInstanceState.getSerializable(TRANSACTION_ITEMS);
			mDiscount = (Discount) savedInstanceState.getSerializable(DISCOUNT);
			mSelectedOrder = (Orders) savedInstanceState.getSerializable(SELECTED_ORDER);
			mState = (String) savedInstanceState.getSerializable(STATE);
		}

		if (mTransactionItems == null) {
			
			mTransactionItems = new ArrayList<TransactionItem>();
		}

		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mProductSearchFragment = (CashierProductSearchFragment) getFragmentManager().findFragmentByTag(mProductSearchFragmentTag);
		mOrderFragment = (CashierOrderFragment) getFragmentManager().findFragmentByTag(mOrderFragmentTag);

		if (mProductSearchFragment == null) {
			mProductSearchFragment = new CashierProductSearchFragment();

		} else {
			removeFragment(mProductSearchFragment);
		}

		if (mOrderFragment == null) {
			mOrderFragment = new CashierOrderFragment();

		} else {
			removeFragment(mOrderFragment);
		}

		mOrderFragment.setTransactionItems(mTransactionItems);
		mOrderFragment.setDiscount(mDiscount);
		
		if (mProductCountDlgFragment == null) {
			mProductCountDlgFragment = new CashierProductCountDlgFragment();
		}

		if (mPaymentDlgFragment == null) {
			mPaymentDlgFragment = new CashierPaymentDlgFragment();
		}
		
		if (mOrderDlgFragment == null) {
			mOrderDlgFragment = new CashierOrderDlgFragment();
		}

		if (mPaymentSummaryDlgFragment == null) {
			mPaymentSummaryDlgFragment = new CashierPaymentSummaryDlgFragment();
		}
		
		if (mOrderSummaryDlgFragment == null) {
			mOrderSummaryDlgFragment = new CashierOrderSummaryDlgFragment();
		}
		
		if (mDiscountDlgFragment == null) {
			mDiscountDlgFragment = new CashierDiscountDlgFragment();
		}
		
		if (mDiscountAmountDlgFragment == null) {
			mDiscountAmountDlgFragment = new CashierDiscountAmountDlgFragment();
		}
		
		if (mCustomerDlgFragment == null) {
			mCustomerDlgFragment = new CustomerDlgFragment();
		}
	}

	@Override
	protected void afterFragmentRemoved() {
		
		System.out.println("afterFragmentRemoved");
		
		loadFragments();
	}
	
	private void loadFragments() {

		if (mIsMultiplesPane) {

			addFragment(mProductSearchFragment, mProductSearchFragmentTag);
			addFragment(mOrderFragment, mOrderFragmentTag);
			
		} else {

			if (mSelectedProduct != null) {
				addFragment(mProductSearchFragment, mProductSearchFragmentTag);
				
			} else {
				addFragment(mOrderFragment, mOrderFragmentTag);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		outState.putSerializable(TRANSACTION_ITEMS, (Serializable) mTransactionItems);
		outState.putSerializable(DISCOUNT, (Serializable) mDiscount);
		outState.putSerializable(SELECTED_ORDER, mSelectedOrder);
		outState.putSerializable(STATE, mState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cashier_menu, menu);

		mSearchItem = menu.findItem(R.id.menu_item_search);
		mListItem = menu.findItem(R.id.menu_item_list);
		mSelectPrinterItem = menu.findItem(R.id.menu_item_select_printer);

		mSearchView = (SearchView) mSearchItem.getActionView();
		mSearchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.START));

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
		if (null != searchManager) {
			mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		}

		mSearchView.setIconifiedByDefault(false);
		mSearchView.setOnQueryTextListener(this);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		mSearchItem.setVisible(!isDrawerOpen);
		mListItem.setVisible(!isDrawerOpen);
		
		mSelectPrinterItem.setVisible(false);
		
		if (!isDrawerOpen && !PrintUtil.isPrinterConnected()) {
			mSelectPrinterItem.setVisible(true);
		}

		return super.onPrepareOptionsMenu(menu);
	}
	
	public void setSelectPrinterVisible(boolean isVisible) {
		
		if (mSelectPrinterItem != null) {
			mSelectPrinterItem.setVisible(isVisible);
		}
	}
	
	protected void doSearch(String query) {
		
		if (mIsMultiplesPane) {
			
			mProductSearchFragment.searchProducts(query);
			
		} else {
			
			replaceFragment(mProductSearchFragment, mProductSearchFragmentTag);
			mProductSearchFragment.searchProducts(query);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		synchronized (CommonUtil.LOCK) {
		
			switch (item.getItemId()) {
	
			case R.id.menu_item_search:
	
				doSearch(Constant.EMPTY_STRING);
	
				showMessage(R.string.msg_notification_search_action);
	
				return true;
				
			case R.id.menu_item_select_printer:
				
				PrintUtil.selectBluetoothPrinter();
	
				return true;
	
			case R.id.menu_item_list:
				
				hideSearchView();
				
				if (mIsMultiplesPane) {
					
					mProductSearchFragment.showProductGroups();
					
				} else {
					
					Fragment f = getFragmentManager().findFragmentByTag(mProductSearchFragmentTag);
					
					if (f != null) {
						replaceFragment(mOrderFragment, mOrderFragmentTag);
					} else {
						replaceFragment(mProductSearchFragment, mProductSearchFragmentTag);
						mProductSearchFragment.showProductGroups();
					}
				}
	
				return true;
	
			default:
				return super.onOptionsItemSelected(item);
			}
		}
	}

	public boolean onQueryTextChange(String query) {

		boolean isQuerySimilar = query.equals(prevQuery);

		prevQuery = query;

		if (mIsEnableSearch && !isQuerySimilar) {

			doSearch(query);
		}

		return true;
	}

	public boolean onQueryTextSubmit(String query) {

		doSearch(query);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

		return true;
	}

	public void displaySearch() {

		if (!mIsMultiplesPane) {
			replaceFragment(mProductSearchFragment, mProductSearchFragmentTag);
		}
	}
	
	public void hideSearchView() {
		
		mIsEnableSearch = false;
		mSearchItem.collapseActionView();
		mIsEnableSearch = true;
	}
	
	@Override
	public void onClearTransaction() {
		
		mSelectedProduct = null;
		mSelectedOrder = null;
		mDiscount = null;
		
		mTransactionItems.clear();
		
		mState = Constant.CASHIER_STATE_CASHIER;
		
		mOrderType = null;
		mOrderReference = null;
		
		mProductSearchFragment.showProductGroups();
		
		mOrderFragment.setTransactionItems(mTransactionItems);
		mOrderFragment.setDiscount(mDiscount);
		mOrderFragment.setCashierState(mState);
		mOrderFragment.setSelectedOrders(mSelectedOrder);
	}
	
	public View.OnClickListener getMessageTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (!PrintUtil.isBluetoothEnabled()) {
					
					NotificationUtil.setAlertMessage(getFragmentManager(), "Bluetooth tidak aktif, aktifkan bluetooth terlebih dahulu.");
	    			
	    			return;
				}
				
				PrintUtil.selectBluetoothPrinter();
			}
		};
	}
	
	public TransactionItem getTransactionItem(Product product, Employee personInCharge, int quantity, String remarks) {

		TransactionItem transItem = new TransactionItem();

		transItem.setQuantity(quantity);
		transItem.setProduct(product);
		transItem.setProductId(product.getId());
		transItem.setProductName(product.getName());
		transItem.setProductType(product.getType());
		
		if (personInCharge != null) {
			transItem.setEmployeeId(personInCharge.getId());
			transItem.setEmployee(personInCharge);
		}
		
		int costPrice = product.getCostPrice() == null ? product.getPrice() : product.getCostPrice();
		int price = product.getPrice();
		
		if (product.getPromoStart() != null && product.getPromoEnd() != null && product.getPromoPrice() != null) {
			
			Date curDate = new Date();
			
			if (product.getPromoStart().getTime() <= curDate.getTime() &&
				curDate.getTime() <= product.getPromoEnd().getTime()) {
				
				price = product.getPromoPrice();
			}
		}
		
		transItem.setPrice(price);
		transItem.setCostPrice(costPrice);
		
		int discountAmount = 0;
		
		if (mDiscount != null) {
			
			int discountPercentage = mDiscount.getPercentage();
			discountAmount = discountPercentage * transItem.getPrice() / 100;
		}
		
		transItem.setDiscount(discountAmount);
		transItem.setRemarks(remarks);
		
		return transItem;
	}
	
	@Override
	public void onShowProductGroups() {
		
		hideSearchView();
		mProductSearchFragment.showProductGroups();
	}
	
	@Override
	public void onProductSelected(Product product) {
		
		if (!PrintUtil.isPrinterConnected()) {
			setMessage(Constant.MESSAGE_PRINTER_PLEASE_CHECK_PRINTER);
			setSelectPrinterVisible(true);
		}
		
		onProductSelected(product, 0, Constant.EMPTY_STRING);
	}

	@Override
	public void onProductSelected(Product product, int quantity, String remarks) {

		mSelectedProduct = product;

		mProductCountDlgFragment.show(getFragmentManager(), mProductCountDlgFragmentTag);
		mProductCountDlgFragment.setProduct(product, quantity, remarks);
		
		hideSearchView();
	}

	@Override
	public void onProductQuantitySelected(Product product, Employee personInCharge, int quantity, String remarks) {

		mSelectedProduct = null;

		TransactionItem transItem = getTransactionItem(product, personInCharge, quantity, remarks);
		
		if (mIsMultiplesPane) {
			mOrderFragment.addTransactionItem(transItem);
		} else {
			replaceFragment(mOrderFragment, mOrderFragmentTag);
			mOrderFragment.addTransactionItem(transItem);
		}
	}

	@Override
	public void onPaymentRequested(int totalBill) {
		
		mPaymentDlgFragment.show(getFragmentManager(), mPaymentDlgFragmentTag);
		mPaymentDlgFragment.setTotalBill(totalBill);
	}
	
	@Override
	public void onOrderRequested(int totalOrder) {

		mOrderDlgFragment.show(getFragmentManager(), mOrderDlgFragmentTag);
		mOrderDlgFragment.setTotalOrder(totalOrder);
	}

	@Override
	public void onPaymentInfoProvided(Customer customer, String paymentType, int totalBill, int payment) {

		mPaymentSummaryDlgFragment.show(getFragmentManager(), mPaymentSummaryDlgFragmentTag);
		mPaymentSummaryDlgFragment.setPaymentInfo(customer, paymentType, totalBill, payment);
	}
	
	@Override
	public void onOrderInfoProvided(String orderReference, String orderType) {
		
		mOrderSummaryDlgFragment.show(getFragmentManager(), mOrderSummaryDlgFragmentTag);
		mOrderSummaryDlgFragment.setOrderInfo(orderReference, orderType);
	}
	
	@Override
	public void onPaymentCompleted(Transactions transaction) {
		
		transaction.setOrderType(mOrderType);
		transaction.setOrderReference(mOrderReference);
		
		int totalBill = 0;
		
		for (TransactionItem transactionItem : mTransactionItems) {
			totalBill += transactionItem.getQuantity() * transactionItem.getPrice();
		}
		
		transaction.setBillAmount(totalBill);
		
		int discountAmount = 0;
		
		if (mDiscount != null) {
			
			discountAmount = mDiscount.getPercentage() * totalBill / 100;
			
			transaction.setDiscountName(mDiscount.getName());
			transaction.setDiscountPercentage(mDiscount.getPercentage());
			transaction.setDiscountAmount(discountAmount);
		}
		
		totalBill = totalBill - discountAmount;
		
		Merchant merchant = MerchantUtil.getMerchant();
		
		int taxAmount = merchant.getTaxPercentage() * totalBill / 100;
		
		transaction.setTaxPercentage(merchant.getTaxPercentage());
		transaction.setTaxAmount(taxAmount);
		
		int serviceChargeAmount = merchant.getServiceChargePercentage() * totalBill / 100;
		
		transaction.setServiceChargePercentage(merchant.getServiceChargePercentage());
		transaction.setServiceChargeAmount(serviceChargeAmount);
		
		transaction.setMerchant(MerchantUtil.getMerchant());
		transaction.setUploadStatus(Constant.STATUS_YES);
		
		mTransactionDaoService.addTransactions(transaction);
		
		for (TransactionItem transactionItem : mTransactionItems) {
			
			transactionItem.setTransactionId(transaction.getId());
			transactionItem.setMerchant(MerchantUtil.getMerchant());
			transactionItem.setUploadStatus(Constant.STATUS_YES);
			
			if (transactionItem.getQuantity() != 0) {
				mTransactionItemDaoService.addTransactionItem(transactionItem);
			}
			
			// track the product movement in inventory
			Inventory inventory = new Inventory();
			inventory.setMerchant(MerchantUtil.getMerchant());
			inventory.setBillReferenceNo(transaction.getTransactionNo());
			inventory.setProduct(transactionItem.getProduct());
			inventory.setProductName(transactionItem.getProductName());
			inventory.setQuantityStr(String.valueOf(transactionItem.getQuantity()));
			inventory.setQuantity(-transactionItem.getQuantity());
			inventory.setProductCostPrice(transactionItem.getCostPrice());
			inventory.setDeliveryDate(new Date());
			inventory.setStatus(Constant.INVENTORY_STATUS_SALE);
			inventory.setUploadStatus(Constant.STATUS_YES);
			inventory.setCreateBy(UserUtil.getUser().getUserId());
			inventory.setCreateDate(new Date());
			inventory.setUpdateBy(UserUtil.getUser().getUserId());
			inventory.setUpdateDate(new Date());
			
			if (transactionItem.getQuantity() != 0) {
				mInventoryDaoService.addInventory(inventory);
			}
		}
		
		// clear orders
		if (mSelectedOrders != null) {
			
			for (Long orderId : mSelectedOrders.keySet()) {
				
				Orders order = mOrderDaoService.getOrders(orderId);
				
				for (OrderItem orderItem : order.getOrderItemList()) {
					
					mOrderItemDaoService.deleteOrderItem(orderItem);
				}
				
				mOrderDaoService.deleteOrders(order);
			}
			
			mSelectedOrders.clear();
		}
	}
	
	@Override
	public void onOrderConfirmed(Orders order) {
		
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		for (TransactionItem transactionItem : mTransactionItems) {
			
			OrderItem orderItem = new OrderItem();
			
			orderItem.setMerchantId(MerchantUtil.getMerchantId());
			orderItem.setProductId(transactionItem.getProductId());
			orderItem.setProductName(transactionItem.getProductName());
			orderItem.setQuantity(transactionItem.getQuantity());
			orderItem.setRemarks(transactionItem.getRemarks());
			
			orderItems.add(orderItem);
		}
		
		mOrder = order;
		mOrderItems = orderItems;
		
		mOrderDaoService.addOrders(order);
		
		for (OrderItem orderItem : mOrderItems) {
			
			orderItem.setOrderId(order.getId());
			mOrderItemDaoService.addOrderItem(orderItem);
		}
	}

	@Override
	public void onPrintReceipt(Transactions transaction) {
		
		transaction = mTransactionDaoService.getTransactions(transaction.getId());
		
		try {
			PrintUtil.print(transaction);
		} catch (Exception e) {
			showMessage(Constant.MESSAGE_PRINTER_CANT_PRINT);
		}
	}
	
	@Override
	public void onPrintOrder(Orders order) {
		
		order = mOrderDaoService.getOrders(order.getId());
		
		try {
			PrintUtil.printOrder(order);
		} catch (Exception e) {
			showMessage(Constant.MESSAGE_PRINTER_CANT_PRINT);
		}
		
		String message = "Cetak nota untuk pelanggan ?";
		
		ConfirmationUtil.confirmTask(getFragmentManager(), this, ConfirmationUtil.PRINT_ORDER, message);
	}
	
	@Override
	public void onSelectDiscount() {
		
		mDiscountDlgFragment.show(getFragmentManager(), mDiscountDlgFragmentTag);
	}
	
	@Override
	public void onSelectDiscountAmount() {
		
		mDiscountAmountDlgFragment.setDiscount(mDiscount);
		mDiscountAmountDlgFragment.show(getFragmentManager(), mDiscountAmountDlgFragmentTag);
	}
	
	@Override
	public void onDiscountSelected(Discount discount) {
		
		mDiscount = discount;
		
		if (mDiscount != null && 
			mDiscount.getPercentage() == 0 &&
			mDiscount.getAmount() == 0) {
			
			onSelectDiscountAmount();
		
		} else {
			
			mOrderFragment.setDiscount(discount);
			updateTransactionItemsDiscount();
			mDiscountDlgFragment.dismiss();
		}
	}
	
	private void updateTransactionItemsDiscount() {
		
		for (TransactionItem transactionItem : mTransactionItems) {
			
			int discountPercentage = 0;
			
			if (mDiscount != null) {
				mDiscount.getPercentage();
			}
			
			int discountAmount = discountPercentage * transactionItem.getPrice() / 100;
			
			transactionItem.setDiscount(discountAmount);
		}
	}
	
	public void onSelectCustomer() {
		
		mCustomerDlgFragment.show(getFragmentManager(), mCustomerDlgFragmentTag);
	}
	
	public void onCustomerSelected(Customer customer) {
		
		mPaymentDlgFragment.setCustomer(customer);
	}
	
	@Override
	public void onConfirm(String task) {
		
		if (ConfirmationUtil.PRINT_ORDER.equals(task)) {
			
			try {
				PrintUtil.printOrder(mOrder);
			} catch (Exception e) {
				showMessage(Constant.MESSAGE_PRINTER_CANT_PRINT);
			}
		
		} else if (ConfirmationUtil.CANCEL_TRANSACTION.equals(task)) {
			
			onClearTransaction();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadOrders() {
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			
			mSelectedOrders = (HashMap<Long, Boolean>) extras.getSerializable(Constant.SELECTED_ORDERS_FOR_PAYMENT);
			mSelectedOrder = (Orders) extras.getSerializable(Constant.SELECTED_ORDERS_FOR_NEW_ITEM);
			
			if (mSelectedOrders != null) {
				
				mState = Constant.CASHIER_STATE_ORDER_PAYMENT;
				
				for (Long orderId : mSelectedOrders.keySet()) {
					
					boolean isSelected = mSelectedOrders.get(orderId);
					
					if (isSelected) {
						
						Orders order = mOrderDaoService.getOrders(orderId);
						
						mOrderType = order.getOrderType();
						mOrderReference = order.getOrderReference();
						
						List<OrderItem> orderItems = mOrderItemDaoService.getOrderItemsByOrderId(orderId);
						
						for (OrderItem orderItem : orderItems) {
							
							Product product =  mProductDaoService.getProduct(orderItem.getProductId());
							TransactionItem transItem = getTransactionItem(product, null, orderItem.getQuantity(), orderItem.getRemarks());
							
							mOrderFragment.addTransactionItem(transItem, true);
						}
					}
				}
			
			} else if (mSelectedOrder != null) {
				
				mState = Constant.CASHIER_STATE_ORDER_NEW_ITEM;
				
				mOrderFragment.setSelectedOrders(mSelectedOrder);
			}
		}
	}
	
	// Printer - Begin
	
	private void connectToPrinter(String printerType, String address) {
		
		if (printerType.indexOf("PTP-II") != -1) {
			PrintUtil.setPrinterLineSize(40);
			
		} else if (printerType.indexOf("RPP-02N") != -1) {
			PrintUtil.setPrinterLineSize(32);
		}
		
		try {
			PrintUtil.connectToBluetoothPrinter(address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {

		case PrintUtil.REQUEST_CONNECT_DEVICE:

			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				
				// Get the device Printer Type
				String printerType = intent.getExtras().getString(CashierPaymentDeviceListActivity.EXTRA_PRINTER_TYPE);
				
				// Get the device MAC address
				String address = intent.getExtras().getString(CashierPaymentDeviceListActivity.EXTRA_DEVICE_ADDRESS);
				
				Merchant merchant = MerchantUtil.getMerchant();
				merchant.setPrinterType(printerType);
				merchant.setPrinterAddress(address);
				
				MerchantDaoService merchantDaoService = new MerchantDaoService();
				merchantDaoService.updateMerchant(merchant);
				
				connectToPrinter(printerType, address);
			}
			
			break;
			
		case PrintUtil.REQUEST_ENABLE_BT:

			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				
				setMessage("Printer tidak terhubung, aktifkan Bluetooth Printer anda dan hubungkan ke sistem");
				
				// Bluetooth is now enabled, so set up a chat session
				PrintUtil.selectBluetoothPrinter();
				
				mSelectPrinterItem.setVisible(true);
				
			} else {

				// User did not enable Bluetooth or an error occured
				showMessage("Bluetooth tidak aktif, silahkan aktivasikan bluetooth terlebih dahulu!");
			}
			
			break;
		}
	}

	// printer - end
}