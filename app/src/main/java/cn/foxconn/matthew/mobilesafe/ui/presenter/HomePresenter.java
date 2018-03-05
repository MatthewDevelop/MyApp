package cn.foxconn.matthew.mobilesafe.ui.presenter;

import android.util.Log;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.api.WanService;
import cn.foxconn.matthew.mobilesafe.helper.RetrofitServiceManager;
import cn.foxconn.matthew.mobilesafe.model.ResponseData;
import cn.foxconn.matthew.mobilesafe.model.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.model.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.HomeView;
import rx.Scheduler;
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

    private int mCurrentPage;
    /**
     * 刷新首页列表
     */
    public void getRefreshData() {
        mCurrentPage=0;
        RetrofitServiceManager.getInstance().create(WanService.class)
                .getHomeAtricleList(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseData<ArticleListVO>>() {
                    @Override
                    public void onStart() {
                        Log.e(TAG, "onStart: " );
                        getView().showRefreshView(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " );
                        getView().showRefreshView(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(TAG, "onError: " +e.getMessage());
                        getView().getDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseData<ArticleListVO> articleListVOResponseData) {
                        getView().getRefreshDataSuccess(articleListVOResponseData.getData().getDatas());
                    }
                });

    }

    /**
     * 刷新首页轮播图
     */
    public void getBannerData() {
        RetrofitServiceManager.getInstance().create(WanService.class)
                .getHomeBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseData<List<BannerBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().getDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseData<List<BannerBean>> listResponseData) {
                        getView().getBannerDataSuccess(listResponseData.getData());
                    }
                });
    }


    /**
     * 加载更多数据
     */
    public void getMoreData(){
        mCurrentPage=mCurrentPage+1;
        RetrofitServiceManager.getInstance().create(WanService.class)
                .getHomeAtricleList(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseData<ArticleListVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().getDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseData<ArticleListVO> articleListVOResponseData) {
                        getView().getMoreDataSuccess(articleListVOResponseData.getData().getDatas());
                    }
                });
    }
}
