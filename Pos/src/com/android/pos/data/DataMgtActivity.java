package com.android.pos.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.data.customer.CustomerMgtActivity;
import com.android.pos.data.discount.DiscountMgtActivity;
import com.android.pos.data.employee.EmployeeMgtActivity;
import com.android.pos.data.merchant.MerchantMgtActivity;
import com.android.pos.data.product.ProductMgtActivity;
import com.android.pos.data.productGrp.ProductGrpMgtActivity;
import com.android.pos.data.supplier.SupplierMgtActivity;
import com.android.pos.util.DbUtil;

public class DataMgtActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.data_main_menu_activity);
		
		DbUtil.initDb(this);
		
		initDrawerMenu();
    }
	
	@Override
	public void onStart() {
		
		super.onStart();

		setSelectedMenu(Constant.MENU_DATA_MANAGEMENT);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.data_mgt_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
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
}
