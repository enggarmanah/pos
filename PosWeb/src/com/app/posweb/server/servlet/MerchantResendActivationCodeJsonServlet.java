package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.MailUtil;
import com.app.posweb.server.model.Merchant;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class MerchantResendActivationCodeJsonServlet extends BaseJsonServlet {
	
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        
        try {
        	
        	Merchant merchant = request.getMerchant();
			response.setMerchant(merchant);
			
			MailUtil.sendRegistrationEmail(merchant);
			
		} catch (Exception e) {
			
			response.setRespCode(SyncResponse.ERROR);
			
			e.printStackTrace();
		}
        
        return response;
    }
}