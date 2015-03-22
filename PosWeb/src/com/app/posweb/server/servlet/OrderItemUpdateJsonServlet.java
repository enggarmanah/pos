package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.OrderItemDao;
import com.app.posweb.server.model.OrderItem;
import com.app.posweb.server.model.SyncStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
 
@SuppressWarnings("serial")
public class OrderItemUpdateJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        ObjectMapper mapper = new ObjectMapper();
        
        List<OrderItem> orderItems = mapper.readValue(jsonStr.toString(),
        							TypeFactory.defaultInstance().constructCollectionType(List.class,  
        							OrderItem.class));         
        
        OrderItemDao orderItemDao = new OrderItemDao();
        
        for (OrderItem orderItem : orderItems) {
        	
        	String status = SyncStatus.FAIL;
        	
        	try {
				orderItemDao.syncOrderItem(orderItem);
				status = SyncStatus.SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	SyncStatus syncStatus = new SyncStatus();
        	syncStatus.setRemoteId(orderItem.getRemote_id());
        	syncStatus.setStatus(status);
        	
        	syncStatusList.add(syncStatus);
        }
        
        return syncStatusList;
    }
}
