package cn.foxconn.matthew.mobilesafe.utils;

import android.content.Context;
import android.content.res.Resources;

import cn.foxconn.matthew.mobilesafe.app.App;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class UIUtil {

    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return App.getContext();
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 获取colors中的颜色
     * @param resourceId
     * @return
     */
    public static int getColor(int resourceId){
        return getResource().getColor(resourceId);
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

}
