package cn.foxconn.matthew.myapp.wanandroid.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojovo.ArticleListVO;
import cn.foxconn.matthew.myapp.wanandroid.helper.BaseRxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.CollectView;

/**
 * @author:Matthew
 * @date:2018/3/13
 * @email:guocheng0816@163.com
 */

public class CollectPresenter extends BasePresenter<CollectView,ActivityEvent> {

    DataModel mDataModel;
    private int mCurrentPage;

    public CollectPresenter(LifecycleProvider provider) {
        super(provider);
        mDataModel=new DataModelImpl();
    }

    /**
     * 获取收藏列表
     */
    public void getRefreshData(){
        mCurrentPage=0;
        mDataModel.getCollectList(mCurrentPage, getProvider(),new BaseRxObserverHelper<ArticleListVO>() {
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
            protected void next(ArticleListVO articleListVO) {
                getView().onRefreshSuccess(articleListVO.getDatas());
            }

            @Override
            protected void error(String message) {
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
        mDataModel.getCollectList(mCurrentPage, getProvider(), new BaseRxObserverHelper<ArticleListVO>() {
            @Override
            protected void next(ArticleListVO articleListVO) {
                getView().onLoadMoreSuccess(articleListVO.getDatas());
            }

            @Override
            protected void error(String message) {
                getView().onLoadMoreFail(message);
            }
        });

    }

}
