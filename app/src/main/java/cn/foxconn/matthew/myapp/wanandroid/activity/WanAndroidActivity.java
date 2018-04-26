package cn.foxconn.matthew.myapp.wanandroid.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.wanandroid.base.WanAndroidBaseActivity;
import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.fragment.HomeFragment;
import cn.foxconn.matthew.myapp.wanandroid.fragment.TypeFragment;
import cn.foxconn.matthew.myapp.wanandroid.fragment.UserFragment;
import cn.foxconn.matthew.myapp.utils.UIUtil;
import cn.foxconn.matthew.myapp.wanandroid.widget.FontTextView;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class WanAndroidActivity extends WanAndroidBaseActivity {
    @BindView(R.id.ft_search)
    FontTextView mFtSearch;
    @BindView(R.id.ft_hot)
    FontTextView mFtHot;
    @BindView(R.id.ft_refresh)
    FontTextView mFtRefresh;
    @BindView(R.id.ll_home)
    LinearLayout mLlHome;
    @BindView(R.id.ft_home)
    FontTextView mFtHome;
    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.ll_type)
    LinearLayout mLlType;
    @BindView(R.id.ft_type)
    FontTextView mFtType;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.ll_user)
    LinearLayout mLlUser;
    @BindView(R.id.ft_user)
    FontTextView mFtUser;
    @BindView(R.id.tv_user)
    TextView mTvUser;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<android.support.v4.app.Fragment> mFragments;

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
        setTabColor(mFtHome, mTvHome);
        mFtRefresh.setVisibility(View.INVISIBLE);
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(TypeFragment.newInstance());
        mFragments.add(UserFragment.newInstance());
        //TODO 还需添加其他碎片布局
        mViewPager.setAdapter(new cn.foxconn.matthew.myapp.wanandroid.adapter.PagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.setCurrentItem(0, false);
        //设置左右预加载页面的数量
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTabColor(mFtHome, mTvHome);
                        mFtRefresh.setVisibility(View.GONE);
                        break;
                    case 1:
                        setTabColor(mFtType, mTvType);
                        mFtRefresh.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setTabColor(mFtUser, mTvUser);
                        mFtRefresh.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        mFragments = new ArrayList<>();
    }

    /**
     * 设置导航栏的选择状态和颜色
     *
     * @param fontTextView
     * @param textView
     */
    private void setTabColor(FontTextView fontTextView, TextView textView) {
        mFtHome.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        mTvHome.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        mFtType.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        mTvType.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        mFtUser.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        mTvUser.setTextColor(UIUtil.getColor(R.color.tab_bar_normal));
        fontTextView.setTextColor(UIUtil.getColor(R.color.tab_bar_selected));
        textView.setTextColor(UIUtil.getColor(R.color.tab_bar_selected));

    }

    @OnClick({R.id.ll_home, R.id.ll_type, R.id.ll_user, R.id.ft_search, R.id.ft_hot, R.id.ft_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                mViewPager.setCurrentItem(0);
                setTabColor(mFtHome, mTvHome);
                break;
            case R.id.ll_type:
                mViewPager.setCurrentItem(1);
                setTabColor(mFtType, mTvType);
                break;
            case R.id.ll_user:
                mViewPager.setCurrentItem(2);
                setTabColor(mFtUser, mTvUser);
                break;
            case R.id.ft_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.ft_hot:
                startActivity(new Intent(this, HotActivity.class));
                break;
            case R.id.ft_refresh:
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        ((HomeFragment) mFragments.get(0)).onRefresh();
                        break;
                    case 1:
                        ((TypeFragment) mFragments.get(1)).onRefresh();
                        break;
                    case 2:
                        ((UserFragment) mFragments.get(2)).refreshData();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public List<android.support.v4.app.Fragment> getFragments() {
        return mFragments;
    }
}
