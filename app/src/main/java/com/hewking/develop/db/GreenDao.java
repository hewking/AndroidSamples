package com.hewking.develop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hewking.develop.db.table.DaoSession;

/**
 * @author: zgkxzx @ 002001
 * @created: 2019/5/17 14:41
 * @description:
 */
public class GreenDao {

  private final static String DEFAULT_DB_NAME = "developer.db";

  private static Object monitor = new Object();
  private static volatile GreenDao mInstance;
  private DaoSession daoSession;

  private GreenDao() {

  }

  public static GreenDao get() {
    if (mInstance == null) {
      synchronized (monitor) {
        if (mInstance == null) {
          mInstance = new GreenDao();
        }
      }
    }
    return mInstance;
  }

  /**
   * 初始化数据库
   *
   * @param context 上下文
   */
  public void init(Context context) {
    init(context, DEFAULT_DB_NAME);
  }

  /**
   * 初始化数据库
   *
   * @param context 上下文
   * @param dbName 数据库名
   */
  public void init(Context context, String dbName) {
    Context applicationContext = context.getApplicationContext();
    initDb(applicationContext, dbName);
  }

  /**
   * 配置数据库
   */
  private void initDb(Context context, String dbName) {
    GreenDaoUpgradeHelper helper = new GreenDaoUpgradeHelper(context, dbName, null);
    SQLiteDatabase db = helper.getWritableDatabase();
    BaseDaoMaster daoMaster = new BaseDaoMaster(db);
    daoSession = daoMaster.newSession();

    int newVersion = daoMaster.getSchemaVersion();
    int oldVersion = PreferenceTable.INSTANCE.getSchemaVersion();

    Log.e("GreenDao","newVersion ===> " + newVersion + " , oldVersion ===> " + oldVersion);

    if (oldVersion < newVersion) {
      helper.onUpgrade(db, oldVersion, newVersion);
    }
  }

  public DaoSession getDaoSession() {
    return daoSession;
  }
}
