package com.hewking.develop.util;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

/**
 * @author: jianhao
 * @create: 2020/9/16
 * @description:
 */
public class LocationUtil {

    /**
     * 判断网络定位是否打开
     * @param activity
     * @return
     */
    public static boolean isLocationEnable(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }
}
