package com.android.pos.dao;

import java.io.Serializable;
import java.util.List;

import com.android.pos.dao.DaoSession;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PRODUCT_GROUP.
 */
@SuppressWarnings("serial")
public class ProductGroup implements Serializable {

    private Long id;
    /** Not-null value. */
    private String name;
    private String uploadStatus;
    private String createBy;
    private java.util.Date createDate;
    private String updateBy;
    private java.util.Date updateDate;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ProductGroupDao myDao;

    private List<Product> productList;

    public ProductGroup() {
    }

    public ProductGroup(Long id) {
        this.id = id;
    }

    public ProductGroup(Long id, String name, String uploadStatus, String createBy, java.util.Date createDate, String updateBy, java.util.Date updateDate) {
        this.id = id;
        this.name = name;
        this.uploadStatus = uploadStatus;
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProductGroupDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
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

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Product> getProductList() {
        if (productList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProductDao targetDao = daoSession.getProductDao();
            List<Product> productListNew = targetDao._queryProductGroup_ProductList(id);
            synchronized (this) {
                if(productList == null) {
                    productList = productListNew;
                }
            }
        }
        return productList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetProductList() {
        productList = null;
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
