package com.android.pos.dao;

import java.util.List;

import com.android.pos.dao.OrderItem;
import com.android.pos.dao.OrderItemDao;
import com.android.pos.model.OrderItemBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class OrderItemDaoService {
	
	private OrderItemDao mOrderItemDao = DbUtil.getSession().getOrderItemDao();
	private OrdersDao mOrdersDao = DbUtil.getSession().getOrdersDao();
	
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
	
	public void addOrderItems(List<OrderItemBean> orderItems) {
		
		for (OrderItemBean bean : orderItems) {
			
			OrderItem orderItem = getOrderItem(bean.getOrder_no(), bean.getProduct_id());
			
			boolean isNew = false;
			Long orderItemId = null;
			
			if (orderItem != null) {
				orderItemId = orderItem.getId();
			} else {
				isNew = true;
				orderItem = new OrderItem();
			}
			
			BeanUtil.updateBean(orderItem, bean);
			
			Orders order = getOrders(orderItem.getOrderNo());
			
			orderItem.setId(orderItemId);
			orderItem.setOrders(order);
			
			if (isNew) {
				mOrderItemDao.insert(orderItem);
			} else {
				mOrderItemDao.update(orderItem);
			}
		} 
	}
	
	private Orders getOrders(String orderNo) {

		QueryBuilder<Orders> qb = mOrdersDao.queryBuilder();
		
		qb.where(OrdersDao.Properties.OrderNo.eq(orderNo)).orderAsc(OrdersDao.Properties.Id);
		
		Query<Orders> q = qb.build();
		
		return q.unique();
	}
}
