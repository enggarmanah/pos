package com.android.pos.service;

import java.util.List;

import com.android.pos.dao.OrderItem;
import com.android.pos.dao.OrderItemDao;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class OrderItemDaoService {
	
	private static OrderItemDao mOrderItemDao = DbUtil.getSession().getOrderItemDao();
	
	public void addOrderItem(OrderItem orderItem) {
		
		mOrderItemDao.insert(orderItem);
	}
	
	public void updateOrderItem(OrderItem orderItem) {
		
		mOrderItemDao.update(orderItem);
	}
	
	public void deleteOrderItem(OrderItem orderItem) {
		
		getOrderItem(orderItem.getId()).delete();
	}
	
	public OrderItem getOrderItem(Long id) {
		
		return mOrderItemDao.load(id);
	}
	
	public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
		
		QueryBuilder<OrderItem> qb = mOrderItemDao.queryBuilder();
		
		qb.where(OrderItemDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				OrderItemDao.Properties.OrderId.eq(orderId)).orderAsc(OrderItemDao.Properties.Id);
		
		Query<OrderItem> q = qb.build();
		
		return q.list();
	}
}
