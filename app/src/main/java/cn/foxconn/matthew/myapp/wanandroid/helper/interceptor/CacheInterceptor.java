package cn.foxconn.matthew.myapp.wanandroid.helper.interceptor;

import android.util.Log;

import java.io.IOException;

import cn.foxconn.matthew.myapp.utils.NetworkUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:Matthew
 * @date:2018/4/18
 * @email:guocheng0816@163.com
 */
public class CacheInterceptor implements Interceptor {
    private static final String TAG = "CacheInterceptor";

    /**
     * 服务器不支持缓存，通过添加此拦截器达到缓存的目的
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取请求
        Request request = chain.request();
        //没有网络的时候强制使用缓存
        if (!NetworkUtil.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.e(TAG, "intercept: no network");
        }
        Response response = chain.proceed(request);
        if (NetworkUtil.isConnected()) {
            int maxTime = 0;
            return response.newBuilder()
                    //Pragma:no-cache 在http/1.1协议中，含义和Cache-Control:no-cache相同，确保配置生效
                    .removeHeader("Pragma")
                    //设置为0表示不缓存
                    .header("Cache-Control", "public,max-age=" + maxTime)
                    .build();
        } else {
            int maxTime = 4 * 24 * 60 * 60;
            //设置无网络的缓存时间
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //缓存30天
                    .header("Cache-Control", "public,only-if-cache,max-stale=" + maxTime)
                    .build();
        }
    }
}
