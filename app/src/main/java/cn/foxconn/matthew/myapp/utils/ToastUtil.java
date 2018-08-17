package cn.foxconn.matthew.myapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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
    public static final boolean IS_SHOW = true;
    private static final String TAG = "ToastUtil";
    private static WindowManager sWindowManager;
    private static View sView;
    private static float startX;
    private static float startY;
    private static WindowManager.LayoutParams sParams;

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
        if (IS_SHOW) {
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
        if (IS_SHOW) {
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
        if (IS_SHOW) {
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
        if (IS_SHOW) {
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
        if (IS_SHOW) {
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
        if (IS_SHOW) {
            Toast.makeText(context, message, duration).show();
        }
    }

    /**
     * 显示归属地浮窗
     */
    //TODO  android.permission.SYSTEM_ALERT_WINDOW
    public static void showToast(final Context context, String address) {
        //在第三方app中弹出自己的浮窗
        sWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        final int screenWidth = ScreenUtil.getScreenWidth();
        final int screenHeight = ScreenUtil.getScreenHeight();
        final int statusBarHeight = ScreenUtil.getStatusBarHeight();
        sParams = new WindowManager.LayoutParams();
        sParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        sParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        sParams.format = PixelFormat.TRANSLUCENT;
        sParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        sParams.setTitle("Toast");
        sParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        sView = View.inflate(context, R.layout.toast_layout, null);
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        int toastThemeNum = preferences.getInt("toast_theme", 0);
        int lastX = preferences.getInt("lastX", 0);
        int lastY = preferences.getInt("lastY", 0);
        /*boolean isCenterHorizontal = preferences.getBoolean("center_horizontal", false);
        //设置关于原点的偏移量
        if (isCenterHorizontal) {
            sParams.gravity = Gravity.CENTER_HORIZONTAL + Gravity.TOP;
            sParams.y = lastY;
        } else {*/
        //指定原点坐标为左上角，默认是居中
        sParams.gravity = Gravity.LEFT + Gravity.TOP;
        sParams.x = lastX;
        //sParams.y不包含状态栏的高度
        sParams.y = lastY;
//        }
        //ui还未绘制，无法获取对应数据
//        System.out.println(sView.getWidth()+"-"+sView.getHeight()+"-"+sView.getLeft()+"-"+sView.getTop()+"-"+sView.getRight()+"-"+sView.getBottom());
        FontTextView ftIcon = sView.findViewById(R.id.ft_icon);
        ftIcon.setTextColor(UIUtil.getColor(AppConst.TOAST_THEME_COLOR[toastThemeNum]));
        TextView tvAddress = sView.findViewById(R.id.tv_address);
        tvAddress.setText(address);
        tvAddress.setTextColor(UIUtil.getColor(AppConst.TOAST_THEME_COLOR[toastThemeNum]));
        sView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //拖动的初始位置
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        System.out.println(v.getWidth()+"-"+v.getHeight()+"-"+v.getLeft()+"-"+v.getTop()+"-"+v.getRight()+"-"+v.getBottom());
                        //拖动后的位置
                        float endX = event.getRawX();
                        float endY = event.getRawY();
                        //与初始位置的偏移量
                        float mX = endX - startX;
                        float mY = endY - startY;
                        //更新位置
//                        sParams.gravity = Gravity.LEFT + Gravity.TOP;
                        sParams.x += mX;
                        sParams.y += mY;
                        //防止坐标偏离屏幕
                        if (sParams.x < 0) {
                            sParams.x = 0;
                        }
                        if (sParams.y < 0) {
                            sParams.y = 0;
                        }
                        if (sParams.x > screenWidth - v.getWidth()) {
                            sParams.x = screenWidth - v.getWidth();
                        }
                        /**
                         *解通话界面拖拽悬浮框至最低端，设置界面会超出屏幕范围
                         */
                        if (sParams.y > screenHeight - v.getHeight() - statusBarHeight) {
                            sParams.y = screenHeight - v.getHeight() - statusBarHeight;
                        }
//                        Log.e(TAG, "onTouch: "+v.getWidth() );
//                        Log.e(TAG, "onTouch: "+v.getHeight() );
                        //重新绘制
                        sWindowManager.updateViewLayout(v, sParams);
//                        System.out.println(v.getWidth()+"-"+v.getHeight()+"-"+v.getLeft()+"-"+v.getTop()+"-"+v.getRight()+"-"+v.getBottom());
                        //重置起始位置
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //保存坐标数据
                        SharedPreferences.Editor editor =
                                context.getSharedPreferences("config", Context.MODE_PRIVATE).edit();
                        Log.e(TAG, "onTouch: " + sParams.x + "----" + sParams.y);
                        editor.putInt("lastX", sParams.x);
                        editor.putInt("lastY", sParams.y);
//                        editor.putBoolean("center_horizontal", false);
                        editor.apply();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        sWindowManager.addView(sView, sParams);
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
