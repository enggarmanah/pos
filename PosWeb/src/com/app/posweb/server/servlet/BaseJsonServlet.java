package com.app.posweb.server.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.app.posweb.server.ServerUtil;
import com.app.posweb.server.dao.SyncDao;
import com.app.posweb.server.dao.MerchantDao;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.SyncResponse;
import com.app.posweb.shared.Constant;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public abstract class BaseJsonServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(MerchantValidateJsonServlet.class.getName());
	
	private static ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath = request.getServletPath();
		
		System.out.println(servletPath);
		
		String jsonRequest = uncompress(request.getInputStream());
		
		SyncResponse syncResponse = null;
		
		Date start = new Date();
        
		SyncRequest syncRequest = mapper.readValue(jsonRequest, SyncRequest.class);
		
		if ((ServerUtil.isProductionEnvironment() && Constant.APP_DEBUG_CERT_DN.equals(syncRequest.getCert_dn())) ||
			(!ServerUtil.isProductionEnvironment() && !Constant.APP_DEBUG_CERT_DN.equals(syncRequest.getCert_dn()))) {
			
			syncResponse = new SyncResponse();
        	syncResponse.setRespCode(SyncResponse.ERROR);
        	syncResponse.setRespDescription(Constant.ERROR_INVALID_APP_CERT);
			
		} else if (!isByPassTokenValidation(servletPath) && !isValidToken(syncRequest)) {
        	
        	syncResponse = new SyncResponse();
        	syncResponse.setRespCode(SyncResponse.ERROR);
        	syncResponse.setRespDescription(Constant.ERROR_INVALID_TOKEN);
        	
        } else if (!isByPassTokenValidation(servletPath) && !isActiveMerchant(syncRequest)) {
        	
        	syncResponse = new SyncResponse();
        	syncResponse.setRespCode(SyncResponse.ERROR);
        	syncResponse.setRespDescription(Constant.ERROR_SERVICE_EXPIRED);
        
        } else {
        	
        	MerchantDao merchantDao = new MerchantDao();
    		boolean isAcquireLock = merchantDao.getSyncLock(syncRequest.getMerchant_id(), syncRequest.getUuid());
        	
        	if (!(this instanceof GetLastSyncJsonServlet || this instanceof UpdateLastSyncJsonServlet ||
          		  this instanceof MerchantValidateJsonServlet || this instanceof MerchantRegisterJsonServlet || 
          		  this instanceof MerchantGetByLoginIdJsonServlet || this instanceof MerchantResetPasswordJsonServlet || 
          		  this instanceof UserValidateJsonServlet || this instanceof MerchantResendActivationCodeJsonServlet || 
          		  syncRequest.getMerchant_id() == -1) && !isAcquireLock) {
        		
        		syncResponse = new SyncResponse();
                syncResponse.setRespCode(SyncResponse.ERROR);
                syncResponse.setRespDescription(Constant.ERROR_COULD_NOT_OBTAINED_LOCK);
        	
        	} else {
        		
        		syncResponse = processRequest(syncRequest);
        	}
        }
		
		syncResponse.setRespDate(new Date());

		response.setContentType("application/json");
		
		byte[] bytes = mapper.writeValueAsBytes(syncResponse);
		
		log.log(Level.INFO, "Processing Time : " + (new Date().getTime() - start.getTime()));
		log.log(Level.INFO, "Response Size : " + bytes.length);
		
		bytes = compress(bytes);
		
		log.log(Level.INFO, "Compress Size : " + bytes.length);
		
		response.getOutputStream().write(bytes);
	}
	
	private boolean isByPassTokenValidation(String path) {
		
		boolean isByPass = false;
		
		if ("/getLastSyncJsonServlet".equals(path) || 
			"/updateLastSyncJsonServlet".equals(path) ||
			"/merchantRegisterJsonServlet".equals(path) ||
			"/merchantResendActivationCodeJsonServlet".equals(path) ||
			"/merchantResetPasswordJsonServlet".equals(path) ||
			"/merchantValidateJsonServlet".equals(path) ||
			"/userValidateJsonServlet".equals(path) ||
			"/merchantGetByLoginIdJsonServlet".equals(path) ||
			"/merchantGetAllJsonServlet".equals(path) ||
			"/merchantAccessGetAllJsonServlet".equals(path) ||
			"/userGetJsonServlet".equals(path)) {
			
			isByPass = true;
		}
		
		return isByPass;
	}
	
	private boolean isValidToken(SyncRequest syncRequest) {
		
		SyncDao deviceDao = new SyncDao();
		
		return deviceDao.isValidToken(syncRequest.getMerchant_id(), syncRequest.getUuid());
	}
	
	private boolean isActiveMerchant(SyncRequest syncRequest) {
		
		// if root condition
		if (syncRequest.getMerchant_id() == -1) {
			return true;
		}
		
		MerchantDao merchantDao = new MerchantDao();
		
		return merchantDao.isActiveMerchant(syncRequest.getMerchant_id());
	}

	protected abstract SyncResponse processRequest(SyncRequest request)
			throws IOException;

	public static String uncompress(InputStream inputStream)
			throws IOException {
		
		String result = null;
		
		GZIPInputStream zi = null;
		
		try {
			
			zi = new GZIPInputStream(inputStream);
			result = IOUtils.toString(zi);
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			if (zi != null) {
				zi.close();
			}
		}
		
		return result;
	}
	
	public static byte[] compress(byte[] bytes) throws IOException {

		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(rstBao);
		
		zos.write(bytes);
		zos.close();

		byte[] output = rstBao.toByteArray();
		
		return output;
	}
}
