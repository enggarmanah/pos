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

import com.android.pos.dao.Product;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PRODUCT.
*/
public class ProductDao extends AbstractDao<Product, Long> {

    public static final String TABLENAME = "PRODUCT";

    /**
     * Properties of entity Product.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property ProductGroupId = new Property(2, Long.class, "productGroupId", false, "PRODUCT_GROUP_ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
        public final static Property Price = new Property(5, Integer.class, "price", false, "PRICE");
        public final static Property CostPrice = new Property(6, Integer.class, "costPrice", false, "COST_PRICE");
        public final static Property PicRequired = new Property(7, String.class, "picRequired", false, "PIC_REQUIRED");
        public final static Property Commision = new Property(8, Integer.class, "commision", false, "COMMISION");
        public final static Property PromoPrice = new Property(9, Integer.class, "promoPrice", false, "PROMO_PRICE");
        public final static Property PromoStart = new Property(10, java.util.Date.class, "promoStart", false, "PROMO_START");
        public final static Property PromoEnd = new Property(11, java.util.Date.class, "promoEnd", false, "PROMO_END");
        public final static Property Status = new Property(12, String.class, "status", false, "STATUS");
        public final static Property UploadStatus = new Property(13, String.class, "uploadStatus", false, "UPLOAD_STATUS");
        public final static Property CreateBy = new Property(14, String.class, "createBy", false, "CREATE_BY");
        public final static Property CreateDate = new Property(15, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property UpdateBy = new Property(16, String.class, "updateBy", false, "UPDATE_BY");
        public final static Property UpdateDate = new Property(17, java.util.Date.class, "updateDate", false, "UPDATE_DATE");
    };

    private DaoSession daoSession;

    private Query<Product> merchant_ProductListQuery;
    private Query<Product> productGroup_ProductListQuery;

    public ProductDao(DaoConfig config) {
        super(config);
    }
    
    public ProductDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PRODUCT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'PRODUCT_GROUP_ID' INTEGER," + // 2: productGroupId
                "'NAME' TEXT," + // 3: name
                "'TYPE' TEXT," + // 4: type
                "'PRICE' INTEGER," + // 5: price
                "'COST_PRICE' INTEGER," + // 6: costPrice
                "'PIC_REQUIRED' TEXT," + // 7: picRequired
                "'COMMISION' INTEGER," + // 8: commision
                "'PROMO_PRICE' INTEGER," + // 9: promoPrice
                "'PROMO_START' INTEGER," + // 10: promoStart
                "'PROMO_END' INTEGER," + // 11: promoEnd
                "'STATUS' TEXT," + // 12: status
                "'UPLOAD_STATUS' TEXT," + // 13: uploadStatus
                "'CREATE_BY' TEXT," + // 14: createBy
                "'CREATE_DATE' INTEGER," + // 15: createDate
                "'UPDATE_BY' TEXT," + // 16: updateBy
                "'UPDATE_DATE' INTEGER);"); // 17: updateDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PRODUCT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Product entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMerchantId());
 
        Long productGroupId = entity.getProductGroupId();
        if (productGroupId != null) {
            stmt.bindLong(3, productGroupId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
 
        Integer price = entity.getPrice();
        if (price != null) {
            stmt.bindLong(6, price);
        }
 
        Integer costPrice = entity.getCostPrice();
        if (costPrice != null) {
            stmt.bindLong(7, costPrice);
        }
 
        String picRequired = entity.getPicRequired();
        if (picRequired != null) {
            stmt.bindString(8, picRequired);
        }
 
        Integer commision = entity.getCommision();
        if (commision != null) {
            stmt.bindLong(9, commision);
        }
 
        Integer promoPrice = entity.getPromoPrice();
        if (promoPrice != null) {
            stmt.bindLong(10, promoPrice);
        }
 
        java.util.Date promoStart = entity.getPromoStart();
        if (promoStart != null) {
            stmt.bindLong(11, promoStart.getTime());
        }
 
        java.util.Date promoEnd = entity.getPromoEnd();
        if (promoEnd != null) {
            stmt.bindLong(12, promoEnd.getTime());
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(13, status);
        }
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(14, uploadStatus);
        }
 
        String createBy = entity.getCreateBy();
        if (createBy != null) {
            stmt.bindString(15, createBy);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(16, createDate.getTime());
        }
 
        String updateBy = entity.getUpdateBy();
        if (updateBy != null) {
            stmt.bindString(17, updateBy);
        }
 
        java.util.Date updateDate = entity.getUpdateDate();
        if (updateDate != null) {
            stmt.bindLong(18, updateDate.getTime());
        }
    }

    @Override
    protected void attachEntity(Product entity) {
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
    public Product readEntity(Cursor cursor, int offset) {
        Product entity = new Product( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // productGroupId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // type
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // price
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // costPrice
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // picRequired
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // commision
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // promoPrice
            cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)), // promoStart
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)), // promoEnd
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // status
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // uploadStatus
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // createBy
            cursor.isNull(offset + 15) ? null : new java.util.Date(cursor.getLong(offset + 15)), // createDate
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // updateBy
            cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)) // updateDate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Product entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setProductGroupId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPrice(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setCostPrice(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setPicRequired(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCommision(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setPromoPrice(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setPromoStart(cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)));
        entity.setPromoEnd(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
        entity.setStatus(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUploadStatus(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCreateBy(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCreateDate(cursor.isNull(offset + 15) ? null : new java.util.Date(cursor.getLong(offset + 15)));
        entity.setUpdateBy(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setUpdateDate(cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Product entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Product entity) {
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
    
    /** Internal query to resolve the "productList" to-many relationship of Merchant. */
    public List<Product> _queryMerchant_ProductList(long merchantId) {
        synchronized (this) {
            if (merchant_ProductListQuery == null) {
                QueryBuilder<Product> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MerchantId.eq(null));
                queryBuilder.orderRaw("NAME ASC");
                merchant_ProductListQuery = queryBuilder.build();
            }
        }
        Query<Product> query = merchant_ProductListQuery.forCurrentThread();
        query.setParameter(0, merchantId);
        return query.list();
    }

    /** Internal query to resolve the "productList" to-many relationship of ProductGroup. */
    public List<Product> _queryProductGroup_ProductList(Long productGroupId) {
        synchronized (this) {
            if (productGroup_ProductListQuery == null) {
                QueryBuilder<Product> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.ProductGroupId.eq(null));
                queryBuilder.orderRaw("NAME ASC");
                productGroup_ProductListQuery = queryBuilder.build();
            }
        }
        Query<Product> query = productGroup_ProductListQuery.forCurrentThread();
        query.setParameter(0, productGroupId);
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
            SqlUtils.appendColumns(builder, "T1", daoSession.getProductGroupDao().getAllColumns());
            builder.append(" FROM PRODUCT T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN PRODUCT_GROUP T1 ON T.'PRODUCT_GROUP_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Product loadCurrentDeep(Cursor cursor, boolean lock) {
        Product entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }
        offset += daoSession.getMerchantDao().getAllColumns().length;

        ProductGroup productGroup = loadCurrentOther(daoSession.getProductGroupDao(), cursor, offset);
        entity.setProductGroup(productGroup);

        return entity;    
    }

    public Product loadDeep(Long key) {
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
    public List<Product> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Product> list = new ArrayList<Product>(count);
        
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
    
    protected List<Product> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Product> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
