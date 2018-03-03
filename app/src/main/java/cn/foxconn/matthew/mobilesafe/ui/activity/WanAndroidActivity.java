package cn.foxconn.matthew.mobilesafe.ui.activity;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.ui.base.BaseActivity;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.utils.UIUtil;
import cn.foxconn.matthew.mobilesafe.widget.FontTextView;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class WanAndroidActivity extends BaseActivity {
    @BindView(R.id.ft_search)
    FontTextView ft_search;
    @BindView(R.id.ft_hot)
    FontTextView ft_hot;
    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.ft_home)
    FontTextView ft_home;
    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;
    @BindView(R.id.ft_type)
    FontTextView ft_type;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.ll_user)
    LinearLayout ll_user;
    @BindView(R.id.ft_user)
    FontTextView ft_user;
    @BindView(R.id.tv_user)
    TextView tv_user;
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @Override
    protected int getContentResId() {
        return R.layout.activity_wan_android;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        setTabColor(ft_home, tv_home);
    }

    @OnClick({R.id.ll_home, R.id.ll_type, R.id.ll_user, R.id.ft_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                setTabColor(ft_home, tv_home);
                break;
            case R.id.ll_type:
                setTabColor(ft_type, tv_type);
                break;
            case R.id.ll_user:
                setTabColor(ft_user, tv_user);
                break;
            case R.id.ft_search:
                break;
            default:
                break;
        }
    }

    /**
     * 设置导航栏的选择状态和颜色
     *
     * @param fontTextView
     * @param textView
     */
    private void setTabColor(FontTextView fontTextView, TextView textView) {
        ft_home.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        tv_home.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        ft_type.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        tv_type.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        ft_user.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        tv_user.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        fontTextView.setTextColor(UIUtil.getColor(R.color.tab_bar_selected));
        textView.setTextColor(UIUtil.getColor(R.color.tab_bar_selected));

    }
}