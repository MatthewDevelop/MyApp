package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


/**
 * 设置引导页的基类
 * @author:Matthew
 * @date:2018/2/4
 * @email:guocheng0816@163.com
 */

public abstract class BaseSetupActivity extends AppCompatActivity {

    private GestureDetector mDetector;
    public SharedPreferences preference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = getSharedPreferences("config", MODE_PRIVATE);
        mDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            /**
             * 监听手势滑动事件
             * @param e1 手势滑动的起点
             * @param e2 手势滑动的终点
             * @param velocityX 水平速度
             * @param velocityY 垂直速度
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //判断纵向滑动幅度是否过大，过大则不切换界面
                if(Math.abs(e2.getRawY()-e1.getRawY())>100){
                    return true;
                }
                //判断用户的滑动速度是否过慢
                if(Math.abs(velocityX)<100){
//                    Toast.makeText(BaseSetupActivity.this, "慢了", Toast.LENGTH_SHORT).show();
                    return true;
                }
                //向右划，到上一页
                if((e2.getRawX()-e1.getRawX())>200){
                    showPreviousPage();
                    return true;
                }
                //向左划，下一页
                if((e1.getRawX()-e2.getRawX())>200){
                    showNextPage();
                    return true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将用户的触摸事件交给手势识别器
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void showNext(View view){
        showNextPage();
    }

    public void showPrevious(View view){
        showPreviousPage();
    }

    /**
     * 展示上一页
     */
    public abstract void showPreviousPage();

    /**
     * 展示下一页
     */
    public abstract void showNextPage();
}
