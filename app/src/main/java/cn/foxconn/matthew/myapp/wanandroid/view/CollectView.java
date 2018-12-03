package cn.foxconn.matthew.myapp.wanandroid.view;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.base.BaseView;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;

/**
 * @author:Matthew
 * @date:2018/3/13
 * @email:guocheng0816@163.com
 */

public interface CollectView extends BaseView {

    /**
     * 刷新状态
     * @param isRefresh
     */
    void showRefreshView(boolean isRefresh);

    void onRefreshSuccess(List<ArticleBean> data);

    void onRefreshFail(String errorString);

    void onLoadMoreSuccess(List<ArticleBean> data);

    void onLoadMoreFail(String errorString);
}
