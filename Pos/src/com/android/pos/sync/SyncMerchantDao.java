package com.android.pos.sync;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDao;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class SyncMerchantDao {
	
	private MerchantDao merchantDao = DbUtil.getSession().getMerchantDao();
	
	public List<MerchantBean> getMerchantsForUpload() {

		QueryBuilder<Merchant> qb = merchantDao.queryBuilder();
		qb.where(MerchantDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(MerchantDao.Properties.Name);
		
		Query<Merchant> q = qb.build();
		
		ArrayList<MerchantBean> merchantBeans = new ArrayList<MerchantBean>();
		
		for (Merchant prdGroup : q.list()) {
			
			merchantBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return merchantBeans;
	}
	
	public void updateMerchants(List<MerchantBean> merchants) {
		
		for (MerchantBean bean : merchants) {
			
			Merchant merchant = merchantDao.load(bean.getRemote_id());
			
			if (merchant == null) {
				merchant = new Merchant();
			}
			
			BeanUtil.updateBean(merchant, bean);
		} 
	}
	
	public void updateMerchantStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Merchant merchant = merchantDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				merchant.setUploadStatus(Constant.STATUS_NO);
				merchantDao.update(merchant);
			}
		} 
	}
}
