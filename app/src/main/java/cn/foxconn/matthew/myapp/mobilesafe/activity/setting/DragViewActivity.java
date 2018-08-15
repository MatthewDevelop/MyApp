package cn.foxconn.matthew.myapp.mobilesafe.activity.setting;

import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseActivity;
import cn.foxconn.matthew.myapp.utils.ScreenUtil;

/**
 * @author:Matthew
 * @date:2018/8/15
 * @email:guocheng0816@163.com
 * @func:拖动调整归属地提示框的位置
 */
public class DragViewActivity extends MobileSafeBaseActivity {

    @BindView(R.id.tv_top_tip)
    TextView tvTopTip;
    @BindView(R.id.tv_bottom_tip)
    TextView tvBottomTip;
    @BindView(R.id.ll_toast)
    LinearLayout llToast;

    private float startX;
    private float startY;
    private SharedPreferences mPreferences;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mStatusBarHeight;
    private long[] hits = new long[2];

    @Override
    protected void init() {
        super.init();
        mPreferences = getSharedPreferences("config", MODE_PRIVATE);
        //获取上一次的坐标
        int lastX = mPreferences.getInt("lastX", 0);
        int lastY = mPreferences.getInt("lastY", 0);
        //获取屏幕的宽高
        mScreenHeight = ScreenUtil.getScreenHeight(this);
        mScreenWidth = ScreenUtil.getScreenWidth(this);
        mStatusBarHeight = ScreenUtil.getStatusBarHeight();
        /*//此种方法行不通，界面绘制时，会经过onMeasure(测量)，onLayout(布局),onDraw(绘制)三个步骤，
        onCreate方法执行完成之前是不会进行后续的测量、布局、绘制操作，因此此种方式无效（getWidth()getHeight()方法均无效）
        llToast.layout(lastX, lastY, lastX + llToast.getWidth(), lastY + llToast.getHeight());*/

        System.out.println(lastX + "---" + lastY);
        System.out.println(mScreenWidth + "---" + mScreenHeight + "---" + mStatusBarHeight);
        //获取布局对象
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llToast.getLayoutParams();
        //设置左边距
        params.leftMargin = lastX;
        //设置上边距
        params.topMargin = lastY;
        llToast.setLayoutParams(params);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_drag_view;
    }

    @OnTouch(R.id.ll_toast)
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //拖动的初始位置
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //拖动后的位置
                float endX = event.getRawX();
                float endY = event.getRawY();
                //与初始位置的偏移量
                float mX = endX - startX;
                float mY = endY - startY;
                //根据llToast的初始left，top，right，bottom坐标计算移动后的坐标
                int l = (int) (view.getLeft() + mX);
                int t = (int) (view.getTop() + mY);
                int r = (int) (view.getRight() + mX);
                int b = (int) (view.getBottom() + mY);
                //判断拖拽操作是否超出屏幕边界，注意状态栏高度的处理
                if (l < 0 || r > mScreenWidth || t < mStatusBarHeight || b > mScreenHeight) {
                    break;
                }
                //动态显示提示框的位置
                if (t > mScreenHeight / 2) {
                    tvBottomTip.setVisibility(View.INVISIBLE);
                    tvTopTip.setVisibility(View.VISIBLE);
                } else {
                    tvBottomTip.setVisibility(View.VISIBLE);
                    tvTopTip.setVisibility(View.INVISIBLE);
                }
                //根据偏移后的位置，移动llToast
                //此处也可以通过setLayoutParams来重新绘制UI
                view.layout(l, t, r, b);
                //重置起始位置
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //手指离开时记录最后的位置
                System.out.println(view.getLeft());
                System.out.println(view.getRight());
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putInt("lastX", view.getLeft());
                editor.putInt("lastY", view.getTop());
                editor.putBoolean("center_horizontal", false);
                editor.apply();
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 处理双击居中
     *
     * @param view
     */
    @OnClick(R.id.ll_toast)
    public void onClick(View view) {
        //将数组向左移动一位
        System.arraycopy(hits, 1, hits, 0, hits.length - 1);
        hits[hits.length - 1] = System.currentTimeMillis();
        if (System.currentTimeMillis() - hits[0] < 500) {
            //使图片居中
            llToast.layout(mScreenWidth / 2 - llToast.getWidth() / 2, llToast.getTop(),
                    mScreenWidth / 2 + llToast.getWidth() / 2, llToast.getBottom());
            mPreferences.edit().putBoolean("center_horizontal", true).apply();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            SharedPreferences.Editor editor=mPreferences.edit();
            editor.putInt("lastX", llToast.getLeft());
            editor.putInt("lastY", llToast.getTop());
            editor.apply();
        }
        return super.onKeyDown(keyCode, event);
    }
}
