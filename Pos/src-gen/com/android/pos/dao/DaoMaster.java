package com.android.pos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.android.pos.dao.MerchantDao;
import com.android.pos.dao.ProductGroupDao;
import com.android.pos.dao.ProductDao;
import com.android.pos.dao.CustomerDao;
import com.android.pos.dao.EmployeeDao;
import com.android.pos.dao.UserDao;
import com.android.pos.dao.TransactionsDao;
import com.android.pos.dao.TransactionItemDao;
import com.android.pos.dao.DiscountDao;
import com.android.pos.dao.OrdersDao;
import com.android.pos.dao.OrderItemDao;
import com.android.pos.dao.SupplierDao;
import com.android.pos.dao.BillsDao;
import com.android.pos.dao.BillsItemDao;
import com.android.pos.dao.InventoryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 26): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 26;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        MerchantDao.createTable(db, ifNotExists);
        ProductGroupDao.createTable(db, ifNotExists);
        ProductDao.createTable(db, ifNotExists);
        CustomerDao.createTable(db, ifNotExists);
        EmployeeDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
        TransactionsDao.createTable(db, ifNotExists);
        TransactionItemDao.createTable(db, ifNotExists);
        DiscountDao.createTable(db, ifNotExists);
        OrdersDao.createTable(db, ifNotExists);
        OrderItemDao.createTable(db, ifNotExists);
        SupplierDao.createTable(db, ifNotExists);
        BillsDao.createTable(db, ifNotExists);
        BillsItemDao.createTable(db, ifNotExists);
        InventoryDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        MerchantDao.dropTable(db, ifExists);
        ProductGroupDao.dropTable(db, ifExists);
        ProductDao.dropTable(db, ifExists);
        CustomerDao.dropTable(db, ifExists);
        EmployeeDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
        TransactionsDao.dropTable(db, ifExists);
        TransactionItemDao.dropTable(db, ifExists);
        DiscountDao.dropTable(db, ifExists);
        OrdersDao.dropTable(db, ifExists);
        OrderItemDao.dropTable(db, ifExists);
        SupplierDao.dropTable(db, ifExists);
        BillsDao.dropTable(db, ifExists);
        BillsItemDao.dropTable(db, ifExists);
        InventoryDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(MerchantDao.class);
        registerDaoClass(ProductGroupDao.class);
        registerDaoClass(ProductDao.class);
        registerDaoClass(CustomerDao.class);
        registerDaoClass(EmployeeDao.class);
        registerDaoClass(UserDao.class);
        registerDaoClass(TransactionsDao.class);
        registerDaoClass(TransactionItemDao.class);
        registerDaoClass(DiscountDao.class);
        registerDaoClass(OrdersDao.class);
        registerDaoClass(OrderItemDao.class);
        registerDaoClass(SupplierDao.class);
        registerDaoClass(BillsDao.class);
        registerDaoClass(BillsItemDao.class);
        registerDaoClass(InventoryDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
