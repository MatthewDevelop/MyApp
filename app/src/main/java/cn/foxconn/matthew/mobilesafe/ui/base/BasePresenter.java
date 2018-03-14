package cn.foxconn.matthew.mobilesafe.ui.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class BasePresenter<V> {

    protected Reference<V> mReference;

    public void attachView(V view){
        mReference=new WeakReference(view);
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
