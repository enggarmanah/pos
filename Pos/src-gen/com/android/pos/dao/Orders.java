package com.android.pos.dao;

import java.io.Serializable;
import java.util.List;

import com.android.pos.dao.DaoSession;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ORDERS.
 */
@SuppressWarnings("serial")
public class Orders implements Serializable {

    private Long id;
    private String refId;
    private long merchantId;
    private String orderNo;
    /** Not-null value. */
    private java.util.Date orderDate;
    private String orderType;
    private String orderReference;
    private Long waitressId;
    private String waitressName;
    private Long customerId;
    private String customerName;
    private String status;
    private String uploadStatus;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient OrdersDao myDao;

    private Merchant merchant;
    private Long merchant__resolvedKey;

    private Employee employee;
    private Long employee__resolvedKey;

    private Customer customer;
    private Long customer__resolvedKey;

    private List<OrderItem> orderItemList;

    public Orders() {
    }

    public Orders(Long id) {
        this.id = id;
    }

    public Orders(Long id, String refId, long merchantId, String orderNo, java.util.Date orderDate, String orderType, String orderReference, Long waitressId, String waitressName, Long customerId, String customerName, String status, String uploadStatus) {
        this.id = id;
        this.refId = refId;
        this.merchantId = merchantId;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderReference = orderReference;
        this.waitressId = waitressId;
        this.waitressName = waitressName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.uploadStatus = uploadStatus;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrdersDao() : null;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /** Not-null value. */
    public java.util.Date getOrderDate() {
        return orderDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public Long getWaitressId() {
        return waitressId;
    }

    public void setWaitressId(Long waitressId) {
        this.waitressId = waitressId;
    }

    public String getWaitressName() {
        return waitressName;
    }

    public void setWaitressName(String waitressName) {
        this.waitressName = waitressName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    /** To-one relationship, resolved on first access. */
    public Employee getEmployee() {
        Long __key = this.waitressId;
        if (employee__resolvedKey == null || !employee__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EmployeeDao targetDao = daoSession.getEmployeeDao();
            Employee employeeNew = targetDao.load(__key);
            synchronized (this) {
                employee = employeeNew;
            	employee__resolvedKey = __key;
            }
        }
        return employee;
    }

    public void setEmployee(Employee employee) {
        synchronized (this) {
            this.employee = employee;
            waitressId = employee == null ? null : employee.getId();
            employee__resolvedKey = waitressId;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Customer getCustomer() {
        Long __key = this.customerId;
        if (customer__resolvedKey == null || !customer__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CustomerDao targetDao = daoSession.getCustomerDao();
            Customer customerNew = targetDao.load(__key);
            synchronized (this) {
                customer = customerNew;
            	customer__resolvedKey = __key;
            }
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        synchronized (this) {
            this.customer = customer;
            customerId = customer == null ? null : customer.getId();
            customer__resolvedKey = customerId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<OrderItem> getOrderItemList() {
        if (orderItemList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrderItemDao targetDao = daoSession.getOrderItemDao();
            List<OrderItem> orderItemListNew = targetDao._queryOrders_OrderItemList(id);
            synchronized (this) {
                if(orderItemList == null) {
                    orderItemList = orderItemListNew;
                }
            }
        }
        return orderItemList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetOrderItemList() {
        orderItemList = null;
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
