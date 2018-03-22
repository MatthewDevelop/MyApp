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

    private static List<Activity> activities;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        setActivities(new LinkedList<Activity>());
    }

    public void setContext(Context context) {
        setContextValue(context);
    }

    public static void setContextValue(Context contextValue){
        mContext=contextValue;
    }

    public static Context getContext(){
        return mContext;
    }

    public void setActivities(List<Activity> activities) {
        setActivitiesValue(activities);
    }

    public static void setActivitiesValue(List<Activity> activities) {
        App.activities = activities;
    }

    public static List<Activity> getActivities() {
        return activities;
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
