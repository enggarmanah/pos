package com.android.pos;

public class Constant {
	
	public static final String ROOT = "root";
	
	public static final int TIMEOUT = 60000;
	
	public static final String QUERY_LIMIT = "50";
	
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
	public static final String MERCHANT_TYPE_SHOP = "S";
	
	public static int WAIT_FRAGMENT_TO_BE_REMOVED_SLEEP_PERIOD = 200;
	
	public static final String MESSAGE_PRINTER_CANT_CONNECT = "Tidak dapat terhubung ke Printer";
	public static final String MESSAGE_PRINTER_CONNECTION_LOST = "Koneksi ke Printer telah terputus";
	public static final String MESSAGE_PRINTER_CONNECTED_TO = "Terkoneksi dengan Printer : ";
	public static final String MESSAGE_PRINTER_CONNECTING = "Melaksanakan koneksi ke Printer ...";
	public static final String MESSAGE_PRINTER_PLEASE_CHECK_PRINTER = "Tidak dapat terhubung ke Printer. Pastikan Printer anda aktif.";
	public static final String MESSAGE_PRINTER_CONNECT_PRINTER_FROM_CASHIER = "Tidak dapat terhubung ke Printer.\nKoneksikan printer anda dari modul kasir.";
	public static final String MESSAGE_PRINTER_CANT_PRINT = "Tidak dapat terhubung ke printer.\nStruk tidak dapat dicetak";
	
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
	
	public static final String TASK_GET_SUPPLIER = "TASK_GET_SUPPLIER";
	public static final String TASK_UPDATE_SUPPLIER = "TASK_UPDATE_SUPPLIER";
	
	public static final String TASK_GET_BILL = "TASK_GET_BILLS";
	public static final String TASK_UPDATE_BILL = "TASK_UPDATE_BILLS";
	
	public static final String TASK_GET_INVENTORY = "TASK_GET_INVENTORY";
	public static final String TASK_UPDATE_INVENTORY = "TASK_UPDATE_INVENTORY";
	
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
	public static final String ACCESS_DATA_MANAGEMENT = "MGMT";
	public static final String ACCESS_INVENTORY = "INVT";
	public static final String ACCESS_USER_ACCESS = "UACC";
	public static final String ACCESS_BILLS = "BILL";
	public static final String ACCESS_REPORT_TRANSACTION = "RTRA";
	public static final String ACCESS_REPORT_PRODUCT_STATISTIC = "RPST";
	public static final String ACCESS_REPORT_INVENTORY = "RIVT";
	public static final String ACCESS_REPORT_CASHFLOW = "RCFL";
}
