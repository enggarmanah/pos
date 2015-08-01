package com.android.pos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Cashflow;
import com.android.pos.dao.CashflowDao;
import com.android.pos.model.CashFlowMonthBean;
import com.android.pos.model.CashFlowYearBean;
import com.android.pos.model.CashflowBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class CashflowDaoService {
	
	private CashflowDao cashflowDao = DbUtil.getSession().getCashflowDao();
	
	public void addCashflow(Cashflow cashflow) {
		
		if (CommonUtil.isEmpty(cashflow.getRefId())) {
			cashflow.setRefId(CommonUtil.generateRefId());
		}
		
		cashflowDao.insert(cashflow);
	}
	
	public void updateCashflow(Cashflow cashflow) {
		
		cashflowDao.update(cashflow);
	}
	
	public void deleteCashflow(Cashflow cashflow) {

		Cashflow entity = cashflowDao.load(cashflow.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		cashflowDao.update(entity);
	}
	
	public Cashflow getCashflow(Long id) {
		
		return cashflowDao.load(id);
	}
	
	public List<Cashflow> getCashflows(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = CommonUtil.getSqlLikeString(query);
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT c._id "
				+ " FROM "
				+ "   cashflow c "
				+ "   LEFT OUTER JOIN bills b ON c.bill_id = b._id "
				+ "   LEFT OUTER JOIN transactions t ON c.transaction_id = t._id "
				+ " WHERE "
				+ "   (b.bill_reference_no like ? OR b.supplier_name like ? OR t.customer_name like ? OR c.remarks like ? ) AND c.status <> ? "
				+ " ORDER BY c.cash_date DESC LIMIT ? OFFSET ? ",
				new String[] { queryStr, queryStr, queryStr, queryStr, status, limit, lastIdx});
		
		List<Cashflow> list = new ArrayList<Cashflow>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Cashflow item = getCashflow(id);
			list.add(item);
		}
		
		cursor.close();
		
		return list;
	}
	
	public List<Cashflow> getCashflows(Bills bill) {

		QueryBuilder<Cashflow> qb = cashflowDao.queryBuilder();
		qb.where(CashflowDao.Properties.BillId.eq(bill.getId()), 
				CashflowDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(CashflowDao.Properties.CashDate);
		
		Query<Cashflow> q = qb.build();
		
		ArrayList<Cashflow> cashflows = new ArrayList<Cashflow>();
		
		for (Cashflow cashflow : q.list()) {
			cashflows.add(cashflow);
		}
		
		return cashflows;
	}
	
	public List<CashflowBean> getCashflowForUpload() {

		QueryBuilder<Cashflow> qb = cashflowDao.queryBuilder();
		qb.where(CashflowDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(CashflowDao.Properties.CashDate);
		
		Query<Cashflow> q = qb.build();
		
		ArrayList<CashflowBean> cashflowBeans = new ArrayList<CashflowBean>();
		
		for (Cashflow cashflow : q.list()) {
			
			cashflowBeans.add(BeanUtil.getBean(cashflow));
		}
		
		return cashflowBeans;
	}
	
	public void updateCashflows(List<CashflowBean> cashflows) {
		
		DbUtil.getDb().beginTransaction();
		
		List<CashflowBean> shiftedBeans = new ArrayList<CashflowBean>();
		
		for (CashflowBean bean : cashflows) {
			
			boolean isAdd = false;
			
			Cashflow cashflow = cashflowDao.load(bean.getRemote_id());
			
			if (cashflow == null) {
				cashflow = new Cashflow();
				isAdd = true;

			} else if (!CommonUtil.compareString(cashflow.getRefId(), bean.getRef_id())) {
				CashflowBean shiftedBean = BeanUtil.getBean(cashflow);
				shiftedBeans.add(shiftedBean);
			}
			
			BeanUtil.updateBean(cashflow, bean);
			
			if (isAdd) {
				cashflowDao.insert(cashflow);
			} else {
				cashflowDao.update(cashflow);
			}
		}
		
		for (CashflowBean bean : shiftedBeans) {
			
			Cashflow cashflow = new Cashflow();
			BeanUtil.updateBean(cashflow, bean);
			
			cashflow.setId(null);
			cashflow.setUploadStatus(Constant.STATUS_YES);
			
			cashflowDao.insert(cashflow);
		}
		
		DbUtil.getDb().setTransactionSuccessful();
		DbUtil.getDb().endTransaction();
	}
	
	public void updateCashflowStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Cashflow cashflow = cashflowDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				cashflow.setUploadStatus(Constant.STATUS_NO);
				cashflowDao.update(cashflow);
			}
		} 
	}
	
	public boolean hasUpdate() {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM cashflow WHERE upload_status = 'Y'", null);
			
		cursor.moveToFirst();
			
		Long count = cursor.getLong(0);
		
		cursor.close();
		
		return (count > 0);
	}
	
