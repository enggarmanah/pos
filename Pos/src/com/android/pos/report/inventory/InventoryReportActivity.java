package com.android.pos.report.inventory;

import java.io.Serializable;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.activity.BaseActivity;
import com.android.pos.dao.InventoryDaoService;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

public class InventoryReportActivity extends BaseActivity 
	implements InventoryReportActionListener, SearchView.OnQueryTextListener {
	
	private SearchView searchView;
	private MenuItem mSearchMenu;
	
	private String prevQuery = Constant.EMPTY_STRING;
	
	private InventoryReportListFragment mInventoryReportListFragment;
	private InventoryReportDetailFragment mInventoryReportDetailFragment;
	
	boolean mIsMultiplesPane = false;
	
	private Product mSelectedProduct;
	
	private static String SELECTED_PRODUCT = "SELECTED_PRODUCT";
	
	private final String mInventoryReportListFragmentTag = "inventoryReportListFragmentTag";
	private final String mInventoryReportDetailFragmentTag = "inventoryReportDetailFragmentTag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initInstanceState(savedInstanceState);
		
		setContentView(R.layout.report_inventory_activity);

		DbUtil.initDb(this);

		initDrawerMenu();
		
		initFragments();
		
		initWaitAfterFragmentRemovedTask(mInventoryReportListFragmentTag, mInventoryReportDetailFragmentTag);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		setTitle(getString(R.string.menu_report_inventory));
		setSelectedMenu(getString(R.string.menu_report_inventory));
		
		updateProductStock();
	}
	
	private void initInstanceState(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mSelectedProduct = (Product) savedInstanceState.getSerializable(SELECTED_PRODUCT);
		}
	}
	
	private void initFragments() {
		
		mIsMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);

		mInventoryReportListFragment = (InventoryReportListFragment) getFragmentManager().findFragmentByTag(mInventoryReportListFragmentTag);
		
		if (mInventoryReportListFragment == null) {
			mInventoryReportListFragment = new InventoryReportListFragment();

		} else {
			removeFragment(mInventoryReportListFragment);
		}
		
		mInventoryReportDetailFragment = (InventoryReportDetailFragment) getFragmentManager().findFragmentByTag(mInventoryReportDetailFragmentTag);
		
		if (mInventoryReportDetailFragment == null) {
			mInventoryReportDetailFragment = new InventoryReportDetailFragment();

		} else {
			removeFragment(mInventoryReportDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		
		outState.putSerializable(SELECTED_PRODUCT, (Serializable) mSelectedProduct);
	}
	
	@Override
	protected void afterFragmentRemoved() {
		
		loadFragments();
	}
	
	private void loadFragments() {
		
		mInventoryReportListFragment.setSelectedProduct(mSelectedProduct);
		mInventoryReportDetailFragment.setProduct(mSelectedProduct);
		
		if (mIsMultiplesPane) {

			addFragment(mInventoryReportListFragment, mInventoryReportListFragmentTag);
			addFragment(mInventoryReportDetailFragment, mInventoryReportDetailFragmentTag);
			
		} else {

			if (mSelectedProduct != null) {
				
				addFragment(mInventoryReportDetailFragment, mInventoryReportDetailFragmentTag);
				
			} else {
				
				addFragment(mInventoryReportListFragment, mInventoryReportListFragmentTag);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.report_inventory_menu, menu);
		
		mSearchMenu = menu.findItem(R.id.menu_item_search);
		
		searchView = (SearchView) mSearchMenu.getActionView();
		searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.START));

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (null != searchManager) {
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		}

		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		hideSelectedMenu();
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	private void hideSelectedMenu() {
		
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		
		mSearchMenu.setVisible(!isDrawerOpen);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onProductSelected(Product product) {
		
		mSelectedProduct = product;
		
		mInventoryReportListFragment.setSelectedProduct(product);
		mInventoryReportDetailFragment.setProduct(mSelectedProduct);
		
		if (!mIsMultiplesPane) {
			
			replaceFragment(mInventoryReportDetailFragment, mInventoryReportDetailFragmentTag);
		}
	}
	
	@Override
	public void onBackButtonClicked() {
		
		synchronized (CommonUtil.LOCK) {
			
			showInventoryList();
		}
	}
	
	private void showInventoryList() {
		
		mSelectedProduct = null;
		
		mInventoryReportListFragment.setSelectedProduct(mSelectedProduct);
		mInventoryReportDetailFragment.setProduct(mSelectedProduct);
		
		if (!mIsMultiplesPane) {
			
			replaceFragment(mInventoryReportListFragment, mInventoryReportListFragmentTag);
		}
	}
	
	@Override
	public boolean onQueryTextChange(String query) {
		
		boolean isQuerySimilar = query.equals(prevQuery);
		
		prevQuery = query;
		
		if (!isQuerySimilar) {
			
			if (mIsMultiplesPane) {
				
				doSearch(query);
				
			} else {
				
				replaceFragment(mInventoryReportListFragment, mInventoryReportListFragmentTag);
				doSearch(query);
			}
		}

		return true;
	}
	
	@Override
	public boolean onQueryTextSubmit(String query) {

		doSearch(query);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

		return true;
	}
	
	private void doSearch(String query) {
		
		mInventoryReportListFragment.searchProduct(query);
	}
	
	@Override
	public void onProductUnselected() {
		
		mSelectedProduct = null;

		mInventoryReportListFragment.setSelectedProduct(mSelectedProduct);
		mInventoryReportDetailFragment.setProduct(mSelectedProduct);
	}
	
	private void updateProductStock() {
		
		ProductDaoService productDaoService = new ProductDaoService();
		InventoryDaoService inventoryDaoServie = new InventoryDaoService();
		
		List<Product> products = productDaoService.getProducts();
		
		for (Product product : products) {
			
			Integer quantity = inventoryDaoServie.getProductQuantity(product);
			product.setStock(quantity);
			
			productDaoService.updateProduct(product);
		}
	}
}