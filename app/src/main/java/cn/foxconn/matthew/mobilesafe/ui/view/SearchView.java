package cn.foxconn.matthew.mobilesafe.ui.view;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.pojo.ArticleBean;
import cn.foxconn.matthew.mobilesafe.bean.pojo.HotKeyBean;

/**
 * @author:Matthew
 * @date:2018/3/15
 * @email:guocheng0816@163.com
 */

public interface SearchView {

    void getHotKeySuccess(List<HotKeyBean> data);

    void getHotKeyFail(String message);

    void searchDataSuccess(List<ArticleBean> data);

    void searchDataFail(String message);

    void loadMoreDataSuccess(List<ArticleBean> data);

    void loadMoreDataFail(String message);
}
