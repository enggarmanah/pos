package com.app.posweb.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import com.app.posweb.server.ServerUtil;
import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.Merchant;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@SuppressWarnings("serial")
public class MerchantValidateJsonServlet extends BaseJsonServlet {
	
	private static final Logger log = Logger.getLogger(MerchantValidateJsonServlet.class.getName());
	
    protected Object processJsonRequest(String jsonStr) throws IOException {
    	
    	log.info("Processing Mechant Validation Request");
    	
    	ObjectMapper mapper = new ObjectMapper();
        
        Merchant merchant = null;
        
        if (!ServerUtil.isEmpty(jsonStr)) {
        	merchant = mapper.readValue(jsonStr, Merchant.class);
        } else {
        	merchant = new Merchant();
        	merchant.setRemote_id(Long.valueOf(1));
        	merchant.setLogin_id("warjok");
        	merchant.setPassword("warjok");
        }
        
        MerchantDao merchantDao = new MerchantDao();
        
        return merchantDao.validateMerchant(merchant);
    }
}
