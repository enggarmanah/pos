package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.Date;

import com.app.posweb.server.MailUtil;
import com.app.posweb.server.ServiceException;
import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.Merchant;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.shared.Constant;
 
@SuppressWarnings("serial")
public class MerchantRegisterJsonServlet extends BaseJsonServlet {
	
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        MerchantDao merchantDao = new MerchantDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        
        try {
        	
        	Merchant merchant = request.getMerchant();
        	
        	if (merchantDao.getMerchantByEmail(merchant.getContact_email()) != null) {
        		throw new ServiceException(Constant.ERROR_EMAIL_HAS_BEEN_REGISTERED);
        	}
        	
        	merchant.setSync_date(new Date());
        	merchantDao.registerMerchant(merchant);
			
        	response.setMerchant(merchant);
			
			MailUtil.sendRegistrationEmail(merchant);
			MailUtil.sendNotificationEmail(merchant);
			
		} catch (ServiceException e) {
			
			response.setRespCode(SyncResponse.ERROR);
			response.setRespDescription(e.getMessage());
			
			e.printStackTrace();
			
		} catch (Exception e) {
			
			response.setRespCode(SyncResponse.ERROR);
			response.setRespDescription(Constant.ERROR_REGISTER_MERCHANT_CONFLICT);
			
			e.printStackTrace();
		}
        
        return response;
    }
}