package cn.foxconn.matthew.myapp.wanandroid.model;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.TypeTagVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.BaseRxObserverHelper;

/**
 * @author:Matthew
 * @date:2018/3/6
 * @email:guocheng0816@163.com
 */

public interface DataModel {

    void getHomeDataList(int page, LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);

    void getBannerData(LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<List<BannerBean>> subscriber);

    void getTagData(LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<List<TypeTagVO>> subscriber);

    void getTypeDataById(int page, int cid, LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);

    void toLogin(String username, String password, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<UserBean> subscriber);

    void toRegister(String username, String password, String rePassword, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<UserBean> subscriber);

    void collectArticleInHomeList(int id, BaseRxObserverHelper<String> subscriber);

    void unCollectArticleInHomeList(int id, BaseRxObserverHelper<String> subscriber);

    void unCollectArticle(int id, int originId, BaseRxObserverHelper<String> subscriber);

    void getCollectList(int page, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);

    void getHotKeyList(LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<List<HotKeyBean>> subscriber);

    void getSearchData(int page, String keyword, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);
}
