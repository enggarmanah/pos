package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.InventoryDao;
import com.app.posweb.server.model.Inventory;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.server.model.SyncStatus;
 
@SuppressWarnings("serial")
public class InventoryUpdateJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        
        InventoryDao inventoryDao = new InventoryDao();
        
        for (Inventory inventory : request.getInventories()) {
        	
        	inventory.setSync_date(request.getSync_date());
        	
        	String status = SyncStatus.FAIL;
        	
        	try {
				inventoryDao.syncInventory(inventory);
				status = SyncStatus.SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	SyncStatus syncStatus = new SyncStatus();
        	syncStatus.setRemoteId(inventory.getRemote_id());
        	syncStatus.setStatus(status);
        	
        	syncStatusList.add(syncStatus);
        }
        
        SyncResponse response = new SyncResponse();
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setStatus(syncStatusList);
        
        return response;
    }
}
