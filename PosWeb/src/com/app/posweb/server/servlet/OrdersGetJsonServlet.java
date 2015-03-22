package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.OrdersDao;
import com.app.posweb.server.model.SyncRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class OrdersGetJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
        ObjectMapper mapper = new ObjectMapper();
        
        SyncRequest request = mapper.readValue(jsonStr, SyncRequest.class);         
        
        OrdersDao ordersDao = new OrdersDao();
        
        return ordersDao.getOrders(request);
    }
}
