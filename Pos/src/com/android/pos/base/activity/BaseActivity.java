package com.android.pos.base.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.async.HttpAsyncListener;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.ProgressDlgFragment;
import com.android.pos.auth.MerchantLoginActivity;
import com.android.pos.base.adapter.AppMenuArrayAdapter;
import com.android.pos.bills.BillsMgtActivity;
import com.android.pos.cashier.CashierActivity;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.data.DataMgtActivity;
import com.android.pos.inventory.InventoryMgtActivity;
import com.android.pos.order.OrderActivity;
import com.android.pos.report.cashflow.CashFlowActivity;
import com.android.pos.report.inventory.InventoryReportActivity;
import com.android.pos.report.product.ProductStatisticActivity;
import com.android.pos.report.transaction.TransactionActivity;
import com.android.pos.user.UserMgtActivity;
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
	
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerList;
	protected ActionBarDrawerToggle mDrawerToggle;

	List<String> mMenus;
	
	private static boolean activityVisible = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
		
		mMenus.add(Constant.MENU_USER);
		
		UserUtil.getUser();
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_CASHIER)) {
			mMenus.add(Constant.MENU_CASHIER);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_WAITRESS)) {
			mMenus.add(Constant.MENU_WAITRESS);
		}
		
		mMenus.add(Constant.MENU_SYNC);
		
		UserUtil.getUser();
		
		if (UserUtil.isRoot()) {
			
			mMenus.add(Constant.MENU_DATA);
			mMenus.add(Constant.MENU_MERCHANT);
		}
		
		if (UserUtil.isMerchant()) {
			
			mMenus.add(Constant.MENU_DATA);
			mMenus.add(Constant.MENU_USER_ACCESS);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_ORDER)) {
			mMenus.add(Constant.MENU_ORDER);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_TRANSACTION) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_PRODUCT_STATISTIC) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_CASHFLOW) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_INVENTORY)) {
			
			mMenus.add(Constant.MENU_REPORT);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_TRANSACTION)) {
			mMenus.add(Constant.MENU_REPORT_TRANSACTION);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_PRODUCT_STATISTIC)) {
			mMenus.add(Constant.MENU_REPORT_PRODUCT_STATISTIC);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_CASHFLOW)) {
			mMenus.add(Constant.MENU_REPORT_CASHFLOW);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_REPORT_INVENTORY)) {
			mMenus.add(Constant.MENU_REPORT_INVENTORY);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_BILLS) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_INVENTORY) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_USER_ACCESS) ||
			UserUtil.isUserHasAccess(Constant.ACCESS_DATA_MANAGEMENT)) {
			
			mMenus.add(Constant.MENU_DATA);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_BILLS)) {
			mMenus.add(Constant.MENU_BILLS);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_INVENTORY)) {
			mMenus.add(Constant.MENU_INVENTORY);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_USER_ACCESS)) {
			mMenus.add(Constant.MENU_USER_ACCESS);
		}
		
		if (UserUtil.isUserHasAccess(Constant.ACCESS_DATA_MANAGEMENT)) {
			mMenus.add(Constant.MENU_DATA_MANAGEMENT);
		}
		
		mMenus.add(Constant.MENU_EXIT);
		
		AppMenuArrayAdapter adapter = new AppMenuArrayAdapter(getApplicationContext(), mMenus, this);
		
		mDrawerList.setAdapter(adapter);
		
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
		
		if (mSelectedIndex != -1) {
			return mMenus.get(mSelectedIndex);
		} else {
			return null;
		}
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
		
		if (position == 0 || Constant.MENU_REPORT.equals(menu) || Constant.MENU_DATA.equals(menu)) {
			return;
		}
		
		mDrawerLayout.closeDrawer(mDrawerList);
		
		if (Constant.MENU_CASHIER.equals(menu)) {

			Intent intent = new Intent(this, CashierActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_WAITRESS.equals(menu)) {

			Intent intent = new Intent(this, WaitressActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_ORDER.equals(menu)) {

			Intent intent = new Intent(this, OrderActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_REPORT_TRANSACTION.equals(menu)) {

			Intent intent = new Intent(this, TransactionActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_REPORT_PRODUCT_STATISTIC.equals(menu)) {

			Intent intent = new Intent(this, ProductStatisticActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_REPORT_CASHFLOW.equals(menu)) {

			Intent intent = new Intent(this, CashFlowActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_REPORT_INVENTORY.equals(menu)) {

			Intent intent = new Intent(this, InventoryReportActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_BILLS.equals(menu)) {

			Intent intent = new Intent(this, BillsMgtActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_INVENTORY.equals(menu)) {

			Intent intent = new Intent(this, InventoryMgtActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_USER_ACCESS.equals(menu)) {

			Intent intent = new Intent(this, UserMgtActivity.class);
			startActivity(intent);

		} else if (Constant.MENU_DATA_MANAGEMENT.equals(menu)) {

			Intent intent = new Intent(this, DataMgtActivity.class);
			startActivity(intent);
			
		} else if (Constant.MENU_SYNC.equals(menu)) {
			
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
			
		} else if (Constant.MENU_EXIT.equals(menu)) {
			
			UserUtil.resetUser();
			PrintUtil.reset();
			
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
		getFragmentManager().beginTransaction().add(R.id.fragment_container, (Fragment) fragment, fragmentTag).commit();
	}

	protected void replaceFragment(Object fragment, String fragmentTag) {
		getFragmentManager().beginTransaction().replace(R.id.fragment_container, (Fragment) fragment, fragmentTag).commit();
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
		
		List<Product> products = productDaoService.getProducts();
		
		for (Product product : products) {
			
			Integer quantity = inventoryDaoServie.getProductQuantity(product);
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
		
		NotificationUtil.setAlertMessage(getFragmentManager(), Constant.MESSAGE_SERVER_CANT_CONNECT);
	}
	
	@Override
	public void onSyncError() {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), Constant.MESSAGE_SERVER_SYNC_ERROR);
	}
	
	@Override
	public void onSyncError(String message) {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), message);
	}
}
