package cn.foxconn.matthew.myapp.wanandroid.presenter;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.CommonWebView;

/**
 * @author:Matthew
 * @date:2018/3/5
 * @email:guocheng0816@163.com
 */

public class WebViewPresenter extends BasePresenter<CommonWebView,ActivityEvent> {

    public WebViewPresenter(LifecycleProvider provider) {
        super(provider);
    }

    public void setWebView(WebView webView, String url){
        final CommonWebView commonWebView=getView();
        final ProgressBar progressBar=getView().getProgressBar();
        //TODO WebView的设置有待学习
        WebSettings webSettings=webView.getSettings();
        //设置JavaScript可用
        webSettings.setJavaScriptEnabled(true);
        //设置屏幕适应
        //设置支持缩放，是下项设置的前提
        webSettings.setSupportZoom(true);
        //设置可以缩放缩放
        webSettings.setBuiltInZoomControls(true);
        //设置隐藏原生缩放控件
        webSettings.setDisplayZoomControls(false);
        //设置调整图片至合适webView大小
        webSettings.setUseWideViewPort(true);
        //设置缩放至屏幕大小
        webSettings.setLoadWithOverviewMode(true);

        //支持通过js打开新的窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置默认编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //开启Application Cache（html5缓存）功能
        webSettings.setAppCacheEnabled(true);
        //开启Dom Storage功能
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            /**
             * 跳转链接在此方法中执行
             * @param view
             * @param url
             * @return
             */
            //TODO shouldOverrideUrlLoading(WebView view, String url)和 shouldOverrideUrlLoading(WebView view, WebResourceRequest request)区别

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                commonWebView.setTitle(title);
            }
        });

        webView.loadUrl(url);
    }
}
