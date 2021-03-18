package com.hewking.develop.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @创建者 003099
 * @创建时间 2019/12/20 11:01
 * @描述 GreenDaoCompatibleUpdateHelper与SqlUtil对比：1.减少中间表创建及迁移操作 2.减少创建表及删除表操作
 */
public class SqlUtil {

  private static final String SQLITE_MASTER = "sqlite_master";

  public interface GreenDaoUpgradeCallBack {

    void onUpgradeSuccess();

    void onUpgradeFail(String errorMsg);
  }

  private static GreenDaoUpgradeCallBack callBack;

  public SqlUtil setCallBack(GreenDaoUpgradeCallBack callBack1) {
    callBack = callBack1;
    return this;
  }

  @SafeVarargs
  public final void upgradeTable(SQLiteDatabase database,
      Class<? extends AbstractDao<?, ?>>... daoClasses) {
    try {

      if (daoClasses == null && callBack != null) {
        callBack.onUpgradeFail("daoClasses is null");
        callBack = null;
        return;
      }

      StandardDatabase db = new StandardDatabase(database);

      restoreDataFromTempTableToNewTable(db,
          daoClasses);     //创建新表temp,旧表的数据迁移到新表temp,删除旧表,rename新表temp为旧表名

      createNewTablesIfNotExists(db, daoClasses);     //创建之前旧表中不存在的新表

      //dropOldTablesIfExists(db, daoClasses);      //删除旧表中不需要的表(daoClasses需传入当前所有表)

      if (callBack != null) {
        callBack.onUpgradeSuccess();
      }
      callBack = null;

    } catch (Exception e) {
      e.printStackTrace();
      if (callBack != null) {
        callBack.onUpgradeFail("upgradeOldTable ===> " + e.toString());
      }
    }
  }

  @SafeVarargs
  private final void restoreDataFromTempTableToNewTable(StandardDatabase db,
      Class<? extends AbstractDao<?, ?>>... daoClasses) {
    for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {

      DaoConfig daoConfig = new DaoConfig(db, daoClass);

      if (!isTableExists(db, daoConfig.tablename)) {
        continue;
      }

      createTempTable(db, daoConfig);

      restoreData(db, daoConfig);

    }
  }

  private void createTempTable(StandardDatabase db, DaoConfig daoConfig) {
    try {
      db.beginTransaction();

      String tempTableName = daoConfig.tablename.concat("_TEMP");

      db.execSQL("DROP TABLE IF EXISTS " + tempTableName);

      String divider = "";

      StringBuilder createTable = new StringBuilder();

      createTable.append("CREATE TABLE ").append(tempTableName).append(" (");

      for (int i = 0; i < daoConfig.properties.length; i++) {

        String columnName = daoConfig.properties[i].columnName;

        if (daoConfig.properties[i].primaryKey) {
          createTable.append(divider).append(columnName)
              .append(" INTEGER PRIMARY KEY AUTOINCREMENT ");
        } else {
          createTable.append(divider).append(columnName)
              .append(getTableType(daoConfig.properties[i].type));
        }

        divider = ",";

      }

      createTable.append(");");

      db.execSQL(createTable.toString());

      db.setTransactionSuccessful();
    } catch (Exception e) {
      e.printStackTrace();
      if (callBack != null) {
        callBack.onUpgradeFail("createTempTable ===> " + e.toString());
      }
    } finally {
      db.endTransaction();
    }

  }

  private void restoreData(StandardDatabase db, DaoConfig daoConfig) {
    try {
      db.beginTransaction();

      String tableName = daoConfig.tablename;

      String tempTableName = tableName.concat("_TEMP");

      List<String> oldColumns = getColumns(db, tableName);

      List<String> properties = new ArrayList<>();

      for (int i = 0; i < daoConfig.properties.length; i++) {

        String columnName = daoConfig.properties[i].columnName;

        if (oldColumns.contains(columnName)) {

          properties.add(columnName);

        }
      }

      StringBuilder insertTable = new StringBuilder();

      insertTable.append("INSERT INTO ").append(tempTableName).append(" (");
      insertTable.append(TextUtils.join(",", properties));
      insertTable.append(") SELECT ");
      insertTable.append(TextUtils.join(",", properties));
      insertTable.append(" FROM ").append(tableName).append(";");

      StringBuilder dropTable = new StringBuilder();
      dropTable.append("DROP TABLE ").append(tableName);

      StringBuilder renameTable = new StringBuilder();
      renameTable.append("ALTER TABLE ").append(tempTableName).append(" RENAME TO ")
          .append(tableName);

      db.execSQL(insertTable.toString());
      db.execSQL(dropTable.toString());
      db.execSQL(renameTable.toString());

      db.setTransactionSuccessful();
    } catch (Exception e) {
      e.printStackTrace();
      if (callBack != null) {
        callBack.onUpgradeFail("restoreData ===> " + e.toString());
      }
    } finally {
      db.endTransaction();
    }
  }

