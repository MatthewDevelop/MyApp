package cn.foxconn.matthew.myapp.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import cn.foxconn.matthew.myapp.utils.LogUtil;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 开机广播接收器
 *
 * @author Matthew
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";

    //TODO android.permission.RECEIVE_BOOT_COMPLETED
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean protect = preferences.getBoolean("protect", false);
        if (protect) {
            //获取绑定的SIM卡
            String sim = preferences.getString("sim", null);
            if (!TextUtils.isEmpty(sim)) {
                //获取当前的手机卡
                TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                String currentSim = null;
                if (tm != null) {
                    currentSim = tm.getSimSerialNumber();
                }
                if (sim.equals(currentSim)) {
                    //do noting 手机安全
                    LogUtil.e(TAG, "安全");
                } else {
                    //sim卡变化
                    LogUtil.e(TAG, "变化了");
                    String phoneNum = preferences.getString("safe_phone", "");
                    SmsManager smsManager = SmsManager.getDefault();
                    //TODO android.permission.SEND_SMS
                    smsManager.sendTextMessage(phoneNum, null,
                            "Sim card has changed，your phone may be not security now"
                            , null, null);
                }
            }
        }
    }
}
