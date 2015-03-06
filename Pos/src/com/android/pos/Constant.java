package com.android.pos;

public class Constant {
	
	public static final int TIMEOUT = 60000;
	
	public static final String EMPTY_STRING = "";
	
	public static final String STATUS_YES = "Y";
	public static final String STATUS_NO = "N";
	
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	public static final String STATUS_PENDING = "P";
	public static final String STATUS_DELETED = "D";
	
	public static final String PRODUCT_TYPE_GOODS = "P"; 
	public static final String PRODUCT_TYPE_SERVICE = "S";
	
	public static final String USER_ROLE_CASHIER = "C";
	public static final String USER_ROLE_ADMIN = "A";
	
	public static final String PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
	public static final String PRODUCT_REVENUE = "PRODUCT_REVENUE";
	public static final String PRODUCT_PROFIT = "PRODUCT_PROFIT";
	
	public static final String MERCHANT_TYPE_RESTO = "R";
	public static final String MERCHANT_TYPE_BEAUTY_N_SPA = "B";
	public static final String MERCHANT_TYPE_TOKO = "S";
	
	public static int WAIT_FRAGMENT_TO_BE_REMOVED_SLEEP_PERIOD = 250;
	public static int WAIT_ON_LOAD_PERIOD = 250;
	
	public static int LIST_SELECTED_ITEM_GAP = 5;
	
	public static final String TASK_VALIDATE_MERCHANT = "TASK_VALIDATE_MERCHANT";
	
	public static final String TASK_GET_MERCHANT_ONLY = "TASK_GET_MERCHANT_ONLY";
	public static final String TASK_UPDATE_MERCHANT_ONLY = "TASK_UPDATE_MERCHANT_ONLY";
	
	public static final String TASK_GET_LAST_SYNC = "TASK_GET_LAST_SYNC";
	public static final String TASK_GET_LAST_SYNC_ONLY = "TASK_GET_LAST_SYNC_ONLY";
	public static final String TASK_UPDATE_LAST_SYNC = "TASK_UPDATE_LAST_SYNC";
	
	public static final String TASK_GET_PRODUCT_GROUP = "TASK_GET_PRODUCT_GROUP";
	public static final String TASK_UPDATE_PRODUCT_GROUP = "TASK_UPDATE_PRODUCT_GROUP";
	
	public static final String TASK_GET_DISCOUNT = "TASK_GET_DISCOUNT";
	public static final String TASK_UPDATE_DISCOUNT = "TASK_UPDATE_DISCOUNT";
	
	public static final String TASK_GET_MERCHANT = "TASK_GET_MERCHANT";
	public static final String TASK_UPDATE_MERCHANT = "TASK_UPDATE_MERCHANT";
	
	public static final String TASK_GET_EMPLOYEE = "TASK_GET_EMPLOYEE";
	public static final String TASK_UPDATE_EMPLOYEE = "TASK_UPDATE_EMPLOYEE";
	
	public static final String TASK_GET_CUSTOMER = "TASK_GET_CUSTOMER";
	public static final String TASK_UPDATE_CUSTOMER = "TASK_UPDATE_CUSTOMER";
	
	public static final String TASK_GET_PRODUCT = "TASK_GET_PRODUCT";
	public static final String TASK_UPDATE_PRODUCT = "TASK_UPDATE_PRODUCT";
	
	public static final String TASK_GET_USER = "TASK_GET_USER";
	public static final String TASK_UPDATE_USER = "TASK_UPDATE_USER";
	
	public static final String TASK_GET_TRANSACTIONS = "TASK_GET_TRANSACTIONS";
	public static final String TASK_UPDATE_TRANSACTIONS = "TASK_UPDATE_TRANSACTIONS";

	public static final String TASK_GET_TRANSACTION_ITEM = "TASK_GET_TRANSACTION_ITEM";
	public static final String TASK_UPDATE_TRANSACTION_ITEM = "TASK_UPDATE_TRANSACTION_ITEM";
	
	public static final String ORDER_TYPE_DINE_IN = "DINE_IN";
	public static final String ORDER_TYPE_TAKEWAY = "TAKE_AWAY";
	
	public static final String SELECTED_ORDERS_FOR_PAYMENT = "SELECTED_ORDERS_FOR_PAYMENT";
	public static final String SELECTED_ORDERS_FOR_NEW_ITEM = "SELECTED_ORDERS_FOR_NEW_ITEM";
	
	public static final String CASHIER_STATE_CASHIER = "CASHIER_STATE_CASHIER";
	public static final String CASHIER_STATE_ORDER_PAYMENT = "CASHIER_STATE_ORDER_PAYMENT";
	public static final String CASHIER_STATE_ORDER_NEW_ITEM = "CASHIER_STATE_ORDER_NEW_ITEM";
	
	public static final String BILL_TYPE_EXPENSE_REGULAR = "EXPENSE_REGULAR";
	public static final String BILL_TYPE_EXPENSE_ADHOC = "EXPENSE_ADHOC";
	public static final String BILL_TYPE_PRODUCT_PURCHASE = "PRODUCT_PURCHASE";
	public static final String BILL_TYPE_PRODUCT_SUPPORT = "PRODUCT_SUPPORT";
	
	public static final String BILL_STATUS_PAID = "PAID";
	public static final String BILL_STATUS_UNPAID = "UNPAID";
	public static final String BILL_STATUS_PARTIAL = "PARTIAL";
	
	public static final String INVENTORY_STATUS_PURCHASE = "PURCHASE";
	public static final String INVENTORY_STATUS_RETURN = "RETURN";
	public static final String INVENTORY_STATUS_REPLACEMENT = "REPLACEMENT";
	public static final String INVENTORY_STATUS_NOT_OWNED_IN = "NOT_OWNED_IN";
	public static final String INVENTORY_STATUS_NOT_OWNED_OUT = "NOT_OWNED_OUT";
	public static final String INVENTORY_STATUS_LOST = "LOST";
}
