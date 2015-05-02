package com.android.pos.util;

import com.android.pos.CodeBean;
import com.android.pos.Constant;

public class CodeUtil {
	
	public static CodeBean[] merchantTypes;
	public static CodeBean[] productStatus;
	public static CodeBean[] status;
	public static CodeBean[] productTypes;
	public static CodeBean[] restoProductTypes;
	public static CodeBean[] booleans;
	public static CodeBean[] roles;
	public static CodeBean[] emailStatus;
	public static CodeBean[] paymentTypes;
	public static CodeBean[] orderTypes;
	public static CodeBean[] billTypes;
	public static CodeBean[] billStatus;
	public static CodeBean[] inventoryStatus;
	public static CodeBean[] moduleAccess;
	
	static {
		
		status = new CodeBean[2];
		
		CodeBean code = new CodeBean();
		code.setCode(Constant.STATUS_ACTIVE);
		code.setLabel("Aktif");
		status[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_INACTIVE);
		code.setLabel("Tidak Aktif");
		status[1] = code;
		
		productStatus = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_ACTIVE);
		code.setLabel("Aktif / Tersedia");
		productStatus[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_INACTIVE);
		code.setLabel("Tidak Aktif / Tersedia");
		productStatus[1] = code;
		
		productTypes = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode("P");
		code.setLabel("Barang");
		productTypes[0] = code;
		
		code = new CodeBean();
		code.setCode("S");
		code.setLabel("Layanan");
		productTypes[1] = code;
		
		restoProductTypes = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode("P");
		code.setLabel("Barang");
		restoProductTypes[0] = code;
		
		code = new CodeBean();
		code.setCode("M");
		code.setLabel("Menu");
		restoProductTypes[1] = code;
		
