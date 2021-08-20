package com.hewking.develop.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author luobin
 * @descripsiton 刘海屏 util
 * @date :2018/12/27
 */
public class NotchUtil {

  // vivo start
  public static final int VIVO_NOTCH = 0x00000020;//是否有刘海
  public static final int VIVO_FILLET = 0x00000008;//是否有圆角

  public static boolean hasNotchAtVivo(Context context) {
    boolean ret = false;
    try {
      ClassLoader classLoader = context.getClassLoader();
      Class FtFeature = classLoader.loadClass("android.util.FtFeature");
      Method method = FtFeature.getMethod("isFeatureSupport", int.class);
      ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
    } catch (ClassNotFoundException e) {
      Log.e("Notch", "hasNotchAtVoio ClassNotFoundException");
    } catch (NoSuchMethodException e) {
      Log.e("Notch", "hasNotchAtVoio NoSuchMethodException");
    } catch (Exception e) {
      Log.e("Notch", "hasNotchAtVoio Exception");
    } finally {
      Log.e("Notch", "hasNotchAtVivo " + ret);
      return ret;
    }
  }
  // vivo end


  //miui start;
  // 适配过小米 Android O 的刘海屏接口，在小米的 Android P 设备上需要重新适配；Android O 的老接口在 Android P 设备上不生效
  public static boolean hasNotchAtXiaoMi() {
    int result = 0;
    try {
      Class SystemProperties = Class.forName("android.os.SystemProperties");
      Class[] paramTypes = new Class[2];
      paramTypes[0] = String.class;
      paramTypes[1] = int.class;
      Method getInt = SystemProperties.getMethod("getInt", paramTypes);
      //参数
      Object[] params = new Object[2];
      params[0] = new String("ro.miui.notch");
      params[1] = new Integer(0);
      result = (Integer) getInt.invoke(SystemProperties, params);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();

    }
    Log.e("Notch", "hasNotchAtXiaoMi " + (result == 1));
    return result == 1;
  }

  /***
   *仅针对 Android O 版本；
   * window级别：
   * extraFlags 有以下变量：
   <p> 0x00000100 开启配置
   <p> 0x00000200 竖屏配置
   <p> 0x00000400 横屏配置
   <p> 组合后表示 Window 的配置，如：

   <p>  0x00000100 | 0x00000200 竖屏绘制到耳朵区
   <p> 0x00000100 | 0x00000400 横屏绘制到耳朵区
   <p> 0x00000100 | 0x00000200 | 0x00000400 横竖屏都绘制到耳朵区
   */
  public static void setNotchStateAtXiaoMi(Activity activity) {
    int flag = 0x00000100 | 0x00000200 | 0x00000400;
    try {
      Method method = Window.class.getMethod("addExtraFlags",
          int.class);
      method.invoke(activity.getWindow(), flag);
    } catch (Exception e) {
      Log.i("TAG", "addExtraFlags not found.");
    }
  }


  public static int getNotchHeightAtXiaoMi(Context context) {
    int result = 0;
    int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }

    return result;
  }

  public static int getNotchStatusBarHeightAtXiaoMi(Context context) {
    int result = 0;

    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }

    return result;
  }

  //miui end

  //huawei start

  public static boolean hasNotchAtHuaWei(Context context) {

    boolean ret = false;

    try {

      ClassLoader cl = context.getClassLoader();

      Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");

      Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");

      ret = (boolean) get.invoke(HwNotchSizeUtil);

    } catch (ClassNotFoundException e) {

      Log.e("test", "hasNotchInScreen ClassNotFoundException");

    } catch (NoSuchMethodException e) {

      Log.e("test", "hasNotchInScreen NoSuchMethodException");

    } catch (Exception e) {

      Log.e("test", "hasNotchInScreen Exception");

    } finally {
      Log.e("Notch", "hasNotchAtHuaWei " + ret);
      return ret;
    }

  }


  public static int[] getNotchSize(Context context) {

    int[] ret = new int[]{0, 0};

    try {

      ClassLoader cl = context.getClassLoader();

      Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");

      Method get = HwNotchSizeUtil.getMethod("getNotchSize");

      ret = (int[]) get.invoke(HwNotchSizeUtil);

    } catch (ClassNotFoundException e) {

      Log.e("test", "getNotchSize ClassNotFoundException");

    } catch (NoSuchMethodException e) {

      Log.e("test", "getNotchSize NoSuchMethodException");

    } catch (Exception e) {

      Log.e("test", "getNotchSize Exception");

    } finally {

      return ret;

    }

  }

//   m1:  <meta-data android:name="android.notch_support" android:value="true"/>

  //m2:
  /*刘海屏全屏显示FLAG*/
  public static final int FLAG_NOTCH_SUPPORT = 0x00010000;

  /**
   * 设置应用窗口在华为刘海屏手机使用刘海区
   *
   * @param window 应用页面window对象
   */
  public static void setNotFullScreenWindowLayoutInDisplayCutout(Window window) {
    if (window == null) {
      return;
    }
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    try {
      Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
      Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
      Object layoutParamsExObj = con.newInstance(layoutParams);
      Method method = layoutParamsExCls.getMethod("clearHwFlags", int.class);
      method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
    } catch (Exception e) {
      e.printStackTrace();
      Log.e("test", "other Exception");
    }
  }


  public static void setNotchStateAtHuaWei(Activity activity) {
    Window window = activity.getWindow();
    setNotFullScreenWindowLayoutInDisplayCutout(window);
    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) window.getDecorView()
        .getLayoutParams();
    activity.getWindowManager().updateViewLayout(window.getDecorView(), layoutParams);
  }

  //huawei  end

  //oppo start

  public static boolean hasNotchAtOPPO(Context context) {
    boolean ret = context.getPackageManager()
        .hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    Log.e("Notch", "hasNotchAtOPPO " + ret);
    return ret;
  }

  //oppo  end

}
