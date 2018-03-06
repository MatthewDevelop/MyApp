package cn.foxconn.matthew.mobilesafe.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.foxconn.matthew.mobilesafe.app.AppConst.BASE_URL;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIMEOUT=5;
    private static final int DEFAULT_READ_TIMEOUT=10;
    private Retrofit mRetrofit;

    private RetrofitServiceManager() {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIMEOUT,TimeUnit.SECONDS);
        // 添加公共参数拦截器
        /*HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform","android")
                .addHeaderParams("userToken","1234343434dfdfd3434")
                .addHeaderParams("userId","123445")
                .build();*/

        //创建Retrofit
        mRetrofit=new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private static class SingletonHolder{
        private static final RetrofitServiceManager manager=new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitServiceManager getInstance(){
        return SingletonHolder.manager;
    }

    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }
}
