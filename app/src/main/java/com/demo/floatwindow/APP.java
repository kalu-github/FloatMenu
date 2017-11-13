package com.demo.floatwindow;

import android.app.Application;

/**
 * description: 初始化操作
 * created by kalu on 2016/11/9 16:39
 */
public class APP extends Application {

    private final String TAG = "Application";

    private static APP mApplication;

    public static APP getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    private static int activityVisiableCount;

    public static boolean isAppRunningFront() {
        return activityVisiableCount > 0;
    }

    public static void setIsAppRunningFront(boolean isAppRunningFront) {
        if (isAppRunningFront) {
            activityVisiableCount++;
        } else if (activityVisiableCount > 0) {
            activityVisiableCount--;
        }
    }
}