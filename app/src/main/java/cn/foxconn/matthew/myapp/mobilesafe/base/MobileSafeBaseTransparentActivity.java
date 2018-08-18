package cn.foxconn.matthew.myapp.mobilesafe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.activity.BaseActivity;
import cn.foxconn.matthew.myapp.app.AppConst;

/**
 * @author:Matthew
 * @date:2018/4/26
 * @email:guocheng0816@163.com
 * @func:透明的状态栏不做处理
 */
public abstract class MobileSafeBaseTransparentActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResId());
//        translucentBar(AppConst.THEME_COLOR);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
    }

    protected abstract int getContentResId();
}
