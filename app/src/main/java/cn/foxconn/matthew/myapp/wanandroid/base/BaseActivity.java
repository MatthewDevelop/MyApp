package cn.foxconn.matthew.myapp.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.app.App;
import cn.foxconn.matthew.myapp.wanandroid.widget.CustomDialog;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public abstract class BaseActivity<V, T extends BasePresenter<V, ActivityEvent>> extends RxAppCompatActivity {
    protected T mPresenter;
    CustomDialog mCustomDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getActivities().add(this);
        init();
        //判断是否使用MVP模式
        mPresenter = createPresenter();

        if (mPresenter != null) {
            //所有子类均要实现对应的view接口
            mPresenter.attachView((V) this);
        }


        setContentView(getContentResId());

        ButterKnife.bind(this);

        //excuteStatesBar();

        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 获取布局id
     *
     * @return 布局资源id
     */
    protected abstract int getContentResId();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    protected void initListener() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void init() {
    }

    /**
     * 解决4.4设置状态栏颜色之后，布局内容嵌入状态栏位置问题
     */
    private void excuteStatesBar() {
        //todo 有待研究
        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows,
            // 而是设置 ContentView 的第一个子 View ，预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(true);
        }
    }

    public void showProgressDialog(String tip) {
        hideProgressDialog();
        View view = View.inflate(this, R.layout.layout_waiting_dialog, null);
        if (!TextUtils.isEmpty(tip)) {
            ((TextView) view.findViewById(R.id.tv_tip)).setText(tip);
        }
        mCustomDialog = new CustomDialog(this, view, R.style.MyDialog);
        mCustomDialog.setCancelable(false);
        mCustomDialog.show();
    }

    public void hideProgressDialog() {
        if (mCustomDialog != null) {
            mCustomDialog.dismiss();
            mCustomDialog = null;
        }
    }
}
