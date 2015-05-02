package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.OrderItem;
import com.app.posweb.server.model.SyncRequest;

public class OrderItemDao {
	
	public OrderItem syncOrderItem(OrderItem orderItem) {
		
		orderItem = addOrderItem(orderItem);
		
		return orderItem;
	}
	
	public OrderItem addOrderItem(OrderItem bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		em.persist(bean);
		
		em.getTransaction().commit();
		em.close();

		return bean;
	}
	
	public OrderItem updateOrderItem(OrderItem bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		OrderItem orderItem = em.find(OrderItem.class, bean.getId());
		orderItem.setBean(bean);
		
		em.getTransaction().commit();
		em.close();
		
		return orderItem;
	}
	
	public OrderItem deleteOrderItem(OrderItem bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		OrderItem orderItem = em.find(OrderItem.class, bean.getId());
		orderItem.setBean(bean);
		
		em.remove(orderItem);
		
		em.getTransaction().commit();
		em.close();
		
		return orderItem;
	}
	
	public OrderItem getOrderItem(OrderItem orderItem) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT oi FROM OrderItem oi ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<OrderItem> query = em.createQuery(sql.toString(), OrderItem.class);
		
		query.setParameter("merchantId", orderItem.getMerchant_id());
		query.setParameter("remoteId", orderItem.getRemote_id());
		
		OrderItem result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<OrderItem> getOrderItems(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT oi FROM Orders AS o, OrderItem AS oi WHERE  "
				+ "	oi.merchant_id = :merchantId AND oi.order_no = o.order_no AND oi.merchant_id = o.merchant_id");
		
		sql.append(" ORDER BY oi.id");
		
		TypedQuery<OrderItem> query = em.createQuery(sql.toString(), OrderItem.class);

		query.setParameter("merchantId", syncRequest.getMerchant_id());
		
		List<OrderItem> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public List<OrderItem> getOrderItems(Long merchantId, String orderNo) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT oi FROM Orders AS o, OrderItem AS oi WHERE  "
				+ "	oi.merchant_id = :merchantId AND oi.order_no = o.order_no AND oi.merchant_id = o.merchant_id AND oi.order_no = :orderNo");
		
		sql.append(" ORDER BY oi.id");
		
		TypedQuery<OrderItem> query = em.createQuery(sql.toString(), OrderItem.class);

		query.setParameter("merchantId", merchantId);
		query.setParameter("orderNo", orderNo);
		
		List<OrderItem> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(o.id) FROM OrderItem o WHERE merchant_id = :merchantId AND sync_date >= :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
}