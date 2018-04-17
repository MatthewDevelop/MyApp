package cn.foxconn.matthew.myapp.wanandroid.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.helper.RetrofitServiceManager;
import cn.foxconn.matthew.myapp.wanandroid.activity.CollectActivity;
import cn.foxconn.matthew.myapp.wanandroid.activity.LoginActivity;
import cn.foxconn.matthew.myapp.wanandroid.activity.WanAndroidActivity;
import cn.foxconn.matthew.myapp.wanandroid.base.BaseFragment;
import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.utils.PrefUtil;
import cn.foxconn.matthew.myapp.utils.ToastUtil;
import cn.foxconn.matthew.myapp.wanandroid.widget.RoundImageView;

/**
 * @author:Matthew
 * @date:2018/3/12
 * @email:guocheng0816@163.com
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.iv_icon)
    RoundImageView mIvIcon;
    @BindView(R.id.tv_userName)
    TextView mTvUserName;
    @BindView(R.id.cv_collect)
    CardView mCvCollect;
    @BindView(R.id.tv_collect)
    TextView mTvCollect;
    @BindView(R.id.cv_logout)
    CardView mCvLogout;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;

    private WanAndroidActivity mActivity;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (WanAndroidActivity) activity;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void lazyLoad() {
        //do nothing
    }

    @Override
    protected int getContentResId() {
        return R.layout.frag_user;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        if (PrefUtil.getBoolean(getActivity(), AppConst.IS_LOGIN_KEY, false)) {
            mTvUserName.setText(PrefUtil.getString(getActivity(), AppConst.USERNAME_KEY, "暂未登录"));
            mTvLogout.setText("退出登录");
        } else {
            mTvUserName.setText("暂未登录");
            mTvLogout.setText("点击登录");
        }
    }

    @OnClick({R.id.cv_collect, R.id.cv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_collect:
                if (!PrefUtil.getBoolean(getActivity(), AppConst.IS_LOGIN_KEY, false)) {
                    ToastUtil.showShort(getContext(), "请先登录");
                } else {
                    startActivity(new Intent(getContext(), CollectActivity.class));
                }
                break;
            case R.id.cv_logout:
                if (!PrefUtil.getBoolean(getActivity(), AppConst.IS_LOGIN_KEY, false)) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    PrefUtil.setBoolean(getActivity(), AppConst.IS_LOGIN_KEY, false);
                    ToastUtil.showShort(getContext(), "已登出");
                    mTvUserName.setText("暂未登录");
                    mTvLogout.setText("点击登录");
                    //退出登录清除cookie
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
    public void refreshData() {
        ((HomeFragment) mActivity.getFragments().get(0)).onRefresh();
        ((TypeFragment) mActivity.getFragments().get(1)).onRefresh();
    }
}
