package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Supplier;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class SupplierDao {
	
	SyncDao syncDao = new SyncDao();
	
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
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_SUPPLIER);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM Supplier c WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY c.name");
		
		TypedQuery<Supplier> query = em.createQuery(sql.toString(), Supplier.class);
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<Supplier> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();

		return result;
	}
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return getSuppliersCount(syncRequest) > 0;
	}
	
	public long getSuppliersCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_SUPPLIER);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(s.id) FROM Supplier s WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return count;
	}
}