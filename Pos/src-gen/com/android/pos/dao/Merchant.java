package com.android.pos.dao;

import java.io.Serializable;
import java.util.List;

import com.android.pos.dao.DaoSession;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table MERCHANT.
 */
@SuppressWarnings("serial")
public class Merchant implements Serializable {

    private Long id;
    private String refId;
    /** Not-null value. */
    private String name;
    private String type;
    private String address;
    private String telephone;
    private String contactName;
    private String contactTelephone;
    private String contactEmail;
    private String loginId;
    private String printerType;
    private String printerAddress;
    private String printerMiniFont;
    private Integer printerLineSize;
    private String printerRequired;
    private String password;
    private java.util.Date periodStart;
    private java.util.Date periodEnd;
    private Integer priceTypeCount;
    private String priceLabel1;
    private String priceLabel2;
    private String priceLabel3;
    private String discountType;
    private String paymentType;
    private Float taxPercentage;
    private Float serviceChargePercentage;
    private Boolean isLogin;
    private String status;
    private String uploadStatus;
    private String createBy;
    private java.util.Date createDate;
    private String updateBy;
    private java.util.Date updateDate;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient MerchantDao myDao;

    private List<Product> productList;
    private List<Customer> customerList;
    private List<Employee> employeeList;
    private List<User> userList;
    private List<MerchantAccess> merchantAccessList;

    public Merchant() {
    }

    public Merchant(Long id) {
        this.id = id;
    }

    public Merchant(Long id, String refId, String name, String type, String address, String telephone, String contactName, String contactTelephone, String contactEmail, String loginId, String printerType, String printerAddress, String printerMiniFont, Integer printerLineSize, String printerRequired, String password, java.util.Date periodStart, java.util.Date periodEnd, Integer priceTypeCount, String priceLabel1, String priceLabel2, String priceLabel3, String discountType, String paymentType, Float taxPercentage, Float serviceChargePercentage, Boolean isLogin, String status, String uploadStatus, String createBy, java.util.Date createDate, String updateBy, java.util.Date updateDate) {
        this.id = id;
        this.refId = refId;
        this.name = name;
        this.type = type;
        this.address = address;
        this.telephone = telephone;
        this.contactName = contactName;
        this.contactTelephone = contactTelephone;
        this.contactEmail = contactEmail;
        this.loginId = loginId;
        this.printerType = printerType;
        this.printerAddress = printerAddress;
        this.printerMiniFont = printerMiniFont;
        this.printerLineSize = printerLineSize;
        this.printerRequired = printerRequired;
        this.password = password;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.priceTypeCount = priceTypeCount;
        this.priceLabel1 = priceLabel1;
        this.priceLabel2 = priceLabel2;
        this.priceLabel3 = priceLabel3;
        this.discountType = discountType;
        this.paymentType = paymentType;
        this.taxPercentage = taxPercentage;
        this.serviceChargePercentage = serviceChargePercentage;
        this.isLogin = isLogin;
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
        myDao = daoSession != null ? daoSession.getMerchantDao() : null;
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

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPrinterType() {
        return printerType;
    }

    public void setPrinterType(String printerType) {
        this.printerType = printerType;
    }

    public String getPrinterAddress() {
        return printerAddress;
    }

    public void setPrinterAddress(String printerAddress) {
        this.printerAddress = printerAddress;
    }

    public String getPrinterMiniFont() {
        return printerMiniFont;
    }

    public void setPrinterMiniFont(String printerMiniFont) {
        this.printerMiniFont = printerMiniFont;
    }

    public Integer getPrinterLineSize() {
        return printerLineSize;
    }

    public void setPrinterLineSize(Integer printerLineSize) {
        this.printerLineSize = printerLineSize;
    }

    public String getPrinterRequired() {
        return printerRequired;
    }

    public void setPrinterRequired(String printerRequired) {
        this.printerRequired = printerRequired;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.util.Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(java.util.Date periodStart) {
        this.periodStart = periodStart;
    }

    public java.util.Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(java.util.Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public Integer getPriceTypeCount() {
        return priceTypeCount;
    }

    public void setPriceTypeCount(Integer priceTypeCount) {
        this.priceTypeCount = priceTypeCount;
    }

    public String getPriceLabel1() {
        return priceLabel1;
    }

    public void setPriceLabel1(String priceLabel1) {
        this.priceLabel1 = priceLabel1;
    }

    public String getPriceLabel2() {
        return priceLabel2;
    }

    public void setPriceLabel2(String priceLabel2) {
        this.priceLabel2 = priceLabel2;
    }

    public String getPriceLabel3() {
        return priceLabel3;
    }

    public void setPriceLabel3(String priceLabel3) {
        this.priceLabel3 = priceLabel3;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Float getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Float taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Float getServiceChargePercentage() {
        return serviceChargePercentage;
    }

    public void setServiceChargePercentage(Float serviceChargePercentage) {
        this.serviceChargePercentage = serviceChargePercentage;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
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

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Product> getProductList() {
        if (productList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProductDao targetDao = daoSession.getProductDao();
            List<Product> productListNew = targetDao._queryMerchant_ProductList(id);
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

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Customer> getCustomerList() {
        if (customerList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CustomerDao targetDao = daoSession.getCustomerDao();
            List<Customer> customerListNew = targetDao._queryMerchant_CustomerList(id);
            synchronized (this) {
                if(customerList == null) {
                    customerList = customerListNew;
                }
            }
        }
        return customerList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCustomerList() {
        customerList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Employee> getEmployeeList() {
        if (employeeList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EmployeeDao targetDao = daoSession.getEmployeeDao();
            List<Employee> employeeListNew = targetDao._queryMerchant_EmployeeList(id);
            synchronized (this) {
                if(employeeList == null) {
                    employeeList = employeeListNew;
                }
            }
        }
        return employeeList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetEmployeeList() {
        employeeList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<User> getUserList() {
        if (userList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            List<User> userListNew = targetDao._queryMerchant_UserList(id);
            synchronized (this) {
                if(userList == null) {
                    userList = userListNew;
                }
            }
        }
        return userList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetUserList() {
        userList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<MerchantAccess> getMerchantAccessList() {
        if (merchantAccessList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MerchantAccessDao targetDao = daoSession.getMerchantAccessDao();
            List<MerchantAccess> merchantAccessListNew = targetDao._queryMerchant_MerchantAccessList(id);
            synchronized (this) {
                if(merchantAccessList == null) {
                    merchantAccessList = merchantAccessListNew;
                }
            }
        }
        return merchantAccessList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetMerchantAccessList() {
        merchantAccessList = null;
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
