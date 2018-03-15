package cn.foxconn.matthew.myapp.wanandroid.view;

import android.support.design.widget.TabLayout;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;
import cn.foxconn.matthew.myapp.wanandroid.adapter.ArticleListAdapter;
import cn.foxconn.matthew.myapp.wanandroid.widget.AutoLinefeedLayout;

/**
 * @author:Matthew
 * @date:2018/3/8
 * @email:guocheng0816@163.com
 */

public interface  TypeView {
    TabLayout getTabLayout();
    AutoLinefeedLayout getTagLayout();
    ArticleListAdapter getAdapter();
    void getDataError(String message);
    void getRefreshDataSuccess(List<ArticleBean> data);
    void getMoreDataSuccess(List<ArticleBean> data);
}
