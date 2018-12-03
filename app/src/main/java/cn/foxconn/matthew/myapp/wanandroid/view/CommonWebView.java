package cn.foxconn.matthew.myapp.wanandroid.view;

import android.widget.ProgressBar;

import cn.foxconn.matthew.myapp.wanandroid.base.BaseView;

/**
 * @author:Matthew
 * @date:2018/3/5
 * @email:guocheng0816@163.com
 */

public interface CommonWebView  extends BaseView {

    public ProgressBar getProgressBar();
    public void setTitle(String title);
}
