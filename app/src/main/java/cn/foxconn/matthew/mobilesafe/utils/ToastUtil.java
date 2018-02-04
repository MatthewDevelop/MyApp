package cn.foxconn.matthew.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author:Matthew
 * @date:2018/2/4
 * @email:guocheng0816@163.com
 */

public class ToastUtil {
    public static void show(Context ctx,String msg){
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}
