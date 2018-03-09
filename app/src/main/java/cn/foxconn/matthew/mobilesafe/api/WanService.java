package cn.foxconn.matthew.mobilesafe.api;


import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import cn.foxconn.matthew.mobilesafe.bean.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.TypeTagVO;
import retrofit2.http.GET;
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
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ResponseData<ArticleListVO>> getHomeAtricleList(@Path("page") int page);

    /**
     * 获取首页轮播图信息
     * @return
     */
    @GET("banner/json")
    Observable<ResponseData<List<BannerBean>>> getHomeBannerList();

    /**
     * 获取文章分类信息
     * @return
     */
    @GET("tree/json")
    Observable<ResponseData<List<TypeTagVO>>> getTagData();

    @GET("article/list/{page}/json")
    Observable<ResponseData<ArticleListVO>> getTypeDataById(@Path("page") int page,@Query("cid") int cid);
}