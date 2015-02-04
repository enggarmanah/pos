package com.android.pos.reference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.reference.customer.CustomerMgtActivity;
import com.android.pos.reference.discount.DiscountMgtActivity;
import com.android.pos.reference.employee.EmployeeMgtActivity;
import com.android.pos.reference.merchant.MerchantMgtActivity;
import com.android.pos.reference.product.ProductMgtActivity;
import com.android.pos.reference.productGrp.ProductGrpMgtActivity;
import com.android.pos.sync.SyncManager;
import com.android.pos.util.DbUtil;

public class MainMenuActivity extends BaseActivity {

	final Context context = this;
	private SyncManager syncManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.ref_main_menu_activity);
		
		DbUtil.initDb(this);
		
		syncManager = new SyncManager(this);
		
		initDrawerMenu();
		
		setTitle(getString(R.string.menu_data_management));
		
		mDrawerList.setItemChecked(Constant.MENU_DATA_MANAGEMENT_POSITION, true);
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
			
			syncManager.sync(); 

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
}
