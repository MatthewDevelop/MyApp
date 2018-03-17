package cn.foxconn.matthew.myapp.wanandroid.model;


import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.api.WanService;
import cn.foxconn.matthew.myapp.wanandroid.bean.ResponseData;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.TypeTagVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.RetrofitServiceManager;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxResultHelper;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxSchedulersHelper;


/**
 * @author:Matthew
 * @date:2018/3/6
 * @email:guocheng0816@163.com
 */

public class DataModelImpl implements DataModel {

    private WanService mWanService;

    public DataModelImpl() {
        mWanService = RetrofitServiceManager.getInstance().create(WanService.class);
    }

    @Override
    public void getHomeDataList(int page, LifecycleProvider<FragmentEvent> provider, RxObserverHelper<ArticleListVO> subscriber) {
        mWanService.getHomeAtricleList(page)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .compose(provider.<ArticleListVO>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(subscriber);
    }


    @Override
    public void getBannerData(LifecycleProvider<FragmentEvent> provider, RxObserverHelper<List<BannerBean>> subscriber) {
        mWanService.getHomeBannerList()
                .compose(RxSchedulersHelper.<ResponseData<List<BannerBean>>>defaultTransformer())
                .compose(RxResultHelper.<List<BannerBean>>handleResult())
                .compose(provider.<List<BannerBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(subscriber);
    }

    @Override
    public void getTagData(LifecycleProvider<FragmentEvent> provider, RxObserverHelper<List<TypeTagVO>> subscriber) {
        mWanService.getTagData()
                .compose(RxSchedulersHelper.<ResponseData<List<TypeTagVO>>>defaultTransformer())
                .compose(RxResultHelper.<List<TypeTagVO>>handleResult())
                .compose(provider.<List<TypeTagVO>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(subscriber);
    }

    @Override
    public void getTypeDataById(int page, int cid, LifecycleProvider<FragmentEvent> provider, RxObserverHelper<ArticleListVO> subscriber) {
        mWanService.getTypeDataById(page, cid)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .compose(provider.<ArticleListVO>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(subscriber);
    }

    @Override
    public void toLogin(String username, String password, LifecycleProvider<ActivityEvent> provider, RxObserverHelper<UserBean> subscriber) {
        mWanService.toLogin(username, password)
                .compose(RxSchedulersHelper.<ResponseData<UserBean>>defaultTransformer())
                .compose(RxResultHelper.<UserBean>handleResult())
                .compose(provider.<UserBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(subscriber);
    }

    @Override
    public void toRegister(String username, String password, String rePassword, LifecycleProvider<ActivityEvent> provider, RxObserverHelper<UserBean> subscriber) {
        mWanService.toRegister(username, password, rePassword)
                .compose(RxSchedulersHelper.<ResponseData<UserBean>>defaultTransformer())
                .compose(RxResultHelper.<UserBean>handleResult())
                .compose(provider.<UserBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(subscriber);
    }

    @Override
    public void collectArticleInHomeList(int id, RxObserverHelper<String> subscriber) {
        mWanService.collectArticleInHomeList(id)
                .compose(RxSchedulersHelper.<ResponseData<String>>defaultTransformer())
                .compose(RxResultHelper.<String>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void unCollectArticleInHomeList(int id, RxObserverHelper<String> subscriber) {
        mWanService.unCollectArticleInHomeList(id, -1)
                .compose(RxSchedulersHelper.<ResponseData<String>>defaultTransformer())
                .compose(RxResultHelper.<String>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void unCollectArticle(int id, int originId, RxObserverHelper<String> subscriber) {
        mWanService.unCollectArticle(id, originId)
                .compose(RxSchedulersHelper.<ResponseData<String>>defaultTransformer())
                .compose(RxResultHelper.<String>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void getCollectList(int page, LifecycleProvider<ActivityEvent> provider, RxObserverHelper<ArticleListVO> subscriber) {
        mWanService.getCollectList(page)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .compose(provider.<ArticleListVO>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(subscriber);
    }


    @Override
    public void getHotKeyList(LifecycleProvider<ActivityEvent> provider, RxObserverHelper<List<HotKeyBean>> subscriber) {
        mWanService.getHotKeyList()
                .compose(RxSchedulersHelper.<ResponseData<List<HotKeyBean>>>defaultTransformer())
                .compose(RxResultHelper.<List<HotKeyBean>>handleResult())
                .compose(provider.<List<HotKeyBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(subscriber);
    }

    @Override
    public void getSearchData(int page, String keyword, LifecycleProvider<ActivityEvent> provider, RxObserverHelper<ArticleListVO> subscriber) {
        mWanService.getSearchData(page,keyword)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .compose(provider.<ArticleListVO>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(subscriber);
    }

}
