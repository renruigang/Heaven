package com.renrg.heaven;

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

/**
 * Created by renruigang on 2017/11/6.
 */
public class HeavenApplication extends Application {

    private static HeavenApplication mtApplication;
    private Stack<Activity> activityStack = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mtApplication = this;
    }

    public static HeavenApplication getInstance() {
        return mtApplication;
    }

    public void exitApplication() {
        clearStackActivity();
        System.exit(0);
    }

    public void addStackActivity(Activity context) {
        activityStack.add(context);
    }

    public void clearStackActivity() {
        for (Activity context : activityStack) {
            context.finish();
        }
    }
}
