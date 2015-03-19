package com.android.pos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Bills;
import com.android.pos.dao.BillsDao;
import com.android.pos.model.BillsBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class BillsDaoService {
	
	private BillsDao billsDao = DbUtil.getSession().getBillsDao();
	
	public void addBills(Bills bills) {
		
		billsDao.insert(bills);
	}
	
	public void updateBills(Bills bills) {
		
		billsDao.update(bills);
	}
	
	public void deleteBills(Bills bills) {

		Bills entity = billsDao.load(bills.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		billsDao.update(entity);
	}
	
	public Bills getBills(Long id) {
		
		return billsDao.load(id);
	}
	
	public List<Bills> getBills(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = CommonUtil.getSqlLikeString(query);
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM bills "
				+ " WHERE (bill_reference_no like ? OR supplier_name like ? OR remarks like ? ) AND status <> ? "
				+ " ORDER BY bill_date DESC LIMIT ? OFFSET ? ",
				new String[] { queryStr, queryStr, queryStr, status, limit, lastIdx});
		
		List<Bills> list = new ArrayList<Bills>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Bills item = getBills(id);
			list.add(item);
		}

		return list;
	}
	
	public List<Bills> getBills(Date month, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(month).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(month).getTime());
		
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM bills "
				+ " WHERE payment_date BETWEEN ? AND ? AND status <> ? "
				+ " ORDER BY bill_date LIMIT ? OFFSET ? ",
				new String[] { startDate, endDate, status, limit, lastIdx});
		
		List<Bills> list = new ArrayList<Bills>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Bills item = getBills(id);
			list.add(item);
		}

		return list;
	}
	
	public List<Bills> getProductPurchaseBills(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = CommonUtil.getSqlLikeString(query);
		String type = Constant.BILL_TYPE_PRODUCT_PURCHASE;
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM bills "
				+ " WHERE (bill_reference_no like ? OR supplier_name like ? OR remarks like ? ) AND bill_type = ? AND status <> ? "
				+ " ORDER BY delivery_date DESC LIMIT ? OFFSET ? ",
				new String[] { queryStr, queryStr, queryStr, type, status, limit, lastIdx});
		
		List<Bills> list = new ArrayList<Bills>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Bills item = getBills(id);
			list.add(item);
		}

		return list;
	}
	
	public List<BillsBean> getBillsForUpload() {

		QueryBuilder<Bills> qb = billsDao.queryBuilder();
		qb.where(BillsDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(BillsDao.Properties.DeliveryDate);
		
		Query<Bills> q = qb.build();
		
		ArrayList<BillsBean> billsBeans = new ArrayList<BillsBean>();
		
		for (Bills bills : q.list()) {
			
			billsBeans.add(BeanUtil.getBean(bills));
		}
		
		return billsBeans;
	}
	
	public void updateBills(List<BillsBean> billss) {
		
		for (BillsBean bean : billss) {
			
			boolean isAdd = false;
			
			Bills bills = billsDao.load(bean.getRemote_id());
			
			if (bills == null) {
				bills = new Bills();
				isAdd = true;
			}
			
			BeanUtil.updateBean(bills, bean);
			
			if (isAdd) {
				billsDao.insert(bills);
			} else {
				billsDao.update(bills);
			}
		} 
	}
	
	public void updateBillsStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Bills bills = billsDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				bills.setUploadStatus(Constant.STATUS_NO);
				billsDao.update(bills);
			}
		} 
	}
	
	public List<CashFlowYearBean> getBillYears() {
		
		ArrayList<CashFlowYearBean> cashFlowYears = new ArrayList<CashFlowYearBean>();
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%Y', payment_date/1000, 'unixepoch', 'localtime'), SUM(payment) payment "
				+ " FROM bills "
				+ " WHERE payment_date IS NOT NULL AND payment IS NOT NULL "
				+ " GROUP BY strftime('%Y', payment_date/1000, 'unixepoch', 'localtime')", null);
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long value = cursor.getLong(1);
			CashFlowYearBean cashFlowYear = new CashFlowYearBean();
			cashFlowYear.setYear(date);
			cashFlowYear.setAmount(value);
			cashFlowYears.add(cashFlowYear);
		}
		
		return cashFlowYears;
	}
	
	public List<CashFlowMonthBean> getBillMonths(CashFlowYearBean cashFlowYear) {
		
		ArrayList<CashFlowMonthBean> cashFlowMonths = new ArrayList<CashFlowMonthBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(cashFlowYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(cashFlowYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%m-%Y', payment_date/1000, 'unixepoch', 'localtime'), SUM(payment) payment "
				+ " FROM bills "
				+ " WHERE payment_date BETWEEN ? AND ? AND payment IS NOT NULL "
				+ " GROUP BY strftime('%m-%Y', payment_date/1000, 'unixepoch', 'localtime') ", 
				new String[] { startDate, endDate });
		
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "MM-yyyy");
			Long value = cursor.getLong(1);
			CashFlowMonthBean cashFlowMonth = new CashFlowMonthBean();
			cashFlowMonth.setMonth(date);
			cashFlowMonth.setAmount(value);
			cashFlowMonths.add(cashFlowMonth);
		}
		
		return cashFlowMonths;
	}
}
