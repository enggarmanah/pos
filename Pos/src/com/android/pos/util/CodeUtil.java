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
		code.setCode("C");
		code.setLabel("Kasir");
		roles[0] = code;
		
		code = new CodeBean();
		code.setCode("A");
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
}
