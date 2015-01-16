package com.android.pos.cashier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDao;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.ProductGroupDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CashierProductSearchFragment extends BaseFragment 
	implements CashierProductGroupArrayAdapter.ItemActionListener,
	CashierProductArrayAdapter.ItemActionListener {
	
	private TextView mNavigationTitle;
	private TextView mUpButton;
	
	private ListView mProductGroupList;
	private List<ProductGroup> mProductGroups;
	private ProductGroup mSelectedPrdGroup;
	private List<Product> mProducts;
	
	private CashierProductGroupArrayAdapter mProductGroupAdapter;
	private CashierProductArrayAdapter mProductAdapter;
	
	private CashierActionListener mActionListener;
	
	private ProductGroupDao productGroupDao = DbHelper.getSession().getProductGroupDao();
	private ProductDao productDao = DbHelper.getSession().getProductDao();
	
	private static String PRODUCT_GROUPS = "PRODUCT_GROUPS";
	private static String SELECTED_PRODUCT_GROUP = "SELECTED_PRODUCT_GROUP";
	private static String PRODUCTS = "PRODUCTS";
	
	private String mSearchQuery;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mProductGroups = (List<ProductGroup>) savedInstanceState.getSerializable(PRODUCT_GROUPS);
			mProducts = (List<Product>) savedInstanceState.getSerializable(PRODUCTS);
			mSelectedPrdGroup = (ProductGroup) savedInstanceState.getSerializable(SELECTED_PRODUCT_GROUP);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		outState.putSerializable(PRODUCT_GROUPS, (Serializable) mProductGroups);
		outState.putSerializable(PRODUCTS, (Serializable) mProducts);
		outState.putSerializable(SELECTED_PRODUCT_GROUP, mSelectedPrdGroup);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_product_search_fragment, container, false);
		
		if (mProductGroups == null) {
			mProductGroups = getProductGroups(Constant.EMPTY_STRING);
		}
		
		if (mSearchQuery != null) {
			mProducts = getProducts(mSearchQuery);
		}
		
		if (mProducts == null) {
			mProducts = new ArrayList<Product>();
		}
		
		mProductGroupAdapter = new CashierProductGroupArrayAdapter(getActivity(), mProductGroups, this);
		mProductAdapter = new CashierProductArrayAdapter(getActivity(), mProducts, this);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mNavigationTitle = (TextView) getActivity().findViewById(R.id.navigationTitle);
		mUpButton = (TextView) getActivity().findViewById(R.id.upButton);
		mProductGroupList = (ListView) getActivity().findViewById(R.id.productGroupList);
		
		if (mProducts.size() > 0) {
			mProductGroupList.setAdapter(mProductAdapter);
		} else {
			mProductGroupList.setAdapter(mProductGroupAdapter);
		}
		
		mProductGroupList.setItemsCanFocus(true);
		mProductGroupList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		mUpButton.setOnClickListener(getUpButtonOnClickListener());
		
		refreshNavigationPanel();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (CashierActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CashierActionListener");
        }
    }
	
	private void refreshNavigationPanel() {
		
		if (mSelectedPrdGroup != null) {
			mNavigationTitle.setText(mSelectedPrdGroup.getName());
			mUpButton.setVisibility(View.VISIBLE);
		
		} else if (mSearchQuery != null) {
			mNavigationTitle.setText(getString(R.string.product_search));
			mUpButton.setVisibility(View.VISIBLE);
			
		} else {
			mNavigationTitle.setText(getString(R.string.product_group_list));
			mUpButton.setVisibility(View.INVISIBLE);
		}
	}
	
	
	public List<ProductGroup> getProductGroups(String query) {

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.where(ProductGroupDao.Properties.Name.like("%" + query + "%")).orderAsc(ProductGroupDao.Properties.Name);

		Query<ProductGroup> q = qb.build();
		List<ProductGroup> list = q.list();
		
		return list;
	}
	
	public void searchProducts(String query) {
		
		mSearchQuery = query;
		
		if (!isViewInitialized()) {
			return;
		}
		
		mSelectedPrdGroup = null;
		
		mProducts.clear();
		mProducts.addAll(getProducts(query));
		
		mProductGroupList.setAdapter(mProductAdapter);
		
		refreshNavigationPanel(); 
	}
	
	public List<Product> getProducts(String query) {
		
		mSearchQuery = query;

		QueryBuilder<Product> qb = productDao.queryBuilder();
		
		if (mSelectedPrdGroup == null) {
			qb.where(ProductDao.Properties.Name.like("%" + query + "%")).orderAsc(ProductDao.Properties.Name);
		} else {
			qb.where(ProductDao.Properties.Name.like("%" + query + "%"), ProductDao.Properties.ProductGroupId.eq(mSelectedPrdGroup.getId())).orderAsc(ProductDao.Properties.Name);
		}

		Query<Product> q = qb.build();
		List<Product> list = q.list();
		
		return list;
	}
	
	public void showProductGroups() {
		
		if (!isViewInitialized()) {
			return;
		}
		
		mSearchQuery = null;
		mSelectedPrdGroup = null;
		
		mProductGroupList.setAdapter(mProductGroupAdapter);
		
		refreshNavigationPanel();
	}
	
	@Override
	public void onProductGroupSelected(ProductGroup prdGroup) {
		
		System.out.println("Product Group : " + prdGroup.getName());
		
		mSelectedPrdGroup = prdGroup;
		
		mProducts.clear();
		mProducts.addAll(getProducts(Constant.EMPTY_STRING));
		
		mProductGroupList.setAdapter(mProductAdapter);
		
		refreshNavigationPanel();
	}
	
	@Override
	public void onProductSelected(Product product) {
		
		System.out.println("Product : " + product.getName());
		mActionListener.onProductSelected(product, 0);
	}
	
	private View.OnClickListener getUpButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onShowProductGroups();
			}
		};
	}
}