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

import com.android.pos.dao.Orders;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ORDERS.
*/
public class OrdersDao extends AbstractDao<Orders, Long> {

    public static final String TABLENAME = "ORDERS";

    /**
     * Properties of entity Orders.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property OrderDate = new Property(2, java.util.Date.class, "orderDate", false, "ORDER_DATE");
        public final static Property OrderType = new Property(3, String.class, "orderType", false, "ORDER_TYPE");
        public final static Property OrderReference = new Property(4, String.class, "orderReference", false, "ORDER_REFERENCE");
        public final static Property CustomerName = new Property(5, String.class, "customerName", false, "CUSTOMER_NAME");
        public final static Property Status = new Property(6, String.class, "status", false, "STATUS");
    };

    private DaoSession daoSession;


    public OrdersDao(DaoConfig config) {
        super(config);
    }
    
    public OrdersDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ORDERS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'ORDER_DATE' INTEGER NOT NULL ," + // 2: orderDate
                "'ORDER_TYPE' TEXT," + // 3: orderType
                "'ORDER_REFERENCE' TEXT," + // 4: orderReference
                "'CUSTOMER_NAME' TEXT," + // 5: customerName
                "'STATUS' TEXT);"); // 6: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ORDERS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Orders entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMerchantId());
        stmt.bindLong(3, entity.getOrderDate().getTime());
 
        String orderType = entity.getOrderType();
        if (orderType != null) {
            stmt.bindString(4, orderType);
        }
 
        String orderReference = entity.getOrderReference();
        if (orderReference != null) {
            stmt.bindString(5, orderReference);
        }
 
        String customerName = entity.getCustomerName();
        if (customerName != null) {
            stmt.bindString(6, customerName);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(7, status);
        }
    }

    @Override
    protected void attachEntity(Orders entity) {
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
    public Orders readEntity(Cursor cursor, int offset) {
        Orders entity = new Orders( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            new java.util.Date(cursor.getLong(offset + 2)), // orderDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // orderType
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // orderReference
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // customerName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // status
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Orders entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setOrderDate(new java.util.Date(cursor.getLong(offset + 2)));
        entity.setOrderType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setOrderReference(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCustomerName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStatus(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Orders entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Orders entity) {
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
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMerchantDao().getAllColumns());
            builder.append(" FROM ORDERS T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Orders loadCurrentDeep(Cursor cursor, boolean lock) {
        Orders entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }

        return entity;    
    }

    public Orders loadDeep(Long key) {
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
    public List<Orders> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Orders> list = new ArrayList<Orders>(count);
        
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
    
    protected List<Orders> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Orders> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
