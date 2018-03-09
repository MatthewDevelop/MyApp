package cn.foxconn.matthew.mobilesafe.model;


import java.util.List;

import cn.foxconn.matthew.mobilesafe.api.WanService;
import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import cn.foxconn.matthew.mobilesafe.bean.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.TypeTagVO;
import cn.foxconn.matthew.mobilesafe.helper.RetrofitServiceManager;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author:Matthew
 * @date:2018/3/6
 * @email:guocheng0816@163.com
 */

public class DataModelImpl implements DataModel {


    @Override
    public void getHomeDataList(int page, Subscriber<ResponseData<ArticleListVO>> subscriber) {
        RetrofitServiceManager.getInstance().create(WanService.class)
                .getHomeAtricleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    @Override
    public void getBannerData(Subscriber<ResponseData<List<BannerBean>>> subscriber) {
        RetrofitServiceManager.getInstance().create(WanService.class)
                .getHomeBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    @Override
    public void getTagData(Subscriber<ResponseData<List<TypeTagVO>>> subscriber) {
        RetrofitServiceManager.getInstance().create(WanService.class)
                .getTagData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getTypeDataById(int page,int cid,Subscriber<ResponseData<ArticleListVO>> subscriber) {
        RetrofitServiceManager.getInstance().create(WanService.class)
                .getTypeDataById(page,cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
