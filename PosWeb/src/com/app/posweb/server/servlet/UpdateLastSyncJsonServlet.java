package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.Date;

import com.app.posweb.server.dao.BillDao;
import com.app.posweb.server.dao.CustomerDao;
import com.app.posweb.server.dao.OrdersDao;
import com.app.posweb.server.dao.SyncDao;
import com.app.posweb.server.dao.DiscountDao;
import com.app.posweb.server.dao.EmployeeDao;
import com.app.posweb.server.dao.InventoryDao;
import com.app.posweb.server.dao.MerchantAccessDao;
import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.dao.ProductDao;
import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.dao.SupplierDao;
import com.app.posweb.server.dao.TransactionsDao;
import com.app.posweb.server.dao.UserAccessDao;
import com.app.posweb.server.dao.UserDao;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class UpdateLastSyncJsonServlet extends BaseJsonServlet {
 
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
		SyncDao syncDao = new SyncDao();
		BillDao billDao = new BillDao();
		CustomerDao customerDao = new CustomerDao();
		DiscountDao discountDao = new DiscountDao();
		EmployeeDao employeeDao = new EmployeeDao();
		InventoryDao inventoryDao = new InventoryDao();
		MerchantDao merchantDao = new MerchantDao();
		MerchantAccessDao merchantAccessDao = new MerchantAccessDao();
		ProductDao productDao = new ProductDao();
		ProductGroupDao productGroupDao = new ProductGroupDao();
		SupplierDao supplierDao = new SupplierDao();
		TransactionsDao transactionsDao = new TransactionsDao();
		OrdersDao ordersDao = new OrdersDao();
		UserAccessDao userAccessDao = new UserAccessDao();
		UserDao userDao = new UserDao();
		
		Date syncDate = new Date();
		
		Sync sync = request.getSync();
		sync.setLast_sync_date(syncDate);
        
		syncDao.updateSync(sync);
		
		billDao.updateSyncDate(request, syncDate);
		customerDao.updateSyncDate(request, syncDate);
		discountDao.updateSyncDate(request, syncDate);
		employeeDao.updateSyncDate(request, syncDate);
		inventoryDao.updateSyncDate(request, syncDate);
		merchantDao.updateSyncDate(request, syncDate);
		merchantAccessDao.updateSyncDate(request, syncDate);
		productDao.updateSyncDate(request, syncDate);
		productGroupDao.updateSyncDate(request, syncDate);
		supplierDao.updateSyncDate(request, syncDate);
		transactionsDao.updateSyncDate(request, syncDate);
		ordersDao.updateSyncDate(request, syncDate);
		userAccessDao.updateSyncDate(request, syncDate);
		userDao.updateSyncDate(request, syncDate);
		
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setSync(request.getSync());
        
        return response;
    }
}
