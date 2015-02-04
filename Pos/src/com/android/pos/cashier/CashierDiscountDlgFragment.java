package com.android.pos.cashier;

import java.util.List;

import com.android.pos.R;
import com.android.pos.dao.Discount;
import com.android.pos.dao.DiscountDao;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CashierDiscountDlgFragment extends DialogFragment implements CashierDiscountArrayAdapter.ItemActionListener {
	
	ListView mDiscountListView;
	
	TextView mNoDiscountText;
	
	CashierActionListener mActionListener;
	
	CashierDiscountArrayAdapter discountArrayAdapter;
	
	private DiscountDao mDiscountDao = DbUtil.getSession().getDiscountDao();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        
        setCancelable(false);
        
        discountArrayAdapter = new CashierDiscountArrayAdapter(getActivity(), getDiscounts(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cashier_discount_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		
		mDiscountListView = (ListView) getView().findViewById(R.id.discountListView);
		mDiscountListView.setAdapter(discountArrayAdapter);
		
		mNoDiscountText = (TextView) getView().findViewById(R.id.noDiscountText);
		mNoDiscountText.setOnClickListener(getNoDiscountTextOnClickListener());
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
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	public void onDiscountSelected(Discount discount) {
		
		mActionListener.onDiscountSelected(discount);
		dismiss();
	}
	
	private List<Discount> getDiscounts() {

		QueryBuilder<Discount> qb = mDiscountDao.queryBuilder();
		qb.orderAsc(DiscountDao.Properties.Name);

		Query<Discount> q = qb.build();
		List<Discount> list = q.list();
		
		return list;
	}
	
	private View.OnClickListener getNoDiscountTextOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onDiscountSelected(null);
				dismiss();
			}
		};
	}
}
