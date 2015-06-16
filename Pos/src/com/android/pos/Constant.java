package com.android.pos;

public class Constant {
	
	public static final String ROOT = "root";
	
	public static final int TIMEOUT = 60000;
	
	public static final String QUERY_LIMIT = "50";
	
	public static final String EMPTY_STRING = "";
	public static final String SPACE_STRING = " ";
	public static final String CR_STRING = "\r";
	
	public static final String STATUS_YES = "Y";
	public static final String STATUS_NO = "N";
	
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	public static final String STATUS_PENDING = "P";
	public static final String STATUS_DELETED = "D";
	
	public static final String PRODUCT_TYPE_GOODS = "P"; 
	public static final String PRODUCT_TYPE_SERVICE = "S";
	public static final String PRODUCT_TYPE_MENU = "M";
	
	public static final String USER_ROLE_CASHIER = "C";
	public static final String USER_ROLE_ADMIN = "A";
	public static final String USER_ROLE_WAITRESS = "W";
	
	public static final String PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
	public static final String PRODUCT_REVENUE = "PRODUCT_REVENUE";
	public static final String PRODUCT_PROFIT = "PRODUCT_PROFIT";
	
	public static final String MERCHANT_TYPE_RESTO = "R";
	public static final String MERCHANT_TYPE_BEAUTY_N_SPA = "B";
	public static final String MERCHANT_TYPE_SHOP = "S";
	public static final String MERCHANT_TYPE_CLINIC = "C";
	
	public static final String DISCOUNT_TYPE_NOMINAL = "N";
	public static final String DISCOUNT_TYPE_PERCENTAGE = "P";
	
	public static final String PRICE_TYPE_COUNT_1 = "1";
	public static final String PRICE_TYPE_COUNT_2 = "2";
	public static final String PRICE_TYPE_COUNT_3 = "3";
	
	public static final String QUANTITY_TYPE_PIECE = "Pc";
	public static final String QUANTITY_TYPE_METER = "M";
	public static final String QUANTITY_TYPE_LITER = "L";
	
	public static final String FONT_SIZE_REGULAR = "R";
	public static final String FONT_SIZE_SMALL = "S";
	
	public static int WAIT_FRAGMENT_TO_BE_REMOVED_SLEEP_PERIOD = 200;
	
	public static final String TASK_VALIDATE_MERCHANT = "TASK_VALIDATE_MERCHANT";
	public static final String TASK_VALIDATE_USER = "TASK_VALIDATE_USER";
	
	public static final String TASK_ROOT_GET_MERCHANT = "TASK_ROOT_GET_MERCHANT";
	public static final String TASK_ROOT_GET_MERCHANT_ACCESS = "TASK_ROOT_GET_MERCHANT_ACCESS";
	
	public static final String TASK_SUBMIT_ORDERS = "TASK_SUBMIT_ORDERS";
	public static final String TASK_SUBMIT_ORDER_ITEMS = "TASK_SUBMIT_ORDER_ITEMS";
	
	public static final String TASK_GET_ORDERS = "TASK_GET_ORDERS";
	public static final String TASK_GET_ORDER_ITEMS = "TASK_GET_ORDER_ITEMS";
	
	public static final String TASK_DELETE_ORDERS = "TASK_DELETE_ORDERS";
	
	public static final String TASK_GET_LAST_SYNC = "TASK_GET_LAST_SYNC";
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
	
	public static final String TASK_GET_SUPPLIER = "TASK_GET_SUPPLIER";
	public static final String TASK_UPDATE_SUPPLIER = "TASK_UPDATE_SUPPLIER";
	
	public static final String TASK_GET_BILL = "TASK_GET_BILLS";
	public static final String TASK_UPDATE_BILL = "TASK_UPDATE_BILLS";
	
	public static final String TASK_GET_INVENTORY = "TASK_GET_INVENTORY";
	public static final String TASK_UPDATE_INVENTORY = "TASK_UPDATE_INVENTORY";
	
	public static final String TASK_GET_MERCHANT_ACCESS = "TASK_GET_MERCHANT_ACCESS";
	public static final String TASK_UPDATE_MERCHANT_ACCESS = "TASK_UPDATE_MERCHANT_ACCESS";
	
	public static final String TASK_GET_USER_ACCESS = "TASK_GET_USER_ACCESS";
	public static final String TASK_UPDATE_USER_ACCESS = "TASK_UPDATE_USER_ACCESS";
	
	public static final String TASK_COMPLETED = "TASK_COMPLETED";
	
	public static final String ORDER_TYPE_DINE_IN = "DINE_IN";
	public static final String ORDER_TYPE_TAKEWAY = "TAKE_AWAY";
	
	public static final String SELECTED_ORDERS_FOR_PAYMENT = "SELECTED_ORDERS_FOR_PAYMENT";
	public static final String SELECTED_ORDERS_FOR_NEW_ITEM = "SELECTED_ORDERS_FOR_NEW_ITEM";
	
	public static final String CASHIER_STATE_CASHIER = "CASHIER_STATE_CASHIER";
	public static final String CASHIER_STATE_ORDER_PAYMENT = "CASHIER_STATE_ORDER_PAYMENT";
	public static final String CASHIER_STATE_ORDER_NEW_ITEM = "CASHIER_STATE_ORDER_NEW_ITEM";
	
	public static final String BILL_TYPE_EXPENSE_WITH_RECEIPT = "RCPT";
	public static final String BILL_TYPE_EXPENSE_WITHOUT_RECEIPT = "NORC";
	public static final String BILL_TYPE_PRODUCT_PURCHASE = "PRCH";
	
	public static final String BILL_STATUS_PAID = "PAID";
	public static final String BILL_STATUS_UNPAID = "UNPD";
	public static final String BILL_STATUS_PARTIAL = "PART";
	
	public static final String INVENTORY_STATUS_PURCHASE = "PRCH";
	public static final String INVENTORY_STATUS_SALE = "SALE";
	public static final String INVENTORY_STATUS_RETURN = "RETR";
	public static final String INVENTORY_STATUS_REFUND = "REFD";
	public static final String INVENTORY_STATUS_REPLACEMENT = "REPL";
	public static final String INVENTORY_STATUS_NOT_OWNED_IN = "NOIN";
	public static final String INVENTORY_STATUS_NOT_OWNED_OUT = "NOUT";
	public static final String INVENTORY_STATUS_LOST = "LOST";
	public static final String INVENTORY_STATUS_DAMAGE = "DAMG";
	public static final String INVENTORY_STATUS_INITIAL_STOCK = "INIT";
	
	public static final String ACCESS_CASHIER = "CASH";
	public static final String ACCESS_WAITRESS = "WTRS";
	public static final String ACCESS_ORDER = "ORDR";
	public static final String ACCESS_DATA_MANAGEMENT = "MGMT";
	public static final String ACCESS_CUSTOMER = "CUST";
	public static final String ACCESS_INVENTORY = "INVT";
	public static final String ACCESS_USER_ACCESS = "UACC";
	public static final String ACCESS_BILLS = "BILL";
	public static final String ACCESS_REPORT_TRANSACTION = "RTRA";
	public static final String ACCESS_REPORT_PRODUCT_STATISTIC = "RPST";
	public static final String ACCESS_REPORT_COMMISION = "RCMS";
	public static final String ACCESS_REPORT_INVENTORY = "RIVT";
	public static final String ACCESS_REPORT_CASHFLOW = "RCFL";
	public static final String ACCESS_FAVORITE_CUSTOMER = "FVCS";
	public static final String ACCESS_FAVORITE_SUPPLIER = "FVSP";
}
