package cn.foxconn.matthew.mobilesafe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.utils.LogUtil;

/**
 * 自定义组合控件
 * @author:Matthew
 * @date:2018/2/1
 * @email:guocheng0816@163.com
 */

public class SettingItemView extends RelativeLayout {
    private static final String TAG = "SettingItemView";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tv_title;
    private TextView tv_des;
    private CheckBox cb_status;
    private String title;
    private String des_on;
    private String des_off;

    public SettingItemView(Context context) {
        super(context);
        Log.e(TAG, "SettingItemView: 1" );
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "SettingItemView: 2" );
        //getAtts(attrs);
        //根据name获取value
        title = attrs.getAttributeValue(NAMESPACE, "title");
        des_on = attrs.getAttributeValue(NAMESPACE, "des_on");
        des_off = attrs.getAttributeValue(NAMESPACE, "des_off");
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "SettingItemView: 3" );
        initView();
        //getAtts(attrs);

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
        View.inflate(getContext(), R.layout.view_setting_layout,this);
        tv_title = findViewById(R.id.tv_title);
        tv_des = findViewById(R.id.tv_des);
        cb_status = findViewById(R.id.cb_status);
        setTitle(title);
    }

    public void setTitle(String title){
        tv_title.setText(title);
    }

    public void setDes(String des){
        tv_des.setText(des);
    }

    /**
     * 返回选中状态
     * @return
     */
    public boolean isChecked(){
        return cb_status.isChecked();
    }

    public void setChecked(boolean isChecked){
        cb_status.setChecked(isChecked);
        //根据选择状态更新版本描述
        if(isChecked){
            setDes(des_on);
        }else {
            setDes(des_off);
        }
    }
}
