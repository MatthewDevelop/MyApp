package cn.foxconn.matthew.myapp.mobilesafe.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.foxconn.matthew.myapp.mobilesafe.db.dao.AddressDao;
import cn.foxconn.matthew.myapp.utils.ToastUtil;

/**
 * @author:Matthew
 * @date:2018/8/14
 * @email:guocheng0816@163.com
 * @func:监听去电的广播接收者
 */
public class OutCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String result=getResultData();
        String address= AddressDao.getAddress(result);
        System.out.println(address);
        ToastUtil.showToast(context, address);
    }
}
