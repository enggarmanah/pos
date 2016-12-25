package com.app.posweb.shared;

public class Constant {
	
	public static final int MIN_APP_VERSION = 14;
	public static final boolean IS_SYSTEM_MAINTENANCE = false;
	
	public static final String ENV_STAGING = "Staging";
	public static final String ENV_PRODUCTION = "Production";
	
	public static final String APP_CALLBACK_URL_PROD = "http://posweb.appspot.com/verifyCredential";
	public static final String APP_CALLBACK_URL_DEV = "http://posweb.appspot.com:8888/verifyCredential";
	
	public static final String APP_DEBUG_CERT_DN = "CN=Android Debug,O=Android,C=US";
	
	public static final String LINK_WEBSITE = "http://";
	public static final String LINK_FACEBOOK = "http://www.facebook.com/";
	public static final String LINK_TWITTER = "http://www.twitter.com/";
	
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	
	public static final String EMPTY_STRING = "";
	public static final String ZERO_STRING = "0";
	
	public static final int HOUR_SECS = 3600;
	public static final int MIN_SECS = 60;
	public static final int MILISECS = 1000;
	
	public static final int SYNC_RECORD_LIMIT = 1000;
	
	public static final String TASK_VALIDATE_MERCHANT = "TASK_VALIDATE_MERCHANT";
	public static final String TASK_VALIDATE_USER = "TASK_VALIDATE_USER";
	
	public static final String TASK_ROOT_GET_MERCHANT = "TASK_ROOT_GET_MERCHANT";
	public static final String TASK_ROOT_GET_MERCHANT_ACCESS = "TASK_ROOT_GET_MERCHANT_ACCESS";
	
	public static final String TASK_GET_LAST_SYNC = "TASK_GET_LAST_SYNC";
	public static final String TASK_GET_PRODUCT_GROUP = "TASK_GET_PRODUCT_GROUP";
	public static final String TASK_GET_DISCOUNT = "TASK_GET_DISCOUNT";
	public static final String TASK_GET_MERCHANT = "TASK_GET_MERCHANT";
	public static final String TASK_GET_EMPLOYEE = "TASK_GET_EMPLOYEE";
	public static final String TASK_GET_CUSTOMER = "TASK_GET_CUSTOMER";
	public static final String TASK_GET_CASHFLOW = "TASK_GET_CASHFLOW";
	public static final String TASK_GET_PRODUCT = "TASK_GET_PRODUCT";
	public static final String TASK_GET_USER = "TASK_GET_USER";
	public static final String TASK_GET_TRANSACTION = "TASK_GET_TRANSACTION";
	public static final String TASK_GET_TRANSACTION_ITEM = "TASK_GET_TRANSACTION_ITEM";
	public static final String TASK_GET_ORDER = "TASK_GET_ORDER";
	public static final String TASK_GET_ORDER_ITEM = "TASK_GET_ORDER_ITEM";
	public static final String TASK_GET_SUPPLIER = "TASK_GET_SUPPLIER";
	public static final String TASK_GET_BILL = "TASK_GET_BILL";
	public static final String TASK_GET_INVENTORY = "TASK_GET_INVENTORY";
	public static final String TASK_GET_MERCHANT_ACCESS = "TASK_GET_MERCHANT_ACCESS";
	public static final String TASK_GET_USER_ACCESS = "TASK_GET_USER_ACCESS";
	public static final String TASK_UPDATE_USER_ACCESS = "TASK_UPDATE_USER_ACCESS";
	
	public static final String TASK_COMPLETED = "TASK_COMPLETED";
	
	public static final String SYNC_BILLS = "SYNC_BILLS";
	public static final String SYNC_CASHFLOW = "SYNC_CASHFLOW";
	public static final String SYNC_CUSTOMER = "SYNC_CUSTOMER";
	public static final String SYNC_DISCOUNT = "SYNC_DISCOUNT";
	public static final String SYNC_EMPLOYEE = "SYNC_EMPLOYEE";
	public static final String SYNC_INVENTORY = "SYNC_INVENTORY";
	public static final String SYNC_MERCHANT = "SYNC_MERCHANT";
	public static final String SYNC_MERCHANT_ACCESS = "SYNC_MERCHANT_ACCESS";
	public static final String SYNC_ORDER_ITEM = "SYNC_ORDER_ITEM";
	public static final String SYNC_ORDERS = "SYNC_ORDERS";
	public static final String SYNC_PRODUCT = "SYNC_PRODUCT";
	public static final String SYNC_PRODUCT_GROUP = "SYNC_PRODUCT_GROUP";
	public static final String SYNC_SUPPLIER = "SYNC_SUPPLIER";
	public static final String SYNC_TRANSACTION_ITEM = "SYNC_TRANSACTION_ITEM";
	public static final String SYNC_TRANSACTIONS = "SYNC_TRANSACTIONS";
	public static final String SYNC_USER = "SYNC_USER";
	public static final String SYNC_USER_ACCESS = "SYNC_USER_ACCESS";
	
	public static final String ERROR_INVALID_TOKEN = "ERR001";
	public static final String ERROR_SERVICE_EXPIRED = "ERR002";
	public static final String ERROR_COULD_NOT_OBTAINED_LOCK = "ERR003";
	public static final String ERROR_REGISTER_MERCHANT_CONFLICT = "ERR004";
	public static final String ERROR_INVALID_APP_CERT = "ERR005";
	public static final String ERROR_APP_UPDATE_REQUIRED = "ERR006";
	public static final String ERROR_SYSTEM_MAINTENANCE = "ERR007";
	public static final String ERROR_EMAIL_HAS_BEEN_REGISTERED = "ERR008";
}