  @SafeVarargs
  private final void createNewTablesIfNotExists(StandardDatabase db,
      Class<? extends AbstractDao<?, ?>>... daoClasses) {
    reflectMethod(db, "createTable", true, daoClasses);
  }

  @SafeVarargs
  private final void reflectMethod(StandardDatabase db, String methodName, boolean isExists,
      @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
    if (daoClasses.length < 1) {
      if (callBack != null) {
        callBack.onUpgradeFail("reflectMethod ===> daoClasses.length < 1");
      }
      return;
    }
    try {
      for (Class cls : daoClasses) {
        Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
        method.invoke(null, db, isExists);
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (callBack != null) {
        callBack.onUpgradeFail("reflectMethod ===> " + e.toString());
      }
    }
  }

  //此方法需传入所有的daoClasses才能去删除不存在的表
  @SafeVarargs
  private final void dropOldTablesIfExists(StandardDatabase db,
      Class<? extends AbstractDao<?, ?>>... daoClasses) {
    String[] oldAllTable = getAllTable(db);

    String[] newAllTable = new String[daoClasses.length];

    for (int i = 0; i < daoClasses.length; i++) {
      DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

      newAllTable[i] = daoConfig.tablename;
    }

    List<String> dropTables = compare(newAllTable, oldAllTable);

    if (dropTables.size() == 0) {
      return;
    }

    for (String table : dropTables) {

      StringBuilder dropColumn = new StringBuilder();

      dropColumn.append("DROP TABLE ").append(table);

      Log.e("SqlUtil","table drop == " + dropColumn.toString());

      db.execSQL(dropColumn.toString());
    }
  }

  private Object getTableType(Class<?> type) {
    if (type.equals(Integer.class) || type.equals(int.class) ||
        type.equals(byte.class) || type.equals(Byte.class) ||
        type.equals(short.class) || type.equals(Short.class)) {
      return " INTEGER DEFAULT 0 ";
    }

    if (type.equals(Double.class) || type.equals(double.class)) {
      return " DOUBLE DEFAULT 0 ";
    }

    if (type.equals(Float.class) || type.equals(float.class)) {
      return " FLOAT DEFAULT 0 ";
    }

    if (type.equals(Long.class) || type.equals(long.class)) {
      return " Long DEFAULT 0 ";
    }

    if (type.equals(String.class)) {
      return " TEXT ";
    }

    if (type.equals(Boolean.class) || type.equals(boolean.class)) {
      return " NUMERIC DEFAULT 0 ";
    }

    return " TEXT ";
  }

  private List<String> getColumns(Database db, String tableName) {
    List<String> columns = new ArrayList<>();
    Cursor cursor = null;
    try {
      cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
      if (cursor != null) {
        columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (callBack != null) {
        callBack.onUpgradeFail("getColumns ===> " + e.toString());
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return columns;
  }


  private boolean isTableExists(Database db, String tableName) {
    if (db == null || TextUtils.isEmpty(tableName)) {
      return false;
    }

    String sql = "SELECT COUNT(*) FROM `" + SQLITE_MASTER + "` WHERE type = ? AND name = ?";
    Cursor cursor = null;
    int count = 0;

    try {
      cursor = db.rawQuery(sql, new String[]{"table", tableName});
      if (cursor == null || !cursor.moveToFirst()) {
        return false;
      }
      count = cursor.getInt(0);
    } catch (Exception e) {
      e.printStackTrace();
      if (callBack != null) {
        callBack.onUpgradeFail("isTableExists ===> " + e.toString());
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }

    return count > 0;
  }

  private String[] getAllTable(Database db) {
    Cursor cursor = db
        .rawQuery("SELECT NAME FROM `" + SQLITE_MASTER + "` WHERE type = 'table'", null);

    List<String> tableNames = new ArrayList<>();

    while (cursor.moveToNext()) {
      //遍历出表名
      String name = cursor.getString(0);

      if ("android_metadata".equals(name) || "sqlite_sequence".equals(name)) {
        continue;
      }

      tableNames.add(name);

      Log.e("SqlUtil","name == " + name);
    }

    return tableNames.toArray(new String[tableNames.size()]);
  }

  //找出两个数组中不相同的元素
  private <T> List<T> compare(T[] t1, T[] t2) {
    List<T> list1 = Arrays.asList(t1);
    List<T> list2 = new ArrayList<>();
    for (T t : t2) {
      if (!list1.contains(t)) {
        list2.add(t);
      }
    }
    return list2;
  }
}
