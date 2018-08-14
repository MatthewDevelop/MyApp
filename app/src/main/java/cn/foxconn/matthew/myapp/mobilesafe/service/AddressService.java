package cn.foxconn.matthew.myapp.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.TextView;

import cn.foxconn.matthew.myapp.mobilesafe.db.dao.AddressDao;
import cn.foxconn.matthew.myapp.utils.ToastUtil;

/**
 * @author:Matthew
 * @date:2018/8/13
 * @email:guocheng0816@163.com
 * @func:
 */
public class AddressService extends Service {

    private TelephonyManager mTelephonyManager;
    private MyListener mListener;
    private OutCallReceiver mReceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mListener = new MyListener();
        //监听来电话的状态
        mTelephonyManager.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
        //注册广播监听去电状态
        mReceiver = new OutCallReceiver();
        mIntentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止服务取消监听
        mTelephonyManager.listen(mListener, PhoneStateListener.LISTEN_NONE);
        unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class MyListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                //来电
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("电话响了····");
                    String address = AddressDao.getAddress(incomingNumber);
                    System.out.println(address);
                    //ToastUtil.showLong(address);
                    ToastUtil.showToast(AddressService.this, address);
                    break;
                    //挂断电话，电话处于闲置状态
                case TelephonyManager.CALL_STATE_IDLE:
                    ToastUtil.hideToast();
                    break;
                default:
                    break;
            }
        }
    }
}
