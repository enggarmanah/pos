package com.android.pos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionsDao;
import com.android.pos.model.TransactionDayBean;
import com.android.pos.model.TransactionMonthBean;
import com.android.pos.model.TransactionYearBean;
import com.android.pos.model.TransactionsBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class TransactionsDaoService {
	
	private TransactionsDao mTransactionsDao = DbUtil.getSession().getTransactionsDao();
	
	public void addTransactions(Transactions transactions) {
		
		mTransactionsDao.insert(transactions);
	}
	
	public void updateTransactions(Transactions transactions) {
		
		mTransactionsDao.update(transactions);
	}
	
	public void deleteTransactions(Transactions transactions) {

		Transactions entity = mTransactionsDao.load(transactions.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		mTransactionsDao.update(entity);
	}
	
	public Transactions getTransactions(Long id) {
		
		return mTransactionsDao.load(id);
	}
	
	public List<TransactionsBean> getTransactionsForUpload() {

		QueryBuilder<Transactions> qb = mTransactionsDao.queryBuilder();
		qb.where(TransactionsDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(TransactionsDao.Properties.Id);
		
		Query<Transactions> q = qb.build();
		
		ArrayList<TransactionsBean> transactionsBeans = new ArrayList<TransactionsBean>();
		
		for (Transactions prdGroup : q.list()) {
			
			transactionsBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return transactionsBeans;
	}
	
	public void updateTransactions(List<TransactionsBean> transactionss) {
		
		for (TransactionsBean bean : transactionss) {
			
			boolean isAdd = false;
			
			Transactions transactions = mTransactionsDao.load(bean.getRemote_id());
			
			if (transactions == null) {
				transactions = new Transactions();
				isAdd = true;
			}
			
			BeanUtil.updateBean(transactions, bean);
			
			if (isAdd) {
				mTransactionsDao.insert(transactions);
			} else {
				mTransactionsDao.update(transactions);
			}
		} 
	}
	
	public void updateTransactionsStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Transactions transactions = mTransactionsDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				transactions.setUploadStatus(Constant.STATUS_NO);
				mTransactionsDao.update(transactions);
			}
		} 
	}
	
	public List<TransactionYearBean> getTransactionYears() {
		
		ArrayList<TransactionYearBean> transactionYears = new ArrayList<TransactionYearBean>();
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount) total_amount "
				+ " FROM transactions "
				+ " GROUP BY strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime')", null);
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long amount = cursor.getLong(1);
			TransactionYearBean transactionYear = new TransactionYearBean();
			transactionYear.setYear(date);
			transactionYear.setAmount(amount);
			transactionYears.add(transactionYear);
		}
		
		return transactionYears;
	}
	
	public List<TransactionMonthBean> getTransactionMonths(TransactionYearBean transactionYear) {
		
		ArrayList<TransactionMonthBean> transactionMonths = new ArrayList<TransactionMonthBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(transactionYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(transactionYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount) total_amount "
				+ " FROM transactions "
				+ " WHERE transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime')", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "MM-yyyy");
			Long amount = cursor.getLong(1);
			TransactionMonthBean transactionMonth = new TransactionMonthBean();
			transactionMonth.setMonth(date);
			transactionMonth.setAmount(amount);
			transactionMonths.add(transactionMonth);
		}
		
		return transactionMonths;
	}
	
	public List<TransactionDayBean> getTransactionDays(TransactionMonthBean transactionMonth) {
		
		ArrayList<TransactionDayBean> transactionDays = new ArrayList<TransactionDayBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(transactionMonth.getMonth()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(transactionMonth.getMonth()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount) total_amount "
				+ " FROM transactions " 
				+ " WHERE transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime')", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "dd-MM-yyyy");
			Long amount = cursor.getLong(1);
			TransactionDayBean summary = new TransactionDayBean();
			summary.setDate(date);
			summary.setAmount(amount);
			transactionDays.add(summary);
		}
		
		return transactionDays;
	}
	
	public List<Transactions> getTransactions(Date transactionDate) {
		
		Date startDate = transactionDate;
		Date endDate = new Date(startDate.getTime() + 24 * 60 * 60 * 1000 - 1);
		
		QueryBuilder<Transactions> qb = mTransactionsDao.queryBuilder();
		qb.where(TransactionsDao.Properties.TransactionDate.between(startDate, endDate))
		  .orderAsc(TransactionsDao.Properties.TransactionDate);

		Query<Transactions> q = qb.build();
		List<Transactions> list = q.list();
		
		return list;
	}
}