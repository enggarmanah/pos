package com.android.pos.dao;

import java.io.Serializable;
import java.util.List;

import com.android.pos.dao.DaoSession;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER.
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private Long id;
    private String refId;
    private long merchantId;
    private String name;
    private String userId;
    private String password;
    private String role;
    private String status;
    private String uploadStatus;
    private String createBy;
    private java.util.Date createDate;
    private String updateBy;
    private java.util.Date updateDate;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UserDao myDao;

    private Merchant merchant;
    private Long merchant__resolvedKey;

    private List<Transactions> transactionsList;
    private List<UserAccess> userAccessList;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String refId, long merchantId, String name, String userId, String password, String role, String status, String uploadStatus, String createBy, java.util.Date createDate, String updateBy, java.util.Date updateDate) {
        this.id = id;
        this.refId = refId;
        this.merchantId = merchantId;
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.status = status;
        this.uploadStatus = uploadStatus;
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    /** To-one relationship, resolved on first access. */
    public Merchant getMerchant() {
        long __key = this.merchantId;
        if (merchant__resolvedKey == null || !merchant__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MerchantDao targetDao = daoSession.getMerchantDao();
            Merchant merchantNew = targetDao.load(__key);
            synchronized (this) {
                merchant = merchantNew;
            	merchant__resolvedKey = __key;
            }
        }
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        if (merchant == null) {
            throw new DaoException("To-one property 'merchantId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.merchant = merchant;
            merchantId = merchant.getId();
            merchant__resolvedKey = merchantId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Transactions> getTransactionsList() {
        if (transactionsList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TransactionsDao targetDao = daoSession.getTransactionsDao();
            List<Transactions> transactionsListNew = targetDao._queryUser_TransactionsList(id);
            synchronized (this) {
                if(transactionsList == null) {
                    transactionsList = transactionsListNew;
                }
            }
        }
        return transactionsList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetTransactionsList() {
        transactionsList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<UserAccess> getUserAccessList() {
        if (userAccessList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserAccessDao targetDao = daoSession.getUserAccessDao();
            List<UserAccess> userAccessListNew = targetDao._queryUser_UserAccessList(id);
            synchronized (this) {
                if(userAccessList == null) {
                    userAccessList = userAccessListNew;
                }
            }
        }
        return userAccessList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetUserAccessList() {
        userAccessList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
