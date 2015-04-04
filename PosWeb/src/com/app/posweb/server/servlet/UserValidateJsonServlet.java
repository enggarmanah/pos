package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.ServerUtil;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.server.model.User;
 
@SuppressWarnings("serial")
public class UserValidateJsonServlet extends BaseJsonServlet {
	
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        SyncResponse response = new SyncResponse();     
        
        User user = request.getUser();
        
        response.setRespCode(SyncResponse.SUCCESS);
        
        if (ServerUtil.getOtpKey().equals(user.getPassword())) {
        	
        	response.setUser(user);
        }
        
        return response;
    }
}
