package cn.foxconn.matthew.myapp.wanandroid.presenter;

import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxObserverHelper;
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
        mDataModel.getHomeDataList(mCurrentPage, getProvider(),new RxObserverHelper<ArticleListVO>() {

            @Override
            protected void _onCompleted() {
                super._onCompleted();
                getView().showRefreshView(false);
            }

            @Override
            protected void _onSubscribe() {
                super._onSubscribe();
                getView().showRefreshView(true);
            }

            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().getRefreshDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                Log.e(TAG, "getRefreshData _onError: " + message);
                getView().showRefreshView(false);
                getView().getRefreshDataFailed(message);
            }
        });
    }

    /**
     * 刷新首页轮播图
     */
    public void getBannerData() {
        mDataModel.getBannerData(getProvider(),new RxObserverHelper<List<BannerBean>>() {
            @Override
            protected void _onNext(List<BannerBean> bannerBeans) {
                getView().getBannerDataSuccess(bannerBeans);
            }

            @Override
            protected void _onError(String message) {
                getView().getRefreshDataFailed(message);
            }
        });
    }


    /**
     * 加载更多数据
     */
    public void getMoreData() {
        mCurrentPage = mCurrentPage + 1;
        mDataModel.getHomeDataList(mCurrentPage, getProvider(),new RxObserverHelper<ArticleListVO>() {
            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().getMoreDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                getView().getMoreDataFailed(message);
            }
        });
    }
}
