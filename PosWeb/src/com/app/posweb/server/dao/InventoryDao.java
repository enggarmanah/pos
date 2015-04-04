package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Inventory;
import com.app.posweb.server.model.SyncRequest;

public class InventoryDao {
	
	public Inventory syncInventory(Inventory inventory) {
		
		Inventory obj = getInventory(inventory);
		
		if (obj != null) {
			
			inventory.setId(obj.getId());
			inventory = updateInventory(inventory);
			
		} else {
			
			inventory = addInventory(inventory);
		}
		
		return inventory;
	}
	
	public Inventory addInventory(Inventory bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Inventory updateInventory(Inventory bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Inventory inventory = em.find(Inventory.class, bean.getId());
		inventory.setBean(bean);
		
		em.close();
		
		return inventory;
	}
	
	public Inventory getInventory(Inventory inventory) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT c FROM Inventory c ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Inventory> query = em.createQuery(sql.toString(), Inventory.class);
		
		query.setParameter("merchantId", inventory.getMerchant_id());
		query.setParameter("remoteId", inventory.getRemote_id());
		
		Inventory result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Inventory> getInventories(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM Inventory c WHERE merchant_id = :merchantId AND sync_date >= :lastSyncDate");
		
		sql.append(" ORDER BY c.delivery_date");
		
		TypedQuery<Inventory> query = em.createQuery(sql.toString(), Inventory.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Inventory> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(i.id) FROM Inventory i WHERE merchant_id = :merchantId AND sync_date >= :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
}