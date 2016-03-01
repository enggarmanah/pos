package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.OrderItemDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class OrderItemGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        OrderItemDao orderItemDao = new OrderItemDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setOrderItems(orderItemDao.getOrderItems(request));
        
        response.setResultCount(orderItemDao.getOrderItemsCount(request));
        response.setNextIndex(getNextIndex(request.getIndex(), response.getResultCount()));
        
        return response;
    }
}