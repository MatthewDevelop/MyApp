package cn.foxconn.matthew.mobilesafe.ui.presenter;

import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.helper.RxSubscribeHelper;
import cn.foxconn.matthew.mobilesafe.model.DataModel;
import cn.foxconn.matthew.mobilesafe.model.DataModelImpl;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.CollectView;

/**
 * @author:Matthew
 * @date:2018/3/13
 * @email:guocheng0816@163.com
 */

public class CollectPresenter extends BasePresenter<CollectView> {

    DataModel mDataModel;
    private int mCurrentPage;

    public CollectPresenter(){
        mDataModel=new DataModelImpl();
    }

    /**
     * 获取收藏列表
     */
    public void getRefreshData(){
        mCurrentPage=0;
        mDataModel.getCollectList(mCurrentPage, new RxSubscribeHelper<ArticleListVO>() {
            @Override
            protected void _onCompleted() {
                super._onCompleted();
                getView().showRefreshView(false);
            }

            @Override
            protected void _onStart() {
                super._onStart();
                getView().showRefreshView(true);
            }

            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().onRefreshSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                getView().onRefreshFail(message);
                getView().showRefreshView(false);
            }
        });
    }

    /**
     * 加载更多
     */
    public void getMoreData(){
        mCurrentPage=mCurrentPage+1;
        mDataModel.getCollectList(mCurrentPage, new RxSubscribeHelper<ArticleListVO>() {
            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().onLoadMoreSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                getView().onLoadMoreFail(message);
            }
        });

    }

}
