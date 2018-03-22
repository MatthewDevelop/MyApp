package cn.foxconn.matthew.myapp.wanandroid.api;


import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.ResponseData;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.TypeTagVO;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public interface WanService {
    /**
     * 获取首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ResponseData<ArticleListVO>> getHomeAtricleList(@Path("page") int page);

    /**
     * 获取首页轮播图信息
     *
     * @return
     */
    @GET("banner/json")
    Observable<ResponseData<List<BannerBean>>> getHomeBannerList();

    /**
     * 获取文章分类标签信息
     *
     * @return
     */
    @GET("tree/json")
    Observable<ResponseData<List<TypeTagVO>>> getTagData();

    /**
     * 根据标签获取文章列表信息
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ResponseData<ArticleListVO>> getTypeDataById(@Path("page") int page, @Query("cid") int cid);

    /**
     * 添加@FormUrlEncoded注释后就至少需要一个@Field参数
     * <p>
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<ResponseData<UserBean>> toLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param rePassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<ResponseData<UserBean>> toRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String rePassword);

    /**
     * 主页收藏文章
     *
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<ResponseData<String>> collectArticleInHomeList(@Path("id") int id);

    /**
     * 主页抓取的数据和收藏列表抓取的数据不相同，主页的数据缺少originId字段，且取消收藏的url也不一样
     * 主页取消收藏
     *
     * @param id       文章id
     * @param originId 主页数据无文章的originId故默认-1
     * @return
     */
    @FormUrlEncoded
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ResponseData<String>> unCollectArticleInHomeList(@Path("id") int id, @Field("originId") int originId);

    /**
     * 我的收藏页面取消收藏
     *
     * @param id       文章id
     * @param originId
     * @return
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<ResponseData<String>> unCollectArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 获取文章收藏列表
     *
     * @param page
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<ResponseData<ArticleListVO>> getCollectList(@Path("page") int page);

    /**
     * 获取搜索热门关键词
     *
     * @return
     */
    @GET("hotkey/json")
    Observable<ResponseData<List<HotKeyBean>>> getHotKeyList();

    /**
     * 根据关键词获取搜索结果
     *
     * @param page
     * @param keyword
     * @return
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<ResponseData<ArticleListVO>> getSearchData(@Path("page") int page, @Field("k") String keyword);
}
