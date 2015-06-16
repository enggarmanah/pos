package com.android.pos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Bills;
import com.android.pos.dao.BillsDao;
import com.android.pos.dao.Inventory;
import com.android.pos.dao.InventoryDao;
import com.android.pos.model.BillsBean;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class BillsDaoService {
	
	private BillsDao billsDao = DbUtil.getSession().getBillsDao();
	private InventoryDao inventoryDao = DbUtil.getSession().getInventoryDao();
	
	public void addBills(Bills bills) {
		
		if (CommonUtil.isEmpty(bills.getRefId())) {
			bills.setRefId(CommonUtil.generateRefId());
		}
		
		billsDao.insert(bills);
	}
	
	public void updateBills(Bills bills) {
		
		billsDao.update(bills);
		
		for (Inventory i : bills.getInventoryList()) {
			i.setBillReferenceNo(bills.getBillReferenceNo());
			i.setUploadStatus(Constant.STATUS_YES);
			inventoryDao.update(i);
		}
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
				+ " ORDER BY bill_date DESC, bill_reference_no ASC LIMIT ? OFFSET ? ",
				new String[] { queryStr, queryStr, queryStr, status, limit, lastIdx});
		
		List<Bills> list = new ArrayList<Bills>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Bills item = getBills(id);
			list.add(item);
		}
		
		cursor.close();

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
		
		cursor.close();
		
		return list;
	}
	
	public List<Bills> getPastDueBills() {

		SQLiteDatabase db = DbUtil.getDb();
		
		String today = String.valueOf(new Date().getTime());
		String status = Constant.STATUS_DELETED;
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM bills "
				+ " WHERE payment < bill_amount AND bill_due_date <= ? AND status <> ? "
				+ " ORDER BY bill_date ",
				new String[] { today, status});
		
		List<Bills> list = new ArrayList<Bills>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Bills item = getBills(id);
			list.add(item);
		}
		
		cursor.close();

		return list;
	}
	
	public Integer getPastDueBillsCount() {
		
		Integer pastDueBillsCount = 0;
		
		SQLiteDatabase db = DbUtil.getDb();
		
		String today = String.valueOf(new Date().getTime());
		String status = Constant.STATUS_DELETED;
		
		Cursor cursor = db.rawQuery("SELECT count(_id) "
				+ " FROM bills "
				+ " WHERE payment < bill_amount AND bill_due_date <= ? AND status <> ? "
				+ " ORDER BY bill_date ",
				new String[] { today, status});
		
		if (cursor.moveToNext()) {
			
			pastDueBillsCount = cursor.getInt(0);
		}
		
		cursor.close();

		return pastDueBillsCount;
	}
	
	public List<Bills> getOutstandingBills() {

		SQLiteDatabase db = DbUtil.getDb();
		
		String status = Constant.STATUS_DELETED;
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM bills "
				+ " WHERE payment < bill_amount AND status <> ? "
				+ " ORDER BY bill_date ",
				new String[] { status });
		
		List<Bills> list = new ArrayList<Bills>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Bills item = getBills(id);
			list.add(item);
		}
		
		cursor.close();

		return list;
	}
	
	public Integer getOutstandingBillsCount() {
		
		Integer pastDueBillsCount = 0;
		
		SQLiteDatabase db = DbUtil.getDb();
		
		String status = Constant.STATUS_DELETED;
		
		Cursor cursor = db.rawQuery("SELECT count(_id) "
				+ " FROM bills "
				+ " WHERE payment < bill_amount AND status <> ? "
				+ " ORDER BY bill_due_date ",
				new String[] { status });
		
		if (cursor.moveToNext()) {
			
			pastDueBillsCount = cursor.getInt(0);
		}
		
		cursor.close();

		return pastDueBillsCount;
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
		
		cursor.close();

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
	
	public void updateBills(List<BillsBean> bills) {
		
		DbUtil.getDb().beginTransaction();
		
		List<BillsBean> shiftedBeans = new ArrayList<BillsBean>();
		
		for (BillsBean bean : bills) {
			
			boolean isAdd = false;
			
			Bills bill = billsDao.load(bean.getRemote_id());
			
			if (bill == null) {
				bill = new Bills();
				isAdd = true;
			
			} else if (!CommonUtil.compareString(bill.getRefId(), bean.getRef_id())) {
				BillsBean shiftedBean = BeanUtil.getBean(bill);
				shiftedBeans.add(shiftedBean);
			}
			
			BeanUtil.updateBean(bill, bean);
			
			if (isAdd) {
				billsDao.insert(bill);
			} else {
				billsDao.update(bill);
			}
		}
		
		for (BillsBean bean : shiftedBeans) {
			
			Bills bill = new Bills();
			BeanUtil.updateBean(bill, bean);
			
			Long oldId = bill.getId();
			
			bill.setId(null);
			bill.setUploadStatus(Constant.STATUS_YES);
			
			Long newId = billsDao.insert(bill);
			
			updateInventoryFk(oldId, newId);
		}
		
		DbUtil.getDb().setTransactionSuccessful();
		DbUtil.getDb().endTransaction();
	}
	
	private void updateInventoryFk(Long oldId, Long newId) {
		
		Bills oldBill = billsDao.load(oldId);
		
		for (Inventory i : oldBill.getInventoryList()) {
			i.setBillId(newId);
			i.setUploadStatus(Constant.STATUS_YES);
			inventoryDao.update(i);
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
		
		cursor.close();
		
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
		
		cursor.close();
		
		return cashFlowMonths;
	}
	
	public boolean hasUpdate() {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM bills WHERE upload_status = 'Y'", null);
			
		cursor.moveToFirst();
			
		Long count = cursor.getLong(0);
		
		cursor.close();
		
		return (count > 0);
	}
}
