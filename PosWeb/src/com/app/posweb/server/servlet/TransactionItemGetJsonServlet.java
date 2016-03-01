package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.TransactionItemDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class TransactionItemGetJsonServlet extends BaseJsonServlet {
	
	protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        TransactionItemDao transactionItemDao = new TransactionItemDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setTransactionItems(transactionItemDao.getTransactionItems(request));

        response.setResultCount(transactionItemDao.getTransactionItemsCount(request));
        response.setNextIndex(getNextIndex(request.getIndex(), response.getResultCount()));
        
        return response;
    }
}