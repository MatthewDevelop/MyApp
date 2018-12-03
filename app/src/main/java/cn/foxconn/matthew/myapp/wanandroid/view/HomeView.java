package cn.foxconn.matthew.myapp.wanandroid.view;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.base.BaseView;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.BannerBean;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public interface HomeView  extends BaseView {

    void showRefreshView(Boolean refresh);

    void getBannerDataSuccess(List<BannerBean> data);

    void getRefreshDataFailed(String message);

    void getRefreshDataSuccess(List<ArticleBean> data);

    void getMoreDataSuccess(List<ArticleBean> data);

    void getMoreDataFailed(String message);
}
