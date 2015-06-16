package com.android.pos.report.commision;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.Employee;
import com.android.pos.dao.ProductDaoService;
import com.android.pos.model.CommisionMonthBean;
import com.android.pos.model.EmployeeCommisionBean;
import com.android.pos.util.CommonUtil;

public class CommisionDetailFragment extends BaseFragment implements CommisionDetailArrayAdapter.ItemActionListener {
	
	private ImageButton mBackButton;
	
	private TextView mInfoText;
	private TextView mDateText;
	
	protected ListView mEmployeeCommisionList;

	protected CommisionMonthBean mCommisionMonth;
	protected Employee mEmployee;
	
	protected List<EmployeeCommisionBean> mEmployeeCommisions;
	protected List<EmployeeCommisionBean> mEmployeeCommisionDetails;
	
	private CommisionActionListener mActionListener;
	
	private CommisionDetailArrayAdapter mCommisionAdapter;
	private CommisionDetailEmployeeArrayAdapter mCommisionDetailAdapter;
	
	private ProductDaoService mProductDaoService = new ProductDaoService();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_commision_detail_fragment, container, false);
		
		if (mEmployeeCommisions == null) {
			mEmployeeCommisions = new ArrayList<EmployeeCommisionBean>();
		}
		
		if (mEmployeeCommisionDetails == null) {
			mEmployeeCommisionDetails = new ArrayList<EmployeeCommisionBean>();
		}
		
		mCommisionAdapter = new CommisionDetailArrayAdapter(getActivity(), mEmployeeCommisions, this);
		mCommisionDetailAdapter = new CommisionDetailEmployeeArrayAdapter(getActivity(), mEmployeeCommisionDetails);
		
		return view;
	}
	
	private void initViewVariables() {
		
		mEmployeeCommisionList = (ListView) getView().findViewById(R.id.employeeCommisionList);

		mEmployeeCommisionList.setAdapter(mCommisionAdapter);
		mEmployeeCommisionList.setItemsCanFocus(true);
		mEmployeeCommisionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		mBackButton = (ImageButton) getView().findViewById(R.id.backButton);
		mBackButton.setOnClickListener(getBackButtonOnClickListener());
		
		mDateText = (TextView) getView().findViewById(R.id.dateText);
		mInfoText = (TextView) getView().findViewById(R.id.infoText);
	}
	
	private void initBackButton() {
		
		boolean isMultiplesPane = getResources().getBoolean(R.bool.has_multiple_panes);
		
		if (isMultiplesPane && mEmployee == null) {
			mBackButton.setVisibility(View.GONE);
		} else {
			mBackButton.setVisibility(View.VISIBLE);
		}
	}
	
	public void onStart() {
		super.onStart();
		
		initViewVariables();
		updateContent();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (CommisionActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TransactionActionListener");
        }
    }
	
	@Override
	public void onEmployeeSelected(Employee employee) {
		
		mEmployee = employee;
		
		mActionListener.onEmployeeSelected(employee);
		
		updateContent();
	}
	
	public void setEmployee(Employee employee) {
		
		mEmployee = employee;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void setCommisionMonth(CommisionMonthBean transactionMonth) {
		
		mCommisionMonth = transactionMonth;
		mEmployee = null;
		
		if (isViewInitialized()) {
			updateContent();
		}
	}
	
	public void updateContent() {
		
		if (mEmployee != null) {
			
			mEmployeeCommisionDetails.clear();
			
			mEmployeeCommisionDetails.addAll(mProductDaoService.getEmployeeCommisions(mCommisionMonth, mEmployee));
			mInfoText.setText(mEmployee.getName());
			
			mCommisionDetailAdapter.notifyDataSetChanged();
			
			mEmployeeCommisionList.setAdapter(mCommisionDetailAdapter);
			
		} else if (mCommisionMonth != null) {
			
			mEmployeeCommisions.clear();
			
			mEmployeeCommisions.addAll(mProductDaoService.getEmployeeCommisions(mCommisionMonth));
			mInfoText.setText(getString(R.string.report_commision_amount));
			
			mCommisionAdapter.notifyDataSetChanged();
			
			mEmployeeCommisionList.setAdapter(mCommisionAdapter);
			
			getView().setVisibility(View.VISIBLE);
		
		} else {
		
			getView().setVisibility(View.INVISIBLE);
			return;
		}
		
		mDateText.setText(CommonUtil.formatMonth(mCommisionMonth.getMonth()));
		
		mCommisionAdapter.notifyDataSetChanged();
		
		initBackButton();
	}
	
	private View.OnClickListener getBackButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mActionListener.onBackPressed();
			}
		};
	}
}