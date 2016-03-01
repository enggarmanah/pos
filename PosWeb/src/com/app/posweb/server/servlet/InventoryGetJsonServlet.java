package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.InventoryDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class InventoryGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        InventoryDao inventoryDao = new InventoryDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setInventories(inventoryDao.getInventories(request));
        
        response.setResultCount(inventoryDao.getInventoriesCount(request));
        response.setNextIndex(getNextIndex(request.getIndex(), response.getResultCount()));
        
        return response;
    }
}
