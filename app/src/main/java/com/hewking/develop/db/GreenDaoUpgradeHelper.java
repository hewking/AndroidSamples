package com.hewking.develop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.hewking.develop.BuildConfig;
import com.hewking.develop.db.table.DaoMaster;
import com.hewking.develop.db.table.HomePageDataTable;
import com.hewking.develop.db.table.HomePageDataTableDao;
import com.hewking.develop.db.table.MessageTable;
import com.hewking.develop.db.table.MessageTableDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import io.reactivex.rxjava3.core.Observable;


/**
 * @author: 钟志勇
 * @create: 2019/10/11 21:53
 * @describe:
 */
public class GreenDaoUpgradeHelper extends DaoMaster.DevOpenHelper {

  public static final String SCHEMA_VERSION = "schema_version";

  //必须与gradle中greendao版本号一致,目前手动填写,后续从gradle中获取
  private static final int CURRENT_VERSION = BuildConfig.schema_version;

  public GreenDaoUpgradeHelper(Context context, String name) {
    super(context, name);
  }

  public GreenDaoUpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
    super(context, name, factory);
  }

  @SafeVarargs
  private final void upgradeMainThread(SQLiteDatabase db,
      Class<? extends AbstractDao<?, ?>>... daoClasses) {
    long last = System.currentTimeMillis();
    new GreenDaoCompatibleUpdateHelper()
        .setCallBack(new GreenDaoCompatibleUpdateHelper.GreenDaoCompatibleUpdateCallBack() {
                       @Override
                       public void onFinalSuccess() {
                           PreferenceTable.INSTANCE.setSchemaVersion(CURRENT_VERSION);

                           Log.d("upgradeThread","GreenDaoUpgradeHelper进行数据库升级 ===> 成功" + "|耗时:" + (System.currentTimeMillis()
                             - last) + "ms");
                       }

                       @Override
                       public void onFailedLog(String errorMsg) {
                           Log.d("upgradeThread","GreenDaoUpgradeHelper升级失败日志 ===> " + errorMsg);
                       }
                     }
        ).compatibleUpdate(db, daoClasses);
  }

  @SafeVarargs
  private final void upgradeThread(SQLiteDatabase db,
                                                 Class<? extends AbstractDao<?, ?>>... daoClasses) {
    long last = System.currentTimeMillis();
    new SqlUtil().setCallBack(new SqlUtil.GreenDaoUpgradeCallBack() {
                       @Override
                       public void onUpgradeSuccess() {
                         PreferenceTable.INSTANCE.setSchemaVersion(CURRENT_VERSION);

                           Log.d("upgradeThread","进行数据库升级 ===> 成功" + "|耗时:" + (System.currentTimeMillis() - last) + "ms");

//                         subscriber
//                             .onNext("进行数据库升级 ===> 成功" + "|耗时:" + (System.currentTimeMillis() - last) + "ms");
//                         subscriber.onCompleted();
                       }

                       @Override
                       public void onUpgradeFail(String errorMsg) {
//                         subscriber.onNext("升级失败日志 ===> " + errorMsg);
//                         subscriber.onCompleted();
                           Log.d("upgradeThread","onUpgradeFail 升级失败日志 ===> " + errorMsg);
                       }
                     }
        ).upgradeTable(db, daoClasses);
  }

  /**
   * 如果不需要迁移原有数据，比如新建表，可以在这里处理直接创建 如果要保留老数据，则需要做数据迁移则调用数据迁移的方法
   */
  @Override
  public void onUpgrade(Database db, int oldVersion, int newVersion) {
    // 不要调用父类的，它默认是先删除全部表再创建
    // super.onUpgrade(db, oldVersion, newVersion);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    super.onUpgrade(db, oldVersion, newVersion);

    upgradeMainThread(db, MessageTableDao.class, HomePageDataTableDao.class);
  }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}