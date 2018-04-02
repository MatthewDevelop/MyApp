package cn.foxconn.matthew.myapp.wanandroid.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.helper.BaseRxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.view.HotView;

/**
 * @author:Matthew
 * @date:2018/3/31
 * @email:guocheng0816@163.com
 */

public class HotPresenter extends BasePresenter<HotView, ActivityEvent> {

    DataModel mDataModel;

    public HotPresenter(LifecycleProvider provider) {
        super(provider);
        mDataModel = new DataModelImpl();
    }

    public void getCommonWebSiteList() {

        mDataModel.getCommonWebsitList(getProvider(), new BaseRxObserverHelper<List<HotKeyBean>>() {

            @Override
            protected void completed() {
                super.completed();
                getView().showRefreshView(false);
            }

            @Override
            protected void start() {
                super.start();
                getView().showRefreshView(true);
            }

            @Override
            protected void next(List<HotKeyBean> hotKeyBeans) {
                if(hotKeyBeans.size()==0){
                    getView().loadDataFailed("获取数据为空");
                }else {
                    getView().loadCommonWebSiteSuccess(hotKeyBeans);
                }
            }

            @Override
            protected void error(String message) {
                getView().loadDataFailed(message);
            }
        });
    }


    public void getHotKeyList() {
        mDataModel.getHotKeyListInActivity(getProvider(), new BaseRxObserverHelper<List<HotKeyBean>>() {
            @Override
            protected void next(List<HotKeyBean> hotKeyBeans) {
                if(hotKeyBeans.size()==0){
                    getView().loadDataFailed("获取数据为空");
                }else {
                    getView().loadHotKeySuccess(hotKeyBeans);
                }
            }

            @Override
            protected void error(String message) {
                getView().loadDataFailed(message);
            }
        });
    }
}
