package cn.foxconn.matthew.myapp.utils;

import android.content.Context;
import android.view.WindowManager;

import cn.foxconn.matthew.myapp.app.App;

/**
 * @author:Matthew
 * @date:2018/8/15
 * @email:guocheng0816@163.com
 * @func:屏幕工具类
 */
public class ScreenUtil {
    /**
     * 获取WindowManager
     * @return
     */
    private static WindowManager getWindowManager(){
        return (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
    }
    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        return getWindowManager().getDefaultDisplay().getHeight();
    }


    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        return getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight(){
        int resourceId = App.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return App.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
