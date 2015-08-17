package com.app.posweb.server.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Merchant;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class MerchantDao {
	
	SyncDao syncDao = new SyncDao();
	
	public Merchant syncMerchant(Merchant merchant) {
		
		Merchant obj = getMerchant(merchant);
		
		if (obj != null) {
			
			merchant.setId(obj.getId());
			merchant = updateMerchant(merchant);
			
		} else {
			
			merchant = addMerchant(merchant);
		}
		
		return merchant;
	}
	
	public Merchant addMerchant(Merchant bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Merchant registerMerchant(Merchant bean) {
		
		bean = addMerchant(bean);
		bean.setRemote_id(bean.getId());
		bean = updateMerchant(bean);

		return bean;
	}
	
	public Merchant updateMerchant(Merchant bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Merchant merchant = em.find(Merchant.class, bean.getId());
		merchant.setBean(bean);
		
		em.close();
		
		return merchant;
	}
	
	public Merchant getMerchant(Merchant merchant) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT m FROM Merchant m ");
		sql.append(" WHERE remote_id = :remoteId ");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);
		
		query.setParameter("remoteId", merchant.getRemote_id());
		
		Merchant result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public boolean getSyncLock(Long merchantId, String uuid) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" UPDATE Merchant m SET last_sync_key = :lastSyncKey, last_sync_date = :lastSyncDate ");
		sql.append(" WHERE remote_id = :remoteId AND (last_sync_key IS NULL or last_sync_key = 'NULL' or last_sync_key = :lastSyncKey or (last_sync_key IS NOT NULL and last_sync_date < :minSyncDate)) ");
		
		Query query = em.createQuery(sql.toString());
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10); 
		
		query.setParameter("remoteId", merchantId);
		query.setParameter("lastSyncKey", uuid);
		query.setParameter("lastSyncDate", new Date());
		query.setParameter("minSyncDate", cal.getTime());
		
		int result = query.executeUpdate();
		
		em.close();

		return result == 1;
	}
	
	public boolean releaseSyncLock(Long merchantId, String uuid) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" UPDATE Merchant m SET last_sync_key = 'NULL', last_sync_date = :lastSyncDate ");
		sql.append(" WHERE remote_id = :remoteId AND last_sync_key = :lastSyncKey ");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("remoteId", merchantId);
		query.setParameter("lastSyncKey", uuid);
		query.setParameter("lastSyncDate", new Date());
		
		int result = query.executeUpdate();
		
		em.close();

		return result == 1;
	}
	
	public Merchant validateMerchant(Merchant merchant) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT m FROM Merchant m ");
		sql.append(" WHERE LOWER(login_id) = :loginId AND password = :password");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);
		
		query.setParameter("loginId", merchant.getLogin_id().toLowerCase());
		query.setParameter("password", merchant.getPassword());
		
		Merchant result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public Merchant getMerchant(String loginId) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT m FROM Merchant m ");
		sql.append(" WHERE LOWER(login_id) = :loginId");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);
		
		query.setParameter("loginId", loginId.toLowerCase());
		
		Merchant result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Merchant> getMerchants(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT m FROM Merchant m WHERE sync_date > :lastSyncDate AND remote_id = :merchantId");
		
		sql.append(" ORDER BY m.name");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);

		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		query.setParameter("merchantId", sync.getMerchant_id());
		
		List<Merchant> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public List<Merchant> getAllMerchants(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT m FROM Merchant m WHERE sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY m.name");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);

		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<Merchant> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean isActiveMerchant(Long merchantId) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT m FROM Merchant m ");
		sql.append(" WHERE remote_id = :remoteId AND :curDate BETWEEN period_start AND period_end");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);
		
		query.setParameter("remoteId", merchantId);
		query.setParameter("curDate", new Date());
		
		Merchant result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return (result != null);
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(m.id) FROM Merchant m WHERE remote_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
	
	public boolean hasRootUpdate(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_MERCHANT);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(m.id) FROM Merchant m WHERE sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
}