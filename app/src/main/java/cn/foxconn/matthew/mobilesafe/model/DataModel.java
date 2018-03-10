package cn.foxconn.matthew.mobilesafe.model;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import cn.foxconn.matthew.mobilesafe.bean.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.TypeTagVO;
import cn.foxconn.matthew.mobilesafe.helper.RxSubscribeHelper;
import rx.Subscriber;

/**
 * @author:Matthew
 * @date:2018/3/6
 * @email:guocheng0816@163.com
 */

public interface DataModel {

    void getHomeDataList(int page,RxSubscribeHelper<ArticleListVO> subscriber);

    void getBannerData(RxSubscribeHelper<List<BannerBean>> subscriber);

    void getTagData(RxSubscribeHelper<List<TypeTagVO>> subscriber);

    void getTypeDataById(int page,int cid,RxSubscribeHelper<ArticleListVO> subscriber);
}
