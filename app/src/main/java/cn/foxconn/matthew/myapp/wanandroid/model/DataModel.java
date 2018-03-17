package cn.foxconn.matthew.myapp.wanandroid.model;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.TypeTagVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxObserverHelper;

/**
 * @author:Matthew
 * @date:2018/3/6
 * @email:guocheng0816@163.com
 */

public interface DataModel {

    void getHomeDataList(int page, RxObserverHelper<ArticleListVO> subscriber);

    void getBannerData(RxObserverHelper<List<BannerBean>> subscriber);

    void getTagData(RxObserverHelper<List<TypeTagVO>> subscriber);

    void getTypeDataById(int page, int cid, RxObserverHelper<ArticleListVO> subscriber);

    void toLogin(String username, String password, RxObserverHelper<UserBean> subscriber);

    void toRegister(String username, String password, String rePassword, RxObserverHelper<UserBean> subscriber);

    void collectArticleInHomeList(int id, RxObserverHelper<String> subscriber);

    void unCollectArticleInHomeList(int id, RxObserverHelper<String> subscriber);

    void unCollectArticle(int id, int originId, RxObserverHelper<String> subscriber);

    void getCollectList(int page, RxObserverHelper<ArticleListVO> subscriber);

    void getHotKeyList(RxObserverHelper<List<HotKeyBean>> subscriber);

    void getSearchData(int page,String keyword,RxObserverHelper<ArticleListVO> subscriber);
}
