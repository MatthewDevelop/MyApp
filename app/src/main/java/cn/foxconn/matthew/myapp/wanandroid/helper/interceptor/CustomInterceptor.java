package cn.foxconn.matthew.myapp.wanandroid.helper.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:Matthew
 * @date:2018/3/10
 * @email:guocheng0816@163.com
 */

public class CustomInterceptor implements Interceptor {
    /**
     * 通过拦截器的方式添加请求参数
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        HttpUrl url=request.url().newBuilder().addQueryParameter("token", "tokenValue").build();
        request=request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
