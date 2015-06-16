package com.app.posweb.server.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Employee;
import com.app.posweb.server.model.SyncRequest;

public class EmployeeDao {
	
	public Employee syncEmployee(Employee employee) {
		
		Employee obj = getEmployee(employee);
		
		if (obj != null) {
			
			employee.setId(obj.getId());
			employee = updateEmployee(employee);
			
		} else {
			
			employee = addEmployee(employee);
		}
		
		return employee;
	}
	
	public Employee addEmployee(Employee bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Employee updateEmployee(Employee bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Employee employee = em.find(Employee.class, bean.getId());
		employee.setBean(bean);
		
		em.close();
		
		return employee;
	}
	
	public Employee getEmployee(Employee employee) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT e FROM Employee e ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Employee> query = em.createQuery(sql.toString(), Employee.class);
		
		query.setParameter("merchantId", employee.getMerchant_id());
		query.setParameter("remoteId", employee.getRemote_id());
		
		Employee result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Employee> getEmployees(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT e FROM Employee e WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY e.name");
		
		TypedQuery<Employee> query = em.createQuery(sql.toString(), Employee.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Employee> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(e.id) FROM Employee e WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
	
	public void updateSyncDate(SyncRequest syncRequest, Date syncDate) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE Employee e SET sync_date = :syncDate WHERE merchant_id = :merchantId AND sync_date = :lastSyncDate ");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getSync_date());
		query.setParameter("syncDate", syncDate);
		
		query.executeUpdate();
		
		em.close();
	}
}