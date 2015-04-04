package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.UserDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class UserGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        UserDao userDao = new UserDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setUsers(userDao.getUsers(request));
        
        return response;
    }
}