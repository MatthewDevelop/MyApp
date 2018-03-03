package cn.foxconn.matthew.mobilesafe.api;


import java.util.List;

import cn.foxconn.matthew.mobilesafe.model.ResponseData;
import cn.foxconn.matthew.mobilesafe.model.pojo.BannerBean;
import cn.foxconn.matthew.mobilesafe.model.pojoVO.ArticleListVO;
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

}
