package cn.foxconn.matthew.myapp.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public abstract class BaseFragment<V, T extends BasePresenter<V, FragmentEvent>> extends RxFragment {
    private static final String TAG = "BaseFragment";
    protected T mPresenter;
    /**
     * Fragment是否可见
     */
    private boolean isVisible;
    /**
     * Fragment是否初始化完成
     */
    private boolean isPrepared;
    /**
     * Fragment是否为首次加载
     */
    private boolean isFirst = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void init() {
    }

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisiable();
        } else {
            isVisible = false;
            onInVisiable();
        }
    }

    private void onVisiable() {
//        Log.e(TAG, "onVisiable: "+isPrepared+isVisible+isFirst );
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        lazyLoad();
        isFirst = false;
    }

    private void onInVisiable() {

    }

    /**
     * 懒加载
     */
    protected abstract void lazyLoad();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentResId(), container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
        isPrepared = true;
        onVisiable();
    }

    protected void initData() {

    }

    protected void initListener() {

    }

    /**
     * 获取资源id
     *
     * @return 资源id
     */
    protected abstract int getContentResId();

    protected void initView(View rootView) {

    }
}
