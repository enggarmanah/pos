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
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.Transactions;
import com.app.posweb.shared.Constant;

public class TransactionsDao {
	
	SyncDao syncDao = new SyncDao();
	
	public Transactions syncTransactions(Transactions transactions) {
		
		Transactions obj = getTransactions(transactions);
		
		if (obj != null) {
			
			transactions.setId(obj.getId());
			transactions = updateTransactions(transactions);
			
		} else {
			
			transactions = addTransactions(transactions);
		}
		
		return transactions;
	}
	
	public Transactions addTransactions(Transactions bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Transactions updateTransactions(Transactions bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Transactions transactions = em.find(Transactions.class, bean.getId());
		transactions.setBean(bean);
		
		em.close();
		
		return transactions;
	}
	
	public Transactions getTransactions(Transactions transactions) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT t FROM Transactions t ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Transactions> query = em.createQuery(sql.toString(), Transactions.class);
		
		query.setParameter("merchantId", transactions.getMerchant_id());
		query.setParameter("remoteId", transactions.getRemote_id());
		
		Transactions result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	/*public List<Transactions> getTransactions(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTIONS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT t FROM Transactions t WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY t.id");
		
		TypedQuery<Transactions> query = em.createQuery(sql.toString(), Transactions.class);

		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<Transactions> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();

		return result;
	}*/
		
	public List<Transactions> getTransactions(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTIONS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT"
				+ " t.id, "
				+ " t.remote_id, "
				+ " t.ref_id, "
				+ " t.merchant_id, "
				+ " t.transaction_no, "
				+ " t.order_type, "
				+ " t.order_reference, "
				+ " t.transaction_date, "
				+ " t.bill_amount, "
				+ " t.discount_name, "
				+ " t.discount_percentage, "
				+ " t.discount_amount, "
				+ " t.tax_percentage, "
				+ " t.tax_amount, "
				+ " t.service_charge_percentage, "
				+ " t.service_charge_amount, "
				+ " t.total_amount, "
				+ " t.payment_amount, "
				+ " t.return_amount, "
				+ " t.payment_type, "
				+ " t.cashier_id, "
				+ " t.cashier_name, "
				+ " t.waitress_id, "
				+ " t.waitress_name, "
				+ " t.customer_id, "
				+ " t.customer_name, "
				+ " t.status, "
				+ " t.sync_date "
				+ " FROM transactions t WHERE merchant_id = ?1 AND sync_date > ?2");
		
		sql.append(" ORDER BY t.id");
		sql.append(" LIMIT ?3 OFFSET ?4");
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter(1, sync.getMerchant_id());
		query.setParameter(2, sync.getLast_sync_date());
		query.setParameter(3, Constant.SYNC_RECORD_LIMIT);
		query.setParameter(4, syncRequest.getIndex());
		
		List<Transactions> transactions = new ArrayList<Transactions>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		for (int i = 0; i < results.size(); i++) {

			Object[] arr = results.get(i);
			
			Transactions transaction = new Transactions();
			
			transaction.setId(ServerUtil.getIntegerToLong(arr[0]));
			transaction.setRemote_id(ServerUtil.getIntegerToLong(arr[1]));
			transaction.setRef_id((String) arr[2]);
			transaction.setMerchant_id(ServerUtil.getIntegerToLong(arr[3]));
			transaction.setTransaction_no((String) arr[4]);
			
			transaction.setOrder_type((String) arr[5]);
			transaction.setOrder_reference((String) arr[6]);
			transaction.setTransaction_date((Date) arr[7]);
			transaction.setBill_amount(ServerUtil.getBigDecimalToFloat(arr[8]));
			transaction.setDiscount_name((String) arr[9]);
			
			transaction.setDiscount_percentage(ServerUtil.getBigDecimalToFloat(arr[10]));
			transaction.setDiscount_amount(ServerUtil.getBigDecimalToFloat(arr[11]));
			transaction.setTax_percentage(ServerUtil.getBigDecimalToFloat(arr[12]));
			transaction.setTax_amount(ServerUtil.getBigDecimalToFloat(arr[13]));
			transaction.setService_charge_percentage(ServerUtil.getBigDecimalToFloat(arr[14]));
			
			transaction.setService_charge_amount(ServerUtil.getBigDecimalToFloat(arr[15]));
			transaction.setTotal_amount(ServerUtil.getBigDecimalToFloat(arr[16]));
			transaction.setPayment_amount(ServerUtil.getBigDecimalToFloat(arr[17]));
			transaction.setReturn_amount(ServerUtil.getBigDecimalToFloat(arr[18]));
			transaction.setPayment_type((String) arr[19]);
			
			transaction.setCashier_id(ServerUtil.getIntegerToLong(arr[20]));
			transaction.setCashier_name((String) arr[21]);
			transaction.setWaitress_id(ServerUtil.getIntegerToLong(arr[22]));
			transaction.setWaitress_name((String) arr[23]);
			transaction.setCustomer_id(ServerUtil.getIntegerToLong(arr[24]));
			
			transaction.setCustomer_name((String) arr[25]);
			transaction.setStatus((String) arr[26]);
			transaction.setSync_date((Date) arr[27]);
			
			/*
			 + " t.id, "
				+ " t.remote_id, "
				+ " t.ref_id, "
				+ " t.merchant_id, "
				+ " t.transaction_no, "
				
				+ " t.order_type, "
				+ " t.order_reference, "
				+ " t.transaction_date, "
				+ " t.bill_amount, "
				+ " t.discount_name, "
				
				+ " t.discount_percentage, "
				+ " t.discount_amount, "
				+ " t.tax_percentage, "
				+ " t.tax_amount, "
				+ " t.service_charge_percentage, "
				
				+ " t.service_charge_amount, "
				+ " t.total_amount, "
				+ " t.payment_amount, "
				+ " t.return_amount, "
				+ " t.payment_type, "
				
				+ " t.cashier_id, "
				+ " t.cashier_name, "
				+ " t.waitress_id, "
				+ " t.waitress_name, "
				+ " t.customer_id, "
				
				+ " t.customer_name, "
				+ " t.status, "
				+ " t.sync_date "
			 * */
			
			transactions.add(transaction);
		}
		
		em.close();

		return transactions;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return getTransactionsCount(syncRequest) > 0;
	}
	
	public long getTransactionsCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTIONS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(t.id) FROM Transactions t WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return count;
	}
}