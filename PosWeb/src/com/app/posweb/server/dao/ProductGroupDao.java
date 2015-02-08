package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.ProductGroup;
import com.app.posweb.server.model.SyncRequest;

public class ProductGroupDao {
	
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
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT pg FROM ProductGroup pg WHERE merchant_id = :merchantId AND update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY pg.name");
		
		TypedQuery<ProductGroup> query = em.createQuery(sql.toString(), ProductGroup.class);

		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<ProductGroup> result = query.getResultList();
		
		em.close();

		return result;
	}
}