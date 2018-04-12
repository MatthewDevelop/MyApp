package cn.foxconn.matthew.myapp.wanandroid.presenter;

import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.BaseRxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.HomeView;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class HomePresenter extends BasePresenter<HomeView,
        FragmentEvent> {
    private static final String TAG = "HomePresenter";

    DataModel mDataModel;
    private int mCurrentPage;

    public HomePresenter(LifecycleProvider provider) {
        super(provider);
        mDataModel = new DataModelImpl();
    }

    /**
     * 刷新首页列表
     */
    public void getRefreshData() {
        mCurrentPage = 0;
        mDataModel.getHomeDataList(mCurrentPage, getProvider(), new BaseRxObserverHelper<ArticleListVO>() {

            @Override
            protected void completed() {
                super.completed();
                getView().showRefreshView(false);
            }

            @Override
            protected void start() {
                super.start();
                getView().showRefreshView(true);
            }

            @Override
            protected void next(ArticleListVO articleListVO) {
                getView().getRefreshDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void error(String message) {
                Log.e(TAG, "getRefreshData error: " + message);
                getView().showRefreshView(false);
                getView().getRefreshDataFailed(message);
            }
        });
    }

    /**
     * 刷新首页轮播图
     */
    public void getBannerData() {
        mDataModel.getBannerData(getProvider(), new BaseRxObserverHelper<List<BannerBean>>() {
            @Override
            protected void next(List<BannerBean> bannerBeans) {
                getView().getBannerDataSuccess(bannerBeans);
            }

            @Override
            protected void error(String message) {
                getView().getRefreshDataFailed(message);
            }
        });
    }


    /**
     * 加载更多数据
     */
    public void getMoreData() {
        mCurrentPage = mCurrentPage + 1;
        mDataModel.getHomeDataList(mCurrentPage, getProvider(), new BaseRxObserverHelper<ArticleListVO>() {
            @Override
            protected void next(ArticleListVO articleListVO) {
                getView().getMoreDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void error(String message) {
                getView().getMoreDataFailed(message);
            }
        });
    }
}
