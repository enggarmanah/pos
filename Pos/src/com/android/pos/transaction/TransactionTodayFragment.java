package com.android.pos.transaction;

import java.io.Serializable;
import java.util.List;

import com.android.pos.DbHelper;
import com.android.pos.R;
import com.android.pos.base.fragment.BaseFragment;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionsDao;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class TransactionTodayFragment extends BaseFragment 
	implements TransactionTodayArrayAdapter.ItemActionListener {
	
	private TextView mNavigationTitle;
	private TextView mUpButton;
	
	private ListView mTransactionList;
	private List<Transactions> mTransactions;
	private Transactions mSelectedTransaction;
	
	private TransactionTodayArrayAdapter mTransactionTodayAdapter;
	
	private TransactionActionListener mActionListener;
	
	private TransactionsDao transactionDao = DbHelper.getSession().getTransactionsDao();
	
	private static String TRANSACTIONS = "TRANSACTIONS";
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mTransactions = (List<Transactions>) savedInstanceState.getSerializable(TRANSACTIONS);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		outState.putSerializable(TRANSACTIONS, (Serializable) mTransactions);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.transaction_today_fragment, container, false);
		
		if (mTransactions == null) {
			mTransactions = getTransactions();
		}
		
		mTransactionTodayAdapter = new TransactionTodayArrayAdapter(getActivity(), mTransactions, this);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mNavigationTitle = (TextView) getActivity().findViewById(R.id.navigationTitle);
		mUpButton = (TextView) getActivity().findViewById(R.id.upButton);
		mTransactionList = (ListView) getActivity().findViewById(R.id.transactionList);
		
		mTransactionList.setAdapter(mTransactionTodayAdapter);
		
		mTransactionList.setItemsCanFocus(true);
		mTransactionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		mUpButton.setOnClickListener(getUpButtonOnClickListener());
		
		refreshNavigationPanel();
		refreshTransactions();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActionListener = (TransactionActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TransactionActionListener");
        }
    }
	
	private void refreshNavigationPanel() {
		
		mNavigationTitle.setText(getString(R.string.transaction_list));
	}
	
	private void refreshTransactions() {
		
		mTransactions.clear();
		mTransactions.addAll(getTransactions());
	}
	
	public List<Transactions> getTransactions() {

		QueryBuilder<Transactions> qb = transactionDao.queryBuilder();
		qb.orderAsc(TransactionsDao.Properties.Id);

		Query<Transactions> q = qb.build();
		List<Transactions> list = q.list();
		
		return list;
	}
	
	@Override
	public void onTransactionSelected(Transactions transaction) {
		
		mSelectedTransaction = transaction;
		mActionListener.onTransactionSelected(transaction);
		mTransactionTodayAdapter.notifyDataSetChanged();
	}
	
	@Override
	public Transactions getSelectedTransaction() {
		
		return mSelectedTransaction;
	}
	
	private View.OnClickListener getUpButtonOnClickListener() {
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//mActionListener.onShowTransactionThisMonth();
			}
		};
	}
}