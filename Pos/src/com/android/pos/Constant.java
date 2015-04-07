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
	public static final String PRODUCT_TYPE_MENU = "M";
	
	public static final String USER_ROLE_CASHIER = "C";
	public static final String USER_ROLE_ADMIN = "A";
	public static final String USER_ROLE_WAITRESS = "W";
	
	public static final String USER_ROLE_CASHIER_DESC = "Kasir";
	public static final String USER_ROLE_ADMIN_DESC = "Admin";
	public static final String USER_ROLE_WAITRESS_DESC = "Pramuniaga";
	
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
	public static final String MESSAGE_PRINTER_BLUETOOTH_INACTIVE = "Bluetooth tidak aktif, aktifkan bluetooth terlebih dahulu.";
	
	public static final String MESSAGE_SERVER_CANT_CONNECT = "Tidak dapat terhubung ke Server!";
	public static final String MESSAGE_SERVER_SYNC_ERROR = "Error dalam sync data ke Server!";
	
	public static final String MESSAGE_ORDER_SUBMIT_OK = "Pesanan telah berhasil disubmit ke Server!";
	public static final String MESSAGE_ORDER_DOWNLOAD_OK = "Pesanan telah berhasil didownload dari Server!";
	
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
	public static final String ACCESS_REPORT_INVENTORY = "RIVT";
	public static final String ACCESS_REPORT_CASHFLOW = "RCFL";
	public static final String ACCESS_FAVORITE_CUSTOMER = "FVCS";
	public static final String ACCESS_FAVORITE_SUPPLIER = "FVSP";
	
	public static final String MENU_USER = "User";
	public static final String MENU_CASHIER = "Kasir";
	public static final String MENU_WAITRESS = "Pramuniaga";
	public static final String MENU_ORDER = "Pemesanan";
	public static final String MENU_DATA = "Data";
	public static final String MENU_CUSTOMER = "Data Pelanggan";
	public static final String MENU_MERCHANT = "Data Merchant";
	public static final String MENU_DATA_MANAGEMENT = "Referensi";
	public static final String MENU_INVENTORY = "Data Inventori";
	public static final String MENU_USER_ACCESS = "Data Pengguna";
	public static final String MENU_BILLS = "Data Pengeluaran";
	public static final String MENU_REPORT = "Laporan";
	public static final String MENU_REPORT_TRANSACTION = "Laporan Transaksi";
	public static final String MENU_REPORT_PRODUCT_STATISTIC = "Laporan Penjualan";
	public static final String MENU_REPORT_INVENTORY = "Laporan Inventori";
	public static final String MENU_REPORT_CASHFLOW = "Laporan Keuangan";
	public static final String MENU_FAVORITE = "Favorit";
	public static final String MENU_FAVORITE_CUSTOMER = "Pelanggan Favorit";
	public static final String MENU_FAVORITE_SUPPLIER = "Supplier Favorit";
	public static final String MENU_REFERENCE_MERCHANT = "Merchant";
	public static final String MENU_REFERENCE_PRODUCT_GROUP = "Group Produk";
	public static final String MENU_REFERENCE_PRODUCT = "Produk";
	public static final String MENU_REFERENCE_EMPLOYEE = "Pegawai";
	public static final String MENU_REFERENCE_SUPPLIER = "Supplier";
	public static final String MENU_REFERENCE_DISCOUNT = "Diskon";
	public static final String MENU_EXIT = "Keluar";
	public static final String MENU_SYNC = "Sync";
}
