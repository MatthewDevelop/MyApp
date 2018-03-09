package cn.foxconn.matthew.mobilesafe.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.ui.base.BaseActivity;
import cn.foxconn.matthew.mobilesafe.ui.presenter.WebViewPresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.CommonWebView;
import cn.foxconn.matthew.mobilesafe.utils.ActivityUtil;
import cn.foxconn.matthew.mobilesafe.utils.SharesUtils;
import cn.foxconn.matthew.mobilesafe.widget.CustomPopWindow;
import cn.foxconn.matthew.mobilesafe.widget.FontTextView;
import cn.foxconn.matthew.mobilesafe.widget.WebViewFragment;

/**
 * @author:Matthew
 * @date:2018/3/5
 * @email:guocheng0816@163.com
 */

public class WebViewActivity
        extends BaseActivity<CommonWebView, WebViewPresenter>
        implements CommonWebView {

    public static final String WEB_URL = "web_url";

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ft_return)
    FontTextView ft_return;
    @BindView(R.id.ft_more)
    FontTextView ft_more;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.rl_topbar)
    RelativeLayout rl_topbar;
    @BindView(R.id.webView_container)
    NestedScrollView webContainer;

    private String url;
    private WebViewFragment mWebViewFragment;
    private WebView mWebView;
    private CustomPopWindow mMorePopWindow;


    @Override
    protected int getContentResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter();
    }

    @Override
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 启动Activity
     *
     * @param context
     * @param url
     */
    public static void runActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        super.init();
        url=getIntent().getStringExtra(WEB_URL);
    }

    @Override
    protected void initView() {
        super.initView();
        mWebViewFragment=new WebViewFragment();
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),mWebViewFragment,R.id.webView_container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //get WebView
        mWebView=mWebViewFragment.getWebView();
        mPresenter.setWebView(mWebView,url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    /**
     * 处理页面回退
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(mWebView.canGoBack()){
                mWebView.goBack();
            }else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ft_return,R.id.ft_more})
    public void OnViewClicked(View view){
        switch (view.getId()){
            case R.id.ft_return:
                finish();
                break;
            case R.id.ft_more:
                //TODO Android PobWindow
                View popView=View.inflate(this,R.layout.pupup_webview_more,null);
                mMorePopWindow=new CustomPopWindow.PopupWindowBuilder(this)
                                .setView(popView)
                                .enableBackgroundDark(false)
                                .create()
                                .showAsDropDown(ft_more,-430,-10);

                //设置点击事件
                //分享
                popView.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharesUtils.share(WebViewActivity.this,mWebView.getUrl());
                        mMorePopWindow.dissmiss();
                    }
                });
                //复制链接
                popView.findViewById(R.id.tv_copy_link).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 剪切板的使用
                        ClipboardManager clipboardManager= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText(getString(R.string.copy_link),mWebView.getUrl()));
                        Snackbar.make(getWindow().getDecorView(),R.string.copy_link_success,Snackbar.LENGTH_SHORT).show();
                        mMorePopWindow.dissmiss();
                    }
                });
                //用系统浏览器打开
                popView.findViewById(R.id.tv_open_out).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO open with system Browser
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl()));
                        startActivity(intent);
                        mMorePopWindow.dissmiss();
                    }
                });
                break;
        }
    }
}
