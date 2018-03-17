package cn.foxconn.matthew.myapp.wanandroid.presenter;

import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.UserBean;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.base.BasePresenter;
import cn.foxconn.matthew.myapp.wanandroid.view.LoginView;

/**
 * @author:Matthew
 * @date:2018/3/12
 * @email:guocheng0816@163.com
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private DataModel mDataModel;

    public LoginPresenter(){
        mDataModel=new DataModelImpl();
    }

    public void toLogin(String username,String password){
        mDataModel.toLogin(username, password, new RxObserverHelper<UserBean>() {

            @Override
            protected void _onSubscribe() {
                super._onSubscribe();
                getView().showProgress("正在登录");
            }

            @Override
            protected void _onNext(UserBean userBean) {
                getView().loginSuccess(userBean);
            }

            @Override
            protected void _onError(String message) {
                getView().loginFail(message);
                getView().hideProgress();
            }

            @Override
            protected void _onCompleted() {
                super._onCompleted();
                getView().hideProgress();
            }
        });
    }


    public void toRegister(String username,String password,String rePassword){
        mDataModel.toRegister(username, password, rePassword,new RxObserverHelper<UserBean>() {
            @Override
            protected void _onCompleted() {
                super._onCompleted();
                getView().hideProgress();
            }

            @Override
            protected void _onSubscribe() {
                super._onSubscribe();
                getView().showProgress("正在注册");
            }

            @Override
            protected void _onNext(UserBean userBean) {
                getView().registerSuccess(userBean);
            }

            @Override
            protected void _onError(String message) {
                getView().registerFail(message);
                getView().hideProgress();
            }
        });
    }
}
