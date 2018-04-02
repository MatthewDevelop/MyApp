package cn.foxconn.matthew.myapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import cn.foxconn.matthew.myapp.app.App;

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
        return ContextCompat.getColor(getContext(),resourceId);
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


    /**
     * 获取随机颜色代码
     * @return
     */
    public static int getRandomColor(){
        Random random=new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        return ranColor;
    }

}
