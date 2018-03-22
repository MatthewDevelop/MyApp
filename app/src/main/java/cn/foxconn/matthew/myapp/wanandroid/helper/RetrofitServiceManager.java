package cn.foxconn.matthew.myapp.wanandroid.helper;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import cn.foxconn.matthew.myapp.app.App;
import cn.foxconn.matthew.myapp.app.AppConst;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.foxconn.matthew.myapp.app.AppConst.BASE_URL;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIMEOUT=5;
    private static final int DEFAULT_READ_TIMEOUT=10;
    private Retrofit mRetrofit;
    private static ClearableCookieJar mCookieJar = null;

    private RetrofitServiceManager() {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIMEOUT,TimeUnit.SECONDS);
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if(AppConst.DEBUG) {
            builder.addInterceptor(logging);
        }
        mCookieJar = new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(App.getContext()));
        builder.cookieJar(mCookieJar);
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void clearCookie(){
        //清除登录cookie
        mCookieJar.clear();
//        mCookieJar.clearSession();
    }


    private static class SingletonHolder{
        private static final RetrofitServiceManager MANAGER =new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitServiceManager getInstance(){
        return SingletonHolder.MANAGER;
    }

    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }
}
