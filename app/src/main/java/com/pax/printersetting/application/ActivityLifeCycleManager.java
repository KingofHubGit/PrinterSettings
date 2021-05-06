package com.pax.printersetting.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.Stack;

/**
 * Created by dengtl on 2021/1/7.
 */

public class ActivityLifeCycleManager implements Application.ActivityLifecycleCallbacks{

    private static final String TAG = "ActivityLifeCycleManager";
    //Activity栈
    private Stack<Activity> activities = new Stack<>();
    private static ActivityLifeCycleManager INSTANCE;

    public ActivityLifeCycleManager(){

    }

    /**
     * 单例
     * @return activityManager instance
     */
    public static ActivityLifeCycleManager getInstance(){
        if(INSTANCE == null){
            synchronized (ActivityLifeCycleManager.class){
                if(INSTANCE == null){
                    INSTANCE = new ActivityLifeCycleManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取Activity任务栈
     * @return activity stack
     */
    public Stack<Activity> getActivityStack(){
        return activities;
    }

    /**
     * Activity 入栈
     * @param activity Activity
     */
    public void addActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * Activity出栈
     * @param activity Activity
     */
    public void removeActivity(Activity activity){
        if(activity != null){
            activities.remove(activity);
        }
    }

    /**
     * 结束某Activity
     * @param activity Activity
     */
    public void finishActivity(Activity activity){
        if(activity != null){
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 获取当前Activity
     * @return current activity
     */
    public Activity getCurrentActivity(){
        return activities.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity(){
        finishActivity(activities.lastElement());
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: activity = " + activity.toString());
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted: activity = " + activity.toString());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed: activity = " + activity.toString());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused: activity = " + activity.toString());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped: activity = " + activity.toString());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG, "onActivitySaveInstanceState: activity = " + activity.toString());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed: activity = " + activity.toString());
        removeActivity(activity);
    }
}
