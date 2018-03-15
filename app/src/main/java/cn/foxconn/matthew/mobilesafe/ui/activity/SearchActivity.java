package cn.foxconn.matthew.mobilesafe.ui.activity;

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
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.bean.pojo.ArticleBean;
import cn.foxconn.matthew.mobilesafe.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.mobilesafe.ui.adapter.ArticleListAdapter;
import cn.foxconn.matthew.mobilesafe.ui.base.BaseActivity;
import cn.foxconn.matthew.mobilesafe.ui.presenter.SearchPresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.SearchView;
import cn.foxconn.matthew.mobilesafe.utils.ToastUtil;
import cn.foxconn.matthew.mobilesafe.widget.AutoLinefeedLayout;
import cn.foxconn.matthew.mobilesafe.widget.FontTextView;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */
public class SearchActivity
        extends BaseActivity<SearchView, SearchPresenter>
        implements SearchView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.ft_clear)
    FontTextView ft_clear;
    @BindView(R.id.ll_hot_key)
    LinearLayout ll_hot_key;
    @BindView(R.id.layout_hot_key)
    AutoLinefeedLayout hot_key;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ArticleListAdapter mAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArticleListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mPresenter.getHotKeyData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        /**
         * 设置编辑框内容变化监听事件
         */
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword=et_search.getText().toString();
                if(!TextUtils.isEmpty(keyword)) {
                    mPresenter.getSearchData(keyword);
                }else {
                    ll_hot_key.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.ft_clear, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ft_clear:
                et_search.setText("");
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
        if(!TextUtils.isEmpty(et_search.getText().toString())){
            mPresenter.getMoreData(et_search.getText().toString());
        }
    }

    @Override
    public void getHotKeySuccess(final List<HotKeyBean> data) {
        hot_key.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            View view = LinearLayout.inflate(this, R.layout.item_hot_key, null);
            TextView tv_hotkey = view.findViewById(R.id.tv_hot_key);
            tv_hotkey.setText(data.get(i).getName());
            hot_key.addView(view);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_search.setText(data.get(finalI).getName());

                    // 将光标移至字符串尾部
                    CharSequence charSequence = et_search.getText();
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
        ToastUtil.showShort(this,message);
    }

    @Override
    public void searchDataSuccess(List<ArticleBean> data) {
        if(data==null||data.size()==0){
            ll_hot_key.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            ll_hot_key.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.setNewData(data);
    }

    @Override
    public void searchDataFail(String message) {
        ToastUtil.showShort(this,message);
    }

    @Override
    public void loadMoreDataSuccess(List<ArticleBean> data) {
        if(data.size()!=0){
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
        }else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void loadMoreDataFail(String message) {
        mAdapter.loadMoreComplete();
        ToastUtil.showShort(this,message);
    }
}
