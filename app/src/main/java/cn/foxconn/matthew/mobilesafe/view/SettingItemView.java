package cn.foxconn.matthew.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.foxconn.matthew.mobilesafe.R;

/**
 * @author:Matthew
 * @date:2018/2/1
 * @email:guocheng0816@163.com
 */

public class SettingItemView extends RelativeLayout {

    private TextView tv_title;
    private TextView tv_des;
    private CheckBox cb_status;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
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
    }
}
