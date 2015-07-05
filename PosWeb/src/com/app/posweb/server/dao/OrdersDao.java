package com.app.posweb.server.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.Orders;

public class OrdersDao {
	
	public Orders syncOrders(Orders orders) {
		
		Orders obj = getOrders(orders);
		
		if (obj != null) {
			
			orders.setId(obj.getId());
			orders = updateOrders(orders);
			
		} else {
			
			orders = addOrders(orders);
		}
		
		return orders;
	}
	
	public Orders addOrders(Orders bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		em.persist(bean);
		
		em.getTransaction().commit();
		em.close();

		return bean;
	}
	
	public Orders updateOrders(Orders bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		Orders orders = em.find(Orders.class, bean.getId());
		orders.setBean(bean);
		
		em.getTransaction().commit();
		em.close();
		
		return orders;
	}
	
	public Orders deleteOrders(Orders bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		Orders orders = em.find(Orders.class, bean.getId());
		bean.setBean(orders);
		
		em.remove(orders);
		
		em.getTransaction().commit();
		em.close();
		
		return orders;
	}
	
	public Orders getOrders(Orders orders) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT o FROM Orders o ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Orders> query = em.createQuery(sql.toString(), Orders.class);
		
		query.setParameter("merchantId", orders.getMerchant_id());
		query.setParameter("remoteId", orders.getRemote_id());
		
		Orders result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Orders> getOrders(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT o FROM Orders o WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY o.id");
		
		TypedQuery<Orders> query = em.createQuery(sql.toString(), Orders.class);

		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Orders> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(o.id) FROM Orders o WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
	
	public void updateSyncDate(SyncRequest syncRequest, Date syncDate) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE Orders o SET sync_date = :syncDate WHERE merchant_id = :merchantId AND sync_date = :lastSyncDate ");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getSync_date());
		query.setParameter("syncDate", syncDate);
		
		query.executeUpdate();
		
		em.close();
	}
}