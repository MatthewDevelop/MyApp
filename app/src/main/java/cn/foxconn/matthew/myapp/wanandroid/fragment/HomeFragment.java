package cn.foxconn.matthew.myapp.wanandroid.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.wanandroid.helper.ImageLoaderManager;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.activity.WebViewActivity;
import cn.foxconn.matthew.myapp.wanandroid.adapter.ArticleListAdapter;
import cn.foxconn.matthew.myapp.wanandroid.base.BaseFragment;
import cn.foxconn.matthew.myapp.wanandroid.presenter.HomePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.HomeView;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class HomeFragment extends BaseFragment<HomeView,HomePresenter>
                        implements HomeView,SwipeRefreshLayout.OnRefreshListener
                        ,BaseQuickAdapter.RequestLoadMoreListener{
    private static final String TAG = "HomeFragment";
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ArticleListAdapter mAdapter;
    BGABanner mBGABanner;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter=new ArticleListAdapter(getContext(),null);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);

        //添加轮播图布局
        View headView=View.inflate(getContext(),R.layout.layout_banner,null);
        mBGABanner=headView.findViewById(R.id.bgaBanner);
        mAdapter.addHeaderView(headView);
        onRefresh();
    }

    @Override
    protected void initDate() {
        super.initDate();
    }

    @Override
    protected int getContentResId() {
        return R.layout.frag_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onRefresh() {
        Log.e(TAG, "onRefresh: " );
        mPresenter.getBannerData();
        mPresenter.getRefreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreData();
    }

    @Override
    public void getBannerDataSuccess(List<BannerBean> data) {
        //设置轮播图
        mBGABanner.setData(R.layout.item_banner,data,null);
        mBGABanner.setAdapter(new BGABanner.Adapter<View,BannerBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, BannerBean model, int position) {
                ImageView imageView=itemView.findViewById(R.id.imageView);
                ImageLoaderManager.LoadImage(getContext(),model.getImagePath(),imageView);
            }
        });
        mBGABanner.setDelegate(new BGABanner.Delegate<View,BannerBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, BannerBean model, int position) {
                WebViewActivity.runActivity(getContext(),model.getUrl());
            }
        });
    }

    @Override
    public void showRefreshView(final Boolean refresh) {
        //mSwipeRefreshLayout.setRefreshing(refresh);
        //保证首次加载数据时，有加载动画效果
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(refresh);
            }
        });
    }

    @Override
    public void getRefreshDataFailed(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getRefreshDataSuccess(List<ArticleBean> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void getMoreDataSuccess(List<ArticleBean> data) {
        if (data.size()!=0){
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
        }else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void getMoreDataFailed(String message) {
        mAdapter.loadMoreComplete();
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
