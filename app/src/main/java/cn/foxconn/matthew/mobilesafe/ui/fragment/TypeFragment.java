package cn.foxconn.matthew.mobilesafe.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.bean.pojo.ArticleBean;
import cn.foxconn.matthew.mobilesafe.ui.adapter.ArticleListAdapter;
import cn.foxconn.matthew.mobilesafe.ui.base.BaseFragment;
import cn.foxconn.matthew.mobilesafe.ui.presenter.TypePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.TypeView;
import cn.foxconn.matthew.mobilesafe.widget.AutoLinefeedLayout;

/**
 * @author:Matthew
 * @date:2018/3/8
 * @email:guocheng0816@163.com
 */

public class TypeFragment
        extends BaseFragment<TypeView, TypePresenter>
        implements TypeView,BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_tag)
    AutoLinefeedLayout mAutoLinefeedLayout;
    @BindView(R.id.ll_blank)
    LinearLayout ll_blank;

    private ArticleListAdapter mAdapter;



    public static TypeFragment newInstance(){
        return new TypeFragment();
    }

    @Override
    protected int getContentResId() {
        return R.layout.frag_type;
    }

    @Override
    protected TypePresenter createPresenter() {
        return new TypePresenter(getActivity());
    }


    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter=new ArticleListAdapter(getContext(),null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
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
    public void getDataError(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getRefreshDataSuccess(List<ArticleBean> data) {
        if(data.size()!=0) {
            ll_blank.setVisibility(View.GONE);
        }else {
            ll_blank.setVisibility(View.VISIBLE);
        }
        mAdapter.setNewData(data);
    }

    @Override
    public void getMoreDataSuccess(List<ArticleBean> data) {
        if(data.size()!=0){
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
        }else {
            mAdapter.loadMoreEnd();
        }
    }
}
