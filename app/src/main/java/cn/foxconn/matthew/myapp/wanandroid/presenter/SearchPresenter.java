package cn.foxconn.matthew.myapp.wanandroid.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.BaseRxObserverHelper;
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
        mDataModel.getHotKeyListInActivity(getProvider(),new BaseRxObserverHelper<List<HotKeyBean>>() {
            @Override
            protected void next(List<HotKeyBean> hotKeyBeans) {
                getView().getHotKeySuccess(hotKeyBeans);
            }

            @Override
            protected void error(String message) {
                getView().getHotKeyFail(message);
            }
        });
    }

    public void getSearchData(String keyword) {
        mCurrentPage = 0;
        mDataModel.getSearchData(mCurrentPage, keyword,getProvider(), new BaseRxObserverHelper<ArticleListVO>() {
            @Override
            protected void next(ArticleListVO articleListVO) {
                getView().searchDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void error(String message) {
                getView().searchDataFail(message);
            }
        });
    }

    public void getMoreData(String keyword) {
        mCurrentPage = mCurrentPage + 1;
        mDataModel.getSearchData(mCurrentPage, keyword, getProvider(),new BaseRxObserverHelper<ArticleListVO>() {
            @Override
            protected void next(ArticleListVO articleListVO) {
                getView().loadMoreDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void error(String message) {
                getView().loadMoreDataFail(message);
            }
        });
    }

}
