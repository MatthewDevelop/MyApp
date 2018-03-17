package cn.foxconn.matthew.myapp.wanandroid.helper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author:Matthew
 * @date:2018/3/9
 * @email:guocheng0816@163.com
 */

/**
 * 自定义subscriber，减少不必要回掉
 *
 * @param <T>
 */
public abstract class RxObserverHelper<T> implements Observer<T> {

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
    public void onSubscribe(Disposable d) {
        _onSubscribe();
    }

    @Override
    public void onComplete() {
        _onCompleted();
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            _onError("请求超时，稍后再试");
        } else if (e instanceof HttpException) {
            _onError("服务器异常，稍后再试");
        } else {
            //_onError("请求失败，稍后再试");
            _onError(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected void _onCompleted() {

    }

    protected void _onSubscribe() {

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
