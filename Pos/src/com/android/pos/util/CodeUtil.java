package com.android.pos.util;

import com.android.pos.CodeBean;
import com.android.pos.Constant;

public class CodeUtil {
	
	public static CodeBean[] merchantTypes;
	public static CodeBean[] productStatus;
	public static CodeBean[] status;
	public static CodeBean[] productTypes;
	public static CodeBean[] booleans;
	public static CodeBean[] roles;
	public static CodeBean[] emailStatus;
	public static CodeBean[] paymentTypes;
	public static CodeBean[] orderTypes;
	public static CodeBean[] billTypes;
	public static CodeBean[] billStatus;
	public static CodeBean[] inventoryStatus;
	
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
		
		booleans = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_YES);
		code.setLabel("Ya");
		booleans[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.STATUS_NO);
		code.setLabel("Tidak");
		booleans[1] = code;
		
		merchantTypes = new CodeBean[3];
		
		code = new CodeBean();
		code.setCode("R");
		code.setLabel("Resto");
		merchantTypes[0] = code;
		
		code = new CodeBean();
		code.setCode("S");
		code.setLabel("Toko");
		merchantTypes[1] = code;
		
		code = new CodeBean();
		code.setCode("B");
		code.setLabel("Salon Kecantikan & Spa");
		merchantTypes[2] = code;
		
		roles = new CodeBean[2];
		
		code = new CodeBean();
		code.setCode(Constant.USER_ROLE_CASHIER);
		code.setLabel("Kasir");
		roles[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.USER_ROLE_ADMIN);
		code.setLabel("Admin");
		roles[1] = code;
		
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
		
		billTypes = new CodeBean[4];
		
		code = new CodeBean();
		code.setCode(Constant.BILL_TYPE_EXPENSE_REGULAR);
		code.setLabel("Pengeluaran Rutin");
		billTypes[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_TYPE_EXPENSE_ADHOC);
		code.setLabel("Pengeluaran Tidak Rutin");
		billTypes[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_TYPE_PRODUCT_PURCHASE);
		code.setLabel("Pembelian Barang Dagang");
		billTypes[2] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_TYPE_PRODUCT_SUPPORT);
		code.setLabel("Pembelian Barang Penunjang");
		billTypes[3] = code;
		
		billStatus = new CodeBean[3];
		
		code = new CodeBean();
		code.setCode(Constant.BILL_STATUS_PAID);
		code.setLabel("Lunas");
		billStatus[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_STATUS_UNPAID);
		code.setLabel("Belum Dibayar");
		billStatus[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.BILL_STATUS_PARTIAL);
		code.setLabel("Pembayaran Sebagian");
		billStatus[2] = code;
		
		inventoryStatus = new CodeBean[7];
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_PURCHASE);
		code.setLabel("Barang Pembelian");
		inventoryStatus[0] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_RETURN);
		code.setLabel("Barang Retur");
		inventoryStatus[1] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_REFUND);
		code.setLabel("Pembatalan Pembelian");
		inventoryStatus[2] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_REPLACEMENT);
		code.setLabel("Barang Pengganti");
		inventoryStatus[3] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_NOT_OWNED_IN);
		code.setLabel("Barang Titipan Masuk");
		inventoryStatus[4] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_NOT_OWNED_OUT);
		code.setLabel("Barang Titipan Keluar");
		inventoryStatus[5] = code;
		
		code = new CodeBean();
		code.setCode(Constant.INVENTORY_STATUS_LOST);
		code.setLabel("Barang Hilang");
		inventoryStatus[6] = code;
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
}
