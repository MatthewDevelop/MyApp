package cn.foxconn.matthew.myapp.wanandroid.model;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.TypeTagVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxSubscribeHelper;

/**
 * @author:Matthew
 * @date:2018/3/6
 * @email:guocheng0816@163.com
 */

public interface DataModel {

    void getHomeDataList(int page, RxSubscribeHelper<ArticleListVO> subscriber);

    void getBannerData(RxSubscribeHelper<List<BannerBean>> subscriber);

    void getTagData(RxSubscribeHelper<List<TypeTagVO>> subscriber);

    void getTypeDataById(int page, int cid, RxSubscribeHelper<ArticleListVO> subscriber);

    void toLogin(String username, String password, RxSubscribeHelper<UserBean> subscriber);

    void toRegister(String username, String password, String rePassword, RxSubscribeHelper<UserBean> subscriber);

    void collectArticleInHomeList(int id, RxSubscribeHelper<String> subscriber);

    void unCollectArticleInHomeList(int id, RxSubscribeHelper<String> subscriber);

    void unCollectArticle(int id, int originId, RxSubscribeHelper<String> subscriber);

    void getCollectList(int page, RxSubscribeHelper<ArticleListVO> subscriber);

    void getHotKeyList(RxSubscribeHelper<List<HotKeyBean>> subscriber);

    void getSearchData(int page,String keyword,RxSubscribeHelper<ArticleListVO> subscriber);
}
