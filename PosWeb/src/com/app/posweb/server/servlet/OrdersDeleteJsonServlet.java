package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.OrderItemDao;
import com.app.posweb.server.dao.OrdersDao;
import com.app.posweb.server.model.OrderItem;
import com.app.posweb.server.model.Orders;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.server.model.SyncStatus;
 
@SuppressWarnings("serial")
public class OrdersDeleteJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        
        OrdersDao ordersDao = new OrdersDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        
        for (Orders order : request.getOrders()) {
        	
        	String status = SyncStatus.FAIL;
        	
        	try {
				
        		List<OrderItem> orderItems = orderItemDao.getOrderItems(order.getMerchant_id(), order.getOrder_no());
            	
            	for (OrderItem orderItem : orderItems) {
            		
            		orderItemDao.deleteOrderItem(orderItem);
            	}
            	
            	ordersDao.deleteOrders(order);
        		
        		status = SyncStatus.SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	SyncStatus syncStatus = new SyncStatus();
        	syncStatus.setRemoteId(order.getRemote_id());
        	syncStatus.setStatus(status);
        	
        	syncStatusList.add(syncStatus);
        }
        
        SyncResponse response = new SyncResponse();
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setStatus(syncStatusList);
        
        return response;
    }
}
