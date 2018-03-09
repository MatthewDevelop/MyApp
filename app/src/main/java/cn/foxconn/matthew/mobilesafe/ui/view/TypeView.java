package cn.foxconn.matthew.mobilesafe.ui.view;

import android.support.design.widget.TabLayout;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.pojo.ArticleBean;
import cn.foxconn.matthew.mobilesafe.ui.adapter.ArticleListAdapter;
import cn.foxconn.matthew.mobilesafe.widget.AutoLinefeedLayout;

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
