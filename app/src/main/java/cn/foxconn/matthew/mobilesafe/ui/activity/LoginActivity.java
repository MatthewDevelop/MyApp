package cn.foxconn.matthew.mobilesafe.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.app.AppConst;
import cn.foxconn.matthew.mobilesafe.bean.pojo.UserBean;
import cn.foxconn.matthew.mobilesafe.ui.base.BaseActivity;
import cn.foxconn.matthew.mobilesafe.ui.presenter.LoginPresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.LoginView;
import cn.foxconn.matthew.mobilesafe.utils.PrefUtil;
import cn.foxconn.matthew.mobilesafe.utils.ToastUtil;
import cn.foxconn.matthew.mobilesafe.widget.FontTextView;

/**
 * @author:Matthew
 * @date:2018/3/12
 * @email:guocheng0816@163.com
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.ft_close)
    FontTextView ft_close;
    @BindView(R.id.et_userName)
    EditText et_userName;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.bt_register)
    Button bt_register;
    @BindView(R.id.bt_login)
    Button bt_login;


    @Override
    protected int getContentResId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick({R.id.ft_close, R.id.bt_login, R.id.bt_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ft_close:
                finish();
                break;
            case R.id.bt_login:
                if(TextUtils.isEmpty(et_userName.getText().toString())||TextUtils.isEmpty(et_password.getText().toString())){
                    ToastUtil.showShort(this,"用户名或密码不为空");
                }else if (et_userName.getText().toString().length()<6||et_password.getText().toString().length()<6){
                    ToastUtil.showShort(this,"用户名或密码长度不能小于6位");
                }else {
                    mPresenter.toLogin(et_userName.getText().toString(),et_password.getText().toString());
                }
                break;
            case R.id.bt_register:
                if(TextUtils.isEmpty(et_userName.getText().toString())||TextUtils.isEmpty(et_password.getText().toString())){
                    ToastUtil.showShort(this,"用户名或密码不为空");
                }else if (et_userName.getText().toString().length()<6||et_password.getText().toString().length()<6){
                    ToastUtil.showShort(this,"用户名或密码长度不能小于6位");
                }else {
                    mPresenter.toRegister(et_userName.getText().toString(),et_password.getText().toString(),et_password.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgress(String tipString) {
        showProgressDialog(tipString);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    /**
     * login and register
     */

    @Override
    public void loginSuccess(UserBean user) {
        ToastUtil.showShort(this,"登录成功");
        PrefUtil.setBoolean(LoginActivity.this, AppConst.IS_LOGIN_KEY,true);
        PrefUtil.setString(LoginActivity.this,AppConst.USERNAME_KEY,et_userName.getText().toString());
        startActivity(new Intent(LoginActivity.this,WanAndroidActivity.class));
        finish();
    }

    @Override
    public void registerSuccess(UserBean user) {
        ToastUtil.showShort(this,"注册成功");
        PrefUtil.setBoolean(LoginActivity.this, AppConst.IS_LOGIN_KEY,true);
        PrefUtil.setString(LoginActivity.this,AppConst.USERNAME_KEY,et_userName.getText().toString());
        startActivity(new Intent(LoginActivity.this,WanAndroidActivity.class));
        finish();
    }

    @Override
    public void loginFail(String message) {
        ToastUtil.showShort(this,message);
    }

    @Override
    public void registerFail(String message) {
        ToastUtil.showShort(this,message);
    }
}
