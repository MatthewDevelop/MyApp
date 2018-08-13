package cn.foxconn.matthew.myapp.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import cn.foxconn.matthew.myapp.mobilesafe.db.dao.AddressDao;

/**
 * @author:Matthew
 * @date:2018/8/13
 * @email:guocheng0816@163.com
 * @func:
 */
public class AddressService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        MyListener myListener = new MyListener();
        //监听来电话的状态
        telephonyManager.listen(myListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("电话响了····");
                    String address=AddressDao.getAddress(incomingNumber);

                    break;
                default:
                    break;
            }
        }
    }
}
