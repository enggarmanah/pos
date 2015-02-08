package com.android.pos.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.android.pos.dao.DaoMaster;
import com.android.pos.dao.DaoSession;
import com.android.pos.dao.DiscountDao;
import com.android.pos.dao.TransactionsDao;
import com.android.pos.dao.DaoMaster.DevOpenHelper;
import com.android.pos.dao.ProductGroupDao;
import com.android.pos.dao.TransactionItemDao;

public class DbUtil {
	
	private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static Context context;
    
    public static class DbOpenHelper extends DaoMaster.DevOpenHelper {
    	
    	public DbOpenHelper(Context context, String name, CursorFactory factory) {
            
    		super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            
        	Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            
        	// handle version 2 changes
            if (oldVersion < 2) {
            	db.execSQL("ALTER TABLE 'PRODUCT' ADD 'PIC_REQUIRED' TEXT");
            	db.execSQL("ALTER TABLE 'PRODUCT' ADD 'COMMISION' INTEGER");
            }
            
            // handle version 3 changes
            if (oldVersion < 3) {
            	db.execSQL("ALTER TABLE 'USER' ADD 'ROLE' TEXT");
            }
            
            // handle version 4 changes
            if (oldVersion < 4) {
            	db.execSQL("ALTER TABLE 'PRODUCT' ADD 'PRODUCT_GROUP_ID' INTEGER");
            	ProductGroupDao.createTable(db, true);
            }
            
            // handle version 5 changes
            if (oldVersion < 5) {
            	TransactionItemDao.dropTable(db, true);
            	TransactionItemDao.createTable(db, true);
            }
            
            // handle version 6 changes
            if (oldVersion < 6) {
            	db.execSQL("ALTER TABLE 'MERCHANT' ADD 'TAX_PERCENTAGE' INTEGER");
            	db.execSQL("ALTER TABLE 'MERCHANT' ADD 'SERVICE_CHARGE_PERCENTAGE' INTEGER");
            	
            	TransactionsDao.dropTable(db, true);
            	TransactionsDao.createTable(db, true);
            	DiscountDao.createTable(db, true);
            }
            
            // handle version 7 changes
            if (oldVersion < 7) {
            	TransactionsDao.dropTable(db, true);
            	TransactionsDao.createTable(db, true);
            }
            
            // handle version 8 changes
            if (oldVersion < 8) {
            	TransactionItemDao.dropTable(db, true);
            	TransactionItemDao.createTable(db, true);
            	TransactionsDao.dropTable(db, true);
            	TransactionsDao.createTable(db, true);
            }
            
            // handle version 9 changes
            if (oldVersion < 9) {
            	db.execSQL("ALTER TABLE 'PRODUCT_GROUP' ADD 'MERCHANT_ID' INTEGER");
            	db.execSQL("ALTER TABLE 'DISCOUNT' ADD 'MERCHANT_ID' INTEGER");
            }
            
            // handle version 10 changes
            if (oldVersion < 10) {
            	db.execSQL("ALTER TABLE 'PRODUCT_GROUP' ADD 'STATUS' TEXT");
            }
            
            // handle version 11 changes
            if (oldVersion < 11) {
            	db.execSQL("ALTER TABLE 'TRANSACTIONS' ADD 'STATUS' TEXT");
            }
            
            // handle version 12 changes
            if (oldVersion < 12) {
            	db.execSQL("ALTER TABLE 'TRANSACTIONS' ADD 'UPLOAD_STATUS' TEXT");
            }
            
            // handle version 12 changes
            if (oldVersion < 13) {
            	db.execSQL("ALTER TABLE 'TRANSACTION_ITEM' ADD 'MERCHANT_ID' INTEGER");
            	db.execSQL("ALTER TABLE 'TRANSACTION_ITEM' ADD 'UPLOAD_STATUS' TEXT");
            }

            //DaoMaster.dropAllTables(db, true);
            //onCreate(db);
        }
    }
    
    public static void initDb(Context ctx) {
    	
    	context = ctx;
    	
    	if (daoSession == null) {
    		
    		DevOpenHelper helper = new DbOpenHelper(context, "pos-db", null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
    	}
    }
    
    public static DaoSession getSession() {
    	
    	if (daoSession == null) {
    		initDb(context);
    	}
    	
    	return daoSession;
    }
    
    public static SQLiteDatabase getDb() {
    	
    	return db;
    }
}
