package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.ProductGroup;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class ProductGroupDao {
	
	SyncDao syncDao = new SyncDao();
	
	public ProductGroup syncProductGroup(ProductGroup productGroup) {
		
		ProductGroup obj = getProductGroup(productGroup);
		
		if (obj != null) {
			
			productGroup.setId(obj.getId());
			productGroup = updateProductGroup(productGroup);
			
		} else {
			
			productGroup = addProductGroup(productGroup);
		}
		
		return productGroup;
	}
	
	public ProductGroup addProductGroup(ProductGroup bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public ProductGroup updateProductGroup(ProductGroup bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		ProductGroup productGroup = em.find(ProductGroup.class, bean.getId());
		productGroup.setBean(bean);
		
		em.close();
		
		return productGroup;
	}
	
	public ProductGroup getProductGroup(ProductGroup productGroup) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT pg FROM ProductGroup pg ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<ProductGroup> query = em.createQuery(sql.toString(), ProductGroup.class);
		
		query.setParameter("merchantId", productGroup.getMerchant_id());
		query.setParameter("remoteId", productGroup.getRemote_id());
		
		ProductGroup result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<ProductGroup> getProductGroups(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_PRODUCT_GROUP);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT pg FROM ProductGroup pg WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY pg.name");
		
		TypedQuery<ProductGroup> query = em.createQuery(sql.toString(), ProductGroup.class);

		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<ProductGroup> result = query.getResultList();
		
		em.close();

		return result;
	}
		
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return getProductGroupsCount(syncRequest) > 0;
	}
	
	public long getProductGroupsCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_PRODUCT_GROUP);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(p.id) FROM ProductGroup p WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getSingleResult();
		
		em.close();

		return count;
	}
}