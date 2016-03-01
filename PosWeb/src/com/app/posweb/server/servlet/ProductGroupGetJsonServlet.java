package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.ProductGroupDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class ProductGroupGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        ProductGroupDao productGroupDao = new ProductGroupDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setProductGroups(productGroupDao.getProductGroups(request));
        
        response.setResultCount(productGroupDao.getProductGroupsCount(request));
        response.setNextIndex(getNextIndex(request.getIndex(), response.getResultCount()));
        
        return response;
    }
}