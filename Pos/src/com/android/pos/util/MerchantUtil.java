package com.android.pos.util;

import com.android.pos.Config;
import com.android.pos.dao.BillsDaoService;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDaoService;
import com.android.pos.dao.ProductDaoService;

public class MerchantUtil {
	
	private static MerchantDaoService merchantDaoService = new MerchantDaoService();
	private static ProductDaoService productDaoService = new ProductDaoService();
	private static BillsDaoService billDaoService = new BillsDaoService();
	
	private static Merchant mMerchant;
	
	private static Integer mBelowStockLimitProductCount;
	private static Integer mPastDueBillsCount;
	private static Integer mOutstandingBillsCount;
	
	
	public static Merchant getMerchant() {
		
		if (mMerchant == null && Config.isDevelopment() && !UserUtil.isRoot()) {
			mMerchant = merchantDaoService.getMerchant(Long.valueOf(7));
		}
		
		return mMerchant;
	}
	
	public static Long getMerchantId() {
		
		return getMerchant() != null ? getMerchant().getId() : null;
	}
	
	public static String getMerchantType() {
		
		return getMerchant() != null ? getMerchant().getType() : null;
	}
	
	public static void setMerchant(Merchant merchant) {
		
		mMerchant = merchant;
		
		mBelowStockLimitProductCount = null;
		mPastDueBillsCount = null;
		mOutstandingBillsCount = null;
	}
	
	public static void recreateDao() {
		
		merchantDaoService = new MerchantDaoService();
		productDaoService = new ProductDaoService();
		billDaoService = new BillsDaoService();
	}
	
	public static Integer getBelowStockLimitProductCount() {
		
		if (mBelowStockLimitProductCount == null) {
			refreshBelowStockLimitProductCount(); 
		}
		
		return mBelowStockLimitProductCount; 
	}
	
	public static Integer getPastDueBillsCount() {
		
		if (mPastDueBillsCount == null) {
			refreshPastDueBillsCount();
		}
		
		return mPastDueBillsCount; 
	}
	
	public static Integer getOutstandingBillsCount() {
		
		if (mOutstandingBillsCount == null) {
			refreshOutstandingBillsCount();
		}
		
		return mOutstandingBillsCount; 
	}
	
	public static void refreshBelowStockLimitProductCount() {
		
		mBelowStockLimitProductCount = productDaoService.getBelowStockLimitProductCount();
	}
	
	public static void refreshPastDueBillsCount() {
		
		mPastDueBillsCount = billDaoService.getPastDueBillsCount();
	}
	
	public static void refreshOutstandingBillsCount() {
		
		mOutstandingBillsCount = billDaoService.getOutstandingBillsCount();
	}
}
