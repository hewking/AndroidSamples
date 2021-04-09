package com.hewking.develop.db.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HomePageData".
*/
public class HomePageDataTableDao extends AbstractDao<HomePageDataTable, Long> {

    public static final String TABLENAME = "HomePageData";

    /**
     * Properties of entity HomePageDataTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "Id");
        public final static Property RequestKey = new Property(1, String.class, "requestKey", false, "requestKey");
        public final static Property DataJson = new Property(2, String.class, "dataJson", false, "dataJson");
    }


    public HomePageDataTableDao(DaoConfig config) {
        super(config);
    }
    
    public HomePageDataTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HomePageData\" (" + //
                "\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"requestKey\" TEXT," + // 1: requestKey
                "\"dataJson\" TEXT);"); // 2: dataJson
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HomePageData\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HomePageDataTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String requestKey = entity.getRequestKey();
        if (requestKey != null) {
            stmt.bindString(2, requestKey);
        }
 
        String dataJson = entity.getDataJson();
        if (dataJson != null) {
            stmt.bindString(3, dataJson);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HomePageDataTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String requestKey = entity.getRequestKey();
        if (requestKey != null) {
            stmt.bindString(2, requestKey);
        }
 
        String dataJson = entity.getDataJson();
        if (dataJson != null) {
            stmt.bindString(3, dataJson);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public HomePageDataTable readEntity(Cursor cursor, int offset) {
        HomePageDataTable entity = new HomePageDataTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // requestKey
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // dataJson
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HomePageDataTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRequestKey(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDataJson(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HomePageDataTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HomePageDataTable entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HomePageDataTable entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
