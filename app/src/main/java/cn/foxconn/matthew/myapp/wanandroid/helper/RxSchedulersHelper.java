package cn.foxconn.matthew.myapp.wanandroid.helper;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:Matthew
 * @date:2018/3/9
 * @email:guocheng0816@163.com
 */

/**
 * 默认的rx线程转换，在io线程发起请求，回掉给主线程。
 */
public class RxSchedulersHelper {

    public static <T> ObservableTransformer<T, T> defaultTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream//指subscribe发生在IO线程，即事件产生的线程
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        //指定subscriber所运行的线程，事件消费的线程。subscriber发生在UI线程，故可以在onStart方法中更新UI
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
