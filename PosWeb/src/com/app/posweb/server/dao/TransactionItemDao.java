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
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.TransactionItem;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class TransactionItemDao {
	
	SyncDao syncDao = new SyncDao();
	
	public TransactionItem syncTransactionItem(TransactionItem transactionItem) {
		
		TransactionItem obj = getTransactionItem(transactionItem);
		
		if (obj != null) {
			
			transactionItem.setId(obj.getId());
			transactionItem = updateTransactionItem(transactionItem);
			
		} else {
			
			transactionItem = addTransactionItem(transactionItem);
		}
		
		return transactionItem;
	}
	
	public TransactionItem addTransactionItem(TransactionItem bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public TransactionItem updateTransactionItem(TransactionItem bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		TransactionItem transactionItem = em.find(TransactionItem.class, bean.getId());
		transactionItem.setBean(bean);
		
		em.close();
		
		return transactionItem;
	}
	
	public TransactionItem getTransactionItem(TransactionItem transactionItem) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ti FROM TransactionItem ti ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<TransactionItem> query = em.createQuery(sql.toString(), TransactionItem.class);
		
		query.setParameter("merchantId", transactionItem.getMerchant_id());
		query.setParameter("remoteId", transactionItem.getRemote_id());
		
		TransactionItem result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	/*public List<TransactionItem> getTransactionItems(SyncRequest syncRequest) {
		
		Date date = new Date();
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTION_ITEM);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT ti FROM Transactions AS t, TransactionItem AS ti WHERE  "
				+ "	ti.merchant_id = :merchantId AND ti.transaction_id = t.remote_id AND ti.merchant_id = t.merchant_id AND t.sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY ti.id");
		
		TypedQuery<TransactionItem> query = em.createQuery(sql.toString(), TransactionItem.class);

		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<TransactionItem> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();
		
		System.out.println("getTransactionItems Processing Time: " + ((new Date()).getTime() - date.getTime()));
		
		return result;
	}*/
	
	public List<TransactionItem> getTransactionItems(SyncRequest syncRequest) {
		
		Date date = new Date();
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTION_ITEM);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT "
				+ "	ti.id, "
				+ " ti.remote_id, "
				+ " ti.ref_id, "
				+ " ti.merchant_id, "
				+ " ti.transaction_id, "
				+ " ti.product_id, "
				+ " ti.product_name, "
				+ " ti.product_type, "
				+ " ti.price, "
				+ " ti.cost_price, "
				+ " ti.discount, "
				+ " ti.quantity, "
				+ " ti.commision, "
				+ " ti.employee_id "
				+ " FROM transactions t, transaction_item ti WHERE  "
				+ "	ti.merchant_id = ?1 AND ti.transaction_id = t.remote_id AND ti.merchant_id = t.merchant_id AND t.sync_date > ?2");
		
		sql.append(" ORDER BY ti.id");
		sql.append(" LIMIT ?3 OFFSET ?4");
		
		List<TransactionItem> transactionItems = new ArrayList<TransactionItem>();
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter(1, sync.getMerchant_id());
		query.setParameter(2, sync.getLast_sync_date());
		query.setParameter(3, Constant.SYNC_RECORD_LIMIT);
		query.setParameter(4, syncRequest.getIndex());
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		for (int i = 0; i < results.size(); i++) {

			Object[] arr = results.get(i);
			
			TransactionItem transactionItem = new TransactionItem();
			
			transactionItem.setId(ServerUtil.getIntegerToLong(arr[0]));
			transactionItem.setRemote_id(ServerUtil.getIntegerToLong(arr[1]));
			transactionItem.setRef_id((String) arr[2]);
			transactionItem.setMerchant_id(ServerUtil.getIntegerToLong(arr[3]));
			transactionItem.setTransaction_id(ServerUtil.getIntegerToLong(arr[4]));
			transactionItem.setProduct_id(ServerUtil.getIntegerToLong(arr[5]));
			transactionItem.setProduct_name((String) arr[6]);
			transactionItem.setProduct_type((String) arr[7]);
			transactionItem.setPrice(ServerUtil.getBigDecimalToFloat(arr[8]));
			transactionItem.setCost_price(ServerUtil.getBigDecimalToFloat(arr[9]));
			transactionItem.setDiscount(ServerUtil.getBigDecimalToFloat(arr[10]));
			transactionItem.setQuantity(ServerUtil.getBigDecimalToFloat(arr[11]));
			transactionItem.setCommision(ServerUtil.getBigDecimalToFloat(arr[12]));
			transactionItem.setEmployee_id(ServerUtil.getIntegerToLong(arr[13]));
			
			transactionItems.add(transactionItem);
		}
		
		em.close();
		
		System.out.println("getTransactionItems Processing Time: " + ((new Date()).getTime() - date.getTime()));

		return transactionItems;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return (getTransactionItemsCount(syncRequest) > 0);
	}
	
	public int getTransactionItemsCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTION_ITEM);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(ti.id) FROM Transactions AS t, TransactionItem AS ti WHERE  "
				+ "	ti.merchant_id = :merchantId AND ti.transaction_id = t.remote_id AND ti.merchant_id = t.merchant_id AND t.sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		int count = (int) (long) query.getSingleResult();
		
		em.close();

		return count;
	}
}