package com.hewking.develop.util;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date: 2021/4/6 17:15
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
public final class LiveDataBus {

  private final java.util.Map<String, BusMutableLiveData<Object>> bus;

  private LiveDataBus() {
    bus = new HashMap<>();
  }

  private static class SingletonHolder {
    private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
  }

  public static LiveDataBus get() {
    return SingletonHolder.DEFAULT_BUS;
  }

  public <T> MutableLiveData<T> with(String key, Class<T> type) {
    if (!bus.containsKey(key)) {
      bus.put(key, new BusMutableLiveData<>());
    }
    return (MutableLiveData<T>) bus.get(key);
  }

  public MutableLiveData<Object> with(String key) {
    return with(key, Object.class);
  }

  private static class ObserverWrapper<T> implements androidx.lifecycle.Observer<T> {

    private androidx.lifecycle.Observer<T> observer;

    public ObserverWrapper(androidx.lifecycle.Observer<T> observer) {
      this.observer = observer;
    }

    @Override
    public void onChanged(@androidx.annotation.Nullable T t) {
      if (observer != null) {
        if (isCallOnObserve()) {
          return;
        }
        observer.onChanged(t);
      }
    }

    private boolean isCallOnObserve() {
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      if (stackTrace != null && stackTrace.length > 0) {
        for (StackTraceElement element : stackTrace) {
          if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
              "observeForever".equals(element.getMethodName())) {
            return true;
          }
        }
      }
      return false;
    }
  }

  private static class BusMutableLiveData<T> extends MutableLiveData<T> {

    private java.util.Map<androidx.lifecycle.Observer, androidx.lifecycle.Observer> observerMap = new HashMap<>();

    @Override
    public void observe(@androidx.annotation.NonNull LifecycleOwner owner, @androidx.annotation.NonNull androidx.lifecycle.Observer<? super T> observer) {
      super.observe(owner, observer);
      try {
        hook(observer);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void observeForever(@androidx.annotation.NonNull androidx.lifecycle.Observer<? super T> observer) {
      if (!observerMap.containsKey(observer)) {
        observerMap.put(observer, new ObserverWrapper(observer));
      }
      super.observeForever(observerMap.get(observer));
    }

    @Override
    public void removeObserver(@androidx.annotation.NonNull androidx.lifecycle.Observer<? super T> observer) {
      androidx.lifecycle.Observer realObserver = null;
      if (observerMap.containsKey(observer)) {
        realObserver = observerMap.remove(observer);
      } else {
        realObserver = observer;
      }
      super.removeObserver(realObserver);
    }

    private void hook(@androidx.annotation.NonNull androidx.lifecycle.Observer<? super T> observer) throws Exception {
      //get wrapper's version
      Class<androidx.lifecycle.LiveData> classLiveData = androidx.lifecycle.LiveData.class;
      Field fieldObservers = classLiveData.getDeclaredField("mObservers");
      fieldObservers.setAccessible(true);
      Object objectObservers = fieldObservers.get(this);
      Class<?> classObservers = objectObservers.getClass();
      Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
      methodGet.setAccessible(true);
      Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
      Object objectWrapper = null;
      if (objectWrapperEntry instanceof java.util.Map.Entry) {
        objectWrapper = ((java.util.Map.Entry) objectWrapperEntry).getValue();
      }
      if (objectWrapper == null) {
        throw new NullPointerException("Wrapper can not be bull!");
      }
      Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
      Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
      fieldLastVersion.setAccessible(true);
      //get livedata's version
      Field fieldVersion = classLiveData.getDeclaredField("mVersion");
      fieldVersion.setAccessible(true);
      Object objectVersion = fieldVersion.get(this);
      //set wrapper's version
      fieldLastVersion.set(objectWrapper, objectVersion);
    }
  }
}
