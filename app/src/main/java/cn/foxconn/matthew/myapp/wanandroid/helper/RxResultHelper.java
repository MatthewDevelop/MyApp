package cn.foxconn.matthew.myapp.wanandroid.helper;




import android.util.Log;

import cn.foxconn.matthew.myapp.wanandroid.bean.ResponseData;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 处理请求结果的数据剥离
 * @author:Matthew
 * @date:2018/3/9
 * @email:guocheng0816@163.com
 */
public class RxResultHelper {
    private static final String TAG = "RxResultHelper";
    /**
     * 请求成功时返回值为0,失败返回-1
     */
    private static final int RESPONSE_SUCCESS_CODE = 0;
    private static final int RESPONSE_ERROR_CODE = -1;


    public static <T>ObservableTransformer<ResponseData<T>,T> handleResult(){
        return new ObservableTransformer<ResponseData<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseData<T>> upstream) {
                return upstream.flatMap(new Function<ResponseData<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(ResponseData<T> tResponseData) throws Exception {
                        if (tResponseData.getErrorCode()==RESPONSE_SUCCESS_CODE){
                            //请求成功时将数据部分剥离返回
                            Log.e(TAG, "call: " +tResponseData.toString());
                            return Observable.just(tResponseData.getData());
                        }else if(tResponseData.getErrorCode()==RESPONSE_ERROR_CODE){
                            //失败时将错误原因返回
                            return Observable.error(new Exception(tResponseData.getErrorMsg()));
                        }else {
                            return Observable.empty();
                        }
                    }
                });
            }
        };
    }
}
