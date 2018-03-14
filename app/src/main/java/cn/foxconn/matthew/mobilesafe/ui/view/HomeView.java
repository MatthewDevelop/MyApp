package cn.foxconn.matthew.mobilesafe.ui.view;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.pojo.ArticleBean;
import cn.foxconn.matthew.mobilesafe.bean.pojo.BannerBean;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public interface HomeView {

    void showRefreshView(Boolean refresh);

    void getBannerDataSuccess(List<BannerBean> data);

    void getRefreshDataFailed(String message);

    void getRefreshDataSuccess(List<ArticleBean> data);

    void getMoreDataSuccess(List<ArticleBean> data);

    void getMoreDataFailed(String message);
}
