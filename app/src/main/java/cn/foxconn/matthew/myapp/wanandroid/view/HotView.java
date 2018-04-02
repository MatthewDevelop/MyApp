package cn.foxconn.matthew.myapp.wanandroid.view;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;

/**
 * @author:Matthew
 * @date:2018/3/31
 * @email:guocheng0816@163.com
 */

public interface HotView {
    void showRefreshView(Boolean refresh);
    void loadHotKeySuccess(List<HotKeyBean> hotKeyBeans);
    void loadCommonWebSiteSuccess(List<HotKeyBean> hotKeyBeans);
    void loadDataFailed(String message);
}
