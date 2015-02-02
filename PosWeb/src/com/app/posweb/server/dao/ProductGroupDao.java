package com.app.posweb.server.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.ProductGroup;

public class ProductGroupDao {
	
	public ProductGroup syncProductGroup(ProductGroup productGroup) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		ProductGroup obj = getProductGroup(productGroup);
		
		if (obj != null) {
			productGroup.setId(obj.getId());
		}
		
		em.persist(productGroup);
		em.refresh(productGroup);
		em.detach(productGroup);
		
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
			em.detach(result);
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<ProductGroup> getProductGroups(Date lastSyncDate) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT pg FROM ProductGroup pg WHERE update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY pg.name");
		
		TypedQuery<ProductGroup> query = em.createQuery(sql.toString(), ProductGroup.class);
		query.setParameter("lastSyncDate", lastSyncDate);
		
		List<ProductGroup> result = query.getResultList();
		
		em.close();

		return result;
	}
}