package com.android.pos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.TransactionItemDao;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionsDao;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.model.TransactionDayBean;
import com.android.pos.model.TransactionMonthBean;
import com.android.pos.model.TransactionYearBean;
import com.android.pos.model.TransactionsBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class TransactionsDaoService {
	
	private TransactionsDao mTransactionsDao = DbUtil.getSession().getTransactionsDao();
	private TransactionItemDao transactionItemDao = DbUtil.getSession().getTransactionItemDao();
	
	public void addTransactions(Transactions transactions) {
		
		if (CommonUtil.isEmpty(transactions.getRefId())) {
			transactions.setRefId(CommonUtil.generateRefId());
		}
		
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
	
	public void updateTransactions(List<TransactionsBean> transactions) {
		
		DbUtil.getDb().beginTransaction();
		
		List<TransactionsBean> shiftedBeans = new ArrayList<TransactionsBean>();
		
		for (TransactionsBean bean : transactions) {
			
			boolean isAdd = false;
			
			Transactions transaction = mTransactionsDao.load(bean.getRemote_id());
			
			if (transaction == null) {
				transaction = new Transactions();
				isAdd = true;
			
			} else if (!CommonUtil.compareString(transaction.getRefId(), bean.getRef_id())) {
				TransactionsBean shiftedBean = BeanUtil.getBean(transaction);
				shiftedBeans.add(shiftedBean);
			}
			
			BeanUtil.updateBean(transaction, bean);
			
			if (isAdd) {
				mTransactionsDao.insert(transaction);
			} else {
				mTransactionsDao.update(transaction);
			}
		}
				
		for (TransactionsBean bean : shiftedBeans) {
			
			Transactions transaction = new Transactions();
			BeanUtil.updateBean(transaction, bean);
			
			Long oldId = transaction.getId();
			
			transaction.setId(null);
			transaction.setUploadStatus(Constant.STATUS_YES);
			
			Long newId = mTransactionsDao.insert(transaction);
			
			updateTransactionItemFk(oldId, newId);
		}
		
		DbUtil.getDb().setTransactionSuccessful();
		DbUtil.getDb().endTransaction();
	}
	
	private void updateTransactionItemFk(Long oldId, Long newId) {
		
		Transactions t = mTransactionsDao.load(oldId);
		
		for (TransactionItem ti : t.getTransactionItemList()) {
			ti.setTransactionId(newId);
			ti.setUploadStatus(Constant.STATUS_YES);
			transactionItemDao.update(ti);
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
				+ " WHERE status = 'A' "
				+ " GROUP BY strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime')", null);
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long amount = cursor.getLong(1);
			TransactionYearBean transactionYear = new TransactionYearBean();
			transactionYear.setYear(date);
			transactionYear.setAmount(amount);
			transactionYears.add(transactionYear);
		}
		
		cursor.close();
		
		return transactionYears;
	}
	
	public List<CashFlowYearBean> getCashFlowYears() {
		
		ArrayList<CashFlowYearBean> cashFlowYears = new ArrayList<CashFlowYearBean>();
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount - tax_amount - service_charge_amount) income "
				+ " FROM transactions "
				+ " WHERE status = 'A' "
				+ " GROUP BY strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime')", null);
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long amount = cursor.getLong(1);
			CashFlowYearBean transactionYear = new CashFlowYearBean();
			transactionYear.setYear(date);
			transactionYear.setAmount(amount);
			cashFlowYears.add(transactionYear);
		}
		
		cursor.close();
		
		return cashFlowYears;
	}
	
	public List<TransactionMonthBean> getTransactionMonths(TransactionYearBean transactionYear) {
		
		ArrayList<TransactionMonthBean> transactionMonths = new ArrayList<TransactionMonthBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(transactionYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(transactionYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount) total_amount "
				+ " FROM transactions "
				+ " WHERE status = 'A' AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime')", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "MM-yyyy");
			Long amount = cursor.getLong(1);
			TransactionMonthBean transactionMonth = new TransactionMonthBean();
			transactionMonth.setMonth(date);
			transactionMonth.setAmount(amount);
			transactionMonths.add(transactionMonth);
		}
		
		cursor.close();
		
		return transactionMonths;
	}
	
	public List<CashFlowMonthBean> getCashFlowMonths(CashFlowYearBean cashFlowYear) {
		
		ArrayList<CashFlowMonthBean> cashFlowMonths = new ArrayList<CashFlowMonthBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(cashFlowYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(cashFlowYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount - tax_amount - service_charge_amount) income "
				+ " FROM transactions "
				+ " WHERE status = 'A' AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime')", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "MM-yyyy");
			Long amount = cursor.getLong(1);
			CashFlowMonthBean cashFlowMonth = new CashFlowMonthBean();
			cashFlowMonth.setMonth(date);
			cashFlowMonth.setAmount(amount);
			cashFlowMonths.add(cashFlowMonth);
		}
		
		cursor.close();
		
		return cashFlowMonths;
	}
	
	public List<Long> getMonthIncome(Date month) {
		
		List<Long> income = new ArrayList<Long>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(month).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(month).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT SUM(total_amount), SUM(tax_amount), SUM(service_charge_amount) "
				+ " FROM transactions "
				+ " WHERE status = 'A' AND transaction_date BETWEEN ? AND ? "
				, new String[] { startDate, endDate });
		
		if (cursor.moveToFirst()) {
			
			income.add(cursor.getLong(0));
			income.add(cursor.getLong(1));
			income.add(cursor.getLong(2));
			
		} else {
			
			long zero = 0;
			
			income.add(zero);
			income.add(zero);
			income.add(zero);
		}
		
		cursor.close();
		
		return income;
	}
	
	public List<TransactionDayBean> getTransactionDays(TransactionMonthBean transactionMonth) {
		
		ArrayList<TransactionDayBean> transactionDays = new ArrayList<TransactionDayBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(transactionMonth.getMonth()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(transactionMonth.getMonth()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(total_amount) total_amount "
				+ " FROM transactions " 
				+ " WHERE status = 'A' AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime')", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "dd-MM-yyyy");
			Long amount = cursor.getLong(1);
			TransactionDayBean summary = new TransactionDayBean();
			summary.setDate(date);
			summary.setAmount(amount);
			transactionDays.add(summary);
		}
		
		cursor.close();
		
		return transactionDays;
	}
	
	public List<Transactions> getTransactions(Date transactionDate) {
		
		Date startDate = transactionDate;
		Date endDate = new Date(startDate.getTime() + 24 * 60 * 60 * 1000 - 1);
		
		QueryBuilder<Transactions> qb = mTransactionsDao.queryBuilder();
		qb.where(TransactionsDao.Properties.Status.eq(Constant.STATUS_ACTIVE),
				TransactionsDao.Properties.TransactionDate.between(startDate, endDate))
		  .orderAsc(TransactionsDao.Properties.TransactionDate);

		Query<Transactions> q = qb.build();
		List<Transactions> list = q.list();
		
		return list;
	}
	
	public boolean hasUpdate() {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM transactions WHERE upload_status = 'Y'", null);
			
		cursor.moveToFirst();
			
		Long count = cursor.getLong(0);
		
		cursor.close();
		
		return (count > 0);
	}
}
