package cn.foxconn.matthew.myapp.wanandroid.helper;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author:Matthew
 * @date:2018/3/9
 * @email:guocheng0816@163.com
 */

/**
 * 默认的rx线程转换，在io线程发起请求，回掉给主线程。
 */
public class RxSchedulersHelper {

    public static <T>Observable.Transformer<T,T> defaultTransformer(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        //指subscribe发生在IO线程，即事件产生的线程
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        //指定subscriber所运行的线程，事件消费的线程。subscriber发生在UI线程，故可以在onStart方法中更新UI
                        .observeOn(AndroidSchedulers.mainThread());

            }
        };
    }
}
