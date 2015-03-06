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

import com.android.pos.dao.Bills;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BILLS.
*/
public class BillsDao extends AbstractDao<Bills, Long> {

    public static final String TABLENAME = "BILLS";

    /**
     * Properties of entity Bills.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property BillReference = new Property(2, String.class, "billReference", false, "BILL_REFERENCE");
        public final static Property BillType = new Property(3, String.class, "billType", false, "BILL_TYPE");
        public final static Property BillDate = new Property(4, java.util.Date.class, "billDate", false, "BILL_DATE");
        public final static Property BillDueDate = new Property(5, java.util.Date.class, "billDueDate", false, "BILL_DUE_DATE");
        public final static Property BillAmount = new Property(6, Integer.class, "billAmount", false, "BILL_AMOUNT");
        public final static Property Payment = new Property(7, Integer.class, "payment", false, "PAYMENT");
        public final static Property SupplierId = new Property(8, long.class, "supplierId", false, "SUPPLIER_ID");
        public final static Property DeliveryDate = new Property(9, java.util.Date.class, "deliveryDate", false, "DELIVERY_DATE");
        public final static Property Remarks = new Property(10, String.class, "remarks", false, "REMARKS");
        public final static Property Status = new Property(11, String.class, "status", false, "STATUS");
        public final static Property UploadStatus = new Property(12, String.class, "uploadStatus", false, "UPLOAD_STATUS");
        public final static Property CreateBy = new Property(13, String.class, "createBy", false, "CREATE_BY");
        public final static Property CreateDate = new Property(14, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property UpdateBy = new Property(15, String.class, "updateBy", false, "UPDATE_BY");
        public final static Property UpdateDate = new Property(16, java.util.Date.class, "updateDate", false, "UPDATE_DATE");
    };

    private DaoSession daoSession;


    public BillsDao(DaoConfig config) {
        super(config);
    }
    
    public BillsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BILLS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'BILL_REFERENCE' TEXT," + // 2: billReference
                "'BILL_TYPE' TEXT," + // 3: billType
                "'BILL_DATE' INTEGER," + // 4: billDate
                "'BILL_DUE_DATE' INTEGER," + // 5: billDueDate
                "'BILL_AMOUNT' INTEGER," + // 6: billAmount
                "'PAYMENT' INTEGER," + // 7: payment
                "'SUPPLIER_ID' INTEGER NOT NULL ," + // 8: supplierId
                "'DELIVERY_DATE' INTEGER," + // 9: deliveryDate
                "'REMARKS' TEXT," + // 10: remarks
                "'STATUS' TEXT," + // 11: status
                "'UPLOAD_STATUS' TEXT," + // 12: uploadStatus
                "'CREATE_BY' TEXT," + // 13: createBy
                "'CREATE_DATE' INTEGER," + // 14: createDate
                "'UPDATE_BY' TEXT," + // 15: updateBy
                "'UPDATE_DATE' INTEGER);"); // 16: updateDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BILLS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Bills entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMerchantId());
 
        String billReference = entity.getBillReference();
        if (billReference != null) {
            stmt.bindString(3, billReference);
        }
 
        String billType = entity.getBillType();
        if (billType != null) {
            stmt.bindString(4, billType);
        }
 
        java.util.Date billDate = entity.getBillDate();
        if (billDate != null) {
            stmt.bindLong(5, billDate.getTime());
        }
 
        java.util.Date billDueDate = entity.getBillDueDate();
        if (billDueDate != null) {
            stmt.bindLong(6, billDueDate.getTime());
        }
 
        Integer billAmount = entity.getBillAmount();
        if (billAmount != null) {
            stmt.bindLong(7, billAmount);
        }
 
        Integer payment = entity.getPayment();
        if (payment != null) {
            stmt.bindLong(8, payment);
        }
        stmt.bindLong(9, entity.getSupplierId());
 
        java.util.Date deliveryDate = entity.getDeliveryDate();
        if (deliveryDate != null) {
            stmt.bindLong(10, deliveryDate.getTime());
        }
 
        String remarks = entity.getRemarks();
        if (remarks != null) {
            stmt.bindString(11, remarks);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(12, status);
        }
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(13, uploadStatus);
        }
 
        String createBy = entity.getCreateBy();
        if (createBy != null) {
            stmt.bindString(14, createBy);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(15, createDate.getTime());
        }
 
        String updateBy = entity.getUpdateBy();
        if (updateBy != null) {
            stmt.bindString(16, updateBy);
        }
 
        java.util.Date updateDate = entity.getUpdateDate();
        if (updateDate != null) {
            stmt.bindLong(17, updateDate.getTime());
        }
    }

    @Override
    protected void attachEntity(Bills entity) {
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
    public Bills readEntity(Cursor cursor, int offset) {
        Bills entity = new Bills( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // billReference
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // billType
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // billDate
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)), // billDueDate
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // billAmount
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // payment
            cursor.getLong(offset + 8), // supplierId
            cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)), // deliveryDate
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // remarks
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // status
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // uploadStatus
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // createBy
            cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)), // createDate
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // updateBy
            cursor.isNull(offset + 16) ? null : new java.util.Date(cursor.getLong(offset + 16)) // updateDate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Bills entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setBillReference(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBillType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBillDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setBillDueDate(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setBillAmount(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setPayment(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setSupplierId(cursor.getLong(offset + 8));
        entity.setDeliveryDate(cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)));
        entity.setRemarks(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setStatus(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUploadStatus(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCreateBy(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCreateDate(cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)));
        entity.setUpdateBy(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setUpdateDate(cursor.isNull(offset + 16) ? null : new java.util.Date(cursor.getLong(offset + 16)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Bills entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Bills entity) {
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
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getSupplierDao().getAllColumns());
            builder.append(" FROM BILLS T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN SUPPLIER T1 ON T.'SUPPLIER_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Bills loadCurrentDeep(Cursor cursor, boolean lock) {
        Bills entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }
        offset += daoSession.getMerchantDao().getAllColumns().length;

        Supplier supplier = loadCurrentOther(daoSession.getSupplierDao(), cursor, offset);
         if(supplier != null) {
            entity.setSupplier(supplier);
        }

        return entity;    
    }

    public Bills loadDeep(Long key) {
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
    public List<Bills> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Bills> list = new ArrayList<Bills>(count);
        
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
    
    protected List<Bills> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Bills> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
