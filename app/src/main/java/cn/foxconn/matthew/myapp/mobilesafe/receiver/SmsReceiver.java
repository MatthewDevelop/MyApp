package cn.foxconn.matthew.myapp.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;


import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.service.LocationService;
import cn.foxconn.matthew.myapp.utils.AdminManager;

/**
 * @author:Matthew
 * @date:2018/8/3
 * @email:guocheng0816@163.com
 * @func:短信拦截
 */
public class SmsReceiver extends BroadcastReceiver {
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

            if ("#*alarm*#".equals(messageBody)) {
                //播放音乐
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);//加载资源
                player.setVolume(1f, 1f);//设置音量
                player.setLooping(true);//单曲循环
                player.start();
                System.out.println("中断广播");
                abortBroadcast();//中断短信的传递，系统短信app接收不到内容
            } else if ("#*location*#".equals(messageBody)) {
                //获取经纬度坐标
                context.startService(new Intent(context, LocationService.class));
                SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                String location = preferences.getString("Location", "getting location...");
                System.out.println("location:" + location);
                String phoneNum = preferences.getString("safe_phone", "");
                SmsManager smsManager = SmsManager.getDefault();
                //TODO android.permission.SEND_SMS
                smsManager.sendTextMessage(phoneNum, null,
                        location
                        , null, null);
                abortBroadcast();
            } else if ("#*lockScreen*#".equals(messageBody)) {
                //锁屏
                AdminManager.getInstance().lockScreen();
                abortBroadcast();
            } else if ("#*wipeData*#".equals(messageBody)) {
                //清除数据
                AdminManager.getInstance().wipeData();
                abortBroadcast();
            }
        }
    }


}
