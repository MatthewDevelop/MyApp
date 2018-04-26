package cn.foxconn.matthew.myapp.wanandroid.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.utils.UIUtil;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.adapter.ArticleListAdapter;
import cn.foxconn.matthew.myapp.wanandroid.base.WanAndroidBaseActivity;
import cn.foxconn.matthew.myapp.wanandroid.presenter.SearchPresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.SearchView;
import cn.foxconn.matthew.myapp.utils.ToastUtil;
import cn.foxconn.matthew.myapp.wanandroid.widget.AutoLinefeedLayout;
import cn.foxconn.matthew.myapp.wanandroid.widget.FontTextView;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */
public class SearchActivity
        extends WanAndroidBaseActivity<SearchView, SearchPresenter>
        implements SearchView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ft_clear)
    FontTextView mFtClear;
    @BindView(R.id.ll_hot_key)
    LinearLayout mLlHotKey;
    @BindView(R.id.layout_hot_key)
    AutoLinefeedLayout mHotKey;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ArticleListAdapter mAdapter;

    CompositeDisposable mCompositeDisposable;

    private boolean isHotKeyExist = false;
    private String hotKey;

    @Override
    protected int getContentResId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void init() {
        super.init();
        Intent intent=getIntent();
        isHotKeyExist=intent.getBooleanExtra(AppConst.SEARCH_KEY,false);
        hotKey=intent.getStringExtra(AppConst.CONTENT_TITLE_KEY);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArticleListAdapter(this, null, mCompositeDisposable);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mPresenter.getHotKeyData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        /**
         * 设置编辑框内容变化监听事件
         */
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = mEtSearch.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    mPresenter.getSearchData(keyword);
                } else {
                    mLlHotKey.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if(isHotKeyExist){
            mEtSearch.setText(hotKey);
            // 将光标移至字符串尾部
            CharSequence charSequence = mEtSearch.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        }
    }

    @OnClick({R.id.ft_clear, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ft_clear:
                mEtSearch.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onLoadMoreRequested() {
        if (!TextUtils.isEmpty(mEtSearch.getText().toString())) {
            mPresenter.getMoreData(mEtSearch.getText().toString());
        }
    }

    @Override
    public void getHotKeySuccess(final List<HotKeyBean> data) {
        mHotKey.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            View view = LinearLayout.inflate(this, R.layout.item_hot_key, null);
            TextView tvHotKey = view.findViewById(R.id.tv_hot_key);
            tvHotKey.setText(data.get(i).getName());
            tvHotKey.setTextColor(UIUtil.getRandomColor());
            mHotKey.addView(view);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEtSearch.setText(data.get(finalI).getName());

                    // 将光标移至字符串尾部
                    CharSequence charSequence = mEtSearch.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                }
            });
        }
    }

    @Override
    public void getHotKeyFail(String message) {
        ToastUtil.showShort(this, message);
    }

    @Override
    public void searchDataSuccess(List<ArticleBean> data) {
        if (data == null || data.size() == 0) {
            mLlHotKey.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mLlHotKey.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.setNewData(data);
    }

    @Override
    public void searchDataFail(String message) {
        ToastUtil.showShort(this, message);
    }

    @Override
    public void loadMoreDataSuccess(List<ArticleBean> data) {
        if (data.size() != 0) {
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void loadMoreDataFail(String message) {
        mAdapter.loadMoreComplete();
        ToastUtil.showShort(this, message);
    }
}
