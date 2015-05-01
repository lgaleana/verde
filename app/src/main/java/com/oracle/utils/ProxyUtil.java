package com.oracle.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lingyzha on 4/30/2015.
 */
public class ProxyUtil {

    public static void setProxy(Context appContext) {
        System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("https.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("https.proxyPort", "80");
        try {
            Class applictionClass = Class.forName("android.app.Application");
            Field mLoadedApkField = applictionClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mloadedApk = mLoadedApkField.get(appContext);
            Class loadedApkClass = Class.forName("android.app.LoadedApk");
            Field mReceiversField = loadedApkClass.getDeclaredField("mReceivers");
            mReceiversField.setAccessible(true);
            ArrayMap receivers = (ArrayMap) mReceiversField.get(mloadedApk);
            for (Object receiverMap : receivers.values()) {
                for (Object receiver : ((ArrayMap) receiverMap).keySet()) {
                    Class clazz = receiver.getClass();
                    if (clazz.getName().contains("ProxyChangeListener")) {
                        Method onReceiveMethod = clazz.getDeclaredMethod("onReceive", Context.class, Intent.class);
                        Intent intent = new Intent(Proxy.PROXY_CHANGE_ACTION);
                        onReceiveMethod.invoke(receiver, appContext, intent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeProxy(){
        System.getProperties().remove("http.proxyHost");
        System.getProperties().remove("http.proxyPort");
        System.getProperties().remove("https.proxyHost");
        System.getProperties().remove("https.proxyPort");
    }
}
