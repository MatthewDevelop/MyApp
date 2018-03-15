package cn.foxconn.matthew.myapp.wanandroid.model;


import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.api.WanService;
import cn.foxconn.matthew.myapp.wanandroid.bean.ResponseData;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.TypeTagVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.RetrofitServiceManager;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxResultHelper;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxSchedulersHelper;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxSubscribeHelper;


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
    public void getHomeDataList(int page, RxSubscribeHelper<ArticleListVO> subscriber) {
        mWanService.getHomeAtricleList(page)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(subscriber);
    }


    @Override
    public void getBannerData(RxSubscribeHelper<List<BannerBean>> subscriber) {
        mWanService.getHomeBannerList()
                .compose(RxSchedulersHelper.<ResponseData<List<BannerBean>>>defaultTransformer())
                .compose(RxResultHelper.<List<BannerBean>>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void getTagData(RxSubscribeHelper<List<TypeTagVO>> subscriber) {
        mWanService.getTagData()
                .compose(RxSchedulersHelper.<ResponseData<List<TypeTagVO>>>defaultTransformer())
                .compose(RxResultHelper.<List<TypeTagVO>>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void getTypeDataById(int page, int cid, RxSubscribeHelper<ArticleListVO> subscriber) {
        mWanService.getTypeDataById(page, cid)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void toLogin(String username, String password, RxSubscribeHelper<UserBean> subscriber) {
        mWanService.toLogin(username, password)
                .compose(RxSchedulersHelper.<ResponseData<UserBean>>defaultTransformer())
                .compose(RxResultHelper.<UserBean>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void toRegister(String username, String password, String rePassword, RxSubscribeHelper<UserBean> subscriber) {
        mWanService.toRegister(username, password, rePassword)
                .compose(RxSchedulersHelper.<ResponseData<UserBean>>defaultTransformer())
                .compose(RxResultHelper.<UserBean>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void collectArticleInHomeList(int id, RxSubscribeHelper<String> subscriber) {
        mWanService.collectArticleInHomeList(id)
                .compose(RxSchedulersHelper.<ResponseData<String>>defaultTransformer())
                .compose(RxResultHelper.<String>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void unCollectArticleInHomeList(int id, RxSubscribeHelper<String> subscriber) {
        mWanService.unCollectArticleInHomeList(id, -1)
                .compose(RxSchedulersHelper.<ResponseData<String>>defaultTransformer())
                .compose(RxResultHelper.<String>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void unCollectArticle(int id, int originId, RxSubscribeHelper<String> subscriber) {
        mWanService.unCollectArticle(id, originId)
                .compose(RxSchedulersHelper.<ResponseData<String>>defaultTransformer())
                .compose(RxResultHelper.<String>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void getCollectList(int page, RxSubscribeHelper<ArticleListVO> subscriber) {
        mWanService.getCollectList(page)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void getHotKeyList(RxSubscribeHelper<List<HotKeyBean>> subscriber) {
        mWanService.getHotKeyList()
                .compose(RxSchedulersHelper.<ResponseData<List<HotKeyBean>>>defaultTransformer())
                .compose(RxResultHelper.<List<HotKeyBean>>handleResult())
                .subscribe(subscriber);
    }

    @Override
    public void getSearchData(int page, String keyword,RxSubscribeHelper<ArticleListVO> subscriber) {
        mWanService.getSearchData(page,keyword)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>defaultTransformer())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(subscriber);
    }

}
