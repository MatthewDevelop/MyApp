package cn.foxconn.matthew.myapp.expressinquiry.api;

import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public interface ExpressService {

    /**
     * 查询快递信息
     *
     * @param companyCode
     * @param postId
     * @return
     */
    @GET("query")
    Observable<ExpressResponseData> queryExpressInfo(@Query("type") String companyCode, @Query("postid") String postId);

    /**
     * 查询快递信息
     *
     * @param url
     * @return
     */
    @GET
    Observable<ExpressResponseData> queryExpressInfo_(@Url String url);
}
