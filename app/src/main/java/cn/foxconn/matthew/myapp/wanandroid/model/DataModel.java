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
    /**
     * 获取主页的文章信息
     *
     * @param page
     * @param provider
     * @param subscriber
     */
    void getHomeDataList(int page, LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);

    /**
     * 获取轮播图信息
     *
     * @param provider
     * @param subscriber
     */
    void getBannerData(LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<List<BannerBean>> subscriber);

    /**
     * 获取标签信息
     *
     * @param provider
     * @param subscriber
     */
    void getTagData(LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<List<TypeTagVO>> subscriber);

    /**
     * 根据标签抓取数据
     *
     * @param page
     * @param cid
     * @param provider
     * @param subscriber
     */
    void getTypeDataById(int page, int cid, LifecycleProvider<FragmentEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param provider
     * @param subscriber
     */
    void toLogin(String username, String password, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<UserBean> subscriber);

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param rePassword
     * @param provider
     * @param subscriber
     */
    void toRegister(String username, String password, String rePassword, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<UserBean> subscriber);

    /**
     * 主页中个收藏文章
     *
     * @param id
     * @param subscriber
     */
    void collectArticleInHomeList(int id, BaseRxObserverHelper<String> subscriber);

    /**
     * 主页取消收藏文章
     *
     * @param id
     * @param subscriber
     */
    void unCollectArticleInHomeList(int id, BaseRxObserverHelper<String> subscriber);

    /**
     * 收藏页取消收藏
     *
     * @param id
     * @param originId
     * @param subscriber
     */
    void unCollectArticle(int id, int originId, BaseRxObserverHelper<String> subscriber);

    /**
     * 获取收藏列表
     *
     * @param page
     * @param provider
     * @param subscriber
     */
    void getCollectList(int page, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);

    /**
     * 获取热搜关键词
     *
     * @param provider
     * @param subscriber
     */
    void getHotKeyListInActivity(LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<List<HotKeyBean>> subscriber);


    /**
     * 获取根据关键词的搜索结果
     *
     * @param page
     * @param keyword
     * @param provider
     * @param subscriber
     */
    void getSearchData(int page, String keyword, LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<ArticleListVO> subscriber);

    /**
     * 获取热搜关键词
     *
     * @param provider
     * @param subscriber
     */
    void getCommonWebsitList(LifecycleProvider<ActivityEvent> provider, BaseRxObserverHelper<List<HotKeyBean>> subscriber);
}
