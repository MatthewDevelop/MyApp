package cn.foxconn.matthew.myapp.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.activity.setting.SmokeActivity;
import cn.foxconn.matthew.myapp.utils.ScreenUtil;

/**
 * @author:Matthew
 * @date:2018/8/18
 * @email:guocheng0816@163.com
 * @func:
 */
public class RocketService extends Service {
    private static final String TAG = "RocketService";
    private static float startX;
    private static float startY;
    private WindowManager mWindowManager;
    private View mView;
    //    private static WindowManager.LayoutParams sParams;
    private ExecutorService fixedThreadPool;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mBarHeight;
    private WindowManager.LayoutParams mParams;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0001:
                    //重新绘制
                    mWindowManager.updateViewLayout(mView, mParams);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadFactory checkUpdateFactory = new ThreadFactoryBuilder().setNameFormat("sendRocket-thread").build();
        fixedThreadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), checkUpdateFactory);
        //在第三方app中弹出自己的浮窗
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mScreenWidth = ScreenUtil.getScreenWidth();
        mScreenHeight = ScreenUtil.getScreenHeight();
        mBarHeight = ScreenUtil.getStatusBarHeight();
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.gravity = Gravity.LEFT + Gravity.TOP;
        mView = View.inflate(this, R.layout.rocket_layout, null);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //拖动的初始位置
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        System.out.println(v.getWidth()+"-"+v.getHeight()+"-"+v.getLeft()+"-"+v.getTop()+"-"+v.getRight()+"-"+v.getBottom());
                        //拖动后的位置
                        float endX = event.getRawX();
                        float endY = event.getRawY();
                        //与初始位置的偏移量
                        float mX = endX - startX;
                        float mY = endY - startY;
                        //更新位置
//                        sParams.gravity = Gravity.LEFT + Gravity.TOP;
                        mParams.x += mX;
                        mParams.y += mY;
                        //防止坐标偏离屏幕
                        if (mParams.x < 0) {
                            mParams.x = 0;
                        }
                        if (mParams.y < 0) {
                            mParams.y = 0;
                        }
                        if (mParams.x > mScreenWidth - v.getWidth()) {
                            mParams.x = mScreenWidth - v.getWidth();
                        }
                        /**
                         *解通话界面拖拽悬浮框至最低端，设置界面会超出屏幕范围
                         */
                        if (mParams.y > mScreenHeight - v.getHeight() - mBarHeight) {
                            mParams.y = mScreenHeight - v.getHeight() - mBarHeight;
                        }
//                        Log.e(TAG, "onTouch: "+v.getWidth() );
//                        Log.e(TAG, "onTouch: "+v.getHeight() );
                        //重新绘制
                        mWindowManager.updateViewLayout(v, mParams);
//                        System.out.println(v.getWidth()+"-"+v.getHeight()+"-"+v.getLeft()+"-"+v.getTop()+"-"+v.getRight()+"-"+v.getBottom());
                        //重置起始位置
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "onTouch: " + mParams.x + "---->" + mParams.y);
                        if (mParams.y > 3 * mScreenHeight / 4 && mParams.x > mScreenWidth / 4 && (mParams.x + v.getWidth()) < 3 * mScreenWidth / 4) {
                            Log.e(TAG, "onTouch:  sendRocket");
                            //强制火箭居中
                            mParams.x=mScreenWidth/2-v.getWidth()/2;
                            sendRocket();
                            Intent intent=new Intent(RocketService.this, SmokeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mWindowManager.addView(mView, mParams);
    }

    private void sendRocket() {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int distance = mParams.y-mScreenHeight/5+mBarHeight;
                for (int i = 0; i < 10; i++) {
                    mParams.y = mParams.y - distance / 10;
                    mHandler.sendEmptyMessage(0x0001);
                    try {
                        Thread.sleep(50L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWindowManager != null && mView != null) {
            mWindowManager.removeView(mView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
