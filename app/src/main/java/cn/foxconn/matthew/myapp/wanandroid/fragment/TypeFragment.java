package cn.foxconn.matthew.myapp.wanandroid.fragment;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;
import cn.foxconn.matthew.myapp.wanandroid.adapter.ArticleListAdapter;
import cn.foxconn.matthew.myapp.wanandroid.base.BaseFragment;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.TypeTagVO;
import cn.foxconn.matthew.myapp.wanandroid.presenter.TypePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.TypeView;
import cn.foxconn.matthew.myapp.wanandroid.widget.AutoLinefeedLayout;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author:Matthew
 * @date:2018/3/8
 * @email:guocheng0816@163.com
 */

public class TypeFragment
        extends BaseFragment<TypeView, TypePresenter>
        implements TypeView, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "TypeFragment";
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_tag)
    AutoLinefeedLayout mAutoLinefeedLayout;
    @BindView(R.id.ll_blank)
    LinearLayout mLlBlank;

    private ArticleListAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable;


    public static TypeFragment newInstance() {
        return new TypeFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void init() {
        super.init();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected TypePresenter createPresenter() {
        return new TypePresenter(getActivity(), this);
    }

    @Override
    protected void lazyLoad() {
        onRefresh();
    }

    @Override
    protected int getContentResId() {
        return R.layout.frag_type;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleListAdapter(getContext(), null, mCompositeDisposable);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }


    public void onRefresh() {
        Log.e(TAG, "onRefresh: ");
        mPresenter.getTagData();
    }

    /**
     * 请求加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreData();
    }

    @Override
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    @Override
    public AutoLinefeedLayout getTagLayout() {
        return mAutoLinefeedLayout;
    }

    @Override
    public ArticleListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void getTagDataSuccess(List<TypeTagVO> typeTagVOs) {

    }

    @Override
    public void getDataError(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getRefreshDataSuccess(List<ArticleBean> data) {
        if (data.size() != 0) {
            mLlBlank.setVisibility(View.GONE);
        } else {
            mLlBlank.setVisibility(View.VISIBLE);
        }
        mAdapter.setNewData(data);
    }

    @Override
    public void getMoreDataSuccess(List<ArticleBean> data) {
        if (data.size() != 0) {
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }
}
