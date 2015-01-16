package com.android.pos.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.android.pos.dao.Merchant;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.Product;
import com.android.pos.dao.Customer;
import com.android.pos.dao.Employee;
import com.android.pos.dao.User;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.Discount;

import com.android.pos.dao.MerchantDao;
import com.android.pos.dao.ProductGroupDao;
import com.android.pos.dao.ProductDao;
import com.android.pos.dao.CustomerDao;
import com.android.pos.dao.EmployeeDao;
import com.android.pos.dao.UserDao;
import com.android.pos.dao.TransactionsDao;
import com.android.pos.dao.TransactionItemDao;
import com.android.pos.dao.DiscountDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig merchantDaoConfig;
    private final DaoConfig productGroupDaoConfig;
    private final DaoConfig productDaoConfig;
    private final DaoConfig customerDaoConfig;
    private final DaoConfig employeeDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig transactionsDaoConfig;
    private final DaoConfig transactionItemDaoConfig;
    private final DaoConfig discountDaoConfig;

    private final MerchantDao merchantDao;
    private final ProductGroupDao productGroupDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final EmployeeDao employeeDao;
    private final UserDao userDao;
    private final TransactionsDao transactionsDao;
    private final TransactionItemDao transactionItemDao;
    private final DiscountDao discountDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        merchantDaoConfig = daoConfigMap.get(MerchantDao.class).clone();
        merchantDaoConfig.initIdentityScope(type);

        productGroupDaoConfig = daoConfigMap.get(ProductGroupDao.class).clone();
        productGroupDaoConfig.initIdentityScope(type);

        productDaoConfig = daoConfigMap.get(ProductDao.class).clone();
        productDaoConfig.initIdentityScope(type);

        customerDaoConfig = daoConfigMap.get(CustomerDao.class).clone();
        customerDaoConfig.initIdentityScope(type);

        employeeDaoConfig = daoConfigMap.get(EmployeeDao.class).clone();
        employeeDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        transactionsDaoConfig = daoConfigMap.get(TransactionsDao.class).clone();
        transactionsDaoConfig.initIdentityScope(type);

        transactionItemDaoConfig = daoConfigMap.get(TransactionItemDao.class).clone();
        transactionItemDaoConfig.initIdentityScope(type);

        discountDaoConfig = daoConfigMap.get(DiscountDao.class).clone();
        discountDaoConfig.initIdentityScope(type);

        merchantDao = new MerchantDao(merchantDaoConfig, this);
        productGroupDao = new ProductGroupDao(productGroupDaoConfig, this);
        productDao = new ProductDao(productDaoConfig, this);
        customerDao = new CustomerDao(customerDaoConfig, this);
        employeeDao = new EmployeeDao(employeeDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        transactionsDao = new TransactionsDao(transactionsDaoConfig, this);
        transactionItemDao = new TransactionItemDao(transactionItemDaoConfig, this);
        discountDao = new DiscountDao(discountDaoConfig, this);

        registerDao(Merchant.class, merchantDao);
        registerDao(ProductGroup.class, productGroupDao);
        registerDao(Product.class, productDao);
        registerDao(Customer.class, customerDao);
        registerDao(Employee.class, employeeDao);
        registerDao(User.class, userDao);
        registerDao(Transactions.class, transactionsDao);
        registerDao(TransactionItem.class, transactionItemDao);
        registerDao(Discount.class, discountDao);
    }
    
    public void clear() {
        merchantDaoConfig.getIdentityScope().clear();
        productGroupDaoConfig.getIdentityScope().clear();
        productDaoConfig.getIdentityScope().clear();
        customerDaoConfig.getIdentityScope().clear();
        employeeDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
        transactionsDaoConfig.getIdentityScope().clear();
        transactionItemDaoConfig.getIdentityScope().clear();
        discountDaoConfig.getIdentityScope().clear();
    }

    public MerchantDao getMerchantDao() {
        return merchantDao;
    }

    public ProductGroupDao getProductGroupDao() {
        return productGroupDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TransactionsDao getTransactionsDao() {
        return transactionsDao;
    }

    public TransactionItemDao getTransactionItemDao() {
        return transactionItemDao;
    }

    public DiscountDao getDiscountDao() {
        return discountDao;
    }

}
