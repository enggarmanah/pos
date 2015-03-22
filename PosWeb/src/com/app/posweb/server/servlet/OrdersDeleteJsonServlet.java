package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.OrderItemDao;
import com.app.posweb.server.dao.OrdersDao;
import com.app.posweb.server.model.OrderItem;
import com.app.posweb.server.model.Orders;
import com.app.posweb.server.model.SyncStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
 
@SuppressWarnings("serial")
public class OrdersDeleteJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        ObjectMapper mapper = new ObjectMapper();
        
        List<Orders> orders = mapper.readValue(jsonStr.toString(),
        							TypeFactory.defaultInstance().constructCollectionType(List.class,  
        							Orders.class));         
        
        OrdersDao ordersDao = new OrdersDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        
        for (Orders order : orders) {
        	
        	List<OrderItem> orderItems = orderItemDao.getOrderItems(order.getMerchant_id(), order.getOrder_no());
        	
        	for (OrderItem orderItem : orderItems) {
        		
        		orderItemDao.deleteOrderItem(orderItem);
        	}
        	
        	ordersDao.deleteOrders(order);
        }
        
        return syncStatusList;
    }
}
