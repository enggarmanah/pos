package com.android.pos.report.inventory;

import java.util.List;

import com.android.pos.Constant;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDaoService;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryReportListFragment extends BaseFragment 
	implements InventoryReportArrayAdapter.ItemActionListener {
	
	private TextView mTitleText;
	
	private ListView mProductList;
	
	private List<Product> mProducts;
	private List<Product> mStockAlertProducts;
	
	private Product mSelectedProduct;
	
	private InventoryReportArrayAdapter mAdapter;
	private InventoryReportArrayAdapter mStockAlertAdapter;
	
	private InventoryReportActionListener mActionListener;
	
	private boolean mIsLoadData = false;
	private boolean mIsEndOfList = false;
	
	private boolean mIsShowAllProducts = false;
	private boolean mIsShowBelowStockLimitProducts = false;
	
	private String mQuery = Constant.EMPTY_STRING;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		if (!mIsShowBelowStockLimitProducts) {
			mIsShowAllProducts = true;
		}
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_inventory_list_fragment, container, false);
		
		if (mProducts == null) {
			mProducts = mProductDaoService.getProducts(Constant.EMPTY_STRING, 0);
		}
		
		mAdapter = new InventoryReportArrayAdapter(getActivity(), mProducts, this);
		
		if (mStockAlertProducts == null) {
			mStockAlertProducts = mProductDaoService.getBelowStockLimitProducts(Constant.EMPTY_STRING, 0);
		}
		
		mStockAlertAdapter = new InventoryReportArrayAdapter(getActivity(), mStockAlertProducts, this);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mTitleText = (TextView) getView().findViewById(R.id.titleText);
		
		mProductList = (ListView) getView().findViewById(R.id.transactionList);
		
		mProductList.setItemsCanFocus(true);
		mProductList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mProductList.setOnScrollListener(getListOnScrollListener());
		
		if (mIsShowAllProducts) {
			
			mTitleText.setText("Produk");
			mProductList.setAdapter(mAdapter);
			
		} else if (mIsShowBelowStockLimitProducts) {
			
			mTitleText.setText("Produk Dibawah Min. Stok");
			mProductList.setAdapter(mStockAlertAdapter);
		}
		
		if (mSelectedProduct != null) {
			onProductSelected(mSelectedProduct);	
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (InventoryReportActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement InventoryReportActionListener");
        }
    }
	
	public void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}
		
		mStockAlertAdapter.notifyDataSetChanged();
		mAdapter.notifyDataSetChanged();
	}
	
	public void setSelectedProduct(Product product) {
		
		mSelectedProduct = product;
		
		if (product == null) {
			return;
		}
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void showAllProducts() {
		
		mIsShowAllProducts = true;
		mIsShowBelowStockLimitProducts =  false;
		
		mTitleText.setText("Produk");
		mProductList.setAdapter(mAdapter);
	}
	
	public void showBelowStockLimitProducts() {
		
		mIsShowAllProducts = false;
		mIsShowBelowStockLimitProducts =  true;
		
		mTitleText.setText("Produk Dibawah Min. Stok");
		mProductList.setAdapter(mStockAlertAdapter);
	}
	
	public void searchProduct(String query) {
		
		mQuery = query;
		mIsEndOfList = false;
		
		if (!isViewInitialized()) {
			
			mSelectedProduct = null;
			return;
		}
		
		mActionListener.onProductUnselected();
		
		if (mIsShowAllProducts) { 
		
			mProducts.clear();
			mProducts.addAll(mProductDaoService.getGoodsProducts(mQuery, 0));
		
		} else if (mIsShowBelowStockLimitProducts) {
			
			mStockAlertProducts.clear();
			mStockAlertProducts.addAll(mProductDaoService.getBelowStockLimitProducts(mQuery, 0));
		}
			
		updateContent();
	}
	
	@Override
	public void onProductSelected(Product product) {
		
		mActionListener.onProductSelected(product);
	}
	
	@Override
	public Product getSelectedProduct() {
		
		return mSelectedProduct;
	}
	
	private AbsListView.OnScrollListener getListOnScrollListener() {
		
		return new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
				if (firstVisibleItem == 0) {
					return;
				}
				
				int lastInScreen = firstVisibleItem + visibleItemCount;
				
				if((lastInScreen == totalItemCount) && !mIsLoadData && !mIsEndOfList) {
					
					mIsLoadData = true;
					
					List<Product> list = null;
					
					if (mIsShowAllProducts) {
						list = mProductDaoService.getProducts(Constant.EMPTY_STRING, mProducts.size());
						
					} else if (mIsShowBelowStockLimitProducts) {
						list = mProductDaoService.getBelowStockLimitProducts(Constant.EMPTY_STRING, mStockAlertProducts.size());
					}
					
					if (list.size() == 0) {
						mIsEndOfList = true;
					}
					
					String message = Constant.EMPTY_STRING;
					
					if (mIsEndOfList) {
						message = "Tidak lagi terdapat data untuk ditampilkan!";
					} else {
						message = "Menampilkan data selanjutnya ...";
					}
					
					Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					
					if (mIsShowAllProducts) {
						
						mProducts.addAll(list);
						mAdapter.notifyDataSetChanged();
						
					} else if (mIsShowBelowStockLimitProducts) {
						
						mStockAlertProducts.addAll(list);
						mStockAlertAdapter.notifyDataSetChanged();
					}
					
					mIsLoadData = false;
				}
			}
		};
	}
}