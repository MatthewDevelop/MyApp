package cn.foxconn.matthew.myapp.expressinquiry.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.utils.UIUtil;

/**
 * 字母条
 *
 * @author:Matthew
 * @date:2018/4/13
 * @email:guocheng0816@163.com
 */
public class SideBar extends View {
    // 26个字母
    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    //触摸事件
    private OnTouchingLetterChangedListener mOnTouchingLetterChangedListener;
    private int choose = -1;

    private Paint paint = new Paint();

    private TextView mTextViewDialog;

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        //点击Y坐标
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = mOnTouchingLetterChangedListener;
        //点击y坐标所占总高度的比例*b数组的长度就等于点击b中的位置
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(UIUtil.getColor(R.color.white));
                choose = -1;
                invalidate();
                if (mTextViewDialog != null) {
                    mTextViewDialog.setVisibility(INVISIBLE);
                }
                break;
            default:
                setBackgroundColor(UIUtil.getColor(R.color.wheat));
                if (oldChoose != c) {
                    if (c > 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchLetterChanged(b[c]);
                        }
                        if (mTextViewDialog != null) {
                            mTextViewDialog.setText(b[c]);
                            mTextViewDialog.setVisibility(VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
//        获取每一个字母的高度
        int singleHeight = height / b.length;

        for (int i = 0; i < b.length; i++) {
            paint.setColor(UIUtil.getColor(R.color.main));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            //打开抗锯齿
            paint.setAntiAlias(true);
            paint.setTextSize(40);
            //设置选中状态
            if (i == choose) {
                paint.setColor(UIUtil.getColor(R.color.black));
                paint.setFakeBoldText(true);
            }
            //x坐标为中间位置减去字体宽度的一半
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            //重置画笔
            paint.reset();
        }
    }

    /**
     * 设置显示字母的TextView
     *
     * @param textViewDialog
     */
    public void setTextViewDialog(TextView textViewDialog) {
        mTextViewDialog = textViewDialog;
    }

    /**
     * 设置监听器
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        mOnTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 首字母选择监听接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchLetterChanged(String s);
    }
}
