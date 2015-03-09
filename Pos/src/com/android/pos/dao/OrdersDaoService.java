package com.android.pos.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Orders;
import com.android.pos.dao.OrdersDao;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class OrdersDaoService {
	
	private OrdersDao mOrdersDao = DbUtil.getSession().getOrdersDao();
	
	public void addOrders(Orders Orders) {
		
		mOrdersDao.insert(Orders);
	}
	
	public void updateOrders(Orders Orders) {
		
		mOrdersDao.update(Orders);
	}
	
	public void deleteOrders(Orders Orders) {

		Orders entity = mOrdersDao.load(Orders.getId());
		entity.delete();
	}
	
	public Orders getOrders(Long id) {
		
		return mOrdersDao.load(id);
	}
	
	public List<Orders> getOrders() {

		QueryBuilder<Orders> qb = mOrdersDao.queryBuilder();
		
		qb.where(OrdersDao.Properties.Status.eq(Constant.STATUS_ACTIVE)).orderAsc(OrdersDao.Properties.Id);
		
		Query<Orders> q = qb.build();
		
		return q.list();
	}
	
	public List<Orders> getOrdersByOrderReference(String orderReference) {

		QueryBuilder<Orders> qb = mOrdersDao.queryBuilder();
		
		qb.where(OrdersDao.Properties.OrderReference.eq(orderReference),
				OrdersDao.Properties.Status.eq(Constant.STATUS_ACTIVE)).orderAsc(OrdersDao.Properties.Id);
		
		Query<Orders> q = qb.build();
		
		return q.list();
	}
	
	public List<String> getOrderReferences() {
		
		ArrayList<String> orderReferences = new ArrayList<String>();
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT DISTINCT order_reference "
				+ " FROM orders "
				+ " ORDER BY order_reference ASC ", null);
			
		while(cursor.moveToNext()) {
			
			String orderReference = cursor.getString(0);
			orderReferences.add(orderReference);
		}
		
		return orderReferences;
	}
}
