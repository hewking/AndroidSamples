package com.hewking.develop.db;


import com.hewking.develop.db.table.DaoSession;

import org.greenrobot.greendao.AbstractDao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author: zgkxzx @ 002001
 * @created: 2019/5/20 10:58
 * @description: 基本的GreenDao 对应的Dao
 */
public class BaseDAO<T> {

  private final DaoSession daoSession;


  public BaseDAO() {
    daoSession = GreenDao.get().getDaoSession();
  }

  protected AbstractDao<T, ?> getDAO() {
    return (AbstractDao<T, ?>) daoSession.getDao(getType());
  }

  @SuppressWarnings("unchecked")
  private Class<T> getType() {
    try {
      ParameterizedType parameterizedType = (ParameterizedType) this.getClass()
          .getGenericSuperclass();
      return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 查询所有
   */
  public List<T> queryAllBlock() {
    return getDAO().queryBuilder().list();
  }

  /**
   * 保存数据
   */
  public void saveBlock(T entity) {
    getDAO().insertOrReplace(entity);
  }

  /**
   * 批量保存数据
   */
  public void saveAllBlock(Iterable<T> entities) {
    getDAO().insertOrReplaceInTx(entities);
  }

  /**
   * 删除数据
   */
  public void deleteBlock(T entity) {
    getDAO().delete(entity);
  }

  public void deleteAllBlock(List<T> entities) {
    getDAO().deleteInTx(entities);
  }

  /**
   * 删除所有
   */
  public void clearBlock() {
    getDAO().deleteAll();
  }
}
