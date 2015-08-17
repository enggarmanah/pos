package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.Date;

import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.dao.SyncDao;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.shared.Constant;
 
@SuppressWarnings("serial")
public class UpdateLastSyncJsonServlet extends BaseJsonServlet {
 
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
		SyncDao syncDao = new SyncDao();
		MerchantDao merchantDao = new MerchantDao();
		
		Date syncDate = request.getSync_date();
		
		for (String task : request.getGetRequests()) {
        	
        	if (Constant.TASK_GET_PRODUCT_GROUP.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_PRODUCT_GROUP);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_PRODUCT.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_PRODUCT);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_DISCOUNT.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_DISCOUNT);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_EMPLOYEE.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_EMPLOYEE);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_CUSTOMER.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_CUSTOMER);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_USER.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_USER);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_USER_ACCESS.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_USER_ACCESS);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_TRANSACTION.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_TRANSACTIONS);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_TRANSACTION_ITEM.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_TRANSACTION_ITEM);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_ORDER.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_ORDERS);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_ORDER_ITEM.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_ORDER_ITEM);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_SUPPLIER.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_SUPPLIER);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_BILL.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_BILLS);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_CASHFLOW.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_CASHFLOW);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_INVENTORY.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_INVENTORY);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_MERCHANT.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_MERCHANT);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_ROOT_GET_MERCHANT.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_MERCHANT);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_GET_MERCHANT_ACCESS.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_MERCHANT_ACCESS);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        		
        	} else if (Constant.TASK_ROOT_GET_MERCHANT_ACCESS.equals(task)) {
        		
        		Sync sync = syncDao.getSync(request.getMerchant_id(), request.getUuid(), Constant.SYNC_MERCHANT_ACCESS);
        		sync.setLast_sync_date(syncDate);
        		syncDao.updateSync(sync);
        	}
        }
		
		SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        
        merchantDao.releaseSyncLock(request.getMerchant_id(), request.getUuid());
        
        return response;
    }
}
