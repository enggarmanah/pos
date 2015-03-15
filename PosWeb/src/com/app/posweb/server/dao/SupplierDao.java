package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Supplier;
import com.app.posweb.server.model.SyncRequest;

public class SupplierDao {
	
	public Supplier syncSupplier(Supplier supplier) {
		
		Supplier obj = getSupplier(supplier);
		
		if (obj != null) {
			
			supplier.setId(obj.getId());
			supplier = updateSupplier(supplier);
			
		} else {
			
			supplier = addSupplier(supplier);
		}
		
		return supplier;
	}
	
	public Supplier addSupplier(Supplier bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Supplier updateSupplier(Supplier bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Supplier supplier = em.find(Supplier.class, bean.getId());
		supplier.setBean(bean);
		
		em.close();
		
		return supplier;
	}
	
	public Supplier getSupplier(Supplier supplier) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT c FROM Supplier c ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Supplier> query = em.createQuery(sql.toString(), Supplier.class);
		
		query.setParameter("merchantId", supplier.getMerchant_id());
		query.setParameter("remoteId", supplier.getRemote_id());
		
		Supplier result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Supplier> getSuppliers(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM Supplier c WHERE merchant_id = :merchantId AND update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY c.name");
		
		TypedQuery<Supplier> query = em.createQuery(sql.toString(), Supplier.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Supplier> result = query.getResultList();
		
		em.close();

		return result;
	}
}