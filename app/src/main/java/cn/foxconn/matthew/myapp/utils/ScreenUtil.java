package cn.foxconn.matthew.myapp.utils;

import android.app.Activity;

import cn.foxconn.matthew.myapp.app.App;

/**
 * @author:Matthew
 * @date:2018/8/15
 * @email:guocheng0816@163.com
 * @func:屏幕工具类
 */
public class ScreenUtil {

    /**
     * 获取屏幕高度
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity){
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }


    /**
     * 获取屏幕宽度
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity){
        return activity.getWindowManager().getDefaultDisplay().getWidth();
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
