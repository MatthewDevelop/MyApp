package cn.foxconn.matthew.myapp.wanandroid.helper;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 自定义subscriber，减少不必要回掉
 *
 * @author:Matthew
 * @date:2018/3/9
 * @email:guocheng0816@163.com
 */

public abstract class BaseRxObserverHelper<T> extends DisposableObserver<T> {

    /**
     * onStart方法总是在订阅的线程执行，即observerOn指定的线程，指定AndroidSchedulers.mainThread()时，可以在onStart方法中更新UI
     */
    /**
     * onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。
     * 这是一个可选方法，默认情况下它的实现为空。
     * 需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行）， onStart() 就不适用了，
     * 因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法
     */

    @Override
    protected void onStart() {
        super.onStart();
        start();
    }

    @Override
    public void onComplete() {
        completed();
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
            error("请求超时，稍后再试");
        } else if (e instanceof ConnectException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException) {
            error("网络异常，稍后再试");
        } else if (e instanceof HttpException) {
            error("服务器异常，稍后再试");
        } else if (e instanceof NullPointerException) {
            //特殊处理
            next();
        } else {
            //error("请求失败，稍后再试");
            error(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        next(t);
    }

    /**
     * 请求完成回调方法
     */
    protected void completed() {

    }

    /**
     * 提交订阅时的回调方法
     */
    protected void start() {

    }

    /**
     * {
     * "data": null,
     * "errorCode": 0,
     * "errorMsg": ""
     * }
     * <p>
     * 针对收藏与取消收藏接口进行特殊处理，成功，但返回的data为空，observer会抛出空指针
     */
    protected void next() {

    }

    /**
     * 请求成功回调方法
     *
     * @param t
     */
    protected abstract void next(T t);

    /**
     * 请求出错回调方法
     *
     * @param message
     */
    protected abstract void error(String message);
}
