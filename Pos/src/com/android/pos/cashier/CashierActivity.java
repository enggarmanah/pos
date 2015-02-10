package com.android.pos.cashier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.Customer;
import com.android.pos.dao.DaoSession;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Employee;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.Product;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.Transactions;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.PrintUtil;

import android.app.ActionBar;
import android.app.Activity;
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
	implements CashierActionListener, SearchView.OnQueryTextListener {
	
	LinearLayout messagePanel;
	TextView messageText;

	protected CashierProductSearchFragment mProductSearchFragment;
	protected CashierOrderFragment mOrderFragment;
	private CashierProductCountDlgFragment mProductCountDlgFragment;
	private CashierPaymentDlgFragment mPaymentDlgFragment;
	private CashierPaymentSummaryDlgFragment mPaymentSummaryDlgFragment;
	private CashierDiscountDlgFragment mDiscountDlgFragment;
	private CashierDiscountAmountDlgFragment mDiscountAmountDlgFragment;
	private CashierCustomerDlgFragment mCustomerDlgFragment;
	
	boolean mIsMultiplesPane = false;
	boolean mIsEnableSearch = true;
	
	SearchView mSearchView;
	MenuItem mSearchItem;

	Product mSelectedProduct;
	List<TransactionItem> mTransactionItems;
	
	Discount mDiscount;

	private static String TRANSACTION_ITEMS = "TRANSACTION_ITEMS";
	private static String DISCOUNT = "DISCOUNT";

	private String mProductSearchFragmentTag = "productSearchFragmentTag";
	private String mProductCountDlgFragmentTag = "productCountDlgFragmentTag";
	private String mOrderFragmentTag = "orderFragment";
	private String mPaymentDlgFragmentTag = "paymentDlgFragmentTag";
	private String mPaymentSummaryDlgFragmentTag = "paymentSummaryDlgFragmentTag";
	private String mDiscountDlgFragmentTag = "discountDlgFragmentTag";
	private String mDiscountAmountDlgFragmentTag = "discountAmountDlgFragmentTag";
	private String mCustomerDlgFragmentTag = "customerDlgFragmentTag";

	private String prevQuery = Constant.EMPTY_STRING;
	
	private static DaoSession daoSession;
	
	private static DaoSession getDaoSession() {
		
		if (daoSession == null) {
			daoSession = DbUtil.getSession();
		}
		
		return daoSession;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cashier_activity);

		DbUtil.initDb(this);

		initDrawerMenu();

		initFragments(savedInstanceState);

		setTitle(getString(R.string.menu_cashier));
		mDrawerList.setItemChecked(Constant.MENU_CASHIER_POSITION, true);
		
		System.out.println("CashierActivity.onCreate");

		initWaitAfterFragmentRemovedTask(mProductSearchFragmentTag, mOrderFragmentTag);
		
		messagePanel = (LinearLayout) findViewById(R.id.messagePanel);
		messageText = (TextView) findViewById(R.id.messageText);
		
		messagePanel.setVisibility(View.GONE);
		
		messageText.setOnClickListener(getMessageTextOnClickListener());
		
		PrintUtil.initBluetooth(this);
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

		if (mPaymentSummaryDlgFragment == null) {
			mPaymentSummaryDlgFragment = new CashierPaymentSummaryDlgFragment();
		}
		
		if (mDiscountDlgFragment == null) {
			mDiscountDlgFragment = new CashierDiscountDlgFragment();
		}
		
		if (mDiscountAmountDlgFragment == null) {
			mDiscountAmountDlgFragment = new CashierDiscountAmountDlgFragment();
		}
		
		if (mCustomerDlgFragment == null) {
			mCustomerDlgFragment = new CashierCustomerDlgFragment();
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cashier_menu, menu);

		mSearchItem = menu.findItem(R.id.menu_item_search);

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

		menu.findItem(R.id.menu_item_search).setVisible(!isDrawerOpen);
		menu.findItem(R.id.menu_item_list).setVisible(!isDrawerOpen);

		return super.onPrepareOptionsMenu(menu);
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

		switch (item.getItemId()) {

		case R.id.menu_item_search:

			doSearch(Constant.EMPTY_STRING);

			showMessage(R.string.msg_notification_search_action);

			return true;

		case R.id.menu_item_list:
			
			hideSearchView();
			
			if (mIsMultiplesPane) {
				mProductSearchFragment.showProductGroups();
			} else {
				replaceFragment(mProductSearchFragment, mProductSearchFragmentTag);
				mProductSearchFragment.showProductGroups();
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
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
		mDiscount = null;
		
		mTransactionItems.clear();
		
		mProductSearchFragment.showProductGroups();
		mOrderFragment.setTransactionItems(mTransactionItems);
		mOrderFragment.setDiscount(mDiscount);
	}
	
	public View.OnClickListener getMessageTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				PrintUtil.selectBluetoothPrinter();
			}
		};
	}
	
	@Override
	public void onShowProductGroups() {
		
		hideSearchView();
		mProductSearchFragment.showProductGroups();
	}
	
	@Override
	public void onProductSelected(Product product) {
		
		onProductSelected(product, 0);
	}

	@Override
	public void onProductSelected(Product product, int quantity) {

		mSelectedProduct = product;

		mProductCountDlgFragment.show(getFragmentManager(), mProductCountDlgFragmentTag);
		mProductCountDlgFragment.setProduct(product, quantity);
		
		hideSearchView();
	}

	@Override
	public void onProductQuantitySelected(Product product, Employee personInCharge, int quantity) {

		mSelectedProduct = null;

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
		
		transItem.setPrice(product.getPrice());

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
	public void onPaymentInfoProvided(Customer customer, String paymentType, int totalBill, int payment) {

		mPaymentSummaryDlgFragment.show(getFragmentManager(), mPaymentSummaryDlgFragmentTag);
		mPaymentSummaryDlgFragment.setPaymentInfo(customer, paymentType, totalBill, payment);
	}

	@Override
	public void onPaymentCompleted(Transactions transaction) {
		
		transaction.setMerchant(MerchantUtil.getMerchant());
		transaction.setUploadStatus(Constant.STATUS_YES);
		
		getDaoSession().insert(transaction);
		
		System.out.println("Trasaction ID : " + transaction.getId());
		
		for (TransactionItem transactionItem : mTransactionItems) {
			
			transactionItem.setTransactionId(transaction.getId());
			transactionItem.setMerchant(MerchantUtil.getMerchant());
			transactionItem.setUploadStatus(Constant.STATUS_YES);
			
			getDaoSession().insert(transactionItem);
			
			System.out.println("Trasaction Item ID : " + transactionItem.getId());
		}
		
		onClearTransaction();
	}

	@Override
	public void onPrintReceipt(Transactions transaction) {
		
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
		
		PrintUtil.print(transaction, mTransactionItems);
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
			mDiscountDlgFragment.dismiss();
		}
	}
	
	public void onSelectCustomer() {
		
		mCustomerDlgFragment.show(getFragmentManager(), mCustomerDlgFragmentTag);
	}
	
	public void onCustomerSelected(Customer customer) {
		
		mPaymentDlgFragment.setCustomer(customer);
	}
	
	// Printer - Begin

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		case PrintUtil.REQUEST_CONNECT_DEVICE:

			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				
				// Get the device Printer Type
				String printerType = data.getExtras().getString(CashierPaymentDeviceListActivity.EXTRA_PRINTER_TYPE);
				
				// Get the device MAC address
				String address = data.getExtras().getString(CashierPaymentDeviceListActivity.EXTRA_DEVICE_ADDRESS);
				
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
			break;
			
		case PrintUtil.REQUEST_ENABLE_BT:

			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				
				setMessage("Aktifkan Bluetooth Printer anda dan hubungkan ke sistem");
				
				// Bluetooth is now enabled, so set up a chat session
				PrintUtil.setupChat();

			} else {

				// User did not enable Bluetooth or an error occured
				showMessage("Bluetooth tidak aktif, silahkan aktivasikan bluetooth terlebih dahulu!");
			}
		}
	}

	// printer - end
}