package cn.foxconn.matthew.mobilesafe.ui.presenter;

import cn.foxconn.matthew.mobilesafe.bean.pojo.UserBean;
import cn.foxconn.matthew.mobilesafe.helper.RxSubscribeHelper;
import cn.foxconn.matthew.mobilesafe.model.DataModel;
import cn.foxconn.matthew.mobilesafe.model.DataModelImpl;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.LoginView;

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
        mDataModel.toLogin(username, password, new RxSubscribeHelper<UserBean>() {

            @Override
            protected void _onStart() {
                super._onStart();
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
        mDataModel.toRegister(username, password, rePassword,new RxSubscribeHelper<UserBean>() {
            @Override
            protected void _onCompleted() {
                super._onCompleted();
                getView().hideProgress();
            }

            @Override
            protected void _onStart() {
                super._onStart();
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
