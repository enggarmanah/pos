package com.android.pos.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.User;
import com.android.pos.dao.UserDao;
import com.android.pos.model.UserBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class UserDaoService {
	
	private UserDao userDao = DbUtil.getSession().getUserDao();
	
	public void addUser(User user) {
		
		userDao.insert(user);
	}
	
	public void updateUser(User user) {
		
		userDao.update(user);
	}
	
	public void deleteUser(User user) {

		User entity = userDao.load(user.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		userDao.update(entity);
	}
	
	public User getUser(Long id) {
		
		return userDao.load(id);
	}
	
	public User validateUser(Long merchantId, String loginId, String password) {
		
		QueryBuilder<User> qb = userDao.queryBuilder();
		
		qb.where(UserDao.Properties.UserId.eq(loginId), 
				UserDao.Properties.Password.eq(password));

		Query<User> q = qb.build();
		User user = q.unique();
		
		return user;
	}
	
	public List<User> getUsers(String query, int lastIndex) {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = "%" + CommonUtil.getNvlString(query) + "%";
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM user "
				+ " WHERE name like ? AND status <> ? "
				+ " ORDER BY name LIMIT ? OFFSET ? ",
				new String[] { queryStr, status, limit, lastIdx});
		
		List<User> list = new ArrayList<User>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			User item = getUser(id);
			list.add(item);
		}
		
		cursor.close();

		return list;
	}
	
	public List<UserBean> getUsersForUpload() {

		QueryBuilder<User> qb = userDao.queryBuilder();
		qb.where(UserDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(UserDao.Properties.Name);
		
		Query<User> q = qb.build();
		
		ArrayList<UserBean> userBeans = new ArrayList<UserBean>();
		
		for (User user : q.list()) {
			
			userBeans.add(BeanUtil.getBean(user));
		}
		
		return userBeans;
	}
	
	public void updateUsers(List<UserBean> users) {
		
		for (UserBean bean : users) {
			
			boolean isAdd = false;
			
			User user = userDao.load(bean.getRemote_id());
			
			if (user == null) {
				user = new User();
				isAdd = true;
			}
			
			BeanUtil.updateBean(user, bean);
			
			if (isAdd) {
				userDao.insert(user);
			} else {
				userDao.update(user);
			}
		} 
	}
	
	public void updateUserStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			User user = userDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				user.setUploadStatus(Constant.STATUS_NO);
				userDao.update(user);
			}
		} 
	}
	
	public boolean hasUpdate() {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM user WHERE upload_status = 'Y'", null);
			
		cursor.moveToFirst();
			
		Long count = cursor.getLong(0);
		
		cursor.close();
		
		return (count > 0);
	}
}
