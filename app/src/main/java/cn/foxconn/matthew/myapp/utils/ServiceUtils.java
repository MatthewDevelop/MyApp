package cn.foxconn.matthew.myapp.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;


/**
 * @author:Matthew
 * @date:2018/8/13
 * @email:guocheng0816@163.com
 * @func:
 */
public class ServiceUtils {

    public static boolean isServiceRunning(Context context,String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfoList = manager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo :
                serviceInfoList
                ) {
            String className = runningServiceInfo.service.getClassName();
            System.out.println(className);
            if (serviceName.equals(className)){
                return true;
            }
        }
        return false;
    }
}
