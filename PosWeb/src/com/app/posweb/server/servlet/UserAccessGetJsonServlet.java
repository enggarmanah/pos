package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.UserAccessDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class UserAccessGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        UserAccessDao userAccessDao = new UserAccessDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setUserAccesses(userAccessDao.getUserAccesses(request));
        
        return response;
    }
}