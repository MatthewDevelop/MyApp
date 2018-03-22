package cn.foxconn.matthew.myapp.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void initListener() {

    }

    protected void initData() {

    }

    protected void initView(View rootView) {

    }

    /**
     * 获取资源id
     *
     * @return 资源id
     */
    protected abstract int getContentResId();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    protected void init() {
    }
}
