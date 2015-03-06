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

import com.android.pos.dao.Supplier;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SUPPLIER.
*/
public class SupplierDao extends AbstractDao<Supplier, Long> {

    public static final String TABLENAME = "SUPPLIER";

    /**
     * Properties of entity Supplier.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Telephone = new Property(3, String.class, "telephone", false, "TELEPHONE");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
        public final static Property PicName = new Property(5, String.class, "picName", false, "PIC_NAME");
        public final static Property PicTelephone = new Property(6, String.class, "picTelephone", false, "PIC_TELEPHONE");
        public final static Property Remarks = new Property(7, String.class, "remarks", false, "REMARKS");
        public final static Property Status = new Property(8, String.class, "status", false, "STATUS");
        public final static Property UploadStatus = new Property(9, String.class, "uploadStatus", false, "UPLOAD_STATUS");
        public final static Property CreateBy = new Property(10, String.class, "createBy", false, "CREATE_BY");
        public final static Property CreateDate = new Property(11, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property UpdateBy = new Property(12, String.class, "updateBy", false, "UPDATE_BY");
        public final static Property UpdateDate = new Property(13, java.util.Date.class, "updateDate", false, "UPDATE_DATE");
    };

    private DaoSession daoSession;


    public SupplierDao(DaoConfig config) {
        super(config);
    }
    
    public SupplierDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SUPPLIER' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'NAME' TEXT," + // 2: name
                "'TELEPHONE' TEXT," + // 3: telephone
                "'ADDRESS' TEXT," + // 4: address
                "'PIC_NAME' TEXT," + // 5: picName
                "'PIC_TELEPHONE' TEXT," + // 6: picTelephone
                "'REMARKS' TEXT," + // 7: remarks
                "'STATUS' TEXT," + // 8: status
                "'UPLOAD_STATUS' TEXT," + // 9: uploadStatus
                "'CREATE_BY' TEXT," + // 10: createBy
                "'CREATE_DATE' INTEGER," + // 11: createDate
                "'UPDATE_BY' TEXT," + // 12: updateBy
                "'UPDATE_DATE' INTEGER);"); // 13: updateDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SUPPLIER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Supplier entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMerchantId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(4, telephone);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String picName = entity.getPicName();
        if (picName != null) {
            stmt.bindString(6, picName);
        }
 
        String picTelephone = entity.getPicTelephone();
        if (picTelephone != null) {
            stmt.bindString(7, picTelephone);
        }
 
        String remarks = entity.getRemarks();
        if (remarks != null) {
            stmt.bindString(8, remarks);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(9, status);
        }
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(10, uploadStatus);
        }
 
        String createBy = entity.getCreateBy();
        if (createBy != null) {
            stmt.bindString(11, createBy);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(12, createDate.getTime());
        }
 
        String updateBy = entity.getUpdateBy();
        if (updateBy != null) {
            stmt.bindString(13, updateBy);
        }
 
        java.util.Date updateDate = entity.getUpdateDate();
        if (updateDate != null) {
            stmt.bindLong(14, updateDate.getTime());
        }
    }

    @Override
    protected void attachEntity(Supplier entity) {
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
    public Supplier readEntity(Cursor cursor, int offset) {
        Supplier entity = new Supplier( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // telephone
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // picName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // picTelephone
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // remarks
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // status
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // uploadStatus
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // createBy
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)), // createDate
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // updateBy
            cursor.isNull(offset + 13) ? null : new java.util.Date(cursor.getLong(offset + 13)) // updateDate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Supplier entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTelephone(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPicName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPicTelephone(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRemarks(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setStatus(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUploadStatus(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCreateBy(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCreateDate(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
        entity.setUpdateBy(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUpdateDate(cursor.isNull(offset + 13) ? null : new java.util.Date(cursor.getLong(offset + 13)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Supplier entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Supplier entity) {
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
            builder.append(" FROM SUPPLIER T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Supplier loadCurrentDeep(Cursor cursor, boolean lock) {
        Supplier entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }

        return entity;    
    }

    public Supplier loadDeep(Long key) {
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
    public List<Supplier> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Supplier> list = new ArrayList<Supplier>(count);
        
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
    
    protected List<Supplier> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Supplier> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
