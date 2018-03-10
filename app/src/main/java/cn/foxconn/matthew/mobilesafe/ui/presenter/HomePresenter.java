package cn.foxconn.matthew.mobilesafe.ui.presenter;

import android.util.Log;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.api.WanService;
import cn.foxconn.matthew.mobilesafe.helper.RetrofitServiceManager;
import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import cn.foxconn.matthew.mobilesafe.bean.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.helper.RxSubscribeHelper;
import cn.foxconn.matthew.mobilesafe.model.DataModel;
import cn.foxconn.matthew.mobilesafe.model.DataModelImpl;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.HomeView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class HomePresenter extends BasePresenter<HomeView> {
    private static final String TAG = "HomePresenter";

    DataModel mDataModel;
    private int mCurrentPage;

    public HomePresenter(){
        mDataModel=new DataModelImpl();
    }

    /**
     * 刷新首页列表
     */
    public void getRefreshData() {
        mCurrentPage=0;
        mDataModel.getHomeDataList(mCurrentPage, new RxSubscribeHelper<ArticleListVO>() {

            @Override
            protected void _onCompleted() {
                super._onCompleted();
                getView().showRefreshView(false);
            }

            @Override
            protected void _onStart() {
                super._onStart();
                getView().showRefreshView(true);
            }

            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                Log.e(TAG, "_onNext: "+articleListVO.toString() );
                getView().getRefreshDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                Log.e(TAG, "_onError: "+message );
                getView().getDataError(message);
            }
        });
    }

    /**
     * 刷新首页轮播图
     */
    public void getBannerData() {
        mDataModel.getBannerData(new RxSubscribeHelper<List<BannerBean>>() {
            @Override
            protected void _onNext(List<BannerBean> bannerBeans) {
                getView().getBannerDataSuccess(bannerBeans);
            }

            @Override
            protected void _onError(String message) {
                getView().getDataError(message);
            }
        });
    }


    /**
     * 加载更多数据
     */
    public void getMoreData(){
        mCurrentPage=mCurrentPage+1;
        mDataModel.getHomeDataList(mCurrentPage, new RxSubscribeHelper<ArticleListVO>() {
            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().getMoreDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                getView().getDataError(message);
            }
        });
    }
}
