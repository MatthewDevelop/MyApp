package cn.foxconn.matthew.myapp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class App extends Application {

    public static List<Activity> activities=new LinkedList<>();

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

    /**
     * 退出程序
     */
    public static void exit(){
        for (Activity activity:activities){
            activity.finish();
        }
    }
}
