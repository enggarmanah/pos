package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.DeviceDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class UpdateLastSyncJsonServlet extends BaseJsonServlet {
 
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
		DeviceDao deviceDao = new DeviceDao();
        
		deviceDao.updateDevice(request.getDevice());
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setDevice(request.getDevice());
        
        return response;
    }
}
