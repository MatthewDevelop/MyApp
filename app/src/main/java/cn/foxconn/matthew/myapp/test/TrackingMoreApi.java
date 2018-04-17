package cn.foxconn.matthew.myapp.test;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * @author:Matthew
 * @date:2018/4/16
 * @email:guocheng0816@163.com
 */
public interface TrackingMoreApi {
    @Headers({"Content-Type:application/json", "Lang:en"})
    @GET("carriers")
    Observable<CarrierBean> queryCarriers(@Header("Trackingmore-Api-Key") String key);
}
