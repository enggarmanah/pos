package com.android.pos.base.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Config;
import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncListener;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.ProgressDlgFragment;
import com.android.pos.auth.MerchantLoginActivity;
import com.android.pos.base.adapter.AppMenuArrayAdapter;
import com.android.pos.cashier.CashierActivity;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.data.bills.BillsMgtActivity;
import com.android.pos.data.cashflow.CashflowMgtActivity;
import com.android.pos.data.customer.CustomerMgtActivity;
import com.android.pos.data.discount.DiscountMgtActivity;
import com.android.pos.data.employee.EmployeeMgtActivity;
import com.android.pos.data.inventory.InventoryMgtActivity;
import com.android.pos.data.merchant.MerchantMgtActivity;
import com.android.pos.data.product.ProductMgtActivity;
import com.android.pos.data.productGrp.ProductGrpMgtActivity;
import com.android.pos.data.supplier.SupplierMgtActivity;
import com.android.pos.data.user.UserMgtActivity;
import com.android.pos.favorite.customer.CustomerActivity;
import com.android.pos.favorite.supplier.SupplierActivity;
import com.android.pos.order.OrderActivity;
import com.android.pos.printer.PrinterSelectActivity;
import com.android.pos.report.bills.BillsActivity;
import com.android.pos.report.cashflow.CashFlowActivity;
import com.android.pos.report.commission.CommissionActivity;
import com.android.pos.report.inventory.InventoryReportActivity;
import com.android.pos.report.product.ProductStatisticActivity;
import com.android.pos.report.transaction.TransactionActivity;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;
import com.android.pos.util.NotificationUtil;
import com.android.pos.util.PrintUtil;
import com.android.pos.util.UserUtil;
import com.android.pos.waitress.WaitressActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity
	implements AppMenuArrayAdapter.ItemActionListener, HttpAsyncListener {
	
	protected static ProgressDlgFragment mProgressDialog;
	
	protected static final String progressDialogTag = "progressDialogTag";
	
	protected static Integer mProgress = 0;
	
	protected HttpAsyncManager mHttpAsyncManager;
	
	protected int mSelectedIndex = -1;
	protected String mSelectedMenu = Constant.EMPTY_STRING;
	
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerList;
	protected ActionBarDrawerToggle mDrawerToggle;
	
	protected AppMenuArrayAdapter mAppMenuArrayAdapter;
	
	List<String> mMenus;
	
	private static boolean activityVisible = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CodeUtil.initCodes(this);
		
		if (Config.isDebug()) {

			DbUtil.initDb(this);
			MerchantUtil.getMerchant();
		}
		
		mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag(progressDialogTag);
		
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDlgFragment();
		}
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		activityVisible = true;
		
		if (getFragmentManager().findFragmentByTag(progressDialogTag) != null) {
			mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag(progressDialogTag);
		}
		
		if (mProgress == 100 && mProgressDialog.isVisible()) {
			
			mProgressDialog.dismiss();
			
			onAsyncTaskCompleted();
		}
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		
		activityVisible = true;
	}

	@Override
	public void onPause() {
		
		super.onPause();
		
		activityVisible = false;
	}
	
	@Override
	public void onBackPressed() {
	}
	
	public static boolean isActivityVisible() {
		
		return activityVisible;
	}
	
	protected void initDrawerMenu() {
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		
		mMenus = new ArrayList<String>();
		
		mAppMenuArrayAdapter = new AppMenuArrayAdapter(getApplicationContext(), mMenus, this);
		
		refreshMenus();
		
		mDrawerList.setAdapter(mAppMenuArrayAdapter);
		
		mDrawerList.setOnItemClickListener(getMenuListOnItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		
		Config.setMenusExpanded(false);
		
		showSelectedMenu();
		refreshMenus();
		
		return super.onCreateOptionsMenu(menu);
	}
	
	protected void showSelectedMenu() {
		
		if (getString(R.string.menu_report_cashflow).equals(getSelectedMenu()) ||
			getString(R.string.menu_report_bills).equals(getSelectedMenu()) ||
			getString(R.string.menu_report_commision).equals(getSelectedMenu()) ||
			getString(R.string.menu_report_inventory).equals(getSelectedMenu()) ||
			getString(R.string.menu_report_product_statistic).equals(getSelectedMenu()) ||
			getString(R.string.menu_report_transaction).equals(getSelectedMenu())) {
			
			Config.setMenuReportExpanded(true);
		
		} else if (getString(R.string.menu_favorite_customer).equals(getSelectedMenu()) ||
			getString(R.string.menu_favorite_patient).equals(getSelectedMenu()) ||
			getString(R.string.menu_favorite_supplier).equals(getSelectedMenu())) {
			
			Config.setMenuFavoriteExpanded(true);
		
		} else if (getString(R.string.menu_reference_discount).equals(getSelectedMenu()) ||
			getString(R.string.menu_reference_employee).equals(getSelectedMenu()) ||
			getString(R.string.menu_reference_merchant).equals(getSelectedMenu()) ||
			getString(R.string.menu_reference_product).equals(getSelectedMenu()) ||
			getString(R.string.menu_reference_product_group).equals(getSelectedMenu()) ||
			getString(R.string.menu_reference_supplier).equals(getSelectedMenu())) {
			
			if (!UserUtil.isRoot()) {
				Config.setMenuDataReferenceExpanded(true);
			}
		} 
	}
	
	protected void refreshMenus() {
		
		mMenus.clear();
		
		mMenus.add(getString(R.string.menu_user));
		
		UserUtil.getUser();
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_CASHIER)) {
			mMenus.add(getString(R.string.menu_cashier));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_WAITRESS)) {
			mMenus.add(getString(R.string.menu_waitress));
		}
		
		mMenus.add(getString(R.string.menu_sync));
		
		UserUtil.getUser();
		
		if (UserUtil.isRoot()) {
			
			mMenus.add(getString(R.string.menu_merchant));
		}
		
		if (UserUtil.isMerchant()) {
			
			mMenus.add(getString(R.string.menu_user_access));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_ORDER)) {
			mMenus.add(getString(R.string.menu_order));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_TRANSACTION) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_PRODUCT_STATISTIC) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_CASHFLOW) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_BILLS) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_INVENTORY)) {
			
			mMenus.add(getString(R.string.menu_report));
		}
		
		if (Config.isMenuReportExpanded()) {
			
			if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_TRANSACTION)) {
				mMenus.add(getString(R.string.menu_report_transaction));
			}
			
			if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_PRODUCT_STATISTIC)) {
				mMenus.add(getString(R.string.menu_report_product_statistic));
			}
			
			if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_COMMISION)) {
				mMenus.add(getString(R.string.menu_report_commision));
			}
			
			if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_CASHFLOW)) {
				mMenus.add(getString(R.string.menu_report_cashflow));
			}
			
			if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_BILLS)) {
				mMenus.add(getString(R.string.menu_report_bills));
			}
			
			if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_INVENTORY)) {
				mMenus.add(getString(R.string.menu_report_inventory));
			}
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_FAVORITE_CUSTOMER) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_FAVORITE_SUPPLIER)) {
				
			mMenus.add(getString(R.string.menu_favorite));
		}
		
		if (Config.isMenuFavoriteExpanded()) {
		
			if (UserUtil.isUserHasAccess(Constant.ACCESS_FAVORITE_CUSTOMER)) {
				
				if (Constant.MERCHANT_TYPE_CLINIC.equals(MerchantUtil.getMerchantType())) {
					mMenus.add(getString(R.string.menu_favorite_patient));
				} else {
					mMenus.add(getString(R.string.menu_favorite_customer));
				}
			}
			
			if (UserUtil.isUserHasAccess(Constant.ACCESS_FAVORITE_SUPPLIER)) {
				mMenus.add(getString(R.string.menu_favorite_supplier));
			}
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_BILLS)) {
			mMenus.add(getString(R.string.menu_bills));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_CASHFLOW)) {
			mMenus.add(getString(R.string.menu_cashflow));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_INVENTORY)) {
			mMenus.add(getString(R.string.menu_inventory));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_CUSTOMER)) {
			
			if (Constant.MERCHANT_TYPE_CLINIC.equals(MerchantUtil.getMerchantType())) {
				mMenus.add(getString(R.string.menu_patient));
			} else {
				mMenus.add(getString(R.string.menu_customer));
			}	
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_USER_ACCESS)) {
			mMenus.add(getString(R.string.menu_user_access));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_DATA_MANAGEMENT)) {
			mMenus.add(getString(R.string.menu_data_management));
		}
		
		if (Config.isMenuDataReferenceExpanded()) {
			
			mMenus.add(getString(R.string.menu_reference_merchant));
			mMenus.add(getString(R.string.menu_reference_product_group));
			mMenus.add(getString(R.string.menu_reference_product));
			mMenus.add(getString(R.string.menu_reference_employee));
			mMenus.add(getString(R.string.menu_reference_supplier));
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_CASHIER)) {
			
			mMenus.add(getString(R.string.menu_printer));
		}
		
		mMenus.add(getString(R.string.menu_exit));
		
		mAppMenuArrayAdapter.notifyDataSetChanged();
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public String getSelectedMenu() {
		
		return mSelectedMenu;
	}
	
	protected AdapterView.OnItemClickListener getMenuListOnItemClickListener() {

		return new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		};
	}

	@Override
	public void setTitle(CharSequence title) {
		
		getActionBar().setTitle(title);
	}

	private void selectItem(int position) {
		
		String menu = mMenus.get(position);
		
		if (position == 0) {
			return;
		}
		
		if (getString(R.string.menu_report).equals(menu)) {
			
			boolean isExpanded = Config.isMenuReportExpanded();
			
			Config.setMenusExpanded(false);
			Config.setMenuReportExpanded(!isExpanded);
			refreshMenus();
			return;
			
		} else if (getString(R.string.menu_favorite).equals(menu)) {
			
			boolean isExpanded = Config.isMenuFavoriteExpanded();
			
			Config.setMenusExpanded(false);
			Config.setMenuFavoriteExpanded(!isExpanded);
			refreshMenus();
			return;
			
		} else if (getString(R.string.menu_data_management).equals(menu)) {
			
			boolean isExpanded = Config.isMenuDataReferenceExpanded();
			
			Config.setMenusExpanded(false);
			Config.setMenuDataReferenceExpanded(!isExpanded);
			refreshMenus();
			return;
		}  
		
		mDrawerLayout.closeDrawer(mDrawerList);
		
		if (getString(R.string.menu_cashier).equals(menu)) {

			Intent intent = new Intent(this, CashierActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_waitress).equals(menu)) {

			Intent intent = new Intent(this, WaitressActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_order).equals(menu)) {

			Intent intent = new Intent(this, OrderActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_report_transaction).equals(menu)) {

			Intent intent = new Intent(this, TransactionActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_report_product_statistic).equals(menu)) {

			Intent intent = new Intent(this, ProductStatisticActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_report_commision).equals(menu)) {

			Intent intent = new Intent(this, CommissionActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_report_cashflow).equals(menu)) {

			Intent intent = new Intent(this, CashFlowActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_report_bills).equals(menu)) {

			Intent intent = new Intent(this, BillsActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_report_inventory).equals(menu)) {

			Intent intent = new Intent(this, InventoryReportActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_favorite_customer).equals(menu) ||
				   getString(R.string.menu_favorite_patient).equals(menu)) {

			Intent intent = new Intent(this, CustomerActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_favorite_supplier).equals(menu)) {

			Intent intent = new Intent(this, SupplierActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_bills).equals(menu)) {

			Intent intent = new Intent(this, BillsMgtActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_cashflow).equals(menu)) {

			Intent intent = new Intent(this, CashflowMgtActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_inventory).equals(menu)) {

			Intent intent = new Intent(this, InventoryMgtActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_customer).equals(menu) ||
				   getString(R.string.menu_patient).equals(menu)) {

			Intent intent = new Intent(this, CustomerMgtActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_user_access).equals(menu)) {

			Intent intent = new Intent(this, UserMgtActivity.class);
			startActivity(intent);

		} else if (getString(R.string.menu_reference_merchant).equals(menu)) {

			Intent intent = new Intent(this, MerchantMgtActivity.class);
			startActivity(intent);
			
		} else if (getString(R.string.menu_reference_product_group).equals(menu)) {

			Intent intent = new Intent(this, ProductGrpMgtActivity.class);
			startActivity(intent);
			
		} else if (getString(R.string.menu_reference_product).equals(menu)) {

			Intent intent = new Intent(this, ProductMgtActivity.class);
			startActivity(intent);
			
		} else if (getString(R.string.menu_reference_employee).equals(menu)) {

			Intent intent = new Intent(this, EmployeeMgtActivity.class);
			startActivity(intent);
			
		} else if (getString(R.string.menu_reference_supplier).equals(menu)) {

			Intent intent = new Intent(this, SupplierMgtActivity.class);
			startActivity(intent);
			
		} else if (getString(R.string.menu_reference_discount).equals(menu)) {

			Intent intent = new Intent(this, DiscountMgtActivity.class);
			startActivity(intent);
			
		} else if (getString(R.string.menu_sync).equals(menu)) {
			
			if (mProgressDialog.isAdded()) {
				return;
			}
			
			mProgressDialog.show(getFragmentManager(), progressDialogTag);
			
			if (mHttpAsyncManager == null) {
				mHttpAsyncManager = new HttpAsyncManager(this);
			}
			
			if (UserUtil.isWaitress()) {
				mHttpAsyncManager.syncProducts();
				
			} else if (UserUtil.isCashier() || UserUtil.isAdmin()) {
				mHttpAsyncManager.sync();
				
			} else if (UserUtil.isMerchant()) {
				mHttpAsyncManager.syncUsers();
				
			} else if (UserUtil.isRoot()) {
				mHttpAsyncManager.syncMerchants();
			}
			
		} else if (getString(R.string.menu_printer).equals(menu)) {

			Intent intent = new Intent(this, PrinterSelectActivity.class);
			startActivityForResult(intent, -1);
			
		} else if (getString(R.string.menu_exit).equals(menu)) {
			
			UserUtil.resetUser();
			PrintUtil.reset();
			Config.resetMenus();
			
			Intent intent = new Intent(this, MerchantLoginActivity.class);
			
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}
	
	private int getMenuIndex(String str) {
		
		int index = -1;
		
		for (String menu : mMenus) {
			
			index++;
			
			if (str.equals(menu)) {
				
				return index;
			}
		}
		
		return index;
	}
	
	protected void setSelectedMenu(String menu) {
		
		mSelectedIndex = getMenuIndex(menu);
		mSelectedMenu = menu;
		
		mDrawerList.setItemChecked(mSelectedIndex, true);
	}

	public void showMessage(int resourceId) {

		try {
			Toast toast = Toast.makeText(this, getResources().getString(resourceId), Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showLongMessage(String message) {
		
		try {
			Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String message) {

		try {
			Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void addFragment(Object fragment, String fragmentTag) {
		
		Fragment f = (Fragment) fragment;
		
		if (f.isAdded()) {
			return;
		}
		
		getFragmentManager().beginTransaction().add(R.id.fragment_container, f, fragmentTag).commit();
	}

	protected void replaceFragment(Object fragment, String fragmentTag) {
		
		Fragment f = (Fragment) fragment;
		
		if (f.isAdded()) {
			return;
		}
		
		getFragmentManager().beginTransaction().replace(R.id.fragment_container, f, fragmentTag).commit();
	}

	protected void removeFragment(Object fragment) {
		
		getFragmentManager().beginTransaction().remove((Fragment) fragment).commit();
	}

	protected void afterFragmentRemoved() {
	};

	protected void initWaitAfterFragmentRemovedTask(String... fragmentTags) {
		
		System.out.println("initWaitAfterFragmentRemovedTask");
		
		WaitAfterFragmentRemovedTask task = new WaitAfterFragmentRemovedTask();
		task.execute(fragmentTags);
	}

	private class WaitAfterFragmentRemovedTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {

			boolean isRemoved = false;
			
			while (!isRemoved) {
				
				isRemoved = true;
				for (String fragmentTag : params) {
					if (getFragmentManager().findFragmentByTag(fragmentTag) != null) {
						isRemoved = false;
					}
				}

				try {
					if (!isRemoved) {
						Thread.sleep(Constant.WAIT_FRAGMENT_TO_BE_REMOVED_SLEEP_PERIOD);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			afterFragmentRemoved();

			return true;
		}

		@Override
		protected void onProgressUpdate(Void... progress) {
		}

		@Override
		protected void onPostExecute(Boolean result) {
		}
	}
	
	protected void updateProductStock() {
		
		ProductDaoService productDaoService = new ProductDaoService();
		InventoryDaoService inventoryDaoServie = new InventoryDaoService();
		
		List<Product> products = productDaoService.getProducts(Constant.PRODUCT_TYPE_GOODS);
		
		for (Product product : products) {
			
			Float quantity = inventoryDaoServie.getProductQuantity(product);
			product.setStock(quantity);
			
			productDaoService.updateProduct(product);
		}
	}
	
	protected void onAsyncTaskCompleted() {}
	
	@Override
	public void setSyncProgress(int progress) {
		
		mProgress = progress;
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				// update product min stock count
				updateProductStock();
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						if (isActivityVisible()) {
							
							mProgressDialog.dismiss();
							
							onAsyncTaskCompleted();
						}
					}
				}, 500);
			}
		}
	}
	
	@Override
	public void setSyncMessage(String message) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setMessage(message);
		}
	}
	
	@Override
	public void onTimeOut() {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.server_cant_connect));
	}
	
	@Override
	public void onSyncError() {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), getString(R.string.server_sync_error));
	}
	
	@Override
	public void onSyncError(String message) {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), message);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	}
}
