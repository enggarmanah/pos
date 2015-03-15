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
import android.widget.Toast;

public class InventoryReportListFragment extends BaseFragment 
	implements InventoryReportArrayAdapter.ItemActionListener {
	
	private ListView mProductList;
	
	private List<Product> mProducts;
	
	private Product mSelectedProduct;
	
	private InventoryReportArrayAdapter mAdapter;
	
	private InventoryReportActionListener mActionListener;
	
	private boolean mIsLoadData = false;
	private boolean mIsEndOfList = false;
	
	private String mQuery = Constant.EMPTY_STRING;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
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
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mProductList = (ListView) getActivity().findViewById(R.id.transactionList);
		
		mProductList.setItemsCanFocus(true);
		mProductList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mProductList.setAdapter(mAdapter);
		mProductList.setOnScrollListener(getListOnScrollListener());
		
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
	
	private void updateContent() {
		
		if (!isViewInitialized()) {
			return;
		}
		
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
	
	public void searchProduct(String query) {
		
		mQuery = query;
		mIsEndOfList = false;
		
		if (!isViewInitialized()) {
			
			mSelectedProduct = null;
			return;
		}
		
		mActionListener.onProductUnselected();
		
		mProducts.clear();
		mProducts.addAll(mProductDaoService.getProducts(mQuery, 0));
		
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
					
					List<Product> list = mProductDaoService.getProducts(Constant.EMPTY_STRING, mProducts.size());
					
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
						
					mProducts.addAll(list);
					mAdapter.notifyDataSetChanged();
					
					mIsLoadData = false;
				}
			}
		};
	}
}