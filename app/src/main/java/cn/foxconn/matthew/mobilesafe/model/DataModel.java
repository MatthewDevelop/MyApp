package cn.foxconn.matthew.mobilesafe.model;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import cn.foxconn.matthew.mobilesafe.bean.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.TypeTagVO;
import rx.Subscriber;

/**
 * @author:Matthew
 * @date:2018/3/6
 * @email:guocheng0816@163.com
 */

public interface DataModel {

    void getHomeDataList(int page,Subscriber<ResponseData<ArticleListVO>> subscriber);

    void getBannerData(Subscriber<ResponseData<List<BannerBean>>> subscriber);

    void getTagData(Subscriber<ResponseData<List<TypeTagVO>>> subscriber);

    void getTypeDataById(int page,int cid,Subscriber<ResponseData<ArticleListVO>> subscriber);
}
