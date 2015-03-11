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

import com.android.pos.dao.Inventory;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table INVENTORY.
*/
public class InventoryDao extends AbstractDao<Inventory, Long> {

    public static final String TABLENAME = "INVENTORY";

    /**
     * Properties of entity Inventory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MerchantId = new Property(1, long.class, "merchantId", false, "MERCHANT_ID");
        public final static Property ProductId = new Property(2, long.class, "productId", false, "PRODUCT_ID");
        public final static Property ProductName = new Property(3, String.class, "productName", false, "PRODUCT_NAME");
        public final static Property QuantityStr = new Property(4, String.class, "quantityStr", false, "QUANTITY_STR");
        public final static Property Quantity = new Property(5, Integer.class, "quantity", false, "QUANTITY");
        public final static Property BillsId = new Property(6, Long.class, "billsId", false, "BILLS_ID");
        public final static Property BillsReferenceNo = new Property(7, String.class, "billsReferenceNo", false, "BILLS_REFERENCE_NO");
        public final static Property SupplierId = new Property(8, Long.class, "supplierId", false, "SUPPLIER_ID");
        public final static Property SupplierName = new Property(9, String.class, "supplierName", false, "SUPPLIER_NAME");
        public final static Property DeliveryDate = new Property(10, java.util.Date.class, "deliveryDate", false, "DELIVERY_DATE");
        public final static Property Remarks = new Property(11, String.class, "remarks", false, "REMARKS");
        public final static Property Status = new Property(12, String.class, "status", false, "STATUS");
        public final static Property UploadStatus = new Property(13, String.class, "uploadStatus", false, "UPLOAD_STATUS");
        public final static Property CreateBy = new Property(14, String.class, "createBy", false, "CREATE_BY");
        public final static Property CreateDate = new Property(15, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property UpdateBy = new Property(16, String.class, "updateBy", false, "UPDATE_BY");
        public final static Property UpdateDate = new Property(17, java.util.Date.class, "updateDate", false, "UPDATE_DATE");
    };

    private DaoSession daoSession;


    public InventoryDao(DaoConfig config) {
        super(config);
    }
    
    public InventoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'INVENTORY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MERCHANT_ID' INTEGER NOT NULL ," + // 1: merchantId
                "'PRODUCT_ID' INTEGER NOT NULL ," + // 2: productId
                "'PRODUCT_NAME' TEXT," + // 3: productName
                "'QUANTITY_STR' TEXT," + // 4: quantityStr
                "'QUANTITY' INTEGER," + // 5: quantity
                "'BILLS_ID' INTEGER," + // 6: billsId
                "'BILLS_REFERENCE_NO' TEXT," + // 7: billsReferenceNo
                "'SUPPLIER_ID' INTEGER," + // 8: supplierId
                "'SUPPLIER_NAME' TEXT," + // 9: supplierName
                "'DELIVERY_DATE' INTEGER," + // 10: deliveryDate
                "'REMARKS' TEXT," + // 11: remarks
                "'STATUS' TEXT," + // 12: status
                "'UPLOAD_STATUS' TEXT," + // 13: uploadStatus
                "'CREATE_BY' TEXT," + // 14: createBy
                "'CREATE_DATE' INTEGER," + // 15: createDate
                "'UPDATE_BY' TEXT," + // 16: updateBy
                "'UPDATE_DATE' INTEGER);"); // 17: updateDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'INVENTORY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Inventory entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMerchantId());
        stmt.bindLong(3, entity.getProductId());
 
        String productName = entity.getProductName();
        if (productName != null) {
            stmt.bindString(4, productName);
        }
 
        String quantityStr = entity.getQuantityStr();
        if (quantityStr != null) {
            stmt.bindString(5, quantityStr);
        }
 
        Integer quantity = entity.getQuantity();
        if (quantity != null) {
            stmt.bindLong(6, quantity);
        }
 
        Long billsId = entity.getBillsId();
        if (billsId != null) {
            stmt.bindLong(7, billsId);
        }
 
        String billsReferenceNo = entity.getBillsReferenceNo();
        if (billsReferenceNo != null) {
            stmt.bindString(8, billsReferenceNo);
        }
 
        Long supplierId = entity.getSupplierId();
        if (supplierId != null) {
            stmt.bindLong(9, supplierId);
        }
 
        String supplierName = entity.getSupplierName();
        if (supplierName != null) {
            stmt.bindString(10, supplierName);
        }
 
        java.util.Date deliveryDate = entity.getDeliveryDate();
        if (deliveryDate != null) {
            stmt.bindLong(11, deliveryDate.getTime());
        }
 
        String remarks = entity.getRemarks();
        if (remarks != null) {
            stmt.bindString(12, remarks);
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
    protected void attachEntity(Inventory entity) {
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
    public Inventory readEntity(Cursor cursor, int offset) {
        Inventory entity = new Inventory( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // merchantId
            cursor.getLong(offset + 2), // productId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // productName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // quantityStr
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // quantity
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // billsId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // billsReferenceNo
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // supplierId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // supplierName
            cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)), // deliveryDate
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // remarks
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
    public void readEntity(Cursor cursor, Inventory entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMerchantId(cursor.getLong(offset + 1));
        entity.setProductId(cursor.getLong(offset + 2));
        entity.setProductName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setQuantityStr(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setQuantity(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setBillsId(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setBillsReferenceNo(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSupplierId(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setSupplierName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDeliveryDate(cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)));
        entity.setRemarks(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setStatus(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUploadStatus(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCreateBy(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCreateDate(cursor.isNull(offset + 15) ? null : new java.util.Date(cursor.getLong(offset + 15)));
        entity.setUpdateBy(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setUpdateDate(cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Inventory entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Inventory entity) {
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
            SqlUtils.appendColumns(builder, "T1", daoSession.getProductDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getBillsDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getSupplierDao().getAllColumns());
            builder.append(" FROM INVENTORY T");
            builder.append(" LEFT JOIN MERCHANT T0 ON T.'MERCHANT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN PRODUCT T1 ON T.'PRODUCT_ID'=T1.'_id'");
            builder.append(" LEFT JOIN BILLS T2 ON T.'BILLS_ID'=T2.'_id'");
            builder.append(" LEFT JOIN SUPPLIER T3 ON T.'SUPPLIER_ID'=T3.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Inventory loadCurrentDeep(Cursor cursor, boolean lock) {
        Inventory entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Merchant merchant = loadCurrentOther(daoSession.getMerchantDao(), cursor, offset);
         if(merchant != null) {
            entity.setMerchant(merchant);
        }
        offset += daoSession.getMerchantDao().getAllColumns().length;

        Product product = loadCurrentOther(daoSession.getProductDao(), cursor, offset);
         if(product != null) {
            entity.setProduct(product);
        }
        offset += daoSession.getProductDao().getAllColumns().length;

        Bills bills = loadCurrentOther(daoSession.getBillsDao(), cursor, offset);
        entity.setBills(bills);
        offset += daoSession.getBillsDao().getAllColumns().length;

        Supplier supplier = loadCurrentOther(daoSession.getSupplierDao(), cursor, offset);
        entity.setSupplier(supplier);

        return entity;    
    }

    public Inventory loadDeep(Long key) {
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
    public List<Inventory> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Inventory> list = new ArrayList<Inventory>(count);
        
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
    
    protected List<Inventory> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Inventory> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
