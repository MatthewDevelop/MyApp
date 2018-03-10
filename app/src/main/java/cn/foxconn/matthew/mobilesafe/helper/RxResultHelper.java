package cn.foxconn.matthew.mobilesafe.helper;

/**
 * @author:Matthew
 * @date:2018/3/9
 * @email:guocheng0816@163.com
 */


import android.util.Log;

import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import rx.Observable;
import rx.functions.Func1;

/**
 * 处理请求结果的数据剥离
 */
public class RxResultHelper {
    private static final String TAG = "RxResultHelper";
    private static final int RESPONSE_SUCCESS_CODE = 0; //请求成功时返回值为0
    private static final int RESPONSE_ERROR_CODE = -1;

    public static <T>Observable.Transformer<ResponseData<T>,T> handleResult(){
        return new Observable.Transformer<ResponseData<T>,T>(){

            @Override
            public Observable<T> call(final Observable<ResponseData<T>> responseDataObservable) {
                return responseDataObservable.flatMap(new Func1<ResponseData<T>, Observable<T>>() {

                    @Override
                    public Observable<T> call(ResponseData<T> tResponseData) {
                        Log.e(TAG, "call: "+tResponseData.getErrorCode() );
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
