package cn.foxconn.matthew.mobilesafe.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author:Matthew
 * @date:2018/3/13
 * @email:guocheng0816@163.com
 */

public class CustomDialog extends Dialog {



    public CustomDialog(@NonNull Context context, View view, int themeResId) {
        super(context, themeResId);
        setContentView(view);
        Window window=getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        params.gravity= Gravity.CENTER;
        window.setAttributes(params);
    }

}
