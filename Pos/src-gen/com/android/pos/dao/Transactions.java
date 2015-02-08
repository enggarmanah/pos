package com.android.pos.dao;

import java.io.Serializable;
import java.util.List;

import com.android.pos.dao.DaoSession;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table TRANSACTIONS.
 */
@SuppressWarnings("serial")
public class Transactions implements Serializable {

    private Long id;
    private long merchantId;
    private String transactionNo;
    /** Not-null value. */
    private java.util.Date transactionDate;
    private Integer billAmount;
    private String discountName;
    private Integer discountPercentage;
    private Integer discountAmount;
    private Integer taxPercentage;
    private Integer taxAmount;
    private Integer serviceChargePercentage;
    private Integer serviceChargeAmount;
    private Integer totalAmount;
    private Integer paymentAmount;
    private Integer returnAmount;
    private String paymentType;
    private long cashierId;
    private String cashierName;
    private long customerId;
    private String customerName;
    private String uploadStatus;
    private String status;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TransactionsDao myDao;

    private Merchant merchant;
    private Long merchant__resolvedKey;

    private User user;
    private Long user__resolvedKey;

    private Customer customer;
    private Long customer__resolvedKey;

    private List<TransactionItem> transactionItemList;

    public Transactions() {
    }

    public Transactions(Long id) {
        this.id = id;
    }

    public Transactions(Long id, long merchantId, String transactionNo, java.util.Date transactionDate, Integer billAmount, String discountName, Integer discountPercentage, Integer discountAmount, Integer taxPercentage, Integer taxAmount, Integer serviceChargePercentage, Integer serviceChargeAmount, Integer totalAmount, Integer paymentAmount, Integer returnAmount, String paymentType, long cashierId, String cashierName, long customerId, String customerName, String uploadStatus, String status) {
        this.id = id;
        this.merchantId = merchantId;
        this.transactionNo = transactionNo;
        this.transactionDate = transactionDate;
        this.billAmount = billAmount;
        this.discountName = discountName;
        this.discountPercentage = discountPercentage;
        this.discountAmount = discountAmount;
        this.taxPercentage = taxPercentage;
        this.taxAmount = taxAmount;
        this.serviceChargePercentage = serviceChargePercentage;
        this.serviceChargeAmount = serviceChargeAmount;
        this.totalAmount = totalAmount;
        this.paymentAmount = paymentAmount;
        this.returnAmount = returnAmount;
        this.paymentType = paymentType;
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.uploadStatus = uploadStatus;
        this.status = status;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTransactionsDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    /** Not-null value. */
    public java.util.Date getTransactionDate() {
        return transactionDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTransactionDate(java.util.Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Integer billAmount) {
        this.billAmount = billAmount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Integer taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getServiceChargePercentage() {
        return serviceChargePercentage;
    }

    public void setServiceChargePercentage(Integer serviceChargePercentage) {
        this.serviceChargePercentage = serviceChargePercentage;
    }

    public Integer getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(Integer serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Integer returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public long getCashierId() {
        return cashierId;
    }

    public void setCashierId(long cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    public User getUser() {
        long __key = this.cashierId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
            	user__resolvedKey = __key;
            }
        }
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            throw new DaoException("To-one property 'cashierId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            cashierId = user.getId();
            user__resolvedKey = cashierId;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Customer getCustomer() {
        long __key = this.customerId;
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
        if (customer == null) {
            throw new DaoException("To-one property 'customerId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.customer = customer;
            customerId = customer.getId();
            customer__resolvedKey = customerId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<TransactionItem> getTransactionItemList() {
        if (transactionItemList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TransactionItemDao targetDao = daoSession.getTransactionItemDao();
            List<TransactionItem> transactionItemListNew = targetDao._queryTransactions_TransactionItemList(id);
            synchronized (this) {
                if(transactionItemList == null) {
                    transactionItemList = transactionItemListNew;
                }
            }
        }
        return transactionItemList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetTransactionItemList() {
        transactionItemList = null;
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
