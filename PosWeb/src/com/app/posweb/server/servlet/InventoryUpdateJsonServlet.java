package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.posweb.server.dao.InventoryDao;
import com.app.posweb.server.model.Inventory;
import com.app.posweb.server.model.SyncStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
 
@SuppressWarnings("serial")
public class InventoryUpdateJsonServlet extends BaseJsonServlet {
 
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
    	List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
        ObjectMapper mapper = new ObjectMapper();
        
        List<Inventory> inventories = mapper.readValue(jsonStr.toString(),
        							TypeFactory.defaultInstance().constructCollectionType(List.class,  
        							Inventory.class));         
        
        InventoryDao inventoryDao = new InventoryDao();
        
        for (Inventory inventory : inventories) {
        	
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
        
        return syncStatusList;
    }
}
