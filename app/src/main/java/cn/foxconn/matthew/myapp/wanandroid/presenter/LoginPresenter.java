package cn.foxconn.matthew.myapp.wanandroid.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.helper.BaseRxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.LoginView;

/**
 * @author:Matthew
 * @date:2018/3/12
 * @email:guocheng0816@163.com
 */

public class LoginPresenter extends BasePresenter<LoginView,ActivityEvent> {

    private DataModel mDataModel;

    public LoginPresenter(LifecycleProvider provider) {
        super(provider);
        mDataModel=new DataModelImpl();
    }

    public void toLogin(String username,String password){
        mDataModel.toLogin(username, password, getProvider(),new BaseRxObserverHelper<UserBean>() {

            @Override
            protected void start() {
                super.start();
                getView().showProgress("正在登录");
            }

            @Override
            protected void next(UserBean userBean) {
                getView().loginSuccess(userBean);
            }

            @Override
            protected void error(String message) {
                getView().loginFail(message);
                getView().hideProgress();
            }

            @Override
            protected void completed() {
                super.completed();
                getView().hideProgress();
            }
        });
    }


    public void toRegister(String username,String password,String rePassword){
        mDataModel.toRegister(username, password, rePassword,getProvider(),new BaseRxObserverHelper<UserBean>() {
            @Override
            protected void completed() {
                super.completed();
                getView().hideProgress();
            }

            @Override
            protected void start() {
                super.start();
                getView().showProgress("正在注册");
            }

            @Override
            protected void next(UserBean userBean) {
                getView().registerSuccess(userBean);
            }

            @Override
            protected void error(String message) {
                getView().registerFail(message);
                getView().hideProgress();
            }
        });
    }
}
