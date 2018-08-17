package cn.foxconn.matthew.myapp.utils;

import android.util.Log;

/**
 * 控制log输出
 * Created by Matthew on 2017/11/4.
 */

public class LogUtil {

    private static final int VERVOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;

    public static final int LEVEL = VERVOSE;

    public static void v(String tag, String message) {
        if (LEVEL <= VERVOSE) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= INFO) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= WARN) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (LEVEL <= ERROR) {
            Log.e(tag, message);
        }
    }


}
