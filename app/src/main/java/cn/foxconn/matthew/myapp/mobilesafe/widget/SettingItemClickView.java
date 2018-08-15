package cn.foxconn.matthew.myapp.mobilesafe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.utils.LogUtil;

/**
 * 自定义组合控件
 * @author:Matthew
 * @date:2018/2/1
 * @email:guocheng0816@163.com
 */

public class SettingItemClickView extends RelativeLayout {
    private static final String TAG = "SettingItemView";
    private TextView mTvTitle;
    private TextView mTvDes;

    public SettingItemClickView(Context context) {
        super(context);
        initView();
    }

    public SettingItemClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SettingItemClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "SettingItemView: 3" );
        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        //将自定义的布局文件设置给当前的SettingItemClickView
        View.inflate(getContext(), R.layout.view_setting_item_click,this);
        mTvTitle = findViewById(R.id.tv_title);
        mTvDes = findViewById(R.id.tv_des);
    }

    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    public void setDes(String des){
        mTvDes.setText(des);
    }
}
