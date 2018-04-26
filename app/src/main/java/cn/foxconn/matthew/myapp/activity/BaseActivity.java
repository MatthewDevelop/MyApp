package cn.foxconn.matthew.myapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author:Matthew
 * @date:2018/4/26
 * @email:guocheng0816@163.com
 * @func:超级基类，4.4以上沉浸状态栏
 */
public class BaseActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 处理状态栏变色
     *
     * @param color 状态栏颜色
     *              4.4状态栏会显示为该颜色
     *              5.0以上会显示半透明效果
     */
    public void translucentBar(int color){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取状态栏高度
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            //绘制一个和状态栏高度一致的View
            View view=new View(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
            view.setLayoutParams(layoutParams);
            view.setBackgroundColor(ContextCompat.getColor(this,color));
            //添加布局到布局中
            ViewGroup viewGroup= (ViewGroup) getWindow().getDecorView();
            viewGroup.addView(view);
            //设置根布局的参数
            ViewGroup rootView= (ViewGroup) ((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }
}
