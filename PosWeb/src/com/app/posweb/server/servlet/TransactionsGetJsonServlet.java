package com.app.posweb.server.servlet;

import java.io.IOException;

import com.app.posweb.server.dao.TransactionsDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
 
@SuppressWarnings("serial")
public class TransactionsGetJsonServlet extends BaseJsonServlet {
 
    protected SyncResponse processRequest(SyncRequest request) throws IOException {
    	
        TransactionsDao transactionDao = new TransactionsDao();
        
        SyncResponse response = new SyncResponse();         
        
        response.setRespCode(SyncResponse.SUCCESS);
        response.setTransactions(transactionDao.getTransactions(request));
        
        return response;
    }
}