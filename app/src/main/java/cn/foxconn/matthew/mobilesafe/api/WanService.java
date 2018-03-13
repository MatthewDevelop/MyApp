package cn.foxconn.matthew.mobilesafe.api;


import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import cn.foxconn.matthew.mobilesafe.bean.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.bean.pojo.UserBean;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.TypeTagVO;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

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
     * @param username
     * @param password
     * @param rePassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<ResponseData<UserBean>> toRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String rePassword);
}
