package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Product;
import com.app.posweb.server.model.SyncRequest;

public class ProductDao {
	
	public Product syncProduct(Product product) {
		
		Product obj = getProduct(product);
		
		if (obj != null) {
			
			product.setId(obj.getId());
			product = updateProduct(product);
			
		} else {
			
			product = addProduct(product);
		}
		
		return product;
	}
	
	public Product addProduct(Product bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Product updateProduct(Product bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Product product = em.find(Product.class, bean.getId());
		product.setBean(bean);
		
		em.close();
		
		return product;
	}
	
	public Product getProduct(Product product) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT p FROM Product p ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Product> query = em.createQuery(sql.toString(), Product.class);
		
		query.setParameter("merchantId", product.getMerchant_id());
		query.setParameter("remoteId", product.getRemote_id());
		
		Product result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Product> getProducts(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT p FROM Product p WHERE merchant_id = :merchantId AND update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY p.name");
		
		TypedQuery<Product> query = em.createQuery(sql.toString(), Product.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Product> result = query.getResultList();
		
		em.close();

		return result;
	}
}