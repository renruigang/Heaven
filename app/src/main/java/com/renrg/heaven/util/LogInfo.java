package com.renrg.heaven.util;

import android.util.Log;

import com.renrg.heaven.BuildConfig;

public class LogInfo {

    private static boolean loggable = BuildConfig.DEBUG;
    private final static String tag = "HV";

    public static void e(Object msg) {
        if (loggable) {
            if (msg != null) {
                if (msg instanceof Exception) {
                    Log.e(tag, "" + ((Exception) msg).getMessage());
                } else {
                    Log.e(tag, "" + msg.toString());
                }
            } else {
                e("object null");
            }
        }
    }

    public static void e(int msg) {
        if (loggable) {
            Log.e(tag, "" + msg);
        }
    }

    public static void e(String msg) {
        if (loggable) {
            Log.e(tag, "" + msg);
        }
    }
}
