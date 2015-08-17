package com.app.posweb.server.servlet;

import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.BillDao;
import com.app.posweb.server.dao.CashflowDao;
import com.app.posweb.server.dao.CustomerDao;
import com.app.posweb.server.dao.OrderItemDao;
import com.app.posweb.server.dao.OrdersDao;
import com.app.posweb.server.dao.DiscountDao;
import com.app.posweb.server.dao.EmployeeDao;
import com.app.posweb.server.dao.InventoryDao;
import com.app.posweb.server.dao.MerchantAccessDao;
import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.dao.ProductDao;
import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.dao.SupplierDao;
import com.app.posweb.server.dao.TransactionItemDao;
import com.app.posweb.server.dao.TransactionsDao;
import com.app.posweb.server.dao.UserAccessDao;
import com.app.posweb.server.dao.UserDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.shared.Constant;
 
@SuppressWarnings("serial")
public class GetLastSyncJsonServlet extends BaseJsonServlet {
	
	protected SyncResponse processRequest(SyncRequest request) {
		
		MerchantDao merchantDao = new MerchantDao();
		
		SyncResponse response = new SyncResponse();
		
		boolean isAcquireLock = false;
		
		do {
			
			if (request.getMerchant_id() != -1) {
				isAcquireLock = merchantDao.getSyncLock(request.getMerchant_id(), request.getUuid());
			} else {
				isAcquireLock = true;
			}
			
			if (!isAcquireLock) {
				
				try {
					Thread.sleep(30 * Constant.MILISECS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		} while (!isAcquireLock);
		
		response.setRespCode(SyncResponse.SUCCESS);
        
        List<String> taskHasUpdates = new ArrayList<String>();
        
        for (String task : request.getGetRequests()) {
        	
        	if (Constant.TASK_GET_PRODUCT_GROUP.equals(task)) {
        		
        		ProductGroupDao productGroupDao = new ProductGroupDao();
        		
        		if (productGroupDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}	
        	} else if (Constant.TASK_GET_PRODUCT.equals(task)) {
        		
        		ProductDao productDao = new ProductDao();
        		
        		if (productDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_DISCOUNT.equals(task)) {
        		
        		DiscountDao discountDao = new DiscountDao();
        		
        		if (discountDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_EMPLOYEE.equals(task)) {
        		
        		EmployeeDao employeeDao = new EmployeeDao();
        		
        		if (employeeDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_CUSTOMER.equals(task)) {
        		
        		CustomerDao customerDao = new CustomerDao();
        		
        		if (customerDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_USER.equals(task)) {
        		
        		UserDao userDao = new UserDao();
        		
        		if (userDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_USER_ACCESS.equals(task)) {
        		
        		UserAccessDao userAccessDao = new UserAccessDao();
        		
        		if (userAccessDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_TRANSACTION.equals(task)) {
        		
        		TransactionsDao transactionDao = new TransactionsDao();
        		
        		if (transactionDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_TRANSACTION_ITEM.equals(task)) {
        		
        		TransactionItemDao transactionItemDao = new TransactionItemDao();
        		
        		if (transactionItemDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_ORDER.equals(task)) {
        		
        		OrdersDao orderDao = new OrdersDao();
        		
        		if (orderDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_ORDER_ITEM.equals(task)) {
        		
        		OrderItemDao orderItemDao = new OrderItemDao();
        		
        		if (orderItemDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_SUPPLIER.equals(task)) {
        		
        		SupplierDao supplierDao = new SupplierDao();
        		
        		if (supplierDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_BILL.equals(task)) {
        		
        		BillDao billDao = new BillDao();
        		
        		if (billDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_CASHFLOW.equals(task)) {
        		
        		CashflowDao cashflowDao = new CashflowDao();
        		
        		if (cashflowDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_INVENTORY.equals(task)) {
        		
        		InventoryDao inventoryDao = new InventoryDao();
        		
        		if (inventoryDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_MERCHANT.equals(task)) {
        		
        		if (merchantDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_ROOT_GET_MERCHANT.equals(task)) {
        		
        		if (merchantDao.hasRootUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_GET_MERCHANT_ACCESS.equals(task)) {
        		
        		MerchantAccessDao merchantAccessDao = new MerchantAccessDao();
        		
        		if (merchantAccessDao.hasUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	} else if (Constant.TASK_ROOT_GET_MERCHANT_ACCESS.equals(task)) {
        		
        		MerchantAccessDao merchantAccessDao = new MerchantAccessDao();
        		
        		if (merchantAccessDao.hasRootUpdate(request)) {
        			
        			taskHasUpdates.add(task);
        		}
        	}
        }
        
        response.setTaskHasUpdates(taskHasUpdates);
        
        return response;
    }
}