public List<CashFlowYearBean> getCashFlowYears() {
		
		ArrayList<CashFlowYearBean> cashFlowYears = new ArrayList<CashFlowYearBean>();
		
		String paymentType = Constant.PAYMENT_TYPE_CREDIT;
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery(""
				+ " SELECT strftime('%Y', cash_date/1000, 'unixepoch', 'localtime') cash_date, SUM(cash_amount) cash_amount "
				+ " FROM "
				+ "   (SELECT cash_date, cash_amount "
				+ "    FROM cashflow "
				+ "    WHERE status = 'A' "
				+ "   UNION "
				+ "    SELECT transaction_date cash_date, total_amount cash_amount "
				+ "    FROM transactions "
				+ "    WHERE status = 'A' AND payment_type <> ? )"
				+ " GROUP BY strftime('%Y', cash_date/1000, 'unixepoch', 'localtime')", new String[] { paymentType });
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long amount = cursor.getLong(1);
			CashFlowYearBean cashFlowYear = new CashFlowYearBean();
			
			cashFlowYear.setYear(date);
			cashFlowYear.setAmount(amount);
			cashFlowYears.add(cashFlowYear);
		}
		
		cursor.close();
		
		return cashFlowYears;
	}
	
	public List<CashFlowMonthBean> getCashFlowMonths(CashFlowYearBean cashFlowYear) {
		
		ArrayList<CashFlowMonthBean> cashFlowMonths = new ArrayList<CashFlowMonthBean>();
		
		String paymentType = Constant.PAYMENT_TYPE_CREDIT;
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(cashFlowYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(cashFlowYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery(""
				+ " SELECT strftime('%m-%Y', cash_date/1000, 'unixepoch', 'localtime') cash_date, SUM(cash_amount) cash_amount "
				+ " FROM "
				+ "   (SELECT cash_date, cash_amount "
				+ "    FROM cashflow "
				+ "    WHERE status = 'A' AND cash_date BETWEEN ? AND ? "
				+ "   UNION "
				+ "    SELECT transaction_date cash_date, total_amount cash_amount "
				+ "    FROM transactions "
				+ "    WHERE status = 'A' AND payment_type <> ? AND transaction_date BETWEEN ? AND ? )"
				+ " GROUP BY strftime('%m-%Y', cash_date/1000, 'unixepoch', 'localtime')", new String[] { startDate, endDate, paymentType, startDate, endDate });
			
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
	
	public List<CashflowBean> getCashFlowDays(CashFlowMonthBean cashFlowMonth) {
		
		ArrayList<CashflowBean> cashflows = new ArrayList<CashflowBean>();
		
		String paymentType = Constant.PAYMENT_TYPE_CREDIT;
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(cashFlowMonth.getMonth()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(cashFlowMonth.getMonth()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery(""
				+ " SELECT * FROM ("
				+ " SELECT type, bill_id, transaction_id, strftime('%d-%m-%Y', cash_date/1000, 'unixepoch', 'localtime') cash_date, cash_amount, remarks "
				+ "  FROM cashflow "
				+ "  WHERE status = 'A' AND cash_date BETWEEN ? AND ? "
				+ " UNION "
				+ " SELECT null type, null bill_id, null transaction_id, cash_date, cash_amount, null remarks "
				+ " FROM ("
				+ "  SELECT strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime') cash_date, SUM(total_amount) cash_amount "
				+ "  FROM transactions "
				+ "  WHERE status = 'A' AND payment_type <> ? AND transaction_date BETWEEN ? AND ? "
				+ "  GROUP BY strftime('%d-%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'))"
				+ " ) ORDER BY cash_date ", 
				new String[] { startDate, endDate, paymentType, startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			String type = cursor.getString(0);
			Long billId = !cursor.isNull(1) ? cursor.getLong(1) : null;
			Long transactionId = !cursor.isNull(2) ? cursor.getLong(2) : null;
			Date date = CommonUtil.parseDate(cursor.getString(3), "dd-MM-yyyy");
			Float amount = cursor.getFloat(4);
			String remarks = cursor.getString(5);
			
			CashflowBean cashFlowBean = new CashflowBean();
			
			cashFlowBean.setType(type);
			cashFlowBean.setBill_id(billId);
			cashFlowBean.setTransaction_id(transactionId);
			cashFlowBean.setCash_date(date);
			cashFlowBean.setCash_amount(amount);
			cashFlowBean.setRemarks(remarks);
			
			cashflows.add(cashFlowBean);
		}
		
		cursor.close();
		
		return cashflows;
	}
}