		booleans = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_YES);
		code.setLabel("Ya");
		booleans[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_NO);
		code.setLabel("Tidak");
		booleans[1] = code;
		
		merchantTypes = new CodeBean[4];
		
		code = new CodeBean();
		code.setCode(Constant.MERCHANT_TYPE_RESTO);
		code.setLabel("Resto");
		merchantTypes[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.MERCHANT_TYPE_SHOP);
		code.setLabel("Toko");
		merchantTypes[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.MERCHANT_TYPE_BEAUTY_N_SPA);
		code.setLabel("Salon Kecantikan & Spa");
		merchantTypes[2] = code;
		
		code = new CodeBean();
		code.setCode(Constant.MERCHANT_TYPE_CLINIC);
		code.setLabel("Klinik");
		merchantTypes[3] = code;
		
		roles = new CodeBean[3];
		
		code = new CodeBean();
		code.setCode(Constant.USER_ROLE_CASHIER);
		code.setLabel(Constant.USER_ROLE_CASHIER_DESC);
		roles[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.USER_ROLE_WAITRESS);
		code.setLabel(Constant.USER_ROLE_WAITRESS_DESC);
		roles[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.USER_ROLE_ADMIN);
		code.setLabel(Constant.USER_ROLE_ADMIN_DESC);
		roles[2] = code;
		
		emailStatus = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_YES);
		code.setLabel("Kirim Email Promosi");
		emailStatus[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_NO);
		code.setLabel("Tidak Kirim Email");
		emailStatus[1] = code;
		
		paymentTypes = new CodeBean[3];
		
		code = new CodeBean();
		code.setCode("CASH");
		code.setLabel("Tunai");
		paymentTypes[0] = code;
		
		code = new CodeBean();
		code.setCode("DEBIT");
		code.setLabel("Debit Card");
		paymentTypes[1] = code;
		
		code = new CodeBean();
		code.setCode("CREDIT");
		code.setLabel("Credit Card");
		paymentTypes[2] = code;
		
		orderTypes = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode("DINE_IN");
		code.setLabel("Makan di Tempat");
		orderTypes[0] = code;
		
		code = new CodeBean();
		code.setCode("TAKE_AWAY");
		code.setLabel("Take Away");
		orderTypes[1] = code;
		
		billTypes = new CodeBean[3];
		
		code = new CodeBean();
		code.setCode(Constant.BILL_TYPE_PRODUCT_PURCHASE);
		code.setLabel("Pembelian Produk");
		billTypes[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_TYPE_EXPENSE_WITH_RECEIPT);
		code.setLabel("Pengeluaran Dengan Nota");
		billTypes[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_TYPE_EXPENSE_WITHOUT_RECEIPT);
		code.setLabel("Pengeluaran Tanpa Nota");
		billTypes[2] = code;
		
		billStatus = new CodeBean[3];
		
		code = new CodeBean();
		code.setCode(Constant.BILL_STATUS_UNPAID);
		code.setLabel("Belum Dibayar");
		billStatus[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_STATUS_PARTIAL);
		code.setLabel("Pembayaran Sebagian");
		billStatus[1] = code;

		code = new CodeBean();
		code.setCode(Constant.BILL_STATUS_PAID);
		code.setLabel("Lunas");
		billStatus[2] = code;
		
		inventoryStatus = new CodeBean[10];
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_PURCHASE);
		code.setLabel("Pembelian Produk");
		inventoryStatus[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_SALE);
		code.setLabel("Penjualan Produk");
		inventoryStatus[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_RETURN);
		code.setLabel("Retur Produk Rusak");
		inventoryStatus[2] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_REFUND);
		code.setLabel("Pembatalan Pembelian");
		inventoryStatus[3] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_REPLACEMENT);
		code.setLabel("Penggantian Produk Rusak");
		inventoryStatus[4] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_NOT_OWNED_IN);
		code.setLabel("Produk Titipan Masuk");
		inventoryStatus[5] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_NOT_OWNED_OUT);
		code.setLabel("Produk Titipan Keluar");
		inventoryStatus[6] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_LOST);
		code.setLabel("Produk Hilang");
		inventoryStatus[7] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_DAMAGE);
		code.setLabel("Produk Rusak");
		inventoryStatus[8] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_INITIAL_STOCK);
		code.setLabel("Stok Awal");
		inventoryStatus[9] = code;
		
		moduleAccess = new CodeBean[15];
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_CASHIER);
		code.setLabel(Constant.MENU_CASHIER);
		moduleAccess[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_WAITRESS);
		code.setLabel(Constant.MENU_WAITRESS);
		moduleAccess[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_ORDER);
		code.setLabel(Constant.MENU_ORDER);
		moduleAccess[2] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_DATA_MANAGEMENT);
		code.setLabel(Constant.MENU_DATA_MANAGEMENT);
		moduleAccess[3] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_CUSTOMER);
		code.setLabel(Constant.MENU_CUSTOMER);
		moduleAccess[4] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_INVENTORY);
		code.setLabel(Constant.MENU_INVENTORY);
		moduleAccess[5] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_BILLS);
		code.setLabel(Constant.MENU_BILLS);
		moduleAccess[6] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_USER_ACCESS);
		code.setLabel(Constant.MENU_USER_ACCESS);
		moduleAccess[7] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_REPORT_TRANSACTION);
		code.setLabel(Constant.MENU_REPORT_TRANSACTION);
		moduleAccess[8] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_REPORT_PRODUCT_STATISTIC);
		code.setLabel(Constant.MENU_REPORT_PRODUCT_STATISTIC);
		moduleAccess[9] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_REPORT_COMMISION);
		code.setLabel(Constant.MENU_REPORT_COMMISION);
		moduleAccess[10] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_REPORT_INVENTORY);
		code.setLabel(Constant.MENU_REPORT_INVENTORY);
		moduleAccess[11] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_REPORT_CASHFLOW);
		code.setLabel(Constant.MENU_REPORT_CASHFLOW);
		moduleAccess[12] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_FAVORITE_CUSTOMER);
		code.setLabel(Constant.MENU_FAVORITE_CUSTOMER);
		moduleAccess[13] = code;
		
		code = new CodeBean();
		code.setCode(Constant.ACCESS_FAVORITE_SUPPLIER);
		code.setLabel(Constant.MENU_FAVORITE_SUPPLIER);
		moduleAccess[14] = code;
	}
	
	public static CodeBean[] getMerchantTypes() {
		return merchantTypes;
	}
	
	public static CodeBean[] getStatus() {
		return status;
	}
	
	public static CodeBean[] getProductStatus() {
		return productStatus;
	}
	
	public static CodeBean[] getProductTypes() {
		return productTypes;
	}
	
	public static CodeBean[] getRestoProductTypes() {
		return restoProductTypes;
	}
	
	public static CodeBean[] getBooleans() {
		return booleans;
	}
	
	public static CodeBean[] getRoles() {
		return roles;
	}
	
	public static CodeBean[] getEmailStatus() {
		return emailStatus;
	}
	
	public static CodeBean[] getPaymentTypes() {
		return paymentTypes;
	}
	
	public static CodeBean[] getOrderTypes() {
		return orderTypes;
	}
	
	public static CodeBean[] getBillTypes() {
		return billTypes;
	}
	
	public static CodeBean[] getBillStatus() {
		return billStatus;
	}
	
	public static CodeBean[] getInventoryStatus() {
		return inventoryStatus;
	}
	
	public static CodeBean[] getModuleAccesses() {
		return moduleAccess;
	}
	
	public static String getPaymentTypeLabel(String code) {
		
		String label = Constant.EMPTY_STRING;
		
		for (CodeBean codeBean : paymentTypes) {
			if (codeBean.getCode().equals(code)) {
				label = codeBean.getLabel();
				break;
			}
		}
		
		return label;
	}
	
	public static String getOrderTypeLabel(String code) {
		
		String label = Constant.EMPTY_STRING;
		
		for (CodeBean codeBean : orderTypes) {
			if (codeBean.getCode().equals(code)) {
				label = codeBean.getLabel();
				break;
			}
		}
		
		return label;
	}
	
	public static String getInvetoriStatusLabel(String code) {
		
		String label = Constant.EMPTY_STRING;
		
		for (CodeBean codeBean : inventoryStatus) {
			if (codeBean.getCode().equals(code)) {
				label = codeBean.getLabel();
				break;
			}
		}
		
		return label;
	}
	
	public static String getBillsTypeLabel(String code) {
		
		String label = Constant.EMPTY_STRING;
		
		for (CodeBean codeBean : billTypes) {
			if (codeBean.getCode().equals(code)) {
				label = codeBean.getLabel();
				break;
			}
		}
		
		return label;
	}
	
	public static String getBillsStatusLabel(String code) {
		
		String label = Constant.EMPTY_STRING;
		
		for (CodeBean codeBean : billStatus) {
			if (codeBean.getCode().equals(code)) {
				label = codeBean.getLabel();
				break;
			}
		}
		
		return label;
	}
}
