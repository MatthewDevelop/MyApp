package cn.foxconn.matthew.myapp.mobilesafe.activity.setting;

import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;


import butterknife.BindView;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseTransparentActivity;

/**
 * @author:Matthew
 * @date:2018/8/18
 * @email:guocheng0816@163.com
 * @func:烟雾效果的activity
 */
public class SmokeActivity extends MobileSafeBaseTransparentActivity {
    @BindView(R.id.iv_smoke_bottom)
    ImageView ivBottom;
    @BindView(R.id.iv_smoke_top)
    ImageView ivTop;

    @Override
    protected void init() {
        super.init();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        ivTop.startAnimation(alphaAnimation);
        ivBottom.startAnimation(alphaAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_smoke;
    }
}
