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

public class SettingItemCheckView extends RelativeLayout {
    private static final String TAG = "SettingItemView";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private TextView mTvTitle;
    private TextView mTvDes;
    private CheckBox mCbStatus;
    private String mTitle;
    private String mDesOn;
    private String mDesOff;

    public SettingItemCheckView(Context context) {
        super(context);
        Log.e(TAG, "SettingItemView: 1" );
        initView();
    }

    public SettingItemCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "SettingItemView: 2" );
//        getAtts(attrs);
        //根据name获取value
        mTitle = attrs.getAttributeValue(NAMESPACE, "title");
        mDesOn = attrs.getAttributeValue(NAMESPACE, "des_on");
        mDesOff = attrs.getAttributeValue(NAMESPACE, "des_off");
        initView();
    }

    public SettingItemCheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "SettingItemView: 3" );
        initView();
//        getAtts(attrs);
    }

    /**
     * show自定义属性信息
     * @param attrs
     */
    public void getAtts(AttributeSet attrs){
        int attributeCount=attrs.getAttributeCount();
        for (int i=0;i<attributeCount;i++){
            String name=attrs.getAttributeName(i);
            String value=attrs.getAttributeValue(i);
            LogUtil.e(TAG,name+" "+value);
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        //将自定义的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item_check,this);
        mTvTitle = findViewById(R.id.tv_title);
        mTvDes = findViewById(R.id.tv_des);
        mCbStatus = findViewById(R.id.cb_status);
        setTitle(mTitle);
    }

    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    public void setDes(String des){
        mTvDes.setText(des);
    }

    /**
     * 返回选中状态
     * @return
     */
    public boolean isChecked(){
        return mCbStatus.isChecked();
    }

    public void setChecked(boolean isChecked){
        mCbStatus.setChecked(isChecked);
        //根据选择状态更新版本描述
        if(isChecked){
            setDes(mDesOn);
        }else {
            setDes(mDesOff);
        }
    }
}
