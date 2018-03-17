package cn.foxconn.matthew.myapp.wanandroid.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.SearchView;

/**
 * @author:Matthew
 * @date:2018/3/15
 * @email:guocheng0816@163.com
 */

public class SearchPresenter extends BasePresenter<SearchView,ActivityEvent> {

    DataModel mDataModel;
    private int mCurrentPage;

    public SearchPresenter(LifecycleProvider provider) {
        super(provider);
        mDataModel = new DataModelImpl();
    }


    public void getHotKeyData() {
        mDataModel.getHotKeyList(getProvider(),new RxObserverHelper<List<HotKeyBean>>() {
            @Override
            protected void _onNext(List<HotKeyBean> hotKeyBeans) {
                getView().getHotKeySuccess(hotKeyBeans);
            }

            @Override
            protected void _onError(String message) {
                getView().getHotKeyFail(message);
            }
        });
    }

    public void getSearchData(String keyword) {
        mCurrentPage = 0;
        mDataModel.getSearchData(mCurrentPage, keyword,getProvider(), new RxObserverHelper<ArticleListVO>() {
            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().searchDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                getView().searchDataFail(message);
            }
        });
    }

    public void getMoreData(String keyword) {
        mCurrentPage = mCurrentPage + 1;
        mDataModel.getSearchData(mCurrentPage, keyword, getProvider(),new RxObserverHelper<ArticleListVO>() {
            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().loadMoreDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                getView().loadMoreDataFail(message);
            }
        });
    }

}
