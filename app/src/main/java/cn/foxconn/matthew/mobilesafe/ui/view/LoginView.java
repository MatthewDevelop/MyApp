package cn.foxconn.matthew.mobilesafe.ui.view;

import cn.foxconn.matthew.mobilesafe.bean.pojo.UserBean;

/**
 * @author:Matthew
 * @date:2018/3/12
 * @email:guocheng0816@163.com
 */

public interface LoginView {

    void showProgress(String tipString);

    void hideProgress();

    void loginSuccess(UserBean user);

    void registerSuccess(UserBean user);

    void loginFail(String message);

    void registerFail(String message);

}
