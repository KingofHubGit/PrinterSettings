package com.pax.printersetting.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.pax.printersetting.constants.SettingConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dengtl on 2020/11/24.
 */

public class Utils {

    public static void startActivityByClass(Context context, Class<?> skip){
        context.startActivity(new Intent(context, skip));
    }

    public static void startActivityByAction(Context context, String skipAction){
        context.startActivity(new Intent(skipAction));
    }


    public static void startActivityByComponent(Context context, String skipPackages, String skipClassName){
        ComponentName cn = new ComponentName(skipPackages, skipClassName) ;
        Intent intent = new Intent() ;
        intent.setComponent(cn) ;
        context.startActivity(intent) ;
    }

    public static String getProp(String key, String defstr) {
        String result="";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            result=(String)get.invoke(c, key, defstr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setProp(String key, String val) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class,String.class);
            set.invoke(c, key,val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isPasswordDisabled(){
        return Utils.getProp(SettingConfig.PASSWORD_DISABLED_PROP,"false").equals("true");
    }

}
