package cn.foxconn.matthew.myapp.wanandroid.helper.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:Matthew
 * @date:2018/4/18
 * @email:guocheng0816@163.com
 */
public class CacheInterceptor implements Interceptor {

    /**
     * 服务器不支持缓存，通过添加此拦截器达到缓存的目的
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response newResponse = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //缓存30天
                .header("Cache-Control", request.cacheControl().toString())
                .build();
        return newResponse;
    }
}
