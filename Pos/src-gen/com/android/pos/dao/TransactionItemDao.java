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

import com.android.pos.dao.TransactionItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TRANSACTION_ITEM.
*/
public class TransactionItemDao extends AbstractDao<TransactionItem, Long> {

    public static final String TABLENAME = "TRANSACTION_ITEM";

    /**
     * Properties of entity TransactionItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property TransactionId = new Property(2, long.class, "transactionId", false, "TRANSACTION_ID");
        public final static Property ProductId = new Property(3, long.class, "productId", false, "PRODUCT_ID");
        public final static Property ProductName = new Property(4, String.class, "productName", false, "PRODUCT_NAME");
        public final static Property ProductType = new Property(5, String.class, "productType", false, "PRODUCT_TYPE");
        public final static Property Price = new Property(6, Integer.class, "price", false, "PRICE");
        public final static Property CostPrice = new Property(7, Integer.class, "costPrice", false, "COST_PRICE");
        public final static Property Discount = new Property(8, Integer.class, "discount", false, "DISCOUNT");
        public final static Property Quantity = new Property(9, Integer.class, "quantity", false, "QUANTITY");
        public final static Property EmployeeId = new Property(10, long.class, "employeeId", false, "EMPLOYEE_ID");
        public final static Property UploadStatus = new Property(11, String.class, "uploadStatus", false, "UPLOAD_STATUS");
    };

    private DaoSession daoSession;

    private Query<TransactionItem> transactions_TransactionItemListQuery;
    private Query<TransactionItem> employee_TransactionItemListQuery;

    public TransactionItemDao(DaoConfig config) {
        super(config);
    }
    
    public TransactionItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TRANSACTION_ITEM' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'TRANSACTION_ID' INTEGER NOT NULL ," + // 2: transactionId
                "'PRODUCT_ID' INTEGER NOT NULL ," + // 3: productId
                "'PRODUCT_NAME' TEXT," + // 4: productName
                "'PRODUCT_TYPE' TEXT," + // 5: productType
                "'PRICE' INTEGER," + // 6: price
                "'COST_PRICE' INTEGER," + // 7: costPrice
                "'DISCOUNT' INTEGER," + // 8: discount
                "'QUANTITY' INTEGER," + // 9: quantity
                "'EMPLOYEE_ID' INTEGER NOT NULL ," + // 10: employeeId
                "'UPLOAD_STATUS' TEXT);"); // 11: uploadStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TRANSACTION_ITEM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TransactionItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMerchantId());
        stmt.bindLong(3, entity.getTransactionId());
        stmt.bindLong(4, entity.getProductId());
 
        String productName = entity.getProductName();
        if (productName != null) {
            stmt.bindString(5, productName);
        }
 
        String productType = entity.getProductType();
        if (productType != null) {
            stmt.bindString(6, productType);
        }
 
        Integer price = entity.getPrice();
        if (price != null) {
            stmt.bindLong(7, price);
        }
 
        Integer costPrice = entity.getCostPrice();
        if (costPrice != null) {
            stmt.bindLong(8, costPrice);
        }
 
        Integer discount = entity.getDiscount();
        if (discount != null) {
            stmt.bindLong(9, discount);
        }
 
        Integer quantity = entity.getQuantity();
        if (quantity != null) {
            stmt.bindLong(10, quantity);
        }
        stmt.bindLong(11, entity.getEmployeeId());
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(12, uploadStatus);
        }
    }

    @Override
    protected void attachEntity(TransactionItem entity) {
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
    public TransactionItem readEntity(Cursor cursor, int offset) {
        TransactionItem entity = new TransactionItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            cursor.getLong(offset + 2), // transactionId
            cursor.getLong(offset + 3), // productId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // productName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // productType
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // price
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // costPrice
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // discount
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // quantity
            cursor.getLong(offset + 10), // employeeId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // uploadStatus
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TransactionItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setTransactionId(cursor.getLong(offset + 2));
        entity.setProductId(cursor.getLong(offset + 3));
        entity.setProductName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setProductType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPrice(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setCostPrice(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setDiscount(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setQuantity(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setEmployeeId(cursor.getLong(offset + 10));
        entity.setUploadStatus(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TransactionItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TransactionItem entity) {
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
    
    /** Internal query to resolve the "transactionItemList" to-many relationship of Transactions. */
    public List<TransactionItem> _queryTransactions_TransactionItemList(long transactionId) {
        synchronized (this) {
            if (transactions_TransactionItemListQuery == null) {
                QueryBuilder<TransactionItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TransactionId.eq(null));
                queryBuilder.orderRaw("PRODUCT_NAME ASC");
                transactions_TransactionItemListQuery = queryBuilder.build();
            }
        }
        Query<TransactionItem> query = transactions_TransactionItemListQuery.forCurrentThread();
        query.setParameter(0, transactionId);
        return query.list();
    }

    /** Internal query to resolve the "transactionItemList" to-many relationship of Employee. */
    public List<TransactionItem> _queryEmployee_TransactionItemList(long transactionId) {
        synchronized (this) {
            if (employee_TransactionItemListQuery == null) {
                QueryBuilder<TransactionItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TransactionId.eq(null));
                queryBuilder.orderRaw("_id ASC");
                employee_TransactionItemListQuery = queryBuilder.build();
            }
        }
        Query<TransactionItem> query = employee_TransactionItemListQuery.forCurrentThread();
        query.setParameter(0, transactionId);
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
            SqlUtils.appendColumns(builder, "T1", daoSession.getTransactionsDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getProductDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getEmployeeDao().getAllColumns());
            builder.append(" FROM TRANSACTION_ITEM T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN TRANSACTIONS T1 ON T.'TRANSACTION_ID'=T1.'_id'");
            builder.append(" LEFT JOIN PRODUCT T2 ON T.'PRODUCT_ID'=T2.'_id'");
            builder.append(" LEFT JOIN EMPLOYEE T3 ON T.'EMPLOYEE_ID'=T3.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected TransactionItem loadCurrentDeep(Cursor cursor, boolean lock) {
        TransactionItem entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }
        offset += daoSession.getMerchantDao().getAllColumns().length;

        Transactions transactions = loadCurrentOther(daoSession.getTransactionsDao(), cursor, offset);
         if(transactions != null) {
            entity.setTransactions(transactions);
        }
        offset += daoSession.getTransactionsDao().getAllColumns().length;

        Product product = loadCurrentOther(daoSession.getProductDao(), cursor, offset);
         if(product != null) {
            entity.setProduct(product);
        }
        offset += daoSession.getProductDao().getAllColumns().length;

        Employee employee = loadCurrentOther(daoSession.getEmployeeDao(), cursor, offset);
         if(employee != null) {
            entity.setEmployee(employee);
        }

        return entity;    
    }

    public TransactionItem loadDeep(Long key) {
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
    public List<TransactionItem> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TransactionItem> list = new ArrayList<TransactionItem>(count);
        
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
    
    protected List<TransactionItem> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TransactionItem> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
