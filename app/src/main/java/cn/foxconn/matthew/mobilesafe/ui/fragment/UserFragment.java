package cn.foxconn.matthew.mobilesafe.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.app.AppConst;
import cn.foxconn.matthew.mobilesafe.helper.RetrofitServiceManager;
import cn.foxconn.matthew.mobilesafe.ui.activity.CollectActivity;
import cn.foxconn.matthew.mobilesafe.ui.activity.LoginActivity;
import cn.foxconn.matthew.mobilesafe.ui.activity.WanAndroidActivity;
import cn.foxconn.matthew.mobilesafe.ui.base.BaseFragment;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.utils.PrefUtil;
import cn.foxconn.matthew.mobilesafe.utils.ToastUtil;
import cn.foxconn.matthew.mobilesafe.widget.RoundImageView;

/**
 * @author:Matthew
 * @date:2018/3/12
 * @email:guocheng0816@163.com
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.iv_icon)
    RoundImageView iv_icon;
    @BindView(R.id.tv_userName)
    TextView tv_userName;
    @BindView(R.id.cv_collect)
    CardView cv_collect;
    @BindView(R.id.tv_collect)
    TextView tv_collect;
    @BindView(R.id.cv_logout)
    CardView cv_logout;
    @BindView(R.id.tv_logout)
    TextView tv_logout;


    @Override
    protected int getContentResId() {
        return R.layout.frag_user;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        if (PrefUtil.getBoolean(getActivity(), AppConst.IS_LOGIN_KEY, false)) {
            tv_userName.setText(PrefUtil.getString(getActivity(), AppConst.USERNAME_KEY, "暂未登录"));
            tv_logout.setText("退出登录");
        } else {
            tv_userName.setText("暂未登录");
            tv_logout.setText("点击登录");
        }
    }

    @OnClick({R.id.cv_collect, R.id.cv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_collect:
                if (!PrefUtil.getBoolean(getActivity(), AppConst.IS_LOGIN_KEY, false)) {
                    ToastUtil.showShort(getContext(),"请先登录");
                }else {
                    startActivity(new Intent(getContext(), CollectActivity.class));
                }
                break;
            case R.id.cv_logout:
                if (!PrefUtil.getBoolean(getActivity(), AppConst.IS_LOGIN_KEY, false)) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    PrefUtil.setBoolean(getActivity(),AppConst.IS_LOGIN_KEY,false);
                    ToastUtil.showShort(getContext(),"已登出");
                    tv_userName.setText("暂未登录");
                    tv_logout.setText("点击登录");
                    RetrofitServiceManager.clearCookie();
                    refreshData();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 登出刷新数据
     */
    public void refreshData(){
        ((HomeFragment)WanAndroidActivity.getFragments().get(0)).onRefresh();
        ((TypeFragment)WanAndroidActivity.getFragments().get(1)).onRefresh();
    }
}
