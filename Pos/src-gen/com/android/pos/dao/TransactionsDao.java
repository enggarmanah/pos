package com.android.pos.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.android.pos.dao.Transactions;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TRANSACTIONS.
*/
public class TransactionsDao extends AbstractDao<Transactions, Long> {

    public static final String TABLENAME = "TRANSACTIONS";

    /**
     * Properties of entity Transactions.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property TransactionNo = new Property(2, String.class, "transactionNo", false, "TRANSACTION_NO");
        public final static Property TransactionDate = new Property(3, java.util.Date.class, "transactionDate", false, "TRANSACTION_DATE");
        public final static Property BillAmount = new Property(4, Integer.class, "billAmount", false, "BILL_AMOUNT");
        public final static Property DiscountName = new Property(5, String.class, "discountName", false, "DISCOUNT_NAME");
        public final static Property DiscountPercentage = new Property(6, Integer.class, "discountPercentage", false, "DISCOUNT_PERCENTAGE");
        public final static Property DiscountAmount = new Property(7, Integer.class, "discountAmount", false, "DISCOUNT_AMOUNT");
        public final static Property TaxPercentage = new Property(8, Integer.class, "taxPercentage", false, "TAX_PERCENTAGE");
        public final static Property TaxAmount = new Property(9, Integer.class, "taxAmount", false, "TAX_AMOUNT");
        public final static Property ServiceChargePercentage = new Property(10, Integer.class, "serviceChargePercentage", false, "SERVICE_CHARGE_PERCENTAGE");
        public final static Property ServiceChargeAmount = new Property(11, Integer.class, "serviceChargeAmount", false, "SERVICE_CHARGE_AMOUNT");
        public final static Property TotalAmount = new Property(12, Integer.class, "totalAmount", false, "TOTAL_AMOUNT");
        public final static Property PaymentAmount = new Property(13, Integer.class, "paymentAmount", false, "PAYMENT_AMOUNT");
        public final static Property ReturnAmount = new Property(14, Integer.class, "returnAmount", false, "RETURN_AMOUNT");
        public final static Property PaymentType = new Property(15, String.class, "paymentType", false, "PAYMENT_TYPE");
        public final static Property CashierId = new Property(16, long.class, "cashierId", false, "CASHIER_ID");
        public final static Property CashierName = new Property(17, String.class, "cashierName", false, "CASHIER_NAME");
        public final static Property CustomerId = new Property(18, long.class, "customerId", false, "CUSTOMER_ID");
        public final static Property CustomerName = new Property(19, String.class, "customerName", false, "CUSTOMER_NAME");
        public final static Property UploadStatus = new Property(20, String.class, "uploadStatus", false, "UPLOAD_STATUS");
        public final static Property Status = new Property(21, String.class, "status", false, "STATUS");
    };

    private DaoSession daoSession;

    private Query<Transactions> user_TransactionsListQuery;
    private Query<Transactions> customer_TransactionsListQuery;

    public TransactionsDao(DaoConfig config) {
        super(config);
    }
    
    public TransactionsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TRANSACTIONS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'TRANSACTION_NO' TEXT," + // 2: transactionNo
                "'TRANSACTION_DATE' INTEGER NOT NULL ," + // 3: transactionDate
                "'BILL_AMOUNT' INTEGER," + // 4: billAmount
                "'DISCOUNT_NAME' TEXT," + // 5: discountName
                "'DISCOUNT_PERCENTAGE' INTEGER," + // 6: discountPercentage
                "'DISCOUNT_AMOUNT' INTEGER," + // 7: discountAmount
                "'TAX_PERCENTAGE' INTEGER," + // 8: taxPercentage
                "'TAX_AMOUNT' INTEGER," + // 9: taxAmount
                "'SERVICE_CHARGE_PERCENTAGE' INTEGER," + // 10: serviceChargePercentage
                "'SERVICE_CHARGE_AMOUNT' INTEGER," + // 11: serviceChargeAmount
                "'TOTAL_AMOUNT' INTEGER," + // 12: totalAmount
                "'PAYMENT_AMOUNT' INTEGER," + // 13: paymentAmount
                "'RETURN_AMOUNT' INTEGER," + // 14: returnAmount
                "'PAYMENT_TYPE' TEXT," + // 15: paymentType
                "'CASHIER_ID' INTEGER NOT NULL ," + // 16: cashierId
                "'CASHIER_NAME' TEXT," + // 17: cashierName
                "'CUSTOMER_ID' INTEGER NOT NULL ," + // 18: customerId
                "'CUSTOMER_NAME' TEXT," + // 19: customerName
                "'UPLOAD_STATUS' TEXT," + // 20: uploadStatus
                "'STATUS' TEXT);"); // 21: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TRANSACTIONS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Transactions entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMerchantId());
 
        String transactionNo = entity.getTransactionNo();
        if (transactionNo != null) {
            stmt.bindString(3, transactionNo);
        }
        stmt.bindLong(4, entity.getTransactionDate().getTime());
 
        Integer billAmount = entity.getBillAmount();
        if (billAmount != null) {
            stmt.bindLong(5, billAmount);
        }
 
        String discountName = entity.getDiscountName();
        if (discountName != null) {
            stmt.bindString(6, discountName);
        }
 
        Integer discountPercentage = entity.getDiscountPercentage();
        if (discountPercentage != null) {
            stmt.bindLong(7, discountPercentage);
        }
 
        Integer discountAmount = entity.getDiscountAmount();
        if (discountAmount != null) {
            stmt.bindLong(8, discountAmount);
        }
 
        Integer taxPercentage = entity.getTaxPercentage();
        if (taxPercentage != null) {
            stmt.bindLong(9, taxPercentage);
        }
 
        Integer taxAmount = entity.getTaxAmount();
        if (taxAmount != null) {
            stmt.bindLong(10, taxAmount);
        }
 
        Integer serviceChargePercentage = entity.getServiceChargePercentage();
        if (serviceChargePercentage != null) {
            stmt.bindLong(11, serviceChargePercentage);
        }
 
        Integer serviceChargeAmount = entity.getServiceChargeAmount();
        if (serviceChargeAmount != null) {
            stmt.bindLong(12, serviceChargeAmount);
        }
 
        Integer totalAmount = entity.getTotalAmount();
        if (totalAmount != null) {
            stmt.bindLong(13, totalAmount);
        }
 
        Integer paymentAmount = entity.getPaymentAmount();
        if (paymentAmount != null) {
            stmt.bindLong(14, paymentAmount);
        }
 
        Integer returnAmount = entity.getReturnAmount();
        if (returnAmount != null) {
            stmt.bindLong(15, returnAmount);
        }
 
        String paymentType = entity.getPaymentType();
        if (paymentType != null) {
            stmt.bindString(16, paymentType);
        }
        stmt.bindLong(17, entity.getCashierId());
 
        String cashierName = entity.getCashierName();
        if (cashierName != null) {
            stmt.bindString(18, cashierName);
        }
        stmt.bindLong(19, entity.getCustomerId());
 
        String customerName = entity.getCustomerName();
        if (customerName != null) {
            stmt.bindString(20, customerName);
        }
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(21, uploadStatus);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(22, status);
        }
    }

    @Override
    protected void attachEntity(Transactions entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Transactions readEntity(Cursor cursor, int offset) {
        Transactions entity = new Transactions( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // transactionNo
            new java.util.Date(cursor.getLong(offset + 3)), // transactionDate
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // billAmount
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // discountName
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // discountPercentage
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // discountAmount
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // taxPercentage
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // taxAmount
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // serviceChargePercentage
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // serviceChargeAmount
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // totalAmount
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // paymentAmount
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // returnAmount
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // paymentType
            cursor.getLong(offset + 16), // cashierId
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // cashierName
            cursor.getLong(offset + 18), // customerId
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // customerName
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // uploadStatus
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21) // status
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Transactions entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setTransactionNo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTransactionDate(new java.util.Date(cursor.getLong(offset + 3)));
        entity.setBillAmount(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setDiscountName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDiscountPercentage(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setDiscountAmount(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setTaxPercentage(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setTaxAmount(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setServiceChargePercentage(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setServiceChargeAmount(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setTotalAmount(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setPaymentAmount(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setReturnAmount(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setPaymentType(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCashierId(cursor.getLong(offset + 16));
        entity.setCashierName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCustomerId(cursor.getLong(offset + 18));
        entity.setCustomerName(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setUploadStatus(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setStatus(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Transactions entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Transactions entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "transactionsList" to-many relationship of User. */
    public List<Transactions> _queryUser_TransactionsList(long cashierId) {
        synchronized (this) {
            if (user_TransactionsListQuery == null) {
                QueryBuilder<Transactions> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CashierId.eq(null));
                queryBuilder.orderRaw("TRANSACTION_DATE ASC");
                user_TransactionsListQuery = queryBuilder.build();
            }
        }
        Query<Transactions> query = user_TransactionsListQuery.forCurrentThread();
        query.setParameter(0, cashierId);
        return query.list();
    }

    /** Internal query to resolve the "transactionsList" to-many relationship of Customer. */
    public List<Transactions> _queryCustomer_TransactionsList(long customerId) {
        synchronized (this) {
            if (customer_TransactionsListQuery == null) {
                QueryBuilder<Transactions> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CustomerId.eq(null));
                queryBuilder.orderRaw("TRANSACTION_DATE ASC");
                customer_TransactionsListQuery = queryBuilder.build();
            }
        }
        Query<Transactions> query = customer_TransactionsListQuery.forCurrentThread();
        query.setParameter(0, customerId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMerchantDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getUserDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getCustomerDao().getAllColumns());
            builder.append(" FROM TRANSACTIONS T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN USER T1 ON T.'CASHIER_ID'=T1.'_id'");
            builder.append(" LEFT JOIN CUSTOMER T2 ON T.'CUSTOMER_ID'=T2.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Transactions loadCurrentDeep(Cursor cursor, boolean lock) {
        Transactions entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }
        offset += daoSession.getMerchantDao().getAllColumns().length;

        User user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
         if(user != null) {
            entity.setUser(user);
        }
        offset += daoSession.getUserDao().getAllColumns().length;

        Customer customer = loadCurrentOther(daoSession.getCustomerDao(), cursor, offset);
         if(customer != null) {
            entity.setCustomer(customer);
        }

        return entity;    
    }

    public Transactions loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Transactions> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Transactions> list = new ArrayList<Transactions>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Transactions> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Transactions> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
