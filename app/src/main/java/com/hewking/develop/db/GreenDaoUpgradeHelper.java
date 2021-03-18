package com.hewking.develop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.hewking.develop.db.table.DaoMaster;

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
  private static final int CURRENT_VERSION = 9;

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
//                         LogUtil.e("GreenDaoUpgradeHelper进行数据库升级 ===> 成功" + "|耗时:" + (System.currentTimeMillis()
//                             - last) + "ms");
                       }

                       @Override
                       public void onFailedLog(String errorMsg) {
//                         LogUtil.e("GreenDaoUpgradeHelper升级失败日志 ===> " + errorMsg);
                       }
                     }
        ).compatibleUpdate(db, daoClasses);
  }

//  @SafeVarargs
//  private final Observable<String> upgradeThread(SQLiteDatabase db,
//                                                 Class<? extends AbstractDao<?, ?>>... daoClasses) {
//    long last = System.currentTimeMillis();
//    return RxExtensions.createWithSchedulers(subscriber -> new SqlUtil()
//        .setCallBack(new SqlUtil.GreenDaoUpgradeCallBack() {
//                       @Override
//                       public void onUpgradeSuccess() {
//                         MMKVUtil.setValue(Global.applicationContext, SCHEMA_VERSION, CURRENT_VERSION);
//
//                         subscriber
//                             .onNext("进行数据库升级 ===> 成功" + "|耗时:" + (System.currentTimeMillis() - last) + "ms");
//                         subscriber.onCompleted();
//                       }
//
//                       @Override
//                       public void onUpgradeFail(String errorMsg) {
//                         subscriber.onNext("升级失败日志 ===> " + errorMsg);
//                         subscriber.onCompleted();
//                       }
//                     }
//        ).upgradeTable(db, daoClasses));
//  }

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

//    upgradeThread(db, AroundThingsHomeTableDao.class, ExpressSearchTableDao.class,
//        FullAdvertTableDao.class, HomePageCloseTableDao.class, HomePageDataTableDao.class,
//        PostAndReceivedManTableDao.class, TopicTableDao.class)
//        .subscribe( LogUtil::e, Throwable::printStackTrace);
  }
}