package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.OrdersDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class OrdersGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        OrdersDao orderDao = new OrdersDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setOrders(orderDao.getOrders(request));
        
        response.setResultCount(orderDao.getOrdersCount(request));
        response.setNextIndex(getNextIndex(request.getIndex(), response.getResultCount()));
        
        return response;
    }
}