package cn.foxconn.matthew.myapp;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.foxconn.matthew.myapp.expressinquiry.api.ExpressService;
import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;
import cn.foxconn.matthew.myapp.wanandroid.api.WanService;
import cn.foxconn.matthew.myapp.wanandroid.bean.ResponseData;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.TypeTagVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.interceptor.CustomInterceptor;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void MD5Test() {
        //计算文件或字符串的特征码，不可逆，任何MD5都是32位,
        String str = "111";
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            StringBuffer sb=new StringBuffer();
            for (byte b : digest) {
                //获取字节低八位有效值
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if(hexString.length()<2)
                    hexString="0"+hexString;
                System.out.println(hexString);
                sb.append(hexString);
            }
            System.out.println(sb);
            System.out.println(sb.length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void APITest(){
        String BASE_URL = "http://www.kuaidi100.com/";
//        String BASE_URL = "http://wanandroid.com/";
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        //添加公共拦截器
        builder.addInterceptor(new CustomInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WanService movieService = retrofit.create(WanService.class);
        ExpressService expressService = retrofit.create(ExpressService.class);
        boolean test=true;
        if(test){
            System.out.println("hello");
            expressService.queryExpressInfo("yuantong","888590141663223471").subscribe(new Observer<ExpressResponseData>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ExpressResponseData expressResponseData) {
                    System.out.println(expressResponseData.toString());
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }else {
            movieService.getTagData().subscribe(new Observer<ResponseData<List<TypeTagVO>>>() {
                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ResponseData<List<TypeTagVO>> listResponseData) {
                    System.out.println(listResponseData.toString());
                }
            });
            movieService.getHomeBannerList().subscribe(new Observer<ResponseData<List<BannerBean>>>() {

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ResponseData<List<BannerBean>> listResponseData) {
                    System.out.println(listResponseData.toString());
                }
            });
            movieService.getHomeArticleList(5).subscribe(new Observer<ResponseData<ArticleListVO>>() {

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ResponseData<ArticleListVO> articleListVOResponseData) {
                    System.out.println(articleListVOResponseData.toString());
                }
            });
        }
    }
}