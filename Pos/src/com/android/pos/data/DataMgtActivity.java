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
import com.android.pos.async.HttpAsyncProgressDlgFragment;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.common.AlertDlgFragment;
import com.android.pos.data.customer.CustomerMgtActivity;
import com.android.pos.data.discount.DiscountMgtActivity;
import com.android.pos.data.employee.EmployeeMgtActivity;
import com.android.pos.data.merchant.MerchantMgtActivity;
import com.android.pos.data.product.ProductMgtActivity;
import com.android.pos.data.productGrp.ProductGrpMgtActivity;
import com.android.pos.util.DbUtil;
import com.android.pos.util.NotificationUtil;

public class DataMgtActivity extends BaseActivity implements HttpAsyncListener {

	final Context context = this;
	private HttpAsyncManager mHttpAsyncManager;
	
	private HttpAsyncProgressDlgFragment mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.data_main_menu_activity);
		
		DbUtil.initDb(this);
		
		mHttpAsyncManager = new HttpAsyncManager(this);
		
		initDrawerMenu();
		
		mProgressDialog = (HttpAsyncProgressDlgFragment) getFragmentManager().findFragmentByTag("progressDialogTag");
		
		if (mProgressDialog == null) {
			mProgressDialog = new HttpAsyncProgressDlgFragment();
		}
    }
	
	@Override
	public void onStart() {
		
		super.onStart();

		setTitle(getString(R.string.menu_data_management));
		setSelectedMenu(getString(R.string.menu_data_management));
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
			
			mProgressDialog.show(getFragmentManager(), "progressDialogTag");
			
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
	
	public void goToModuleDiscount(View view) {
		
		Intent intent = new Intent(this, DiscountMgtActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void setSyncProgress(int progress) {
		
		if (mProgressDialog != null) {
			
			mProgressDialog.setProgress(progress);
			
			if (progress == 100) {
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mProgressDialog.dismiss();
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
		
		mProgressDialog.dismiss();
		
		AlertDlgFragment alertDialogFragment = NotificationUtil.getAlertDialogInstance();
		alertDialogFragment.show(getFragmentManager(), NotificationUtil.ALERT_DIALOG_FRAGMENT_TAG);
		alertDialogFragment.setAlertMessage("Tidak dapat terhubung ke Server!");
	}
}
