package com.android.pos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDao;
import com.android.pos.dao.ProductGroup;
import com.android.pos.model.ProductBean;
import com.android.pos.model.ProductStatisticBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.model.TransactionMonthBean;
import com.android.pos.model.TransactionYearBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class ProductDaoService {
	
	private ProductDao productDao = DbUtil.getSession().getProductDao();
	
	public void addProduct(Product product) {
		
		productDao.insert(product);
	}
	
	public void updateProduct(Product product) {
		
		productDao.update(product);
	}
	
	public void deleteProduct(Product product) {

		Product entity = productDao.load(product.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		productDao.update(entity);
	}
	
	public Product getProduct(Long id) {
		
		return productDao.load(id);
	}
	
	public List<Product> getProducts(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = "%" + CommonUtil.getNvlString(query) + "%";
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM product "
				+ " WHERE name like ? AND status <> ? "
				+ " ORDER BY name LIMIT ? OFFSET ? ",
				new String[] { queryStr, status, limit, lastIdx});
		
		List<Product> list = new ArrayList<Product>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Product item = getProduct(id);
			list.add(item);
		}

		return list;
	}
	
	public List<Product> getProducts(String query, ProductGroup productGroup) {

		QueryBuilder<Product> qb = productDao.queryBuilder();
		
		if (productGroup == null) {
			qb.where(ProductDao.Properties.Name.like("%" + query + "%"),
					ProductDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(ProductDao.Properties.Name);
		} else {
			qb.where(ProductDao.Properties.Name.like("%" + query + "%"), 
					ProductDao.Properties.ProductGroupId.eq(productGroup.getId()),
					ProductDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(ProductDao.Properties.Name);
		}

		Query<Product> q = qb.build();
		List<Product> list = q.list();
		
		return list;
	}
	
	public List<ProductBean> getProductsForUpload() {

		QueryBuilder<Product> qb = productDao.queryBuilder();
		qb.where(ProductDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(ProductDao.Properties.Name);
		
		Query<Product> q = qb.build();
		
		ArrayList<ProductBean> productBeans = new ArrayList<ProductBean>();
		
		for (Product prdGroup : q.list()) {
			
			productBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return productBeans;
	}
	
	public void updateProducts(List<ProductBean> products) {
		
		for (ProductBean bean : products) {
			
			boolean isAdd = false;
			
			Product product = productDao.load(bean.getRemote_id());
			
			if (product == null) {
				product = new Product();
				isAdd = true;
			}
			
			BeanUtil.updateBean(product, bean);
			
			if (isAdd) {
				productDao.insert(product);
			} else {
				productDao.update(product);
			}
		} 
	}
	
	public void updateProductStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Product product = productDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				product.setUploadStatus(Constant.STATUS_NO);
				productDao.update(product);
			}
		} 
	}
	
	public List<ProductStatisticBean> getProductStatisticsQuantity(TransactionMonthBean transactionMonth) {
		
		ArrayList<ProductStatisticBean> productStatistics = new ArrayList<ProductStatisticBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(transactionMonth.getMonth()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(transactionMonth.getMonth()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT product_name, SUM(quantity) quantity "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY product_name "
				+ " ORDER BY quantity DESC, product_name ASC ", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			String productName = cursor.getString(0);
			Long value = cursor.getLong(1);
			
			ProductStatisticBean productStatistic = new ProductStatisticBean();
			
			productStatistic.setProduct_name(productName);
			productStatistic.setValue(value);
			
			productStatistics.add(productStatistic);
		}
		
		return productStatistics;
	}
	
	public List<ProductStatisticBean> getProductStatisticsRevenue(TransactionMonthBean transactionMonth) {
		
		ArrayList<ProductStatisticBean> productStatistics = new ArrayList<ProductStatisticBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(transactionMonth.getMonth()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(transactionMonth.getMonth()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT product_name, SUM(price - discount) revenue "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY product_name "
				+ " ORDER BY revenue DESC, product_name ASC ", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			String productName = cursor.getString(0);
			Long value = cursor.getLong(1);
			
			ProductStatisticBean productStatistic = new ProductStatisticBean();
			
			productStatistic.setProduct_name(productName);
			productStatistic.setValue(value);
			
			productStatistics.add(productStatistic);
		}
		
		return productStatistics;
	}
	
	public List<ProductStatisticBean> getProductStatisticsProfit(TransactionMonthBean transactionMonth) {
		
		ArrayList<ProductStatisticBean> productStatistics = new ArrayList<ProductStatisticBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfMonth(transactionMonth.getMonth()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfMonth(transactionMonth.getMonth()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT product_name, SUM(price - discount - cost_price) profit "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY product_name "
				+ " ORDER BY profit DESC, product_name ASC ", new String[] { startDate, endDate });
			
		while(cursor.moveToNext()) {
			
			String productName = cursor.getString(0);
			Long value = cursor.getLong(1);
			
			ProductStatisticBean productStatistic = new ProductStatisticBean();
			
			productStatistic.setProduct_name(productName);
			productStatistic.setValue(value);
			
			productStatistics.add(productStatistic);
		}
		
		return productStatistics;
	}
	
	public List<TransactionYearBean> getTransactionYearsQuantity() {
		
		ArrayList<TransactionYearBean> transactionYears = new ArrayList<TransactionYearBean>();
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(quantity) quantity "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id "
				+ " GROUP BY strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime')", null);
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long value = cursor.getLong(1);
			TransactionYearBean transactionYear = new TransactionYearBean();
			transactionYear.setYear(date);
			transactionYear.setAmount(value);
			transactionYears.add(transactionYear);
		}
		
		return transactionYears;
	}
	
	public List<TransactionYearBean> getTransactionYearsRevenue() {
		
		ArrayList<TransactionYearBean> transactionYears = new ArrayList<TransactionYearBean>();
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(price-discount) revenue "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id "
				+ " GROUP BY strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime')", null);
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long value = cursor.getLong(1);
			TransactionYearBean transactionYear = new TransactionYearBean();
			transactionYear.setYear(date);
			transactionYear.setAmount(value);
			transactionYears.add(transactionYear);
		}
		
		return transactionYears;
	}
	
	public List<TransactionYearBean> getTransactionYearsProfit() {
		
		ArrayList<TransactionYearBean> transactionYears = new ArrayList<TransactionYearBean>();
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(price - discount - cost_price) profit "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id "
				+ " GROUP BY strftime('%Y', transaction_date/1000, 'unixepoch', 'localtime')", null);
			
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "yyyy");
			Long value = cursor.getLong(1);
			TransactionYearBean transactionYear = new TransactionYearBean();
			transactionYear.setYear(date);
			transactionYear.setAmount(value);
			transactionYears.add(transactionYear);
		}
		
		return transactionYears;
	}
	
	public List<TransactionMonthBean> getTransactionMonthsQuantity(TransactionYearBean transactionYear) {
		
		ArrayList<TransactionMonthBean> transactionMonths = new ArrayList<TransactionMonthBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(transactionYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(transactionYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(quantity) quantity "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime') ", 
				new String[] { startDate, endDate });
		
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "MM-yyyy");
			Long value = cursor.getLong(1);
			TransactionMonthBean transactionMonth = new TransactionMonthBean();
			transactionMonth.setMonth(date);
			transactionMonth.setAmount(value);
			transactionMonths.add(transactionMonth);
		}
		
		return transactionMonths;
	}
	
	public List<TransactionMonthBean> getTransactionMonthsRevenue(TransactionYearBean transactionYear) {
		
		ArrayList<TransactionMonthBean> transactionMonths = new ArrayList<TransactionMonthBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(transactionYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(transactionYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(price-discount) revenue "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime') ", 
				new String[] { startDate, endDate });
		
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "MM-yyyy");
			Long value = cursor.getLong(1);
			TransactionMonthBean transactionMonth = new TransactionMonthBean();
			transactionMonth.setMonth(date);
			transactionMonth.setAmount(value);
			transactionMonths.add(transactionMonth);
		}
		
		return transactionMonths;
	}
	
	public List<TransactionMonthBean> getTransactionMonthsProfit(TransactionYearBean transactionYear) {
		
		ArrayList<TransactionMonthBean> transactionMonths = new ArrayList<TransactionMonthBean>();
		
		String startDate = String.valueOf(CommonUtil.getFirstDayOfYear(transactionYear.getYear()).getTime());
		String endDate = String.valueOf(CommonUtil.getLastDayOfYear(transactionYear.getYear()).getTime());
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime'), SUM(price - discount - cost_price) profit "
				+ " FROM transactions t, transaction_item ti "
				+ " WHERE t._id = ti.transaction_id AND transaction_date BETWEEN ? AND ? "
				+ " GROUP BY strftime('%m-%Y', transaction_date/1000, 'unixepoch', 'localtime') ", 
				new String[] { startDate, endDate });
		
		while(cursor.moveToNext()) {
			
			Date date = CommonUtil.parseDate(cursor.getString(0), "MM-yyyy");
			Long value = cursor.getLong(1);
			TransactionMonthBean transactionMonth = new TransactionMonthBean();
			transactionMonth.setMonth(date);
			transactionMonth.setAmount(value);
			transactionMonths.add(transactionMonth);
		}
		
		return transactionMonths;
	}
}
