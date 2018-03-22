package cn.foxconn.matthew.myapp.wanandroid.base;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class BasePresenter<V,R> {

    protected Reference<V> mReference;
    private LifecycleProvider<R> mProvider;
    public void attachView(V view){
        mReference=new WeakReference(view);
    }

    public BasePresenter(LifecycleProvider<R> provider) {
        mProvider=provider;
    }

    public LifecycleProvider<R> getProvider(){
        return mProvider;
    }


    protected V getView(){
        return mReference.get();
    }

    public boolean isAttached(){
        return mReference!=null&&mReference.get()!=null;
    }

    public void detachView(){
        if(mReference!=null){
            mReference.clear();
            mReference=null;
        }
    }
}
