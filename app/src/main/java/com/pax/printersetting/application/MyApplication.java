package com.pax.printersetting.application;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by dengtl on 2020/11/26.
 */

public class MyApplication extends Application {
    private static Context context = null;
    private static ContentResolver contentResolver = null;
    ActivityLifeCycleManager mActivityLifeCycleManager;

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        contentResolver = getContentResolver();
        mActivityLifeCycleManager = new ActivityLifeCycleManager();
        //registerActivityLifecycleCallbacks(mActivityLifeCycleManager);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //unregisterActivityLifecycleCallbacks(mActivityLifeCycleManager);
    }

    public static Context getAppContext() {
        return context;
    }

    public static ContentResolver getAppContentResolver() {
        return contentResolver;
    }

}
