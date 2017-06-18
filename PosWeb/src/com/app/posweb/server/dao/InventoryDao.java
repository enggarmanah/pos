package com.app.posweb.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.ServerUtil;
import com.app.posweb.server.model.Inventory;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class InventoryDao {
	
	SyncDao syncDao = new SyncDao();
	
	public Inventory syncInventory(Inventory inventory) {
		
		Inventory obj = getInventory(inventory);
		
		if (obj != null) {
			
			inventory.setId(obj.getId());
			inventory = updateInventory(inventory);
			
		} else {
			
			inventory = addInventory(inventory);
		}
		
		return inventory;
	}
	
	public Inventory addInventory(Inventory bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Inventory updateInventory(Inventory bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Inventory inventory = em.find(Inventory.class, bean.getId());
		inventory.setBean(bean);
		
		em.close();
		
		return inventory;
	}
	
	public Inventory getInventory(Inventory inventory) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT c FROM Inventory c ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Inventory> query = em.createQuery(sql.toString(), Inventory.class);
		
		query.setParameter("merchantId", inventory.getMerchant_id());
		query.setParameter("remoteId", inventory.getRemote_id());
		
		Inventory result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
/*	public List<Inventory> getInventories(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_INVENTORY);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM Inventory c WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY c.inventory_date");
		
		TypedQuery<Inventory> query = em.createQuery(sql.toString(), Inventory.class);
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<Inventory> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();

		return result;
	}*/
	
	public List<Inventory> getInventories(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_INVENTORY);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT "
				+ "	i.id, "
				+ " i.remote_id, "
				+ " i.ref_id, "
				+ " i.merchant_id, "
				+ " i.create_by, "
				+ " i.create_date, "
				+ " i.update_by, "
				+ " i.update_date, "
				+ " i.sync_date, "
				+ " i.product_id, "
				+ " i.product_name, "
				+ " i.product_cost_price, "
				+ " i.quantity,"
				+ " i.bill_id, "
				+ " i.bill_reference_no, "
				+ " i.supplier_id, "
				+ " i.supplier_name, "
				+ " i.inventory_date, "
				+ " i.remarks, "
				+ " i.status "
				+ " FROM inventory i WHERE merchant_id = ?1 AND sync_date > ?2 ");
		
		sql.append(" ORDER BY i.id");
		sql.append(" LIMIT ?3 OFFSET ?4");
		
		List<Inventory> inventories = new ArrayList<Inventory>();
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter(1, sync.getMerchant_id());
		query.setParameter(2, sync.getLast_sync_date());
		query.setParameter(3, Constant.SYNC_RECORD_LIMIT);
		query.setParameter(4, syncRequest.getIndex());
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		for (int i = 0; i < results.size(); i++) {

			Object[] arr = results.get(i);
			
			Inventory inventory = new Inventory();
			
			inventory.setId(ServerUtil.getIntegerToLong(arr[0]));
			inventory.setRemote_id(ServerUtil.getIntegerToLong(arr[1]));
			inventory.setRef_id((String) arr[2]);
			inventory.setMerchant_id(ServerUtil.getIntegerToLong(arr[3]));
			inventory.setCreate_by((String) arr[4]);
			
			inventory.setCreate_date((Date) arr[5]);
			inventory.setUpdate_by((String) arr[6]);
			inventory.setUpdate_date((Date) arr[7]);
			inventory.setSync_date((Date) arr[8]);
			inventory.setProduct_id(ServerUtil.getIntegerToLong(arr[9]));
			
			inventory.setProduct_name((String) arr[10]);
			inventory.setProduct_cost_price(ServerUtil.getBigDecimalToFloat(arr[11]));
			inventory.setQuantity(ServerUtil.getBigDecimalToFloat(arr[12]));
			inventory.setBill_id(ServerUtil.getIntegerToLong(arr[13]));
			inventory.setBill_reference_no((String) arr[14]);
			
			inventory.setSupplier_id(ServerUtil.getIntegerToLong(arr[15]));
			inventory.setSupplier_name((String) arr[16]);
			inventory.setInventory_date((Date) arr[17]);
			inventory.setRemarks((String) arr[18]);
			inventory.setStatus((String) arr[19]);
			
			inventories.add(inventory);
		}
		em.close();

		return inventories;
	}

	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return getInventoriesCount(syncRequest) > 0;
	}
	
	public long getInventoriesCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_CUSTOMER);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(i.id) FROM Inventory i WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return count;
	}
}