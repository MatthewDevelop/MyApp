package cn.foxconn.matthew.mobilesafe.ui.view;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.model.pojo.ArticleBean;
import cn.foxconn.matthew.mobilesafe.model.pojo.BannerBean;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public interface HomeView {
    void showRefreshView(Boolean refresh);
    void getBannerDataSuccess(List<BannerBean> data);
    void getDataError(String message);
    void getRefreshDataSuccess(List<ArticleBean> data);
    void getMoreDataSuccess(List<ArticleBean> data);
}
