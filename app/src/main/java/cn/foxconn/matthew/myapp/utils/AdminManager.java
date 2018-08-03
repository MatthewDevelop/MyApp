package cn.foxconn.matthew.myapp.utils;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import cn.foxconn.matthew.myapp.app.App;
import cn.foxconn.matthew.myapp.mobilesafe.receiver.AdminReceiver;

/**
 * @author:Matthew
 * @date:2018/8/3
 * @email:guocheng0816@163.com
 * @func:管理员工具类
 */
public class AdminManager {

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;

    private AdminManager() {
        //获取设备策略服务
        mDevicePolicyManager = (DevicePolicyManager) App.getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(App.getContext(), AdminReceiver.class);
    }

    public static AdminManager getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * 开启设备管理器
     */
    public void activeAdmin(Activity activity) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "谨慎开启设备管理器");
        activity.startActivityForResult(intent, 0);
//        App.getContext().startActivity(intent);
    }

    /**
     * 移除设备管理器
     */
    public void removeActive() {
        mDevicePolicyManager.removeActiveAdmin(mComponentName);
    }

    /**
     * 锁屏并重置密码
     */
    public void lockScreen() {
        if (isAdminActived()) {
            mDevicePolicyManager.lockNow();
            mDevicePolicyManager.resetPassword("1234", 0);
        } else {
            ToastUtil.show("前往设置激活设备管理器");
        }
    }

    public boolean isAdminActived() {
        return mDevicePolicyManager.isAdminActive(mComponentName);
    }

    /**
     * 清除数据
     */
    public void wipeData() {
        if (isAdminActived()) {
            mDevicePolicyManager.wipeData(0);
        } else {
            ToastUtil.show("前往设置激活设备管理器");
        }
    }

    /**
     * 卸载App
     */
    public void uninstall() {
        //移除设备管理器
        mDevicePolicyManager.removeActiveAdmin(mComponentName);
        //卸载App
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + App.getContext().getPackageName()));
        App.getContext().startActivity(intent);
    }

    private static class InstanceHolder {
        private static AdminManager instance = new AdminManager();
    }

}
