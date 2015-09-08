package com.android.pos.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.OrderItem;
import com.android.pos.dao.OrderItemDao;
import com.android.pos.model.OrderItemBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class OrderItemDaoService {
	
	private OrderItemDao mOrderItemDao = DbUtil.getSession().getOrderItemDao();
	
	public void addOrderItem(OrderItem orderItem) {
		
		if (CommonUtil.isEmpty(orderItem.getRefId())) {
			orderItem.setRefId(CommonUtil.generateRefId());
		}
		
		mOrderItemDao.insert(orderItem);
	}
	
	public void updateOrderItem(OrderItem orderItem) {
		
		mOrderItemDao.update(orderItem);
	}
	
	public OrderItem getOrderItem(Long id) {
		
		OrderItem orderItem = mOrderItemDao.load(id);
		
		if (orderItem != null) {
			mOrderItemDao.detach(orderItem);
		}
		
		return orderItem;
	}
	
	public List<OrderItemBean> getOrderItemsForUpload() {
		
		QueryBuilder<OrderItem> qb = mOrderItemDao.queryBuilder();
		qb.where(OrderItemDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(OrderItemDao.Properties.Id);
		
		Query<OrderItem> q = qb.build();
		
		ArrayList<OrderItemBean> orderItemBeans = new ArrayList<OrderItemBean>();
		
		for (OrderItem orderItem : q.list()) {
			
			orderItemBeans.add(BeanUtil.getBean(orderItem));
		}
		
		return orderItemBeans;
	}
	
	public OrderItem getOrderItem(String orderNo, Long productId) {
		
		QueryBuilder<OrderItem> qb = mOrderItemDao.queryBuilder();
		
		qb.where(OrderItemDao.Properties.OrderNo.eq(orderNo),
				OrderItemDao.Properties.ProductId.eq(productId)).orderAsc(OrderItemDao.Properties.Id);
		
		return qb.unique();
	}
	
	public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
		
		QueryBuilder<OrderItem> qb = mOrderItemDao.queryBuilder();
		
		qb.where(OrderItemDao.Properties.OrderId.eq(orderId)).orderAsc(OrderItemDao.Properties.Id);
		
		Query<OrderItem> q = qb.build();
		
		return q.list();
	}
	
	public void updateOrderItems(List<OrderItemBean> orderItems) {
		
		DbUtil.getDb().beginTransaction();
		
		List<OrderItemBean> shiftedBeans = new ArrayList<OrderItemBean>();
		
		for (OrderItemBean bean : orderItems) {
			
			boolean isAdd = false;
			
			OrderItem orderItem = mOrderItemDao.load(bean.getRemote_id());
			
			if (orderItem == null) {
				orderItem = new OrderItem();
				isAdd = true;
			
			} else if (!CommonUtil.compareString(orderItem.getRefId(), bean.getRef_id())) {
				OrderItemBean shiftedBean = BeanUtil.getBean(orderItem);
				shiftedBeans.add(shiftedBean);
			}
			
			BeanUtil.updateBean(orderItem, bean);
			
			if (isAdd) {
				mOrderItemDao.insert(orderItem);
			} else {
				mOrderItemDao.update(orderItem);
			}
		} 
		
		for (OrderItemBean bean : shiftedBeans) {
			
			OrderItem orderItem = new OrderItem();
			BeanUtil.updateBean(orderItem, bean);
			
			orderItem.setId(null);
			orderItem.setUploadStatus(Constant.STATUS_YES);
			
			mOrderItemDao.insert(orderItem);
		}
		
		DbUtil.getDb().setTransactionSuccessful();
		DbUtil.getDb().endTransaction();
	}
	
	public void updateOrderItemStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			OrderItem orderItem = mOrderItemDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				orderItem.setUploadStatus(Constant.STATUS_NO);
				mOrderItemDao.update(orderItem);
			}
		} 
	}
	
	public boolean hasUpdate() {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM order_item WHERE upload_status = 'Y'", null);
			
		cursor.moveToFirst();
			
		Long count = cursor.getLong(0);
		
		cursor.close();
		
		return (count > 0);
	}
}
