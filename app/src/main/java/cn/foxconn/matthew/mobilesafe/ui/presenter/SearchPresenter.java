package cn.foxconn.matthew.mobilesafe.ui.presenter;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.bean.pojo.HotKeyBean;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.helper.RxSubscribeHelper;
import cn.foxconn.matthew.mobilesafe.model.DataModel;
import cn.foxconn.matthew.mobilesafe.model.DataModelImpl;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.SearchView;

/**
 * @author:Matthew
 * @date:2018/3/15
 * @email:guocheng0816@163.com
 */

public class SearchPresenter extends BasePresenter<SearchView> {

    DataModel mDataModel;
    private int mCurrentPage;

    public SearchPresenter() {
        mDataModel = new DataModelImpl();
    }


    public void getHotKeyData() {
        mDataModel.getHotKeyList(new RxSubscribeHelper<List<HotKeyBean>>() {
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
        mDataModel.getSearchData(mCurrentPage, keyword, new RxSubscribeHelper<ArticleListVO>() {
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
        mDataModel.getSearchData(mCurrentPage, keyword, new RxSubscribeHelper<ArticleListVO>() {
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
