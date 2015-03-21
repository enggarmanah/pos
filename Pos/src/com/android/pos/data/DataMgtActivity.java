package com.android.pos.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.pos.R;
import com.android.pos.async.HttpAsyncListener;
import com.android.pos.async.HttpAsyncManager;
import com.android.pos.async.ProgressDlgFragment;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.data.customer.CustomerMgtActivity;
import com.android.pos.data.discount.DiscountMgtActivity;
import com.android.pos.data.employee.EmployeeMgtActivity;
import com.android.pos.data.merchant.MerchantMgtActivity;
import com.android.pos.data.product.ProductMgtActivity;
import com.android.pos.data.productGrp.ProductGrpMgtActivity;
import com.android.pos.data.supplier.SupplierMgtActivity;
import com.android.pos.util.DbUtil;
import com.android.pos.util.NotificationUtil;

public class DataMgtActivity extends BaseActivity implements HttpAsyncListener {

	final Context context = this;
	private HttpAsyncManager mHttpAsyncManager;
	
	private static ProgressDlgFragment mProgressDialog;
	private static Integer mProgress = 0;
	
	private static final String progressDialogTag = "progressDialogTag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.data_main_menu_activity);
		
		DbUtil.initDb(this);
		
		initDrawerMenu();
		
		mHttpAsyncManager = new HttpAsyncManager(this);
		
		mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag(progressDialogTag);
		
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDlgFragment();
		}
    }
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.menu_data_management));
		setSelectedMenu(getString(R.string.menu_data_management));
		
		if (getFragmentManager().findFragmentByTag(progressDialogTag) != null) {
			mProgressDialog = (ProgressDlgFragment) getFragmentManager().findFragmentByTag(progressDialogTag);
		}
		
		if (mProgress == 100 && mProgressDialog.isVisible()) {
			mProgressDialog.dismiss();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.data_mgt_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_item_sync:
			
			mProgressDialog.show(getFragmentManager(), progressDialogTag);
			
			mHttpAsyncManager.sync(); 
			
			return true;

		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void goToModuleMerchant(View view) {
		
		Intent intent = new Intent(this, MerchantMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleProductGroup(View view) {
		
		Intent intent = new Intent(this, ProductGrpMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleProduct(View view) {
		
		Intent intent = new Intent(this, ProductMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleEmployee(View view) {
		
		Intent intent = new Intent(this, EmployeeMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleCustomer(View view) {
		
		Intent intent = new Intent(this, CustomerMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleSupplier(View view) {
		
		Intent intent = new Intent(this, SupplierMgtActivity.class);
		startActivity(intent);
	}
	
	public void goToModuleDiscount(View view) {
		
		Intent intent = new Intent(this, DiscountMgtActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void setSyncProgress(int progress) {
		
		mProgress = progress;
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						if (isActivityVisible()) {
							mProgressDialog.dismiss();
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
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "Tidak dapat terhubung ke Server!");
	}
	
	@Override
	public void onSyncError() {
		
		mProgress = 100;
		
		if (isActivityVisible()) {
			mProgressDialog.dismiss();
		}
		
		NotificationUtil.setAlertMessage(getFragmentManager(), "Error dalam sync data ke Server!");
	}
}
