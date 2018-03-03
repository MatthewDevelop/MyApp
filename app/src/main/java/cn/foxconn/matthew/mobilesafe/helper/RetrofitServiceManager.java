package cn.foxconn.matthew.mobilesafe.helper;

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

    private Retrofit mRetrofit;

    private RetrofitServiceManager() {
        //创建Retrofit
        mRetrofit=new Retrofit.Builder()
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
