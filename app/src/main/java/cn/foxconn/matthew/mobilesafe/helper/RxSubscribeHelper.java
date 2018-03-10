package cn.foxconn.matthew.mobilesafe.helper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * @author:Matthew
 * @date:2018/3/9
 * @email:guocheng0816@163.com
 */

/**
 * 自定义subscriber，减少不必要回掉
 * @param <T>
 */
public abstract class RxSubscribeHelper<T> extends Subscriber<T> {



    @Override
    public void onStart() {
        super.onStart();
        _onStart();
    }

    @Override
    public void onCompleted() {
        //取消订阅
        if(!isUnsubscribed()){
            unsubscribe();
        }
        _onCompleted();

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException||e instanceof ConnectException){
            _onError("请求超时，稍后再试");
        }else if(e instanceof HttpException){
            _onError("服务器异常，稍后再试");
        }else {
            _onError("请求失败，稍后再试");
            _onError(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected void _onCompleted() {

    }

    protected void _onStart() {

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
