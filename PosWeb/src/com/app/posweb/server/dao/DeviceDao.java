package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.ServerUtil;
import com.app.posweb.server.model.Device;

public class DeviceDao {
	
	public Device syncDevice(Device device) {
		
		Device obj = getDevice(device);
		
		if (obj != null) {
			
			device.setId(obj.getId());
			device = updateDevice(device);
			
		} else {
			
			device = addDevice(device);
		}
		
		return device;
	}
	
	public Device addDevice(Device bean) {
		
		bean.setLast_sync_date(ServerUtil.getStartDate());
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		
		em.close();

		return bean;
	}
	
	public Device updateDevice(Device bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Device device = em.find(Device.class, bean.getId());
		device.setBean(bean);
		
		em.close();
		
		return device;
	}
	
	public Device getDevice(Device device) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT pg FROM Device pg ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND uuid = :uuid ");
		
		TypedQuery<Device> query = em.createQuery(sql.toString(), Device.class);
		
		query.setParameter("merchantId", device.getMerchant_id());
		query.setParameter("uuid", device.getUuid());
		
		Device result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Device> getDevices() {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer("SELECT pg FROM Device pg");
		
		sql.append(" ORDER BY pg.name");
		
		TypedQuery<Device> query = em.createQuery(sql.toString(), Device.class);
		
		List<Device> result = query.getResultList();
		
		em.close();

		return result;
	}
}
