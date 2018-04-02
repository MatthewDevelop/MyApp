package cn.foxconn.matthew.myapp.wanandroid.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.utils.UIUtil;
import cn.foxconn.matthew.myapp.wanandroid.base.BaseActivity;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.presenter.HotPresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.HotView;

/**
 * @author:Matthew
 * @date:2018/3/31
 * @email:guocheng0816@163.com
 */

public class HotActivity extends BaseActivity<HotView, HotPresenter>
        implements HotView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tfl_hot_search)
    TagFlowLayout mTflHotSearch;
    @BindView(R.id.tfl_common_website)
    TagFlowLayout mTflCommonWebsit;

    private TagFlowLayoutAdapter mHotKeyTagAdapter;
    private TagFlowLayoutAdapter mCommonWebsiteTagAdapter;

    private List<HotKeyBean> mHotKeyList;
    private List<HotKeyBean> mCommomWebsiteList;


    @Override
    protected int getContentResId() {
        return R.layout.activity_hot;
    }

    @Override
    protected HotPresenter createPresenter() {
        return new HotPresenter(this);
    }

    @Override
    protected void init() {
        super.init();
        mHotKeyList = new ArrayList<>();
        mCommomWebsiteList = new ArrayList<>();
        mHotKeyTagAdapter = new TagFlowLayoutAdapter(mHotKeyList);
        mCommonWebsiteTagAdapter = new TagFlowLayoutAdapter(mCommomWebsiteList);
    }

    @Override
    protected void initView() {
        super.initView();
        mTflHotSearch.setAdapter(mHotKeyTagAdapter);
        mTflCommonWebsit.setAdapter(mCommonWebsiteTagAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.getHotKeyList();
        mPresenter.getCommonWebSiteList();
    }

    @Override
    public void showRefreshView(final Boolean refresh) {
        //保障首次
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(refresh);
            }
        });
    }

    @Override
    public void loadHotKeySuccess(List<HotKeyBean> hotKeyBeans) {
        mHotKeyList.clear();
        mHotKeyList.addAll(hotKeyBeans);
        mHotKeyTagAdapter.notifyDataChanged();
    }

    @Override
    public void loadCommonWebSiteSuccess(List<HotKeyBean> hotKeyBeans) {
        mCommomWebsiteList.clear();
        mCommomWebsiteList.addAll(hotKeyBeans);
        mCommonWebsiteTagAdapter.notifyDataChanged();
    }

    @Override
    public void loadDataFailed(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT).show();
    }

    class TagFlowLayoutAdapter extends TagAdapter<HotKeyBean> {


        public TagFlowLayoutAdapter(List<HotKeyBean> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, HotKeyBean hotKeyBean) {
            View view = LayoutInflater.from(HotActivity.this).inflate(R.layout.item_hot_key, parent, false);
            TextView textView = view.findViewById(R.id.tv_hot_key);
            textView.setText(hotKeyBean.getName());
            textView.setTextColor(UIUtil.getRandomColor());
            return view;
        }
    }

}
