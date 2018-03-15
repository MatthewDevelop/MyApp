package cn.foxconn.matthew.myapp.wanandroid.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * @author:Matthew
 * @date:2018/3/5
 * @email:guocheng0816@163.com
 */

/**
 * 包含一个webView的fragment
 */
public class WebViewFragment extends Fragment {
    private WebView mWebView;
    private boolean isWebViewAvailable;

    public WebViewFragment(){

    }

    /**
     * 初始化时返回一个webView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mWebView!=null){
            mWebView.destroy();
        }
        mWebView=new WebView(getContext());
        isWebViewAvailable=true;
        return mWebView;
    }

    /**
     *不可见时pause WebView
     */
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    /**
     * 布局重新可见时恢复布局
     */
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isWebViewAvailable=false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebView!=null){
            mWebView.destroy();
            mWebView=null;
        }
    }

    /**
     * get WebView
     * @return
     */
    public WebView getWebView(){
        return isWebViewAvailable? mWebView:null;
    }
}
