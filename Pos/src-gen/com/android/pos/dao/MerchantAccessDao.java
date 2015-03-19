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

import com.android.pos.dao.MerchantAccess;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MERCHANT_ACCESS.
*/
public class MerchantAccessDao extends AbstractDao<MerchantAccess, Long> {

    public static final String TABLENAME = "MERCHANT_ACCESS";

    /**
     * Properties of entity MerchantAccess.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Code = new Property(3, String.class, "code", false, "CODE");
        public final static Property Status = new Property(4, String.class, "status", false, "STATUS");
        public final static Property UploadStatus = new Property(5, String.class, "uploadStatus", false, "UPLOAD_STATUS");
        public final static Property CreateBy = new Property(6, String.class, "createBy", false, "CREATE_BY");
        public final static Property CreateDate = new Property(7, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property UpdateBy = new Property(8, String.class, "updateBy", false, "UPDATE_BY");
        public final static Property UpdateDate = new Property(9, java.util.Date.class, "updateDate", false, "UPDATE_DATE");
    };

    private DaoSession daoSession;

    private Query<MerchantAccess> merchant_MerchantAccessListQuery;

    public MerchantAccessDao(DaoConfig config) {
        super(config);
    }
    
    public MerchantAccessDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MERCHANT_ACCESS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'NAME' TEXT," + // 2: name
                "'CODE' TEXT," + // 3: code
                "'STATUS' TEXT," + // 4: status
                "'UPLOAD_STATUS' TEXT," + // 5: uploadStatus
                "'CREATE_BY' TEXT," + // 6: createBy
                "'CREATE_DATE' INTEGER," + // 7: createDate
                "'UPDATE_BY' TEXT," + // 8: updateBy
                "'UPDATE_DATE' INTEGER);"); // 9: updateDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MERCHANT_ACCESS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MerchantAccess entity) {
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
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(4, code);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(5, status);
        }
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(6, uploadStatus);
        }
 
        String createBy = entity.getCreateBy();
        if (createBy != null) {
            stmt.bindString(7, createBy);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(8, createDate.getTime());
        }
 
        String updateBy = entity.getUpdateBy();
        if (updateBy != null) {
            stmt.bindString(9, updateBy);
        }
 
        java.util.Date updateDate = entity.getUpdateDate();
        if (updateDate != null) {
            stmt.bindLong(10, updateDate.getTime());
        }
    }

    @Override
    protected void attachEntity(MerchantAccess entity) {
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
    public MerchantAccess readEntity(Cursor cursor, int offset) {
        MerchantAccess entity = new MerchantAccess( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // code
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // status
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // uploadStatus
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // createBy
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // createDate
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // updateBy
            cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)) // updateDate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MerchantAccess entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStatus(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUploadStatus(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreateBy(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCreateDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setUpdateBy(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUpdateDate(cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MerchantAccess entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MerchantAccess entity) {
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
    
    /** Internal query to resolve the "merchantAccessList" to-many relationship of Merchant. */
    public List<MerchantAccess> _queryMerchant_MerchantAccessList(long merchantId) {
        synchronized (this) {
            if (merchant_MerchantAccessListQuery == null) {
                QueryBuilder<MerchantAccess> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MerchantId.eq(null));
                queryBuilder.orderRaw("_id ASC");
                merchant_MerchantAccessListQuery = queryBuilder.build();
            }
        }
        Query<MerchantAccess> query = merchant_MerchantAccessListQuery.forCurrentThread();
        query.setParameter(0, merchantId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMerchantDao().getAllColumns());
            builder.append(" FROM MERCHANT_ACCESS T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected MerchantAccess loadCurrentDeep(Cursor cursor, boolean lock) {
        MerchantAccess entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }

        return entity;    
    }

    public MerchantAccess loadDeep(Long key) {
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
    public List<MerchantAccess> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<MerchantAccess> list = new ArrayList<MerchantAccess>(count);
        
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
    
    protected List<MerchantAccess> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<MerchantAccess> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
