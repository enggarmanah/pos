package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.MerchantAccess;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class MerchantAccessDao {
	
	SyncDao syncDao = new SyncDao();
	
	public MerchantAccess syncMerchantAccess(MerchantAccess merchantAccess) {
		
		MerchantAccess obj = getMerchantAccess(merchantAccess);
		
		if (obj != null) {
			
			merchantAccess.setId(obj.getId());
			merchantAccess = updateMerchantAccess(merchantAccess);
			
		} else {
			
			merchantAccess = addMerchantAccess(merchantAccess);
		}
		
		return merchantAccess;
	}
	
	public MerchantAccess addMerchantAccess(MerchantAccess bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public MerchantAccess updateMerchantAccess(MerchantAccess bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		MerchantAccess merchantAccess = em.find(MerchantAccess.class, bean.getId());
		merchantAccess.setBean(bean);
		
		em.close();
		
		return merchantAccess;
	}
	
	public MerchantAccess getMerchantAccess(MerchantAccess merchantAccess) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT m FROM MerchantAccess m ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<MerchantAccess> query = em.createQuery(sql.toString(), MerchantAccess.class);
		
		query.setParameter("merchantId", merchantAccess.getMerchant_id());
		query.setParameter("remoteId", merchantAccess.getRemote_id());
		
		MerchantAccess result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<MerchantAccess> getMerchantAccesses(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT_ACCESS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT m FROM MerchantAccess m WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY m.id");
		
		TypedQuery<MerchantAccess> query = em.createQuery(sql.toString(), MerchantAccess.class);
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<MerchantAccess> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();

		return result;
	}
	
	public List<MerchantAccess> getAllMerchantAccesses(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT_ACCESS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT m FROM MerchantAccess m WHERE sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY m.id");
		
		TypedQuery<MerchantAccess> query = em.createQuery(sql.toString(), MerchantAccess.class);
		
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<MerchantAccess> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();

		return result;
	}
	
	public long getAllMerchantAccessesCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT_ACCESS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT count(m.id) FROM MerchantAccess m WHERE sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY m.id");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return count;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return getMerchantAccessesCount(syncRequest) > 0;
	}
	
	public long getMerchantAccessesCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT_ACCESS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(m.id) FROM MerchantAccess m WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getSingleResult();
		
		em.close();

		return count;
	}
	
	public boolean hasRootUpdate(SyncRequest syncRequest) {
		
		return getAllMerchantAccessesCount(syncRequest) > 0;
	}
}