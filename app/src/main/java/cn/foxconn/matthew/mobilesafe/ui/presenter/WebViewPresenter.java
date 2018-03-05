package cn.foxconn.matthew.mobilesafe.ui.presenter;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.function.ToDoubleBiFunction;

import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.CommonWebView;

/**
 * @author:Matthew
 * @date:2018/3/5
 * @email:guocheng0816@163.com
 */

public class WebViewPresenter extends BasePresenter<CommonWebView> {

    public void setWebView(WebView webView,String url){
        final CommonWebView commonWebView=getView();
        final ProgressBar progressBar=getView().getProgressBar();
        //TODO WebView的设置有待学习
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置JavaScript可用
        webSettings.setDisplayZoomControls(true);//设置显示缩放按钮
        webSettings.setUseWideViewPort(true);//设置支持双击缩放
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setAppCacheEnabled(true);
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
