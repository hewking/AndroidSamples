package com.hewking.develop.db;

import android.database.sqlite.SQLiteDatabase;

import com.hewking.develop.db.table.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

/**
 * @author: 钟志勇
 * @create: 2019/10/12 11:17
 * @describe:
 */
public class BaseDaoMaster extends DaoMaster {

  public BaseDaoMaster(SQLiteDatabase db) {
    super(db);
  }

  public Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> getDaoMap() {
    return daoConfigMap;
  }

}
