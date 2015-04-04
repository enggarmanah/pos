package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Customer;
import com.app.posweb.server.model.SyncRequest;

public class CustomerDao {
	
	public Customer syncCustomer(Customer customer) {
		
		Customer obj = getCustomer(customer);
		
		if (obj != null) {
			
			customer.setId(obj.getId());
			customer = updateCustomer(customer);
			
		} else {
			
			customer = addCustomer(customer);
		}
		
		return customer;
	}
	
	public Customer addCustomer(Customer bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Customer updateCustomer(Customer bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Customer customer = em.find(Customer.class, bean.getId());
		customer.setBean(bean);
		
		em.close();
		
		return customer;
	}
	
	public Customer getCustomer(Customer customer) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT c FROM Customer c ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Customer> query = em.createQuery(sql.toString(), Customer.class);
		
		query.setParameter("merchantId", customer.getMerchant_id());
		query.setParameter("remoteId", customer.getRemote_id());
		
		Customer result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Customer> getCustomers(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM Customer c WHERE merchant_id = :merchantId AND sync_date >= :lastSyncDate");
		
		sql.append(" ORDER BY c.name");
		
		TypedQuery<Customer> query = em.createQuery(sql.toString(), Customer.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Customer> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(c.id) FROM Customer c WHERE merchant_id = :merchantId AND sync_date >= :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
}