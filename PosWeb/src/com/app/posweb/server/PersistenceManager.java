package com.app.posweb.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.app.posweb.shared.Config;
import com.app.posweb.shared.Constant;

public class PersistenceManager {
	
	static EntityManagerFactory emf;
	
	private static final Logger log = Logger.getLogger(PersistenceManager.class.getName());
	
	private static void initEntityManageFactory() {
		
		try {
			
			Map<String, String> properties = new HashMap<String, String>();
			
			if (ServerUtil.isProductionEnvironment()) {
				
				log.info("Environment Production");
				
				properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
			
				if (Constant.ENV_PRODUCTION.equals(Config.ENV_CURRENT)) {
					
					log.info("Load Production Url");
					
					properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.prd"));
					
				} else {
					
					log.info("Load Staging Url");
					
					properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.stg"));
				}
			
			} else {
				
				log.info("Environment Development");
				
				properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
				properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost/tokoku?user=root");
			}
		
			emf = Persistence.createEntityManagerFactory("PosWeb", properties);
			
		} catch (Exception e) {
			
			log.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static EntityManager getEntityManager() {
		
		if (emf == null) {
			
			log.info("Init Entity Manager Factory");
			
			initEntityManageFactory();
			
			log.info("EMF = " + emf);
		}
		
		return emf.createEntityManager();
	}
}
