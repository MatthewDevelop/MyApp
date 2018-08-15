package cn.foxconn.matthew.myapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.app.App;
import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.wanandroid.widget.FontTextView;

import static android.content.Context.WINDOW_SERVICE;

/**
 * @author:Matthew
 * @date:2018/2/4
 * @email:guocheng0816@163.com
 */

public class ToastUtil {

    public static final boolean isShow = true;
    private static WindowManager sWindowManager;
    private static View sView;

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void show(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void show(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            Toast.makeText(context, message, duration).show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow) {
            Toast.makeText(context, message, duration).show();
        }
    }

    /**
     * 显示归属地浮窗
     */
    public static void showToast(Context context, String address) {
        //在第三方app中弹出自己的浮窗
        sWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        sView = View.inflate(context, R.layout.toast_layout, null);
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        int toastThemeNum = preferences.getInt("toast_theme", 0);
        FontTextView ftIcon = sView.findViewById(R.id.ft_icon);
        ftIcon.setTextColor(ContextCompat.getColor(context,AppConst.TOAST_THEME_COLOR[toastThemeNum]));
        TextView tvAddress = sView.findViewById(R.id.tv_address);
        tvAddress.setText(address);
        tvAddress.setTextColor(ContextCompat.getColor(context,AppConst.TOAST_THEME_COLOR[toastThemeNum]));
        sWindowManager.addView(sView, params);
    }


    /**
     * 从window上移除toast
     */
    public static void hideToast() {
        if (sWindowManager != null && sView != null) {
            sWindowManager.removeView(sView);
            sView = null;
            sWindowManager = null;
        }
    }
}
