package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.SupplierDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class SupplierGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        SupplierDao supplierDao = new SupplierDao();
        
        SyncResponse response = new SyncResponse();  
        
        response.setResultCount(request.getResultCount());
        response.setNextIndex(getNextIndex(request.getIndex(), request.getResultCount()));
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setSuppliers(supplierDao.getSuppliers(request));
        
        return response;
    }
}