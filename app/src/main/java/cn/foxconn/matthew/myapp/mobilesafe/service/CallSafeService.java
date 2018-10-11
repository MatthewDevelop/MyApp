package cn.foxconn.matthew.myapp.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsMessage;

import cn.foxconn.matthew.myapp.mobilesafe.db.dao.BlackNumDao;

public class CallSafeService extends Service {

    private BlackNumDao mBlackNumDao;
    private InnerReceiver mReceiver;

    public CallSafeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBlackNumDao = new BlackNumDao(this);
        mReceiver = new InnerReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class InnerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//短信长度超标会变成多条短信，所以是数组
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            for (Object object :
                    objects) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
                //来信号码
                String fromAddress = message.getOriginatingAddress();
                //短信内容
                String messageBody = message.getMessageBody();
                System.out.println(fromAddress + "--->" + messageBody);
                //根据号码查询拦截模式
                String mode=mBlackNumDao.query(fromAddress);
                System.out.println(mode);
                /**
                 * 拦截模式
                 * 1 电话
                 * 2 短信
                 * 3 电话加短信
                 */
                System.out.println("短信来了");
                if ("2".equals(mode)||"3".equals(mode)){
                    abortBroadcast();
                    System.out.println("abortBroadcast");
                }

            }
        }
    }
}
